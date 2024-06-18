package unRunAble;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import unRunAble.InterFaces.FrontEndTestInterface;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Strategy:
 * <p>
 * The testing strategy for the FrontEnd class involves testing various states and behaviors related to the user interface
 * and interaction with the BackEnd. The class implements FrontEndTestInterface, which defines the interactions with the
 * BackEnd. The tests cover the following scenarios:
 * <p>
 * The tests use a mock BackEnd object for testing, and various methods are invoked to simulate user interactions and check
 * the expected states of components. Assertions are used to verify that the actual states match the expected states.
 */

class FrontEndTest {
    FrontEndTestInterface frontEnd;

    @BeforeEach
    void setUp() {
        frontEnd = new FrontEnd();
    }

    @AfterEach
    void tearDown() {
        frontEnd = null;
    }

    /**
     * Interface Options:
     *  Testing the addition of interfaces, setting the current interface, and checking the state of interface options.
     *  Verifying that interface information is added and components are updated appropriately.
     * */
    @Test
    void testInterfaceOptionsInterfaceInfo() {
        // Create a mock BackEnd object for testing
        BackEnd mockBackEnd = new BackEnd((FrontEnd) frontEnd);
        frontEnd.setBackEnd(mockBackEnd);
        mockBackEnd.start();

        // Simulate selecting an interface
        frontEnd.addInterface("Mock Interface");
        frontEnd.setInterface(2);


        // Check if interface info is added and components are updated
        assertNotNull(frontEnd.getInterfaceInfo());
    }

    @Test
    void testInterfaceOptionsInterfaceOptionsState() {
        // Create a mock BackEnd object for testing
        BackEnd mockBackEnd = new BackEnd((FrontEnd) frontEnd);
        frontEnd.setBackEnd(mockBackEnd);
        mockBackEnd.start();

        // Simulate selecting an interface
        frontEnd.addInterface("Mock Interface");
        frontEnd.setInterface(2);


        // Check if interface info is added and components are updated
        assertFalse(frontEnd.interfaceOptionsState());
    }

    /**
     * Filter Options:
     * Testing the state of filter options when selecting an interface.
     *  */
    @Test
    void testInterfaceOptionsFilterOptionState() {
        // Create a mock BackEnd object for testing
        BackEnd mockBackEnd = new BackEnd((FrontEnd) frontEnd);
        frontEnd.setBackEnd(mockBackEnd);
        mockBackEnd.start();

        // Simulate selecting an interface
        frontEnd.addInterface("Mock Interface");
        frontEnd.setInterface(2);


        // Check if interface info is added and components are updated
        assertTrue(frontEnd.filterOptionsState());
    }


    /**
     * Capture Button:
     * Testing the state of interface options and filter options when starting packet capture.
     *  */
    @Test
    void testCaptureButtonInterfaceOptionState() {
        // Create a mock BackEnd object for testing
        BackEnd mockBackEnd = new BackEnd((FrontEnd) frontEnd);
        frontEnd.setBackEnd(mockBackEnd);
        mockBackEnd.start();
        // Simulate clicking the capture button
        frontEnd.setInterface(2);
        frontEnd.startCapture();

        // Check if packet reading is started and components are updated
        assertFalse(frontEnd.interfaceOptionsState());

    }

    @Test
    void testCaptureButtonFilterOptionState() {
        // Create a mock BackEnd object for testing
        BackEnd mockBackEnd = new BackEnd((FrontEnd) frontEnd);
        frontEnd.setBackEnd(mockBackEnd);
        mockBackEnd.start();
        // Simulate clicking the capture button
        frontEnd.setInterface(2);
        frontEnd.startCapture();

        // Check if packet reading is started and components are updated
        assertFalse(frontEnd.filterOptionsState());
    }


    /**
     * Stop Button:
     * Testing the state of interface options, filter options, and capture state when stopping packet capture.
     *  */
    @Test
    void testStopButtonCaptureSate() {
        BackEnd mockBackEnd = new BackEnd((FrontEnd) frontEnd);
        frontEnd.setBackEnd(mockBackEnd);
        mockBackEnd.start();
        // Simulate clicking the capture button
        frontEnd.setInterface(2);
        frontEnd.startCapture();

        // Simulate clicking the stop button
        frontEnd.stopCapture();

        // Check if packet reading is stopped and components are updated
        assertFalse(frontEnd.getCaptureState());

    }

