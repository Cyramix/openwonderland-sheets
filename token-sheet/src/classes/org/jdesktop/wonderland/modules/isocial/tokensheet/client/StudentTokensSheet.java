/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.wonderland.modules.isocial.tokensheet.client;

import org.jdesktop.wonderland.modules.isocial.tokensheet.client.presenters.StudentTokensPresenter;
import org.jdesktop.wonderland.modules.isocial.tokensheet.client.views.StudentTokenViewImpl;
import org.jdesktop.wonderland.modules.isocial.tokensheet.client.SPI.StudentTokensViewSPI;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import org.jdesktop.wonderland.client.hud.CompassLayout;
import org.jdesktop.wonderland.client.hud.HUD;
import org.jdesktop.wonderland.client.hud.HUDComponent;
import org.jdesktop.wonderland.client.hud.HUDManagerFactory;
import org.jdesktop.wonderland.modules.isocial.client.HUDDetailsWrapper;
import org.jdesktop.wonderland.modules.isocial.client.ISocialManager;
import org.jdesktop.wonderland.modules.isocial.client.view.SheetView;
import org.jdesktop.wonderland.modules.isocial.client.view.annotation.View;
import org.jdesktop.wonderland.modules.isocial.common.model.Role;
import org.jdesktop.wonderland.modules.isocial.common.model.Sheet;
import org.jdesktop.wonderland.modules.isocial.common.model.state.CSString;
import org.jdesktop.wonderland.modules.isocial.tokensheet.client.utils.UnitTokensRetriever;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.TokenSheet;

/**
 *
 * @author Ryan
 */
@View(value = TokenSheet.class, roles = Role.STUDENT)
public class StudentTokensSheet implements SheetView {

    private ISocialManager manager;
    private Sheet sheet;
    private TokenSheet tokenDetails;
    private static final String POSSIBLE_PER_STUDENT_PER_LESSON = "tokens.possible.per.student.per.lesson";
    private static final String POSSIBLE_PER_STUDENT_PER_UNIT = "tokens.possible.per.student.per.unit";
    private static final String NUMBER_OF_STUDENTS = "number.of.students";
    private Integer maxLessonTokens;
    private static final Logger logger = Logger.getLogger(StudentTokensSheet.class.getName());

    //THESE SCORES ARE UNIT-WIDE
    private Map<String, Integer> tokenScore;
    
    //THIS IS THE LESSON-WIDE TOKEN PROGRESS FOR THE LOCAL CLIENT
    private int currentTokensForThisLesson = 0;
    
    public StudentTokensSheet() {
        tokenScore = new HashMap<String, Integer>();
    }
    
    
    
    public void initialize(ISocialManager ism, Sheet sheet, Role role) {
        this.manager = ism;
        this.sheet = sheet;
        this.tokenDetails = (TokenSheet) sheet.getDetails();
        try {
            tokenScore.putAll(UnitTokensRetriever.retrieve(this));
        } catch (IOException ex) {
            Logger.getLogger(StudentTokensSheet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        logger.warning("STUDENT TOKENS SHEET INITIALIZE!");

    }

    public String getMenuName() {
        return "Tokens";
    }

    public boolean isAutoOpen() {
        return true;
    }

    public HUDDetailsWrapper open(HUD hud) {
        try {
            //create view
            JLabel label = new JLabel();
            
            Integer students = Integer.valueOf(getMaxStudents());
            Integer unitTokensPerStudent = Integer.valueOf(getMaxUnitTokens());
            
            StudentTokensViewSPI view = new StudentTokenViewImpl(label,
                                                    students*unitTokensPerStudent);
            
            /*
             * TEST DATA
             */

            students = 3;
            unitTokensPerStudent = 100;
            
            view = new StudentTokenViewImpl(label, students * unitTokensPerStudent);
            
            
            /*
             * END TEST DATA
             */


            //crate hud component
            final HUDComponent hc = hud().createComponent(label);
            hc.setDecoratable(false);
            hc.setPreferredLocation(CompassLayout.Layout.NORTHWEST);

            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    hud().addComponent(hc);
                    hc.setVisible(true);
                }
            });



            //create presenter
            StudentTokensPresenter presenter = new StudentTokensPresenter(this,
                    sheet.getId(),
                    view,
                    hc);


            //create HUDDetails wrapper
            HUDDetailsWrapper wrapper = new HUDDetailsWrapper(sheet.getName(),
                    hc,
                    label);



            //return wrapper
            return wrapper;
        } catch (IOException ex) {
            Logger.getLogger(StudentTokensSheet.class.getName()).log(Level.SEVERE, null, ex);
            return new HUDDetailsWrapper(null, null, null);
        }
    }

    public void close() {
    }

    public ISocialManager getManager() {
        return manager;
    }

    private String getMaxStudents() throws IOException {

        CSString state = (CSString) manager.getCohortState(NUMBER_OF_STUDENTS).getDetails();
        return state.getValue();
    }

    private String getMaxLessonTokens() throws IOException {

        return getUnitStringState(POSSIBLE_PER_STUDENT_PER_LESSON);

    }
    
    private String getMaxUnitTokens() throws IOException {
        
        return getUnitStringState(POSSIBLE_PER_STUDENT_PER_UNIT);
    }
    
    private String getUnitStringState(String stateKey) throws IOException {
        String unitId = manager.getCurrentInstance().getUnit().getId();
        CSString state = (CSString) manager.getCohortState(unitId + stateKey).getDetails();
        return state.getValue();
    }

    private HUD hud() {
        return HUDManagerFactory.getHUDManager().getHUD("main");
    }

    public void updateTokenScore(String name, int tokensValue) {
        synchronized(tokenScore) {
            Integer i = tokenScore.get(name);
            
            i += tokensValue;
            tokenScore.put(name, i);
        }
    }
    
    public Map<String, Integer> getTokenScore() {
        return tokenScore;
    }

    public int getCurrentLessonTokens() {
        return currentTokensForThisLesson;
    }
    
    public void setCurrentTokensForThisLesson(int tokens) {
        currentTokensForThisLesson = tokens;
    }
}
