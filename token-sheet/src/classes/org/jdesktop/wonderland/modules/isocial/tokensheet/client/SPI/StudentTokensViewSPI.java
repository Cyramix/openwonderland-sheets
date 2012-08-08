/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.wonderland.modules.isocial.tokensheet.client.SPI;

import java.beans.PropertyChangeListener;
import java.util.Map;

/**
 *
 * @author Ryan
 */
public interface StudentTokensViewSPI {

    public void updateImage(int currentLessonTokens, Map<String, Integer> tokenScore);
    
}
