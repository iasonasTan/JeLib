package com.je.gui.xml;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.function.Consumer;

class XmlUtils {

    static void forEachChild(Element parent, Consumer<Element> action) {
        NodeList children = parent.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (node instanceof Element child) {
                action.accept(child);
            }
        }
    }
}
