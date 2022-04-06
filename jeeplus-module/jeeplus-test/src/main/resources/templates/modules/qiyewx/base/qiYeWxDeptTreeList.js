<script>
		$(document).ready(function() {
			var to = false;
			$('#search_q').keyup(function () {
				if(to) { clearTimeout(to); }
				to = setTimeout(function () {
					var v = $('#search_q').val();
					$('#qiYeWxDeptjsTree').jstree(true).search(v);
				}, 250);
			});
			$('#qiYeWxDeptjsTree').jstree({
				'core' : {
					"multiple": false,
                    "animation": 0,
                     "themes": {"icons": true, "stripes": false},
					'data' : {
						"url" : "${ctx}/qiyewx/base/qiYeWxDept/treeData",
						"dataType" : "json" 
					}
				},
				"conditionalselect" : function (node, event) {
					return false;
				},
				'plugins': ["contextmenu", 'types', 'wholerow', "search"],
				"contextmenu": {
					"items": function (node) {
						var tmp = $.jstree.defaults.contextmenu.items();
						delete tmp.create.action;
						delete tmp.rename.action;
						tmp.rename = null;
						tmp.create = {
							"label": "添加下级部门员工",
							"action": function (data) {
								var inst = jQuery.jstree.reference(data.reference),
									obj = inst.get_node(data.reference);
								jp.openSaveDialog('添加下级部门员工', '${ctx}/qiyewx/base/qiYeWxDept/form/edit?parent.id=' + obj.id + "&parent.name=" + obj.text, '80%', '70%');
							}
						};
						tmp.remove = {
							"label": "删除部门员工",
							"action": function (data) {
								var inst = jQuery.jstree.reference(data.reference),
									obj = inst.get_node(data.reference);
								jp.confirm('确认要删除部门员工吗？', function(){
									jp.loading();
									$.get("${ctx}/qiyewx/base/qiYeWxDept/delete?id="+obj.id, function(data){
										if(data.success){
											$('#qiYeWxDeptjsTree').jstree("refresh");
											jp.success(data.msg);
										}else{
											jp.error(data.msg);
										}
									})

								});
							}
						}
						tmp.ccp = {
							"label": "编辑部门员工",
							"action": function (data) {
								var inst = jQuery.jstree.reference(data.reference),
									obj = inst.get_node(data.reference);
								var parentId = inst.get_parent(data.reference);
								var parent = inst.get_node(parentId);
								jp.openSaveDialog('编辑部门员工', '${ctx}/qiyewx/base/qiYeWxDept/form/edit?id=' + obj.id, '80%', '70%');
							}
						}
						return tmp;
					}

				},
			"types": {
						"default": {
							"icon": "fa fa-folder text-custom"
						},
						"file": {
							"icon": "fa fa-file text-success"
						}
					}

			}).bind("activate_node.jstree", function (obj, e) {
				var node = $('#qiYeWxDeptjsTree').jstree(true).get_selected(true)[0];
				var opt = {
					silent: true,
					query:{
						'mainDept.id':node.id
					}
				};
				$("#mainDeptId").val(node.id);
				$("#mainDeptName").val(node.text);
				$('#qiYeWxEmployeeTable').bootstrapTable('refresh',opt);
			}).on('loaded.jstree', function() {
				$("#qiYeWxDeptjsTree").jstree('open_all');
			});
		});

		function refreshTree() {
            		$('#qiYeWxDeptjsTree').jstree("refresh");
        	}
	</script>