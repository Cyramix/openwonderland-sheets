/**
 * iSocial Project
 * http://isocial.missouri.edu
 *
 * Copyright (c) 2011, University of Missouri iSocial Project, All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * The iSocial project designates this particular file as
 * subject to the "Classpath" exception as provided by the iSocial
 * project in the License file that accompanied this code.
 */
package org.jdesktop.wonderland.modules.isocial.tokensheet.client.utils;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * This class manages the images for token system. It assigns one image per student
 * depending upon the student's color assignment. The assigned color and image
 * remains same per cohort.
 *
 * 
 * @author Kaustubh
 */
public class ImageAssigner {

    private static final Map<Color, String> colors;
    
    private static final String baseUrl = "/org/jdesktop/wonderland/modules/isocial/tokensheet/client/resources/";
    private static final String DEFAULT_PNG = "white.png";
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

    static {
        colors = new HashMap<Color, String>();
        
        colors.put(Color.decode("#C02F64"), "pink.png");
        colors.put(Color.decode("#008848"), "green.png");
        colors.put(Color.decode("#005CA7"), "blue.png");
        colors.put(Color.decode("#A5CD39"), "lime.png");
        colors.put(Color.decode("#D1662C"), "gold.png");
        colors.put(Color.decode("#7E4298"), "purple.png");
//        colors.put(Color.decode("#7E4298"), "purple.png");
        colors.put(Color.decode("#47A4AD"), "turquoise.png");
        
    }
    
    
    /**
     * This method returns the appropriate image name for the given color.
     * If no matching image name found, it returns the white image.
     * @param color
     * @return
     */
    public static String getImageNameFor(Color color) {
        
        
        if(colors.containsKey(color)) {
            return baseUrl + colors.get(color);
        } 
        
        return baseUrl + DEFAULT_PNG;
        
//        for (int i = 0; i < ASSIGN_COLORS.length; i++) {
//            String colorName = ASSIGN_COLORS[i];
//            if (Color.decode(colorName).equals(color)) {
//                return baseUrl + ASSIGN_IMG[i];
//            }
//        }
//        return baseUrl + "white.png";
    }
}
