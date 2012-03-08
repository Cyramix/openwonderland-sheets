/**
 * Open Wonderland
 *
 * Copyright (c) 2012, Open Wonderland Foundation, All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above copyright and
 * this condition.
 *
 * The contents of this file are subject to the GNU General Public License,
 * Version 2 (the "License"); you may not use this file except in compliance
 * with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/gpl-license.php.
 *
 * The Open Wonderland Foundation designates this particular file as subject to
 * the "Classpath" exception as provided by the Open Wonderland Foundation in
 * the License file that accompanied this code.
 */

/**
 * WonderBuilders, Inc.
 *
 * Copyright (c) 2012, WonderBuilders, Inc., All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above copyright and
 * this condition.
 *
 * The contents of this file are subject to the GNU General Public License,
 * Version 2 (the "License"); you may not use this file except in compliance
 * with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/gpl-license.php.
 *
 * WonderBuilders, Inc. designates this particular file as subject to the
 * "Classpath" exception as provided WonderBuilders, Inc. in the License file
 * that accompanied this code.
 */     
package org.jdesktop.wonderland.modules.standardsheet.client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.wonderland.client.hud.HUD;
import org.jdesktop.wonderland.client.hud.HUDComponent;
import org.jdesktop.wonderland.modules.isocial.client.ISocialManager;
import org.jdesktop.wonderland.modules.isocial.client.view.ResultListener;
import org.jdesktop.wonderland.modules.isocial.client.view.SheetView;
import org.jdesktop.wonderland.modules.isocial.client.view.annotation.View;
import org.jdesktop.wonderland.modules.isocial.common.model.Result;
import org.jdesktop.wonderland.modules.isocial.common.model.Role;
import org.jdesktop.wonderland.modules.isocial.common.model.Sheet;
import org.jdesktop.wonderland.modules.standardsheet.common.StandardResult;
import org.jdesktop.wonderland.modules.standardsheet.common.StandardSheet;

/**
 * Standard sheet view for a single student.
 * @author Jonathan Kaplan <jonathankap@wonderbuilders.com>
 */
@View(value=StandardSheet.class, roles=Role.STUDENT)
public class StandardStudentView extends javax.swing.JPanel 
    implements SheetView, ResultListener
{
    private static final Logger LOGGER =
            Logger.getLogger(StandardStudentView.class.getName());
    
    private final StandardSheetPanel sheetPanel;
    
    private ISocialManager manager;
    private Sheet sheet;
    private Result currentResult;
    private HUDComponent component;
    
    /**
     * Creates new form StandardStudentView
     */
    public StandardStudentView() {
        initComponents();
        
        sheetPanel = new StandardSheetPanel();
        scrollPane.setViewportView(sheetPanel);
    }
    
    public void initialize(ISocialManager manager, Sheet sheet, Role role) {
        this.manager = manager;
        this.sheet = sheet;    
    }

    public String getMenuName() {
        return sheet.getName();
    }

    public boolean isAutoOpen() {
        return false;
    }

    public HUDComponent open(HUD hud) {
        if (component == null) {
            component = hud.createComponent(this);
        }
        
        sheetPanel.setSize(component.getWidth() - 30, 1);
        sheetPanel.renderSheet((StandardSheet) sheet.getDetails());
        
        // see if we already have a result
        manager.addResultListener(sheet.getId(), this);
        currentResult = null;
        try {
            for (Result r : manager.getResults(sheet.getId())) {
                if (r.getCreator().equals(manager.getUsername())) {
                    currentResult = r;
                    sheetPanel.renderResults((StandardResult) r.getDetails());
                }
            }
        } catch (IOException ioe) {
            LOGGER.log(Level.WARNING, "Error reading results", ioe);
        }
        
        return component;
    }

    public void close() {
        manager.removeResultListener(sheet.getId(), this);
    }
    
    public void resultAdded(Result result) {
        if (result.getCreator().equals(manager.getUsername())) {
            currentResult = result;
            sheetPanel.renderResults((StandardResult) result.getDetails());
        }
    }

    public void resultUpdated(Result result) {
        if (result.getCreator().equals(manager.getUsername())) {
            currentResult = result;
            sheetPanel.renderResults(((StandardResult) result.getDetails()));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        submitButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/jdesktop/wonderland/modules/standardsheet/client/resources/Bundle"); // NOI18N
        submitButton.setText(bundle.getString("StandardStudentView.submitButton.text")); // NOI18N
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        cancelButton.setText(bundle.getString("StandardStudentView.cancelButton.text")); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 212, Short.MAX_VALUE)
                .addComponent(cancelButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(submitButton))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(submitButton)
                    .addComponent(cancelButton)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        component.setVisible(false);
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
        try {
            if (currentResult == null) {
                manager.submitResult(sheet.getId(), sheetPanel.getResults());
            } else {
                manager.updateResult(currentResult.getId(), sheetPanel.getResults());
            }
            
            component.setVisible(false);
        } catch (IOException ioe) {
            LOGGER.log(Level.WARNING, "Error submitting response", ioe);
        }
    }//GEN-LAST:event_submitButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JButton submitButton;
    // End of variables declaration//GEN-END:variables

}