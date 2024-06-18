package unRunAble.InterFaces;


/**
 * FrontEnd Interface for Backend When it needs to make changes to the FrontEnd
 */

public interface FrontEndToBackEndInterface {
    void addInterface(String name);

    String getSelectedInterface();

    String getSelectedFilter();

    void addInterfaceInfo(String info);

    boolean getCaptureState();

    void setCaptureState(boolean state);

    void addPacketToTable(Object[] packetInfo);

}
