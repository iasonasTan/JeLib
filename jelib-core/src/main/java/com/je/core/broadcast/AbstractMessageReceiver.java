package com.je.core.broadcast;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract receiver with default action management implementation.
 * The correct use of the class is extending it and
 * implementing method {@link #onReceive(Message)}.
 */
public abstract class AbstractMessageReceiver implements MessageReceiver {
    /**
     * List storing the acceptable actions of the receiver.
     */
    private final List<String> mActions = new ArrayList<>();

    /**
     * Constructor initializes actions.
     * @param actions the actions that receiver will accept as {@link List<String>}.
     */
    public AbstractMessageReceiver(List<String> actions) {
        mActions.addAll(actions);
    }

    /**
     * Constructor initializes actions.
     * @param actions the action that receiver will accept as {@code String...}.
     */
    public AbstractMessageReceiver(String... actions) {
        mActions.addAll(List.of(actions));
    }

    /**
     * This method gets called whenever a message passes the filter.
     *
     * @param message received message as {@link Message}.
     */
    @Override
    public abstract void onReceive(Message message);

    /**
     * Returns actions that the receiver accepts.
     *
     * @return acceptable actions as {@link List}.
     */
    @Override
    public List<String> getActions() {
        return new ArrayList<>(mActions);
    }

    /**
     * Enabled or disables an action.
     *
     * @param action  action to set enabled or disabled.
     * @param enabled ture whenever action is going to be enabled; false otherwise
     */
    @Override
    public void setAction(String action, boolean enabled) {
        if(enabled) {
            mActions.add(action);
        } else {
            mActions.remove(action);
        }
    }
}
