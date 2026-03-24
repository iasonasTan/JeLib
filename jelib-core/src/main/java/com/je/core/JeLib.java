package com.je.core;

/**
 * Core class of JeLib.
 * Something like {@link System} but for JeLib.
 */
public final class JeLib {
    /**
     * System.out console instance.
     * This is the default console instance that prints to the terminal.
     */
    private static Console sConsoleInstance;

    /**
     * Returns an instance of {@link Console} that prints in the terminal and creates one if there's no.
     * @return always returns an instance of {@link Console} with {@link System#out}
     * as the {@link java.io.PrintStream} as the main print stream.
     */
    public static Console console() {
        if(sConsoleInstance == null) {
            sConsoleInstance = new Console(System.out);
        }
        return sConsoleInstance;
    }

    /**
     * Private constructor prevents instantiation from outside the class.
     */
    private JeLib(){
    }
}
