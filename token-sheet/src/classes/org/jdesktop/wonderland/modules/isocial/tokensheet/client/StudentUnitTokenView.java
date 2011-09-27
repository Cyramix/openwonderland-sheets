package org.jdesktop.wonderland.modules.isocial.tokensheet.client;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.jdesktop.wonderland.client.hud.CompassLayout.Layout;
import org.jdesktop.wonderland.client.hud.HUD;
import org.jdesktop.wonderland.client.hud.HUDComponent;
import org.jdesktop.wonderland.modules.isocial.client.ISocialManager;
import org.jdesktop.wonderland.modules.isocial.client.view.DockableSheetView;
import org.jdesktop.wonderland.modules.isocial.client.view.ResultListener;
import org.jdesktop.wonderland.modules.isocial.client.view.SheetView;
import org.jdesktop.wonderland.modules.isocial.client.view.annotation.View;
import org.jdesktop.wonderland.modules.isocial.common.model.Instance;
import org.jdesktop.wonderland.modules.isocial.common.model.Result;
import org.jdesktop.wonderland.modules.isocial.common.model.Role;
import org.jdesktop.wonderland.modules.isocial.common.model.Sheet;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.TokenResult;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.TokenSheet;

/*
 * StudentUnitTokenView.java
 *
 * Created on Jun 28, 2011, 12:19:22 PM
 */
/**
 *
 * @author Kaustubh
 */
@View(value = TokenSheet.class, roles = {Role.GUIDE, Role.ADMIN, Role.STUDENT})
public class StudentUnitTokenView extends JPanel implements SheetView, ResultListener,
        DockableSheetView {

    private ISocialManager manager;
    private Sheet sheet;
    private int rows;
    private HUDComponent hudComponent;
    private TokenStudentPanel currentLessonPanel;
    private String currentUnit;
    private JLabel currentLabel;

    public void initialize(ISocialManager manager, Sheet sheet, Role role) {
        this.manager = manager;
        this.sheet = sheet;
        String currentLesson = null;
        try {
            currentLesson = manager.getCurrentInstance().getLesson().getId();
            currentUnit = manager.getCurrentInstance().getUnit().getId();
        } catch (IOException ex) {
            Logger.getLogger(StudentUnitTokenView.class.getName()).log(Level.SEVERE, null, ex);
        }
        manager.addResultListener(sheet.getId(), this);
        try {
            Collection<Instance> instances = manager.getInstances();
            for (Instance instance : instances) {
                if (instance.getUnit().getId().equals(currentUnit)) {
                    TokenStudentPanel panel = new TokenStudentPanel(manager);
                    rows++;
                    GridLayout gl = (GridLayout) getLayout();
                    gl.setRows(rows);
                    String lessonName = instance.getLesson().getName();
                    JLabel label = new JLabel(panel.getImageIcon());
                    label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
                    label.setHorizontalTextPosition(JLabel.LEFT);
                    label.setText(lessonName);
                    if (instance.getLesson().getId().equals(currentLesson)) {
                        currentLessonPanel = panel;
                        currentLabel = label;
                    }
                    int stringWidth = label.getFontMetrics(label.getFont()).stringWidth(lessonName) + 15;
                    this.add(label);
                    this.setPreferredSize(new Dimension(panel.getImageIcon().getIconWidth() + stringWidth,
                            panel.getImageIcon().getIconHeight() * rows));
                    List<Sheet> sheets = instance.getSheets();
                    for (Sheet sheet1 : sheets) {
                        if (sheet1.getDetails() instanceof TokenSheet) {
                            Collection<Result> resultsForInstance = manager.getResultsForInstance(instance.getId(), sheet1.getId());
                            Result myResult = null;
                            for (Result result : resultsForInstance) {
                                if (result.getCreator().equals(manager.getUsername())) {
                                    myResult = result;
                                    panel.updateStudentTokens((TokenResult) result.getDetails());
                                }
                            }
                            if (myResult != null) {
                                resultsForInstance.remove(myResult);
                            }
                            panel.updateTokens(resultsForInstance);
                        }
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(StudentUnitTokenView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getMenuName() {
        return "Unit Tokens";
    }

    public boolean isAutoOpen() {
        return ((TokenSheet) sheet.getDetails()).isAutoOpen();
    }

    public HUDComponent open(HUD hud) {
        hudComponent = hud.createComponent(this);
        hudComponent.setPreferredLocation(Layout.EAST);
        hudComponent.setTransparency(1.0f);
        return hudComponent;
    }

    public void close() {
        manager.removeResultListener(sheet.getId(), this);
    }

    /** Creates new form StudentUnitTokenView */
    public StudentUnitTokenView() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.GridLayout(0, 1));
    }// </editor-fold>//GEN-END:initComponents

    public boolean isDockable() {
        return ((TokenSheet) sheet.getDetails()).isDockable();
    }

    public void resultAdded(final Result result) {
        final TokenResult details = (TokenResult) result.getDetails();
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                currentLessonPanel.resetImage();
                try {
                    currentLessonPanel.updateTokens(manager.getResults(sheet.getId()));
                    currentLabel.repaint();
                } catch (IOException ex) {
                    Logger.getLogger(StudentUnitTokenView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void resultUpdated(final Result result) {
        final TokenResult details = (TokenResult) result.getDetails();
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                currentLessonPanel.resetImage();
                try {
                    currentLessonPanel.updateTokens(manager.getResults(sheet.getId()));
                    currentLabel.repaint();
                } catch (IOException ex) {
                    Logger.getLogger(StudentUnitTokenView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
