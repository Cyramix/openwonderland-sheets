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
package org.jdesktop.wonderland.modules.isocial.web;

import com.sun.enterprise.security.web.integration.WebPrincipal;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import java.security.Principal;
import java.util.logging.Logger;
import javax.security.auth.Subject;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.glassfish.security.common.Group;
import org.jdesktop.wonderland.front.admin.AdminRegistration;
import org.jdesktop.wonderland.front.admin.AdminRegistration.RegistrationFilter;
import org.jdesktop.wonderland.modules.isocial.weblib.ISocialWebUtils;

/**
 * Jersey servlet context that registers with the admin registry
 * @author Jonathan Kaplan <jonathankap@wonderbuilders.com>
 */
public class ISocialServletContainer extends ServletContainer
    implements ServletContextListener
{
    private static final Logger LOGGER =
            Logger.getLogger(ISocialServletContainer.class.getName());

    private static final String LESSON_REG_KEY = "__iSocialLessonAdminRegistration";

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        RegistrationFilter adminOrGuideFilter = new RegistrationFilter() {
            public boolean isVisible(HttpServletRequest request,
                                     HttpServletResponse response)
            {
                if (ISocialWebUtils.isAdmin(request)) {
                    return true;
                }

                // check whether this is a guide. We can't use the built-in
                // mechanisms because the servlet that is evaluating this
                // request (web-front/admin) doesn't know about the guide
                // group
                Principal p = request.getUserPrincipal();
                if (p instanceof WebPrincipal) {
                    Subject s = ((WebPrincipal) p).getSecurityContext().getSubject();
                    for (Group group : s.getPrincipals(Group.class)) {
                        if (group.getName().equals("guide")) {
                            return true;
                        }
                    }
                }

                return false;
            }
        };

        // register with the UI
        AdminRegistration lr = new AdminRegistration("iSocial Sheets",
                                                     "/isocial-sheets/isocial-sheets/lessons.jsp");
        lr.setFilter(adminOrGuideFilter);
        AdminRegistration.register(lr, context);
        context.setAttribute(LESSON_REG_KEY, lr);
    }

    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        AdminRegistration lr = (AdminRegistration) context.getAttribute(LESSON_REG_KEY);
        if (lr != null) {
            AdminRegistration.unregister(lr, context);
        }
    }
}
