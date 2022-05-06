<script>
		$(document).ready(function() {
			var to = false;
			$('#search_q').keyup(function () {
				if(to) { clearTimeout(to); }
				to = setTimeout(function () {
					var v = $('#search_q').val();
					$('#businessFileTypejsTree').jstree(true).search(v);
				}, 250);
			});
			$('#businessFileTypejsTree').jstree({
				'core' : {
					"multiple": false,
                    "animation": 0,
                     "themes": {"icons": true, "stripes": false},
					'data' : {
						"url" : "${ctx}/business/filemanger/businessFileType/treeData",
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
							"label": "添加下级文件",
							"action": function (data) {
								var inst = jQuery.jstree.reference(data.reference),
									obj = inst.get_node(data.reference);
								jp.openSaveDialog('添加下级文件', '${ctx}/business/filemanger/businessFileType/form/edit?parent.id=' + obj.id + "&parent.name=" + obj.text, '90%', '90%');
							}
						};
						tmp.remove = {
							"label": "删除文件",
							"action": function (data) {
								var inst = jQuery.jstree.reference(data.reference),
									obj = inst.get_node(data.reference);
								jp.confirm('确认要删除文件吗？', function(){
									jp.loading();
									$.get("${ctx}/business/filemanger/businessFileType/delete?id="+obj.id, function(data){
										if(data.success){
											$('#businessFileTypejsTree').jstree("refresh");
											jp.success(data.msg);
										}else{
											jp.error(data.msg);
										}
									})

								});
							}
						}
						tmp.ccp = {
							"label": "编辑文件",
							"action": function (data) {
								var inst = jQuery.jstree.reference(data.reference),
									obj = inst.get_node(data.reference);
								var parentId = inst.get_parent(data.reference);
								var parent = inst.get_node(parentId);
								jp.openSaveDialog('编辑文件', '${ctx}/business/filemanger/businessFileType/form/edit?id=' + obj.id, '90%', '90%');
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
				var node = $('#businessFileTypejsTree').jstree(true).get_selected(true)[0];
				var opt = {
					silent: true,
					query:{
						'type.id':node.id
					}
				};
				$("#typeId").val(node.id);
				$("#typeName").val(node.text);
				$('#bussinessFileMangerTable').bootstrapTable('refresh',opt);
			}).on('loaded.jstree', function() {
				$("#businessFileTypejsTree").jstree('open_all');
			});
		});

		function refreshTree() {
            		$('#businessFileTypejsTree').jstree("refresh");
        	}
	</script>