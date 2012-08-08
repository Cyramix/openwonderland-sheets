 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.wonderland.modules.isocial.tokensheet.client.views;

import org.jdesktop.wonderland.modules.isocial.tokensheet.client.utils.BehaviorReportGenerator;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.swing.JLabel;
import org.jdesktop.wonderland.modules.isocial.tokensheet.client.SPI.TeamBehaviorViewSPI;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.Student;

/**
 *
 * @author Ryan
 */
public class TeamBehaviorViewImpl extends TeamBehaviorViewSPI {

    private int rows = 0;
    private static Logger logger = Logger.getLogger(TeamBehaviorViewImpl.class.getName());
    private static final int ROW_HEIGHT = 70;
    private static final int ROW_WIDTH = 300;
    private static final List<JLabel> views = new ArrayList<JLabel>();

    private final GridBagConstraints constraints = new GridBagConstraints();
    
    /**
     * Creates new form TeamBehaviorViewImpl
     */
    public TeamBehaviorViewImpl() {
        initComponents();
        
        this.setMinimumSize(new Dimension(ROW_WIDTH, ROW_HEIGHT));
        this.setPreferredSize(new Dimension(ROW_WIDTH, ROW_HEIGHT));
    }

    public void update(Map<String, Student> behaviors) {
        rows = 0;

        for (JLabel view : views) {
            remove(view);
        }

        views.clear();

        List<Student> students = sort(behaviors);

        _adjustSize(students.size());

        for (Student student : students) {
            addBehavior(student);
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

        setLayout(new java.awt.GridBagLayout());
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    private List<Student> sort(Map<String, Student> behaviors) {
        List<Student> students = new ArrayList<Student>();

        students.addAll(behaviors.values());

        Collections.sort(students, new Comparator<Student>() {
            public int compare(Student t, Student t1) {
                String first = t.getName();
                String second = t1.getName();
                return Collator.getInstance().compare(first, second);
            }
        });
        
        return students;

    }

    private void _adjustSize(int rows) {
        this.setMinimumSize(new Dimension(ROW_WIDTH, ROW_HEIGHT * rows));
        this.setPreferredSize(new Dimension(ROW_WIDTH, ROW_HEIGHT * rows));
    }

    private void addBehavior(Student student) {
        JLabel view = BehaviorReportGenerator.generateView(student);
        views.add(view);
        addLabelWithDefaultConstraints(view);
    }

    private void addLabelWithDefaultConstraints(JLabel view) {
        rows += 1;
        
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.gridx = 0;
        constraints.gridy = rows;
        constraints.ipady = 10;
        this.add(view, constraints);
        
    }
}
