$('#sample_1').dataTable({
            	"bProcessing": true,
                "bServerSide": true,
                "bFilter":true,
                "bLengthChange":false,
                "sAjaxSource": "/deep/job/queryJobList",
                "fnServerParams":{'bbbb':'454545555555555555'},
                "oSearch": {"sSearch":"初始化1","abc":"123"},
           		"bStateSave": true,
       		    "fnStateLoadParams": function (oSettings, oData) {
       		        //oData.oSearch.sSearch = "aabb=6666666666666";
       		        //oData.oSearch = {"processId": "5665454555555","sSearch":"000"};
       		    	
       		    },
           		"aoSearchCols": [
           		     		           null,
           		     		           { "sSearchaa": "My filter" },
           		     		           null,
           		     		           { "groupId": "^[0-9]", "bEscapeRegex": false }
           		     		  ],
                "aoColumns": [
                  { "sTitle": "组名称","mData":"groupId","bSortable": false,"sWidth":"400",height:"20"},
                  { "sTitle": "任务名称","mData":"jobId","bSortable": false,"sWidth":100 },
                  { "sTitle": "出发器名称","mData":"triggerName","bSortable": false },
                  { "sTitle": "创建日期","mData":"gmtCreate","bSortable": false },
                  { "sTitle": "操作","mData":"status","bSortable": false }
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
                        	return "<a class=' btn green' name='showProcessImage' id='showProcessImage'  1data-toggle='modal' procDefId='"+setobj.aData['processDefineId']+"' href='javascript:void(0)' trigger='alert('0000')'>流程</a>";
                        }
                    }
                ]
            });
            
            $(document).ready(function () {
                //var table = $('#sample_1').DataTable();
            	//动态创建的元素 通过绑定到 document
            	$(document).off('click.modal').on('click.modal.data-api', '[1data-toggle="modal"]', function ( e ) {
                	var procDefId = $(this).attr('procDefId');
                	//alert(procDefId);
                    $('#stack1').modal({
                    	confirm:function(formvalue){
                    		var param = $('#myform').serialize();
                    		$.post("/deep/job/createJob",
                    				param,
                    			function(data){
                    			   alert(data);
                    		    }
                    		);
                    	}
                    	
                    });
                })
                //给2个输入框添加blur事件调用draw方法执行自定义过滤函数
                $('#min, #max').blur(function () {
                    table.draw();
                });
            });
            
            $("a[name='showProcessImage']").click(function(){
            	alert( $(this).attr('procDefId'));
            });