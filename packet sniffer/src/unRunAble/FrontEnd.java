package unRunAble;

import graphicalModified.Screen;
import graphicalModified.Button;
import unRunAble.InterFaces.FrontEndInterface;
import unRunAble.InterFaces.FrontEndTestInterface;
import unRunAble.InterFaces.FrontEndToBackEndInterface;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Manages the graphical user interface (GUI) for a packet sniffer application.
 */
public class FrontEnd implements FrontEndTestInterface, FrontEndInterface, FrontEndToBackEndInterface {
    private final JToolBar bar = new JToolBar();
    private JTextArea textAreaPacketInfo;
    private JTextArea textAreaHexaView;
    private JComboBox filterOptions;
    private JComboBox interfaceOptions;
    private JTable packetTable;
    private JTextArea textAreaInterfaceInfo;
    private boolean captureState = false;
    private Screen screen;
    private JLabel labelForInterFace;
    private Button saveButton;
    private Button stopButton;
    private Button captureButton;
    private JLabel labelForFilter;
    private JScrollPane packetTableScrollPane;
    private JScrollPane textAreaHexaScrollPane;
    private JLabel label;
    private JLabel label1;
    private JLabel labelInterfaceInfo;
    private BackEnd backEnd;

    /**
     * Initializes a FrontEnd object with no associated BackEnd.
     */
    public FrontEnd() {
        this.backEnd = null;
    }

    /**
     * Sets the BackEnd for the FrontEnd object.
     *
     * @param backEnd BackEnd object to associate with the FrontEnd.
     */
    public void setBackEnd(BackEnd backEnd) {
        buildGUIComponents();
        this.backEnd = backEnd;
        setGuiComponents();
    }


