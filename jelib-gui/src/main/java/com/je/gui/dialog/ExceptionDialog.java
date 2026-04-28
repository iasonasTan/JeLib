package com.je.gui.dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

import com.je.core.JeLib;
import com.je.core.util.Utils;
import com.je.gui.GuiUtils;
import com.je.io.IOUtils;

public class ExceptionDialog extends AbstractDialog<Throwable> {
    public static final String PROPERTY_CONTACT = "jelib-gui.dialog.exception.contact";

    /**
     * Shows {@code JOptionPane} with exception info.
     * @param exception exception to show its info.
     */
    @Override
    public void showMessage(Throwable exception) {
        JeLib.console().log("JeLib-gui/com.je.gui.GuiUtils#showException(Throwable) handled the below exception: ");
        JeLib.console().exception(exception);

        String messageText = String.format("""
                        The application has a bug "%s: %s".
                        
                        Stacktrace:
                        %s
                        Please send this to the developer (%s)
                        """,
                exception.getClass().getName(),
                exception.getMessage()==null?"No message details.":exception.getLocalizedMessage(),
                Utils.stacktraceToString(exception.getStackTrace(),null),
                properties.getOrDefault(PROPERTY_CONTACT, "No Contact.")
        );
        Image image = IOUtils.loadImage("/alert.png", GuiUtils.class);
        image = image.getScaledInstance(100, 100, BufferedImage.SCALE_SMOOTH);

        JTextArea jTextArea = new JTextArea();
        jTextArea.setEditable(true);
        jTextArea.setText(messageText);
        jTextArea.setEditable(false);

        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setVerticalScrollBarPolicy  (JScrollPane.VERTICAL_SCROLLBAR_ALWAYS  );
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane.setPreferredSize(new Dimension(700, 600));

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(jScrollPane);

        JOptionPane.showMessageDialog(null, panel, "Unexpected Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(image));
    }
}
