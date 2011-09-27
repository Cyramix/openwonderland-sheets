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
package org.jdesktop.wonderland.modules.isocial.sample.client;

import java.util.ResourceBundle;
import java.util.logging.Logger;
import org.jdesktop.wonderland.modules.isocial.client.ISocialManager;
import org.jdesktop.wonderland.modules.isocial.common.model.Result;
import org.jdesktop.wonderland.modules.isocial.common.model.Sheet;
import org.jdesktop.wonderland.modules.isocial.sample.common.SampleResult;
import org.jdesktop.wonderland.modules.isocial.sample.common.SampleSheet;

/**
 * Panel for viewing a sample
 * @author Jonathan Kaplan <jonathankap@wonderbuilders.com>
 */
public class SampleViewPanel extends javax.swing.JPanel {
    private static final Logger LOGGER =
            Logger.getLogger(SampleViewPanel.class.getName());
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/isocial/sample/client/Bundle");

    private final ISocialManager manager;
    private final Sheet sheet;

    /** Creates new form Sample1View */
    public SampleViewPanel(ISocialManager manager, Sheet sheet)
    {
        this.manager = manager;
        this.sheet = sheet;

        initComponents();

        SampleSheet ss1 = (SampleSheet) sheet.getDetails();
        questionArea.setText(ss1.getQuestion());
        answerArea.setText("");
    }

    /**
     * Set the view with the given result. This will disable the submit
     * button and fill in results from the given result.
     * @param result the result to set this sheet from
     */
    public void setResult(Result result) {
        SampleResult details = (SampleResult) result.getDetails();

        answerArea.setText(details.getAnswer());
        answerArea.setEditable(false);

        submitButton.setEnabled(false);
    }

    /**
     * Get a ResultDetails object from this view.
     * @return a SampleResultDetails object filled in with the details from this
     * view
     */
    public SampleResult getResultDetails() {
        SampleResult details = new SampleResult();
        details.setAnswer(answerArea.getText());

        return details;
    }

    /**
     * Toggle this panel editable
     */
    public void setEditable(boolean editable) {
        answerArea.setEditable(editable);
        submitButton.setEnabled(editable);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        questionArea = new javax.swing.JTextArea();
        answerArea = new javax.swing.JTextField();
        submitButton = new javax.swing.JButton();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/jdesktop/wonderland/modules/isocial/sample/client/Bundle"); // NOI18N
        jLabel1.setText(bundle.getString("SampleViewPanel.jLabel1.text")); // NOI18N

        jScrollPane1.setBackground(javax.swing.UIManager.getDefaults().getColor("Panel.background"));
        jScrollPane1.setBorder(null);
        jScrollPane1.setEnabled(false);
        jScrollPane1.setFocusable(false);

        questionArea.setBackground(new java.awt.Color(238, 238, 238));
        questionArea.setColumns(20);
        questionArea.setEditable(false);
        questionArea.setLineWrap(true);
        questionArea.setRows(5);
        questionArea.setText(bundle.getString("SampleViewPanel.questionArea.text")); // NOI18N
        questionArea.setWrapStyleWord(true);
        questionArea.setBorder(null);
        jScrollPane1.setViewportView(questionArea);

        answerArea.setText(bundle.getString("SampleViewPanel.answerArea.text")); // NOI18N

        submitButton.setText(bundle.getString("SampleViewPanel.submitButton.text")); // NOI18N
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addComponent(answerArea, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                    .addComponent(submitButton, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(answerArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(submitButton)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
        firePropertyChange("submit", null, answerArea.getText());
    }//GEN-LAST:event_submitButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField answerArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea questionArea;
    private javax.swing.JButton submitButton;
    // End of variables declaration//GEN-END:variables

}
