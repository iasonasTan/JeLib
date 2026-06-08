package com.je.gui.xml;

import java.awt.*;

class DimensionParser {

    static Dimension parse(String sizeTxt) {
        String[] parts = sizeTxt.split("x");
        return new Dimension(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }
}
