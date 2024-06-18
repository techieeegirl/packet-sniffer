package unRunAble;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import unRunAble.InterFaces.FrontEndTestInterface;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Testing Strategy:
 * <p>
 * The testing strategy for the BackEnd class involves testing various aspects of its interaction with the FrontEnd,
 * including the initialization of GUI components, thread handling, and packet reading functionality.
 * <p>
 * The tests use a mock FrontEnd object for testing, and various methods are invoked to simulate interactions with the
 * GUI and check the expected states. Assertions are used to verify that the actual states match the expected states.
 */
class BackEndTest {

    FrontEndTestInterface mockGui;
    BackEnd backend;

    @BeforeEach
    void setUp() {
        mockGui = new FrontEnd();
        backend = new BackEnd((FrontEnd) mockGui);

    }

    @AfterEach
    void tearDown() {
        mockGui = null;
        backend = null;
    }

    /**
     * Start Adding Info to GUI Interface List:
     *  Testing if the networkInterfacesList is initialized and the GUI is updated with interface information.
     *  */
    @Test
    void testStartAddingInfoToGuiInterfaceList() {

        mockGui.setBackEnd(backend);
        backend.start();
        // Test if the networkInterfacesList is initialized and the GUI is updated
        assertNotNull(backend.getNetworkInterfacesList());


    }

    /**
     * Start Adding Info to GUI Interface List Count:
     * Testing if the networkInterfacesList is initialized, and the count of interfaces in the GUI is greater than zero.
     * */
    @Test
    void testStartAddingInfoToGuiInterfaceListCount() {

        mockGui.setBackEnd(backend);
        backend.start();
        // Test if the networkInterfacesList is initialized and the GUI is updated
        assertTrue(mockGui.getInterfaceListCount() > 0);

    }

    /**
     * Start Reading Packets Thread:
     * Testing the initialization of the packet reading thread when starting packet capture.
     * Checking if the thread is started and the capture state is set to true.
     * */
    @Test
    void testStartReadingPacketsThread() {
        mockGui.setBackEnd(backend);
        backend.start();

        mockGui.addInterface("Number 1\tMock Interface");
        mockGui.setInterface(2);
        // Simulate starting packet reading

        mockGui.startCapture();

//        // Check if the thread is started
        assertNotNull(backend.thread);
    }

    /**
     * Start Reading Packets Capture State:
     * Checking if the capture state is set to true after starting packet capture.
     */
    @Test
    void testStartReadingPacketsCaptureState() {
        mockGui.setBackEnd(backend);
        backend.start();

        mockGui.addInterface("Number 1\tMock Interface");
        mockGui.setInterface(2);
        // Simulate starting packet reading

        mockGui.startCapture();

//        // Check if the capture state is true
        assertTrue(mockGui.getCaptureState());
    }

    /**
     * Add Interface Info:
     * Simulating the addition of interface information and checking if it is appended to the text area in the GUI.
     * */
    @Test
    void testAddInterfaceInfo() {

        mockGui.setBackEnd(backend);
        backend.start();

        // Simulate adding interface info
        mockGui.addInterface("Number 1\tMock Interface");
        mockGui.setInterface(2);
        backend.addInterfaceInfo();

        // Check if the interface info is appended to the text area
        assertNotNull(mockGui.getInterfaceInfo());
    }

    /**
     * Stop Reading Packets:
     * Simulating the start and stop of packet reading and checking if the capture state is set to false.
     * */
    @Test
    void testStopReadingPackets() {

        mockGui.setBackEnd(backend);
        backend.start();
        // Simulate starting packet reading
        mockGui.setInterface(2);
        mockGui.startCapture();

        // Simulate stopping packet reading

        mockGui.stopCapture();


        // Check if the capture state is false
        assertFalse(mockGui.getCaptureState());

    }
}