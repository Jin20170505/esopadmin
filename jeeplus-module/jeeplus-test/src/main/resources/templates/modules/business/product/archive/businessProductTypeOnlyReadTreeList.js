<script>
		$(document).ready(function() {
			var to = false;
			$('#search_q').keyup(function () {
				if(to) { clearTimeout(to); }
				to = setTimeout(function () {
					var v = $('#search_q').val();
					$('#businessProductTypeOnlyReadjsTree').jstree(true).search(v);
				}, 250);
			});
			$('#businessProductTypeOnlyReadjsTree').jstree({
				'core' : {
					"multiple": false,
                    "animation": 0,
                     "themes": {"icons": true, "stripes": false},
					'data' : {
						"url" : "${ctx}/business/product/archive/businessProductTypeOnlyRead/treeData",
						"dataType" : "json" 
					}
				},
				"conditionalselect" : function (node, event) {
					return false;
				},
				'plugins': ['types', 'wholerow', "search"],
			"types": {
						"default": {
							"icon": "fa fa-folder text-custom"
						},
						"file": {
							"icon": "fa fa-file text-success"
						}
					}

			}).bind("activate_node.jstree", function (obj, e) {
				var node = $('#businessProductTypeOnlyReadjsTree').jstree(true).get_selected(true)[0];
				var opt = {
					silent: true,
					query:{
						'type.id':node.id
					}
				};
				$("#typeId").val(node.id);
				$("#typeName").val(node.text);
				$('#businessProductTable').bootstrapTable('refresh',opt);
			}).on('loaded.jstree', function() {
				$("#businessProductTypeOnlyReadjsTree").jstree('open_all');
			});
		});

		function refreshTree() {
            $('#businessProductTypeOnlyReadjsTree').jstree("refresh");
        }
	</script>