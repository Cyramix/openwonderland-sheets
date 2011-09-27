/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.isocial.generic.common;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import org.jdesktop.wonderland.modules.isocial.common.model.annotation.ISocialModel;

/**
 *
 * @author Ryan
 */
@ISocialModel
@XmlRootElement(name="YesNoQuestion")
public class YesNoQuestion extends GenericQuestion implements Serializable {
    public YesNoQuestion() { 
    }
    
//    @Override
//    public QuestionType getQuestionType() {
//        return QuestionType.OPEN_ENDED;
//    }

    @Override
    public String toString() {
        return "value: "+getValue()
                +"\ntitle: "+getTitle();

    }
    
}
