import com.je.core.Console;
import com.je.core.JeLib;
import com.je.core.util.Bundle;
import com.je.gui.AbstractScreen;
import com.je.gui.GuiUtils;
import com.je.gui.Screen;
import com.je.gui.component.*;
import com.je.gui.configuration.DefaultConfigurationLoader;
import com.je.gui.layout.VerticalFlowLayout;
import com.je.io.IOUtils;
import com.je.io.configuration.Configuration;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
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
    public void gui() {
        Configuration.init("library-test");
        JeComponentBuilder builder = new JeComponentBuilder(new DefaultConfigurationLoader());
        TestScreen screen = new TestScreen(builder);
        screen.setVisible();
        while(true){}
    }

    static final class TestScreen2 extends AbstractScreen {
        public TestScreen2(JeComponentBuilder builder) {
            super(builder);
            setLayout(new GridBagLayout());
            JeSection section = builder.createSection(new VerticalFlowLayout());
            JeText text = builder.createTextComponent(JeText.class, "This is the second screen...").get();
            section.add(text);
            JeImage image = builder.createImage(IOUtils.loadImage("/image2.png", GuiTest.class));
            section.add(image);
            addChild(section, new GridBagConstraints());
        }

        @Override
        protected String title() { return "Test Screen 2"; }

        @Override
        protected Image icon() { return IOUtils.loadImage("/images.png", GuiUtils.class); }
    }

    static final class TestScreen extends AbstractScreen implements ActionListener {
        public TestScreen(JeComponentBuilder builder) {
            super(builder);
            setLayout(new GridBagLayout());
            JeSection section = builder.createSection(new VerticalFlowLayout());
            section.setPreferredSize(new Dimension(230, 100));
            var jButton = builder.createTextComponent(JeButton.class, "Hello!").get();
            jButton.addActionListener(this);
            section.add(jButton);
            var text = builder.createTextComponent(JeText.class, "This is the first screen...").get();
            section.add(text);
            add(section, new GridBagConstraints());
        }

        @Override
        protected String title() { return "Test Screen"; }

        @Override
        protected Image icon() { return IOUtils.loadImage("/images.png", GuiTest.class); }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JeLib.console().log("Button is pressed!!!");
            new TestScreen2(new JeComponentBuilder(new DefaultConfigurationLoader())).setVisible();
            Bundle guiS = Configuration.loadBundle("gui.properties");
            Bundle newS = Bundle.builder()
                    .put("night_theme", !guiS.getBoolean("night_theme", false))
                    .build();
            Configuration.storeBundle("gui.properties", newS);
        }
    }
}
