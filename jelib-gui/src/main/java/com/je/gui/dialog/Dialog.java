package com.je.gui.dialog;

/**
 * Represents a dialog that can show a message.
 */
public interface Dialog<T> {
    /**
     * Shows a GUI window that displays the given object.
     * @param object object to show.
     */
    void showMessage(T object);

    /**
     * Puts property into dialog.
     * @param key   Key of property.
     * @param value Value of property.
     */
    void putProperty(String key, String value);
}
