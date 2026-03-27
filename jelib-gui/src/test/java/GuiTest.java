import com.je.core.Console;
import com.je.core.JeLib;
import com.je.gui.GuiUtils;
import com.je.gui.Theme;
import com.je.gui.component.JeComponentBuilder;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class GuiTest {
    @Test
    public void guiUtils() {
        JeLib.console().setEnabled(Console.Type.EXCEPTION, false);
        // Generate a big stack that throws an exception in the bottom.
        try {
            Consumer<Integer> thrower = new Consumer<>() {
                @Override
                public void accept(Integer integer) {
                    if (integer <= 0)
                        // noinspection all
                        Integer.parseInt("a");
                    else
                        accept(--integer);
                }
            };
            thrower.accept(20);
        } catch (NumberFormatException e) {
            GuiUtils.showException(e);
        }
    }

    @Test
    public void gui() throws InterruptedException {
        JFrame jFrame = new JFrame("Test Window");
        JPanel jPanel = new JPanel(new FlowLayout());
        var builder = new JeComponentBuilder(Theme.Day::new);
        var jButton = builder.createComponent(JButton.class, "Hello!").get();
        jButton.addActionListener(_ -> JeLib.console().log("Button is pressed!!!"));
        jPanel.add(jButton);
        jFrame.setContentPane(jPanel);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        while(true){}
    }
}
