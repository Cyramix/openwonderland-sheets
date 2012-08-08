/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.wonderland.modules.isocial.tokensheet.client.utils;

import java.awt.Color;

/**
 *
 * @author Ryan
 */
public class TokenMeterSection {
    private final String name;
    private final Color color;
    private final int amountOfTokens;
    
    public TokenMeterSection(String name,
                             Color color,
                             int amountOfTokens) {
        this.name = name;
        this.color = color;
        this.amountOfTokens = amountOfTokens;
    }

    public int getAmountOfTokens() {
        return amountOfTokens;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }
    
    
}
