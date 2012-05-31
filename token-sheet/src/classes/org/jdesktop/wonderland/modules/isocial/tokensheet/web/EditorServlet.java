/**
 * iSocial Project http://isocial.missouri.edu
 *
 * Copyright (c) 2011, University of Missouri iSocial Project, All Rights
 * Reserved
 *
 * Redistributions in source code form must reproduce the above copyright and
 * this condition.
 *
 * The contents of this file are subject to the GNU General Public License,
 * Version 2 (the "License"); you may not use this file except in compliance
 * with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/gpl-license.php.
 *
 * The iSocial project designates this particular file as subject to the
 * "Classpath" exception as provided by the iSocial project in the License file
 * that accompanied this code.
 */
package org.jdesktop.wonderland.modules.isocial.tokensheet.web;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import org.jdesktop.wonderland.modules.isocial.common.model.Lesson;
import org.jdesktop.wonderland.modules.isocial.common.model.Sheet;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.TokenSheet;
import org.jdesktop.wonderland.modules.isocial.weblib.servlet.ISocialServletBase;

/**
 * Servlet that handles editing for the token sheet
 *
 * @author Jonathan Kaplan <jonathankap@wonderbuilders.com>
 * @author Ryan Babiuch
 */
public class EditorServlet extends ISocialServletBase {

    private static final Logger LOGGER =
            Logger.getLogger(EditorServlet.class.getName());
    private static final String UNIT_ID_PARAM = "unitId";
    private static final String LESSON_ID_PARAM = "lessonId";
    private static final String SHEET_ID_PARAM = "sheetId";
    private static final String ACTION_PARAM = "action";
    private static final String SHEET_ATTR = "sheet";

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            LOGGER.warning("INSIDE TOKEN EDITOR SERVLET!");

