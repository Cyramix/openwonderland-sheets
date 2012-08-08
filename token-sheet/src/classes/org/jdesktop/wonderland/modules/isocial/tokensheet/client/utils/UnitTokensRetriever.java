/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.wonderland.modules.isocial.tokensheet.client.utils;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.jdesktop.wonderland.modules.isocial.client.ISocialManager;
import org.jdesktop.wonderland.modules.isocial.common.model.Result;
import org.jdesktop.wonderland.modules.isocial.tokensheet.client.StudentTokensSheet;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.Student;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.TokenResult;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.TokenSheet;

/**
 *
 * @author Ryan
 */
public class UnitTokensRetriever {
    
    public static Map<String, Integer> retrieve(StudentTokensSheet model) throws IOException {
        
        // our dataset to be returned
        Map<String, Integer> currentTokens = new HashMap<String, Integer>();
        
        
        //get sheet manager so we can retrieve the data we want
        ISocialManager manager = model.getManager();
        
        //get all results for the current unit of the given type
        Collection<Result> results
                = manager.getCurrentUnitResults(new TokenSheet());
        
        //for every result in the current unit
        for(Result result: results) {
            
            //check that the result is of the proper type
            if(result.getDetails() instanceof TokenResult) {
                TokenResult tokenResult = (TokenResult)result.getDetails();
                
                
                Student studentResult = tokenResult.getStudentResult();  
                
                //results are non-relative so we only deal with aboslute values
                //here. Ergo, we never need to "update" data, we'll only ever
                //put data.
                currentTokens.put(studentResult.getName(),
                                  studentResult.getTokensValue());
            }
        }

        return currentTokens;
    }
    
}
