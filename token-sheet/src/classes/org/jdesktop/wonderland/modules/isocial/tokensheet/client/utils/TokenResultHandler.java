/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.wonderland.modules.isocial.tokensheet.client.utils;

import java.util.logging.Logger;
import org.jdesktop.wonderland.modules.isocial.common.model.Result;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.Student;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.TokenResult;

/**
 *
 * @author Ryan
 */
public abstract class TokenResultHandler extends ResultHandler {

    private static final Logger logger = Logger.getLogger(TokenResultHandler.class.getName());
    
    protected abstract void tokenIncremented(TokenResult result);

    protected abstract void tokenDecremented(TokenResult result);

    protected abstract void passIncremented(TokenResult result);

    protected abstract void passDecremented(TokenResult result);

    protected abstract void strikeIncremented(TokenResult result);

    protected abstract void strikeDecremented(TokenResult result);

    @Override
    protected void processAndReactToResult(Result result) {
        if (result.getDetails() instanceof TokenResult) {

            TokenResult tr = (TokenResult) result.getDetails();
            
            Student student = tr.getStudentResult();
            logger.warning("RECEIVED RESULT FOR STUDENT: " + student.getName() +
                                    " AND CREATED BY: " + result.getCreator());

            switch (tr.getType()) {
                case TOKEN_INCREMENT:
                    tokenIncremented(tr);
                    break;
                case TOKEN_DECREMENT:
                    tokenDecremented(tr);
                    break;
                case PASS_INCREMENT:
                    passIncremented(tr);
                    break;
                case PASS_DECREMENT:
                    passDecremented(tr);
                    break;
                case STRIKE_INCREMENT:
                    strikeIncremented(tr);
                    break;
                case STRIKE_DECREMENT:
                    strikeDecremented(tr);
                    break;
            }

        } else {
            throw new UnsupportedOperationException("RESULT IS NOT TOKEN RESULT!");
        }
    }
}
