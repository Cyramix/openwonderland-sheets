/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.wonderland.modules.isocial.weblib;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ryan
 */
public class CohortPropertiesRegistration {
    public static final String COHORT_PROP_REGISTRY = "CohortPropertiesRegistry";
    public static final String ADMIN_CONTEXT = "/wonderland-web-front";
    
    private ArrayList<String> propertyNames = null;
    private ArrayList<String> defaultValues = null;
    
    public static List<CohortPropertiesRegistration> getRegistry(ServletContext context) {
        if(context == null) {
            throw new IllegalStateException("Unable to find context: "+context.getContextPath());
        }
        
        List<CohortPropertiesRegistration> registry = (List<CohortPropertiesRegistration>)
                context.getAttribute(COHORT_PROP_REGISTRY);
        
        if(registry == null) {
            throw new IllegalStateException("Unable To find property: "+COHORT_PROP_REGISTRY);
        }
        
        return registry;
    }
    
    public static void register(CohortPropertiesRegistration r, ServletContext context) {
        List<CohortPropertiesRegistration> registry = getRegistry(context);
        registry.add(r);
    }
    
    public static void unregister(CohortPropertiesRegistration r, ServletContext context) {
        List registry = getRegistry(context);
        registry.remove(r);
    }
    
    public CohortPropertiesRegistration(List<String> keys, List<String> values) {
        propertyNames = new ArrayList<String>();
        propertyNames.addAll(keys);
        
        defaultValues = new ArrayList<String>();
        defaultValues.addAll(values);
    }

    public ArrayList<String> getDefaultValues() {
        return defaultValues;
    }

    public ArrayList<String> getPropertyNames() {
        return propertyNames;
    }
    
        public interface RegistrationFilter {
        public boolean isVisible(HttpServletRequest request,
                                 HttpServletResponse response);
    }
       
    public static final UnitPropertiesRegistration.RegistrationFilter ADMIN_FILTER = new UnitPropertiesRegistration.RegistrationFilter(){

        public boolean isVisible(HttpServletRequest request, HttpServletResponse response) {
            return request.isUserInRole("admin");
        }
    
    };
    
    public static final UnitPropertiesRegistration.RegistrationFilter LOGGED_IN_FILTER = new UnitPropertiesRegistration.RegistrationFilter() {

        public boolean isVisible(HttpServletRequest request, HttpServletResponse response) {
            return (request.getUserPrincipal() != null);
        }
        
    };
    
}
