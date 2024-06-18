package abstractClasses;
/**
 * This is a simple swing worker thread class which is available on the official oracle website
 */

import javax.swing.*;

/**
 * The SniffThread class provides a simple framework for creating Swing worker threads.
 * This abstract class encapsulates the basic structure for executing tasks in a separate thread,
 * ensuring proper Swing concurrency and handling interruptions.
 */

public abstract class SniffThread {

    private final ThreadVar threadVar;
    private Object value;
    private Thread thread;

    /**
     * Constructs a SniffThread object.z
     * Initializes the threadVar and sets up the runnables for the construction and finishing phases.
     */
    public SniffThread() {
        final Runnable doFinished = new Runnable() {
            public void run() {
                finished();
            }
        };

        Runnable doConstruct = new Runnable() {
            public void run() {
                try {
                    setValue(construct());
                } finally {
                    threadVar.clear();
                }
                SwingUtilities.invokeLater(doFinished);
            }
        };
        Thread t = new Thread(doConstruct);
        threadVar = new ThreadVar(t);
    }

    /**
     * Abstract method to be implemented by subclasses to define the task to be performed in the background thread.
     *
     * @return The result of the background task.
     */
    public abstract Object construct();

    /**
     * Protected method to retrieve the current value set by the background task.
     *
     * @return The value set by the background task.
     */
    protected synchronized Object getValue() {
        return value;
    }

    /**
     * Private method to set the value produced by the background task.
     *
     * @param x The value produced by the background task.
     */
    private synchronized void setValue(Object x) {
        value = x;
    }

    /**
     * Method called when the background task is finished. Subclasses can override this method to perform additional actions.
     */
    public void finished() {
        // Default implementation does nothing.
    }

    /**
     * Interrupts the background thread associated with this SniffThread object.
     */
    public void interrupt() {
        Thread t = threadVar.get();
        if (t != null) {
            t.interrupt();
        }
        threadVar.clear();
    }

    /**
     * Blocks until the background thread completes its task and returns the result.
     *
     * @return The result of the background task.
     */
    public Object get() {
        while (true) {
            Thread t = threadVar.get();
            if (t == null) {
                return getValue();
            }
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
    }

    /**
     * Starts the background thread associated with this SniffThread object.
     */
    public void start() {
        Thread t = threadVar.get();
        if (t != null) {
            t.start();
        }
    }

    /**
     * A helper class to hold the reference to the background thread.
     */
    private static class ThreadVar {

        private Thread thread;

        /**
         * Constructs a ThreadVar object with the specified thread.
         *
         * @param t The thread to be stored.
         */
        ThreadVar(Thread t) {
            thread = t;
        }

        /**
         * Retrieves the stored thread.
         *
         * @return The stored thread.
         */
        synchronized Thread get() {
            return thread;
        }

        /**
         * Clears the stored thread.
         */
        synchronized void clear() {
            thread = null;
        }
    }
}