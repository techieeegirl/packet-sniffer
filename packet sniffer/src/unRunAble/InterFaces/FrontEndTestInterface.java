package unRunAble.InterFaces;

import unRunAble.BackEnd;

/**
 * FrontEnd Interface for testing the correct functionality of the FrontEnd
 * For Example:
 * When test changes some values in the FrontEnd and checks if the BackEnd has the correct values
 */
public interface FrontEndTestInterface {

    void setBackEnd(BackEnd backEnd);

    void addInterface(String name);

    void setInterface(int x);

    String getInterfaceInfo();

    boolean getCaptureState();

    void startCapture();

    void stopCapture();

    void saveCapture();

    boolean captureButtonState();

    boolean stopButtonState();

    boolean saveButtonState();

    boolean interfaceOptionsState();

    boolean filterOptionsState();

    int getInterfaceListCount();
}
