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
public class UnitPropertiesRegistration {

    public static final String UNIT_PROP_REGISTRY = "UnitPropertiesRegistry";
    public static final String ADMIN_CONTEXT = "/wonderland-web-front";
    
    private ArrayList<String> propertyNames = null;
    private ArrayList<String> defaultValues = null;
    
    private String url = "";
    private String context = "";
    
    public static void register(UnitPropertiesRegistration reg, ServletContext context) {
        List<UnitPropertiesRegistration> registry = getRegistry(context);
        registry.add(reg);
    }
    
    public static void unregister(UnitPropertiesRegistration reg, ServletContext context) {
        List<UnitPropertiesRegistration> registry = getRegistry(context);
        registry.remove(reg);
    }
    
    public static List<UnitPropertiesRegistration> getRegistry(ServletContext context) {
        ServletContext adminContext = context;//.getContext(ADMIN_CONTEXT);
        
        if(adminContext == null) {
            throw new IllegalStateException("Unable to find context "+ ADMIN_CONTEXT);
        }
        
        List<UnitPropertiesRegistration> registry = (List<UnitPropertiesRegistration>)
                adminContext.getAttribute(UNIT_PROP_REGISTRY);
        
        if(registry == null) {
            throw new IllegalStateException("Unable to find property "+UNIT_PROP_REGISTRY);
        }
        return registry;
    }
    
    public UnitPropertiesRegistration(List<String> keys, List<String> values, String url, String context) {
        propertyNames = new ArrayList<String>();
        propertyNames.addAll(keys);
        
        defaultValues = new ArrayList<String>();
        defaultValues.addAll(values);
        
        this.url = url;
        this.context = context;
    }

    public String getContext() {
        return context;
    }

    
    public String getUrl() {
        return url;
    }
    
    
    public List<String> getNames() {
        return this.propertyNames;
    }
    
    public List<String> getDefaultValues() {
        return this.defaultValues;
    }
    
    public interface RegistrationFilter {
        public boolean isVisible(HttpServletRequest request,
                                 HttpServletResponse response);
    }
       
    public static final RegistrationFilter ADMIN_FILTER = new RegistrationFilter(){

        public boolean isVisible(HttpServletRequest request, HttpServletResponse response) {
            return request.isUserInRole("admin");
        }
    
    };
    
    public static final RegistrationFilter LOGGED_IN_FILTER = new RegistrationFilter() {

        public boolean isVisible(HttpServletRequest request, HttpServletResponse response) {
            return (request.getUserPrincipal() != null);
        }
        
    };
    
       
    
    
}

