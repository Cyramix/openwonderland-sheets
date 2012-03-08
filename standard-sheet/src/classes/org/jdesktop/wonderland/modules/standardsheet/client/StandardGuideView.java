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
import javax.swing.DefaultComboBoxModel;
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
 * View for a guide that renders results from multiple students
 * @author Jonathan Kaplan <jonathankap@wonderbuilders.com>
 */
@View(value=StandardSheet.class, roles={Role.GUIDE, Role.ADMIN})
public class StandardGuideView extends javax.swing.JPanel
        implements SheetView, ResultListener
{
    private static final Logger LOGGER =
            Logger.getLogger(StandardGuideView.class.getName());

    private final DefaultComboBoxModel resultsModel;

    private ISocialManager manager;
    private Sheet sheet;

    private HUDComponent component;
    private StandardSheetPanel panel;
    
    
    public StandardGuideView() {
        initComponents();

        resultsModel = new DefaultComboBoxModel();
        studentsCB.setModel(resultsModel);
        
        panel = new StandardSheetPanel();
        panel.setEditable(false);
        scrollPane.setViewportView(panel);
    }

    public void initialize(ISocialManager manager, Sheet sheet, Role role) {
        this.manager = manager;
        this.sheet = sheet;
        
        manager.addResultListener(sheet.getId(), this);
        try {
            for (Result r : manager.getResults(sheet.getId())) {
                resultsModel.addElement(new NamedResult(r));
            } 
        } catch (IOException ioe) {
            LOGGER.log(Level.WARNING, "Errror getting results", ioe);
        }
    }

    public String getMenuName() {
        return ((StandardSheet) sheet.getDetails()).getName();
    }

    public boolean isAutoOpen() {
        return false;
    }

    public HUDComponent open(HUD hud) {
        if (component == null) {
            component = hud.createComponent(this);
        
            panel.setSize(component.getWidth() - 30, 1);
            panel.renderSheet((StandardSheet) sheet.getDetails());
        }
        
        // see if there is a result
        NamedResult nr = (NamedResult) studentsCB.getSelectedItem();
        if (nr != null) {
            panel.renderResults((StandardResult) nr.getResult().getDetails());
        }
        
        return component;
    }

    public void close() {
        manager.removeResultListener(sheet.getId(), this);
    }

    public void resultAdded(final Result result) {
        resultsModel.addElement(new NamedResult(result));
    }

    public void resultUpdated(final Result result) {
        for (int i = 0; i < resultsModel.getSize(); i++) {
            NamedResult nr = (NamedResult) resultsModel.getElementAt(i);
            if (nr.getResult().getCreator().equals(result.getCreator())) {
                nr.setResult(result);
            }
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        okButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        studentsCB = new javax.swing.JComboBox();
        scrollPane = new javax.swing.JScrollPane();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/jdesktop/wonderland/modules/standardsheet/client/resources/Bundle"); // NOI18N
        okButton.setText(bundle.getString("StandardGuideView.okButton.text")); // NOI18N
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        jLabel1.setText(bundle.getString("StandardGuideView.jLabel1.text")); // NOI18N

        studentsCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        studentsCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentsCBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(studentsCB, 0, 482, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(okButton)))
                .addContainerGap())
            .addComponent(scrollPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(studentsCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(okButton)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void studentsCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentsCBActionPerformed
        NamedResult nr = (NamedResult) studentsCB.getSelectedItem();
        if (nr != null && panel != null) {
            panel.renderResults((StandardResult) nr.getResult().getDetails());
        }
    }//GEN-LAST:event_studentsCBActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        component.setVisible(false);
    }//GEN-LAST:event_okButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton okButton;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JComboBox studentsCB;
    // End of variables declaration//GEN-END:variables

    class NamedResult {
        private Result result;

        public NamedResult(Result result) {
            this.result = result;
        }

        public Result getResult() {
            return result;
        }

        public void setResult(Result result) {
            this.result = result;
        }
        
        @Override
        public String toString() {
            return result.getCreator();
        }
    }
}
