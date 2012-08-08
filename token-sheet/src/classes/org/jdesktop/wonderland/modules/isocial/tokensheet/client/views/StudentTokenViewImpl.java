/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.wonderland.modules.isocial.tokensheet.client.views;

import org.jdesktop.wonderland.modules.isocial.tokensheet.client.SPI.StudentTokensViewSPI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import org.jdesktop.wonderland.client.hud.CompassLayout;
import org.jdesktop.wonderland.client.hud.HUD;
import org.jdesktop.wonderland.client.hud.HUDComponent;
import org.jdesktop.wonderland.client.hud.HUDManagerFactory;
import org.jdesktop.wonderland.modules.isocial.tokensheet.client.utils.ImageAssigner;
import org.jdesktop.wonderland.modules.isocial.tokensheet.client.views.brushes.ScoreMeterBrush;
import org.jdesktop.wonderland.modules.isocial.tokensheet.client.utils.TokenMeterSection;

/**
 *
 * @author Ryan
 */
public class StudentTokenViewImpl implements StudentTokensViewSPI {

    private JLabel swingView;
    private Logger logger = Logger.getLogger(StudentTokenViewImpl.class.getName());
    private ScoreMeterBrush brush;
    
    public StudentTokenViewImpl(JLabel label,
                                int maxTokens,
                                int initialCurrentTokens,
                                Map<String, Integer> initialScore) {
        swingView = label;
        brush = new ScoreMeterBrush(maxTokens);
        
        /*
         * TEST DATA
         */
//        brush.addSection(new TokenMeterSection("C", Color.red, 100));
//        brush.addSection(new TokenMeterSection("B", Color.yellow, 100));
//        brush.addSection(new TokenMeterSection("A", Color.blue, 100));
//        
        /*
         * END TEST DATA
         */
        
        
        
        logger.warning("CREATING STUDENT TOKEN VIEW!");
        updateImage(initialCurrentTokens, initialScore);

    }
    
    public void updateImage(int currentLessonTokens, Map<String, Integer> tokenScore) {
        
        //for every score...
        for(Map.Entry<String,Integer> e: tokenScore.entrySet()) {
            
            //retrieve color somehow
            Color color = retrieveColorForName(e.getKey());
            
            
            //add result as section to the painter
            brush.addSection(new TokenMeterSection(e.getKey(),
                                                     color,
                                                     e.getValue()));
                    
                    
        }
        
        
        ImageIcon icon = new ImageIcon(brush.paintMeter(currentLessonTokens));
        swingView.setIcon(icon);
    }

    private void retrieveImage() {
        Image img = brush.paintMeter(10);
        
        ImageIcon icon = new ImageIcon(img);
        
        
        if(icon == null) {
            logger.warning("ICON IS NULL INSIDE TOKEN SHEET VIEW!");
        } else {
//            logger.warning("ICON HEIGHT: "+icon.getIconHeight() +" ICON WIDTH: "+icon.getIconWidth());
        }
        swingView.setIcon(icon);
//        logger.warning("LABEL HEIGHT: "+swingView.getHeight() +" LABEL WIDTH: "+swingView.getWidth());

    }

    private Color retrieveColorForName(String key) {
        /*
         * Right now GREEN is used. In the future, this method should use
         * the ColorManager module to assign and retrieve colors.
         */
        return Color.GREEN;
    }


}
