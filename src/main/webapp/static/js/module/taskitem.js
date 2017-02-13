$('#sample_1').dataTable({
            	"bProcessing": true,
                "bServerSide": true,
                "bFilter":true,
                "bLengthChange":false,
                "sAjaxSource": "/deep/bpm/taskItem",
                "aoColumns": [
                  { "sTitle": "主题","mData":"processName","bSortable": false,"sWidth":"400" },
                  { "sTitle": "申请时间","mData":"startDate","bSortable": false },
                  { "sTitle": "受理人","mData":"assignee","bSortable": false },
                  { "sTitle": "任务编号","mData":"taskId","bSortable": false },
                  { "sTitle": "操作","mData":"processInstanceId","bSortable": false }
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
                        fnRender: function (setobj, data ) {
                            return "<a href='/deep/bpm/taskCompletePage?taskId="+data+"'>"+data+"</a>";
                        }
                    }
                ]
            });
            
            $(document).ready(function () {
                var table = $('#sample_1').DataTable();
                //给2个输入框添加blur事件调用draw方法执行自定义过滤函数
                $('#min, #max').blur(function () {
                    table.draw();
                });
            });