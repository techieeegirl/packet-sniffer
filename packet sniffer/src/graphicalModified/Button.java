package graphicalModified;


import javax.swing.*;

/**
 * The Button class represents a JButton with customizable features.
 */
public final class Button extends JButton {

    /**
     * Constructor to initialize the Button object with specified properties.
     *
     * @param text    The text to be displayed on the button.
     * @param enabled A boolean indicating whether the button is initially enabled.
     */
    public Button(String text, boolean enabled) {
        setText(text);
        this.setFocusPainted(false);
        setEnabled(enabled);
    }
}
