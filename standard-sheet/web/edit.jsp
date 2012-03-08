<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editor</title>

        <link href="../isocial-sheets/css/isocial.css" rel="stylesheet" type="text/css" media="screen" />
        <link href="/wonderland-web-front/css/wonderland-theme/jquery-ui.css" rel="stylesheet" type="text/css" media="screen" />
        <link href="../isocial-sheets/css/jquery.treeTable.css" rel="stylesheet" type="text/css" media="screen" />
        <link href="../isocial-sheets/css/jquery.ui.selectmenu.css" rel="stylesheet" type="text/css" media="screen" />

        <script src="/wonderland-web-front/javascript/json2.min.js" type="text/javascript"></script>
        <script src="/wonderland-web-front/javascript/jquery.min.js" type="text/javascript"></script>
        <script src="/wonderland-web-front/javascript/jquery-ui.min.js" type="text/javascript"></script>      
        <script src="../isocial-sheets/scripts/isocial.js" type="text/javascript"></script>
        <script src="../isocial-sheets/scripts/jquery.treeTable.js" type="text/javascript"></script>
        <script src="../isocial-sheets/scripts/jquery.ui.selectmenu.js" type="text/javascript"></script>

        <script src="../isocial-sheets/scripts/underscore.js" type="text/javascript"></script>
        <script src="../isocial-sheets/scripts/handlebars.js" type="text/javascript"></script>
        
        <style>
            textarea {
                resize: none;
            }
            
            div#mainForm h1 {
                text-align: center;
            }
            
            div#mainForm input { 
                padding-bottom: 10px; 
            }
            
            div#sheet-area {
                border: 1px solid black;
                min-height: 480px;
                padding-bottom: 10px;
            }
            
            div#top-questions {
                padding: 10px 15px 10px 10px;
                background-color: lightgray;
            }
            
            div#sheet-area label, div#sheet-area input { 
                display: inline; 
            }
            
            div#sheet-checkboxes {
                padding-top: 10px;
            }
            
            div#sheet-checkboxes label {
                padding-right: 25px;
            }
            
            div#bottom-buttons {
                margin: 10px 0px 10px 0px;
                float: right;
            }
            
            div.question-controls-holder {
                height: 32px;
                padding-right: 5px;
            }
            
            div.question-controls {
                float: right;
                padding-top: 10px;
            }
            
            div.question-controls button {
                padding: 0px;
                width: 44px;
                height: 16px;
                font-size: 10px;
            }
            
            div.question-controls .edit-button span,
            div.question-controls .delete-button span {
                padding: 0px;
                font-size: 10px;
                position: absolute;
                top: 50%;
                margin-top: -8px;
                text-align: center;
                width: 42px;
            }
            
            div.question-content {
                margin: 0px 5px 0px 5px;
                padding: 2px 5px 2px 5px;
                border: 1px dashed black; 
            }
            
            div.question-content .question-field {
                width: 100%;
                color: gray;
            }
            
            div.multiple-question-content ul {
                list-style: none;
            }
            
            div.multiple-question-content ul li.other-li input,
            div.multiple-question-content ul li.other-li label
            {
                position: relative;
                top: -9px;
            }
            
            div#edit-dialog label {
                font-weight: bold;
                margin-top: 10px;
                margin-bottom: 4px;
            }
            
            #edit-question-text {
                width: 100%;
            }
            
            #edit-question-type {
                width: 180px;
            }

            #edit-question-details {
                margin-top: 20px;
            }
            
            #edit-field-lines-label {
                display: inline;
                position: relative;
                top: -8px;
            }
            
            #edit-field-lines {
                display: inline;
            }
            
            #edit-field-instructions {
                width: 100%;
            }
            
            div#edit-dialog .instruction-text {
                font-weight: normal;
                font-style: italic;
            }
            
            div#edit-multiple-div textarea {
                width: 100%;
            }
            
            div#edit-multiple-other-div, div#edit-multiple-allow-multiple-div {
                margin-top: 15px;
            }
            
            div#edit-multiple-other-div input,
            div#edit-multiple-other-div label,
            div#edit-multiple-allow-multiple-div input,
            div#edit-multiple-allow-multiple-div label
            {
                display: inline;
                font-weight: normal;
            }
        </style>      
        <script type="text/javascript">
            var questions;
            
            $(function() {
                Handlebars.registerHelper('string-if', function(str, options) {
                    if(/true/i.test(str)) {
                        return options.fn(this);
                    } else {
                        return options.inverse(this);
                    }
                });
                
                $('.edit-button').button();
                $('#save').focus();
            
                $('#add-question').click(function() {
                    editQuestionDialog();
                });
                
                $.getJSON('edit/${it.unitId}/${it.lessonId}/${it.sheetId}', function(data) {
                    loadData(data);
                })
                
                $('#save').click(function() {
                    $.ajax({
                        type: 'POST',
                        url: 'edit/${it.unitId}/${it.lessonId}/${it.sheetId}',
                        data: formToJSON(),
                        dataType: 'text',
                        contentType: 'application/json',
                        success: function(data) {
                            window.location.href=data;
                        }
                    });
                });
                
                $('#duplicate').click(function() {
                    $.ajax({
                        type: 'POST',
                        url: 'edit/${it.unitId}/${it.lessonId}/${it.sheetId}/duplicate',
                        data: formToJSON(),
                        dataType: 'text',
                        contentType: 'application/json',
                        success: function(data) {
                            window.location.href=data;
                        }
                    });
                });
                
                $('#cancel').click(function() {
                    $.ajax({
                        type: 'GET',
                        url: 'edit/${it.unitId}/${it.lessonId}/${it.sheetId}/cancel',
                        dataType: 'text',
                        success: function(data) {
                            window.location.href=data;
                        }
                    });
                });
            });
            
            function loadData(data) {
                $('#name-field').val(data.name);
                $('#autoOpen').prop("checked", /true/i.test(data.autoOpen));
                $('#visible').prop("checked", /true/i.test(data.visible));
                    
                questions = fixJSON(data.questions);
                renderQuestions();
            }
            
            var templates = {};
            function getTemplate(name) {
                if (!templates[name]) {
                    templates[name] = Handlebars.compile($("#" + name).html());
                }
                
                return templates[name];
            }
            
            function fixJSON(json) {
                _.each(json, function(question) {
                    if (question.properties && question.properties.item) {
                        _.each(question.properties.item, function(item) {
                            question[item.key] = item.value;
                        });
                    }
                    
                    delete question.properties;
                    
                    questionType(question.type).parseJSON(question);
                });
                
                return json;
            }
            
            function questionsToJSON(questions) {
                var NO_CONVERT = ['id', 'text', 'type'];
                
                _.each(questions, function(question) {
                    var properties = null;
                    
                    questionType(question.type).toJSON(question);
                    
                    _.each(question, function(val, key) {                        
                        if (_.indexOf(NO_CONVERT, key) == -1) {
                            if (!properties) {
                                properties = {
                                    item: []
                                }
                            }
                                                        
                            properties.item.push({
                                "key": key,
                                "value": val
                            });
                            
                            delete question[key];
                        }
                    });
                    
                    if (properties) {
                        question.properties = properties;
                    }
                });
                
                return questions;
            }
            
            function formToJSON() {
                var out = {
                    name: $("#name-field").val(),
                    autoOpen: $("#autoOpen").is(":checked"),
                    visible: $("#visible").is(":checked"),
                    questions: questionsToJSON(questions)
                };
                
                return JSON.stringify(out);
            }
            
            function renderQuestions() {
                // sort questions
                questions = _.sortBy(questions, function(question) {
                    return question.id;
                });
                
                var template = getTemplate('tmpl-question');
                $('#sheet-questions').empty();
                
                _.each(questions, function(question) {
                    var type = questionType(question.type);
                    
                    var questionTemplate = type.renderTemplate();
                    var questionJSON = type.prerender(question);
                    
                    Handlebars.registerPartial("question", questionTemplate);
                    var rendered = template(questionJSON);  
                    var appended = $(rendered).appendTo('#sheet-questions');
                    
                    appended.find('.edit-button').button().click(function() {
                        editQuestionDialog(question);
                    });
                    appended.find('.delete-button').button().click(function() {
                        removeQuestion(question.id);
                    });
                    appended.find('.up-button').button({
                        icons: {
                            primary: "ui-icon-triangle-1-n"
                        },
                        text: false
                    }).click(function() {
                        moveDown(question.id);
                    });
                    appended.find('.down-button').button({
                        icons: {
                            primary: "ui-icon-triangle-1-s"
                        },
                        text: false
                    }).click(function() {
                        moveUp(question.id); 
                    });
                });
            }
            
            function editQuestionDialog(question) {
                var question = question || {};
                var template = getTemplate("tmpl-edit-dialog");
                var rendered = template(question);
                var appended = $(rendered).appendTo('body');
                
                appended.find('#edit-question-type option[value="' + 
                              question.type + '"]').attr("selected", "true");
                
                appended.find("#edit-question-type").selectmenu({
                    select: function(event, options) {
                        loadDetails({type: options.value}, $("#edit-question-details"));
                    }
                });
                
                if (question.type) {
                    loadDetails(question, $("#edit-question-details"));
                }
                
                var buttons = [{
                    text: "Cancel",
                    click: function() {
                        $("#edit-dialog").dialog("close");
                    }
                }];
                if (!question.type) {
                    buttons.push({
                        text: "Save & Add Question", 
                        click: function() {
                            addQuestion(dialogToQuestion(question));
                            $("#edit-dialog").dialog("close");
                            editQuestionDialog();
                        }
                    });
                }
                buttons.push({
                    text: "Save",
                    click: function() {
                        addQuestion(dialogToQuestion(question));
                        $("#edit-dialog").dialog("close");
                    }
                });
                
                appended.dialog({
                    title: question.type?"Edit Question":"Add Question",
                    width: 480,
                    height: 640,
                    buttons: buttons,
                    close: function() {
                        $("#edit-dialog").remove();
                    }
                });
            }
            
            function loadDetails(question, div) {
                div.empty();

                questionType(question.type).loadDetails(question, div);
            }
            
            function dialogToQuestion(question) {
                var result = {
                    id: question?question.id:null,
                    type: $("#edit-question-type").val(),
                    text: $("#edit-question-text").val()
                };
                
                return questionType(result.type).toQuestion(result);
            }
            
            function addQuestion(question) {
                if (question.id) {
                    questions[question.id] = question;
                } else {
                    // existing question
                    question.id = _.size(questions);
                    questions.push(question);
                }
                
                renderQuestions();
            }
            
            function removeQuestion(index) {
                questions.splice(index, 1);
                _.each(questions, function(question) {
                    if (question.id > index) {
                        question.id = question.id - 1;
                    }
                });
                
                renderQuestions();
            }
            
            function moveUp(index) {
                if (index == _.size(questions) - 1) {
                    return;
                }
                
                questions[index].id += 1;
                questions[index + 1].id -=1;
                renderQuestions();
            } 
            
            function moveDown(index) {
                if (index == 0) {
                    return;
                }
                
                questions[index].id -= 1;
                questions[index - 1].id += 1;
                renderQuestions();
            }
            
            var BaseQuestionType = {
                renderTemplate: function() {
                    return getTemplate("tmpl-" + this.type + "-question");
                },
                
                editTemplate: function() {
                    return getTemplate("tmpl-edit-details-" + this.type); 
                },
                
                parseJSON: function(json) {
                    return json;
                },
                
                toJSON: function(json) {
                    return json;
                },
                
                prerender: function(question) {
                    return question;
                },
                
                loadDetails: function(question, div) {
                    var template = this.editTemplate();
                    
                    var question = question || {};
                    div.html(template(question));
                },
                
                toQuestion: function(result) {
                    return result;
                },
                
                extend: function(obj) {
                    _.defaults(obj, BaseQuestionType);
                    _.bindAll(obj);
                    return obj;
                }
            };
            
            var TextQuestionType = BaseQuestionType.extend({
                type: "text",
                
                loadDetails: function(question, div) {
                    // do nothing
                }
            });
            
            var FieldQuestionType = BaseQuestionType.extend({
                type: "field",
                
                loadDetails: function(question, div) {
                    var template = this.editTemplate();
                
                    var question = question || {lines: '1'};
                    div.html(template(question));
                },
                
                toQuestion: function(result) {
                    result['lines'] = $("#edit-field-lines").val();
                    result['instructions'] = $("#edit-field-instructions").val();
                    return result;
                }
            });
            
            var MultipleQuestionType = BaseQuestionType.extend({
                type: "multiple",
                
                prerender: function(json) {
                    var choices = json['choices'];
                    
                    if (choices) {
                        json = _.clone(json);
                        json['choices'] = _.filter(choices.split('\n'), function(data) {
                            return data && data.length > 0;
                        });
                    }
                    
                    if (json['multiple']) {
                        json['multiple'] = /true/i.test(json['multiple']);
                    }
                    
                    if (json['other']) {
                        json['other'] = /true/i.test(json['other']);
                    }
                    
                    return json;
                },
                
                toQuestion: function(result) {
                    result['choices'] = $("#edit-multiple-choices").val();
                    result['other'] = $("#edit-multiple-other").is(":checked");
                    result['otherText'] = $("#edit-multiple-other-text").val();
                    result['multiple'] = $("#edit-multiple-allow-multiple").is(":checked");
                    return result;
                }
            });
            
            var QuestionType = {
                text: TextQuestionType,
                field: FieldQuestionType,
                multiple: MultipleQuestionType
            };
            
            function questionType(type) {
                return QuestionType[type];
            }
            
        </script>
    </head>
    <body>
        <div id="mainForm" class="ui-widget-container ui-widget ui-corner-all">
            <h1>Design Sheet</h1>
            <div id="sheet-area">
                <div id="top-questions">
                    <label for="name-field">Sheet Name:</label>
                    <input id="name-field" class="ui-widget-content ui-corner-all" size="50" type="text" name="name" value=""/>
                    <input id="add-question" class="edit-button" value="+ Add Question" type="submit"/>
                
                    <div id="sheet-checkboxes">
                        <input class="ui-widget-content ui-corner-all" type="checkbox" id="autoOpen" value="true"/>
                        <label for="autoOpen">Visible on startup</label>
                    
                        <input class="ui-widget-content ui-corner-all" type="checkbox" id="visible" value="true"/>
                        <label for="visible">Visible in Windows menu</label>
                    </div>
                </div>
                
                <div id="sheet-questions">    
                </div>
            </div>    

            <div id="bottom-buttons">
                <input class="edit-button" id="cancel" name="action" value="Cancel" type="submit"/>
                <input class="edit-button" id="duplicate" name="action" value="Duplicate" type="submit"/>
                <input class="edit-button" id="save" name="action" value="Save" type="submit"/>
            </div>
        </div>
        
        <script id="tmpl-question" type="text/x-handlebars-tmpl">
            <div class="question">
                <div class="question-controls-holder">
                    <div class="question-controls">
                        <button class="edit-button">Edit</button>
                        <button class="delete-button">Delete</button>
                        <button class="up-button"></button>
                        <button class="down-button"></button>
                    </div>
                </div>
                {{> question}}
            </div>
        </script> 
        
        <script id="tmpl-text-question" type="text/x-handlebars-tmpl">
            <div class="question-content">
                {{ text }}
            </div>
        </script>
        
        <script id="tmpl-field-question" type="text/x-handlebars-tmpl">
            <div class="question-content">
                <label for="question-{{id}}-field">{{text}}</label>
                <textarea id="question-{{id}}-field" class="question-field ui-widget-content ui-corner-all" readonly="true" rows="{{lines}}">{{instructions}}</textarea>
            </div>
        </script>
        
        <script id="tmpl-multiple-question" type="text/x-handlebars-tmpl">
            <div class="question-content multiple-question-content">
                <label for="question-{{id}}-multiple">{{text}}</label>
                <form id="question-{{id}}-multiple">
                    <ul>
                    {{#choices}}
                        <li>
                        <input type={{#string-if ../multiple}}"radio"{{else}}"checkbox"{{/string-if}} name="question-{{../id}}-radio"/>
                        <label>{{this}}</label>
                    {{/choices}}
                    {{#if other}}
                        <li class="other-li">
                        <input type={{#string-if multiple}}"radio"{{else}}"checkbox"{{/string-if}}" name="question-{{id}}-radio"/>
                        <label>Other {{#if otherText}}({{otherText}}){{/if}}:</label>
                        <textarea rows="1" class="ui-widget ui-corner-all" readonly="true"/>
                    {{/if}}
                    </ul>
                </form>
            </div>
        </script>
        
        <script id="tmpl-edit-dialog" type="text/x-handlebars-tmpl">
            <div id="edit-dialog">
                <label for="edit-question-text">Question Text:</label>
                <textarea rows="3" id="edit-question-text" class="ui-widget-content ui-corner-all">{{text}}</textarea>
                
                <label for="edit-question-type">Question Type:</label>
                <select id="edit-question-type">
                    <option value="text">Text Only</option>
                    <option value="field">Short Answer</option>
                    <option value="multiple">Multiple Choice</option>
                </select>
                
                <div id="edit-question-details">
                </div>
            </div>
        </script>
        
        <script id="tmpl-edit-details-field" type="text/x-handlebars-tmpl">
            <label id="edit-field-lines-label" for="edit-field-lines">Number of lines:</label>
            <textarea rows="1" cols="4" id="edit-field-lines" class="ui-widget-content ui-corner-all">{{lines}}</textarea>
            
            <label for="edit-field-instructions" class="instruction-text">If you want any instructions to appear in the text box, enter them below:</label>
            <textarea rows="1" id="edit-field-instructions" class="ui-widget-content ui-corner-all">{{instructions}}</textarea>
        </script>
        
        <script id="tmpl-edit-details-multiple" type="text/x-handlebars-tmpl">
            <div id="edit-multiple-div">
                <div id="edit-multiple-choices-div">
                    <label for="edit-multiple-choices" class="instruction-text">Put each choice on a separate line:</label>
                    <textarea rows="5" id="edit-multiple-choices" class="ui-widget-content ui-corner-all">{{#choices}}{{.}}&#10;{{/choices}}</textarea>
                </div>

                <div id="edit-multiple-other-div">
                    <input type="checkbox" id="edit-multiple-other" {{#string-if other}}checked="true"{{/string-if}}/>
                    <label for="edit-multiple-other">Add "Other" as a choice using this text:</label>
                    <textarea rows="1" id="edit-multiple-other-text" class="ui-widget-content ui-corner-all">{{otherText}}</textarea>
                </div>

                <div id="edit-multiple-allow-multiple-div">
                    <input type="checkbox" id="edit-multiple-allow-multiple" {{#string-if multiple}}checked="true"{{/string-if}}/>
                    <label for="edit-multiple-allow-multiple">Allow multiple answers</label>
                </div>
            </div>
        </script>
        
    </body>
</html>
