package unRunAble;


import jpcap.packet.ICMPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;


import java.util.ArrayList;
import java.util.List;


/**
 * This class represents the contents of packets and provides methods to receive and process packet information.
 */
public class PacketContents {

    /**
     * List to store packet information as arrays.
     */
    public static List<Object[]> rowList = new ArrayList<Object[]>();

    /**
     * Counter to track the packet number.
     */
    private int packetNo = 0;


    /**
     * Receives a packet, if of type udp tcp or icmp, processes its information, and adds it to the row list .
     *
     * @param packet The packet to be processed.
     * @return An array representing packet information, or an empty array if the packet type is not supported.
     */
    public Object[] receivePacket(Packet packet) {
        if (packet instanceof TCPPacket) {
            TCPPacket tcp = (TCPPacket) packet;
            Object[] row = {packetNo, tcp.length, tcp.src_ip, tcp.dst_ip, "TCP"};
            rowList.add(new Object[]{packetNo, tcp.length, tcp.src_ip, tcp.dst_ip, "TCP", tcp.src_port, tcp.dst_port,
                    tcp.ack, tcp.ack_num, tcp.data, tcp.sequence, tcp.offset, tcp.header});
            packetNo++;
            return row;
        } else if (packet instanceof UDPPacket) {
            UDPPacket udp = (UDPPacket) packet;
            Object[] row = {packetNo, udp.length, udp.src_ip, udp.dst_ip, "UDP"};
            rowList.add(new Object[]{packetNo, udp.length, udp.src_ip, udp.dst_ip, "UDP", udp.src_port, udp.dst_port,
                    udp.data, udp.offset, udp.header});
            packetNo++;
            return row;
        } else if (packet instanceof ICMPPacket) {
            ICMPPacket icmp = (ICMPPacket) packet;
            Object[] row = {packetNo, icmp.length, icmp.src_ip, icmp.dst_ip, "ICMP"};
            rowList.add(new Object[]{packetNo, icmp.length, icmp.src_ip, icmp.dst_ip, "ICMP", icmp.checksum, icmp.header,
                    icmp.offset, icmp.orig_timestamp, icmp.recv_timestamp, icmp.trans_timestamp, icmp.data});
            packetNo++;
            return row;
        }
        return new Object[0];
    }
}