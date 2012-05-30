/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.wonderland.modules.isocial.web.resources;

import java.util.*;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.codehaus.jettison.json.JSONArray;
import org.jdesktop.wonderland.modules.isocial.common.model.CohortState;
import org.jdesktop.wonderland.modules.isocial.common.model.state.CSString;
import org.jdesktop.wonderland.modules.isocial.weblib.CohortPropertiesRegistration;
import org.jdesktop.wonderland.modules.isocial.weblib.UnitPropertiesRegistration;
import org.jdesktop.wonderland.modules.isocial.weblib.resources.ISocialResourceBase;

/**
 *
 * @author Ryan
 */
@Path("/properties")
public class PropertiesResource extends ISocialResourceBase {

    private static final Logger logger = Logger.getLogger(PropertiesResource.class.getName());
    @Context
    ServletContext context;

    @GET
    @Path("/unit/urls")
    @Produces({"application/json"})
    public Response getUnitURLS() {
        
        List<String> urls = new ArrayList<String>();
        for(UnitPropertiesRegistration upr: getUnitRegistry()) {
            if(!upr.getUrl().equals(""))
            urls.add(upr.getUrl());
        }
        
        return Response.ok(new JSONArray(urls))
                        .cacheControl(NO_CACHE)
                        .build();
    }
    
    @GET
    @Path("/testing")
    @Produces({"application/json"})
    public Response mapTest() {
        Map<String, String> testdb = new LinkedHashMap<String, String>();


        testdb.put("abc", "def");
        testdb.put("123", "456");
        testdb.put("def", "abc");
        testdb.put("456", "123");
//        JAXBMap<String, String> m = new JAXBMap<String, String>();
//        m.setMap(testdb);

        return Response.ok(new JSONArray(testdb.entrySet())).cacheControl(NO_CACHE).build();
    }

    private Map<String, String> getUnitValuesForProperties(Map<String, String> initialProperties) {
        
        
        //create a map to contain our properties
        Map<String, String> properties = new HashMap<String, String>();
        
        //populate the map with our existing properties
        properties.putAll(initialProperties);
        
        //get the current UnitID as it was prepended to any property keys on
        //the webside, as we need them to be unit-specific.
        String currentUnitID = dao().getCurrentInstance().getUnit().getId();
        
        //get the current cohort id because we need it to acquire cohort states
        String currentCohortID = dao().getCurrentInstance().getCohortId();
        
        //for every key in the initial properties...
        for (String key : initialProperties.keySet()) {
            
            //grab the cohort state for that key for this cohort
            CohortState state = dao().getCohortState(currentCohortID,
                    currentUnitID + key);

            //if the state isn't null, replace the pre-existing default value
            if (state != null) {

                CSString value = (CSString) state.getDetails();
                properties.put(key, value.getValue());
            }

        }
        //return our new map
        return properties;
    }
    
    private Map<String, String> getCohortValuesForProperties(Map<String, String> initialProperties) {
                
        
        //create a map to contain our properties
        Map<String, String> properties = new HashMap<String, String>();
        
        //populate the map with our existing properties
        properties.putAll(initialProperties);        
        
        //get the current cohort id because we need it to acquire cohort states
        String currentCohortID = dao().getCurrentInstance().getCohortId();
        
        //for every key in the initial properties...
        for (String key : initialProperties.keySet()) {
            
            //grab the cohort state for that key for this cohort
            CohortState state = dao().getCohortState(currentCohortID, key);

            //if the state isn't null, replace the pre-existing default value
            if (state != null) {

                CSString value = (CSString) state.getDetails();
                properties.put(key, value.getValue());
            }

        }
        //return our new map
        return properties;
    }

    @GET
    @Path("/unit")
    @Produces({"application/json"})
    public Response getUnitSpecificProperties() {
//        try {
        List<UnitPropertiesRegistration> registry = getUnitRegistry();

        if (registry == null) {
            logger.warning("UNIT PROPERTIES REGISTRY IS NULL!");
            return Response.status(Status.INTERNAL_SERVER_ERROR).cacheControl(NO_CACHE).build();
        }

        if (registry.isEmpty()) {
            logger.warning("UNIT PROPERTIES REGISTRY IS EMPTY!");
        }

        List<String> propertyNames = new LinkedList<String>();
        List<String> defaultValues = new LinkedList<String>();

        //populate both our lists with values from the registry
        for (UnitPropertiesRegistration r : registry) {
            propertyNames.addAll(r.getNames());
            defaultValues.addAll(r.getDefaultValues());
        }

        //create a map for processing
        Map<String, String> initial = new HashMap<String, String>();

        //populate our map with the content of our lists
        for (int i = 0; i <= propertyNames.size() - 1; i++) {
            String key = propertyNames.get(i);
            String value = "";
            
            //default values are not mandatory so it's possible there may be 
            //more keys than values. If so, grab it, otherwise set the default
            //value to ""
            if (i < defaultValues.size() - 1) {
                value = defaultValues.get(i);
            }
            initial.put(key, value);
        }


        //grab any existing states if they appear.
        Map<String, String> properties = getUnitValuesForProperties(initial);

        return Response.ok(new JSONArray(properties.entrySet())).cacheControl(NO_CACHE).build();
    }

