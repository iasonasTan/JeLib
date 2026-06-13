package com.je.gui.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class JeButton extends JButton {
    JeButton(){

    }

    @Override
    protected void addImpl(Component comp, Object constraints, int index) {
        super.addImpl(comp, constraints, index);
        comp.setFocusable(false);
    }

    @Override
    public void addActionListener(final ActionListener l) {
        super.addActionListener(l);
        final ChildrenListener listener = new ChildrenListener(this);
        List.of(getComponents()).forEach(c -> c.addMouseListener(listener));
    }

    private record ChildrenListener(JeButton mJeButton) implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            mJeButton.doClick();
        }

        @Override public void mousePressed (MouseEvent mouseEvent) {}
        @Override public void mouseReleased(MouseEvent mouseEvent) {}
        @Override public void mouseEntered (MouseEvent mouseEvent) {}
        @Override public void mouseExited  (MouseEvent mouseEvent) {}
    }
}
