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
 * @author iSocial (Kaustubh, Ryan)
 */
@ISocialModel
@XmlRootElement(name="GenericAnswer")
public class GenericAnswer implements Serializable {

    private String questionTitle;
    private String value;

    public GenericAnswer() { }
    /**
     *
     * @return the title of the question it corresponds to
     */
    public String getQuestionTitle() {
        return questionTitle;
    }

    public String getValue() {
        return value;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "title: " +questionTitle
                +"\nvalue: "+value;
    }
}
