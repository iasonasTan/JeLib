package com.je.gui.layout;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This layout manager aligns the components vertically from top to bottom.
 * Component will have their preferred size.
 * If the container is out of height, a new column will appear in the right.
 */
public final class VerticalFlowLayout implements LayoutManager2 {
    /**
     * Components will be stored here.
     */
    private final List<Component> mComponents = new ArrayList<>();

    /**
     * Vertical gaps between two consecutive GUI components.
     */
    public int mVeGap;

    /**
     * Horizontal gaps between two consecutive GUI components.
     */
    public int mHoGap;

    /**
     * Constructs a VerticalFlowLayout with given gaps.
     * @param hGap gap between two horizontally consecutive gui components in pixels.
     * @param vGap gap between vertically consecutive gui components in pixels.
     */
    public VerticalFlowLayout(int hGap, int vGap) {
        this.mHoGap = hGap;
        this.mVeGap = vGap;
    }

    /**
     * Constructs a VerticalFlowLayout with horizontal and vertical gaps set to zero.
     */
    public VerticalFlowLayout () {
        this(0, 0);
    }

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        mComponents.add(comp);
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0;
    }

    @Override
    public void invalidateLayout(Container target) {

    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
        mComponents.add(comp);
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        mComponents.remove(comp);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        Dimension out=new Dimension();
        for (Component c: mComponents) {
            final int RIGHT = c.getPreferredSize().width+c.getX();
            if (out.width < RIGHT) {
                out.width = RIGHT;
            }

            final int DOWN = c.getPreferredSize().height+c.getY();
            if (out.height < DOWN) {
                out.height = DOWN;
            }
        }
        return out;
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return preferredLayoutSize(target);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return preferredLayoutSize(parent);
    }

    @Override
    public void layoutContainer(Container parent) {
        int x = mHoGap;
        int y = mVeGap;

        // That's where next column's x will be.
        int maxWidth = 0;

        final Dimension parentSize = parent.getPreferredSize();

        for (Component c: mComponents) {
            if (!c.isVisible()) continue; // Layout only visible components.

            final Dimension componentSize = c.getPreferredSize();

            // Update maxWidth
            if (maxWidth < componentSize.width) {
                maxWidth = componentSize.width;
            }

            // Change component's x and y coordinates.
            c.setBounds(x, y, componentSize.width, componentSize.height);

            // Add component height and vertical gap to y.
            y+=componentSize.height+ mVeGap;

            // Go to next column if components are going to cross the limit.
            if (y+componentSize.height > parentSize.height) {
                y = mVeGap;
                x+= mHoGap +maxWidth;
                maxWidth = mVeGap;
            }

        }

    }
}
