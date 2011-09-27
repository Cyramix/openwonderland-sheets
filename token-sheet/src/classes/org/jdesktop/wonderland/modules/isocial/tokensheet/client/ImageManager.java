package org.jdesktop.wonderland.modules.isocial.tokensheet.client;

import java.awt.Color;

/**
 *
 * @author Kaustubh
 */
public class ImageManager {

    private static final String baseUrl = "/org/jdesktop/wonderland/modules/isocial/tokensheet/client/resources/";
    //This color collection is same as ColorStore in color-manager.
    private static final String[] ASSIGN_COLORS = new String[]{
        "#C02F64", // pink
        "#008848", // green
        "#005CA7", // blue
        "#A5CD39", // lime
        "#D1662C", // gold
        "#7E4298", // purple
        "#47A4AD", // turquoise
    };
    private static final String[] ASSIGN_IMG = new String[]{
        "pink.png",
        "green.png",
        "blue.png",
        "lime.png",
        "gold.png",
        "purple.png",
        "turquoise.png",};

    /**
     * This method returns the appropriate image name for the given color.
     * If no matching image name found, it returns the white image.
     * @param color
     * @return
     */
    public static String getImageNameFor(Color color) {
        for (int i = 0; i < ASSIGN_COLORS.length; i++) {
            String colorName = ASSIGN_COLORS[i];
            if (Color.decode(colorName).equals(color)) {
                return baseUrl + ASSIGN_IMG[i];
            }
        }
        return baseUrl + "white.png";
    }
}
