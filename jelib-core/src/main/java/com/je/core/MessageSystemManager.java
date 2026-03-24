package com.je.core;

import com.je.core.broadcast.Message;
import com.je.core.broadcast.MessageReceiver;

/**
 * Manages message system.
 * Sends each broadcast to the receiver it can go via {@link #broadcastMessage(Message)}.
 * Registers receivers via {@link #registerReceiver(MessageReceiver)}.
 */
public interface MessageSystemManager {
    /**
     * Sends given message to the right receiver(s).
     * @param message message to send.
     */
    void broadcastMessage(Message message);

    /**
     * Registers a receiver that will be able to receive actions.
     * @param receiver receiver to register.
     */
    void registerReceiver(MessageReceiver receiver);
}
