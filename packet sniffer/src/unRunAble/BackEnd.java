package unRunAble;


import abstractClasses.SniffThread;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;
import jpcap.packet.Packet;
import unRunAble.InterFaces.FrontEndToBackEndInterface;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Objects;

import static jpcap.JpcapCaptor.getDeviceList;

/**
 * The BackEnd class represents the backend logic for packet sniffing.
 */
public class BackEnd {


    private final ArrayList<Packet> packetList = new ArrayList<Packet>(10);
    private final FrontEndToBackEndInterface frontEnd;
    public SniffThread thread;
    private NetworkInterface[] networkInterfacesList;
    private int selectedInterFace;
    private JpcapCaptor jpcapCaptor;
    private Packet packet;
    private Formatter fileWriter;
    private final PacketContents packetReceive = new PacketContents();

    /**
     * Constructor for BackEnd class.
     *
     * @param ob FrontEndToBackEndInterface implementation for communication with the frontend.
     */
    public BackEnd(FrontEnd ob) {
        this.frontEnd = ob;
    }

    public NetworkInterface[] getNetworkInterfacesList() {
        return networkInterfacesList;
    }

    /**
     * Gets the list of available network interfaces and populates the frontend with interface information.
     */
    public void start() {

        networkInterfacesList = getDeviceList();

        for (int y = 0; y < networkInterfacesList.length; y++) {
            frontEnd.addInterface("Number " + (y + 1) + " \t " + networkInterfacesList[y].description);
        }
    }


    /**
     * Starts reading packets on the selected interface.
     */
    public void startReadingPackets() {
        selectedInterFace = Integer.parseInt(frontEnd.getSelectedInterface().split(" ")[1]) - 1;

        thread = new SniffThread() {
            @Override
            public Object construct() {
                try {
                    jpcapCaptor = JpcapCaptor.openDevice(networkInterfacesList[selectedInterFace], 65535, false, 20);
                    String selectedFilter = frontEnd.getSelectedFilter();
                    classifyPacketType(jpcapCaptor, selectedFilter);

                    while (frontEnd.getCaptureState()) {
                        packet = jpcapCaptor.getPacket();
                        if (!Objects.isNull(packet)) {
                            Object[] packetRow = packetReceive.receivePacket(packet);
                            if (packetRow.length > 1) {
                                frontEnd.addPacketToTable(packetRow);
                                packetList.add(packet);
                            }
                        }
                    }
                    jpcapCaptor.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return 0;
            }

            public void finished() {
                this.interrupt();
            }
        };
        thread.start();

    }

    /**
     * Sets packet type filters based on user selection.
     *
     * @param cap            JpcapCaptor instance.
     * @param selectedFilter User-selected packet filter (UDP, TCP, ICMP).
     * @throws IOException if an error occurs while setting the filter.
     */
    private void classifyPacketType(JpcapCaptor Cap, String selectedFilter) throws IOException {
        if ("UDP".equals(selectedFilter)) {
            Cap.setFilter("udp", true);
        } else if ("TCP".equals(selectedFilter)) {
            Cap.setFilter("tcp", true);
        } else if ("ICMP".equals(selectedFilter)) {
            Cap.setFilter("icmp", true);
        }
    }


    /**
     * Adds information about the selected interface to the frontend.
     */
    public void addInterfaceInfo() {

        String selectedInterface = frontEnd.getSelectedInterface();
        byte[] r = networkInterfacesList[Integer.parseInt(selectedInterface.split(" ")[1]) - 1].mac_address;

        String[] macAddress = new String[r.length];
        for (int z = 0; z < r.length; z++) {
            macAddress[z] = Integer.toHexString(r[z] & 0xff);
        }
        frontEnd.addInterfaceInfo("\n\n");
        frontEnd.addInterfaceInfo(" Interface MacAddress-->");

        for (String macBit : macAddress) {
            if (macBit != null) {
                if (macBit == macAddress[macAddress.length - 1]) {
                    frontEnd.addInterfaceInfo(macBit);
                } else {
                    frontEnd.addInterfaceInfo(macBit + "-");

                }
            }
        }

        frontEnd.addInterfaceInfo("\n");

        NetworkInterfaceAddress[] net = networkInterfacesList[Integer.parseInt(selectedInterface.split(" ")[1]) - 1].addresses;

        if (net.length != 0) {
            frontEnd.addInterfaceInfo(" Interface Address --> " + String.valueOf(net[0].address).replace("/", "") + "\n");
            frontEnd.addInterfaceInfo(" Interface Subnet --> " + String.valueOf(net[0].subnet).replace("/", "") + "\n");
            frontEnd.addInterfaceInfo(" Interface Broadcast --> " + String.valueOf(net[0].broadcast).replace("/", "") + "\n");
        } else {
            frontEnd.addInterfaceInfo(" Interface Address --> " + "No Connection" + "\n");
            frontEnd.addInterfaceInfo(" Interface Subnet --> " + "No Connection" + "\n");
            frontEnd.addInterfaceInfo(" Interface Broadcast --> " + "No Connection" + "\n");
        }

    }

    /**
     * Stops reading packets and saves them to a file.
     */
    public void stopReadingPackets() {
        frontEnd.setCaptureState(false);
        thread.finished();
        savePackets(packetList);
    }

    /**
     * Saves the captured packets to a file.
     * Overwrites the file if it already exists. If the file does not exist, it is created.
     *
     * @param packets List of packets to be saved.
     */
    private void savePackets(ArrayList<Packet> packets) {
        try {
            fileWriter = new Formatter("src/Captured.txt");
        } catch (FileNotFoundException e) {
            System.exit(0);
        } finally {
            for (Packet pac : packets) {
                fileWriter.format(pac + "\n");
            }
            if (fileWriter != null)
                fileWriter.close();
        }
    }


}