            $(document).ready(function () {
            	var mygridtab = $('#transactions').dataTable({
                	"bProcessing": true,
                    "bServerSide": true,
                    "bFilter":true,
                    "bLengthChange":false,
                    "sAjaxSource": "/deep/sysmng/organizationList",
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
                      { "sTitle": "机构名称","mData":"organizationName","bSortable": false},
                      { "sTitle": "创建日期","mData":"gmtCreate","bSortable": false,"sWidth":130 },
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
                            'aTargets': [3],
                            fnRender: function (setobj, data) {
                            	var delhtml = "<a class='mini purple' id='{0}' data-toggle='delete' ><i class='icon-trash'></i> {1}</a>&nbsp;<a class='mini purple' id='{0}' data-toggle='edit' ><i class='icon-edit'></i> {2}</a>".format(setobj.aData['organizationId'],"删除","修改");
                            	return delhtml;
                            }
                        }
                    ]
                });
            	
            	//动态创建的元素 通过绑定到 document
            	$(document).off('click.modal').on('click.modal.data-api', '[1data-toggle^="modal"]', function ( e ) {
                	var procDefId = $(this).attr('procDefId');
                	
                    $('#stack1').modal({
                    	confirm:function(formvalue){
                    		var param = $('#myform').serialize();
                    		$.post("/deep/sysmng/addOrg",
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
            		if(confirm("确认删除")){
            		$.post("/deep/sysmng/delOrg",
            				"organizationId="+id,
            			function(data){
            			   mygridtab.fnDraw();
            			   alert(data);
            		    }
            		);
            		}
            	});
            	
            	//修改资源
            	$(document).off('click.modal3').on('click.modal3.data-api', '[data-toggle="edit"]', function ( e ) {
            		var id = $(this).attr('id');
            		$('#myform')[0].reset();
            		
            		$.ajax({
						url:'/deep/sysmng/queryOrg',
						async:true,
						method:'POST',
						data:'organizationId='+id,
						dataType : 'json',
						error :function(resp){alert(resp)},
						success:function(resp){
							$('#myform input[name="organizationId"]').val(resp.organizationId);
							$('#myform input[name=organizationName]').val(resp.organizationName);
							$('#myform input[name=parentOrgId]').val(resp.parentOrgId);
						}
					});
            		
            		$('#stack1').modal({
                    	confirm:function(formvalue){
                    		var param = $('#myform').serialize();
                    		$.post("/deep/sysmng/updateOrg",
                    				param,
                    			function(data){
                    			   mygridtab.fnDraw();
                    			   alert(data);
                    		    }
                    		);
                    	}
                    });
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
            	
            	$(".page-sidebar-menu li[name='系统管理']").addClass("active");
            	$(".page-sidebar-menu li[name='部门']").addClass("active");
            });