/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.wonderland.modules.isocial.tokensheet.client.SPI;

import java.util.Map;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.Student;

/**
 *
 * @author Ryan
 */
public interface TeamBehaviorViewSPI {
    public void update(Map<String, Student> behaviors);
}
