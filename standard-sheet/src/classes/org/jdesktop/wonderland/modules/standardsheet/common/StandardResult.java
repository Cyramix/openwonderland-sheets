/**
 * Open Wonderland
 *
 * Copyright (c) 2012, Open Wonderland Foundation, All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above copyright and
 * this condition.
 *
 * The contents of this file are subject to the GNU General Public License,
 * Version 2 (the "License"); you may not use this file except in compliance
 * with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/gpl-license.php.
 *
 * The Open Wonderland Foundation designates this particular file as subject to
 * the "Classpath" exception as provided by the Open Wonderland Foundation in
 * the License file that accompanied this code.
 */

/**
 * WonderBuilders, Inc.
 *
 * Copyright (c) 2012, WonderBuilders, Inc., All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above copyright and
 * this condition.
 *
 * The contents of this file are subject to the GNU General Public License,
 * Version 2 (the "License"); you may not use this file except in compliance
 * with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/gpl-license.php.
 *
 * WonderBuilders, Inc. designates this particular file as subject to the
 * "Classpath" exception as provided WonderBuilders, Inc. in the License file
 * that accompanied this code.
 */
package org.jdesktop.wonderland.modules.standardsheet.common;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.jdesktop.wonderland.modules.isocial.common.model.ResultDetails;
import org.jdesktop.wonderland.modules.isocial.common.model.SheetDetails;
import org.jdesktop.wonderland.modules.isocial.common.model.annotation.ISocialModel;

/**
 * Standard result read from a StandardSheet.
 * @author Jonathan Kaplan <jonathankap@wonderbuilders.com>
 */
@ISocialModel
@XmlRootElement(name = "standard-result")
public class StandardResult extends ResultDetails {
    private List<StandardAnswer> answers = new ArrayList<StandardAnswer>();

    public StandardResult() {
    }

    @XmlElement
    public List<StandardAnswer> getAnswers() {
        return answers;
    }
    
    public void setAnswers(List<StandardAnswer> answers) {
        this.answers = answers;
    }
    
    @Override
    public List<String> getResultValues(List<String> list, SheetDetails sd) {
        List<String> values = new ArrayList<String>();
        for (StandardAnswer answer : answers) {
            values.add(answer.getValueString());
        }
        return values;
    }
}
