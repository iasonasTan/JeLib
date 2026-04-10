package com.je.core.broadcast;

import com.je.core.MessageManager;

import java.util.List;
import java.util.function.Consumer;

/**
 * This is a functional message receiver, it works with a consumer.
 * It is useful when you want to use an existing method as consumer and pass it easily to {@link MessageManager#registerReceiver(MessageReceiver)}.
 */
public final class FunctionalMessageReceiver extends AbstractMessageReceiver {
    /**
     * Consumer that will do the job instead of {@link #onReceive(Message)}.
     */
    private final Consumer<Message> mConsumer;

    /**
     * Constructs a functional message receiver with given consumer and actions.
     * @param consumer consumer that will consume the messages.
     * @param actions  actions that receiver will allow as {@link List<String>}.
     */
    public FunctionalMessageReceiver(Consumer<Message> consumer, List<String> actions) {
        mConsumer = consumer;
        super(actions);
    }

    /**
     * Constructs a functional message receiver with given consumer and actions.
     * @param consumer consumer that will consume the messages.
     * @param actions  actions that receiver will allow as {@code String...}.
     */
    public FunctionalMessageReceiver(Consumer<Message> consumer, String... actions) {
        this(consumer, List.of(actions));
    }

    /**
     * This method gets called whenever a message passes the filter.
     * @param message received message as {@link Message}.
     */
    @Override
    public void onReceive(Message message) {
        mConsumer.accept(message);
    }
}
