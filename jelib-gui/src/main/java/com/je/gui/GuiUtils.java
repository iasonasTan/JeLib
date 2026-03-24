package com.je.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import com.je.core.Utils;

import static com.je.io.IOUtils.loadImage;

public final class GuiUtils {
    /**
     * Method loads image from given {@code class} and given {@code path}.<br>
     * @param path  path to load image from.
     * @param clazz class to get resource from.
     * @return returns loaded or fallback image as {@link BufferedImage}.
     */
    public static void showException(Exception exception) {
        String messageText = String.format("""
                        The application has a bug "%s: %s".
                        
                        Stacktrace:
                        %s
                        Please send this to the developer (iasonas.tan@gmail.com)
                        """,
                exception.getClass().getName(),
                exception.getMessage()==null?"No message details.":exception.getLocalizedMessage(),
                Utils.stacktraceToString(exception.getStackTrace(), null)
        );
        Image image = loadImage("/alert.png", GuiUtils.class);
        image = image.getScaledInstance(100, 100, BufferedImage.SCALE_SMOOTH);
        JTextArea jTextArea = new JTextArea();
        jTextArea.setEditable(true);
        jTextArea.setText(messageText);
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(jTextArea);
        JOptionPane.showMessageDialog(null, panel, "Unexpected Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(image));
    }

    private GuiUtils(){
    }
}
