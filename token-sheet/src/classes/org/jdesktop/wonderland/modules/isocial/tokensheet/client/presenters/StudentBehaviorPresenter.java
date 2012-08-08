/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.wonderland.modules.isocial.tokensheet.client.presenters;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.wonderland.modules.isocial.tokensheet.client.SPI.StudentBehaviorViewSPI;
import javax.swing.SwingUtilities;
import org.jdesktop.wonderland.client.hud.HUDComponent;
import org.jdesktop.wonderland.modules.isocial.client.ISocialManager;
import org.jdesktop.wonderland.modules.isocial.common.model.Result;
import org.jdesktop.wonderland.modules.isocial.tokensheet.client.StudentBehaviorSheet;
import org.jdesktop.wonderland.modules.isocial.tokensheet.client.utils.TokenResultHandler;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.Student;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.TokenResult;

/**
 *
 * @author Ryan
 */
public class StudentBehaviorPresenter extends TokenResultHandler {
    private final HUDComponent hudComponent;
    private final StudentBehaviorViewSPI view;
    private final StudentBehaviorSheet model;

    private static final Logger logger = Logger.getLogger(StudentBehaviorPresenter.class.getName());
    
    public StudentBehaviorPresenter(StudentBehaviorSheet model,
                                    String sheetId,
                                    StudentBehaviorViewSPI view,
                                    HUDComponent hc) {
        
        this.model = model;
        this.view = view;
        this.hudComponent = hc;
        
        manager().addResultListener(sheetId, this);
        try {
            for(Result r: manager().getResults(sheetId)) {
                if(r.getCreator().equals(manager().getUsername())) {
                    
                    TokenResult result = (TokenResult)r.getDetails();
                    Student studentResult = result.getStudentResult();
                    
                    logger.warning("FOUND RESULTS, PASSES: "
                                    +studentResult.getPassesValue()+
                                    " STRIKES: "+studentResult.getStrikesValue());
                    
                    updateModelAndView(studentResult);
                    
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(StudentBehaviorPresenter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
    }

    public void setVisible(final boolean visible) {
        SwingUtilities.invokeLater(new Runnable() { 
            public void  run() { 
                hudComponent.setVisible(visible);
            }
        
        });
    }
    
    private ISocialManager manager() {
        return model.getManager();
    }
    
    
    private void updateModelAndView(Student student) {
        model.updatePassesAndStrikes(student.getPassesValue(), student.getStrikesValue());
        
        SwingUtilities.invokeLater(new Runnable() { 
            public void run() {
                view.updateImage(model.getPasses(), model.getStrikes());
            }
        });
    }
    
    @Override
    protected void tokenIncremented(TokenResult result) {
        //do nothing
    }

    @Override
    protected void tokenDecremented(TokenResult result) {
        //do nothing
    }

    @Override
    protected void passIncremented(TokenResult result) {
        updateModelAndView(result.getStudentResult());
    }

    @Override
    protected void passDecremented(TokenResult result) {
        updateModelAndView(result.getStudentResult());
    }

    @Override
    protected void strikeIncremented(TokenResult result) {
        updateModelAndView(result.getStudentResult());
    }

    @Override
    protected void strikeDecremented(TokenResult result) {
        updateModelAndView(result.getStudentResult());
    }
    
}
