
package org.jdesktop.wonderland.modules.isocial.client.scripting;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.modules.ezscript.client.SPI.ScriptMethodSPI;
import org.jdesktop.wonderland.modules.ezscript.client.annotation.ScriptMethod;
import org.jdesktop.wonderland.modules.isocial.client.CohortStateManager;

/**
 * Creates/Updates a cohort state for the given cohort based upon a cell name as
 * a key.
 *
 * @author Ryan
 */
@ScriptMethod
public class PutCohortState implements ScriptMethodSPI {

    private Cell cell;
    private ModelCohortState state;
    public String getFunctionName() {
        return "PutCohortState";
    }

    public void setArguments(Object[] os) {
        cell = (Cell)os[0];
        state = (ModelCohortState)os[1];
    }

    public String getDescription() {
        return "Creates/Updates a cohort state for the given cohort based upon" +
                "a cell name as a key.\n" +
                "-- usage: PutCohortState(cell, state);";
    }

    public String getCategory() {
        return "cohorts";
    }

    public void run() {
        try {
            CohortStateManager.INSTANCE.setState(cell.getName(), state);
        } catch (IOException ex) {
            Logger.getLogger(PutCohortState.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
