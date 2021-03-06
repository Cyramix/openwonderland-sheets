package org.jdesktop.wonderland.modules.isocial.tokensheet.client.legacy;

import java.awt.Color;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.jdesktop.wonderland.modules.colormanager.client.ColorManager;
import org.jdesktop.wonderland.modules.isocial.client.ISocialManager;
import org.jdesktop.wonderland.modules.isocial.common.model.state.CSString;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.ResultType;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.Student;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.TokenResult;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.TokenSheet;

/**
 * Creates the panel on guide's view to show the student details such as token,
 * passes and strikes.
 *
 * @author Kaustubh
 */
public class StudentDetailsPanel extends javax.swing.JPanel {

    private static final String POSSIBLE_PER_STUDENT_PER_LESSON = "tokens.possible.per.student.per.lesson";
    private static final String POSSIBLE_PER_STUDENT_PER_UNIT = "tokens.possible.per.student.per.unit";
    private static final String NUMBER_OF_STUDENTS = "number.of.students";
    private Student student = null;
    private int token;
    private int pass;
    private int strike;
    private int tokenMaxLimit;
    private final int passMaxLimit = 3;
    String[] strikeValues = new String[]{"0", "W", "1", "2", "3"};
    private final Color passColor = new Color(71, 164, 173);
    private final Color strikeColor = new Color(183, 40, 47);
    private int strikeMaxLimit = 4;
    private TokenSheet tokenSheet;
    private ResultType type;
    private ISocialManager manager = null;

    /**
     * Creates new form StudentDetailsPanel
     */
    public StudentDetailsPanel() {
        manager = ISocialManager.INSTANCE;
    }

    public StudentDetailsPanel(ISocialManager manager, Student student, TokenSheet sheet) {
        try {
            initComponents();
            this.manager = manager;
            this.student = student;
            this.token = student.getTokensValue();
            this.pass = student.getPassesValue();
            this.strike = student.getStrikesValue();
            this.tokenSheet = sheet;
    //        tokenMaxLimit = tokenSheet.getMaxLessonTokens();
            tokenMaxLimit = getMaxLessonTokens();
            tokenInc.setText(student.getName());
    //        try {
    //            tokenInc.setBackground(ColorManager.getInstance().getColorFor(manager.getCurrentInstance().getCohortId(), student.getName()));
    //        } catch (IOException ex) {
    //            Logger.getLogger(StudentDetailsPanel.class.getName()).log(Level.SEVERE, null, ex);
    //        }
            tokens.setText(String.valueOf(token) + "/" + tokenMaxLimit);
            passes.setText(String.valueOf(pass));
            strikes.setText(String.valueOf(strikeValues[strike]));
        } catch (IOException ex) {
            Logger.getLogger(StudentDetailsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int getMaxLessonTokens() throws IOException {
        String unitId = manager.getCurrentInstance().getUnit().getId();
        CSString state = (CSString)manager
                        .getCohortState(unitId+POSSIBLE_PER_STUDENT_PER_LESSON)
                        .getDetails();
        
        return Integer.parseInt(state.getValue());
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        tokenInc = new javax.swing.JButton();
        tokens = new javax.swing.JLabel();
        tokenDec = new javax.swing.JButton();
        passInc = new javax.swing.JButton();
        passes = new javax.swing.JLabel();
        passDec = new javax.swing.JButton();
        strikeInc = new javax.swing.JButton();
        strikes = new javax.swing.JLabel();
        strikeDec = new javax.swing.JButton();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(405, 25));

        tokenInc.setFont(new java.awt.Font("Lucida Grande", 0, 10));
        tokenInc.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tokenIncActionPerformed(evt);
            }
        });

        tokens.setFont(new java.awt.Font("Lucida Grande", 0, 10));
        tokens.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tokens.setText("0/10");

