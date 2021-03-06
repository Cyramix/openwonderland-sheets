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
package org.jdesktop.wonderland.modules.isocial.weblib.servlet;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.jdesktop.wonderland.modules.isocial.weblib.*;

/**
 * Jersey servlet context that connects the iSocial web connection
 * @author Jonathan Kaplan <jonathankap@wonderbuilders.com>
 */
public class ISocialContextListener implements ServletContextListener {
    private static final Logger LOGGER =
            Logger.getLogger(ISocialContextListener.class.getName());

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        ISocialWebConnectionFactory.registerContext(context);
        context.setAttribute(ISocialWebUtils.DAO_KEY, ISocialDAOFactory.getInstance());
        
        List<UnitPropertiesRegistration> unitRegistry = new ArrayList<UnitPropertiesRegistration>();
        List<CohortPropertiesRegistration> cohortRegistry = new ArrayList<CohortPropertiesRegistration>();
        
        context.setAttribute(UnitPropertiesRegistration.UNIT_PROP_REGISTRY, unitRegistry);
        context.setAttribute(CohortPropertiesRegistration.COHORT_PROP_REGISTRY, cohortRegistry);
//        LOGGER.warning("SETTING UNIT REGISTRY TO CONTEXT: "+context.getContextPath());
        
        
        
        
    }

    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        ISocialWebConnectionFactory.unregisterContext(context);
        context.removeAttribute(ISocialWebUtils.DAO_KEY);
        context.removeAttribute(UnitPropertiesRegistration.UNIT_PROP_REGISTRY);
        context.removeAttribute(CohortPropertiesRegistration.COHORT_PROP_REGISTRY);
    }
}
