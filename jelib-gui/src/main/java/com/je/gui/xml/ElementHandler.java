package com.je.gui.xml;

import com.je.core.JeLib;
import com.je.gui.component.JeGuiBuilder;
import com.je.gui.component.JeSection;
import com.je.gui.component.JeText;
import org.w3c.dom.Element;

import javax.swing.*;
import java.awt.*;

class ElementHandler {
    private final JeGuiBuilder mGuiBuilder;
    private final JeSection mRoot;

    ElementHandler(JeGuiBuilder guiBuilder, JeSection root) {
        mGuiBuilder = guiBuilder;
        mRoot = root;
    }

    void process(Element element, Object parent) throws Throwable {
        Object obj = instantiateFromElement(element);
        if (obj == null) return;

        if (obj instanceof JComponent leafComp) {
            PropertyBinder.apply(element, leafComp);
            attachLeafToParent(leafComp, parent);
            return;
        }

        JeSection container = resolveContainer(obj, element);
        if (container == null) return;

        PropertyBinder.apply(element, container);

        XmlUtils.forEachChild(element, child -> {
            try {
                process(child, container);
            } catch (Throwable t) {
                JeLib.console().exception(t);
            }
        });

        attachContainerToParent(container, parent);
    }

    private void attachLeafToParent(JComponent comp, Object parent) {
        //mRoot.addChild(comp);
        if (parent instanceof JScrollPane scrollPane) {
            scrollPane.setViewportView(comp);
        } else if (parent instanceof JeSection section) {
            section.addChild(comp);
        }
    }

    private void attachContainerToParent(JeSection container, Object parent) {
        if (container instanceof JComponent jc) {
            mRoot.addChild(jc);
        }
        if (parent instanceof JScrollPane scrollPane) {
            scrollPane.setViewportView(container);
        } else if (parent instanceof JeSection section) {
            section.addChild(container);
        }
    }

    @SuppressWarnings("unchecked")
    private Object instantiateFromElement(Element element) throws Throwable {
        Class<?> clz = ReflectionUtils.guiClass(element.getTagName());
        if (clz == null) return null;

        String text = element.getAttribute("text");
        boolean isTextComponent = JeText.class.isAssignableFrom(clz) || clz.isAssignableFrom(JeText.class);

        if (!text.isBlank() && isTextComponent) {
            return mGuiBuilder.createTextComponent((Class<? extends JComponent>) clz, text);
        }
        return ReflectionUtils.instantiate(clz);
    }

    private JeSection resolveContainer(Object obj, Element element) {
        if (obj instanceof LayoutManager layout) {
            JeSection section = mGuiBuilder.createSection(layout);
            PropertyBinder.applyLayoutProps(element, layout);
            return section;
        }
        if (obj instanceof JeSection section) {
            return section;
        }
        return null;
    }
}
