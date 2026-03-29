package com.je.gui.component;

import javax.swing.*;
import java.awt.*;

public class JeImage extends JLabel {
    /**
     * Default constructor.
     */
    public JeImage() {

    }

    /**
     * Constructs image view and sets view's image to given image.
     * @param image image to set as {@link java.awt.image.BufferedImage}.
     */
    public JeImage(Image image) {
        setImage(image);
    }

    /**
     * Sets view's image to given image.
     * @param image image to set as {@link java.awt.image.BufferedImage}.
     */
    public void setImage(Image image) {
        setIcon(new ImageIcon(image));
    }
}
