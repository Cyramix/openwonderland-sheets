package org.jdesktop.wonderland.modules.isocial.timer.client;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import java.util.logging.Logger;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.jdesktop.wonderland.client.hud.HUD;
import org.jdesktop.wonderland.client.hud.HUDComponent;
import org.jdesktop.wonderland.modules.isocial.client.ISocialManager;
import org.jdesktop.wonderland.modules.isocial.client.view.DockableSheetView;
import org.jdesktop.wonderland.modules.isocial.client.view.ResultListener;
import org.jdesktop.wonderland.modules.isocial.client.view.SheetView;
import org.jdesktop.wonderland.modules.isocial.client.view.annotation.View;
import org.jdesktop.wonderland.modules.isocial.common.model.Result;
import org.jdesktop.wonderland.modules.isocial.common.model.Role;
import org.jdesktop.wonderland.modules.isocial.common.model.Sheet;
import org.jdesktop.wonderland.modules.isocial.timer.common.TimerSection;
import org.jdesktop.wonderland.modules.isocial.timer.common.TimerSheet;

/**
 * Alternative View for testing purposes
 * 
 * @author ryan
 */
@View(value = TimerSheet.class, roles = {Role.GUIDE, Role.ADMIN})
public class TimerGuideView extends javax.swing.JPanel
        implements SheetView, ResultListener, DockableSheetView {

    private static final Logger logger = Logger.getLogger(TimerGuideView.class.getName());
    private ISocialManager manager;
    private Sheet sheet;
    private Role role;
    private Map<String, TableColumn> columnNames;
    private DefaultTableModel model;
    private Timer swingTimer;
    //string to be put in startButton label
    private String currentTime;
    private int currentMinute = 0;
    private int currentSecond = 0;
    private int totalTime = 0;
    private TimerCanvas canvas;

    /** Creates new form TimerGuideView */
    public TimerGuideView() {
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

        resetButton = new javax.swing.JButton();
        startButton = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        resetButton.setText("Reset");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });
        add(resetButton, new java.awt.GridBagConstraints());

        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });
        add(startButton, new java.awt.GridBagConstraints());
    }// </editor-fold>//GEN-END:initComponents

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        // TODO add your handling code here:
        if (!swingTimer.isRunning()) {
            swingTimer.start();
            //startButton.setText("Pause");
        } else {
            swingTimer.stop();
            //startButton.s
        }
    }//GEN-LAST:event_startButtonActionPerformed

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        // TODO add your handling code here:
        swingTimer.restart();
        canvas.setRedRectHeight(0);
        currentMinute = 0;
        currentSecond = 0;
    }//GEN-LAST:event_resetButtonActionPerformed

    public void initialize(ISocialManager ism, Sheet sheet, Role role) {
        this.manager = ism;
        this.sheet = sheet;
        this.role = role;
//        List<List<String>> rows = new ArrayList<List<String>>();
        manager.addResultListener(sheet.getId(), this);
//        model = new DefaultTableModel();
        TimerSheet timerDetails = (TimerSheet) sheet.getDetails();
        canvas = new TimerCanvas(timerDetails.getSections());
        //this.add(canvas);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        
        this.add(startButton, c);

        c.gridx = 0;
        c.gridy = 1;
        this.add(resetButton, c);

        c.gridx = 0;
        c.gridy = 2;

        this.add(canvas, c);
        swingTimer = new Timer(1 * 1000, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //if currentSecond is already 59
                boolean fail = false;
                if ((currentSecond + 1) == 60) {
                    currentMinute += 1;
                    currentSecond = 0;

                } else {
                    currentSecond += 1;
                }

                int actualMinute = totalTime / 60 - currentMinute - 1;
                int actualSecond = 60 - currentSecond;

                if (actualMinute == -1 && actualSecond == 60) {
                    actualMinute = 0;
                    actualSecond = 0;
                    fail = true;


                }

                String timeLabel = "";
                if (actualMinute < 10) {
                    timeLabel += "0";
                }

                timeLabel += actualMinute + ":";
                if (actualSecond < 10) {
                    timeLabel += "0";
                }

                timeLabel += actualSecond;
                startButton.setText(timeLabel);

                double height = canvas.getRedRectHeight();
                // height = ((double) canvas.getRectHeight()) - calculateElapsedTime();
                height += (double) canvas.getRectHeight() / (double) (totalTime);

                canvas.setRedRectHeight(new Double(height).doubleValue());
                canvas.repaint();
                if(fail) {
                    swingTimer.restart();
                    swingTimer.stop();
                    currentMinute = 0;
                    currentSecond = 0;
                }
            }
        });
        swingTimer.setInitialDelay(0);

//<editor-fold desc="legacy code">
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
//        model.addColumn("Students");
        //Collections.reverse(genericSheet.getQuestions());
        //for(TimerSection question: genericSheet.getQuestions()) {
        //   model.addColumn(question.getValue());
        //}

//        //populate table with results
//       for(List<String> row: rows) {
//           //Collections.reverse(row);
//
//           model.addRow(row.toArray());
//       }
//        answersTable.setModel(model);
        //</editor-fold>

        for (TimerSection section : timerDetails.getSections()) {
            totalTime += (section.getSectionTime() * 60);
        }
            
            }

    /**
     * Get the current time in string format, MM:SS
     * @return
     */
    private double calculateElapsedTime() {

        return ((double) ((currentMinute * 60) + currentSecond)) / ((double) (totalTime));

    }

    public String getMenuName() {
        return ((TimerSheet) sheet.getDetails()).getName();
    }

    public boolean isAutoOpen() {
        return ((TimerSheet) sheet.getDetails()).isAutoOpen();
    }

    public HUDComponent open(HUD hud) {
        return hud.createComponent(this);
    }

    public void close() {
        manager.removeResultListener(sheet.getId(), this);
    }

    public void resultAdded(Result result) {

        logger.warning("RESULT ADDED: " + result.getCreator());
    }

    public void resultUpdated(Result result) {

        logger.warning("RESULT UPDATED: " + result.getUpdater());
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton resetButton;
    private javax.swing.JButton startButton;
    // End of variables declaration//GEN-END:variables

    public boolean isDockable() {
       return ((TimerSheet)sheet.getDetails()).isDockable();
    }
}
