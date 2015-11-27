 $(function () {
        var colM = [
                        
            { title: "Дата", width: 130, dataIndx: "date" },
            { title: "Филиал", width: 100, dataIndx: "namecompany"},
            /*
             { title: "Филиал", width: 100, dataIndx: "namecompany", render: function (ui) {
                return ui.cellData.code;
            }, },
            { title: "Валюта", width: 100, dataIndx: "codecurrency", render: function (ui) {
                return ui.cellData.alpha;
            },},*/
            { title: "Валюта", width: 100, dataIndx: "codecurrency" },
            { title: "Флаг", width: 16, copy: false},
            { title: "Сумма", width: 100, dataIndx: "sum"}, 
            { title: "Сумма/вход", width: 100, dataIndx: "sumIn"},
            { title: "Сумма/выход", width: 100, dataIndx: "sumOut"}, 
            { title: "Сумма/прогноз", width: 100, dataIndx: "sumFree"}, 
		];
        var dataModel = {
            location: "remote",
            sorting: "local",             
            dataType: "JSON",
            method: "GET",
            url: "cashList",
            getData: function (dataJSON) {                
                return { curPage: dataJSON.curPage, totalRecords: dataJSON.totalRecords, data: dataJSON.data };                
            }
        }
        var newObj = {
        		showTitle: true,
                flexHeight: true,
                flexWidth: false,
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
                                    $("#grid_local_sorting").pqGrid("exportCsv", { url: "/excel", sheetName: "cash" });
                                }
                            }]
                    }]
                },
                pageModel: { type: "remote", rPP: 20, strRpp: "{0}", rPPOptions: [1, 2, 5, 10, 20, 100] },
                colModel: colM,
                selectionModel: { mode: 'single' },
                editable: false,
                scrollModel: { horizontal: true },
                width: 800, 
                height: 400,
                resizable: true,
                title: "Касса"
            };
        
        $.extend(newObj.colModel[3], {
            render: function (ui) {
                return "<img src='/currencies/image.html?alpha="+ui.rowData["codecurrency"]+"'/>&nbsp;";
            }});
        
        //var $pqPager = $("div.pq-pager", $grid).pqPager();

        var grid1 = $("div#grid_local_sorting").pqGrid(newObj);
        
      //bind the select list.
        $("#grid_local_sorting").change(function(evt){            
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