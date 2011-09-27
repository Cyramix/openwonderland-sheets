<%-- 
    Document   : cohorts
    Created on : Nov 29, 2010, 4:45:04 PM
    Author     : jkaplan
--%>

<%@page contentType="text/html" pageEncoding="MacRoman"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/isocial.tld" prefix="i" %>

<html>
    <head>
        <!--link href="/wonderland-web-front/css/base.css" rel="stylesheet" type="text/css" media="screen" />
        <link href="/wonderland-web-front/css/module.css" rel="stylesheet" type="text/css" media="screen" /-->

        <link href="css/isocial.css" rel="stylesheet" type="text/css" media="screen" />
        <link href="css/ui-lightness/jquery-ui-1.8.6.custom.css" rel="stylesheet" type="text/css" media="screen" />
        <link href="css/jquery.treeTable.css" rel="stylesheet" type="text/css" media="screen" />
        <link href="css/jquery.ui.selectmenu.css" rel="stylesheet" type="text/css" media="screen" />

        <script src="scripts/json2.js" type="text/javascript"></script>
        <script src="scripts/jquery-1.4.4.min.js" type="text/javascript"></script>
        <script src="scripts/isocial.js" type="text/javascript"></script>
        <script src="scripts/date.js" type="text/javascript"></script>
        <script src="scripts/jquery-ui-1.8.6.custom.min.js" type="text/javascript"></script>
        <script src="scripts/jquery.treeTable.js" type="text/javascript"></script>
        <script src="scripts/jquery.ui.selectmenu.js" type="text/javascript"></script>

        <style>
            div#results-form select { width: 300px; }
            div#results-table { margin: 20px 0; }
            div#results-view { width: 100%; margin: 20px 0; }

            .results-info td { border: 0px; padding: 2px 0px 2px 5px; }
            .results-table tr:nth-child(even) { background: white; }
        </style>

        <script type="text/javascript">
            var selectedCohort = "${empty param.cohortId ? "" : param.cohortId}";
            var selectedStudent = "${empty param.student ? "" : param.student}";
            var selectedInstance = "${empty param.instanceId ? "" : param.instanceId}";
            var selectedUnit = "${empty param.unitId ? "" : param.unitId}";
            var selectedLesson = "${empty param.lessonId ? "" : param.lessonId}";
            var selectedSheet = "${empty param.sheetId ? "" : param.sheetId}";

            $(function() {
                $( "#results-view" ).hide();

                $( "#results-cohort-select" ).selectmenu({});
                $( "#results-student-select" ).selectmenu({});
                $( "#results-instance-select" ).selectmenu({});
                $( "#results-unit-select" ).selectmenu({});
                $( "#results-lesson-select" ).selectmenu({});
                $( "#results-sheet-select" ).selectmenu({});

                $( "#new-query" ).button().click(function() {
                    window.location.reload();
                });

                $( "#download-all" ).button().click(function() {
                    $( "#download-all-form" ).submit();
                });

                loadValues();
                
                $( "#results-form" ).dialog({
                    autoOpen: true,
                    height: 500,
                    width: 350,
                    modal: true,
                    buttons: {
                        "Get Results": function() {
                            populateDownloadAll();
                            doQuery();
                            $( this ).dialog( "close" );
                        }
                    }
                });
            });
             
            function loadValues() {
                retrieveMenu({
                    url: "resources/cohorts",
                    selector: "results-cohort",
                    name: "Cohort",
                    selected: selectedCohort,
                    getData: function(data) {
                        return data.cohort;
                    },
                    select: function(event, options) {
                        cohortSelected(options.value);
                    }
                });

                retrieveMenu({
                    url: "resources/units",
                    selector: "results-unit",
                    name: "Unit",
                    selected: selectedUnit,
                    getData: function(data) {
                        return data.unit;
                    },
                    select: function(event, options) {
                        unitSelected(options.value);
                    }
                });

                retrieveMenu({
                    url: "resources/instances",
                    selector: "results-instance",
                    name: "Date",
                    selected: selectedInstance,
                    getData: function(data) {
                        return data.instance;
                    },
                    getName: function(instance) {
                        var date = new Date(Date.parse(instance.created));
                        return date.format();
                    },
                    select: function(event, options) {
                        instanceSelected(options.value);
                    }
                });
            }

            function cohortSelected(id) {
                if (id == "none") {
                    resetMenu("results-student", "Student", "Select Cohort...")
                    return;
                }

                retrieveMenu({
                    url: "resources/cohorts/" + id + "/students",
                    selector: "results-student",
                    name: "Student",
                    selected: selectedStudent,
                    getId: function(student) {
                        return student;
                    },
                    getName: function(student) {
                        return student;
                    }
                });
            }

            function unitSelected(id) {
                if (id == "none") {
                    resetMenu("results-lesson", "Lesson", "Select Unit...");
                    resetMenu("results-sheet", "Sheet", "Select Lesson...");
                    return;
                }

                retrieveMenu({
                    url: "resources/lessons/" + id,
                    selector: "results-lesson",
                    name: "Lesson",
                    selected: selectedLesson,
                    getData: function(data) {
                        return data.lesson;
                    },
                    select: function(event, options) {
                        lessonSelected(id, options.value);
                    }
                });
            }

            function lessonSelected(unitId, id) {
                if (id == "none") {
                    resetMenu("results-sheet", "Sheet", "Select Lesson...");
                    return;
                }

                retrieveMenu({
                    url: "resources/sheets/" + unitId + "/" + id,
                    selector: "results-sheet",
                    name: "Sheet",
                    selected: selectedSheet,
                    getData: function(data) {
                        return data.sheet;
                    }
                });
            }

            function retrieveMenu(options) {
                $.getJSON(options.url, function(data) {
                    populateMenu(data, options);
                });
            }

            function populateMenu(data, options) {
                var getData = options.getData || function(data) {
                    return data;
                };

                var getId = options.getId || function(obj) {
                    return obj.id;
                };

                var getName = options.getName || function(obj) {
                    return obj.name;
                };

                var selectFound;

                $( "#" + options.selector ).html("");
                $( "#" + options.selector ).append(
                        "<label for=\"" + options.selector + "-select\">" +
                        options.name + "</label>" +
                        "<select id=\"" + options.selector + "-select\"" +
                        "name=\"" + options.selector + "-select\">" +
                        "<option value=\"none\">All " + options.name + "s</option>" +
                        "</select>");

                var res = ensureArray(getData(data));
                $.each(res, function(index, obj) {
                    var selected = "";
                    if (getId(obj) == options.selected) {
                        selected = "selected=\"selected\"";
                        selectFound = "true";
                    }

                    $( "#" + options.selector + "-select" ).append(
                        "<option value=\"" + getId(obj) + "\"" + selected + ">" +
                        getName(obj) + "</option>");
                });

                $( "#" + options.selector + "-select" ).selectmenu({
                    select: options.select
                });

                // if the selected item was found, fake a select event
                if (selectFound) {
                    var opts = new Object();
                    opts.value = options.selected;
                    options.select(null, opts);
                }
            }

            function resetMenu(selector, name, text) {
                $( "#" + selector ).html("");
                $( "#" + selector ).append(
                        "<label for=\"" + selector + "-select\">" +
                        name + "</label>" +
                        "<select id=\"" + selector + "-select\"" +
                        "name=\"" + selector + "-select\">" +
                        "<option value=\"none\">" + text + "</option>" +
                        "</select>");
                $( "#" + selector + "-select" ).selectmenu({});
            }

            function doQuery() {
                var query = new Object();
                setFromSelect(query, "cohortId", "results-cohort-select");
                setFromSelect(query, "studentId", "results-student-select");
                setFromSelect(query, "unitId", "results-unit-select");
                setFromSelect(query, "lessonId", "results-lesson-select");
                setFromSelect(query, "sheetId", "results-sheet-select");
                setFromSelect(query, "instanceId", "results-instance-select");
                
                $.ajax({
                    type: 'POST',
                    url: "resources/results",
                    data: JSON.stringify(query),
                    contentType: "application/json",
                    dataType: 'json',
                    success: function(data) {
                        var res = ensureArray(data['csv-result-table']);
                        $.each(res, loadResult);
                    }
                });
            }

            function populateDownloadAll() {
                setFieldFromSelect("download-all-cohort", "results-cohort-select");
                setFieldFromSelect("download-all-student", "results-student-select");
                setFieldFromSelect("download-all-unit", "results-unit-select");
                setFieldFromSelect("download-all-lesson", "results-lesson-select");
                setFieldFromSelect("download-all-sheet", "results-sheet-select");
                setFieldFromSelect("download-all-instance", "results-instance-select");
            }

            function doDownload(data) {
                $( "#download-cohort" ).val(data.cohortId);
                $( "#download-unit" ).val(data.unitId);
                $( "#download-lesson" ).val(data.lessonId);
                $( "#download-sheet" ).val(data.sheetId);
                $( "#download-instance" ).val(data.instanceId);

                $( "#download-form" ).submit();
            }

            function setFromSelect(query, key, selector) {
                var out = $( "#" + selector ).val();
                if (out && out != "none") {
                    query[key] = out;
                }
            }

            function setFieldFromSelect(field, selector) {
                var out = $( "#" + selector ).val();
                if (out && out != "none") {
                    $( "#" + field ).val(out);
                } else {
                    $( "#" + field ).val("");
                }
            }

            function loadResult(index, data) {
                var studentCount = data.results.length || 1;

                $( "#results-table-body" ).append("<tr>" +
                   "<td>" + data.cohort + "</td>" +
                   "<td>" + data.unit + "</td>" +
                   "<td>" + data.lesson + "</td>" +
                   "<td>" + data.sheet + "</td>" +
                   "<td>" + data.instance + "</td>" +
                   "<td>" + studentCount + "</td>" +
                   "<td><a id=\"view\" href=\"#\">View</a>" +
                   "    <a id=\"download\" href=\"#\">Download</a></td>" +
                   "</tr>"
                );

                $( "#results-table-body > tr:last a#view" ).button().click(function() {
                    showResultsDialog(data);
                });
                $( "#results-table-body > tr:last a#download" ).button().click(function() {
                    doDownload(data);
                });
            }

            function showResultsDialog(data) {
                $( "#results-view" ).append("<div title=\"View Results\">");

                var dialog = $( "#results-view > div:last");
                dialog.html("<table class=\"results-info\">" +
                            "<tr><td>Cohort:</td><td>" + data.cohort + "</td></tr>" +
                            "<tr><td>Date:</td><td>" + data.instance + "</td></tr>" +
                            "<tr><td>Unit:</td><td>" + data.unit + "</td></tr>" +
                            "<tr><td>Lesson:</td><td>" + data.lesson + "</td></tr>" +
                            "<tr><td>Sheet:</td><td>" + data.sheet + "</td></tr>" +
                            "<table class=\"ui-widget ui-widget-content\">" +
                            "<thead class=\"ui-widget-header results-table\">" +
                            "<tr><td>Student</td></tr>" +
                            "</thead>" +
                            "<tbody class=\"results-table\"></tbody></table>");

                var headings = ensureArray(data.headings);
                $.each(headings, function(index, heading) {
                    dialog.find( "thead.results-table > tr" ).append("<td>" +
                        heading + "</td>");
                });

                var results = ensureArray(data.results);
                $.each(results, function(index, result) {
                    dialog.find( "tbody.results-table" ).append("<tr><td>" + result.student + "</td></tr>");

                    var studentResults = ensureArray(result.results);
                    $.each(studentResults, function(index, result) {
                        dialog.find( "tbody.results-table > tr:last" ).append("<td>" + result + "</td>");
                    });
                });

                dialog.dialog({
                    autoOpen: true,
                    modal: false,
                    buttons: {
                        "Close": function() {
                            $( this ).dialog( "close" );
                        }
                    }
                });
            }
        </script>


        <title>View Results</title>
    </head>
    <body>
        <%@ include file="navigation.jspf" %>
        <script type="text/javascript">
            navSelect("results");
        </script>

        <div id="results-form" title="Enter Query">
            <form action="#">
                <fieldset>
                    <div id="results-cohort">
                        <label for="result-cohort-select">Cohort</label>
                        <select id="results-cohort-select">
                            <option value="none">Loading</option>
                        </select>
                    </div>

                    <div id="results-student">
                        <label for="result-student-select">Student</label>
                        <select id="results-student-select">
                            <option value="none">Select Cohort...</option>
                        </select>
                    </div>

                    <div id="results-unit">
                        <label for="results-unit-select">Unit</label>
                        <select id="results-unit-select">
                            <option value="none">Loading</option>
                        </select>
                    </div>

                    <div id="results-lesson">
                        <label for="results-lesson-select">Lesson</label>
                        <select id="results-lesson-select">
                            <option value="none">Select Unit...</option>
                        </select>
                    </div>

                    <div id="results-sheet">
                        <label for="results-sheet-select">Sheet</label>
                        <select id="results-sheet-select">
                            <option value="none">Select Lesson...</option>
                        </select>
                    </div>

                    <div id="results-instance">
                        <label for="results-instance-select">Date</label>
                        <select id="results-instance-select">
                            <option value="none">Loading</option>
                        </select>
                    </div>
                </fieldset>
            </form>
        </div>

        <div id="results-table">
            <table class="ui-widget ui-widget-content">
                <thead class="ui-widget-header">
                    <tr>
                        <td>Cohort</td>
                        <td>Unit</td>
                        <td>Lesson</td>
                        <td>Sheet</td>
                        <td>Date</td>
                        <td>Students</td>
                        <td></td>
                    </tr>
                </thead>
                <tbody id="results-table-body">
                </tbody>
            </table>
        </div>

        <div id="results-view"></div>

        <div id="download-div">
            <form id="download-all-form" action="resources/results/results.csv.zip" method="POST">
                <fieldset>
                    <input type="hidden" name="cohortId" id="download-all-cohort">
                    <input type="hidden" name="studentId" id="download-all-student">
                    <input type="hidden" name="unitId" id="download-all-unit">
                    <input type="hidden" name="lessonId" id="download-all-lesson">
                    <input type="hidden" name="sheetId" id="download-all-sheet">
                    <input type="hidden" name="instanceId" id="download-all-instance">
                </fieldset>
            </form>

            <form id="download-form" action="resources/results/results.csv.zip" method="POST">
                <fieldset>
                    <input type="hidden" name="cohortId" id="download-cohort">
                    <input type="hidden" name="studentId" id="download-student">
                    <input type="hidden" name="unitId" id="download-unit">
                    <input type="hidden" name="lessonId" id="download-lesson">
                    <input type="hidden" name="sheetId" id="download-sheet">
                    <input type="hidden" name="instanceId" id="download-instance">
                </fieldset>
            </form>
        </div>

        <a id="download-all" href="#">Download All</a>
        <a id="new-query" href="#">New Query</a>
    </body>
</html>