        tokenDec.setFont(new java.awt.Font("Lucida Grande", 0, 10));
        tokenDec.setText("T-");
        tokenDec.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tokenDecActionPerformed(evt);
            }
        });

        passInc.setBackground(passColor);
        passInc.setFont(new java.awt.Font("Lucida Grande", 0, 10));
        passInc.setText("P+");
        passInc.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passIncActionPerformed(evt);
            }
        });

        passes.setFont(new java.awt.Font("Lucida Grande", 0, 10));
        passes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        passDec.setBackground(passColor);
        passDec.setFont(new java.awt.Font("Lucida Grande", 0, 10));
        passDec.setText("P-");
        passDec.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passDecActionPerformed(evt);
            }
        });

        strikeInc.setBackground(strikeColor);
        strikeInc.setFont(new java.awt.Font("Lucida Grande", 0, 10));
        strikeInc.setText("S+");
        strikeInc.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                strikeIncActionPerformed(evt);
            }
        });

        strikes.setFont(new java.awt.Font("Lucida Grande", 0, 10));
        strikes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        strikeDec.setBackground(strikeColor);
        strikeDec.setFont(new java.awt.Font("Lucida Grande", 0, 10));
        strikeDec.setText("S-");
        strikeDec.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                strikeDecActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(tokenInc, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(tokens, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(tokenDec, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(passInc, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(passes, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(passDec, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(strikeInc, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(strikes, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(strikeDec, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(13, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER).addComponent(tokenInc).addComponent(tokens, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(tokenDec).addComponent(passInc).addComponent(passes, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(passDec).addComponent(strikeInc).addComponent(strikes, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(strikeDec))));
    }// </editor-fold>                        

    private void tokenIncActionPerformed(java.awt.event.ActionEvent evt) {
        if (token >= tokenMaxLimit) {
            return;
        }
        token = token + 1;
        tokens.setText(String.valueOf(token) + "/" + tokenMaxLimit);
        type = ResultType.TOKEN_INCREMENT;
        firePropertyChange(student.getName(), null, "");
    }

    private void passIncActionPerformed(java.awt.event.ActionEvent evt) {
        //firePropertyChange(student.getName(), null, "");
        if (pass >= passMaxLimit) {
            return;
        }
        pass++;
        passes.setText(String.valueOf(pass));
        type = ResultType.PASS_INCREMENT;
        firePropertyChange(student.getName(), null, "");
    }

    private void strikeIncActionPerformed(java.awt.event.ActionEvent evt) {
        //firePropertyChange(student.getName(), null, "");
        if (strike == strikeMaxLimit) {
            return;
        }
        strike++;
        strikes.setText(strikeValues[strike]);
        type = ResultType.STRIKE_INCREMENT;
        firePropertyChange(student.getName(), null, "");
    }

    private void tokenDecActionPerformed(java.awt.event.ActionEvent evt) {
        //firePropertyChange(student.getName(), null, "");
        if (token == 0) {
            return;
        }
        token--;
        tokens.setText(String.valueOf(token) + "/" + tokenMaxLimit);
        type = ResultType.TOKEN_DECREMENT;
        firePropertyChange(student.getName(), null, "");
    }

    private void passDecActionPerformed(java.awt.event.ActionEvent evt) {
        //firePropertyChange(student.getName(), null, "");
        if (pass == 0) {
            return;
        }
        pass--;
        passes.setText(String.valueOf(pass));
        type = ResultType.PASS_DECREMENT;
        firePropertyChange(student.getName(), null, "");
    }

    private void strikeDecActionPerformed(java.awt.event.ActionEvent evt) {
        //firePropertyChange(student.getName(), null, "");
        if (strike == 0) {
            return;
        }
        strike--;
        strikes.setText(strikeValues[strike]);
        type = ResultType.STRIKE_DECREMENT;
        firePropertyChange(student.getName(), null, "");
    }
    // Variables declaration - do not modify                     
    private javax.swing.JButton passDec;
    private javax.swing.JButton passInc;
    private javax.swing.JLabel passes;
    private javax.swing.JButton strikeDec;
    private javax.swing.JButton strikeInc;
    private javax.swing.JLabel strikes;
    private javax.swing.JButton tokenDec;
    private javax.swing.JButton tokenInc;
    private javax.swing.JLabel tokens;
    // End of variables declaration                   

    public TokenResult getDetails() {
        student.setTokensValue(token);
        student.setPassesValue(pass);
        student.setStrikesValue(strike);
        TokenResult details = new TokenResult();
        details.setStudentResult(student);
        if (type != null) {
            details.setType(type);
        }
        return details;
    }

    public void updateResult(Student student) {
        this.student = student;
        this.token = student.getTokensValue();
        this.pass = student.getPassesValue();
        this.strike = student.getStrikesValue();
        tokens.setText(String.valueOf(token) + "/" + tokenMaxLimit);
        passes.setText(String.valueOf(pass));
        strikes.setText(strikeValues[strike]);
    }
}