    @GET
    @Path("/unit/values")
    @Produces({"application/json"})
    public Response getUnitSpecificDefaultValues() {
        List<UnitPropertiesRegistration> registry = getUnitRegistry();

        if (registry == null) {
            logger.warning("UNIT PROPERTIES REGISTRY IS NULL!");
            return Response.status(Status.INTERNAL_SERVER_ERROR).cacheControl(NO_CACHE).build();
        }

        if (registry.isEmpty()) {
            logger.warning("UNIT PROPERTIES REGISTRY IS EMPTY!");
        }

        List<String> defaultValues = new LinkedList<String>();

        for (UnitPropertiesRegistration r : registry) {
            defaultValues.addAll(r.getDefaultValues());
        }

        return Response.ok(new JSONArray(defaultValues)).cacheControl(NO_CACHE).build();

    }

    @GET
    @Path("/cohort")
    @Produces({"application/json"})
    public Response getCohortSpecificProperties() {
        List<CohortPropertiesRegistration> registry = getCohortRegistry();
        //<editor-fold defaultstate="collapsed" desc="comment">
        if (registry == null) {
            logger.warning("COHORT PROPERTIES REGISTRY IS NULL!");
            return Response.status(Status.INTERNAL_SERVER_ERROR).cacheControl(NO_CACHE).build();
        }
        
        if (registry.isEmpty()) {
            logger.warning("COHORT PROPERTIES REGISTRY IS EMPTY!");
        }
        //</editor-fold>
        List<String> propertyNames = new LinkedList<String>();
        List<String> defaultValues = new LinkedList<String>();

        for (CohortPropertiesRegistration r : registry) {
            propertyNames.addAll(r.getPropertyNames());
            defaultValues.addAll(r.getDefaultValues());
        }
        
        //create a map for processing
        Map<String, String> initial = new HashMap<String, String>();
        
        //populate our map with the content of our lists
        for(int i = 0; i <= propertyNames.size() - 1; i++) {
            String key = propertyNames.get(i);
            String value = "";
            
            //default values are not mandatory so it's possible there may be
            //more keys than values. If so, grab it, otherwise set the default 
            //value to ""
            
            if(i < defaultValues.size() - 1) {
                value = defaultValues.get(i);
            }
            initial.put(key, value);
            
            //grab any existing states if they appear.
            Map<String, String> properties = getCohortValuesForProperties(initial);
            
            return Response
                    .ok(new JSONArray(properties.entrySet()))
                    .cacheControl(NO_CACHE)
                    .build();
        }
        

        return Response.ok(new JSONArray(propertyNames)).cacheControl(NO_CACHE).build();
    }

    @GET
    @Path("/cohort/values")
    @Produces({"application/json"})
    public Response getCohortSpecificDefaultValues() {
        List<CohortPropertiesRegistration> registry = getCohortRegistry();

        if (registry == null) {
            logger.warning("COHORT PROPERTIES REGISTRY IS NULL!");
            return Response.status(Status.INTERNAL_SERVER_ERROR).cacheControl(NO_CACHE).build();
        }

        if (registry.isEmpty()) {
            logger.warning("COHORT PROPERTIES REGISTRY IS EMPTY!");
        }

        List<String> defaultValues = new LinkedList<String>();

        for (CohortPropertiesRegistration r : registry) {
            defaultValues.addAll(r.getDefaultValues());
        }

        return Response.ok(new JSONArray(defaultValues)).cacheControl(NO_CACHE).build();
    }

    private List<UnitPropertiesRegistration> getUnitRegistry() {
        return (List<UnitPropertiesRegistration>) context.getAttribute(UnitPropertiesRegistration.UNIT_PROP_REGISTRY);
    }

    private List<CohortPropertiesRegistration> getCohortRegistry() {
        return (List<CohortPropertiesRegistration>) context.getAttribute(CohortPropertiesRegistration.COHORT_PROP_REGISTRY);
    }

    /*
     * HARDHAT AREA - TESTING A MARSHAL SYSTEM FOR A MAP
     */
    static class MapElements<K, V> {
//        @XmlElement

        private K key;
//        @XmlElement 
        private V value;

        public MapElements() {
        }

        public MapElements(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @XmlElement
        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        @XmlElement
        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }

    static class MapAdapter<K, V> extends XmlAdapter<ArrayList<MapElements<K, V>>, Map<K, V>> {

        public ArrayList<MapElements<K, V>> marshal(Map<K, V> arg0) throws Exception {
            ArrayList<MapElements<K, V>> elements = new ArrayList<MapElements<K, V>>();

            for (Map.Entry<K, V> entry : arg0.entrySet()) {
                elements.add(new MapElements<K, V>(entry.getKey(),
                        entry.getValue()));
            }
            return elements;


        }

        public Map<K, V> unmarshal(ArrayList<MapElements<K, V>> arg0) throws Exception {
            Map<K, V> r = new HashMap<K, V>();

            for (MapElements<K, V> me : arg0) {
                r.put(me.key, me.value);
            }
            return r;

        }
    }

    @XmlRootElement
    static class JAXBMap<K, V> {

        private Map<K, V> map;

        public JAXBMap() {
            map = new HashMap<K, V>();
        }

        @XmlJavaTypeAdapter(MapAdapter.class)
        public Map<K, V> getMap() {
            return map;
        }

        public void setMap(Map<K, V> map) {
            this.map = map;
        }
    }
}
