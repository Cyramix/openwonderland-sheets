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
package org.jdesktop.wonderland.modules.isocial.common.model;

import java.io.Serializable;
import java.util.List;
import org.jdesktop.wonderland.modules.isocial.common.model.annotation.ISocialModel;

/**
 * Abstract superclass of all result details
 * @author Jonathan Kaplan <jonathankap@wonderbuilders.com>
 */
@ISocialModel
public abstract class ResultDetails implements Serializable {
    /**
     * Get the values corresponding to the headings from the sheet
     * this result originates from. The returned list should have the same
     * size as the list of headings passed in. Empty results should be
     * represented by the empty string.
     *
     * @param headings the headings from the sheetDetails
     * @param sheetDetails the sheet that this result originated from
     */
    public abstract List<String> getResultValues(List<String> headings,
                                                 SheetDetails sheetDetails);

}
