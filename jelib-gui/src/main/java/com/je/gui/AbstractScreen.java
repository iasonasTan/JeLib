package com.je.gui;

import com.je.gui.component.JeGuiBuilder;
import com.je.gui.component.JeSection;
import com.je.gui.layout.VerticalFlowLayout;

import javax.swing.*;
import java.awt.*;

/**
 * Abstract screen is a screen in the application.
 * It implements {@link Screen} interface and extend {@link JeSection}.
 */
public abstract class AbstractScreen implements Screen {
    /**
     * Frame to show screens.
     */
    private static JFrame sFrame;

    /**
     * Disposes frame.
     * {@link #setVisible()} will create a new window next time it will be called.
     */
    public static void dispose() {
        sFrame.dispose();
    }

    private final JeSection mSection;

    /**
     * Constructs an AbstractScreen and styles it based on given {@link JeGuiBuilder}.
     * @param builder builder used to style self.
     */
    public AbstractScreen(JeGuiBuilder builder) {
        mSection = builder.createSection(new VerticalFlowLayout(10, 10));
    }

    public void setLayout(LayoutManager layout) {
        mSection.setLayout(layout);
    }

    public void addChildren(JComponent... children) {
        mSection.addChildren(children);
    }

    public void addChild(JComponent node, Object constrain) {
        mSection.addChild(node, constrain);
    }

    /**
     * Sets the screen visible and set the previous
     * screen invisible if it exists.
     */
    @Override
    public void setVisible() {
        if(sFrame == null) {
            sFrame = new JFrame();
            sFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            sFrame.setLocationRelativeTo(null);
            sFrame.setVisible(true);
        }
        sFrame.setContentPane(mSection);
        sFrame.setIconImage(icon());
        sFrame.setTitle(title());

        sFrame.revalidate();
        sFrame.repaint();
    }

    /**
     * Method used to get screen title.
     * @return Returns title of screen as {@code string}.
     */
    protected abstract String title();

    /**
     * Method used to get screen icon.
     * @return Returns icon of screen as {@link Image}.
     */
    protected abstract Image icon();
}