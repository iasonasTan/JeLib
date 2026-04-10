package com.je.core;

import com.je.core.broadcast.Message;
import com.je.core.broadcast.MessageReceiver;

import java.util.ArrayList;
import java.util.List;

/**
 * Application class that manages everything about the program.
 * It's recommended to extend this class set it as the starter point.
 */
public class JeApplication implements MessageManager {
    /**
     * List storing the active receivers.
     */
    private final List<MessageReceiver> mReceivers = new ArrayList<>();

    /**
     * Sends given message to the right receiver(s).
     * @param message message to send.
     */
    @Override
    public void broadcastMessage(Message message) {
        final String messageActions = message.getAction();
        final List<MessageReceiver> receiversCopy;
        synchronized(this) {
            receiversCopy = new ArrayList<>(mReceivers);
        }
        receiversCopy.stream()
                .filter(r -> r.getActions().contains(messageActions))
                .forEach(r -> r.onReceive(message));
    }

    /**
     * Registers a receiver that will be able to receive actions.
     * @param receiver receiver to register.
     */
    @Override
    public synchronized void registerReceiver(MessageReceiver receiver) {
        mReceivers.add(receiver);
    }
}
