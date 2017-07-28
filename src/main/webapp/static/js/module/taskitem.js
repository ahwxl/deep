$(document)
		.ready(
				function() {
					$('#sample_1')
							.dataTable(
									{
										"bProcessing" : true,
										"bServerSide" : true,
										"bFilter" : true,
										"bLengthChange" : false,
										"sAjaxSource" : "/deep/bpm/taskItem",
										"aoColumns" : [ {
											"sTitle" : "任务编号",
											"mData" : "taskId",
											"bSortable" : false
										}, {
											"sTitle" : "主题",
											"mData" : "processName",
											"bSortable" : false,
											"sWidth" : "400"
										}, {
											"sTitle" : "开始时间",
											"mData" : "startDate",
											"bSortable" : false
										}, {
											"sTitle" : "受理人",
											"mData" : "assignee",
											"bSortable" : false
										}, {
											"sTitle" : "操作",
											"mData" : "processInstanceId",
											"bSortable" : false
										} ],
										"aLengthMenu" : [ [ 5, 15, 20, -1 ],
												[ 5, 15, 20, "All" ] ],
										"iDisplayLength" : 10,
										"sPaginationType" : "bootstrap",
										"oLanguage" : {
											"sLengthMenu" : "_MENU_ records per page",
											"oPaginate" : {
												"sPrevious" : "Prev",
												"sNext" : "Next"
											}
										},
										"aoColumnDefs" : [ {
											'bSortable' : false,
											'aTargets' : [ 4 ],
											fnRender : function(setobj, data) {
												return "<a href='/deep/bpm/taskCompletePage?taskId={0}'>{1}</a>&nbsp; {2}"
														.format(
																setobj.aData['taskId'],
																"受理", "接受");
											}
										} ]
									});

					var table = $('#sample_1').DataTable();
					// 给2个输入框添加blur事件调用draw方法执行自定义过滤函数
					$('#min, #max').blur(function() {
						table.draw();
					});

					$(".page-sidebar-menu li[name='流程管理']").addClass("active");
					$(".page-sidebar-menu li[name='任务列表']").addClass("active");

				});