package com.je.gui.test;

import com.je.core.Console;
import com.je.core.JeLib;
import com.je.gui.AbstractScreen;
import com.je.gui.GuiUtils;
import com.je.gui.Theme;
import com.je.gui.component.*;
import com.je.gui.configuration.DefaultConfigurationManager;
import com.je.gui.dialog.ExceptionDialog;
import com.je.gui.layout.CenterLayout;
import com.je.gui.layout.VerticalFlowLayout;
import com.je.gui.xml.Loader;
import com.je.io.IOUtils;
import com.je.io.configuration.Configuration;
import org.junit.Test;

import java.awt.*;
import java.util.function.Consumer;

public class GuiTest {
    @Test
    public void guiUtils() {
        GuiUtils.exceptionDialog().putProperty(ExceptionDialog.PROPERTY_CONTACT, "developer+email@email.com");
        JeLib.console().setEnabled(Console.Type.EXCEPTION, false);
        // Generate a big stack that throws an exception in the bottom.
        try {
            Consumer<Integer> thrower = new Consumer<>() {
                @Override
                public void accept(Integer integer) {
                    if (integer <= 0)
                        Integer.parseInt("a");
                    else
                        accept(--integer);
                }
            };
            thrower.accept(20);
        } catch (NumberFormatException e) {
            // Catch and show exception
            GuiUtils.exceptionDialog().showMessage(e);
        }
        JeLib.console().setEnabled(Console.Type.EXCEPTION, true);
    }

    // Loader doesn't work, TODO: Fix Loader.
    @Test
    public void loader() {
        if(1 < 2) {
            return;
        }

        Configuration.init("library-test");
        JeGuiBuilder builder = new JeGuiBuilder(DefaultConfigurationManager.getDefaultLoader());
        Loader loader = new Loader(builder);
        JeSection section = loader.loadFrom(getClass().getResourceAsStream("/screen1.xml"));
        section.setPreferredSize(new Dimension(1000, 800));
        AbstractScreen screen = new AbstractScreen(section){
            /**
             * Method used to get screen title.
             * @return Returns title of screen as {@code string}.
             */
            protected String title() {
                return "Screen 1 - Test app";
            }

            /**
             * Method used to get screen icon.
             * @return Returns icon of screen as {@link Image}.
             */
            protected Image icon() {
                return IOUtils.loadImage("/images.png", GuiTest.class);
            }
        };
        screen.setVisible();
        JeLib.sleep(5_000); // 5 SEC
    }

    @Test
    public void gui() {
        final class TestScreen extends AbstractScreen {
            /**
             * Constructs an AbstractScreen and styles it based on given {@link JeGuiBuilder}.
             *
             * @param builder builder used to style self.
             */
            public TestScreen(JeGuiBuilder builder) {
                super(builder);
                setLayout(new CenterLayout());

                JeSection section = builder.createSection(new VerticalFlowLayout());

                String txt = "Lorem ipsum dolor sit amet.\nI don't know the rest but I will type something else in English.\nI wasted too much time in this library but I'm gonna waste however it needs to be a good library.";
                JeText text = builder.createTextComponent(JeText.class, txt).orElseThrow(RuntimeException::new);

                JeInput input = builder.createComponent(JeInput.class).orElseThrow(RuntimeException::new);
                input.setPreferredSize(new Dimension(100, 100));

                JeButton btn = builder.createTextComponent(JeButton.class, "Exit").orElseThrow(RuntimeException::new);
                btn.addActionListener(ignore -> {
                    AbstractScreen.dispose();
                    System.exit(0);
                });

                section.addChildren(text, input, btn);

                addChildren(section);
            }

            /**
             * Method used to get screen title.
             *
             * @return Returns title of screen as {@code string}.
             */
            @Override
            protected String title() {
                return "TestScreen - JeLib:jelib-gui";
            }

            /**
             * Method used to get screen icon.
             *
             * @return Returns icon of screen as {@link Image}.
             */
            @Override
            protected Image icon() {
                return IOUtils.loadImage("/images.png", getClass());
            }
        }
        JeGuiBuilder builder = new JeGuiBuilder(Theme.Night::new);
        AbstractScreen testScreen = new TestScreen(builder);
        testScreen.setVisible();
        JeLib.sleep(5_000);
    }
}
