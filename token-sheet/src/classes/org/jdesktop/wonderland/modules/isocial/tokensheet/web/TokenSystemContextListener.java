/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.wonderland.modules.isocial.tokensheet.web;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.jdesktop.wonderland.modules.isocial.weblib.UnitPropertiesRegistration;
import org.jdesktop.wonderland.modules.isocial.weblib.CohortPropertiesRegistration;

/**
 *
 * @author Ryan
 */
public class TokenSystemContextListener implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(TokenSystemContextListener.class.getName());
    private static final String POSSIBLE_PER_STUDENT_PER_LESSON = "tokens.possible.per.student.per.lesson";
    private static final String POSSIBLE_PER_STUDENT_PER_UNIT = "tokens.possible.per.student.per.unit";
    private static final String NUMBER_OF_STUDENTS = "number.of.students";
    private UnitPropertiesRegistration tokenUnitProperties = null;
    private CohortPropertiesRegistration tokenCohortProperties = null;

    public void contextInitialized(ServletContextEvent sce) {
        List<String> unitProps = new ArrayList<String>();
        unitProps.add(POSSIBLE_PER_STUDENT_PER_UNIT);
        unitProps.add(POSSIBLE_PER_STUDENT_PER_LESSON);

        List<String> cohortProps = new ArrayList<String>();
        cohortProps.add(NUMBER_OF_STUDENTS);


        ServletContext context = sce.getServletContext().getContext("/isocial-sheets/isocial-sheets");
        tokenUnitProperties = new UnitPropertiesRegistration(unitProps, new ArrayList<String>());
        tokenCohortProperties = new CohortPropertiesRegistration(cohortProps, new ArrayList<String>());

        UnitPropertiesRegistration.register(tokenUnitProperties, context);
        CohortPropertiesRegistration.register(tokenCohortProperties, context);

        logger.warning("REGISTERING PROPERTIES TO CONTEXT: " + context.getContextPath());
        logger.warning("TOKEN SYSTEM CONTEXT INITIALIZED!");
    }

    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext().getContext("/isocial-sheets/isocial-sheets");
        UnitPropertiesRegistration.unregister(tokenUnitProperties, context);
        CohortPropertiesRegistration.unregister(tokenCohortProperties, context);
    }
}
