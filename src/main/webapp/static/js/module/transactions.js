$(document).ready(function () {
var mygridtab = $('#transactions').dataTable({
            	"bProcessing": true,
                "bServerSide": true,
                "bFilter":true,
                "bLengthChange":false,
                "sAjaxSource": "/deep/stock/queryTransList",
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
                  { "sTitle": "编号","mData":"stockId","bSortable": false,"sWidth":"100",height:"20"},
                  { "sTitle": "名称","mData":"stockName","bSortable": false,"sWidth":100 },
                  { "sTitle": "数量","mData":"amount","bSortable": false },
                  { "sTitle": "价格","mData":"price","bSortable": false },
                  { "sTitle": "交易类型","mData":"transactionType","bSortable": false,"sWidth":60 },
                  { "sTitle": "交易日期","mData":"transactionDate","bSortable": false },
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
                        'aTargets': [6],
                        fnRender: function (setobj, data) {
                        	var delhtml = "<a class='mini purple' id='{0}' data-toggle='delete'><i class='icon-trash'></i>{1}</a>&nbsp;<a class='mini purple' id='{0}' data-toggle='edit' ><i class='icon-edit'></i> {2}</a>".format(setobj.aData['id'],"删除","修改");
                        	return delhtml;
                        }
                    },{
                    	'aTargets': [4],
                    	fnRender: function (setobj, data) {
                    		if(data == '1'){
                    			return "<a  class='red'>买入</a>";
                    		}else return "<a  class='blue'>卖出</a>";
                    	}
                    }
                ]
            });
            
            
                //var table = $('#sample_1').DataTable();
            	//动态创建的元素 通过绑定到 document
            	$(document).off('click.modal').on('click.modal.data-api', '[1data-toggle^="modal"]', function ( e ) {
                	var procDefId = $(this).attr('procDefId');
                	//alert(procDefId);
                    $('#stack1').modal({
                    	confirm:function(formvalue){
                    		var param = $('#myform').serialize();
                    		$.post("/deep/stock/createTrans",
                    				param,
                    			function(data){
                    			   mygridtab.fnDraw();
                    			   alert(data);
                    		    }
                    		);
                    	}
                    	
                    });
                });
            	//删除
            	$(document).off('click.modal2').on('click.modal2.data-api', '[data-toggle="delete"]', function ( e ) {
            		var id = $(this).attr('id');
            		
            		$.post(cxt +"/stock/delTrans",
            				"id="+id,
            			function(data){
            			   mygridtab.fnDraw();
            			   alert(data);
            		    }
            		);
            		
            	});
            	
            	//修改
            	$(document).off('click.modal3').on('click.modal3.data-api', '[data-toggle="edit"]', function ( e ) {
            		var id = $(this).attr('id');
            		$.ajax({
						url:cxt +'/stock/queryTransaction',
						async:true,
						method:'POST',
						data:'id='+id,
						dataType : 'json',
						error :function(resp){alert(resp)},
						success:function(resp){
							$('#myform input[name="id"]').val(resp.id);
							$('#myform input[name="stockId"]').val(resp.stockId);
							$('#myform input[name="stockName"]').val(resp.stockName);
							$('#myform input[name="amount"]').val(resp.amount);
							$('#myform input[name="price"]').val(resp.price);
							$('#myform select[name="transactionType"]').val(resp.transactionType);
							$('#myform input[name="transactionDate"]').val(resp.transactionDate);
						}
					});
            		//打开修改窗口
            		$('#stack1').modal({
                    	confirm:function(formvalue){
                    		$('#myform').attr("action",cxt + "/stock/modifyTrans").trigger("submit");
                    	}
                    	
                    });
            		
            	});
            	//表单验证
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
                    			App.sysNotify(responseTxt.responseMsg);
                    		},
                    		type:'POST',
                    		url:form.action
                    	});
                    }
                });
            	
            	if (jQuery().datepicker) {
                    $('.date-picker').datepicker('yyyy-mm-dd');
                }
            	
            	$(".page-sidebar-menu li[name='调度管理']").addClass("active");
            	$(".page-sidebar-menu li[name='交易记录']").addClass("active");
            });