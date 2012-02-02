/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MultipleChoiceQuestionPanel.java
 *
 * Created on May 19, 2011, 2:22:34 PM
 */

package org.jdesktop.wonderland.modules.isocial.generic.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import org.jdesktop.wonderland.modules.isocial.generic.common.GenericAnswer;
import org.jdesktop.wonderland.modules.isocial.generic.common.GenericQuestion;

/**
 *
 * @author ryan
 */
public class MultipleChoiceQuestionPanel extends javax.swing.JPanel {

    /** Creates new form MultipleChoiceQuestionPanel */
    private final boolean inclusive;
    private List<JToggleButton> buttons;
    private String selectedOption = "none";
    private static final Logger logger = Logger.getLogger(MultipleChoiceQuestionPanel.class.getName());



    private Map<String, JToggleButton> stringsToButtons;
    public MultipleChoiceQuestionPanel(GenericQuestion question, boolean inclusive) {
        initComponents();
        this.inclusive = inclusive;
        stringsToButtons = new HashMap<String, JToggleButton>();
        buttons = new ArrayList<JToggleButton>();
        if(inclusive) {
            for(GenericAnswer answer: question.getAnswers()) {
                JCheckBox button = new JCheckBox();
                button.setText(answer.getValue());
                //buttonGroup.add(button);
                buttonPanel.add(button);
                logger.warning("ADDING INCLUSIVE BUTTON!");
                buttons.add(button);
                stringsToButtons.put(answer.getValue(),button);
                button.setEnabled(true);
            }
        } else {
            for(GenericAnswer answer: question.getAnswers()) {
                JRadioButton button = new JRadioButton();
                button.setText(answer.getValue());
                button.setActionCommand(answer.getValue());
                button.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                         selectedOption = e.getActionCommand();
                    }

                });
                logger.warning("ADDING EXCLUSIVE BUTTTON!");
                buttonGroup.add(button);
                buttonPanel.add(button);
                buttons.add(button);
                stringsToButtons.put(answer.getValue(), button);
                button.setEnabled(true);
            }
        }
        questionLabel.setText(question.getValue());
    }

    public List<String> getSelectedAnswer() {
        List<String> selectedButtons = new ArrayList();
        if(!inclusive) {
            selectedButtons.add(selectedOption);
            return selectedButtons;
        }

        
        for(JToggleButton button: buttons) {
            if(button.isSelected()) {
               selectedButtons.add(button.getText());
            }
        }
        return selectedButtons;
    }

    public String getQuestion() {
        return questionLabel.getText();
    }

    /**
     * This may not be needed, but might be used in the future.
     * @param answers
     */
    public void setSelectedAnswers(List<String> answers) {
        for(String answer: answers) {
            if(stringsToButtons.containsKey(answer)) {
                stringsToButtons.get(answer).setSelected(true);
            }
        }
    }

    public void setSelectedAnswer(String answer) {
        if(stringsToButtons.containsKey(answer)) {
            stringsToButtons.get(answer).setSelected(true);
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

        buttonGroup = new javax.swing.ButtonGroup();
        questionLabel = new javax.swing.JLabel();
        buttonPanel = new javax.swing.JPanel();

        questionLabel.setText("Default question will be displayed here?");

        buttonPanel.setLayout(new javax.swing.BoxLayout(buttonPanel, javax.swing.BoxLayout.Y_AXIS));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(questionLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(questionLabel)
                .addGap(18, 18, 18)
                .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JLabel questionLabel;
    // End of variables declaration//GEN-END:variables

}
