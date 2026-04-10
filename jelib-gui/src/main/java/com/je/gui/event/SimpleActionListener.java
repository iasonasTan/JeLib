package com.je.gui.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public interface SimpleActionListener extends ActionListener {
    @Override
    default void actionPerformed(ActionEvent actionEvent) {
        actionPerformed();
    }

    void actionPerformed();
}
