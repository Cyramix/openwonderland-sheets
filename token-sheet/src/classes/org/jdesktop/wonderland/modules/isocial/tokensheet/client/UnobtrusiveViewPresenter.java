/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.wonderland.modules.isocial.tokensheet.client;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.SwingUtilities;
import org.jdesktop.wonderland.client.hud.CompassLayout;
import org.jdesktop.wonderland.client.hud.HUD;
import org.jdesktop.wonderland.client.hud.HUDComponent;
import org.jdesktop.wonderland.client.hud.HUDManagerFactory;
import org.jdesktop.wonderland.modules.isocial.client.ISocialManager;
import org.jdesktop.wonderland.modules.isocial.tokensheet.client.SPI.TeamBehaviorViewSPI;
import org.jdesktop.wonderland.modules.isocial.tokensheet.client.presenters.TeamBehaviorPresenter;
import org.jdesktop.wonderland.modules.isocial.tokensheet.client.views.TeamBehaviorViewImpl;

/**
 *
 * @author Ryan
 */
public class UnobtrusiveViewPresenter implements MouseListener {
    private TeamBehaviorPresenter presenter = null;
    private final String sheetId;
    private boolean initialized;

    public UnobtrusiveViewPresenter(String sheetId) {
        
        this.sheetId = sheetId;
        
    }

    public void mouseClicked(MouseEvent me) {
        if(!initialized) {
            initialized = true;
            createUnobtrusiveView(sheetId);
        }
        
        //create all behavior view.
        presenter.setVisible(!presenter.getVisible());
        
    }

    public void mousePressed(MouseEvent me) {
        //do nothing
    }

    public void mouseReleased(MouseEvent me) {
        //do nothing
    }

    public void mouseEntered(MouseEvent me) {
        //do nothing
    }

    public void mouseExited(MouseEvent me) {
        //do nothing
    }
 
    private HUD hud() {
        return HUDManagerFactory.getHUDManager().getHUD("main");
    }
    
    private ISocialManager manager() {
        return ISocialManager.INSTANCE;
    }

    private void createUnobtrusiveView(String sheetId) {
        //create model
        TeamBehaviorReport model = new TeamBehaviorReport(manager(), sheetId);
        
        
        //create view
        TeamBehaviorViewSPI view = new TeamBehaviorViewImpl();
        
        
        //create hud component stuff
        final HUDComponent hc = hud().createComponent(view);
        hc.setDecoratable(false);
        hc.setPreferredLocation(CompassLayout.Layout.EAST);
        SwingUtilities.invokeLater(new Runnable() { 
            public void run() {
                hud().addComponent(hc);
                hc.setVisible(false);
            }
        });
        
         this.presenter = new TeamBehaviorPresenter(sheetId, model, view, hc);
    }
    
}
