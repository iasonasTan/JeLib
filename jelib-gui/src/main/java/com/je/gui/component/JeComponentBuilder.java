package com.je.gui.component;

import com.je.core.JeLib;
import com.je.core.util.Bundle;
import com.je.gui.Theme;
import com.je.gui.border.RoundBorder;
import com.je.gui.configuration.ConfigurationLoader;
import com.je.io.bundle.BundleIO;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
        } catch (IOException e) {
            JeLib.console().error("Failed to load GUI component configuration!");
            JeLib.console().exception(e);
            mProperties = null;
        }
    }

    /**
     * Method that creates a component of the given class and applies styles to it.
     * @param clazz Class of the component to create.
     * @return Styled component of given type {@link T}
     * @param <T> Type of created and returned component.
     */
    public <T extends JComponent> Optional<T> createComponent(Class<T> clazz, String text) {
        try {
            Constructor<T> constructor = clazz.getConstructor();
            T component = constructor.newInstance();

            int foregroundColor = mProperties.getInteger("foreground_color", Color.BLACK.getRGB());

            var textLabel = new JLabel(text);
            textLabel.setForeground(new Color(foregroundColor));

            // Put text in the center
            component.setLayout(new GridBagLayout());
            component.add(textLabel, new GridBagConstraints());

            int backgroundColor = mProperties.getInteger("background_color", Color.WHITE.getRGB());
            int borderColor     = mProperties.getInteger("border_color",     Color.BLACK.getRGB());
            int borderRadius    = mProperties.getInteger("border_radius",    50);
            component.setOpaque(false);
            component.setBackground(new Color(15259903, true));
            component.setForeground(new Color(foregroundColor));
            Border border = new RoundBorder(new Color(backgroundColor), new Color(borderColor), borderRadius);
            component.setBorder(border);
            return Optional.of(component);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            JeLib.console().error("Failed to create component.");
            JeLib.console().exception(e);
            return Optional.empty();
        }
    }
}