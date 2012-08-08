/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.wonderland.modules.isocial.tokensheet.client;

import org.jdesktop.wonderland.modules.isocial.tokensheet.client.presenters.StudentBehaviorPresenter;
import org.jdesktop.wonderland.modules.isocial.tokensheet.client.SPI.StudentBehaviorViewSPI;
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
import org.jdesktop.wonderland.modules.isocial.tokensheet.client.views.StudentBehaviorViewImpl;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.TokenSheet;

/**
 *
 * @author Ryan
 */
@View(value = TokenSheet.class, roles = Role.STUDENT)
public class StudentBehaviorSheet  implements SheetView {

    
    private int passes = 0;
    private int strikes = 0;
    private TokenSheet tokenDetails;
    private Sheet sheet;
    private ISocialManager manager;
    
    public void initialize(ISocialManager ism, Sheet sheet, Role role) {
        this.manager = ism;
        
        this.sheet = sheet;
        this.tokenDetails = (TokenSheet)sheet.getDetails();
        
        
    }

    public String getMenuName() {
        return "Student Behavior";
    }

    public boolean isAutoOpen() {
        return true;
    }

    public HUDDetailsWrapper open(HUD hud) {
        JLabel label = new JLabel();
        label.addMouseListener(new UnobtrusiveViewPresenter(sheet.getId()));
        StudentBehaviorViewSPI view = new StudentBehaviorViewImpl(label);
       
        final HUDComponent hc = hud().createComponent(label);
        hc.setDecoratable(false);
        hc.setPreferredLocation(CompassLayout.Layout.NORTHEAST);
        
        SwingUtilities.invokeLater(new Runnable() { 
            public void run() {
                hud().addComponent(hc);
                hc.setVisible(true);
            }
        
        });
        
        StudentBehaviorPresenter presenter = 
                new StudentBehaviorPresenter(this, sheet.getId(), view, hc);
        
        HUDDetailsWrapper wrapper = new HUDDetailsWrapper(sheet.getName(),
                                                          hc,
                                                          label);
        
        return wrapper;
        
    }

    public void close() {

    }

    private HUD hud() {
        return HUDManagerFactory.getHUDManager().getHUD("main");
    }

    public ISocialManager getManager() {
        return ISocialManager.INSTANCE;
    }

    public void updatePassesAndStrikes(int passesValue, int strikesValue) {
        this.passes = passesValue;
        this.strikes = strikesValue;
    }
    
    public int getPasses() {
        return passes;
    }
    
    public int getStrikes() {
        return strikes;
    }
    
}
