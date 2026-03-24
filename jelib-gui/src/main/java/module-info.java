/**
 * Gui module of JeLib.
 */
module JeLib.gui {
    requires transitive java.desktop;
    requires transitive JeLib.core;
    requires transitive JeLib.media;
    requires JeLib.io;

    exports com.je.gui;
}