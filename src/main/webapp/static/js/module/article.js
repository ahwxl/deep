            $(document).ready(function () {
                //var table = $('#sample_1').DataTable();
            	var mygridtab = $('#transactions').dataTable({
                	"bProcessing": true,
                    "bServerSide": true,
                    "bFilter":true,
                    "bLengthChange":false,
                    "sAjaxSource": "/deep/art/articleList",
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
                      { "sTitle": "编号","mData":"id","bSortable": false,"sWidth":30},
                      { "sTitle": "标题","mData":"title","bSortable": false },
                      { "sTitle": "修改日期","mData":"gmtModify","bSortable": false,"sWidth":130 },
                      { "sTitle": "操作","mData":"gmtCreate","bSortable": false }
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
                            fnRender: function (setobj, data) {
                            	var delhtml = "<a class='mini purple' id='{0}' data-toggle='delete' ><i class='icon-trash'></i> {1}</a>&nbsp;<a class='mini purple' id='{0}' data-toggle='edit' ><i class='icon-edit'></i> {2}</a>&nbsp;<a class='mini purple' id='{0}' data-toggle='view' ><i class='icon-view'></i> {3}</a>".format(setobj.aData['id'],"删除","修改","预览");
                            	return delhtml;
                            }
                        }
                    ]
                });
            	
            	//动态创建的元素 通过绑定到 document
            	$(document).off('click.modal').on('click.modal.data-api', '[1data-toggle^="modal"]', function ( e ) {
            		window.location.href = "/deep/art/createArticlePage";
                });
            	//删除资源
            	$(document).off('click.modal2').on('click.modal2.data-api', '[data-toggle="delete"]', function ( e ) {
            		var id = $(this).attr('id');
            		if(confirm("确认删除")){
            			$.post("/deep/art/delArticle",
                				"id="+id,
                			function(data){
                			   mygridtab.fnDraw();
                			   alert(data);
                		    }
                		);
            		}
            	});
            	
            	//修改资源
            	$(document).off('click.modal3').on('click.modal3.data-api', '[data-toggle="edit"]', function ( e ) {
            		var id = $(this).attr('id');
            		window.location.href = "/deep/art/modifyArticlePage?id="+id;
            	});
            	
            	$(".page-sidebar-menu li[name='内容管理']").addClass("active");
            	$(".page-sidebar-menu li[name='文章管理']").addClass("active");
            });