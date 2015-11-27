$(function() {

	$.extend($.jgrid.defaults, {
				datatype: 'json',
				jsonReader : {
					repeatitems:false,
					total: function(result) {
						//Total number of pages
						return Math.ceil(result.total / result.max);
					},
					records: function(result) {
						//Total number of records
						return result.total;
					}
				},
				prmNames: {
					page: "page.page",
					rows: "page.size",
					sort: "page.sort",
					order: "page.sort.dir"
				},
				sortname: 'title',
				sortorder: 'asc',
				height: 'auto',
				viewrecords: true,
				rowList: [10, 20, 50, 100],
				altRows: true,
				loadError: function(xhr, status, error) {
					alert(error);
				}
			});

	var URL = 'clientList';
	var options = {
		url: URL,
		colModel:[
			{
				name:'id',
				label: 'ID',
				index: 'id',
				formatter:'integer',
				width: 40,
				editable: true,
				editoptions: {disabled: true, size:5}
			},
			{
				name:'company',
				label: 'Company',
				index: 'company',
				width: 300,
				editable: true,
				editrules: {required: true}
			},
			{
				name:'author',
				label: 'Author',
				index: 'author',
				width: 200,
				editable: true,
				editrules: {required: true}
			},
			{
				name:'phone',
				label: 'Phone',
				index: 'phone',
				width: 200,
				editable: true,
				editrules: {edithidden:true},
			},
			{
				name:'addContact',
				label: 'addContact',
				index: 'addContact',
				hidden: true,
				editable: true,
				edittype: 'textarea',
				editrules: {edithidden:true}
			}
		],
		caption: "clients",
		pager : '#pager',
		height: 'auto',
		ondblClickRow: function(id) {
			jQuery(this).jqGrid('editGridRow', id, editOptions);
		}
	};

	$("#grid")
			.jqGrid(options)
			.navGrid('#pager',
			{}, //options
			editOptions,
			addOptions,
			delOptions,
			{} // search options
	);

});
