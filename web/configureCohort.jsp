<%-- 
    Document   : configureCohort
    Created on : May 21, 2012, 2:01:44 PM
    Author     : Ryan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Configure Cohort Properties</title>


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

        <style type="text/css">
            table 
            {
                background-color:orange;
                border-radius:10px;
                border-collapse:collapse;
            }
            
            
            tr, th, td {
                border:0px;
                border-collapse:collapse;
            }
            
            
        </style>
        
        
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
            
            var PROPERTIES_URL = "resources/properties/cohort";
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

            $(function() {

                //load the property names from web server
                getPropertyNames();
                    
                //populate our unit variable
                getCurrentUnitFromInstance();
                    
                //initialize our jQuery widgets
                $("#Save-Button").button();
                $("#Cancel-Button").button();
                $("#Add-Property-Button").button();
                
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
                        saveState(key, value);
                    });
                    //once we're done with all .property-row elements,
                    //redirect the user back to where we originally came from.
                    window.location.href="lessons.jsp";

                });
                
                $("#Cancel-Button").click(function() { 
                    window.location.href="lessons.jsp";
                });
                
                $("#Add-Property-Button").click(function() { 
                    $("#Add-Property-Dialog").dialog("open");
                });
                
                
                $("#Add-Property-Dialog").dialog({
                    autoOpen: false,
                    modal: true,
                    buttons: {
                        "Add Property": function() {
                            var key = $(this).find("#key").val();
                            var value = $(this).find("#value").val();
                            
                            addPropertyWithDefaultValue(key, value);
                            
                            $(this).dialog("close");
                        },
                        "Cancel": function() {
                            $(this).dialog("close");
                        }
                    }
                    
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
        <h1>Configure Cohort!</h1>
        <br/>
        <br/>
        <br/>
        <table id="property-table">
            <tr>
                <th>Property Name</th>
                <th>Property Value</th>
            </tr>
<!--            <tr>
                <td><input class="property" type="text" value="Example"></input></td>
                <td><input class="value" type="text" value="Example Value"></input></td>
            </tr>-->
        </table>

        <br/>
        <button id="Save-Button">Save</button>
        <button id="Cancel-Button">Cancel</button>
        
<!--    commenting out this button until we have more time to dedicate
        to working on the UI design. Ideally, I would like to be able to add
        and remove properties on demand except those specified from the server,
        which will be mandatory for inclusion.-->
<!--        <button id="Add-Property-Button">Add Property</button>-->
        
        <div id="Add-Property-Dialog">
            <form>
                <fieldset>
                    <label for="key">Key</label>
                    <input type="text" name="key" id="key" class="text ui-widget-content ui-corner-all" />
                    <label for="value">Value</label>
                    <input type="text" name="value" id="value" class="text ui-widget-content ui-corner-all"/>
                </fieldset>
            </form>
        </div>
    </body>
</html>
