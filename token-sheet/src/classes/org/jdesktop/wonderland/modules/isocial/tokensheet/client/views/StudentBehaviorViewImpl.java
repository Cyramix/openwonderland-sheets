/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.wonderland.modules.isocial.tokensheet.client.views;

import java.awt.Image;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.jdesktop.wonderland.modules.isocial.tokensheet.client.SPI.StudentBehaviorViewSPI;
import org.jdesktop.wonderland.modules.isocial.tokensheet.client.views.brushes.PassStrikeBrush;

/**
 *
 * @author Ryan
 */
public class StudentBehaviorViewImpl implements StudentBehaviorViewSPI {

    private JLabel swingView = null;
    private static Logger logger = Logger.getLogger(StudentBehaviorViewImpl.class.getName());
    private PassStrikeBrush brush = null;
    
    public StudentBehaviorViewImpl(JLabel label) {
        swingView = label;
        brush = new PassStrikeBrush();
        
        logger.warning("CREATING STUDENT BEHAVIOR VIEW!");
        updateImage(0, 0);
    }
    
    
    public void updateImage(int passes, int strikes) {
        Image img = brush.paintScoreBoard(passes, strikes);
        ImageIcon icon = new ImageIcon(img);
        
        swingView.setIcon(icon);
    }
    
}
