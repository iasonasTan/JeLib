package com.je.core;

public final class Utils {
    /**
     * Converts given StackTrace into a string.
     * @param stackTrace stack trace as array.
     * @param color      nullable color to use.
     * @return String with given color that represents given stacktrace.
     */
    public static String stacktraceToString(StackTraceElement[] stackTrace, String color) {
        StringBuilder messageBuilder = new StringBuilder();
        for (StackTraceElement stackTraceElement: stackTrace) {
            if(color!=null) {
                messageBuilder.append(color);
            }
            messageBuilder
                    .append(stackTraceElement.toString())
                    .append('\n');
        }
        return messageBuilder.toString();
    }

    /**
     * Creates a {@link MapBuilder} with given types.
     * @param ignored1 type of key.
     * @param ignored2 type of value.
     * @return map builder with given types.
     * @param <K> key type
     * @param <V> value type
     */
    public static <K, V> MapBuilder<K, V> mapBuilder(Class<K> ignored1, Class<V> ignored2) {
        return new MapBuilder<>();
    }

    /**
     * Private constructor prevents instantiation.
     */
    private Utils(){
    }
}
