package com.test.main;

import com.je.core.*;
import com.je.core.broadcast.AbstractMessageReceiver;
import com.je.core.broadcast.FunctionalMessageReceiver;
import com.je.core.broadcast.Message;
import com.je.core.util.Bundle;
import com.je.core.util.LazyExecutor;
import com.je.core.util.Utils;
import org.junit.Test;

import java.util.Map;
import java.util.function.Consumer;

public class TestCore {
    @Test
    public void broadcasts() {
        JeApplication application = new JeApplication();

        application.registerReceiver(new AbstractMessageReceiver("show_text") {
            @Override
            public void onReceive(Message message) {
                String text = message
                        .getBundle()
                        .getString("secret_message");
                JeLib.console().log("That's the secret message: "+text);
            }
        });

        Consumer<Message> messageConsumer = m -> JeLib.console().log("Message: "+m.getBundle().getString("secret_message"));
        application.registerReceiver(new FunctionalMessageReceiver(messageConsumer, "show_text"));

        application.broadcastMessage(
                Message.newBuilder()
                        .setAction("show_text")
                        .putExtra("secret_message", "Hello, World!")
                        .build()
        );
    }

    @Test
    public void lazyExecutorAndBundle() {
        // msg1 and msg3 must be printed
        LazyExecutor lazyExecutor = new LazyExecutor(bundle -> JeLib.console().log("Message from lazy executor '"+bundle.getString("message")+"'"), 100);
        lazyExecutor.request(Bundle.builder().put("message", "msg1").build());
        lazyExecutor.request(Bundle.builder().put("message", "msg2").build());
        try {
            Thread.sleep(120);
            lazyExecutor.request(Bundle.builder().put("message", "msg3").build());
        } catch (InterruptedException ie) {
            JeLib.console().exception(ie);
        }
    }

    @Test
    public void console() {
        JeLib.console().setEnabled(Console.Type.WARNING, false);
        JeLib.console().setEnabled(Console.Type.INFO, false);
        JeLib.console().log("Hello from JeLib!");
        JeLib.console().setEnabled(Console.Type.WARNING, true);
        JeLib.console().warn("This lib is empty!");
        JeLib.console().error("Aborting!");
        JeLib.console().setEnabled(Console.Type.INFO, true);
        JeLib.console().log("Hello! Can you see me?");
        JeLib.console().setEnabled(Console.Type.EXCEPTION, false);
        JeLib.console().exception(new RuntimeException("Cannot load stuff"));
    }

    @Test
    public void map() {
        Map<String, Integer> map = Utils.mapBuilder(String.class, Integer.class)
                .put("JeLib?", 1)
                .put("JeLib??", 2)
                .put("JeLib", 0)
                .build();
        JeLib.console().log(map);
    }
}
