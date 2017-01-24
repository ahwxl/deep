$('#sample_1').dataTable({
            	"bProcessing": true,
                "bServerSide": true,
                "bFilter":true,
                "bLengthChange":false,
                "sAjaxSource": "/deep/bpm/processInstance",
                "aoColumns": [
                  { "sTitle": "主题","mData":"processName","bSortable": false },
                  { "sTitle": "申请时间","mData":"startDate","bSortable": false },
                  { "sTitle": "当前状态","mData":"processStatus","bSortable": false },
                  { "sTitle": "流水号","mData":"processInstanceId","bSortable": false },
                  { "sTitle": "操作","mData":"endDate","bSortable": false }
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
                        	return "<a class=' btn green'  data-toggle='modal' href='#stack1' trigger='alert('0000')'>流程</a>";
                            //return "<a href='/deep/bpm/taskCompletePage?taskId="+data+"'>"+data+"</a>";
                        }
                    }
                ]
            });
            
            $(document).ready(function () {
                var table = $('#sample_1').DataTable();
                
                $('#stack1').on('show', function (e) {
                	var url = "/deep/bpm/viewProcessDefImage?key=countersign:5:52504";
                    $("#myprocessimage").attr("src",url);
                })
                //给2个输入框添加blur事件调用draw方法执行自定义过滤函数
                $('#min, #max').blur(function () {
                    table.draw();
                });
            });