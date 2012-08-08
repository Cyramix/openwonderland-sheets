/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.wonderland.modules.isocial.tokensheet.client.utils;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import org.jdesktop.wonderland.modules.isocial.client.ISocialManager;
import org.jdesktop.wonderland.modules.isocial.common.model.Result;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.Student;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.TokenResult;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.TokenSheet;

/**
 *
 * @author Ryan
 */
public class LessonBehaviorsRetriever {
    
    private static final Logger logger = Logger.getLogger(LessonBehaviorsRetriever.class.getName());
    
    public static Map<String, Student> retrieve(ISocialManager manager, String sheetId) throws IOException {
        Map<String, Student> currentBehaviors = new HashMap<String, Student>();
        
        Collection<Result> results
                = manager.getResults(sheetId);
        
        
        logger.warning("RETRIEVED "+results.size()+" RESULTS!");
        for(Result result: results) {
            
            if(result.getDetails() instanceof TokenResult) {
                
                TokenResult tokenResult = (TokenResult)result.getDetails();
                
                Student studentResult = tokenResult.getStudentResult();
                
                logger.warning("PROCESSED RESULT FOR: "+studentResult.getName());
                
                currentBehaviors.put(studentResult.getName(), studentResult);
                                     
            }
        }
        
        return currentBehaviors;
    }
}
