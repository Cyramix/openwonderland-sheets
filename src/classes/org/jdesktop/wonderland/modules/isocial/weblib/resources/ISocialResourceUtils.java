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
package org.jdesktop.wonderland.modules.isocial.weblib.resources;

import com.sun.jersey.api.json.JSONJAXBContext;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.xml.bind.JAXBContext;
import org.jdesktop.wonderland.modules.isocial.common.model.Cohort;
import org.jdesktop.wonderland.modules.isocial.common.model.Instance;
import org.jdesktop.wonderland.modules.isocial.common.model.Result;
import org.jdesktop.wonderland.modules.isocial.common.model.Sheet;
import org.jdesktop.wonderland.modules.isocial.common.model.query.CSVResult;
import org.jdesktop.wonderland.modules.isocial.common.model.query.CSVResultTable;
import org.jdesktop.wonderland.modules.isocial.weblib.ISocialDAO;
import org.jdesktop.wonderland.modules.isocial.weblib.ISocialWebUtils;

/**
 * Resource utilities.
 * @author Jonathan Kaplan <jonathankap@wonderbuilders.com>
 */
public class ISocialResourceUtils {
    private static final Logger LOGGER =
            Logger.getLogger(ISocialResourceUtils.class.getName());

    /**
     * Combine a list of results in a list of CSVResults by combining
     * results with matching values into tables.
     * @param results the list of results to sort
     * @param dao the data access object for lookups
     * @return a list of CSVResult objects
     */
    public static Collection<CSVResultTable> toCSV(Collection<Result> results,
                                                   ISocialDAO dao)
    {
        Map<String, CSVResultTable> tables =
                new LinkedHashMap<String, CSVResultTable>();

        for (Result r : results) {
            Instance i = dao.getInstance(r.getInstanceId());
            Cohort c = dao.getCohort(i.getCohortId());
            Sheet s = i.getSheet(r.getSheetId());

            // get the result headers
            List<String> headings = s.getDetails().getResultHeadings();

            // create a unique id by concatentating all the relevant IDs
            String id = c.getId() + "-" + i.getId() + "-" + s.getUnitId() +
                        "-" + s.getLessonId() + "-" + s.getId();

            // see if we already have a table for this ID
            CSVResultTable table = tables.get(id);
            if (table == null) {
                // create a new table
                table = new CSVResultTable();
                table.setCohortId(c.getId());
                table.setCohort(c.getName());
                table.setInstanceId(i.getId());
                table.setInstance(DateFormat.getDateTimeInstance().format(i.getCreated()));
                table.setUnitId(i.getUnit().getId());
                table.setUnit(i.getUnit().getName());
                table.setLessonId(i.getLesson().getId());
                table.setLesson(i.getLesson().getName());
                table.setSheetId(s.getId());
                table.setSheet(s.getName());

                table.getHeadings().addAll(headings);

                tables.put(id, table);
            }

            // get the list of result values
            List<String> values = r.getDetails().getResultValues(headings, s.getDetails());

            // create a new CSVResult
            CSVResult result = new CSVResult();
            result.setStudent(r.getCreator());
            result.getResults().addAll(values);

            // add the result to the table
            table.getResults().add(result);
        }

        return tables.values();
    }


    /**
     * JSON context resolver for ISocialModel classes. Note this class must be
     * extended in every context where it is needed to be discovered by
     * Jersey.
     */
    @Produces(MediaType.APPLICATION_JSON)
    public static class ISocialJSONContextResolver extends BaseContextResolver {
        public ISocialJSONContextResolver() throws Exception {
            super();
        }

        @Override
        protected JAXBContext createContext(Collection<Class> types)
            throws Exception
        {
            return new JSONJAXBContext(types.toArray(new Class[0]));
        }
    }

    /**
     * XML context resolver for ISocialModel classes
     */
    @Produces(MediaType.APPLICATION_XML)
    public static class ISocialXMLContextResolver extends BaseContextResolver {
        public ISocialXMLContextResolver() throws Exception {
            super();
        }

        @Override
        protected JAXBContext createContext(Collection<Class> types)
            throws Exception
        {
            return JAXBContext.newInstance(types.toArray(new Class[0]));
        }
    }

    /**
     * This provider replaces the default JAXBContext for JSON objects with
     * one that searches for all classes annotated with
     * @ISocialModel
     */
    public abstract static class BaseContextResolver implements ContextResolver<JAXBContext> {
        private static final Collection<Class> TYPES;
        private final JAXBContext context;

        static {
            TYPES = ISocialWebUtils.getISocialModelTypes();
        }

        public BaseContextResolver() throws Exception {
            this.context = createContext(TYPES);
        }

        public JAXBContext getContext(Class<?> objectType) {
            if (TYPES.contains(objectType)) {
                return context;
            }

            return null;
        }

        protected abstract JAXBContext createContext(Collection<Class> types)
                throws Exception;
    }
    
    /**
     * Class for collections
     */
    public static class StringCollection extends ArrayList<String> {
        public StringCollection() {}
        public StringCollection(Collection<String> c) {
            super (c);
        }
    };
}
