package com.je.gui.xml;

import java.awt.*;

class ColorParser {

    static Color parse(String colorTxt) {
        return switch (colorTxt) {
            case "red"    -> Color.RED;
            case "green"  -> Color.GREEN;
            case "blue"   -> Color.BLUE;
            case "gray"   -> Color.GRAY;
            case "black"  -> Color.BLACK;
            case "white"  -> Color.WHITE;
            case "orange" -> Color.ORANGE;
            case "pink"   -> Color.PINK;
            default       -> parseRgb(colorTxt);
        };
    }

    private static Color parseRgb(String colorTxt) {
        String[] parts = colorTxt.split(",");
        return new Color(
                Integer.parseInt(parts[0].trim()),
                Integer.parseInt(parts[1].trim()),
                Integer.parseInt(parts[2].trim())
        );
    }
}
