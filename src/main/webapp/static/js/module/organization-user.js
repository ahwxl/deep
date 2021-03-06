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
			var userTable = $('#userTable').dataTable(
					{
						"bProcessing" : true,
						"bServerSide" : true,
						"bFilter" : true,
						"bLengthChange" : false,
						"sAjaxSource" : "/deep/sysmng/userList",
						"bStateSave" : true,
						"oFeatures" : {
							'sDom' : ''
						},
						"fnStateLoadParams" : function(oSettings, oData) {
							$("#mySubmit").bind(
									"click",
									function() {
										relobj = new relation();
										var oSettings = userTable.fnSettings();
										oSettings.serverparam = $('#searchform').serializeObject();
										oSettings.sDom = '';
										userTable.fnFilter('11');
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
											relobj.addSelectEl(a.aData['userId']);
											//$('#searchform input[name="permissionName2"]').val(relobj.getCheckedEl());
										}
										return "<input type='checkbox' {0} class='group-checkable-sub' value='{1}' />".format(a.aData['checked']==true?"checked":"",a.aData['userId']);
									},
									"sDefaultContent" : "<input type=\"checkbox\" class=\"group-checkable\" value=\"1\" />"
								}, {
									"sTitle" : "编码",
									"mData" : "userId",
									"bSortable" : false,
									"sWidth" : 200
								}, {
									"sTitle" : "名称",
									"mData" : "userName",
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

			// 系统资源树展示
			var setting = {
				check : {
					enable : true,
					chkboxType: { "Y": "p", "N": "ps" }
				},
				data: {
					simpleData: {
						enable: true,
						idKey: "organizationId",
						pIdKey: "parentOrgId",
						rootPId: null,
					},
					 key:{
						  url:"#",
						  name:"organizationName"
					 }
				},
				view : {
					dblClickExpand : false,
					showLine : true,
					selectedMulti : false
				},
				async : {
					enable : true,
					url : "/deep/sysmng/queryOrgTree",
					autoParam : [ "id=parentResourceId", "name=n", "level=lv" ],
					otherParam : {
						"otherParam" : "zTreeAsyncTest"
					},
					dataFilter : filter
				},
				callback: {
							beforeCheck : function zTreeBeforeCheck(treeId,treeNode) {
								var treeObj = $.fn.zTree
										.getZTreeObj("organizationTree");
								if (!treeNode.checked) {
									treeObj.checkAllNodes(false);
								}
							},
							onCheck: onClick
				}
			};
			
			function onClick(event, treeId, treeNode, clickFlag){
				event.stopPropagation();
				$('#searchform input[name="organizationId"]').val(treeNode.organizationId);
				$("#mySubmit").trigger("click");
			}

			function filter(treeId, parentNode, childNodes) {
				if (!childNodes)
					return null;
				for (var i = 0, l = childNodes.length; i < l; i++) {
					//childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
				}
				return childNodes;
			}

			//初始化资源树
			$.fn.zTree.init($("#organizationTree"), setting);

			// 添加资源与权限对应关系  一对一
			$("#save").click(function() {
				
						var selectIds = relobj.getAddEl();
						var delIds = relobj.getDelEl();
						
						var treeObj = $.fn.zTree.getZTreeObj("organizationTree");
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
						var orgId = leafNodes[0].organizationId;
						
						$.ajax({
							url:'/deep/sysmng/addOrgUser',
							async:true,
							method:'POST',
							contentType:'application/x-www-form-urlencoded; charset=UTF-8',
							data:'organizationId='+orgId+"&userIds="+selectIds+"&delIds="+delIds,
							dataType : 'json',
							error:function(jqXHR,textStatus,errorThrown ){alert(errorThrown)},
							success:function(resp){
								alert('success');
							}
						});
			});

			//权限列表第一个 checkbox 全选 动作
			$("#userTable th input:checkbox").on("click",
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
			$(document).off('change.modal-check').on("change.modal-check",".group-checkable-sub", function(){
				var that = this;
				if(that.checked){
					relobj.add($(that).val());
				}else{
					relobj.del($(that).val());
				}
			});
			$(".page-sidebar-menu li[name='系统管理']").addClass("active");
        	$(".page-sidebar-menu li[name='机构-用户']").addClass("active");
        	
		});