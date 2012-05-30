/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.wonderland.modules.isocial.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import org.jdesktop.wonderland.modules.isocial.weblib.UnitPropertiesRegistration;
import org.jdesktop.wonderland.modules.isocial.weblib.servlet.ISocialServletBase;

/**
 *
 * @author Ryan
 */
public class ConfigureServlet extends ISocialServletBase {

    private static final Logger logger = 
            Logger.getLogger(ConfigureServlet.class.getName());
    
    private static final String ACTION_PARAM="action";
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
           logger.warning("INSIDE CONFIGURE SERVLET!");
           
           String action = request.getParameter(ACTION_PARAM);
           if(action.equalsIgnoreCase("unit")) {
               logger.warning("ACQUIRING URLS!");
               
               List<UnitPropertiesRegistration> registry =
                       UnitPropertiesRegistration.getRegistry(request.getServletContext());
               
               if(registry == null) {
                   logger.warning("UNIT PROPERTIES REGISTRY IS NULL!");
               } else {
                   logger.warning("SIZE OF REGISTRY: "+registry.size());
                   request.setAttribute("UnitProperties", registry);
               }
            
               logger.warning("REDIRECTING TO CONFIGURE UNIT!");
               RequestDispatcher rd = request.getRequestDispatcher("/configureUnit.jsp");
               rd.forward(request, response);
           } else {
               logger.warning("REDIRECTED TO CONFIGURE COHORT!");
               RequestDispatcher rd = request.getRequestDispatcher("/configureCohort.jsp");
               rd.forward(request, response);
           }
           
       } catch(WebApplicationException wae) {
           handleException(wae, response);
       }
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
