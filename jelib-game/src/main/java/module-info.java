/**
 * Game module of JeLib.
 */
module JeLib.game {
    requires transitive JeLib.core;
    requires transitive JeLib.gui;
    requires transitive java.desktop;
    requires JeLib.io;
    exports com.je.game;
}