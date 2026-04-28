/**
 * IO module of JeLib.
 */
module JeLib.io {
    requires transitive JeLib.core;
    requires transitive java.desktop;

    exports com.je.io;
    exports com.je.io.bundle;
    exports com.je.io.configuration;
}