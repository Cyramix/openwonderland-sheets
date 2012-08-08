/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.wonderland.modules.isocial.tokensheet.client.presenters;

import org.jdesktop.wonderland.modules.isocial.tokensheet.client.SPI.StudentTokensViewSPI;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import org.jdesktop.wonderland.client.hud.HUD;
import org.jdesktop.wonderland.client.hud.HUDComponent;
import org.jdesktop.wonderland.modules.isocial.client.HUDDetailsWrapper;
import org.jdesktop.wonderland.modules.isocial.client.ISocialManager;
import org.jdesktop.wonderland.modules.isocial.client.view.ResultListener;
import org.jdesktop.wonderland.modules.isocial.client.view.SheetView;
import org.jdesktop.wonderland.modules.isocial.client.view.annotation.View;
import org.jdesktop.wonderland.modules.isocial.common.model.Result;
import org.jdesktop.wonderland.modules.isocial.common.model.Role;
import org.jdesktop.wonderland.modules.isocial.common.model.Sheet;
import org.jdesktop.wonderland.modules.isocial.tokensheet.client.StudentTokensSheet;
import org.jdesktop.wonderland.modules.isocial.tokensheet.client.utils.TokenResultHandler;
import org.jdesktop.wonderland.modules.isocial.tokensheet.client.audio.TokenSoundPlayer;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.ResultType;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.Student;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.TokenResult;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.TokenSheet;

/**
 *
 * @author Ryan
 */
//@View(value = TokenSheet.class, roles = Role.STUDENT)
public class StudentTokensPresenter extends TokenResultHandler {

    private TokenSheet sheetDetails;
    private final StudentTokensSheet model;
    private final StudentTokensViewSPI view;
    private final HUDComponent hudComponent;
    private static final Logger logger = Logger.getLogger(StudentTokensPresenter.class.getName());

    public StudentTokensPresenter(StudentTokensSheet model,
            String sheetID,
            StudentTokensViewSPI view,
            HUDComponent hudComponent) {

        this.model = model;
        this.view = view;
        this.hudComponent = hudComponent;

        manager().addResultListener(sheetID, this);
    }

    public void setVisible(final boolean visible) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                hudComponent.setVisible(visible);
            }
        });
    }

    private ISocialManager manager() {
        return model.getManager();
    }

    private boolean resultIsForMe(TokenResult result) {
        Student student = result.getStudentResult();

        String myName = manager().getUsername();
        String studentName = student.getName();

        return myName.equals(studentName);
    }

    private void updateModelAndView(Student student) {
       
        model.updateTokenScore(student.getName(), student.getTokensValue());               
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                view.updateImage(model.getCurrentLessonTokens(), model.getTokenScore());
            }
        });
    }

    @Override
    protected void tokenIncremented(TokenResult result) {
    /*
     * The student result here is local to the given lesson. 
     */
        Student student = result.getStudentResult();

        if (resultIsForMe(result)) {
            //play sound if this token was added for me.
            TokenSoundPlayer.playTokenSound();
            
            //set my current tokens to this amount
            logger.warning("SETTING MY TOKENS AMOUNT TO: "+student.getTokensValue());
            model.setCurrentTokensForThisLesson(student.getTokensValue());

        }
        
        updateModelAndView(student);

    }

    @Override
    protected void tokenDecremented(TokenResult result) {
        Student student = result.getStudentResult();
        
        logger.warning("SETTING MY TOKENS AMOUNT TO: "+student.getTokensValue());
        model.setCurrentTokensForThisLesson(student.getTokensValue());
        updateModelAndView(student);
    }

    @Override
    protected void passIncremented(TokenResult result) {
        logger.warning("PASS INCREMENTED");
    }

    @Override
    protected void passDecremented(TokenResult result) {
        logger.warning("PASS DECREMENTED");
    }

    @Override
    protected void strikeIncremented(TokenResult result) {
        logger.warning("STRIKE INCREMENTED");
    }

    @Override
    protected void strikeDecremented(TokenResult result) {
        logger.warning("STRIKE DECREMENTED");
    }
}
