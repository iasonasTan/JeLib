package com.je.gui.component;

import javax.swing.*;
import java.awt.*;

/**
 * A section on the screen that contains GUI components.
 */
public class JeSection extends JPanel {
    /**
     * Creates a section that applies given layout manager.
     * @param layout Layout to apply to section.
     */
    JeSection(LayoutManager layout) {
        super(layout);
    }

    /**
     * Adds a child to the section.
     * @param node child to add to the section.
     * @return returns self so chain calls are possible.
     */
    public JeSection addChild(JComponent node) {
        add(node);
        return this;
    }

    /**
     * Adds a child to the section.
     * @param node child to add to the section.
     * @param constrain constrain to use
     * @return returns self so chain calls are possible.
     */
    public JeSection addChild(JComponent node, Object constrain) {
        add(node, constrain);
        return this;
    }

    public void addChildren(JComponent... children) {
        for (JComponent child : children) {
            add(child);
        }
    }
}
