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
package org.jdesktop.wonderland.modules.isocial.web.resources;

import com.sun.jersey.api.json.JSONJAXBContext;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import org.jdesktop.wonderland.modules.isocial.common.model.Cohort;
import org.jdesktop.wonderland.modules.isocial.common.model.Instance;
import org.jdesktop.wonderland.modules.isocial.common.model.Result;
import org.jdesktop.wonderland.modules.isocial.common.model.Sheet;
import org.jdesktop.wonderland.modules.isocial.common.model.query.CSVResult;
import org.jdesktop.wonderland.modules.isocial.common.model.query.CSVResultCollection;
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
     * JSON context resolver for ISocialModel classes
     */
    @Provider
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
    @Provider
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
    abstract static class BaseContextResolver implements ContextResolver<JAXBContext> {
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
     * This provider handles serializing String collections to JSON
     */
    @Provider
    public static class CollectionBodyWriter implements MessageBodyWriter<StringCollection> {
        public boolean isWriteable(Class<?> clazz, Type type, Annotation[] antns,
                                   MediaType mt)
        {
            return StringCollection.class.isAssignableFrom(clazz) &&
                    MediaType.APPLICATION_JSON_TYPE.isCompatible(mt);
        }

        public long getSize(StringCollection t, Class<?> clazz, Type type,
                            Annotation[] antns, MediaType mt)
        {
            return -1;
        }

        public void writeTo(StringCollection t, Class<?> clazz, Type type,
                            Annotation[] antns, MediaType mt,
                            MultivaluedMap<String, Object> mm,
                            OutputStream out)
            throws IOException, WebApplicationException
        {
            PrintStream ps = new PrintStream(out);
            ps.append("[");

            boolean first = true;

            for (String s : t) {
                if (first) {
                    first = false;
                } else {
                    ps.append(",");
                }

                ps.append("\"" + s + "\"");
            }
            ps.append("]");
            ps.flush();
        }
    }

    /**
     * This provider handles serializing CSVResultTable objects to csv files
     */
    @Provider
    public static class CSVBodyWriter
            implements MessageBodyWriter<CSVResultCollection<CSVResultTable>>
    {
        public boolean isWriteable(Class<?> clazz, Type type, Annotation[] antns,
                                   MediaType mt)
        {
            MediaType csvType = new MediaType("application", "zip");
            return CSVResultCollection.class.isAssignableFrom(clazz) &&
                    csvType.isCompatible(mt);
        }

        public long getSize(CSVResultCollection<CSVResultTable> t,
                            Class<?> clazz, Type type,
                            Annotation[] antns, MediaType mt)
        {
            return -1;
        }

        public void writeTo(CSVResultCollection<CSVResultTable> t,
                            Class<?> clazz, Type type,
                            Annotation[] antns, MediaType mt,
                            MultivaluedMap<String, Object> mm,
                            OutputStream out)
            throws IOException, WebApplicationException
        {
            ZipOutputStream zos = new ZipOutputStream(out);
            for (CSVResultTable res : t.getItems()) {
                ZipEntry ze = new ZipEntry(getFileName(res));
                zos.putNextEntry(ze);
                writeTo(zos, res);
                zos.closeEntry();
            }
            zos.close();
        }

        private void writeTo(OutputStream out, CSVResultTable t) {
            PrintStream ps = new PrintStream(out);

            // header line
            ps.append(quote("Cohort") + ",");
            ps.append(quote("Unit") + ",");
            ps.append(quote("Lesson") + ",");
            ps.append(quote("Sheet") + ",");
            ps.append(quote("Instance") + ",");
            ps.append(quote("Student") + ",");

            for (Iterator<String> i = t.getHeadings().iterator(); i.hasNext();) {
                ps.append(quote(i.next()));

                if (i.hasNext()) {
                    ps.append(",");
                }
            }

            ps.println();

            // value lines
            for (CSVResult result : t.getResults()) {
                ps.append(quote(t.getCohort()) + ",");
                ps.append(quote(t.getUnit()) + ",");
                ps.append(quote(t.getLesson()) + ",");
                ps.append(quote(t.getSheet()) + ",");
                ps.append(quote(t.getInstance()) + ",");
                ps.append(quote(result.getStudent()) + ",");

                for (Iterator<String> i = result.getResults().iterator(); i.hasNext();) {
                    ps.append(quote(i.next()));

                    if (i.hasNext()) {
                        ps.append(",");
                    }
                }

                ps.println();
            }

            ps.flush();
        }

        private String getFileName(CSVResultTable t) {
            return fileName(t.getCohort()) + "-" + fileName(t.getUnit()) + "-" +
                   fileName(t.getLesson()) + "-" + fileName(t.getSheet()) + "-" +
                   fileName(t.getInstance()) + "-" + t.getSheetId() + "-" +
                   t.getInstanceId() + ".csv";
        }

        private String quote(String s) {
            s = s.replace('\"', '_');
            return "\"" + s + "\"";
        }

        private String fileName(String s) {
            return s.replaceAll("\\W", "_");
        }
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
