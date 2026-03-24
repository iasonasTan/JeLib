package com.je.core.broadcast;

import com.je.core.MessageSystemManager;
import com.je.core.util.Bundle;

/**
 * A message contains an action and a bundle.
 * You can send it to a {@link MessageReceiver} via {@link MessageSystemManager}.
 * @see MessageSystemManager
 */
public final class Message {
    /**
     * Creates a new {@link Builder}.
     * @return {@link Builder} instance.
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Creates a new {@link Builder}.
     * @param action action to set to builder's message at construction-time.
     * @return {@link Builder} instance with given action.
     */
    public static Builder newBuilder(String action) {
        return new Builder(action);
    }

    /**
     * Action of this {@link Message}.
     * Used to say what the message sender wants to do.
     * @see #getAction()
     */
    private String mAction;

    /**
     * Bundle that contains data used by {@link MessageReceiver}.
     */
    private final Bundle mBundle = new Bundle();

    /**
     * default constructor.
     */
    public Message() {
    }

    /**
     * Action constructor.
     * @param action action of this message.
     */
    public Message(String action) {
        mAction = action;
    }

    /**
     * Puts a key and a value to this message's bundle.
     * @param key   the key of the extra.
     * @param value the value of the extra.
     */
    public void putExtra(String key, Object value) {
        mBundle.put(key, value);
    }

    /**
     * Returns a copy of the bundle.
     * @return copy of the bundle.
     */
    public Bundle getBundle() {
        return mBundle.copy();
    }

    /**
     * Returns this message's action.
     * @return this message's action as {@link java.lang.String}.
     */
    public String getAction() {
        return mAction;
    }

    /**
     * Builds a message with chain syntax.
     */
    public static final class Builder {
        /**
         * The message that's getting built.
         */
        private final Message mMessage = new Message();

        /**
         * Private constructor that sets action instantly.
         * @param action action of the message.
         */
        private Builder(String action) {
            mMessage.mAction = action;
        }

        /**
         * Private no-args constructor.
         */
        private Builder() {
        }

        /**
         * Sets action to builder.
         * @param action sets action to constructing message.
         * @return returns self so chain method calls are possible.
         */
        public Builder setAction(String action) {
            mMessage.mAction = action;
            return this;
        }

        /**
         * Add key and value to builder.
         * @param key key to add to constructing message.
         * @param value value to add to constructing message.
         * @return returns self so chain method calls are possible.
         */
        public Builder putExtra(String key, Object value) {
            mMessage.putExtra(key, value);
            return this;
        }

        /**
         * Returns constructed Message.
         * @return Constructed message.
         */
        public Message build() {
            return mMessage;
        }
    }
}