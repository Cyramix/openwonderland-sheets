package org.jdesktop.wonderland.modules.isocial.generic.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.jdesktop.wonderland.client.hud.HUD;
import org.jdesktop.wonderland.client.hud.HUDComponent;
import org.jdesktop.wonderland.modules.isocial.client.HUDDetailsWrapper;
import org.jdesktop.wonderland.modules.isocial.client.ISocialManager;
import org.jdesktop.wonderland.modules.isocial.client.view.ResultListener;
import org.jdesktop.wonderland.modules.isocial.client.view.SheetView;
import org.jdesktop.wonderland.modules.isocial.client.view.annotation.View;
import org.jdesktop.wonderland.modules.isocial.common.model.Result;
import org.jdesktop.wonderland.modules.isocial.common.model.Role;
import org.jdesktop.wonderland.modules.isocial.common.model.Sheet;
import org.jdesktop.wonderland.modules.isocial.generic.common.GenericAnswer;
import org.jdesktop.wonderland.modules.isocial.generic.common.GenericQuestion;
import org.jdesktop.wonderland.modules.isocial.generic.common.GenericResult;
import org.jdesktop.wonderland.modules.isocial.generic.common.GenericSheet;

/**
 * Alternative View for testing purposes
 * 
 * @author ryan
 */
