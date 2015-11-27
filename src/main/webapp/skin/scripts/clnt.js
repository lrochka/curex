 $(function () {
        var colM = [
            { title: "№", width: 100, dataIndx: "id", hidden : true },            
            { title: "Телефон", width: 130, dataIndx: "phone" },
            { title: "Имя", width: 190, dataIndx: "name" },
            { title: "Филиал", width: 100, dataIndx: "company", render: function (ui) {
                return ui.cellData.code;
            }, },
            { title: "Надежность", width: 100, dataIndx: "clnStats", render: function (ui) {
                return ui.cellData.name;
            },},
            { title: "Кол-во сделок", width: 100, dataIndx: "doneDealCnt"}            
		];
        var dataModel = {
            location: "remote",
            sorting: "local",             
            //sortIndx: ["id", "phone", "company" ],
            //sortDir: ["up", "down", "up"],
            dataType: "JSON",
            method: "GET",
            url: "clientList",
            getData: function (dataJSON) {                
                return { curPage: dataJSON.curPage, totalRecords: dataJSON.totalRecords, data: dataJSON.data };                
            }
        }
        
        //var $pqPager = $("div.pq-pager", $grid).pqPager();

        var grid1 = $("div#grid_local_sorting").pqGrid({ width: 900, height: 400,
            dataModel: dataModel,
            colModel: colM,
            pageModel: { type: "remote", rPP: 20, strRpp: "{0}", rPPOptions: [1, 2, 5, 10, 20, 100] },
            //pager : '#pager',
            editable: false,
            wrap: false, hwrap: false,    
            numberCell:{resizable:true, width:30, title:"#"},
            title: "Клиенты",                                    
            resizable: true          
        });
        
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