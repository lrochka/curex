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
                 url: "/appDocUpdate",
                 data: rowData,
                 success: function () {
                	 $("#grid_IN").pqGrid("refreshDataAndView"); //reload fresh page data from server.
                	 $("#grid_OUT").pqGrid("refreshDataAndView"); //reload fresh page data from server.
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
                     "Приход",
                     "Расход"
        ];
        
        var pageMod = { type: "remote", rPP: 20, strRpp: "{0}", rPPOptions: [1, 2, 5, 10, 20, 100] };
        
        var colM = [
                    { title: "Дата",editable: false, width: 180, dataIndx: "ddate", dataType: "date",
            		    render: function (ui) {
            		        return changeFormat(ui.cellData);
            		    }},
                    { title: "Филиал",editable: false, width: 50, dataType: "string", dataIndx: "namecompany"
                    	// ,filter: { type: 'textbox', condition: 'begin', listeners: ['change'] }
            		 },
                    { title: "Валюта",editable: false, width: 50, dataType: "string", dataIndx: "codecurrency", hidden: true
                    	// ,filter: { type: 'textbox', condition: 'begin', listeners: ['change'] } 
            		 },
                    { title: "Флаг",editable: false, width: 40},
                    { title: "Тип",editable: true, width: 50, dataType: "string", dataIndx: "typename", editor: {
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
                    { title: "Сумма",editable: true, width: 80, summary:{type: ["sum"],title:["Сумма: {0}"]}, dataIndx: "sum", dataType: "float", align: "right",
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
                    { title: "Product ID", dataType: "integer", dataIndx: "id", editable: false, hidden: true, width: 80 },
                    { title: "", editable: false, minWidth: 70, sortable: false, hidden:false, render: function (ui) {
                        return "<button type='button' class='edit_btn'>изм.</button>";
                    }},
                    { title: "Примечание",editable: true, width: 150, dataIndx: "comment", dataType: "string", autoFit: true,
                        editor: { type: "contenteditable" },
                        editModel: { keyUpDown: false, saveKey: '' },
                        validations: [{ type: 'minLen', value: 1, msg: "Обязательное!"}]},
                    { title: "Тип2",editable: false, width: 50, dataIndx: "type", dataType: "string", hidden: true}
        		];
        var dataModel = {
            location: "remote",
            sorting: "local",
            recIndx: "id",
            dataType: "JSON",
            contentType:'application/json; charset=utf-8',
            url: "documentListIN",
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
                dataModel: dataModel,
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
                                	var colM = $("#grid_IN").pqGrid("option", "colModel");
                                	colM[2].hidden = false;
                                	colM[3].hidden = true;
                                    colM[6].hidden = false;
                                    colM[7].hidden = false;
                                    colM[8].hidden = true;
                                	$("#grid_IN").pqGrid("option", "colModel", colM);
                                	$("#grid_IN").pqGrid("exportExcel", { url: "/excel", sheetName: "Документы (Приход)" });
                                	colM[2].hidden = true;
                                	colM[3].hidden = false;
                                    colM[6].hidden = true;
                                    colM[7].hidden = true;
                                    colM[8].hidden = false;
                                	$("#grid_IN").pqGrid("option", "colModel", colM);
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
                colModel: colM,
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
                groupModel:{
                    dataIndx: ["codecurrency"],//,"namecompany","ddate" ],                
                    collapsed: [ false],//, true, true],
                    title: ["<b style='font-weight:bold;'>{0}</b>"], 
                    dir: ["down"]//,"down"]//, icon: ["circle-plus", "circle-triangle", "triangle"]
                }, 
                title: "Документы (Приход)",
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
            	return "<img src='/currencies/image.html?alpha="+ui.rowData["codecurrency"]+"'/>&nbsp;";
            	
            }});
        $.extend(newObj.colModel[0], {
        	filter: { type: 'textbox', condition: "between", init: pqDatePicker, listeners: ['change'] }
        });
        $.extend(newObj.colModel[8], {
            render: function (ui) {
            	if (ui.rowData["status"].toString()=="0") {
            		var colM = $("#grid_IN").pqGrid("option", "colModel");
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

        var grid1 = $("div#grid_IN").pqGrid(newObj);
        
      //bind the select list.
        $("#grid_OUT").change(function(evt){            
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
        
        var dataModel2 = {
                location: "remote",
                sorting: "local",
                recIndx: "id",             
                dataType: "JSON",
                method: "GET",
                url: "documentListOUT",
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
                dataModel: dataModel2,
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
                                    	var colM = $("#grid_IN").pqGrid("option", "colModel");
                                        colM[3].hidden = true;
                                        colM[7].hidden = false;
                                    	$("#grid_IN").pqGrid("option", "colModel", colM);
                                        $("#grid_OUT").pqGrid("exportExcel", { url: "/excel", sheetName: "Документы (расход)" });
                                        colM[3].hidden = false;
                                        colM[7].hidden = true;
                                    	$("#grid_OUT").pqGrid("option", "colModel", colM);
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
                    colModel: colM,
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
                    
                    title: "Документы (расход)",
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
                    return "<img src='/currencies/image.html?alpha="+ui.rowData["codecurrency"]+"'/>&nbsp;";
                }});
            
            $.extend(newObj.colModel[8], {
                render: function (ui) {
                	if (ui.rowData["status"].toString()=="1") {
                		return "";
                		}
                	return "<button type='button' class='edit_btn'>изм.</button>";
                }});
            
            //var $pqPager = $("div.pq-pager", $grid).pqPager();

            var grid2 = $("div#grid_OUT").pqGrid(newObj2);
            
          //bind the select list.
            $("#grid_OUT").change(function(evt){            
                var val=$(this).val();            
                var DM = $grid.pqGrid("option", "dataModel2");
                if(val=="multiple"){                        
                    DM.sortIndx = [DM.sortIndx];
                    DM.sortDir = [DM.sortDir];                
                }
                else{
                    DM.sortIndx = DM.sortIndx[0];
                    DM.sortDir = DM.sortDir[0];                
                }
                $grid.pqGrid("option", "dataModel2", DM);
                $grid.pqGrid("refreshDataAndView");
            });
            
    });