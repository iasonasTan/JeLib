package com.je.gui.component;

import com.je.core.JeLib;
import com.je.core.util.Bundle;
import com.je.gui.Theme;
import com.je.gui.configuration.ConfigurationLoader;
import com.je.io.bundle.BundleIO;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

/**
 * This class is responsible for creating all components for GUI.
 * Only this class is allowed to create GUI components.
 */
public class JeComponentBuilder {
    private final String FOREGROUND_COLOR = "foreground_color";
    private final String BACKGROUND_COLOR = "background_color";
    private final String BORDER_COLOR     = "border_color";
    private final String BORDER_RADIUS    = "border_radius";
    private final String SECTION_COLOR    = "section_color";

    /**
     * Properties that will be applied to every component.
     */
    private Bundle mProperties;

    /**
     * Constructor taking config loader to load configuration.
     * @param configLoader configuration loader implementation.
     */
	public JeComponentBuilder(ConfigurationLoader configLoader) {
        Theme theme = configLoader.loadTheme();
        try (InputStream inputStream = theme.configStream()) {
            mProperties = BundleIO.loadBundle(inputStream);
            if(!mProperties.contains(FOREGROUND_COLOR, BACKGROUND_COLOR, BORDER_COLOR, BORDER_RADIUS, SECTION_COLOR)) {
                JeLib.console().warn("Theme file is invalid.");
                JeLib.console().log("Theme file must contain the below keys:");
                JeLib.console().log(
                        new StringBuilder()
                                .append(FOREGROUND_COLOR).append('\n')
                                .append(BACKGROUND_COLOR).append('\n')
                                .append(BORDER_COLOR).append('\n')
                                .append(BORDER_RADIUS).append('\n')
                                .append(SECTION_COLOR).append('\n')
                );
            }
        } catch (IOException e) {
            JeLib.console().error("Failed to load GUI component configuration!");
            JeLib.console().exception(e);
            mProperties = null;
        }
    }

    /**
     * Method that creates a section and styles it.
     */
    public JeSection createSection(LayoutManager layout) {
        JeSection section = new JeSection(layout);
        return styleSection(section);
    }

    /**
     * Styles given section.
     * @param section section to style.
     * @return returns given section.
     */
    public JeSection styleSection(JeSection section) {
        final int COLOR = mProperties.getInteger(SECTION_COLOR, Color.WHITE.getRGB());
        section.setBackground(new Color(COLOR));
        return section;
    }

    /**
     * Method that creates a component of the given class and applies styles to it.
     * Given text is applied to the component too.
     * @param clazz Class of the component to create.
     * @param text  Text to put to component.
     * @return Styled component of given type {@link T}
     * @param <T> Type of created and returned component.
     */
    public <T extends JComponent> Optional<T> createTextComponent(Class<T> clazz, String text) {
        final int foregroundColor = mProperties.getInteger("foreground_color", Color.BLACK.getRGB());
        var textLabel = new JLabel(text);
        textLabel.setForeground(new Color(foregroundColor));
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/zalando-sans-bold.ttf"));
            textLabel.setFont(font.deriveFont(16f));
        } catch (FontFormatException | IOException e) {
            JeLib.console().warn("Could not load internal font. Font file corrupted or missing.");
            JeLib.console().exception(e);
        }
        Optional<T> componentOpt = createComponent(clazz);
        componentOpt.ifPresent(component -> {
            component.setLayout(new GridBagLayout());
            component.add(textLabel, new GridBagConstraints());
        });
        return componentOpt;
    }

    /**
     * Method that creates a component of the given class and applies styles to it.
     * @param clazz Class of the component to create.
     * @return Styled component of given type {@link T}
     * @param <T> Type of created and returned component.
     */
    public <T extends JComponent> Optional<T> createComponent(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getConstructor();
            T component = constructor.newInstance();

            final Color TRANSPARENT = new Color(15259903, true);
            final int backgroundColor = mProperties.getInteger("background_color", Color.WHITE.getRGB());
            final int borderColor     = mProperties.getInteger("border_color",     Color.BLACK.getRGB());
            final int borderRadius    = mProperties.getInteger("border_radius",    10);

            component.setOpaque(false);
            component.setBackground(TRANSPARENT);

            Border border = new RoundBorder(new Color(backgroundColor), new Color(borderColor), borderRadius);
            component.setBorder(border);
            return Optional.of(component);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            JeLib.console().error("Failed to create instance of "+clazz.getName());
            JeLib.console().error("Class: "+clazz.getSimpleName()+" must have a public no-arg constructor.");
            JeLib.console().exception(e);
            return Optional.empty();
        }
    }

    /**
     * This is NOT a border.
     * This draws the background and the border of a component.
     */
    private static final class RoundBorder implements Border {
        private final Color mColor, mBackground;
        private final int mRadius;

        /**
         * Constructor initializing fields.
         * @param background background color
         * @param color      border color
         * @param radius     border radius
         */
        RoundBorder(Color background, Color color, int radius) {
            mColor = color;
            mBackground = background;
            mRadius = radius;
        }

        @Override
        public void paintBorder(Component component, Graphics graphics, int x, int y, int width, int height) {
            Graphics2D graphics2D = (Graphics2D) graphics;
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Fill shape with background first...
            graphics2D.setColor(mBackground);
            graphics2D.fillRoundRect(x, y, width-1, height-1, mRadius, mRadius);

            // Draw the borderline above...
            graphics2D.setColor(mColor);
            graphics2D.drawRoundRect(x, y, width-1, height-1, mRadius, mRadius);
        }

        @Override
        public Insets getBorderInsets(Component component) {
            return new Insets(mRadius / 2, mRadius / 2, mRadius / 2, mRadius / 2);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }
}