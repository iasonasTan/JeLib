package com.picker;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Color color = JColorChooser.showDialog(null, "Pick a color", Color.WHITE);
        if(color!=null){
            final int rgbInteger = color.getRGB();
            var message = new JTextField("Color: "+rgbInteger);
            message.setEditable(false);
            JOptionPane.showMessageDialog(null, message, "Pick a color", JOptionPane.QUESTION_MESSAGE);
        }
    }
}