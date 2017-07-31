$(document)
		.ready(
				function() {
					var mygrid = $('#sample_1')
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
												return "<a href='/deep/bpm/taskCompletePage?taskId={0}'><i class='icon-edit'></i>{1}</a>&nbsp; <a class='mini purple' id='{0}' data-toggle='claim' ><i class='icon-ok-sign'></i> {2}</a>"
														.format(
																setobj.aData['taskId'],
																"受理", "接受");
											}
										} ]
									});

					// 删除资源
					$(document).off('click.modal').on(
							'click.modal.data-api',
							'[data-toggle="claim"]',
							function(e) {
								var id = $(this).attr('id');
								$.post(cxt + "/bpm/claimTask", "taskId=" + id,
										function(rsp) {
									        mygrid.fnDraw();
											sysNotify(rsp.responseMessage);
										});
							});

					function sysNotify(info) {

						var unique_id = $.gritter.add({
							position : 'bottom-right',
							title : '系统通知!',
							text : info,
							sticky : false,
							time : '2000',
							// class_name: 'my-sticky-class'
							class_name : 'gritter-success'
						});
					}

					$(".page-sidebar-menu li[name='流程管理']").addClass("active");
					$(".page-sidebar-menu li[name='任务列表']").addClass("active");

				});