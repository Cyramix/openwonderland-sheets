
package org.jdesktop.wonderland.modules.isocial.client.scripting;

import javax.xml.bind.annotation.XmlRootElement;
import org.jdesktop.wonderland.modules.isocial.common.model.CohortStateDetails;
import org.jdesktop.wonderland.modules.isocial.common.model.annotation.ISocialModel;
import org.jdesktop.wonderland.modules.isocial.common.model.state.CSBoolean;

/**
 *
 * @author Ryan
 */
@ISocialModel
@XmlRootElement(name="ModelCohortState")
public class ModelCohortState extends CohortStateDetails {

    private CSBoolean visibility = CSBoolean.valueOf(Boolean.TRUE);

    public ModelCohortState() { }

    public CSBoolean getVisibility() {
        return visibility;
    }

    public void setVisibility(CSBoolean visibility) {
        this.visibility = visibility;
    }

    public boolean isVisible() {
        return visibility.getValue().booleanValue();
    }
}
