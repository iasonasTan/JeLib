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
     * Sleeps thread for given time and handles {@link InterruptedException} if it gets thrown.
     * @param millis Time in millis to sleep for as {@code long}.
     */
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            JeLib.console().error("Something went wrong while sleeping.");
            JeLib.console().exception(e);
        }
    }

    /**
     * Runs given code and ignores every type of exception that may get thrown.
     */
    public static void ignoreExceptions(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception ignored) {
            // ignore
        }
    }

    /**
     * Private constructor prevents instantiation from outside the class.
     */
    private JeLib(){
    }
}
