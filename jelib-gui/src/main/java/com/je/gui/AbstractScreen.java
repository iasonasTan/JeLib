package com.je.gui;

import com.je.gui.component.JeComponentBuilder;
import com.je.gui.component.JeSection;
import com.je.gui.layout.VerticalFlowLayout;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractScreen extends JeSection implements Screen {
    private static JFrame sFrame;

    public AbstractScreen(JeComponentBuilder builder) {
        super(new VerticalFlowLayout());
        builder.styleSection(this);
    }

    @Override
    public void setVisible() {
        if(sFrame == null) {
            sFrame = new JFrame();
            sFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            sFrame.setLocationRelativeTo(null);
            sFrame.setVisible(true);
        }
        sFrame.setContentPane(this);
        sFrame.setIconImage(icon());
        sFrame.setTitle(title());

        sFrame.revalidate();
        sFrame.repaint();
    }

    protected abstract String title();
    protected abstract Image icon();
}