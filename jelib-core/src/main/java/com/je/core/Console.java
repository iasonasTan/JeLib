package com.je.core;

import com.je.core.util.Utils;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Console is a class that prints messages to given {@link PrintStream}.
 * Supplies methods like {@link #log(Object)} or {@link #warn(Object)} to
 * print messages with different colors.
 */
public class Console {
    /**
     * Set contains enabled type of logs.
     * To enable of disable use {@link #setEnabled(Type, boolean)}.
     */
    private final Set<Type> mEnabledTypes = new HashSet<>(
            List.of(Type.values()) // All types enabled by default.
    );

    /**
     * Output of the logs.
     * Initialized in constructor only once.
     */
    private final PrintStream mOut;

    /**
     * Constructs a console with given args.
     * @param out output of the logs.
     */
    public Console(PrintStream out) {
        mOut = out;
    }

    /**
     * Enables and disables logging of given type.
     * @param type Type of logging to enable or disable.
     * @param e    True of False when this type of logging is enabled or not.
     */
    public void setEnabled(Type type, boolean e) {
        if(e) mEnabledTypes.add(type);
        else  mEnabledTypes.remove(type);
    }

    /**
     * Prints given object with given type and color if
     * logging of this type is enabled.
     * @param type  Type of message that will get printed.
     * @param obj   Object to print.
     * @param color Color of the message.
     * @see #setEnabled(Type, boolean)
     */
    private void log(Type type, Object obj, int color) {
        if(!mEnabledTypes.contains(type)) return;
        String message = obj.toString();
        StringBuilder coloredMessageBuilder = new StringBuilder();
        for(String line: message.split("\n")) {
            coloredMessageBuilder
                    .append("\u001B[").append(color)
                    .append("m[").append(type.name()).append("] ")
                    .append(line).append("\n");
        }
        mOut.print(coloredMessageBuilder);
    }

    /**
     * Prints info to console.
     * @param obj object to print.
     */
    public void log(Object obj) {
        log(Type.INFO, obj, 37);
    }

    /**
     * Prints error with red color to console.
     * @param obj object to print.
     */
    public void error(Object obj) {
        log(Type.ERROR, obj, 31);
    }

    /**
     * Prints warning with yellow color to console.
     * @param obj object to print.
     */
    public void warn(Object obj) {
        log(Type.WARNING, obj, 33);
    }

    /**
     * Prints exception info.
     * Prints given exception type, message, cause and stacktrace.
     * Uses {@link System#err} to print errors.
     * @param throwable exception to print its info (see above list).
     */
    public void exception(Throwable throwable) {
        Throwable cause = throwable.getCause();
        String msg = String.format("""
                \u001B[31m{
                \u001B[31mException type: %s
                \u001B[31mMessage: %s
                \u001B[31mCause Message: %s
                \u001B[31mStackTrace: %s\u001B[31m}
                """,
                throwable.getClass().getName(),
                throwable.getMessage(),
                cause!=null?cause.getMessage():"No Information",
                Utils.stacktraceToString(throwable.getStackTrace(), "\u001B[31m")
        );
        log(Type.EXCEPTION, msg, 31);
    }

    /**
     * Types of logging.
     */
    public enum Type {
        /**
         * Prints an error.
         */
        ERROR,

        /**
         * Prints a warning.
         */
        WARNING,

        /**
         * Prints information.
         */
        INFO,

        /**
         * Prints an exception.
         */
        EXCEPTION
    }
}
