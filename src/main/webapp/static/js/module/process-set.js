            $(document).ready(function () {
            	var mygridtab = $('#transactions').dataTable({
                	"bProcessing": true,
                    "bServerSide": true,
                    "bFilter":true,
                    "bLengthChange":false,
                    "sAjaxSource": cxt+"/bpm/processDefineList",
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
                      { "sTitle": "编号","mData":"processDefinedId","bSortable": false,"sWidth":100},
                      { "sTitle": "名称","mData":"name","bSortable": false,"sWidth":100},
                      { "sTitle": "描述","mData":"key","bSortable": false,"sWidth":100 },
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
                            	var delhtml = "<a class='mini purple' id='{0}' data-toggle='delete' ><i class='icon-trash'></i> {1}</a>&nbsp;<a class='mini purple' id='{0}' data-toggle='edit' ><i class='icon-edit'></i> {2}</a>&nbsp;<a class='mini purple' id='{0}' data-toggle='set' ><i class='icon-edit'></i> {3}</a>&nbsp;<a class='mini purple' id='{0}' data-toggle='reStart' ><i class='icon-edit'></i> {4}</a>".format(setobj.aData['key'],"删除","修改","设置","启动");
                            	return delhtml;
                            }
                        }
                    ]
                });
            	
                //var table = $('#sample_1').DataTable();
            	//动态创建的元素 通过绑定到 document
            	$(document).off('click.modal').on('click.modal.data-api', '[1data-toggle^="deploy"]', function ( e ) {
                	var procDefId = $(this).attr('procDefId');
                	$('#myform')[0].reset();
                	
                	$("#myform").ajaxForm({
                		dataType:'json',
                		error:function (){alert("操作失败，请联系管理员")},
                		success:function(responseTxt){
                		  alert(responseTxt);
                		},
                		type:'POST',
                		url:cxt+'/bpm/deployProcess'
                	});
                	
                    $('#stack1').modal({
                    	confirm:function(formvalue){
                    		$("#myform").trigger("submit");
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
                		    }
                		);
            		}
            	});
            	//修改资源
            	$(document).off('click.modal3').on('click.modal3.data-api', '[data-toggle="edit"]', function ( e ) {
            		var id = $(this).attr('id');
            		window.location.href = cxt+"/form/updateForm?formId="+id;
            	});
            	//跳转流程设置page
            	$(document).off('click.modal4').on('click.modal4.data-api', '[data-toggle="set"]', function ( e ) {
            		var id = $(this).attr('id');
            		window.location.href = cxt+"/bpm/processDefineSetPage?processDefinedId="+id;
            	});
            	
            	//跳转流程启动page
            	$(document).off('click.modal5').on('click.modal5.data-api', '[data-toggle="reStart"]', function ( e ) {
            		var id = $(this).attr('id');
            		window.location.href = cxt+"/bpm/processStartPage?processDefinedId="+id;
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
            	$(".page-sidebar-menu li[name='流程定义']").addClass("active");
            });