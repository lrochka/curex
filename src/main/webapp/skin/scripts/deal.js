 $(function () {
	 function changeFormat(value) {
         d1 = value ? value.split('-') : null;
         return value ? d1[2] + '-' + d1[1] + '-' + d1[0] : "";
     }
     function pqDatePicker(ui) {
         var $this = $(this);
         $this
             .css({ zIndex: 3, position: "relative" })
             .datepicker({
                 yearRange: "-20:+0", //20 years prior to present.
                 changeYear: true,
                 changeMonth: true,
                 dateFormat: "dd-mm-yy",
                 showButtonPanel: true,
                 onClose: function (evt, ui) {
                     $(this).focus();
                 }
             });
         $this.filter(".pq-from").datepicker('option','currentText');
         $this.filter(".pq-to").datepicker('option','currentText');
     }
     
     function pqDatePicker1(ui) {
         var $this = $(this);
         $this
             .css({ zIndex: 3, position: "relative" })
             .datepicker({
                 yearRange: "-20:+0", //20 years prior to present.
                 changeYear: true,
                 changeMonth: true,
                 dateFormat: "dd-mm-yy",
                 showButtonPanel: true,
                 onClose: function (evt, ui) {
                     $(this).focus();
                 }
             });
         $this.filter(".pq-from").datepicker('option','currentText');
         $this.filter(".pq-to").datepicker('option','currentText');
     }
     
	//define common ajax object for addition, update and delete.
     var ajaxObj = {
         dataType: "JSON",
         contentType: "application/json; charset=utf-8",
         beforeSend: function () {
             this.pqGrid("showLoading");
         },
         complete: function () {
             this.pqGrid("hideLoading");
         },
         error: function () {
             this.pqGrid("rollback");
         }
     };
   //to check whether any row is currently being edited.
     function isEditing($grid) {
         var rows = $grid.pqGrid("getRowsByClass", { cls: 'pq-row-edit' });
         if (rows.length > 0) {
             return true;
         }
         return false;
     }
   //called by edit button.
     function editRow(rowIndx, $grid) {

         $grid.pqGrid("addClass", { rowIndx: rowIndx, cls: 'pq-row-edit' });
         $grid.pqGrid("editFirstCellInRow", { rowIndx: rowIndx });

         //change edit button to update button and delete to cancel.
         var $tr = $grid.pqGrid("getRow", { rowIndx: rowIndx }),
         		$btn = $tr.find("button.edit_btn");
         $btn.button("option", { label: "обн.", "icons": { primary: "ui-icon-check"} })
             .unbind("click")
             .click(function (evt) {
                 evt.preventDefault();
                 return update(rowIndx, $grid);
             });
         var $btnc = $grid.find("button.cancel_btn");
         $btnc.button("option", { label: "Отменить", "icons": { primary: "ui-icon-cancel"} })
             .unbind("click")
             .click(function (evt) {
                 $grid.pqGrid("quitEditMode");
                 $grid.pqGrid("removeClass", { rowIndx: rowIndx, cls: 'pq-row-edit' });
                 $grid.pqGrid("refreshRow", { rowIndx: rowIndx });
                 $grid.pqGrid("rollback");
             });
     }
     //called by update button.
     function update(rowIndx, $grid) {
         if (!$grid.pqGrid("saveEditCell")) {
             return false;
         }

         var rowData = $grid.pqGrid("getRowData", { rowIndx: rowIndx });
         var isValid = $grid.pqGrid("isValid", { rowData: rowData }).valid;
         if (!isValid) {
             return false;
         }
         var isDirty = $grid.pqGrid("isDirty");
         if (isDirty) {
             var recIndx = $grid.pqGrid("option", "dataModel.id");

             $grid.pqGrid("removeClass", { rowIndx: rowIndx, cls: 'pq-row-edit' });

             //url = "/pro/products.php?pq_update=1";for PHP
             
             $.ajax($.extend({}, ajaxObj, {
                 context: $grid,
                 url: "/appDealUpdate",
                 data: rowData,
                 success: function () {
                	 $("#deals_IN").pqGrid("refreshDataAndView"); //reload fresh page data from server.
                	 $("#deals_OUT").pqGrid("refreshDataAndView"); //reload fresh page data from server.
                 }
             }));
         }
         else {
        	 $grid.pqGrid("quitEditMode");
             $grid.pqGrid("removeClass", { rowIndx: rowIndx, cls: 'pq-row-edit' });
             $grid.pqGrid("refreshRow", { rowIndx: rowIndx });
         }
     }
        var types = [
                     "Закрыта",
                     "Не закрыта"
        ];
        var direct = [
                     "Покупка",
                     "Продажа"
        ];
        
        var pageMod = { type: "remote", rPP: 20, strRpp: "{0}", rPPOptions: [1, 2, 5, 10, 20, 100] };
        
        var colM1 = [
                    { title: "Дата",editable: false, width: 180, dataIndx: "ddate", dataType: "date",
            		    render: function (ui) {
            		        return changeFormat(ui.cellData);
            		    }},
                    { title: "Филиал",editable: false, width: 50, dataType: "string", dataIndx: "namecompany"
                    	// ,filter: { type: 'textbox', condition: 'begin', listeners: ['change'] }
            		 },
                    { title: "\"Из\"",editable: false, width: 30, dataType: "string", dataIndx: "codeCurFrom", hidden: false
                    	// ,filter: { type: 'textbox', condition: 'begin', listeners: ['change'] } 
            		 },
            		 { title: "",editable: false, width: 50},
            		 { title: "\"В\"",editable: false, width: 30, dataType: "string", dataIndx: "codeCurTo", hidden: false
                     	// ,filter: { type: 'textbox', condition: 'begin', listeners: ['change'] } 
             		 },
                    { title: "Клиент",editable: false, width: 80, dataType: "string", dataIndx: "clientphone"
                    	// ,filter: { type: 'textbox', condition: 'begin', listeners: ['change'] }
            		 },
                    { title: "Статус",editable: true, width: 60, dataType: "string", dataIndx: "typename", editor: {
                            type: "select",
                            options: types
                        },
                        validations: [
                                      { type: 'minLen', value: 1, msg: "Обязательное!" },
                                      { type: function (ui) {
                                          var value = ui.value;
                                          if ($.inArray(ui.value, types) == -1) {
                                              ui.msg = value + " нет в списке";
                                              return false;
                                          }
                                      }
                                      }
                                  ]
                    },
                    { title: "Тип",editable: false, width: 50, dataType: "string", dataIndx: "drctname"
                    },
                    { title: "Сумма \"ИЗ\"",editable: true, width: 50, summary:{type: ["sum"],title:["Сумма: {0}"]}, dataIndx: "sumfrom", dataType: "float", align: "right",
                        validations: [{ type: 'gte', value: 0, msg: "Должно быть >= 0"},
                                      { type: function (ui) {
                                          if (ui.value.toString() == "") {
                                              ui.msg = "Обязательное!";
                                              return false;}
                                      }}],
                        editModel: { keyUpDown: true },
                        render: function (ui) {
                            return parseFloat(ui.cellData).toFixed(2);
                        }
                        },
                    { title: "Курс",editable: true, width: 50, //summary:{type: ["sum"/"cnt"],title:["Сумма: {0}"]}, 
                        	dataIndx: "currate_plan", dataType: "float", align: "right",
                            validations: [{ type: 'gte', value: 0, msg: "Должно быть >= 0"},
                                          { type: function (ui) {
                                              if (ui.value.toString() == "") {
                                                  ui.msg = "Обязательное!";
                                                  return false;}
                                          }}],
                            editModel: { keyUpDown: true },
                            render: function (ui) {
                                return parseFloat(ui.cellData).toFixed(2);
                            }
                            },
                     { title: "Сумма \"В\" ",editable: true, width: 50, //summary:{type: ["sum"],title:["Сумма: {0}"]}, 
                            	dataIndx: "sumto", dataType: "float", align: "right",
                            validations: [{ type: 'gte', value: 0, msg: "Должно быть >= 0"},
                                          { type: function (ui) {
                                              if (ui.value.toString() == "") {
                                                  ui.msg = "Обязательное!";
                                                  return false;}
                                          }}],
                            editModel: { keyUpDown: true },
                            render: function (ui) {
                                return parseFloat(ui.cellData).toFixed(2);
                            }
                            },
                    { title: "Касса сдана",editable: false, width: 50, dataIndx: "status", dataType: "integer", hidden: true},
                    { title: "ID", dataType: "integer", dataIndx: "id", editable: false, hidden: true, width: 80 },
                    { title: "", editable: false, minWidth: 70, sortable: false, hidden:true, render: function (ui) {
                        return "<button type='button' class='edit_btn'>изм.</button>";
                    }},
                    { title: "Примечание",editable: true, width: 150, dataIndx: "comment", dataType: "string", autoFit: true, hidden : true,
                        editor: { type: "contenteditable" },
                        editModel: { keyUpDown: false, saveKey: '' },
                        validations: [{ type: 'minLen', value: 1, msg: "Обязательное!"}]}
        		];
        var dataMdl = {
            location: "remote",
            //sorting: "local",
            recIndx: "id",
            dataType: "JSON",
            contentType:'application/json; charset=utf-8',
            url: "dealListIN",
            getData: function (dataJSON) {                
                return { curPage: dataJSON.curPage, totalRecords: dataJSON.totalRecords, data: dataJSON.data };                
            }
        }
        var newObj = {
        		width: 800, 
                height: 400,
                autoFit: true,
                filterModel: { on: true, mode: "AND", header: true, type: 'remote' },
                wrap: true,
                hwrap: true,
                resizable: true,
                columnBorders: true,
                track: true,
                numberCell: { show: true },
                flexHeight: true,
                flexWidth: false,
                virtualX: true, virtualY: true,
                dataModel: dataMdl,
                showTitle: true,            
                showBottom: true,            
                toolbar: {
                    cls: 'pq-toolbar-export',
                    items: [{
                            type: 'button',
                            label: "Export to Excel",
                            icon: 'ui-icon-document',
                            listeners: [{
                            	"click": function (evt) {
                                	var colM = $("#deals_IN").pqGrid("option", "colModel");
                                	colM[2].hidden = false;
                                	colM[3].hidden = true;
                                	//colM[5].hidden = true;
                                    //colM[6].hidden = false;
                                    //colM[7].hidden = false;
                                    colM[13].hidden = true;
                                	$("#deals_IN").pqGrid("option", "colModel", colM);
                                	$("#deals_IN").pqGrid("exportExcel", { url: "/excel", sheetName: "Сделки (Покупка)" });
                                	colM[2].hidden = true;
                                	colM[3].hidden = false;
                                	//colM[5].hidden = false;
                                    //colM[6].hidden = true;
                                    //colM[7].hidden = true;
                                    colM[13].hidden = false;
                                	$("#deals_IN").pqGrid("option", "colModel", colM);
                                }
                            	}]
                    		},
                    		{
                    		type: 'button',	
                    		style: 'margin:0px 5px;',
                    		label: "Отменить",
                    		cls: "cancel_btn",
                    		icon: "ui-icon-cancel"}
                    	   ]
                },
                pageModel: pageMod,
                colModel: colM1,
                scrollModel: {
                    autoFit: false
                },
                selectionModel: {
                    type: 'cell'
                },
                hoverMode: 'cell',
                editModel: {
                    saveKey: $.ui.keyCode.ENTER
                },
                editor: { type: 'textbox', select: true },
                validation: {
                    icon: 'ui-icon-info'
                },
                quitEditMode: function (evt, ui) {
                    var $grid = $(this);
                    if (evt.keyCode != $.ui.keyCode.ESCAPE) {
                        $grid.pqGrid("saveEditCell");
                    }
                },
                //make rows editable selectively.
                editable: function (ui) {
                    var $grid = $(this);
                    var rowIndx = ui.rowIndx;
                    if ($grid.pqGrid("hasClass", { rowIndx: rowIndx, cls: 'pq-row-edit' }) == true) {
                        return true;
                    }
                    else {
                        return false;
                    }
                },
                editModel: {
                    saveKey: $.ui.keyCode.ENTER,
                    select: false,
                    keyUpDown: false,
                    cellBorderWidth: 0                
                },
                refresh: function () {
                    //debugger;
                    var $grid = $(this);
                    if (!$grid) {
                        return;
                    }
                    //edit button
                    $grid.find("button.edit_btn").button({ icons: { primary: 'ui-icon-pencil'} })
                    .unbind("click")
                    .bind("click", function (evt) {
                        if (isEditing($grid)) {
                            return false;
                        }
                        var $tr = $(this).closest("tr"),
                            rowIndx = $grid.pqGrid("getRowIndx", { $tr: $tr }).rowIndx;
                        editRow(rowIndx, $grid);
                        return false;
                    });
                    //rows which were in edit mode before refresh, put them in edit mode again.
                    var rows = $grid.pqGrid("getRowsByClass", { cls: 'pq-row-edit' });
                    if (rows.length > 0) {
                        var rowIndx = rows[0].rowIndx;
                        editRow(rowIndx, $grid);
                    }
                },
                scrollModel: { horizontal: true },
                /*groupModel:{
                    dataIndx: ["codecurrency"],//,"namecompany","ddate" ],                
                    collapsed: [ false],//, true, true],
                    title: ["<b style='font-weight:bold;'>{0}</b>"], 
                    dir: ["down"]//,"down"]//, icon: ["circle-plus", "circle-triangle", "triangle"]
                },*/ 
                title: "Сделки (Покупка)",
                cellBeforeSave: function (evt, ui) {
                    var $grid = $(this);
                    var isValid = $grid.pqGrid("isValid", ui);
                    if (!isValid.valid) {
                        //evt.preventDefault();                    
                        return false;
                    }
                }
            };
        /*
        newObj.render = function (evt, ui) {
            $summary = $("<div class='pq-grid-summary'  ></div>")
                .prependTo($(".pq-grid-bottom", this));
            //calculateSummary();
        }
        newObj.refresh = function (evt, ui) {
            //var data = [{ddate: "<b>-Сумма-</b>", namecompany: "", codecurrency: "", typename: "", sum: "0.0", status : "1", id: "0", comment: ""}]; //JSON (array of objects)
            var obj = { $cont: $summary }
            $(this).pqGrid("createTable", obj);        
        }*/
        
        $.extend(newObj.colModel[3], {
            render: function (ui) {
            	// if ((ui.rowData["id"]=="0")||(ui.rowData["codecurrency"]==null)) {return "";}
            	return "<img src='/currencies/image.html?alpha="+ui.rowData["codeCurFrom"]+"'/> <img src='/currencies/image.html?alpha="+ui.rowData["codeCurTo"]+"'/>&nbsp;";
            }});

            $.extend(newObj.colModel[0], {
        	filter: { type: 'textbox', condition: "between", init: pqDatePicker, listeners: ['change'] }
        });
        $.extend(newObj.colModel[13], {
            render: function (ui) {
            	if (ui.rowData["status"].toString()=="0") {
            		var colM = $("#deals_IN").pqGrid("option", "colModel");
                	//colM[8].hidden = false;
                	return "<button type='button' class='edit_btn'>изм.</button>";
            		}
            	//colM[8].hidden = true;
            	return "";
            }});
        /*
        //var arrayData = newObj.colModel;
        var summaryData;
        function calculateSummary() {
       	//getter
       	 var revenueTotal = 10;
       	 for (var i = 0; i < arrayData.length; i++) {
       		 var row = arrayData[i];
            revenueTotal += parseFloat(row[5].replace(",", ""));
        }
        revenueTotal = $.paramquery.formatCurrency(revenueTotal);
        summaryData = ["<b>Total</b>", "", "","","",revenueTotal,"1",""];
        }
        var $summary = "";
        newObj.render = function (evt, ui) {
            $summary = $("<div class='pq-grid-summary'  ></div>")
            .prependTo($(".pq-grid-bottom", this));
            calculateSummary();
        }
        //refresh summary whenever a value in any cell changes.
        newObj.cellSave = function (ui) {
            calculateSummary();
            obj.refresh.call(this);
        }
        newObj.refresh = function (evt, ui) {
            //var data = [summaryData, ["Second row for testing", "row", "for", "testing"]]; //2 dimensional array.
            var data = [summaryData]; //2 dimensional array
            var obj = { data: data, $cont: $summary }

            $(this).pqGrid("createTable", obj);
        }
        //*/
       
        //var $pqPager = $("div.pq-pager", $grid).pqPager();

        var grid1 = $("div#deals_IN").pqGrid(newObj);
        
      //bind the select list.
        $("#deals_IN").change(function(evt){            
            var val=$(this).val();            
            var DM = $grid.pqGrid("option", "dataModel");
            if(val=="multiple"){                        
                DM.sortIndx = [DM.sortIndx];
                DM.sortDir = [DM.sortDir];                
            }
            else{
                DM.sortIndx = DM.sortIndx[0];
                DM.sortDir = DM.sortDir[0];                
            }
            $grid.pqGrid("option", "dataMdl", DM);
            $grid.pqGrid("refreshDataAndView");
        });
        
        var dataMdl2 = {
                location: "remote",
                sorting: "local",
                recIndx: "id",             
                dataType: "JSON",
                method: "GET",
                url: "dealListOUT",
                getData: function (dataJSON) {                
                    return { curPage: dataJSON.curPage, totalRecords: dataJSON.totalRecords, data: dataJSON.data };                
                }
            }
            var newObj2 = {
        		width: 800, 
                height: 400,
                filterModel: { on: true, mode: "AND", header: true, type: 'remote' },
                wrap: true,
                hwrap: true,
                resizable: true,
                columnBorders: true,
                track: true,
                numberCell: { show: true },
                flexHeight: true,
                flexWidth: false,
                dataModel: dataMdl2,
                showTitle: true,            
                showBottom: true,               
                    toolbar: {
                        cls: 'pq-toolbar-export',
                        items: [{
                                type: 'button',
                                label: "Export to Excel",
                                icon: 'ui-icon-document',
                                listeners: [{
                                    "click": function (evt) {
                                    	var colM = $("#deals_IN").pqGrid("option", "colModel");
                                        colM1[3].hidden = true;
                                        //colM[5].hidden = true;
                                        colM[13].hidden = true;
                                    	$("#deals_IN").pqGrid("option", "colModel", colM);
                                        $("#deals_OUT").pqGrid("exportExcel", { url: "/excel", sheetName: "Сделки (Продажа)" });
                                        colM[3].hidden = false;
                                       // colM[5].hidden = false;
                                        colM[13].hidden = true;
                                    	$("#deals_OUT").pqGrid("option", "colModel", colM);
                                    }
                                }]},
                        		{
                            		type: 'button',	
                            		style: 'margin:0px 5px;',
                            		label: "Отменить",
                            		cls: "cancel_btn",
                            		icon: "ui-icon-cancel"}
                        ]
                    },
                    pageModel: pageMod,
                    colModel: colM1,
                    scrollModel: {
                        autoFit: false
                    },
                    selectionModel: {
                        type: 'cell'
                    },
                    hoverMode: 'cell',
                    editModel: {
                        saveKey: $.ui.keyCode.ENTER
                    },
                    editor: { type: 'textbox', select: true },
                    validation: {
                        icon: 'ui-icon-info'
                    },
                    quitEditMode: function (evt, ui) {
                        var $grid = $(this);
                        if (evt.keyCode != $.ui.keyCode.ESCAPE) {
                            $grid.pqGrid("saveEditCell");
                        }
                    },
                    //make rows editable selectively.
                    editable: function (ui) {
                        var $grid = $(this);
                        var rowIndx = ui.rowIndx;
                        if ($grid.pqGrid("hasClass", { rowIndx: rowIndx, cls: 'pq-row-edit' }) == true) {
                            return true;
                        }
                        else {
                            return false;
                        }
                    },
                    editModel: {
                        saveKey: $.ui.keyCode.ENTER,
                        select: false,
                        keyUpDown: false,
                        cellBorderWidth: 0                
                    },
                    refresh: function () {
                        //debugger;
                        var $grid = $(this);
                        if (!$grid) {
                            return;
                        }
                        //edit button
                        $grid.find("button.edit_btn").button({ icons: { primary: 'ui-icon-pencil'} })
                        .unbind("click")
                        .bind("click", function (evt) {
                            if (isEditing($grid)) {
                                return false;
                            }
                            var $tr = $(this).closest("tr"),
                                rowIndx = $grid.pqGrid("getRowIndx", { $tr: $tr }).rowIndx;
                            editRow(rowIndx, $grid);
                            return false;
                        });
                        //rows which were in edit mode before refresh, put them in edit mode again.
                        var rows = $grid.pqGrid("getRowsByClass", { cls: 'pq-row-edit' });
                        if (rows.length > 0) {
                            var rowIndx = rows[0].rowIndx;
                            editRow(rowIndx, $grid);
                        }
                    },
                    //scrollModel: { horizontal: false },
                    
                    title: "Сделки (Продажа)",
                    cellBeforeSave: function (evt, ui) {
                        var $grid = $(this);
                        var isValid = $grid.pqGrid("isValid", ui);
                        if (!isValid.valid) {
                            //evt.preventDefault();                    
                            return false;
                        }
                    }
                };
        	$.extend(newObj2.colModel[0], {
        			filter: { type: 'textbox', condition: "between", init: pqDatePicker1, listeners: ['change'] }
        		});
            $.extend(newObj2.colModel[3], {
                render: function (ui) {
                	return "<img src='/currencies/image.html?alpha="+ui.rowData["codeCurFrom"]+"'/> <img src='/currencies/image.html?alpha="+ui.rowData["codeCurTo"]+"'/>&nbsp;";
                }});
            $.extend(newObj2.colModel[13], {
                render: function (ui) {
                	if (ui.rowData["status"].toString()=="1") {
                		return "";
                		}
                	return "<button type='button' class='edit_btn'>изм.</button>";
                }});
            
            //var $pqPager = $("div.pq-pager", $grid).pqPager();

            var grid2 = $("div#deals_OUT").pqGrid(newObj2);
            
          //bind the select list.
            
            $("#deals_OUT").change(function(evt){            
                var val=$(this).val();            
                var DM = $grid.pqGrid("option", "dataModel");
                if(val=="multiple"){                        
                    DM.sortIndx = [DM.sortIndx];
                    DM.sortDir = [DM.sortDir];                
                }
                else{
                    DM.sortIndx = DM.sortIndx[0];
                    DM.sortDir = DM.sortDir[0];                
                }
                $grid.pqGrid("option", "dataModel", DM);
                $grid.pqGrid("refreshDataAndView");
            });
            
    });