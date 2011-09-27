package org.jdesktop.wonderland.modules.isocial.tokensheet.client;

/**
 *
 * @author Kaustubh
 */
public class CustomDimension {

    private int x;
    private int y;
    private int width;
    private int height;

    public CustomDimension(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
