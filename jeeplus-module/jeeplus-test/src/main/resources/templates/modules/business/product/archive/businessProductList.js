<script>
$(document).ready(function() {
	$('#businessProductTable').bootstrapTable({
		 
		  //请求方法
               method: 'post',
               //类型json
               dataType: "json",
               contentType: "application/x-www-form-urlencoded",
               //移动端自适应
               mobileResponsive: true,
               //允许列拖动大小
               resizable: true,
               //固定表头
               stickyHeader: true,
               stickyHeaderOffsetY: 0,
			   //显示检索按钮
		       showSearch: true,
               //显示刷新按钮
               showRefresh: true,
               //显示切换手机试图按钮
               showToggle: true,
               //显示 内容列下拉框
    	       showColumns: true,
    	       //显示到处按钮
    	       showExport: false,
    	       //显示切换分页按钮
    	       showPaginationSwitch: false,
    	       //最低显示2行
    	       minimumCountColumns: 2,
               //是否显示行间隔色
               striped: false,
               //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性(*)
               cache: false,    
               //是否显示分页(*)
               pagination: true,
               //分页方式: client客户端分页，server服务端分页(*)
               sidePagination: "server",
                //排序方式 
               sortOrder: "asc",  
               //初始化加载第一页，默认第一页
               pageNumber:1,   
               //每页的记录行数(*)
               pageSize: 10,  
               //可供选择的每页的行数(*)
               pageList: [10, 25, 50, 100],
               //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据  
               url: "${ctx}/business/product/archive/businessProduct/data",
               //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
               //queryParamsType:'',   
               ////查询参数,每次调用是会带上这个参数，可自定义                         
               queryParams : function(params) {
               	var searchParam = $("#searchForm").serializeJSON();
               	searchParam.pageNo = params.limit === undefined? "1" :params.offset/params.limit+1;
               	searchParam.pageSize = params.limit === undefined? -1 : params.limit;
               	if(params.sort && params.order){
                    searchParam.orderBy = params.sort+ " "+  params.order;
                 }
                 return searchParam;
               },
               onShowSearch: function () {
               	 $("#import-collapse").hide();
				 $("#search-collapse").fadeToggle();
               },
               columns: [{
		        checkbox: true
		       
		    }
			,{
		        field: 'type.name',
		        title: '存货分类',
		        sortable: true,
		        sortName: 'type.name'
		        ,formatter:function(value, row , index){
		             <% if(shiro.hasPermission("business:product:archive:businessProduct:edit") ){ %>
					   if(!value){
						  return "<a href='#' onclick='edit(\""+row.id+"\")'>-</a>";
					   }else{
						  return "<a href='#' onclick='edit(\""+row.id+"\")'>"+value+"</a>";
					   }
                     <% }else if(shiro.hasPermission("business:product:archive:businessProduct:view")){ %>
					   if(!value){
						  return "<a href='#' onclick='view(\""+row.id+"\")'>-</a>";
					   }else{
                          return "<a href='#' onclick='view(\""+row.id+"\")'>"+value+"</a>";
					   }
                     <% }else{ %>
					      return value;
					 <% } %>
		        	
		        }
		       
		    }
			,{
		        field: 'code',
		        title: '存货编码',
		        sortable: true,
		        sortName: 'code'
		       
		    }
			,{
		        field: 'name',
		        title: '存货名称',
		        sortable: true,
		        sortName: 'name'
		       
		    }
			,{
		        field: 'specification',
		        title: '规格型号',
		        sortable: true,
		        sortName: 'specification'
		       
		    }
			,{
		        field: 'unit',
		        title: '计量单位',
		        sortable: true,
		        sortName: 'unit'
		       
		    }
            ,{
            field: 'daynum',
            title: '日产量',
            sortable: true,
            sortName: 'daynum'
            },{
                field: 'cbprice',
                title: '成本价',
                sortable: true,
                sortName: 'cbprice'
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
			   events: {
				   'click .view': function (e, value, row, index) {
					   view(row.id);
				   },
				   'click .edit': function (e, value, row, index) {
					   edit(row.id)
				   },
				   'click .del': function (e, value, row, index) {
					   del(row.id);

				   }
			   },
			   formatter:  function operateFormatter(value, row, index) {
				   return [
					<% if(shiro.hasPermission("business:product:archive:businessProduct:view")){ %>
					   '<a class="view btn btn-icon waves-effect waves-light btn-custom btn-xs m-r-5"> <i class="fa fa-search"></i></a>',
				   <% } %>
				   <% if(shiro.hasPermission("business:product:archive:businessProduct:edit")){ %>
					   '<a class="edit btn btn-icon waves-effect waves-light btn-success btn-xs m-r-5"> <i class="fa fa-pencil"></i></a>',
				   <% } %>
				   <% if(shiro.hasPermission("business:product:archive:businessProduct:del")){ %>
					   '<a class="del btn btn-icon waves-effect waves-light btn-danger btn-xs"> <i class="fa fa-trash-o"></a>'
				   <% } %>
				   ].join('');
			   }
		   }
		     ]
		
		});
		

	  
	  $('#businessProductTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#businessProductTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#businessProductTable').bootstrapTable('getSelections').length!=1);
        });

	 $("#import").click(function(){//显示导入面板
            $("#search-collapse").hide();
            $("#import-collapse").fadeToggle()

      })

	 $("#btnImportExcel").click(function(){//导入Excel
		 var importForm = $('#importForm')[0];
		 jp.block('#import-collapse',"文件上传中...");
		 jp.uploadFile(importForm,"${ctx}/business/product/archive/businessProduct/import",function (data) {
			 if(data.success){
				 jp.toastr_success(data.msg);
				 refresh();
			 }else{
				 jp.toastr_error(data.msg);
			 }
			 jp.unblock('#import-collapse',200);
		 })
	  })

	 $("#btnDownloadTpl").click(function(){//下载模板文件
            jp.downloadFile('${ctx}/business/product/archive/businessProduct/import/template');
		})

	 $("#export").click(function(){//导出Excel文件
	        var searchParam = $("#searchForm").serializeJSON();
	        searchParam.pageNo = 1;
	        searchParam.pageSize = -1;
            var sortName = $('#businessProductTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#businessProductTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/business/product/archive/businessProduct/export?'+values);
	  })

	  $("#search").click("click", function() {// 绑定查询按扭
		  refresh();
		});

	 $("#reset").click("click", function() { //绑定重置按钮
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  refresh();
		});

		
	});

	//获取选中行
  function getIdSelections() {
        return $.map($("#businessProductTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

  //删除
  function del(ids){
     if(!ids){
          ids = getIdSelections();
     }
	 jp.confirm('确认要删除该存货档案记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/business/product/archive/businessProduct/delete?ids=" + ids, function(data){
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
		jp.get("${ctx}/business/product/archive/businessProduct/sychu8", function(data){
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

    //刷新列表
  function refresh() {
      $('#businessProductTable').bootstrapTable('refresh');
  }

   //新增表单页面
 function add() {
    var typeid = $('#typeId').val();
    var typename = $('#typeName').val();
     jp.openSaveDialog('新增存货档案', "${ctx}/business/product/archive/businessProduct/form/add?type.id="+typeid+"&type.name="+typename,'90%', '90%');
 }
  //编辑表单页面
  function edit(id){
      if(!id){
          id = getIdSelections();
      }
	  jp.openSaveDialog('编辑存货档案', "${ctx}/business/product/archive/businessProduct/form/edit?id="+id,'90%', '90%');
  }
  //查看表单页面
  function view(id) {
      if(!id){
          id = getIdSelections();
      }
      jp.openViewDialog('查看存货档案', "${ctx}/business/product/archive/businessProduct/form/view?id="+id,'90%', '90%');
  }
</script>