/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.wonderland.modules.isocial.client;

import javax.swing.JComponent;
import org.jdesktop.wonderland.client.hud.HUDComponent;

/**
 *
 * @author Ryan
 */
public class HUDDetailsWrapper {
    private final HUDComponent hudComponent;
    private final JComponent component;
    private final String sheetName;

    public HUDDetailsWrapper(String sheetName, HUDComponent hudComponent, JComponent component) {
        this.sheetName = sheetName;
        this.hudComponent = hudComponent;
        this.component = component;
    }

    public JComponent getComponent() {
        return component;
    }

    public HUDComponent getHudComponent() {
        return hudComponent;
    }

    public String getSheetName() {
        return sheetName;
    }
    
    
}
