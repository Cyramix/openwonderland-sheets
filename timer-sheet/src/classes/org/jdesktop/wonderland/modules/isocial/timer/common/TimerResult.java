/**
 * iSocial Project
 * http://isocial.missouri.edu
 *
 * Copyright (c) 2011, University of Missouri iSocial Project, All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * The iSocial project designates this particular file as
 * subject to the "Classpath" exception as provided by the iSocial
 * project in the License file that accompanied this code.
 */
package org.jdesktop.wonderland.modules.isocial.timer.common;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import org.jdesktop.wonderland.modules.isocial.common.model.ResultDetails;
import org.jdesktop.wonderland.modules.isocial.common.model.SheetDetails;
import org.jdesktop.wonderland.modules.isocial.common.model.annotation.ISocialModel;

/**
 * Generic result corresponding to GenericSheet
 * @author Jonathan Kaplan <jonathankap@wonderbuilders.com>
 */
@ISocialModel
@XmlRootElement(name = "TimerResult")
public class TimerResult extends ResultDetails {

    private String finishedSectionName;
    private String nextSectionName;
//    private List<GenericAnswer> answers;

    public TimerResult() {
//        answers = new ArrayList<GenericAnswer>();
    }

    public String getFinishedSectionName() {
        return finishedSectionName;
    }

    public void setFinishedSectionName(String finishedSectionName) {
        this.finishedSectionName = finishedSectionName;
    }

    public String getNextSectionName() {
        return nextSectionName;
    }

    public void setNextSectionName(String nextSectionName) {
        this.nextSectionName = nextSectionName;
    }

    
    // <editor-fold defaultstate="collapsed" desc="Legacy code from Sample Sheet project">
//    public String getAnswer() {
//        return answer;
//    }
//
//    public void setAnswer(String answer) {
//        this.answer = answer;
//    }
    //</editor-fold>

    @Override
    public List<String> getResultValues(List<String> list, SheetDetails sd) {
        List<String> values = new ArrayList<String>();
//        for (GenericAnswer answer1 : answers) {
//            values.add(answer1.getValue());
//        }
        return values;
    }
}
