$(document).ready(function () {
var mygridtab = $('#sample_1').dataTable({
            	"bProcessing": true,
                "bServerSide": true,
                "bFilter":true,
                "bLengthChange":false,
                "sAjaxSource": "/deep/job/queryJobList",
                "fnServerParams":{'bbbb':'454545555555555555'},
                "oSearch": {"sSearch":"初始化1","abc":"123"},
           		"bStateSave": true,
           		"oFeatures":{'sDom':''},
       		    "fnStateLoadParams": function (oSettings, oData) {
       		    	
       		    	$("#mySubmit").bind("click", function(){
       		    		var oSettings = mygridtab.fnSettings();
       		    		oSettings.serverparam=$('#searchform').serializeObject();
       		    		oSettings.sDom='';
       		    		mygridtab.fnFilter('张是');
       		    		/*mygridtab.fnFilter( oSettings, {
    						"sSearch": val, 
    						"bRegex": oPreviousSearch.bRegex,
    						"bSmart": oPreviousSearch.bSmart ,
    						"bCaseInsensitive": oPreviousSearch.bCaseInsensitive 
    					} );*/
       		    	});
       		    	
       		    },
           		"aoSearchCols": [
           		     		           null,
           		     		           { "sSearchaa": "My filter" },
           		     		           null,
           		     		           { "groupId": "^[0-9]", "bEscapeRegex": false }
           		     		  ],
                "aoColumns": [
                  { "sTitle": "组名称","mData":"groupId","bSortable": false,"sWidth":"100",height:"20"},
                  { "sTitle": "任务名称","mData":"jobId","bSortable": false,"sWidth":100 },
                  { "sTitle": "出发器名称","mData":"triggerName","bSortable": false,"sWidth":100 },
                  { "sTitle": "创建日期","mData":"gmtCreate","bSortable": false,"sWidth":150 },
                  { "sTitle": "操作","mData":"status","bSortable": false,"sWidth":190 }
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
                        	var htmlstr = "<a class=' mini purple' id='{0}' data-toggle='editer' href='javascript:void(0)'><i class='icon-edit'></i>{1}</a>".format(setobj.aData['id'],"修改");
                        	var pause =   "<a class=' mini purple' id='{0}'   data-toggle='pause' ><i class='icon-trash'></i>{1}</a>".format(setobj.aData['id'],"暂停");
                        	var delhtml = "<a class=' mini purple' id='{0}' data-toggle='delete' ><i class='icon-trash'></i>{1}</a>".format(setobj.aData['id'],"删除");
                        	var execute = "<a class=' mini purple' id='{0}' data-toggle='execute' ><i class='icon-trash'></i>{1}</a>".format(setobj.aData['id'],"执行");
                        	var resume = "<a class=' mini purple' id='{0}' data-toggle='resume' ><i class='icon-trash'></i>{1}</a>".format(setobj.aData['id'],"开始");
                        	
                        	if("2" == setobj.aData['status']){
                        		pause = resume;
                        	}
                        	
                        	return htmlstr+pause+delhtml+execute;
                        }
                    }
                ]
            });
            
                //var table = $('#sample_1').DataTable();
            	//动态创建的元素 通过绑定到 document
                //添加
            	$(document).off('click.modal1').on('click.modal.data-api', '[1data-toggle^="modal"]', function ( e ) {
                	//var procDefId = $(this).attr('procDefId');
                    $('#stack1').modal({
                    	confirm:function(formvalue){
                    		var param = $('#myform').serialize();
                    		$.post("/deep/job/createJob",
                    				param,
                    			function(data){
                    			   mygridtab.fnDraw();
                    			   alert(data);
                    		    }
                    		);
                    	}
                    	
                    });
                });
            	
            	//修改
            	$(document).off('click.modal2').on('click.modal1.data-api', '[data-toggle="editer"]', function ( e ) {
                	var id = $(this).attr('id');
                	
                    $('#myform')[0].reset();
            		
            		$.ajax({
						url:'/deep/job/queryJob',
						async:true,
						method:'POST',
						data:'id='+id,
						dataType : 'json',
						error :function(resp){alert(resp)},
						success:function(resp){
							$('#myform input[name="id"]').val(resp.id);
							$('#myform input[name=taskParam]').val(resp.taskParam);
							$('#myform input[name=triggerName]').val(resp.triggerName);
							$('#myform input[name=jobId]').val(resp.jobId);
						}
					});
                	
                    $('#stack1').modal({
                    	confirm:function(formvalue){
                    		var param = $('#myform').serialize();
                    		$.post("/deep/job/editerJob",
                    				param,
                    			function(data){
                    			   mygridtab.fnDraw();
                    			   alert(data);
                    		    }
                    		);
                    	}
                    	
                    });
                });
            	
            	
            	//删除任务
            	$(document).off('click.modal3').on('click.modal2.data-api', '[data-toggle="delete"]', function ( e ) {
            		var id = $(this).attr('id');
            		
            		$.post("/deep/job/delJob",
            				"id="+id,
            			function(data){
            			   mygridtab.fnDraw();
            			   alert(data);
            		    }
            		);
            		
            	});
            	//执行
            	$(document).off('click.modal4').on('click.modal3.data-api', '[data-toggle="execute"]', function ( e ) {
            		var id = $(this).attr('id');
            		
            		$.post("/deep/job/executeJob",
            				"id="+id,
            			function(data){
            			   mygridtab.fnDraw();
            			   alert(data);
            		    }
            		);
            		
            	});
            	
            	$(document).off('click.modal5').on('click.modal3.data-api', '[data-toggle="pause"]', function ( e ) {
            		var id = $(this).attr('id');
            		
            		$.post("/deep/job/pauseJob",
            				"id="+id,
            			function(data){
            			   mygridtab.fnDraw();
            			   alert(data);
            		    }
            		);
            		
            	});
            	
            	$(document).off('click.modal6').on('click.modal3.data-api', '[data-toggle="resume"]', function ( e ) {
            		var id = $(this).attr('id');
            		
            		$.post("/deep/job/resumeJob",
            				"id="+id,
            			function(data){
            			   mygridtab.fnDraw();
            			   alert(data);
            		    }
            		);
            		
            	});
            	
            	if (jQuery().datepicker) {
                    $('.date-picker').datepicker();
                }
            	$(".page-sidebar-menu li[name='调度管理']").addClass("active");
            	$(".page-sidebar-menu li[name='任务']").addClass("active");
            });
