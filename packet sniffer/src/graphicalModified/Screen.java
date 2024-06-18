package graphicalModified;

import javax.swing.*;
import java.util.Arrays;

/**
 * The Screen class represents a JFrame with customizable features.
 */
public final class Screen extends JFrame {

    /**
     * Constructor to initialize the Screen object with specified properties.
     *
     * @param title          The title of the screen.
     * @param width          The width of the screen.
     * @param height         The height of the screen.
     * @param resize         A boolean indicating whether the screen is resizable.
     * @param imageAddress   The file path of the image to be set as the screen's icon.
     * @param exitButtonDoes A string indicating the action to be taken when the exit button is clicked.
     */
    public Screen(String title, int width, int height, boolean resize,
                  String imageAddress, String exitButtonDoes) {
        setExitButton(exitButtonDoes);
        this.setResizable(resize);
        this.setSize(width, height);
        this.setTitle(title);
        ImageIcon image = new ImageIcon(imageAddress);
        this.setIconImage(image.getImage());
        this.setLayout(null);
    }

    /**
     * Sets the behavior of the exit button based on the specified action.
     *
     * @param exitButton A string indicating the action to be taken when the exit button is clicked.
     */
    public void setExitButton(String exitButton) {
        String[] actionList = {"close", "hide", "nothing"};
        if (Arrays.asList(actionList).contains(exitButton.toLowerCase())) {
            switch (exitButton) {
                case "close" -> this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                case "hide" -> this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                case "nothing" -> this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        } else
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
