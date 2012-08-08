/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.wonderland.modules.isocial.tokensheet.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.wonderland.modules.isocial.client.ISocialManager;
import org.jdesktop.wonderland.modules.isocial.common.model.Sheet;
import org.jdesktop.wonderland.modules.isocial.tokensheet.client.utils.LessonBehaviorsRetriever;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.Student;

/**
 *
 * @author Ryan
 */
public class TeamBehaviorReport {
    private ISocialManager manager = null;

    private Map<String, Student> teamBehaviors = null;
    
    /**
     *
     * @param manager
     * @param sheet
     */
    public TeamBehaviorReport(ISocialManager manager, String sheetId) {
        try {
            this.manager = manager;
            
            teamBehaviors = new HashMap<String, Student>();
            teamBehaviors.putAll(LessonBehaviorsRetriever.retrieve(manager, sheetId));
        } catch (IOException ex) {
            Logger.getLogger(TeamBehaviorReport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Map<String, Student> getBehaviors() {
        return teamBehaviors;
    }
    
    public void updateBehavior(String studentName, Student student) {
        synchronized(teamBehaviors) {
            teamBehaviors.put(studentName, student);
        }
    }
    
}
