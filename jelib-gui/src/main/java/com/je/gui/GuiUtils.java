package com.je.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import com.je.core.JeLib;
import com.je.core.util.Utils;
import com.je.media.Resources;

/**
 * Utilities related to GUI stuff.
 * e.g. {@link #showException(Throwable)}
 */
public final class GuiUtils {
    /**
     * Shows {@code JOptionPane} with exception info.
     * @param exception exception to show its info.
     */
    public static void showException(Throwable exception) {
        JeLib.console().log("JeLib-gui/com.je.gui.GuiUtils#showException(Throwable) handled the below exception: ");
        JeLib.console().exception(exception);

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
        Image image = Resources.loadImage("/alert.png", GuiUtils.class);
        image = image.getScaledInstance(100, 100, BufferedImage.SCALE_SMOOTH);

        JTextArea jTextArea = new JTextArea();
        jTextArea.setEditable(true);
        jTextArea.setText(messageText);

        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setVerticalScrollBarPolicy  (JScrollPane.VERTICAL_SCROLLBAR_ALWAYS  );
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane.setPreferredSize(new Dimension(700, 700));

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(jScrollPane);
        JOptionPane.showMessageDialog(null, panel, "Unexpected Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(image));
    }

    /**
     * A private constructor prevents instantiation.
     */
    private GuiUtils(){
    }
}