            for (Map.Entry<String, String[]> e : request.getParameterMap().entrySet()) {

                String values = "";
                for (String s : e.getValue()) {
                    values += (s + "\n");
                }
                LOGGER.warning("KEY: " + e.getKey() + "\n--Values--:\n" + values);
            }
            Sheet sheet = null;
            if (request.getParameter(LESSON_ID_PARAM) != null
                    && !request.getParameter(LESSON_ID_PARAM).equals("all")) {
                sheet = getSheet(request);

                String action = request.getParameter(ACTION_PARAM);
                if (action == null) {
                    action = "edit";

                }

                // store it in request scope
                request.setAttribute(SHEET_ATTR, sheet);
                if (action.equalsIgnoreCase("save")
                        || action.equalsIgnoreCase("publish")) {
                    LOGGER.warning("SAVE");
                    doSave(sheet, request, response);
                } else if (action.equalsIgnoreCase("cancel")) {
                    LOGGER.warning("CANCEL");
                    doCancel(sheet, request, response);
                } else {
                    // default action
                    LOGGER.warning("EDIT");
                    doEdit(sheet, request, response);
                }


            } else {
                String action = request.getParameter(ACTION_PARAM);
                LOGGER.warning("HANDLING GROUP OF TOKEN SHEETS.");
                if (action == null) {
                    LOGGER.warning("EDIT ALL!");
                    doEditAll(request, response);
                } else if (action.equalsIgnoreCase("save")
                        || action.equalsIgnoreCase("publish")) {
                    LOGGER.warning("SAVE ALL!");
                    doSaveAll(request, response);
                } else if (action.equalsIgnoreCase("cancel")) {
                    LOGGER.warning("CANCEL ALL!");
                    doCancelAll(request, response);
                }
                return;
            }

        } catch (WebApplicationException wae) {
            handleException(wae, response);
        }
    }

    /**
     * Handle the edit action on a sheet. This simply forwards to the relevant
     * editor.
     *
     * @param sheet the sheet to edit
     * @param request the servlet request
     * @param response the servlet response
     */
    private void doEdit(Sheet sheet, HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        String editor = null;
        if (sheet.getDetails() instanceof TokenSheet) {
            editor = "/edit.jsp";
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        RequestDispatcher rd = request.getRequestDispatcher(editor);
        rd.forward(request, response);
    }

    private void doEditAll(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String editor = "/edit.jsp";

        RequestDispatcher rd = request.getRequestDispatcher(editor);
        rd.forward(request, response);
    }

    /**
     * Save the sheet details to the DAO
     *
     * @param sheet the sheet to save
     * @param request the servlet request
     * @param response the servlet response
     */
    private void doSave(Sheet sheet, HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        if (sheet.getDetails() instanceof TokenSheet) {
            String name = request.getParameter("name");
            ((TokenSheet) sheet.getDetails()).setName(name);

            String autoOpen = request.getParameter("autoOpen");
            ((TokenSheet) sheet.getDetails()).setAutoOpen(Boolean.parseBoolean(autoOpen));

            String dockable = request.getParameter("dockable");
            ((TokenSheet) sheet.getDetails()).setDockable(Boolean.parseBoolean(dockable));

//            String maxStudents = request.getParameter("maxStudents");
//            ((TokenSheet) sheet.getDetails()).setMaxStudents(Integer.parseInt(maxStudents));
//
//            String maxLessonTokens = request.getParameter("maxLessonTokens");
//            ((TokenSheet) sheet.getDetails()).setMaxLessonTokens(Integer.parseInt(maxLessonTokens));
//
//            String maxUnitTokens = request.getParameter("maxUnitTokens");
//            ((TokenSheet) sheet.getDetails()).setMaxUnitTokens(Integer.parseInt(maxUnitTokens));
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        boolean publish = request.getParameter("action").equalsIgnoreCase("publish");
        sheet.setPublished(publish);

        dao(request).updateSheet(sheet);


        String query = "?expanded=" + sheet.getUnitId()
                + "&expanded=" + sheet.getUnitId() + "-" + sheet.getLessonId();


        response.sendRedirect("/isocial-sheets/isocial-sheets/lessons.jsp" + query);
    }

    private void doSaveAll(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
//        Sheet[] sheets = getSheets(request);
        LOGGER.warning("INSIDE SAVE ALL!");
        for (Sheet sheet : getSheets(request)) {
            if (sheet.getDetails() instanceof TokenSheet) {
                String name = request.getParameter("name");
                ((TokenSheet) sheet.getDetails()).setName(name);

                String autoOpen = request.getParameter("autoOpen");
                ((TokenSheet) sheet.getDetails()).setAutoOpen(Boolean.parseBoolean(autoOpen));

                String dockable = request.getParameter("dockable");
                ((TokenSheet) sheet.getDetails()).setDockable(Boolean.parseBoolean(dockable));

//                String maxStudents = request.getParameter("maxStudents");
//                ((TokenSheet) sheet.getDetails()).setMaxStudents(Integer.parseInt(maxStudents));
//
//                String maxLessonTokens = request.getParameter("maxLessonTokens");
//                ((TokenSheet) sheet.getDetails()).setMaxLessonTokens(Integer.parseInt(maxLessonTokens));
//
//                String maxUnitTokens = request.getParameter("maxUnitTokens");
//                ((TokenSheet) sheet.getDetails()).setMaxUnitTokens(Integer.parseInt(maxUnitTokens));
            } else {
                continue;
            }

            dao(request).updateSheet(sheet);

        }

        response.sendRedirect("/isocial-sheets/isocial-sheets/lessons.jsp");

    }

    /**
     * Return to the lesson editor without saving
     *
     * @param sheet the sheet that we are editing
     * @param request the servlet request
     * @param response the servlet response
     */
    private void doCancel(Sheet sheet, HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        String query = "?expanded=" + sheet.getUnitId()
                + "&expanded=" + sheet.getUnitId() + "-" + sheet.getLessonId();


        response.sendRedirect("/isocial-sheets/isocial-sheets/lessons.jsp" + query);
    }

    private void doCancelAll(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        response.sendRedirect("/isocial-sheets/isocial-sheets/lessons.jsp");
    }

    private Sheet getSheet(HttpServletRequest request) {
        Sheet out = null;

        // first see if the request specified a new sheet to load
        String unitId = request.getParameter(UNIT_ID_PARAM);
        String lessonId = request.getParameter(LESSON_ID_PARAM);
        String sheetId = request.getParameter(SHEET_ID_PARAM);
        if (unitId != null && lessonId != null && sheetId != null) {
            out = dao(request).getSheet(unitId, lessonId, sheetId);

            if (out != null) {
                // store the sheet in the session
                request.getSession().setAttribute(SHEET_ATTR, out);
                return out;
            }
        }

        // next see if there is a stored sheet in the session
        out = (Sheet) request.getSession().getAttribute(SHEET_ATTR);
        if (out != null) {
            return out;
        }

        // not found
        throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).
                entity("No sheet found").build());
    }

    private Set<Sheet> getSheets(HttpServletRequest request) {
        LOGGER.warning("GETTING SHEETS!");
        String unitId = request.getParameter(UNIT_ID_PARAM);
//        String lessonId = request.getParameter(LESSON_ID_PARAM);
        String groupId = request.getParameter("groupId");
        Set<Sheet> groupedSheets = new HashSet<Sheet>();

        //sanity check
        if (groupId == null
                || groupId.equals("")
                || groupId.equals("undefined")
                || groupId.equals(" ")) {
            LOGGER.warning("PROBLEM WITH groupId: " + groupId);
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).entity("lesson id not null!").build());
        }

        Collection<Lesson> lessons = dao(request).getLessons(unitId);

        //for every lesson
        LOGGER.warning("For every lesson in " + lessons.size() + " lessons.");
        for (Lesson lesson : lessons) {
            //acquire all sheets with the same groupId
            LOGGER.warning("Acquire all sheets with the same groupId: " + groupId);
            Collection<Sheet> sheets = dao(request).getSheets(unitId, lesson.getId());
            //for every sheet in this lesson...
//            LOGGER.warning("For every sheet in this lesson: "+lesson.getId());
            for (Sheet sheet : sheets) {
                //check if this sheet is in the specified group
//                LOGGER.warning("Check this sheet is in the specified group: "+groupId);
                if (sheet.getGroupId().equals(groupId)) {
                    LOGGER.warning("Add sheet to group: " + groupId);
                    //if so, add this sheet to the list of sheets
                    groupedSheets.add(sheet);
                }
            }
        }
        LOGGER.warning("RETURNING " + groupedSheets.size() + " SHEETS!");
        return groupedSheets;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
