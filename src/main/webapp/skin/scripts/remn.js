 $(function () {
        var colM = [
                        
            { title: "Дата", width: 80, dataIndx: "date" },
            { title: "Филиал", width: 100, dataIndx: "namecompany"},
            { title: "Валюта", width: 50, dataIndx: "codecurrency" },
            { title: "Флаг", width: 25, copy: false},
            { title: "Сумма", width: 50, dataIndx: "sum"} 
           // { title: "Сумма/вход", width: 50, dataIndx: "sumIn"},
           // { title: "Сумма/выход", width: 50, dataIndx: "sumOut"}, 
           // { title: "Сумма/прогноз", width: 50, dataIndx: "sumFree"}, 
		];
        
        var dataModel = {
            location: "remote",
            sorting: "local",             
            dataType: "JSON",
            method: "GET",
            url: "remainsListBGN",
            getData: function (dataJSON) {                
                return { curPage: dataJSON.curPage, totalRecords: dataJSON.totalRecords, data: dataJSON.data };                
            }
        }
        var newObj = {
                flexHeight: true,
                flexWidth: false,
                dataModel: dataModel,
                resizable: true,
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
                                    $("#grid_local_sort_BGN").pqGrid("exportExcel", { url: "/excel", sheetName: "Остатки на начало" });
                                }
                            }]
                    }]
                },
                pageModel: { type: "remote", rPP: 20, strRpp: "{0}", rPPOptions: [1, 2, 5, 10, 20, 100] },
                colModel: colM,
                selectionModel: { mode: 'single' },
                editable: false,
                //scrollModel: { horizontal: false },
                width: 800, 
                height: 400,
                title: "Остатки на начало"
            };
        
        $.extend(newObj.colModel[3], {
            render: function (ui) {
                return "<img src='/currencies/image.html?alpha="+ui.rowData["codecurrency"]+"'/>&nbsp;";
            }});
        
        //var $pqPager = $("div.pq-pager", $grid).pqPager();

        var grid1 = $("div#grid_local_sort_BGN").pqGrid(newObj);
        
      //bind the select list.
        $("#grid_local_sort_BGN").change(function(evt){            
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
                dataType: "JSON",
                method: "GET",
                url: "remainsListEND",
                getData: function (dataJSON) {                
                    return { curPage: dataJSON.curPage, totalRecords: dataJSON.totalRecords, data: dataJSON.data };                
                }
            }
            var newObj2 = {
                    flexHeight: true,
                    flexWidth: false,
                    dataModel: dataModel2,
                    resizable: true,
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
                                        $("#grid_local_sort_END").pqGrid("exportExcel", { url: "/excel", sheetName: "Остатки на конец" });
                                    }
                                }]
                        }]
                    },
                    pageModel: { type: "remote", rPP: 20, strRpp: "{0}", rPPOptions: [1, 2, 5, 10, 20, 100] },
                    colModel: colM,
                    selectionModel: { mode: 'single' },
                    editable: false,
                    //scrollModel: { horizontal: false },
                    width: 800, 
                    height: 400,
                    title: "Остатки на конец"
                };
            
            $.extend(newObj2.colModel[3], {
                render: function (ui) {
                    return "<img src='/currencies/image.html?alpha="+ui.rowData["codecurrency"]+"'/>&nbsp;";
                }});
            
            //var $pqPager = $("div.pq-pager", $grid).pqPager();

            var grid2 = $("div#grid_local_sort_END").pqGrid(newObj2);
            
          //bind the select list.
            $("#grid_local_sort_END").change(function(evt){            
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