package com.je.gui.xml;

import com.je.core.JeLib;
import com.je.gui.component.JeGuiBuilder;
import com.je.gui.component.JeSection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.InputStream;

public class Loader {
    private final JeGuiBuilder mGuiBuilder;
    private final JeSection mRoot;

    public Loader(JeGuiBuilder guiBuilder) {
        mGuiBuilder = guiBuilder;
        mRoot = guiBuilder.createSection(null);
    }

    public JeSection loadFrom(InputStream inputStream) {
        try {
            Document document = parseXml(inputStream);
            Element rootElement = document.getDocumentElement();
            applyRootLayout(rootElement);

            XmlUtils.forEachChild(rootElement, child -> {
                try {
                    new ElementHandler(mGuiBuilder, mRoot).process(child, mRoot);
                } catch (Throwable t) {
                    JeLib.console().exception(t);
                }
            });
        } catch (Throwable e) {
            JeLib.console().exception(e);
        }
        return mRoot;
    }

    private Document parseXml(InputStream stream) throws Exception {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        return builder.parse(stream);
    }

    private void applyRootLayout(Element root) throws Exception {
        String layoutClassText = root.getTagName();
        Object layout = ReflectionUtils.instantiate(layoutClassText);
        if (layout instanceof LayoutManager lm) {
            mRoot.setLayout(lm);
        } else {
            JeLib.console().error("Attribute 'layout' does not resolve to a LayoutManager. No layout applied.");
        }
    }
}


