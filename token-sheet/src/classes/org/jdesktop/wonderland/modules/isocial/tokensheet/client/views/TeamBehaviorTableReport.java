/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.wonderland.modules.isocial.tokensheet.client.views;

import java.util.Map;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.wonderland.modules.isocial.tokensheet.client.SPI.AbstractTeamBehaviorView;
import org.jdesktop.wonderland.modules.isocial.tokensheet.client.SPI.TeamBehaviorViewSPI;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.Student;

/**
 *
 * @author Ryan
 */
public class TeamBehaviorTableReport extends javax.swing.JPanel  
    implements TeamBehaviorViewSPI {

    /**
     * Creates new form TeamBehaviorTableReport
     */
    public TeamBehaviorTableReport() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        reportTable = new javax.swing.JTable();

        reportTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Student", "Passes", "Strikes"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(reportTable);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable reportTable;
    // End of variables declaration//GEN-END:variables

    public void update(Map<String, Student> behaviors) {
        //clear the table
        DefaultTableModel model = (DefaultTableModel)reportTable.getModel();
        model.setRowCount(0);
        
        //for every student score
        for(Student s: behaviors.values()) {
            //create an array consisting of their name, current passes, and current strikes
            Object[] row = new Object[] { s.getName(), s.getPassesValue(), s.getStrikesValue()};
            
            //add the array as a row to the table
            model.addRow(row);
        }
    }
}