//@View(value=GenericSheet.class, roles={Role.GUIDE, Role.ADMIN})
public class GenericGuideView extends javax.swing.JPanel
    implements SheetView, ResultListener {

    private static final Logger logger = Logger.getLogger(GenericGuideView.class.getName());
    private ISocialManager manager;
    private Sheet sheet;
    private Role role;
    private Map<String, TableColumn> columnNames;
    private DefaultTableModel model;
    /** Creates new form GenericGuideView */
    public GenericGuideView() {
        initComponents();
        columnNames = new HashMap<String, TableColumn>();
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
        clientOneName = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        answersTable = new javax.swing.JTable();

        jLabel1.setText("jLabel1");

        clientOneName.setText("jLabel1");

        answersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(answersTable);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 634, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 500, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    public void initialize(ISocialManager ism, Sheet sheet, Role role) {
        this.manager = ism;
        this.sheet = sheet;
        this.role = role;
        List<List<String>> rows = new ArrayList<List<String>>();
        manager.addResultListener(sheet.getId(), this);
        model = new DefaultTableModel();
        GenericSheet genericSheet = (GenericSheet)sheet.getDetails();

//        try {
//            List<String> creatorNames = new ArrayList<String>();
//            //for each result
//            for (Result r : manager.getResults(sheet.getId())) {
//                //create a question to string map
//                Map<String, String> questionsToAnswerStrings = new HashMap<String,String>();
//                //create the row
//                List<String> row = new ArrayList<String>();
//                //grab the details
//                GenericResult details = (GenericResult)r.getDetails();
//
//                //add the first cell of the row to be the creator's name
//                row.add(r.getCreator());
//
//                //for each answer in the result
//                for(GenericAnswer answer: details.getAnswers()) {
//
//                    //check to see if the question already exists in the map
//                    if(questionsToAnswerStrings.containsKey(answer.getQuestionTitle())) {
//                        //if so, append the answer to the existing string
//                        String s = questionsToAnswerStrings.get(answer.getQuestionTitle());
//                        s += ", ";
//                        s += answer.getValue();
//                        questionsToAnswerStrings.put(answer.getQuestionTitle(), s);
//                    } else {
//                        //if not, create the mapping
//                       String question = answer.getQuestionTitle();
//                       String antser = answer.getValue();
//                       questionsToAnswerStrings.put(question, antser);
//                    }
//                }
//
//                row.addAll(questionsToAnswerStrings.values());
//
//               // row.addAll(details.getResultValues(null, null));
//                creatorNames.add(r.getCreator());
//                rows.add(row);
//                logger.warning("ADDING RESULT: " + r);
//            }
//
//            model.addColumn("Students", creatorNames.toArray());
//
//        } catch (IOException ioe) {
//            logger.warning("Unable to populate users for guide view");
//        }

        //add questions as column titles
        model.addColumn("Students");
        Collections.reverse(genericSheet.getQuestions());
        for(GenericQuestion question: genericSheet.getQuestions()) {           
            model.addColumn(question.getValue());
        }

//        //populate table with results
//       for(List<String> row: rows) {
//           //Collections.reverse(row);
//
//           model.addRow(row.toArray());
//       }
        answersTable.setModel(model);
    }

    public String getMenuName() {
        return ((GenericSheet)sheet.getDetails()).getName();
    }

    public boolean isAutoOpen() {
        return ((GenericSheet)sheet.getDetails()).isAutoOpen();
    }

    public HUDDetailsWrapper open(HUD hud) {
        
        HUDComponent hudComponent = hud.createComponent(this);
        
        
        return new HUDDetailsWrapper(sheet.getName(), hudComponent, this);
    }

    public void close() {
        manager.removeResultListener(sheet.getId(), this);
    }

    public void resultAdded(Result result) {

        //create a question to string map
        Map<String, String> questionsToAnswerStrings = new HashMap<String, String>();
        //create the row
        List<String> row = new ArrayList<String>();
        //grab the details
        GenericResult details = (GenericResult) result.getDetails();

        //add the first cell of the row to be the creator's name
        row.add(result.getCreator());

        //for each answer in the result        
        for (GenericAnswer answer : details.getAnswers()) {

            //check to see if the question already exists in the map
            if (questionsToAnswerStrings.containsKey(answer.getQuestionTitle())) {
                //if so, append the answer to the existing string
                String s = questionsToAnswerStrings.get(answer.getQuestionTitle());
                s += ", ";
                s += answer.getValue();
                questionsToAnswerStrings.put(answer.getQuestionTitle(), s);
            } else {
                //if not, create the mapping
                String question = answer.getQuestionTitle();
                String antser = answer.getValue();
                questionsToAnswerStrings.put(question, antser);
            }
        }

        row.addAll(questionsToAnswerStrings.values());

        boolean exists = false;
        for(int i = 0; i < answersTable.getRowCount(); i++) {
            if(model.getValueAt(i, 0).equals(result.getCreator())) {
                model.removeRow(i);
            }
        }
        model.addRow(row.toArray());

        logger.warning("RESULT ADDED: " + result.getCreator());
    }

    public void resultUpdated(Result result) {

        //create a question to string map
        Map<String, String> questionsToAnswerStrings = new HashMap<String, String>();
        //create the row
        List<String> row = new ArrayList<String>();
        //grab the details
        GenericResult details = (GenericResult) result.getDetails();

        //add the first cell of the row to be the creator's name
        row.add(result.getCreator());

        //for each answer in the result
        for (GenericAnswer answer : details.getAnswers()) {

            //check to see if the question already exists in the map
            if (questionsToAnswerStrings.containsKey(answer.getQuestionTitle())) {
                //if so, append the answer to the existing string
                String s = questionsToAnswerStrings.get(answer.getQuestionTitle());
                s += ", ";
                s += answer.getValue();
                questionsToAnswerStrings.put(answer.getQuestionTitle(), s);
            } else {
                //if not, create the mapping
                String question = answer.getQuestionTitle();
                String antser = answer.getValue();
                questionsToAnswerStrings.put(question, antser);
            }
        }

        row.addAll(questionsToAnswerStrings.values());

        boolean exists = false;
        for(int i = 0; i < answersTable.getRowCount(); i++) {
            if(model.getValueAt(i, 0).equals(result.getCreator())) {
                model.removeRow(i);
            }
        }
        model.addRow(row.toArray());
        logger.warning("RESULT UPDATED: "+result.getUpdater());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable answersTable;
    private javax.swing.JLabel clientOneName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}
