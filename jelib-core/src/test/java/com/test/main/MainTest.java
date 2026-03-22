package com.test.main;

import com.je.core.Bundle;
import com.je.core.Console;
import com.je.core.JeLib;
import com.je.core.Utils;
import org.junit.Test;

import java.util.Map;

public class MainTest {
    @Test
    public void console() {
        JeLib.console().setEnabled(Console.Type.WARNING, false);
        JeLib.console().setEnabled(Console.Type.INFO, false);
        JeLib.console().log("Hello from JeLib!");
        JeLib.console().warn("This lib is empty!");
        JeLib.console().error("Aborting!");
        JeLib.console().setEnabled(Console.Type.INFO, true);
        JeLib.console().log("Hello! Can you see me?");
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

    @Test
    public void bundle() {
        Bundle bundle = Bundle.builder()
                .put("username", "jason")
                .put("password", "Jason-Jason-Jason")
                .build();
        JeLib.console().log(bundle);
    }
}
