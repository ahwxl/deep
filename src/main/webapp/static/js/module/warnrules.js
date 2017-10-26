$(document).ready(function () {
var mygridtab = $('#transactions').dataTable({
            	"bProcessing": true,
                "bServerSide": true,
                "bFilter":true,
                "bLengthChange":false,
                "sAjaxSource": "/deep/warn/warnRuleList",
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
                  { "sTitle": "编号","mData":"ruleId","bSortable": false,"sWidth":"30",height:"20"},
                  { "sTitle": "内容","mData":"scripte","bSortable": false,"sWidth":300 },
                  { "sTitle": "描述","mData":"ruleMsg","bSortable": false,"sWidth":300 },
                  { "sTitle": "创建日期","mData":"gmtCreate","bSortable": false },
                  { "sTitle": "操作","mData":"gmtModify","bSortable": false,"sWidth":130 }
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
                        	var delhtml = "<a class='mini purple' id='{0}' data-toggle='delete' ><i class='icon-trash'></i> {1}</a>&nbsp;<a class='mini purple' id='{0}' data-toggle='edit' ><i class='icon-edit'></i> {2}</a>".format(setobj.aData['ruleId'],"删除","修改");
                        	return delhtml;
                        }
                    }
                ]
            });
            
            
                //var table = $('#sample_1').DataTable();
            	//动态创建的元素 通过绑定到 document
            	$(document).off('click.modal').on('click.modal.data-api', '[1data-toggle^="modal"]', function ( e ) {
                    $('#stack1').modal({
                    	confirm:function(formvalue){
                    		$('#myform').attr("action",cxt + "/warn/createWarnRule").trigger("submit");
                    	}
                    	
                    });
                });
            	//删除
            	$(document).off('click.modal2').on('click.modal2.data-api', '[data-toggle="delete"]', function ( e ) {
            		var id = $(this).attr('id');
            		$.post("/deep/warn/delWarnRule",
            				"ruleId="+id,
            			function(data){
            			   mygridtab.fnDraw();
            			   alert(data);
            		    }
            		);
            		
            	});
            	//修改规则
            	$(document).off('click.modal3').on('click.modal3.data-api', '[data-toggle="edit"]', function ( e ) {
            		var id = $(this).attr('id');
            		$.ajax({
						url:cxt +'/warn/queryWarnRule',
						async:true,
						method:'POST',
						data:'ruleId='+id,
						dataType : 'json',
						error :function(resp){alert(resp)},
						success:function(resp){
							$('#myform input[name="ruleId"]').val(resp.ruleId);
							$('#myform textarea[name="scripte"]').val(resp.scripte);
							$('#myform textarea[name="ruleMsg"]').val(resp.ruleMsg);
						}
					});
            		//打开修改窗口
            		$('#stack1').modal({
                    	confirm:function(formvalue){
                    		$('#myform').attr("action",cxt + "/warn/modifyWarnRule").trigger("submit");
                    	}
                    	
                    });
            		
            	});
            	
            	var form1 = $('#myform');
            	var error1 = $('.alert-error', form1);
                var success1 = $('.alert-success', form1);
                form1.validate({
                    errorElement: 'span', //default input error message container
                    errorClass: 'help-inline', // default input error message class
                    focusInvalid: false, // do not focus the last invalid input
                    ignore: "",
                    rules: {
                    	ruleId:{
                    		required: true
                    	},
                    	scripte: {
                    		required: true
                        },
                        ruleMsg: {
                            required: true
                        }
                    },
                    
                    messages: {
                    	ruleId: {
                    		required:"请输入规则编号"
                    	},
                    	scripte:"请输入脚本内容",
                    	ruleMsg: "请输入提示信息"
                    },

                    invalidHandler: function (event, validator) {
                        success1.hide();
                        error1.show();
                    },

                    highlight: function (element) {
                        $(element)
                            .closest('.help-inline').removeClass('ok');
                        $(element)
                            .closest('.control-group').removeClass('success').addClass('error');
                    },

                    unhighlight: function (element) {
                        $(element)
                            .closest('.control-group').removeClass('error');
                    },

                    success: function (label) {
                        label
                            .addClass('valid').addClass('help-inline ok')
                        .closest('.control-group').removeClass('error').addClass('success');
                    },

                    submitHandler: function (form) {
                    	$("#myform").ajaxSubmit({
                    		dataType:'json',
                    		error:function (){alert("操作失败，请联系管理员")},
                    		success:function(responseTxt){
                    			mygridtab.fnDraw();
                    			alert(responseTxt.responseMsg);
                    		},
                    		type:'POST',
                    		url:form.action
                    	});
                    }
                });
            	
            	$(".page-sidebar-menu li[name='调度管理']").addClass("active");
            	$(".page-sidebar-menu li[name='规则脚本']").addClass("active");
            });