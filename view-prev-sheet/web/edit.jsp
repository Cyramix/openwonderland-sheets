<%-- 
    Document   : edit1.jsp
    Created on : Dec 8, 2010, 9:41:51 AM
    Author     : jkaplan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editor</title>

        <link href="../isocial-sheets/css/isocial.css" rel="stylesheet" type="text/css" media="screen" />
        <link href="../isocial-sheets/css/ui-lightness/jquery-ui-1.8.6.custom.css" rel="stylesheet" type="text/css" media="screen" />
        <link href="../isocial-sheets/css/jquery.treeTable.css" rel="stylesheet" type="text/css" media="screen" />
        <link href="../isocial-sheets/css/jquery.ui.selectmenu.css" rel="stylesheet" type="text/css" media="screen" />

        <script src="../isocial-sheets/scripts/json2.js" type="text/javascript"></script>
        <script src="../isocial-sheets/scripts/jquery-1.4.4.min.js" type="text/javascript"></script>
        <script src="../isocial-sheets/scripts/isocial.js" type="text/javascript"></script>
        <script src="../isocial-sheets/scripts/jquery-ui-1.8.6.custom.min.js" type="text/javascript"></script>
        <script src="../isocial-sheets/scripts/jquery.treeTable.js" type="text/javascript"></script>
        <script src="../isocial-sheets/scripts/jquery.ui.selectmenu.js" type="text/javascript"></script>

        <style>
            div#mainForm input { padding-bottom: 10px; }
        </style>

        <script type="text/javascript">
            $(function() {
                $( "#publish" ).button().focus();
                $( "#save" ).button();
                $( "#cancel" ).button();
            });
        </script>
    </head>
    <body>
        <c:set var="details" value="${requestScope['sheet'].details}" />

        <div id="mainForm" class="ui-widget-container ui-widget ui-corner-all">
            <h1>Edit ${details.name}</h1>

            <form id="editForm" action="#">
                <fieldset>
                    <label for="name">Name:</label>
                    <input class="ui-widget-content ui-corner-all" size="30" type="text" name="name" value="${details.internalName}"/>

                    <div style="padding-top: 25px">
                        <input id="publish" name="action" value="Publish" type="submit"/>
                        <input id="save" name="action" value="Save" type="submit"/>
                        <input id="cancel" name="action" value="Cancel" type="submit"/>
                    </div>
                </fieldset>
            </form>
        </div>
    </body>
</html>
