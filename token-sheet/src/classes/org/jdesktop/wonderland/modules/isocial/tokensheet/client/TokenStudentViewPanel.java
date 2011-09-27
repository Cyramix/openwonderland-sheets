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
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.plaf.PanelUI;
import javax.swing.plaf.basic.BasicPanelUI;
import javax.swing.plaf.basic.BasicProgressBarUI;
import org.jdesktop.wonderland.modules.isocial.client.ISocialManager;
import org.jdesktop.wonderland.modules.isocial.common.model.Result;
import org.jdesktop.wonderland.modules.isocial.common.model.Sheet;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.TokenResult;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.TokenSheet;

/**
 * Panel for viewing a token sheet
 * @author Jonathan Kaplan <jonathankap@wonderbuilders.com>
 * @author Ryan Babiuch
 */
public class TokenStudentViewPanel extends javax.swing.JPanel {

    private static final Logger LOGGER =
            Logger.getLogger(TokenStudentViewPanel.class.getName());
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/isocial/sample/client/Bundle");
    private final ISocialManager manager;
    private final Sheet sheet;

    /** Creates new form Sample1View */
    public TokenStudentViewPanel(ISocialManager manager, Sheet sheet) {
        this.manager = manager;
        this.sheet = sheet;
        initComponents();
        tokenProgressBar.setUI(new MyProgressBarUI());
        tokenProgressBar.setValue(15);
        circlePanel.setUI(new CirclePanelUI());
        TokenSheet ss1 = (TokenSheet) sheet.getDetails();
    }

    /**
     * Set the view with the given result. This will disable the submit
     * button and fill in results from the given result.
     * @param result the result to set this sheet from
     */
    public void setResult(Result result) {
        TokenResult details = (TokenResult) result.getDetails();
    }

    /**
     * Get a ResultDetails object from this view.
     * @return a SampleResultDetails object filled in with the details from this
     * view
     */
    public TokenResult getResultDetails() {
        TokenResult details = new TokenResult();
        return details;
    }

    /**
     * Toggle this panel editable
     */
    public void setEditable(boolean editable) {
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tokenProgressBar = new javax.swing.JProgressBar();
        circlePanel = new javax.swing.JPanel();

        setOpaque(false);

        tokenProgressBar.setInheritsPopupMenu(true);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/jdesktop/wonderland/modules/isocial/tokensheet/client/Bundle"); // NOI18N
        tokenProgressBar.setString(bundle.getString("TokenStudentViewPanel.tokenProgressBar.string")); // NOI18N

        circlePanel.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        circlePanel.setOpaque(false);

        javax.swing.GroupLayout circlePanelLayout = new javax.swing.GroupLayout(circlePanel);
        circlePanel.setLayout(circlePanelLayout);
        circlePanelLayout.setHorizontalGroup(
            circlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 56, Short.MAX_VALUE)
        );
        circlePanelLayout.setVerticalGroup(
            circlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 46, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(circlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tokenProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(tokenProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(circlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel circlePanel;
    private javax.swing.JProgressBar tokenProgressBar;
    // End of variables declaration//GEN-END:variables

    void updateTokens(TokenResult details) {
        //String tokens = details.getTokens();
    }

    private class CirclePanelUI extends BasicPanelUI {

        @Override
        public void paint(Graphics grphcs, JComponent jc) {
            Dimension size = jc.getSize();
            int d = Math.min(size.width, size.height);
            int y = (size.height - d) / 2;

            grphcs.setColor(Color.YELLOW);
            grphcs.fillOval(0, y, d, d);

            grphcs.setColor(Color.BLACK);
            grphcs.drawOval(0, y, d, d);

            String text = "4";
            int width = jc.getFontMetrics(jc.getFont()).stringWidth(text);
            int height = jc.getFontMetrics(jc.getFont()).getHeight();
            grphcs.drawString("4", (d / 2 - width / 2), (d / 2 + height / 4));
        }
    }

    private class MyProgressBarUI extends BasicProgressBarUI {

        @Override
        protected void paintDeterminate(Graphics grphcs, JComponent jc) {
            Insets insets = jc.getInsets();
            int barRectWidth = jc.getWidth() - (insets.right + insets.left);
            int barRectHeight = jc.getHeight() - (insets.top + insets.bottom);
            int amountFull = getAmountFull(insets, barRectWidth, barRectHeight);
            Graphics2D g2 = (Graphics2D) grphcs;
            g2.setColor(Color.RED);
            int width = amountFull + insets.left;
            g2.fillRect(insets.left, insets.top, amountFull + insets.left, barRectHeight);

            g2.setColor(Color.YELLOW);
            amountFull = 10 * (barRectWidth / 100);
            g2.fillRect(insets.left + width, insets.top, amountFull + insets.left, barRectHeight);
            width = amountFull + width + insets.left;

            g2.setColor(Color.GREEN);
            amountFull = 20 * (barRectWidth / 100);
            g2.fillRect(insets.left + width, insets.top, amountFull + insets.left, barRectHeight);
            width = amountFull + width + insets.left;

            g2.setColor(Color.MAGENTA);
            amountFull = 25 * (barRectWidth / 100);
            g2.fillRect(insets.left + width, insets.top, amountFull + insets.left, barRectHeight);

            g2.setColor(Color.BLUE);
            g2.fillRect((int) (insets.left + 0.8 * barRectWidth), insets.top, 1 + insets.left, barRectHeight);
        }
    }
}
