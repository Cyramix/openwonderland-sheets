<%-- 
    Document   : configureUnit
    Created on : May 21, 2012, 1:35:52 PM
    Author     : Ryan
--%>

<%@page import="org.eclipse.persistence.internal.libraries.asm.tree.analysis.Value"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Configure Unit Properties</title>


        <link href="css/isocial.css" rel="stylesheet" type="text/css" media="screen" />
        <link href="css/ui-lightness/jquery-ui-1.8.6.custom.css" rel="stylesheet" type="text/css" media="screen" />
        <link href="css/jquery.treeTable.css" rel="stylesheet" type="text/css" media="screen" />
        <link href="css/jquery.ui.selectmenu.css" rel="stylesheet" type="text/css" media="screen" />

        <script src="scripts/json2.js" type="text/javascript"></script>
        <script src="scripts/jquery-1.4.4.min.js" type="text/javascript"></script>
        <script src="scripts/isocial.js" type="text/javascript"></script>
        <script src="scripts/jquery-ui-1.8.6.custom.min.js" type="text/javascript"></script>
        <script src="scripts/jquery.treeTable.js" type="text/javascript"></script>
        <script src="scripts/jquery.ui.selectmenu.js" type="text/javascript"></script>

        <script type="text/javascript">
            // variables set inside generated servlet            
            <c:set var="unitId" value="${param['unitId']}"/>
            <c:set var ="lessonId" value="${param['lessonId']}"/>
            <c:set var="sheetId" value="${param['sheetId']}"/>                                    
                var unit;
            
                /********************
                 *
                 * CONSTANT VARIABLES BLOCK
                 *
                 ********************/
            
                 var PROPERTIES_URL = "resources/properties/unit";
                 var COHORT_STATE_URL = "resources/cohortState"
                 var CURRENT_INSTANCE_URL = "resources/instances/current";
            
                /*******************
                 *
                 * INITIALIZE BLOCK
                 *
                 *******************/
                
                
                function getPropertyNames() {
                    $.ajax({
                        type: 'GET',
                        url: PROPERTIES_URL,
                        success: function(data) {
                            var array = ensureArray(data);

                            for(var s in array) {
                                var splitArray = array[s].split('=');
                                if(splitArray.length > 1) {
                                    addPropertyWithDefaultValue(splitArray[0], splitArray[1])
                                } else {
                                    addPropertyWithDefaultValue(splitArray[0], "");
                                }
                            }
                            
                        },
                        dataType: 'json',
                        contentType: 'application/json'
                    });
        
                }
                         
//                function populateFields(names, values) {
//                    //TODO
//                    for(var n in names) {
//                        //                     alert(names[n]);
//                     
//                        var key = names[n];
//                        if(values.length < n) {
//                            var val = " ";
//                        } else {
//                            var val = values[n];
//                        }
//                     
//                        addPropertyWithDefaultValue(key, val);
//                     
//                    }
//                }
                
                $(function() {

                    //load the property names from web server
                    getPropertyNames();
                    
                    //populate our unit variable
                    getCurrentUnitFromInstance();
                    
                    //initialize our jQuery widgets
                    $("#Save-Button").button();
                    $("#Cancel-Button").button();
                
                    //when the save button is pressed...
                    $("#Save-Button").click(function() {

                        //for every .property-row element in the DOM
                        $(".property-row").each(function(index) { 

                            //get the property field value
                            var key = $(this).find(".property").val();
                            //get the value field value
                            var value = $(this).find(".value").val();
                        
                            //if the value is nothing, explicitly add "none""
                            if(value == ""
                                || value == " ") {
                                value = "none";
                            }
                        
                            //append the prefix to the key and submit it
                            saveState(addPrefixToKey(key), value);
                        });
                        //once we're done with all .property-row elements,
                        //redirect the user back to where we originally came from.
                        window.location.href="lessons.jsp";

                    });
                
                    $("#Cancel-Button").click(function() { 
                        window.location.href="lessons.jsp";
                    });

                });
            
            /************
             *
             * API BLOCK
             * 
             ************/
                function saveState(key, value) {               
                    $.ajax({
                        type: 'POST',
                        url: COHORT_STATE_URL+"/"+key+"/"+value,                    
                        success: function(data) {
                            //do nothing
                        },
                        //we want this request to block
                        async: false,
                        dataType: 'json',
                        contentType: 'application/json'
                    });
                }
            
                function addPropertyWithDefaultValue(name, value) {
                    var row =   "<tr class=\"property-row\">"
                    row +=          "<td>"
                    row +=              "<input size=\"50\" class=\"property\" type=\"text\" value=\""+name+"\" readonly=\"readonly\"></input>";
                    row +=          "</td>";
                    row +=          "<td>";
                    row +=              "<input size=\"50\" class=\"value\" type=\"text\" value=\""+value+"\"></input>";
                    row +=          "</td>";
                    row +=      "</tr>";
                
                    $("#property-table").append(row);
                }
            
                function getCurrentUnitFromInstance() {
                    $.ajax({
                        type: 'GET',
                        url: CURRENT_INSTANCE_URL,
                        success: function(data) {
                            unit = data.unit;
                        },
                        dataType: 'json',
                        contentType: 'application/json'
                    });
                }
                
                function addPrefixToKey(key) {
                    return unit.id + key;
                }
                
                

            
        </script>

    </head>
    <body>
        <h1>Configure Unit!</h1>

        <br/>
        <br/>
        <br/>

        <table id="property-table" border="0">
            <tr>
                <th>Property Name</th>
                <th>Property Value</th>
            </tr>
            <!--            <tr class="property-row">
                            <td><input class="property" type="text" value="Example"></input></td>
                            <td><input class="value" type="text" value="Example Value"></input></td>
                        </tr>-->
        </table>

        <br/>
        <button id="Save-Button">Save</button>
        <button id="Cancel-Button">Cancel</button>


        <input type="hidden" name="unitid" id="unitid" value="${unitId}"/>
        <input type="hidden" name="lessonid" id="lessonid" value="${lessonId}"/>
        <input type="hidden" name="sheetid" id="sheetid" value="${sheetId}"/>

        
        
        <c:forEach var="script" items="${requestScope['UnitProperties']}">
            <c:import url="${script.url}" context="${script.context}"/>
        </c:forEach>
        
        
    </body>
</html>
