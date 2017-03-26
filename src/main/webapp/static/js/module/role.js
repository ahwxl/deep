var mygridtab = $('#transactions').dataTable({
            	"bProcessing": true,
                "bServerSide": true,
                "bFilter":true,
                "bLengthChange":false,
                "sAjaxSource": "/deep/sysmng/roleList",
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
                  { "sTitle": "名称","mData":"roleName","bSortable": false},
                  { "sTitle": "状态","mData":"status","bSortable": false,"sWidth":50 },
                  { "sTitle": "创建日期","mData":"gmtCreate","bSortable": false,"sWidth":130 },
                  { "sTitle": "修改日期","mData":"gmtModify","bSortable": false,"sWidth":130 },
                  { "sTitle": "操作","mData":"roleId","bSortable": false }
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
                        'aTargets': [4],
                        fnRender: function (setobj, data) {
                        	var delhtml = "<a class='mini purple' id='{0}' data-toggle='delete' ><i class='icon-trash'></i> {1}</a>&nbsp;<a class='mini purple' id='{0}' data-toggle='edit' ><i class='icon-edit'></i> {2}</a>".format(setobj.aData['roleId'],"删除","修改");
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
                    $('#stack1').modal({
                    	confirm:function(formvalue){
                    		var param = $('#myform').serialize();
                    		$.post("/deep/sysmng/addRole",
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
            		
            		$.post("/deep/sysmng/delRole",
            				"roleId="+id,
            			function(data){
            			   mygridtab.fnDraw();
            			   alert(data);
            		    }
            		);
            		
            	});
            });