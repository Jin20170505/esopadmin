<script>
var $businessProductTypeTreeTable;
$(document).ready(function() {
	  $businessProductTypeTreeTable = $('#businessProductTypeTreeTable').bootstrapTreeTable({

		 	type: 'get',                   // 请求方式（*）
            url: "${ctx}/business/product/type/businessProductType/data",            // 请求后台的URL（*）
            ajaxParams : {},               // 请求数据的ajax的data属性
            toolbar: "#toolbar",      //顶部工具条
            expandColumn : 1,
               columns: [{
		        checkbox: true

		    }
			,{
		        field: 'code',
		        title: '分类编号',
		        sortable: true,
		        sortName: 'code'
		        ,formatter:function(value, row , index){
		        	  <% if(shiro.hasPermission("business:product:type:businessProductType:edit") ){ %>
					   if(!value){
						  return "<a  href='#' onclick='edit(\""+row.id+"\")'>-</a>";
					   }else{
						  return "<a  href='#' onclick='edit(\""+row.id+"\")'>"+value+"</a>";
						}
                     <% }else if(shiro.hasPermission("business:product:type:businessProductType:view")){ %>
					   if(!value){
						  return "<a  href='#' onclick='view(\""+row.id+"\")'>-</a>";
                       }else{
                          return "<a  href='#' onclick='view(\""+row.id+"\")'>"+value+"</a>";
                       }
                     <% }else{ %>
					      return value;
					 <% } %>
		         }

		    }
			,{
		        field: 'name',
		        title: '分类名称',
		        sortable: true,
		        sortName: 'name'

		    }
			,{
		        field: 'remarks',
		        title: '备注信息',
		        sortable: true,
		        sortName: 'remarks'

		    }
			,{
			   field: 'operate',
			   title: '操作',
			   align: 'center',
			   class: 'text-nowrap',
			   formatter:  function operateFormatter(value, row, index) {
				   return [
					<% if(shiro.hasPermission("business:product:type:businessProductType:view")){ %>
					   '<a class="view btn btn-icon waves-effect waves-light btn-custom btn-xs m-r-5" onclick="view(\''+row.id+'\')">查看</a>',
				   <% } %>
				   <% if(shiro.hasPermission("business:product:type:businessProductType:edit")){ %>
					   '<a class="edit btn btn-icon waves-effect waves-light btn-success btn-xs m-r-5" onclick="edit(\''+row.id+'\')">编辑</a>',
				   <% } %>
				   <% if(shiro.hasPermission("business:product:type:businessProductType:del")){ %>
					   '<a class="del btn btn-icon waves-effect waves-light btn-danger btn-xs  m-r-5" onclick="del(\''+row.id+'\')">删除</a>',
				   <% } %>
				   <% if(shiro.hasPermission("business:product:type:businessProductType:add") ){ %>
					   '<a class="addChild btn btn-icon waves-effect waves-light btn-primary btn-xs" onclick="addChild(\''+row.id+'\')">添加下级存货分类</a>'
				   <% } %>
				   ].join('');
			   }
		   }
		     ]

		});
		

	});

	//获取选中行
  function getSelections() {
  
        return $("#businessProductTypeTreeTable").bootstrapTreeTable('getSelections');
    }

  //删除
  function del(id){
  
	 jp.confirm('确认要删除该存货分类记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/business/product/type/businessProductType/delete?id=" + id, function(data){
				if(data.success){
					refresh();
					jp.toastr_success(data.msg);
				}else{
					jp.toastr_error(data.msg);
				}
				jp.close(index);
			})

	 })
  }
function sychu8(){
    jp.confirm('确认同步U8数据吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/business/product/type/businessProductType/sychu8", function(data){
				if(data.success){
					refresh();
					jp.toastr_success(data.msg);
				}else{
					jp.toastr_error(data.msg);
				}
				jp.close(index);
			})

	 })
}
    //添加下级
 function addChild(id){//添加下级
    jp.openSaveDialog('添加下级存货分类', '${ctx}/business/product/type/businessProductType/form/add?parent.id='+id,'90%', '90%');
 }
    //刷新列表
  function refresh() {
      $('#businessProductTypeTreeTable').bootstrapTreeTable('refresh');
  }

   //新增表单页面
 function add() {
     jp.openSaveDialog('新增存货分类', "${ctx}/business/product/type/businessProductType/form/add",'90%', '90%');
 }
  //编辑表单页面
  function edit(id){
	  jp.openSaveDialog('编辑存货分类', "${ctx}/business/product/type/businessProductType/form/edit?id="+id,'90%', '90%');
  }
  //查看表单页面
  function view(id) {
      jp.openViewDialog('查看存货分类', "${ctx}/business/product/type/businessProductType/form/view?id="+id,'90%', '90%');
  }

      var _expandFlag_all = false;
    $("#expandAllBtn").click(function(){
        if(_expandFlag_all){
            $businessProductTypeTreeTable.bootstrapTreeTable('expandAll');
        }else{
            $businessProductTypeTreeTable.bootstrapTreeTable('collapseAll');
        }
        _expandFlag_all = _expandFlag_all?false:true;
    })
	function refresh() {
		$businessProductTypeTreeTable.bootstrapTreeTable('refresh');
	}
</script>