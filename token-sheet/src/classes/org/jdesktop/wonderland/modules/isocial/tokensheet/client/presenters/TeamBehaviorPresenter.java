/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.wonderland.modules.isocial.tokensheet.client.presenters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.wonderland.modules.isocial.tokensheet.client.SPI.TeamBehaviorViewSPI;
import javax.swing.SwingUtilities;
import org.jdesktop.wonderland.client.hud.HUDComponent;
import org.jdesktop.wonderland.modules.isocial.client.ISocialManager;
import org.jdesktop.wonderland.modules.isocial.common.model.Result;
import org.jdesktop.wonderland.modules.isocial.tokensheet.client.TeamBehaviorReport;
import org.jdesktop.wonderland.modules.isocial.tokensheet.client.utils.TokenResultHandler;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.Student;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.TokenResult;

/**
 *
 * @author Ryan
 */
public class TeamBehaviorPresenter extends TokenResultHandler {

    private final HUDComponent hudComponent;
    private final TeamBehaviorReport model;
    private final String sheetId;
    private final TeamBehaviorViewSPI view;

    public TeamBehaviorPresenter(String sheetId,
            TeamBehaviorReport model,
            TeamBehaviorViewSPI view,
            HUDComponent hudComponent) {
        this.sheetId = sheetId;
        this.model = model;
        this.view = view;
        this.hudComponent = hudComponent;

        manager().addResultListener(sheetId, this);
        try {
            for(TokenResult tokenResult: tokenResultsFromManager()) {
                updateModelAndView(tokenResult);
                
            }
        } catch (IOException ex) {
            Logger.getLogger(TeamBehaviorPresenter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private Collection<TokenResult> tokenResultsFromManager() throws IOException {
        
        Collection<TokenResult> tokenResults = new ArrayList<TokenResult>();
        
        
        for(Result r: manager().getResults(sheetId)) {
            TokenResult tr = (TokenResult)r.getDetails();
            
            tokenResults.add(tr);
            
        }
        
        return tokenResults;
    }

    private ISocialManager manager() {
        return ISocialManager.INSTANCE;
    }

    public void setVisible(final boolean visible) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                hudComponent.setVisible(visible);
            }
        });
    }

    private void updateModelAndView(TokenResult result) {
        Student student = result.getStudentResult();
        
        model.updateBehavior(student.getName(), student);
        
        SwingUtilities.invokeLater(new Runnable() { 
            public void run() {
                view.update(model.getBehaviors());
            }
        
        });
    }
    
    @Override
    protected void tokenIncremented(TokenResult result) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void tokenDecremented(TokenResult result) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void passIncremented(TokenResult result) {
        updateModelAndView(result);
    }

    @Override
    protected void passDecremented(TokenResult result) {
        updateModelAndView(result);

    }

    @Override
    protected void strikeIncremented(TokenResult result) {
        updateModelAndView(result);

    }

    @Override
    protected void strikeDecremented(TokenResult result) {
        updateModelAndView(result);

    }

    public boolean getVisible() {
        return hudComponent.isVisible();
    }
}
