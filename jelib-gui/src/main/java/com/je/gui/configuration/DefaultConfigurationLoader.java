package com.je.gui.configuration;

import com.je.gui.Theme;
import com.je.io.configuration.Configuration;

/**
 * Default configuration loader of jeLib.
 * @see ConfigurationLoader
 */
public class DefaultConfigurationLoader implements ConfigurationLoader {
    /**
     * The name of the configuration file.
     */
    private static final String GUI_CONFIG_FILE = "gui.properties";

    /**
     * Loads theme of the component.
     * @return theme of components as {@link Theme}.
     */
    @Override
    public Theme loadTheme() {
        boolean nightTheme = Configuration
                .loadBundle(GUI_CONFIG_FILE)
                .getBoolean("night_theme", false);
        return nightTheme ?
                new Theme.Night() :
                new Theme.Day();
    }
}
