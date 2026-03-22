package com.je.core;

public final class JeLib {
    private static Console sConsoleInstance;

    public static Console console() {
        if(sConsoleInstance == null) {
            sConsoleInstance = new Console(System.out);
        }
        return sConsoleInstance;
    }

    /**
     * Private constructor prevents instantiation.
     */
    private JeLib(){
    }
}
