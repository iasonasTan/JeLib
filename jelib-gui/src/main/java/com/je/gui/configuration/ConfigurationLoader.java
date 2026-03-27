package com.je.gui.configuration;

import com.je.gui.Theme;

/**
 * Responsible for loading the configuration of the GUI component.
 */
public interface ConfigurationLoader {
    /**
     * Loads theme of the component.
     * @return theme of components as {@link Theme}.
     */
    Theme loadTheme();
}
