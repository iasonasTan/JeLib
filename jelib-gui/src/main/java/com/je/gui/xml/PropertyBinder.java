package com.je.gui.xml;

import com.je.io.IOUtils;
import org.w3c.dom.Element;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class PropertyBinder {

    static void apply(Element element, Component comp) {
        applySize(element, comp);
        applyColors(element, comp);
        applyFocusable(element, comp);
        applyText(element, comp);
        applyImage(element, comp);
    }

    static void applyLayoutProps(Element element, LayoutManager layout) {
        if (!(layout instanceof GridLayout grid)) return;
        String rows = element.getAttribute("rows");
        String cols = element.getAttribute("cols");
        if (!rows.isEmpty() && !cols.isEmpty()) {
            grid.setRows(Integer.parseInt(rows));
            grid.setColumns(Integer.parseInt(cols));
        }
    }

    private static void applySize(Element element, Component comp) {
        String sizeStr = element.getAttribute("size");
        if (!sizeStr.isEmpty()) {
            comp.setPreferredSize(DimensionParser.parse(sizeStr));
        }
    }

    private static void applyColors(Element element, Component comp) {
        String bg = element.getAttribute("background");
        if (!bg.isEmpty()) comp.setBackground(ColorParser.parse(bg));

        String fg = element.getAttribute("foreground");
        if (!fg.isEmpty()) comp.setForeground(ColorParser.parse(fg));
    }

    private static void applyFocusable(Element element, Component comp) {
        String focusable = element.getAttribute("focusable");
        if (!focusable.isEmpty()) {
            comp.setFocusable(Boolean.parseBoolean(focusable));
        }
    }

    private static void applyText(Element element, Component comp) {
        String text = element.getAttribute("text");
        if (text.isEmpty()) return;

        if (comp instanceof JButton btn) btn.setText(text);
        else if (comp instanceof JLabel lbl) lbl.setText(text);
        else if (comp instanceof Button btn) btn.setLabel(text);
        else if (comp instanceof Label lbl) lbl.setText(text);
    }

    private static void applyImage(Element element, Component comp) {
        String src = element.getAttribute("src");
        if (src.isEmpty()) return;

        BufferedImage image = IOUtils.loadImage(src, PropertyBinder.class);
        if (image == null) return;

        if (comp instanceof JLabel lbl) {
            lbl.setIcon(scaleToIcon(image, lbl.getPreferredSize()));
        } else if (comp instanceof JButton btn) {
            btn.setIcon(scaleToIcon(image, btn.getPreferredSize()));
        }
    }

    private static ImageIcon scaleToIcon(BufferedImage image, Dimension size) {
        Image scaled = image.getScaledInstance(size.width, size.height, BufferedImage.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
}