    /**
     * Constructs and initializes GUI components.
     * Invoked by the constructor.
     */
    private void buildGUIComponents() {


        screen = new Screen("Caught It", 1900, 822, false, "src/PacketSniffer/graphicalAssets/IMG-20220712-WA0005.jpg", "close");


        labelForInterFace = new JLabel("    Interface   ");


        interfaceOptions = new JComboBox();
        interfaceOptions.setPreferredSize(new Dimension(370, 25));
        interfaceOptions.setMaximumSize(interfaceOptions.getPreferredSize());
        interfaceOptions.addItem("Select Active Network Interface");
        interfaceOptions.addActionListener(e -> interfaceOptionsActionPerformed());


        labelForFilter = new JLabel("  Protocol Filter   ");


        filterOptions = new JComboBox();
        filterOptions.setPreferredSize(new Dimension(150, 25));
        filterOptions.setMaximumSize(filterOptions.getPreferredSize());
        filterOptions.addItem("No Filter");
        filterOptions.addItem("UDP");
        filterOptions.addItem("TCP");
        filterOptions.addItem("ICMP");
        filterOptions.setEnabled(false);


        bar.setBounds(0, 0, 1900, 40);


        captureButton = new Button("Capture", false);
        captureButton.setBackground(new Color(0, 0, 200));
        captureButton.addActionListener(e -> startCapture());


        stopButton = new Button("Stop", false);
        stopButton.setBackground(new Color(240, 0, 0));
        stopButton.addActionListener(e -> stopCapture());


        saveButton = new Button("Save", false);
        saveButton.setBackground(new Color(0, 240, 0));
        saveButton.addActionListener(e -> saveCapture());


        packetTable = new JTable();
        packetTable = new javax.swing.JTable() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        packetTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                },
                new String[]{
                        "No.", "Length", "Source", "Destination", "Protocol"
                }
        ) {
            final Class[] types = new Class[]{
                    java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        });
        packetTable.setRowHeight(20);
        packetTable.isEditing();
        packetTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });


        packetTableScrollPane = new JScrollPane();
        packetTableScrollPane.setViewportView(packetTable);
        packetTableScrollPane.setBounds(10, 45, 1505, 450);


        labelInterfaceInfo = new JLabel("Interface Info:");
        labelInterfaceInfo.setFont(new Font("Calibri", Font.BOLD, 18));
        labelInterfaceInfo.setBounds(15, 500, 180, 25);


        textAreaInterfaceInfo = new JTextArea();
        textAreaInterfaceInfo.setEditable(false);
        textAreaInterfaceInfo.setBorder(new LineBorder(Color.BLACK));
        textAreaInterfaceInfo.setBounds(10, 525, 260, 250);


        label = new JLabel("Packet Info:");
        label.setFont(new Font("Calibri", Font.BOLD, 18));
        label.setBounds(305, 500, 180, 25);


        textAreaPacketInfo = new JTextArea();
        textAreaPacketInfo.setEditable(false);
        textAreaPacketInfo.setBorder(new LineBorder(Color.BLACK));
        textAreaPacketInfo.setBounds(300, 525, 480, 250);


        label1 = new JLabel("Hexa Decimal:");
        label1.setFont(new Font("Calibri", Font.BOLD, 18));
        label1.setBounds(800, 500, 180, 25);


        textAreaHexaView = new JTextArea();
        textAreaHexaView.setEditable(false);

        textAreaHexaScrollPane = new JScrollPane();
        textAreaHexaScrollPane.setViewportView(textAreaHexaView);
        textAreaHexaScrollPane.setBounds(790, 525, 725, 250);
    }

    /**
     * Sets up the GUI components.
     * Invoked after associating the BackEnd.
     */
    private void setGuiComponents() {
        addComponentsToToolBar(getGuiBarComponents());
        addComponentsToScreen(getGuiScreenComponents());
    }

    /**
     * Handles the action when selecting a network interface.
     * Disables/enables components based on the selected interface.
     */
    private void interfaceOptionsActionPerformed() {
        if (interfaceOptions.getSelectedItem() != interfaceOptions.getItemAt(0)) {
            textAreaInterfaceInfo.setText(null);
            backEnd.addInterfaceInfo();
            interfaceOptions.setEnabled(false);
            captureButton.setEnabled(true);
            stopButton.setEnabled(false);
            filterOptions.setEnabled(true);

        }
    }

    /**
     * Starts the packet capture process.
     * Disables/enables relevant buttons and components.
     */
    public void startCapture() {
        captureButton.setEnabled(false);
        backEnd.startReadingPackets();
        captureState = true;
        interfaceOptions.setEnabled(false);
        filterOptions.setEnabled(false);
        stopButton.setEnabled(true);
        saveButton.setEnabled(false);
    }

    /**
     * Stops the packet capture process.
     * Disables/enables relevant buttons and components.
     */
    public void stopCapture() {
        captureState = false;
        interfaceOptions.setEnabled(true);
        filterOptions.setEnabled(true);
        captureButton.setEnabled(true);
        saveButton.setEnabled(true);
        stopButton.setEnabled(false);
        backEnd.thread.interrupt();
    }

    /**
     * Handles mouse click events on the packet table.
     * Displays detailed packet information based on the selected row.
     */
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {

        Object obj = packetTable.getModel().getValueAt(packetTable.getSelectedRow(), 0);
        if (PacketContents.rowList.get((int) obj)[4] == "TCP") {

            textAreaPacketInfo.setText(" Packet No: " + PacketContents.rowList.get((int) obj)[0]
                    + "\n Seq No: " + PacketContents.rowList.get((int) obj)[10]
                    + "\n Protocol: " + PacketContents.rowList.get((int) obj)[4]
                    + "\n Source IP: " + PacketContents.rowList.get((int) obj)[2]
                    + "\n Dist IP: " + PacketContents.rowList.get((int) obj)[3]
                    + "\n Length: " + PacketContents.rowList.get((int) obj)[1]
                    + "\n Source Port: " + PacketContents.rowList.get((int) obj)[5]
                    + "\n Dist Port: " + PacketContents.rowList.get((int) obj)[6]
                    + "\n Ack: " + PacketContents.rowList.get((int) obj)[7]
                    + "\n Ack No: " + PacketContents.rowList.get((int) obj)[8]
                    + "\n Sequence No: " + PacketContents.rowList.get((int) obj)[10]
                    + "\n Offset: " + PacketContents.rowList.get((int) obj)[11]
                    + "\n Header: " + PacketContents.rowList.get((int) obj)[12]
                    + "\n Data: " + PacketContents.rowList.get((int) obj)[9]
            );

            try {
                textAreaHexaView.setText(customizeHex(toHexadecimal(textAreaPacketInfo.getText())));
            } catch (UnsupportedEncodingException ex) {
                //
            }
        } else if (PacketContents.rowList.get((int) obj)[4] == "UDP") {
            textAreaPacketInfo.setText(" Packet No: " + PacketContents.rowList.get((int) obj)[0]
                    + "\n Protocol:" + PacketContents.rowList.get((int) obj)[4]
                    + "\n Source IP: " + PacketContents.rowList.get((int) obj)[2]
                    + "\n Dist IP: " + PacketContents.rowList.get((int) obj)[3]
                    + "\n Length: " + PacketContents.rowList.get((int) obj)[1]
                    + "\n Source Port: " + PacketContents.rowList.get((int) obj)[5]
                    + "\n Dist Port: " + PacketContents.rowList.get((int) obj)[6]
                    + "\n Offset: " + PacketContents.rowList.get((int) obj)[8]
                    + "\n Header: " + PacketContents.rowList.get((int) obj)[9]
                    + "\n Data: " + PacketContents.rowList.get((int) obj)[7]
            );

            try {
                textAreaHexaView.setText(customizeHex(toHexadecimal(textAreaPacketInfo.getText())));
            } catch (UnsupportedEncodingException ex) {
                //
            }

        } else if (PacketContents.rowList.get((int) obj)[4] == "ICMP") {
            textAreaPacketInfo.setText(" Packet No: " + PacketContents.rowList.get((int) obj)[0]
                    + "\n Protocol:" + PacketContents.rowList.get((int) obj)[4]
                    + "\n Source IP: " + PacketContents.rowList.get((int) obj)[2]
                    + "\n Dist IP: " + PacketContents.rowList.get((int) obj)[3]
                    + "\n Length: " + PacketContents.rowList.get((int) obj)[1]
                    + "\n Checksum: " + PacketContents.rowList.get((int) obj)[5]
                    + "\n Header: " + PacketContents.rowList.get((int) obj)[6]
                    + "\n Offset: " + PacketContents.rowList.get((int) obj)[7]
                    + "\n Originate TimeStamp: " + PacketContents.rowList.get((int) obj)[8] + "bits"
                    + "\n Receive TimeStamp: " + PacketContents.rowList.get((int) obj)[9] + "bits"
                    + "\n Transmit TimeStamp: " + PacketContents.rowList.get((int) obj)[10] + "bits"
                    + "\n Data: " + PacketContents.rowList.get((int) obj)[11]
            );

            try {
                textAreaHexaView.setText(
                        customizeHex(toHexadecimal(textAreaPacketInfo.getText())));
            } catch (UnsupportedEncodingException ex) {
                //
            }
        }
    }


    /**
     * Stops the packet capture process and saves captured packets.
     */
    public void saveCapture() {
        backEnd.stopReadingPackets();
    }

    /**
     * Converts a string to its hexadecimal representation.
     *
     * @param text Input string to be converted.
     * @return Hexadecimal representation of the input string.
     * @throws UnsupportedEncodingException If the encoding is not supported.
     */
    private String toHexadecimal(String text) throws UnsupportedEncodingException {
        byte[] myBytes = text.getBytes(StandardCharsets.UTF_8);
        return DatatypeConverter.printHexBinary(myBytes);
    }

    /**
     * Customizes the formatting of a hexadecimal string.
     *
     * @param text Hexadecimal string to be formatted.
     * @return Formatted hexadecimal string.
     */
    private String customizeHex(String text) {
        String out;
        out = text.replaceAll("(.{32})", "$1\n");
        return out.replaceAll("..(?!$)", "$0 ");
    }


    /**
     * Retrieves an array of GUI components for the main screen.
     *
     * @return Array of GUI components including toolbar, packet table, labels, text areas, and scroll panes.
     */
    private JComponent[] getGuiScreenComponents() {
        return new JComponent[]{bar, packetTableScrollPane, label, labelInterfaceInfo, textAreaInterfaceInfo, textAreaPacketInfo, label1, textAreaHexaScrollPane};
    }

    /**
     * Retrieves an array of GUI components for the toolbar.
     *
     * @return Array of GUI components including labels, combo boxes, and buttons.
     */
    private JComponent[] getGuiBarComponents() {
        return new JComponent[]{labelForInterFace, interfaceOptions, labelForFilter, filterOptions, captureButton, stopButton, saveButton};
    }

    /**
     * Adds an array of components to the toolbar.
     *
     * @param comp Components to be added to the toolbar.
     */
    private void addComponentsToToolBar(JComponent... comp) {

        for (JComponent jComponent : comp) {
            bar.add(jComponent);
        }
    }

    /**
     * Adds an array of components to the main screen.
     *
     * @param comp Components to be added to the main screen.
     */
    private void addComponentsToScreen(JComponent... comp) {

        for (JComponent jComponent : comp) {
            screen.add(jComponent);
        }
    }

    /**
     * Gets the state of the capture button.
     *
     * @return True if the capture button is enabled, false otherwise.
     */
    public boolean captureButtonState() {
        return captureButton.isEnabled();
    }

    /**
     * Gets the state of the stop button.
     *
     * @return True if the stop button is enabled, false otherwise.
     */
    public boolean stopButtonState() {
        return stopButton.isEnabled();
    }

    /**
     * Gets the state of the save button.
     *
     * @return True if the save button is enabled, false otherwise.
     */
    public boolean saveButtonState() {
        return saveButton.isEnabled();
    }


    /**
     * Gets the state of the interface options combo box.
     *
     * @return True if the interface options combo box is enabled, false otherwise.
     */
    public boolean interfaceOptionsState() {
        return interfaceOptions.isEnabled();
    }

    /**
     * Gets the state of the filter options combo box.
     *
     * @return True if the filter options combo box is enabled, false otherwise.
     */
    public boolean filterOptionsState() {
        return filterOptions.isEnabled();
    }


    /**
     * Gets the count of items in the interface options combo box.
     *
     * @return The count of items in the interface options combo box.
     */
    public int getInterfaceListCount() {
        return interfaceOptions.getItemCount();
    }

    /**
     * Adds a new network interface name to the interface options combo box.
     *
     * @param name The name of the network interface to be added.
     */
    public void addInterface(String name) {
        interfaceOptions.addItem(name);
    }

    /**
     * Sets the selected index of the interface options combo box.
     *
     * @param x The index to be set for the interface options combo box.
     */
    public void setInterface(int x) {
        interfaceOptions.setSelectedIndex(x);
    }

    /**
     * Gets the name of the selected network interface from the interface options combo box.
     *
     * @return The name of the selected network interface.
     */
    public String getSelectedInterface() {
        return Objects.requireNonNull(interfaceOptions.getSelectedItem()).toString();
    }

    /**
     * Gets the selected filter from the filter options combo box.
     *
     * @return The selected filter.
     */
    public String getSelectedFilter() {
        return Objects.requireNonNull(filterOptions.getSelectedItem()).toString();
    }

    /**
     * Appends additional information to the interface information text area.
     *
     * @param info The additional information to be appended.
     */
    public void addInterfaceInfo(String info) {
        textAreaInterfaceInfo.append(info);
    }

    /**
     * Gets the information from the interface information text area.
     *
     * @return The information from the interface information text area.
     */
    public String getInterfaceInfo() {
        return textAreaInterfaceInfo.getText();
    }

    /**
     * Gets the state of the packet capture process.
     *
     * @return True if packet capture is active, false otherwise.
     */
    public boolean getCaptureState() {
        return captureState;
    }

    /**
     * Sets the state of the packet capture process.
     *
     * @param state The state to be set for the packet capture process.
     */
    public void setCaptureState(boolean state) {
        captureState = state;
    }

    /**
     * Adds a packet's information to the packet table.
     *
     * @param packetInfo The information of the packet to be added.
     */
    public void addPacketToTable(Object[] packetInfo) {
        ((javax.swing.table.DefaultTableModel) packetTable.getModel()).addRow(packetInfo);
    }

    /**
     * Sets the visibility of the main screen.
     */
    public void setVisible() {
        screen.setVisible(true);
    }
}

