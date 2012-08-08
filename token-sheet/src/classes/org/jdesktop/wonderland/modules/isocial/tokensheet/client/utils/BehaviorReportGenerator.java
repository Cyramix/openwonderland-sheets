/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.wonderland.modules.isocial.tokensheet.client.utils;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.jdesktop.wonderland.modules.isocial.tokensheet.client.views.brushes.PassStrikeBrush;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.Student;

/**
 *
 * @author Ryan
 */
public class BehaviorReportGenerator {

    private static final PassStrikeBrush brush = new PassStrikeBrush();
    
    public static JLabel generateView(Student student) {
        JLabel view = new JLabel();
                
        Image image = brush.paintScoreBoard(student.getPassesValue(), student.getStrikesValue());
        
        ImageIcon icon = new ImageIcon(image);
        
        view.setIcon(icon);
        
        view.setVerticalTextPosition(JLabel.TOP);
        view.setHorizontalTextPosition(JLabel.LEFT);
        view.setHorizontalAlignment(JLabel.LEFT);
        
        view.setText(student.getName());
        
        return view;
    }
}
