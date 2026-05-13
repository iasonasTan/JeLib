package com.je.gui.xml;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.function.Consumer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import com.je.gui.component.*;
import com.je.gui.GuiUtils;
import com.je.gui.layout.VerticalFlowLayout;
import com.je.core.JeLib;
import com.je.io.IOUtils;

public class Loader {
    private JeGuiBuilder mGuiBuilder;
    private final JeSection mSection;

    public Loader(JeGuiBuilder guiBuilder) {
        mGuiBuilder = guiBuilder;
        mSection = guiBuilder.createSection(null);
    }

    public JeSection loadFrom(InputStream inputStream) {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            Element rootNode = document.getDocumentElement();
            putLayout(rootNode);
            handleElement(rootNode, mSection);
        } catch (/*ParserConfigurationException | SAXException | */Throwable e) {
            JeLib.console().exception(e);
        }
        return mSection;
    }

    private void putLayout(Element element) throws Throwable {
        String layoutClassStr = element.getAttribute("layout");
        Class<?> layoutClass  = getGuiClass(layoutClassStr);
        Constructor<?> constructor = layoutClass.getConstructor();
        Object layoutManObj = constructor.newInstance();
        if(layoutManObj instanceof LayoutManager) {
            LayoutManager layoutManager = (LayoutManager)layoutManObj;
            mSection.setLayout(layoutManager);
        } else {
            JeLib.console().error("Given class is not a java.awt.LayoutManager\nNo Layout applied.");
        }
    }

    private void handleElement(Element thisElement, Object parent) throws Throwable {
        Class<?> clz = getGuiClass(thisElement.getTagName());
        // TODO: add text support
        String text = thisElement.getAttribute("text");
        Object obj;
        if(!text.isBlank() && (
            clz.isAssignableFrom(JeText.class) ||
            JeText.class.isAssignableFrom(clz)
        )) {
            obj = mGuiBuilder.createTextComponent((Class<? extends JComponent>)clz, text);
        } else {
            Constructor<?> constructor = clz.getConstructor();
            obj = constructor.newInstance();
            
            
        }
        handleObject(obj, thisElement, parent);
    }

    private void handleObject(Object thisObject, Element thisElement, Object parent) throws Throwable {
        Container thisContainer;

        if(thisObject instanceof LayoutManager layout) {
            thisContainer = new JPanel(layout);
            addProperties(thisElement, layout);
        } else if (thisObject instanceof Container container) {
            thisContainer = container;
        } else {
            return;
        }

        addProperties(thisElement, thisContainer);
        forChild(thisElement, n -> {
            try {
                handleElement(n, thisContainer);
            } catch (Throwable thrbl) {
                JeLib.console().exception(thrbl);
            }
        });
        String textID = thisElement.getAttribute("id");
        // TODO use id
        if(thisContainer instanceof JComponent) {
            mSection.addChild((JComponent)thisContainer);
        }
        if(parent instanceof JScrollPane parentScrollPane)
            parentScrollPane.setViewportView(thisContainer);
        else if (parent instanceof Container parentContainer)
            parentContainer.add(thisContainer);
    }

    private void addProperties(Element element, LayoutManager layout) throws Throwable {
        String rowsTxt = element.getAttribute("rows");
        String colsTxt = element.getAttribute("cols");
        if(!rowsTxt.isEmpty() && !colsTxt.isEmpty() && layout instanceof GridLayout gLayout) {
            int rows = Integer.parseInt(rowsTxt);
            int cols = Integer.parseInt(colsTxt);
            gLayout.setRows(rows);
            gLayout.setColumns(cols);
        }
        // String vGapTxt = element.getAttribute("vGap");
        // String hGapTxt = element.getAttribute("hGap");
        // if(!vGapTxt.isEmpty() && !hGapTxt.isEmpty()) {
        //     int vGap = Integer.parseInt(vGapTxt);
        //     int hGap = Integer.parseInt(hGapTxt);
        //     // TODO: use your own interface for that
        //     if(layout instanceof VerticalFlowLayout vfLayout) {
        //         vfLayout.hGap = hGap;
        //         vfLayout.vGap = vGap;
        //     } else if (layout instanceof GridLayout gLayout) {
        //         gLayout.setHgap(hGap);
        //         gLayout.setVgap(vGap);
        //     } else if (layout instanceof FlowLayout fLayout) {
        //         fLayout.setHgap(hGap);
        //         fLayout.setVgap(vGap);
        //     }
        // }
    }

    private void addProperties(Element element, Component comp) throws Throwable {
        String text = element.getAttribute("text");

        String sizeStr = element.getAttribute("size");
        if(!sizeStr.isEmpty()) {
            comp.setPreferredSize(getSize(sizeStr));
        }
        String imagePath = element.getAttribute("src");
        if(!imagePath.isEmpty()) {
            BufferedImage image = IOUtils.loadImage(imagePath, Loader.class);
            if(comp instanceof JLabel jLabel) {
                Dimension labelSize = jLabel.getPreferredSize();
                Image image2 = image.getScaledInstance(labelSize.width, labelSize.height, BufferedImage.SCALE_SMOOTH);
                jLabel.setIcon(new ImageIcon(image2));
            }
        }
        String backgroundTxt = element.getAttribute("background");
        if(!backgroundTxt.isEmpty()) {
            Color color = getColor(backgroundTxt);
            comp.setBackground(color);
        }
        String foregroundTxt = element.getAttribute("foreground");
        if(!foregroundTxt.isEmpty()) {
            Color color = getColor(foregroundTxt);
            comp.setForeground(color);
        }

        String focusableTxt = element.getAttribute("focusable");
        if(!focusableTxt.isEmpty()) {
            comp.setFocusable(Boolean.parseBoolean(focusableTxt));
        }

        if(comp instanceof Button btn) {
            btn.setLabel(text);
        }
        if(comp instanceof Label lbl) {
            lbl.setText(text);
        }
        if(comp instanceof JButton btn) {
            if(!imagePath.isEmpty()) {
                BufferedImage image = IOUtils.loadImage(imagePath, Loader.class);
                Dimension labelSize = btn.getPreferredSize();
                Image image2 = image.getScaledInstance(labelSize.width, labelSize.height, BufferedImage.SCALE_SMOOTH);
                btn.setIcon(new ImageIcon(image2));
            }
            btn.setText(text);
        }
        if(comp instanceof JLabel lbl) {
            lbl.setText(text);
        }
    }

    public Dimension getSize(String sizeTxt) {
        String[] sizeStrArr = sizeTxt.split("x");
        int[] sizeArr = {Integer.parseInt(sizeStrArr[0]), Integer.parseInt(sizeStrArr[1])};
        return new Dimension(sizeArr[0], sizeArr[1]);
    }

    public Color getColor(String colorTxt) {
        switch(colorTxt) {
            case "red": return Color.RED;
            case "green": return Color.GREEN;
            case "blue": return Color.BLUE;
            case "gray": return Color.GRAY;
            case "black": return Color.BLACK;
            case "white": return Color.WHITE;
            case "orange": return Color.ORANGE;
            case "pink": return Color.PINK;
        }
        String[] colorSplit = colorTxt.split(",");
        int[] colorArr = {
                Integer.parseInt(colorSplit[0]),
                Integer.parseInt(colorSplit[1]),
                Integer.parseInt(colorSplit[2])
        };
        return new Color(colorArr[0], colorArr[1], colorArr[2]);
    }

    public void forChild(Element parentElement, Consumer<Element> childrenConsumer) {
        NodeList childNodes = parentElement.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if(childNode instanceof Element childElement)
                childrenConsumer.accept(childElement);
        }
    }

    private Class<?> getGuiClass(String classStr) {
        if(!classStr.contains(".")) {
            classStr = "com.je.gui.component."+classStr;
        }
        try {
            return Class.forName(classStr);
        } catch (ClassNotFoundException cnfe) {
            GuiUtils.exceptionDialog().showMessage(cnfe);
        }
        return null;
    }
}