
package org.jdesktop.wonderland.modules.isocial.client.scripting;

import org.jdesktop.wonderland.modules.ezscript.client.SPI.ReturnableScriptMethodSPI;
import org.jdesktop.wonderland.modules.ezscript.client.annotation.ReturnableScriptMethod;

/**
 * This class is to be invoked in a script to generate a ModelState object.
 * The object generated should then be used for cohort state manipulation of a
 * model cell.
 * @author Ryan
 */
@ReturnableScriptMethod
public class ModelStateFactory implements ReturnableScriptMethodSPI {

    public static ModelCohortState createState() {
        return new ModelCohortState();
    }

    public String getDescription() {
        return "Creates a ModelCohortState object.\n" +
                "-- usage: CreateModelState();";
    }

    public String getFunctionName() {
        return "CreateModelState";
    }

    public String getCategory() {
        return "cohorts";
    }

    public void setArguments(Object[] os) {
        //nothing
    }

    public Object returns() {
        return createState();
    }

    public void run() {
        //nothing
    }
}
