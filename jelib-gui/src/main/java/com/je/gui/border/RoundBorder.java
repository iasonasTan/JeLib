package com.je.gui.border;

import javax.swing.border.Border;
import java.awt.*;

public final class RoundBorder implements Border {
    private final Color mColor;
    private final Color mBackground;
    private final int mRadius;

    public RoundBorder(Color mBackground, Color color, int radius) {
        this.mColor = color;
        this.mBackground = mBackground;
        this.mRadius = radius;
    }

    @Override
    public void paintBorder(Component component, Graphics graphics, int x, int y, int width, int height) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(mBackground);
        graphics2D.fillRoundRect(x, y, width-1, height-1, mRadius, mRadius);
        graphics2D.setColor(mColor);
        graphics2D.drawRoundRect(x, y, width-1, height-1, mRadius, mRadius);
    }

    @Override
    public Insets getBorderInsets(Component component) {
        return new Insets(mRadius / 2, mRadius / 2, mRadius / 2, mRadius / 2);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}
