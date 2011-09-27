/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.isocial.client.scripting;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.modules.ezscript.client.SPI.ReturnableScriptMethodSPI;
import org.jdesktop.wonderland.modules.ezscript.client.annotation.ReturnableScriptMethod;
import org.jdesktop.wonderland.modules.isocial.client.CohortStateManager;

/**
 * Retrieves a ModelCohortState object from the current cohort based upon a cell
 * name as a key.
 *
 * @author Ryan
 */
@ReturnableScriptMethod
public class GetCohortStateCommand implements ReturnableScriptMethodSPI {

    private Cell cell;
    private ModelCohortState state;
    public String getDescription() {
        return "Retrieves a ModelCohortState object from the current cohort based" +
                "upon a cell name as a key.\n" +
                "-- usage: var state = GetCohortState(cell);";
    }

    public String getFunctionName() {
        return "GetCohortState";
    }

    public String getCategory() {
        return "cohorts";
    }

    public void setArguments(Object[] os) {
        cell = (Cell)os[0];
    }

    public Object returns() {
        return state;
    }

    public void run() {
        try {
            state = CohortStateManager.INSTANCE.getState(cell.getName(), ModelCohortState.class);
        } catch (IOException ex) {
            Logger.getLogger(GetCohortStateCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
