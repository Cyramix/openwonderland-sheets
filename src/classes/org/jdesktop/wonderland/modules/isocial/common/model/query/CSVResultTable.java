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
package org.jdesktop.wonderland.modules.isocial.common.model.query;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.jdesktop.wonderland.modules.isocial.common.model.annotation.ISocialModel;

/**
 * A collection of CSV results, organized into rows
 * @author jkaplan
 */
@ISocialModel
@XmlRootElement(name="csv-result-table")
public class CSVResultTable {
    private String cohort;
    private String cohortId;
    private String instance;
    private String instanceId;
    private String unit;
    private String unitId;
    private String lesson;
    private String lessonId;
    private String sheet;
    private String sheetId;

    private List<String> headings = new ArrayList<String>();
    private List<CSVResult> results = new ArrayList<CSVResult>();

    public CSVResultTable() {
    }

    public String getCohort() {
        return cohort;
    }

    public void setCohort(String cohort) {
        this.cohort = cohort;
    }

    public String getCohortId() {
        return cohortId;
    }

    public void setCohortId(String cohortId) {
        this.cohortId = cohortId;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getSheet() {
        return sheet;
    }

    public void setSheet(String sheet) {
        this.sheet = sheet;
    }

    public String getSheetId() {
        return sheetId;
    }

    public void setSheetId(String sheetId) {
        this.sheetId = sheetId;
    }

    @XmlElement
    public List<String> getHeadings() {
        return headings;
    }

    @XmlElement
    public List<CSVResult> getResults() {
        return results;
    }
}
