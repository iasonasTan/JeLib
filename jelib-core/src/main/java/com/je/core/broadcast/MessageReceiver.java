package com.je.core.broadcast;

import java.util.List;

/**
 * Something that can receive broadcasted messages.
 * It accepts only messages with specific actions.
 */
public interface MessageReceiver {
    /**
     * This method gets called whenever a message passes the filter.
     * @param message received message as {@link Message}.
     */
    void onReceive(Message message);

    /**
     * Returns actions that the receiver accepts.
     * @return acceptable actions as {@link List}.
     */
    List<String> getActions();

    /**
     * Enabled or disables an action.
     * @param action  action to set enabled or disabled.
     * @param enabled ture whenever action is going to be enabled; false otherwise
     */
    void setAction(String action, boolean enabled);
}
