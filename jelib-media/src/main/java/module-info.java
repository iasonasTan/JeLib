/**
 * Media module of JeLib.
 */
module JeLib.media {
    requires transitive JeLib.core;
    requires transitive java.desktop;
    requires JeLib.io;

    exports com.je.media;
}