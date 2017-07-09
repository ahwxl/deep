            $(document).ready(function () {
            	var mygridtab = $('#transactions').dataTable({
                	"bProcessing": true,
                    "bServerSide": true,
                    "bFilter":true,
                    "bLengthChange":false,
                    "sAjaxSource": cxt+"/form/formList",
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
                      { "bSortable": false,"sWidth":20,"sSortDataType": "dom-checkbox","sDefaultContent":"<input type=\"checkbox\" class=\"checkboxes\" value=\"1\" />" },
                      { "sTitle": "编号","mData":"formId","bSortable": false,"sWidth":100},
                      { "sTitle": "名称","mData":"formName","bSortable": false,"sWidth":100},
                      { "sTitle": "描述","mData":"formDesc","bSortable": false,"sWidth":100 },
                      { "sTitle": "创建日期","mData":"gmtCreate","bSortable": false,"sWidth":150 },
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
                        'bSortable': false,"sSortDataType": "dom-checkbox",
                        'aTargets': [0]
                    },{
                            'bSortable': false,
                            'aTargets': [5],
                            fnRender: function (setobj, data) {
                            	var delhtml = "<a class='mini purple' id='{0}' data-toggle='delete' ><i class='icon-trash'></i> {1}</a>&nbsp;<a class='mini purple' id='{0}' data-toggle='edit' ><i class='icon-edit'></i> {2}</a>".format(setobj.aData['formId'],"删除","修改");
                            	return delhtml;
                            }
                        }
                    ]
                });
            	
                //var table = $('#sample_1').DataTable();
            	//动态创建的元素 通过绑定到 document
            	$(document).off('click.modal').on('click.modal.data-api', '[1data-toggle^="modal"]', function ( e ) {
                	var procDefId = $(this).attr('procDefId');
                	$('#myform')[0].reset();
                	
                    $('#stack1').modal({
                    	confirm:function(formvalue){
                    		var param = $('#myform').serialize();
                    		$.post("/deep/sysmng/addUser",
                    				param,
                    			function(data){
                    			   mygridtab.fnDraw();
                    			   alert(data);
                    		    }
                    		);
                    	}
                    });
                });
            	//删除资源
            	$(document).off('click.modal2').on('click.modal2.data-api', '[data-toggle="delete"]', function ( e ) {
            		var id = $(this).attr('id');
            		if(confirm('确认删除')){
            			$.post(cxt+"/form/delForm",
                				"formId="+id,
                			function(data){
                			   mygridtab.fnDraw();
                			   //alert(data);
                		    }
                		);
            		}
            	});
            	//修改资源
            	$(document).off('click.modal3').on('click.modal3.data-api', '[data-toggle="edit"]', function ( e ) {
            		var id = $(this).attr('id');
            		window.location.href = cxt+"/form/updateForm?formId="+id;
            	});
            	
            	//table checkbox全选
            	jQuery('#transactions .group-checkable').change(function () {
                    var set = jQuery(this).attr("data-set");
                    var checked = jQuery(this).is(":checked");
                    jQuery(set).each(function () {
                        if (checked) {
                            $(this).attr("checked", true);
                        } else {
                            $(this).attr("checked", false);
                        }
                    });
                    jQuery.uniform.update(set);
                });
            	
            	$(".page-sidebar-menu li[name='流程管理']").addClass("active");
            	$(".page-sidebar-menu li[name='表单管理']").addClass("active");
            });