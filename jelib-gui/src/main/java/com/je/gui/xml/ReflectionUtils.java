package com.je.gui.xml;

import com.je.gui.GuiUtils;

import java.lang.reflect.Constructor;
import java.util.function.Function;

class ReflectionUtils {
    private static final String GUI_COMPONENT_PACKAGE = "com.je.gui.component.";
    private static final String GUI_LAYOUT_PACKAGE    = "com.je.gui.layout.";

    static Class<?> guiClass(String className) {
        Function<String, String> inComponents = classStr -> classStr.contains(".") ? classStr : GUI_COMPONENT_PACKAGE + classStr;
        Function<String, String> inLayout     = classStr -> classStr.contains(".") ? classStr : GUI_LAYOUT_PACKAGE + classStr;
        try {
            // Search for components
            return Class.forName(inComponents.apply(className));
        } catch (ClassNotFoundException e) {
            // Search for layout
            try {
                return Class.forName(inLayout.apply(className));
            } catch (ClassNotFoundException ignored) {
                // ignore
            }
            GuiUtils.exceptionDialog().showMessage(e);
            return null;
        }
    }

    static Object instantiate(String className) throws Exception {
        return instantiate(guiClass(className));
    }

    static Object instantiate(Class<?> clz) throws Exception {
        if (clz == null) return null;
        Constructor<?> constructor = clz.getConstructor();
        return constructor.newInstance();
    }
}
