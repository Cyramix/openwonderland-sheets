/**
 * iSocial Project
 * http://isocial.missouri.edu
 *
 * Copyright (c) 2011, University of Missouri iSocial Project, All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * The iSocial project designates this particular file as
 * subject to the "Classpath" exception as provided by the iSocial
 * project in the License file that accompanied this code.
 */
package org.jdesktop.wonderland.modules.isocial.tokensheet.client;

import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import org.jdesktop.wonderland.client.hud.CompassLayout.Layout;
import org.jdesktop.wonderland.client.hud.HUD;
import org.jdesktop.wonderland.client.hud.HUDComponent;
import org.jdesktop.wonderland.modules.isocial.client.ISocialManager;
import org.jdesktop.wonderland.modules.isocial.client.view.ResultListener;
import org.jdesktop.wonderland.modules.isocial.client.view.SheetView;
import org.jdesktop.wonderland.modules.isocial.client.view.annotation.View;
import org.jdesktop.wonderland.modules.isocial.common.model.Result;
import org.jdesktop.wonderland.modules.isocial.common.model.Role;
import org.jdesktop.wonderland.modules.isocial.common.model.Sheet;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.TokenResult;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.TokenSheet;

/**
 * Token student view.
 * @author Jonathan Kaplan <Jonathankap@wonderbuilders.com>
 * @author Ryan Babiuch
 * @author Kaustubh
 */
@View(value = TokenSheet.class, roles = Role.STUDENT)
public class TokenStudentView
        implements SheetView, ResultListener {

    private static final Logger LOGGER =
            Logger.getLogger(TokenStudentView.class.getName());
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/isocial/tokensheet/client/Bundle");
    private ISocialManager manager;
    private Sheet sheet;
    private Role role;
    private TokenStudentPanel panel;
    private HUDComponent component;
    private URL audioSource;
    private Color color;
    private JLabel tokenLabel;

    public void initialize(ISocialManager manager, Sheet sheet, Role role) {
        this.manager = manager;
        this.sheet = sheet;
        this.role = role;
        this.panel = new TokenStudentPanel(manager);
        this.audioSource = getClass().getResource(BUNDLE.getString("audioSource"));
        manager.addResultListener(sheet.getId(), this);
        try {
            Collection<Result> results = manager.getResults(sheet.getId());
            Result myResult = null;
            for (Result r : results) {
                if (r.getCreator().equals(manager.getUsername())) {
                    myResult = r;
                    panel.updateStudentTokens((TokenResult) r.getDetails());
                }
            }
            if (myResult != null) {
                results.remove(myResult);
            }
            panel.updateTokens(results);
        } catch (IOException ioe) {
            LOGGER.log(Level.WARNING, "Error reading results", ioe);
        }
    }

    public String getMenuName() {
        return ((TokenSheet) sheet.getDetails()).getName();
    }

    public boolean isAutoOpen() {
        //return ((TokenSheet) sheet.getDetails()).isAutoOpen();
        return true;
    }

    public HUDComponent open(HUD hud) {
        ImageIcon imageIcon = panel.getImageIcon();
        tokenLabel = new JLabel(imageIcon);
        tokenLabel.setOpaque(false);
        component = hud.createComponent(tokenLabel);
        //component = hud.createImageComponent(imageIcon);
        component.setDecoratable(false);
        component.setPreferredLocation(Layout.NORTHWEST);
        component.setTransparency(1.0f);
        return component;
    }

    public void close() {
        manager.removeResultListener(sheet.getId(), this);
    }

    public void resultAdded(final Result result) {
//        if (result.getCreator().equals(manager.getUsername())) {
//            try {
//                SoftphoneControlImpl.getInstance().sendCommandToSoftphone("playFile=" + audioSource.toString() + "=" + 50);
//            } catch (IOException ex) {
//                Logger.getLogger(TokenStudentView.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                panel.resetImage();
                try {
                    panel.updateTokens(manager.getResults(sheet.getId()));
                    tokenLabel.repaint();
                } catch (IOException ex) {
                    Logger.getLogger(TokenStudentView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void resultUpdated(final Result result) {
//        if (result.getCreator().equals(manager.getUsername())) {
//            try {
//                SoftphoneControlImpl.getInstance().sendCommandToSoftphone("playFile=" + audioSource.toString() + "=" + 50);
//            } catch (IOException ex) {
//                Logger.getLogger(TokenStudentView.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                panel.resetImage();
                try {
                    panel.updateTokens(manager.getResults(sheet.getId()));
                    tokenLabel.repaint();
                } catch (IOException ex) {
                    Logger.getLogger(TokenStudentView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
