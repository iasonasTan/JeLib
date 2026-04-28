package com.je.gui.dialog;

import java.util.Map;
import java.util.HashMap;

public abstract class AbstractDialog<T> implements Dialog<T> {
    protected final Map<String, String> properties = new HashMap<String, String>();
    
    @Override
    public void putProperty(String key, String value) {
        properties.put(key, value);
    }
    
}
