$(document).ready(function() {
					$('#sample_1')
							.dataTable(
									{
										"bProcessing" : true,
										"bServerSide" : true,
										"bFilter" : true,
										"bLengthChange" : false,
										"sAjaxSource" : cxt
												+ "/bpm/processInstance",
										"aoColumns" : [ {
											"sTitle" : "流水号",
											"mData" : "processInstanceId",
											"bSortable" : false
										},{
											"sTitle" : "主题",
											"mData" : "processName",
											"bSortable" : false,
											"sWidth" : "400"
										}, {
											"sTitle" : "申请时间",
											"mData" : "startDate",
											"bSortable" : false
										}, {
											"sTitle" : "当前状态",
											"mData" : "processStatus",
											"bSortable" : false,
											"sWidth" : 100
										}, {
											"sTitle" : "操作",
											"mData" : "endDate",
											"bSortable" : false
										} ],
										"aLengthMenu" : [ [ 5, 15, 20, -1 ],
												[ 5, 15, 20, "All" ] // change
																		// per
																		// page
																		// values
																		// here
										],
										// set the initial value
										"iDisplayLength" : 10,
										// "sDom":
										// "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
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
												return "<a class='mini purple' name='showProcessImage' id='showProcessImage'  1data-toggle='modal' procDefId='{0}' href='javascript:void(0)'> {1}</a> {2} {3}"
														.format(
																setobj.aData['processDefineId'],
																'流程','暂停','删除');
											}
										} ]
									});

					// var table = $('#sample_1').DataTable();
					// 动态创建的元素 通过绑定到 document
					$(document).off('click.modal').on(
							'click.modal.data-api',
							'[1data-toggle="modal"]',
							function(e) {
								var procDefId = $(this).attr('procDefId');
								// alert(procDefId);
								var d = new Date();
								var url = "/deep/bpm/viewProcessDefImage?key="
										+ procDefId + "&d=" + d.getTime();
								$("#myprocessimage").attr("src", url);
								$('#stack1').modal({
									width : 900,
									height : 800
								});
							})

					$(".page-sidebar-menu li[name='流程管理']").addClass("active");
					$(".page-sidebar-menu li[name='流程列表']").addClass("active");

				});