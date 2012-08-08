/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.wonderland.modules.isocial.tokensheet.client.SPI;

import java.util.Map;
import javax.swing.JPanel;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.Student;

/**
 *
 * @author Ryan
 */
public abstract class TeamBehaviorViewSPI extends JPanel {
    
    public abstract void update(Map<String, Student> behaviors);
}
