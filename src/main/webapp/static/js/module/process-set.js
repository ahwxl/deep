            $(document).ready(function () {
            	var mygridtab = $('#transactions').dataTable({
                	"bProcessing": true,
                    "bServerSide": true,
                    "bFilter":true,
                    "bLengthChange":false,
                    "sAjaxSource": cxt+"/bpm/processDefineList",
               		"bStateSave": true,
               		"oFeatures":{'sDom':''},
           		    "fnStateLoadParams": function (oSettings, oData) {
           		    	$("#mySubmit").bind("click", function(){
           		    		var oSettings = mygridtab.fnSettings();
           		    		oSettings.serverparam=$('#searchform').serializeObject();
           		    		oSettings.sDom='';
           		    		mygridtab.fnFilter('11');
           		    	});
           		    },
               		"aoSearchCols": [
               		     		           null,
               		     		           { "sSearchaa": "My filter" },
               		     		           null,
               		     		           { "groupId": "^[0-9]", "bEscapeRegex": false }
               		     		  ],
                    "aoColumns": [
                      { "bSortable": false,"sWidth":20,"sSortDataType": "dom-checkbox","sDefaultContent":"<input type=\"checkbox\" class=\"checkboxes\" value=\"1\" />" },
                      { "sTitle": "编号","mData":"processDefinedId","bSortable": false,"sWidth":100},
                      { "sTitle": "名称","mData":"name","bSortable": false,"sWidth":100},
                      { "sTitle": "描述","mData":"key","bSortable": false,"sWidth":100 },
                      { "sTitle": "创建日期","mData":"gmtCreate","bSortable": false,"sWidth":150 },
                      { "sTitle": "操作","mData":"gmtModify","bSortable": false }
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
                        'bSortable': false,"sSortDataType": "dom-checkbox",
                        'aTargets': [0]
                    },{
                            'bSortable': false,
                            'aTargets': [5],
                            fnRender: function (setobj, data) {
                            	var delhtml = "<a class='mini purple' id='{0}' data-toggle='delete' ><i class='icon-trash'></i> {1}</a>&nbsp;<a class='mini purple' id='{0}' data-toggle='edit' ><i class='icon-edit'></i> {2}</a>&nbsp;<a class='mini purple' id='{0}' data-toggle='set' ><i class='icon-edit'></i> {3}</a>&nbsp;<a class='mini purple' id='{0}' data-toggle='reStart' ><i class='icon-edit'></i> {4}</a>".format(setobj.aData['processDefinedId'],"删除","修改","设置","启动");
                            	return delhtml;
                            }
                        }
                    ]
                });
            	
            	function sysNotify(info){

            		var unique_id = $.gritter.add({
            					                    position: 'bottom-right',
            					                    title: '系统通知!',
            					                    text: info,
            					                    sticky: false,
            					                    time: '2000',
            					                    //class_name: 'my-sticky-class'
            					                    class_name: 'gritter-success'
            		 });
            	}
            	
                //var table = $('#sample_1').DataTable();
            	//动态创建的元素 通过绑定到 document
            	$(document).off('click.modal').on('click.modal.data-api', '[1data-toggle^="deploy"]', function ( e ) {
                	var procDefId = $(this).attr('procDefId');
                	$('#myform')[0].reset();
                	
                	$("#myform").ajaxForm({
                		dataType:'json',
                		error:function (){alert("操作失败，请联系管理员")},
                		success:function(responseTxt){
                		  alert(responseTxt);
                		},
                		type:'POST',
                		url:cxt+'/bpm/deployProcess'
                	});
                	
                    $('#stack1').modal({
                    	confirm:function(formvalue){
                    		$("#myform").trigger("submit");
                    	}
                    });
                });
            	//删除资源
            	$(document).off('click.modal2').on('click.modal2.data-api', '[data-toggle="delete"]', function ( e ) {
            		var id = $(this).attr('id');
            		if(confirm('确认删除')){
            			$.post(cxt+"/form/delForm",
                				"formId="+id,
                			function(data){
                			   mygridtab.fnDraw();
                		    }
                		);
            		}
            	});
            	//修改资源
            	$(document).off('click.modal3').on('click.modal3.data-api', '[data-toggle="edit"]', function ( e ) {
            		var id = $(this).attr('id');
            		window.location.href = cxt+"/form/updateForm?formId="+id;
            	});
            	//跳转流程设置page
            	$(document).off('click.modal4').on('click.modal4.data-api', '[data-toggle="set"]', function ( e ) {
            		var id = $(this).attr('id');
            		window.location.href = cxt+"/bpm/processDefineSetPage?processDefinedId="+id;
            	});
            	
            	//跳转流程启动page
            	$(document).off('click.modal5').on('click.modal5.data-api', '[data-toggle="reStart"]', function ( e ) {
            		var id = $(this).attr('id');
            		window.location.href = cxt+"/bpm/createProcessInstance?processDefinedId="+id;
            	});
            	
            	//table checkbox全选
            	jQuery('#transactions .group-checkable').change(function () {
                    var set = jQuery(this).attr("data-set");
                    var checked = jQuery(this).is(":checked");
                    jQuery(set).each(function () {
                        if (checked) {
                            $(this).attr("checked", true);
                        } else {
                            $(this).attr("checked", false);
                        }
                    });
                    jQuery.uniform.update(set);
                });
            	
            	$("#myform").ajaxForm({
            		dataType:'json',
            		error:function (){sysNotify("操作失败，请联系管理员")},
            		success:function(responseTxt){
            		  sysNotify(responseTxt.responseMsg);
            		},
            		type:'POST',
            		url:cxt+'/bpm/processDefineSet'
            	});
            	
            	
            	// 系统资源树展示
    			var setting = {
    				/*check : {
    					enable : true,
    					chkboxType: { "Y": "p", "N": "ps" }
    				},*/
    				data: {
    					 simpleData: {
    						enable: true,
    						idKey: "formId",
    						pIdKey: "parentFormId",
    						rootPId: 0,
    					 },
    					 key:{
    						 name:"formName",
    						 url:"#"
    					 }
    				},
    				view : {
    					dblClickExpand : false,
    					showLine : true,
    					selectedMulti : false
    				},
    				async : {
    					enable : true,
    					url : cxt+"/form/formItem",
    					autoParam : [ "id=formId", "name=n", "level=lv" ],
    					otherParam : {
    						"otherParam" : "zTreeAsyncTest"
    					},
    					dataFilter : filter
    				},
    				callback: {
    							beforeCheck : function zTreeBeforeCheck(treeId,treeNode) {
    								var treeObj = $.fn.zTree
    										.getZTreeObj("formTree");
    								if (!treeNode.checked) {
    									treeObj.checkAllNodes(false);
    								}
    							},
    							onCheck: onClick,
    							onClick: onClick
    				}
    			};
    			
    			function onClick(event, treeId, treeNode, clickFlag){
    				event.stopPropagation();
    				$('#myform input[name=formId]').val(treeNode.formId);
    				//$("#mySubmit").trigger("click");
    			}
    			
    			function hideMenu() {
    				$("#menuContent").fadeOut("fast");
    				$("body").unbind("mousedown", onBodyDown);
    			}
    			
    			$("#formId").click(function () {
    				var cityObj = $("#formId");
    				var cityOffset = $("#formId").offset();
    				$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

    				$("body").bind("mousedown", onBodyDown);
    			});
    			
    			function onBodyDown(event) {
    				if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
    					hideMenu();
    				}
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
    			$.fn.zTree.init($("#formTree"), setting);
            	
            	
            	$(".page-sidebar-menu li[name='流程管理']").addClass("active");
            	$(".page-sidebar-menu li[name='流程定义']").addClass("active");
            });