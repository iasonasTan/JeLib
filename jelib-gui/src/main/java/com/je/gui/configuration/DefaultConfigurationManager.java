package com.je.gui.configuration;

import com.je.core.util.Bundle;
import com.je.gui.Theme;
import com.je.io.configuration.Configuration;

/**
 * Default configuration manager for
 * @see ConfigurationLoader
 */
public final class DefaultConfigurationManager {
    /**
     * The name of the configuration file.
     */
    private static final String GUI_CONFIG_FILE = "gui.properties";

    /**
     * Returns a configuration loader.
     * @return Returns an instance of an implementation of a configuration loader.
     */
    public static ConfigurationLoader getDefaultLoader() {
        return new DefaultConfigurationLoader();
    }

    /**
     * Sets night theme enabled or disabled.
     * @param enabled true when night theme should be enabled; false otherwise.
     */
    public static void setNightThemeEnabled(boolean enabled) {
        Bundle bundle = new Bundle();
        bundle.put("night_theme", enabled);
        Configuration.storeBundle(GUI_CONFIG_FILE, bundle);
    }

    /**
     * Default configuration loader. Loads based on {@code gui.properties} file.
     */
    private static final class DefaultConfigurationLoader implements ConfigurationLoader {
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

    /**
     * Private constructor prevents instantiation.
     */
    private DefaultConfigurationManager() {
    }
}
