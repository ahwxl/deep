$(document)
		.ready(
				function() {
					var mygridtab = $('#sample_1')
							.dataTable(
									{
										"bProcessing" : true,
										"bServerSide" : true,
										"bFilter" : true,
										"bLengthChange" : false,
										"sAjaxSource" : "/deep/app/queryForPage",
										"fnServerParams" : {
											'bbbb' : '454545555555555555'
										},
										"oSearch" : {
											"sSearch" : "初始化1",
											"abc" : "123"
										},
										"bStateSave" : true,
										"oFeatures" : {
											'sDom' : ''
										},
										"fnStateLoadParams" : function(
												oSettings, oData) {

											$("#mySubmit")
													.bind(
															"click",
															function() {
																var oSettings = mygridtab
																		.fnSettings();
																oSettings.serverparam = $(
																		'#searchform')
																		.serializeObject();
																oSettings.sDom = '';
																mygridtab
																		.fnFilter('张是');
																/*
																 * mygridtab.fnFilter(
																 * oSettings, {
																 * "sSearch":
																 * val,
																 * "bRegex":
																 * oPreviousSearch.bRegex,
																 * "bSmart":
																 * oPreviousSearch.bSmart ,
																 * "bCaseInsensitive":
																 * oPreviousSearch.bCaseInsensitive } );
																 */
															});

										},
										"aoSearchCols" : [ null, {
											"sSearchaa" : "My filter"
										}, null, {
											"groupId" : "^[0-9]",
											"bEscapeRegex" : false
										} ],
										"aoColumns" : [ {
											"sTitle" : "应用编码",
											"mData" : "appCode",
											"bSortable" : false,
											"sWidth" : "300",
											height : "20"
										}, {
											"sTitle" : "应用名称",
											"mData" : "appName",
											"bSortable" : false,
											"sWidth" : 100
										}, {
											"sTitle" : "ip地址",
											"mData" : "ipAddr",
											"bSortable" : false
										}, {
											"sTitle" : "创建日期",
											"mData" : "gmtCreate",
											"bSortable" : false,
											"sWidth" : 150
										}, {
											"sTitle" : "操作",
											"mData" : "id",
											"bSortable" : false,
											"sWidth" : 110
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
												var htmlstr = "<a class=' mini purple' name='showProcessImage' id='showProcessImage'  1data-toggle='modal' procDefId='"
														+ setobj.aData['processDefineId']
														+ "' href='javascript:void(0)'><i class='icon-edit'></i>修改</a>";
												var delhtml = "<a class=' mini purple' id='{0}' data-toggle='delete' ><i class='icon-trash'></i>{1}</a>"
														.format(
																setobj.aData['id'],
																"删除");
												return htmlstr + delhtml;
											}
										} ]
									});

					// var table = $('#sample_1').DataTable();
					// 动态创建的元素 通过绑定到 document
					$(document).off('click.modal').on(
							'click.modal.data-api',
							'[1data-toggle^="modal"]',
							function(e) {
								// var procDefId = $(this).attr('procDefId');
								// alert(procDefId);
								$('#stack1').modal(
										{
											confirm : function(formvalue) {
												var param = $('#myform')
														.serialize();
												$.post("/deep/job/createJob",
														param, function(data) {
															mygridtab.fnDraw();
															alert(data);
														});
											}

										});
							});

					$(document).off('click.modal2').on(
							'click.modal2.data-api',
							'[data-toggle="delete"]',
							function(e) {
								var id = $(this).attr('id');

								$.post("/deep/job/delJob", "id=" + id,
										function(data) {
											mygridtab.fnDraw();
											alert(data);
										});

							});

					$(document).off('click.modal3').on(
							'click.modal3.data-api',
							'[data-toggle="deploy"]',
							function(e) {
								var id = $(this).attr('id');

								$.post("/deep/app/deploy", "id=" + id,
										function(data) {
											alert(data);
										});

							});

					$(document).off('click.modal4').on(
							'click.modal4.data-api',
							'[data-toggle="start"]',
							function(e) {
								var id = $(this).attr('id');

								$.post("/deep/app/start", "id=" + id, function(
										data) {
									alert(data);
								});

							});

					$(document).off('click.modal5').on(
							'click.modal5.data-api',
							'[data-toggle="stop"]',
							function(e) {
								var id = $(this).attr('id');

								$.post("/deep/app/stop", "id=" + id, function(
										data) {
									alert(data);
								});

							});

					if (jQuery().datepicker) {
						$('.date-picker').datepicker();
					}
					$(".page-sidebar-menu li[name='服务器管理']").addClass("active");
					$(".page-sidebar-menu li[name='应用管理']").addClass("active");
				});
