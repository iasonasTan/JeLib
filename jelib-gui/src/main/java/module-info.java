/**
 * Gui module of JeLib.
 */
module JeLib.gui {
    requires java.desktop;
    requires transitive JeLib.core;
    requires JeLib.io;

    exports com.je.gui;
}