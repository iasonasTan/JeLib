/**
 * Gui module of JeLib.
 */
module JeLib.gui {
    requires transitive JeLib.core;
    requires transitive JeLib.media;
    requires transitive java.desktop;
    requires JeLib.io;

    exports com.je.gui;
    exports com.je.gui.layout;
    exports com.je.gui.component;
    exports com.je.gui.configuration;
}