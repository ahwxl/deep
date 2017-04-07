var permissionTable = $('#permissionTable')
		.dataTable(
				{
					"bProcessing" : true,
					"bServerSide" : true,
					"bFilter" : true,
					"bLengthChange" : false,
					"sAjaxSource" : "/deep/sysmng/permissionList",
					"bStateSave" : true,
					"oFeatures" : {
						'sDom' : ''
					},
					"fnStateLoadParams" : function(oSettings, oData) {
						$("#mySubmit").bind(
								"click",
								function() {
									var oSettings = mygridtab.fnSettings();
									oSettings.serverparam = $('#searchform')
											.serializeObject();
									oSettings.sDom = '';
									mygridtab.fnFilter('11');
								});
					},
					"aoSearchCols" : [ null, {
						"sSearchaa" : "My filter"
					}, null, {
						"groupId" : "^[0-9]",
						"bEscapeRegex" : false
					} ],
					"aoColumns" : [
							{
								"bSortable" : false,
								"sWidth" : 20,
								"sSortDataType" : "dom-checkbox",
								"fnRender":function(a,b,c,aData){
									return "<input type='checkbox' {0} class='group-checkable' value='{1}' />".format(a.aData['checked']==true?"checked":"",a.aData['permissionId']);
								},
								"sDefaultContent" : "<input type=\"checkbox\" class=\"group-checkable\" value=\"1\" />"
							}, {
								"sTitle" : "编码",
								"mData" : "permissionId",
								"bSortable" : false,
								"sWidth" : 200
							}, {
								"sTitle" : "名称",
								"mData" : "remark",
								"bSortable" : false,
								"sWidth" : 130
							} ],
					"aLengthMenu" : [ [ 5, 15, 20, -1 ], [ 5, 15, 20, "All" ] // change
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
						'aTargets' : [ 0 ]
					} ]
				});

$(document).ready(
		function() {
			// var table = $('#sample_1').DataTable();
			// 动态创建的元素 通过绑定到 document
			$(document).off('click.modal').on(
					'click.modal.data-api',
					'[1data-toggle^="modal"]',
					function(e) {
						var procDefId = $(this).attr('procDefId');
						$('#stack1').modal(
								{
									confirm : function(formvalue) {
										var param = $('#myform').serialize();
										$.post("/deep/sysmng/addPerm", param,
												function(data) {
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

						$.post("/deep/sysmng/delPerm", "permissionId=" + id,
								function(data) {
									mygridtab.fnDraw();
									alert(data);
								});

					});

			// 系统资源树展示
			var setting = {
				check : {
					enable : true
				},
				view : {
					dblClickExpand : false,
					showLine : true,
					selectedMulti : false
				},
				async : {
					enable : true,
					url : "/deep/sysmng/queryResForTree",
					autoParam : [ "id=parentResourceId", "name=n", "level=lv" ],
					otherParam : {
						"otherParam" : "zTreeAsyncTest"
					},
					dataFilter : filter
				},
				callback: {
					//beforeClick: beforeClick,
					onClick: onClick
				}
			};
			
			function onClick(event, treeId, treeNode, clickFlag){
				event.stopPropagation();
			}

			function filter(treeId, parentNode, childNodes) {
				if (!childNodes)
					return null;
				for (var i = 0, l = childNodes.length; i < l; i++) {
					childNodes[i].name = childNodes[i].name
							.replace(/\.n/g, '.');
				}
				return childNodes;
			}

			//初始化资源树
			$.fn.zTree.init($("#resourceTree"), setting);

			// 添加资源与权限对应关系  一对一
			$("#save").click(
					function() {
						var checklist = [];
						$("#permissionTable>tbody>tr>td input[type=checkbox]:checked")
								.each(function(e) {
									checklist.push($(this).val());
								});
						var selectIds = checklist.join(",");
						
						var treeObj = $.fn.zTree.getZTreeObj("resourceTree");
						var nodes = treeObj.getCheckedNodes(true);
						var leafNodes = [];//选择的叶子节点
						
						for(var node in nodes){
							if(!nodes[node].isParent){
								leafNodes.push(nodes[node]);
							}
						}
						
						if(leafNodes.length == 0){
							alert("请选择一个资源");
							return false;
						}
						if(leafNodes.length > 1){
							alert("只能选择一个资源");
							return false;
						}
						var resourceIds = leafNodes[0].id;
						
						$.ajax({
							url:'/deep/sysmng/addPermRes',
							async:true,
							method:'POST',
							contentType:'application/x-www-form-urlencoded; charset=UTF-8',
							data:'resourceIds='+resourceIds+"&permissionId="+selectIds,
							dataType : 'json',
							error :function(resp){alert(resp)},
							success:function(resp){
								alert('success');
							}
						});
			});

			//权限列表第一个 checkbox 全选 动作
			$("#permissionTable th input:checkbox").on(
					"click",
					function() {
						var that = this;
						$(this).closest("table").find(
								"tr > td:first-child input:checkbox").each(
								function() {
									if (this.disabled) {
										return;
									}
									this.checked = that.checked;
								});
					});

		});