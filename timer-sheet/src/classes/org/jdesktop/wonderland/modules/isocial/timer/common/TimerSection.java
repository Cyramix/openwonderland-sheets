package org.jdesktop.wonderland.modules.isocial.timer.common;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import org.jdesktop.wonderland.modules.isocial.common.model.annotation.ISocialModel;

/**
 *
 * @author iSocial (Kaustubh, Ryan)
 */
@ISocialModel
@XmlRootElement(name = "TimerSection")
public class TimerSection implements Serializable {

    private String sectionName;
    private int sectionTime;

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public int getSectionTime() {
        return sectionTime;
    }

    public void setSectionTime(int sectionTime) {
        this.sectionTime = sectionTime;
    }
}
