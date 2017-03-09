var mygridtab = $('#transactions').dataTable({
            	"bProcessing": true,
                "bServerSide": true,
                "bFilter":true,
                "bLengthChange":false,
                "sAjaxSource": "/deep/stock/stockWareHouseList",
                "fnServerParams":{'bbbb':''},
                "oSearch": {"sSearch":"初始化1","abc":"123"},
           		"bStateSave": true,
           		"oFeatures":{'sDom':''},
       		    "fnStateLoadParams": function (oSettings, oData) {
       		    	$("#mySubmit").bind("click", function(){
       		    		var oSettings = mygridtab.fnSettings();
       		    		oSettings.serverparam=$('#searchform').serializeObject();
       		    		oSettings.sDom='';
       		    		mygridtab.fnFilter('11');
       		    	});
       		    },
           		"aoSearchCols": [
           		     		           null,
           		     		           { "sSearchaa": "My filter" },
           		     		           null,
           		     		           { "groupId": "^[0-9]", "bEscapeRegex": false }
           		     		  ],
                "aoColumns": [
                  { "sTitle": "编号","mData":"stockId","bSortable": false,"sWidth":"60",height:"20"},
                  { "sTitle": "名称","mData":"stockName","bSortable": false,"sWidth":70 },
                  { "sTitle": "数量","mData":"amount","bSortable": false,"sWidth":70 },
                  { "sTitle": "价格","mData":"todayPrice","bSortable": false,"sWidth":70 },
                  { "sTitle": "期望价格","mData":"exceptPrice","bSortable": false,"sWidth":"60" },
                  { "sTitle": "期望数量","mData":"exceptAmount","bSortable": false,"sWidth":"60" },
                  { "sTitle": "创建日期","mData":"gmtCreate","bSortable": false,"sWidth":"150" },
                  { "sTitle": "操作","mData":"gmtModify","bSortable": false }
                ],
                "aLengthMenu": [
                    [5, 15, 20, -1],
                    [5, 15, 20, "All"] // change per page values here
                ],
                // set the initial value
                "iDisplayLength": 10,
                //"sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
                "sPaginationType": "bootstrap",
                "oLanguage": {
                    "sLengthMenu": "_MENU_ records per page",
                    "oPaginate": {
                        "sPrevious": "Prev",
                        "sNext": "Next"
                    }
                },
                "aoColumnDefs": [{
                        'bSortable': false,
                        'aTargets': [7],
                        fnRender: function (setobj, data) {
                        	var delhtml = "<a class='mini purple' id='{0}' data-toggle='delete' ><i class='icon-trash'>{1}</i></a>&nbsp;<a class='' id='{0}' data-toggle='modify' ><i class='icon-edit'>{2}</i></a>".format(setobj.aData['stockId'],"删除","修改");
                        	return delhtml;
                        }
                    }
                ]
            });
            
            $(document).ready(function () {
                //var table = $('#sample_1').DataTable();
            	//动态创建的元素 通过绑定到 document
            	$(document).off('click.modal').on('click.modal.data-api', '[1data-toggle^="modal"]', function ( e ) {
                	var procDefId = $(this).attr('procDefId');
                	//alert(procDefId);
                    $('#stack1').modal({
                    	confirm:function(formvalue){
                    		var param = $('#myform').serialize();
                    		$.post("/deep/stock/addStock",
                    				param,
                    			function(data){
                    			   mygridtab.fnDraw();
                    			   alert(data);
                    		    }
                    		);
                    	}
                    	
                    });
                });
            	
            	$(document).off('click.modal2').on('click.modal2.data-api', '[data-toggle="delete"]', function ( e ) {
            		var id = $(this).attr('id');
            		
            		$.post("/deep/stock/delStock",
            				"stockId="+id,
            			function(data){
            			   mygridtab.fnDraw();
            			   alert(data);
            		    }
            		);
            		
            	});
            });