$(document).ready(function() {
	var relation =function() {
		
	    var checkedEl = [];
	    var delEl     = [];
	    var addEl     = [];
	    
	    return {
	    	addSelectEl:function (el){
	    		checkedEl.push(el);
	    	},
	    	containEl:function (ctr,el){
	    		if( typeof ctr == "undefined" ){return false;}
	    		var result = false;
	    		for(var tmp in ctr){
	    			if(ctr[tmp] == el){
	    				result = true;
	    				break;
	    			}
	    		}
	    		return result;
	    	},
	    	removeEl:function (arr, val) {
	    		  for(var i=0; i<arr.length; i++) {
	    		    if(arr[i] == val) {
	    		      arr.splice(i, 1);
	    		      break;
	    		    }
	    		  }
	    	},
	    	add:function (el){
	    		this.removeEl(delEl,el);
		    	if(!this.containEl(checkedEl,el) && !this.containEl(addEl,el)){
	    			addEl.push(el)
	    		}
	    	},
	    	del:function (el){
	    		this.removeEl(addEl,el);
	    		if(this.containEl(checkedEl,el) && !this.containEl(delEl,el)){
	    			delEl.push(el)
	    		}
	    	},
	    	getAddEl:function (){
	    		return addEl.join(',');
	    	},
	    	getDelEl:function(){
	    		return delEl.join(',');
	    	},
	    	getCheckedEl:function(){
	    		return checkedEl.join(',');
	    	}
	    };
	}
			// var table = $('#sample_1').DataTable();
	        var relobj = new relation();
			var permissionTable = $('#permissionTable').dataTable(
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
										relobj = new relation();
										var oSettings = permissionTable.fnSettings();
										oSettings.serverparam = $('#searchform').serializeObject();
										oSettings.sDom = '';
										permissionTable.fnFilter('11');
									});
						},
						"fnPreDrawCallback":function(oSettings){
							//alert('初始');
							//relobj = new relation();
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
										if(a.aData['checked']==true){
											relobj.addSelectEl(a.aData['permissionId']);
											//$('#searchform input[name="permissionId"]').val(relobj.getCheckedEl());
										}
										return "<input type='checkbox' {0} class='group-checkable-sub' name='permission' value='{1}' />".format(a.aData['checked']==true?"checked":"",a.aData['permissionId']);
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
						//"sDom":"",
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
			
			var rolesTable = $('#rolesTable').dataTable(
					{
						"bProcessing" : true,
						"bServerSide" : true,
						"bFilter" : true,
						"bLengthChange" : false,
						"sAjaxSource" : "/deep/sysmng/roleList",
						"bStateSave" : true,
						"oFeatures" : {
							'sDom' : ''
						},
						"fnStateLoadParams" : function(oSettings, oData) {
							$("#searchRoleBtn").bind("click",
									function() {
										relobj = new relation();
										var oSettings = rolesTable.fnSettings();
										oSettings.serverparam = $('#searchRoleForm').serializeObject();
										oSettings.sDom = '';
										rolesTable.fnFilter('11');
									});
						},
						"fnPreDrawCallback":function(oSettings){
							//alert('初始');
							//relobj = new relation();
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
										if(a.aData['checked']==true){
											//relobj.addSelectEl(a.aData['permissionId']);
											//$('#searchform input[name="permissionName2"]').val(relobj.getCheckedEl());
										}
										return "<input type='checkbox' {0} class='group-checkable-sub' name='role' value='{1}' />".format(a.aData['checked']==true?"checked":"",a.aData['roleId']);
									},
									"sDefaultContent" : "<input type=\"checkbox\" class=\"group-checkable\" value=\"1\" />"
								}, {
									"sTitle" : "编码",
									"mData" : "roleId",
									"bSortable" : false,
									"sWidth" : 200
								}, {
									"sTitle" : "名称",
									"mData" : "roleName",
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
						// "sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
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

			// 添加资源与权限对应关系  一对一
			$("#save").click(function() {
						var checklist = [];
						$("#rolesTable>tbody>tr>td input[type=checkbox]:checked")
								.each(function(e) {
									checklist.push($(this).val());
						});
						
						if(checklist.length == 0){
							alert('请选择角色');
							return false;
						}
						var roleIds = checklist.join(",");
						//alert(roleIds);
						var selectIds = relobj.getAddEl();
						var delIds = relobj.getDelEl();
						
						$.ajax({
							url:'/deep/sysmng/addRolePerm',
							async:true,
							method:'POST',
							contentType:'application/x-www-form-urlencoded; charset=UTF-8',
							data:'roleId='+roleIds+"&permIds="+selectIds+"&delIds="+delIds,
							dataType : 'json',
							error:function(jqXHR,textStatus,errorThrown ){alert(errorThrown)},
							success:function(resp){
								$("#mySubmit").trigger("click");
								alert('操作成功');
							}
						});
			});

			//权限列表第一个 checkbox 全选 动作
			$("#permissionTable th input:checkbox").on("click",
					function() {
						var that = this;
						$(this).closest("table").find("tr > td:first-child input:checkbox").each(
								function() {
									if (this.disabled) {
										return;
									}else{
									  this.checked = that.checked;
									  $(this).trigger("change");
									}
								});
			});
			//选择权限单选按钮点击
			$(document).off('change.modal-check2').on("change.modal-check2",'.group-checkable-sub[name=permission]', function(){
				var that = this;
				if(that.checked){
					relobj.add($(that).val());
				}else{
					relobj.del($(that).val());
				}
			});
			//选择[角色]单选按钮点击
			$(document).off('change.modal-check').on("change.modal-check",'.group-checkable-sub[name=role]', function(){
				var checked = this.checked;
				//取消其他行被选中
				$(this).closest("table").find("tr > td:first-child input:checkbox").each(
						function() {
							this.checked = false;
				});
				
				if(checked){
					this.checked = true;
					$('#searchform input[name="roleId"]').val($(this).val());
					$("#mySubmit").trigger("click");
				}else{
					//relobj.del($(that).val());
				}
			});
			$(".page-sidebar-menu li[name='系统管理']").addClass("active");
        	$(".page-sidebar-menu li[name='角色-权限']").addClass("active");
        	
		});