    @Test
    void testStopButtonInterfaceOptionState() {
        BackEnd mockBackEnd = new BackEnd((FrontEnd) frontEnd);
        frontEnd.setBackEnd(mockBackEnd);
        mockBackEnd.start();
        // Simulate clicking the capture button
        frontEnd.setInterface(2);
        frontEnd.startCapture();

        // Simulate clicking the stop button
        frontEnd.stopCapture();

        // Check if packet reading is stopped and components are updated
        assertTrue(frontEnd.interfaceOptionsState());
    }

    @Test
    void testStopButtonFilterOptionState() {
        BackEnd mockBackEnd = new BackEnd((FrontEnd) frontEnd);
        frontEnd.setBackEnd(mockBackEnd);
        mockBackEnd.start();
        // Simulate clicking the capture button
        frontEnd.setInterface(2);
        frontEnd.startCapture();

        // Simulate clicking the stop button
        frontEnd.stopCapture();

        // Check if packet reading is stopped and components are updated
        assertTrue(frontEnd.filterOptionsState());
    }


    /**
     * Save Button:
     * Testing the state of interface options, filter options, capture button, and stop button when saving captured data.
     * */
    @Test
    void testSaveButtonCaptureState() {
        BackEnd mockBackEnd = new BackEnd((FrontEnd) frontEnd);
        frontEnd.setBackEnd(mockBackEnd);
        mockBackEnd.start();
        // Simulate clicking the capture button
        frontEnd.setInterface(2);

        frontEnd.startCapture();
        // Simulate clicking the stop button
        frontEnd.stopCapture();

        // Simulate clicking the save button
        frontEnd.saveCapture();

        // Check if packet reading is stopped and components are updated
        assertFalse(frontEnd.getCaptureState());
    }

    @Test
    void testSaveButtonInterfaceOptionState() {
        BackEnd mockBackEnd = new BackEnd((FrontEnd) frontEnd);
        frontEnd.setBackEnd(mockBackEnd);
        mockBackEnd.start();
        // Simulate clicking the capture button
        frontEnd.setInterface(2);

        frontEnd.startCapture();
        // Simulate clicking the stop button
        frontEnd.stopCapture();

        // Simulate clicking the save button
        frontEnd.saveCapture();

        // Check if packet reading is stopped and components are updated

        assertTrue(frontEnd.interfaceOptionsState());


    }

    @Test
    void testSaveButtonFilterOptionState() {
        BackEnd mockBackEnd = new BackEnd((FrontEnd) frontEnd);
        frontEnd.setBackEnd(mockBackEnd);
        mockBackEnd.start();
        // Simulate clicking the capture button
        frontEnd.setInterface(2);

        frontEnd.startCapture();
        // Simulate clicking the stop button
        frontEnd.stopCapture();

        // Simulate clicking the save button
        frontEnd.saveCapture();

        // Check if packet reading is stopped and components are updated
        assertTrue(frontEnd.filterOptionsState());


    }

    @Test
    void testSaveButtonCaptureButtonState() {
        BackEnd mockBackEnd = new BackEnd((FrontEnd) frontEnd);
        frontEnd.setBackEnd(mockBackEnd);
        mockBackEnd.start();
        // Simulate clicking the capture button
        frontEnd.setInterface(2);

        frontEnd.startCapture();
        // Simulate clicking the stop button
        frontEnd.stopCapture();

        // Simulate clicking the save button
        frontEnd.saveCapture();

        // Check if packet reading is stopped and components are updated
        assertTrue(frontEnd.captureButtonState());


    }

    @Test
    void testSaveButtonSaveButtonState() {
        BackEnd mockBackEnd = new BackEnd((FrontEnd) frontEnd);
        frontEnd.setBackEnd(mockBackEnd);
        mockBackEnd.start();
        // Simulate clicking the capture button
        frontEnd.setInterface(2);

        frontEnd.startCapture();
        // Simulate clicking the stop button
        frontEnd.stopCapture();

        // Simulate clicking the save button
        frontEnd.saveCapture();

        // Check if packet reading is stopped and components are updated
        assertTrue(frontEnd.saveButtonState());
        assertFalse(frontEnd.stopButtonState());
    }

    @Test
    void testSaveButtonStopButtonState() {
        BackEnd mockBackEnd = new BackEnd((FrontEnd) frontEnd);
        frontEnd.setBackEnd(mockBackEnd);
        mockBackEnd.start();
        // Simulate clicking the capture button
        frontEnd.setInterface(2);

        frontEnd.startCapture();
        // Simulate clicking the stop button
        frontEnd.stopCapture();

        // Simulate clicking the save button
        frontEnd.saveCapture();

        // Check if packet reading is stopped and components are updated
        assertFalse(frontEnd.stopButtonState());

    }
}