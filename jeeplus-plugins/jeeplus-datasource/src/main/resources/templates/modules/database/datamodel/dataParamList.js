<script>
$(document).ready(function() {
	$('#dataParamTable').bootstrapTable({
		 
		  //请求方法
               method: 'post',
               //类型json
               dataType: "json",
               contentType: "application/x-www-form-urlencoded",
				//移动端自适应
				mobileResponsive: true,
			   //显示检索按钮
		       showSearch: true,
				//允许列拖动大小
				resizable: true,
				//固定表头
				stickyHeader: true,
				stickyHeaderOffsetY: 0,
               //显示刷新按钮
               showRefresh: true,
               //显示切换手机试图按钮
               showToggle: false,
               //显示 内容列下拉框
    	       showColumns: true,
    	       //显示到处按钮
    	      showExport: false,
    	       //显示切换分页按钮
    	      showPaginationSwitch: false,
    	       //最低显示2行
    	       minimumCountColumns: 2,
               //是否显示行间隔色
               striped: true,
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
               url: "${ctx}/database/datamodel/dataParam/data",
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
		        field: 'dsid',
		        title: '数据源',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	  <% if(shiro.hasPermission("database:datamodel:dataParam:edit") ){ %>
					   if(!value){
						  return "<a href='#' onclick='edit(\""+row.id+"\")'>-</a>";
					   }else{
						  return "<a href='#' onclick='edit(\""+row.id+"\")'>"+value+"</a>";
						}
                     <% }else if(shiro.hasPermission("database:datamodel:dataParam:view")){ %>
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
		        field: 'field',
		        title: '字段',
		        sortable: true
		       
		    }
			,{
		        field: 'defaultvalue',
		        title: '映射关系',
		        sortable: true
		       
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
					<% if(shiro.hasPermission("database:datamodel:dataParam:view")){ %>
					   '<a href="javascript: return false" class="view" title="查看"> <i class="fa fa-search-plus text-primary m-r-10"></i> </a>',
				   <% } %>
				   <% if(shiro.hasPermission("database:datamodel:dataParam:edit")){ %>
						   '<a href="javascript: return false" class="edit" title="编辑"> <i class="fa fa-pencil text-inverse m-r-10"></i> </a>',
				   <% } %>
				   <% if(shiro.hasPermission("database:datamodel:dataParam:del")){ %>
					   '<a href="javascript: return false" class="del" title="删除"> <i class="fa fa-close text-danger"></i> </a>'
				   <% } %>
				   ].join('');
			   }
		   }
		     ]
		
		});
		

	  $('#dataParamTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#dataParamTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#dataParamTable').bootstrapTable('getSelections').length!=1);
        });

	 $("#import").click(function(){//显示导入面板
            $("#search-collapse").hide();
            $("#import-collapse").fadeToggle()

      })

	 $("#btnImportExcel").click(function(){//导入Excel
		 var importForm = $('#importForm')[0];
		 jp.block('#import-collapse',"文件上传中...");
		 jp.uploadFile(importForm,"${ctx}/database/datamodel/dataParam/import",function (data) {
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
            jp.downloadFile('${ctx}/database/datamodel/dataParam/import/template');
		})

	 $("#export").click(function(){//导出Excel文件
			jp.downloadFile('${ctx}/database/datamodel/dataParam/export');
	  })

	  $("#search").click("click", function() {// 绑定查询按扭
          jp.block('#search-collapse',"查询中...");
		  refresh();
          jp.unblock('#search-collapse', 200);

		});

	 $("#reset").click("click", function() { //绑定重置按钮
          jp.block('#search-collapse',"查询中...");
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  refresh();
          jp.unblock('#search-collapse', 200);
		});

		
	});

	//获取选中行
  function getIdSelections() {
        return $.map($("#dataParamTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

  //删除
  function del(ids){
     if(!ids){
          ids = getIdSelections();
     }
	 jp.confirm('确认要删除该数据模型参数记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/database/datamodel/dataParam/delete?ids=" + ids, function(data){
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
      $('#dataParamTable').bootstrapTable('refresh');
  }

   //新增表单页面
 function add() {
     jp.openSaveDialog('新增数据模型参数', "${ctx}/database/datamodel/dataParam/form/add",'80%', '70%');
 }
  //编辑表单页面
  function edit(id){
      if(!id){
          id = getIdSelections();
      }
	  jp.openSaveDialog('编辑数据模型参数', "${ctx}/database/datamodel/dataParam/form/edit?id="+id,'80%', '70%');
  }
  //查看表单页面
  function view(id) {
      if(!id){
          id = getIdSelections();
      }
      jp.openViewDialog('查看数据模型参数', "${ctx}/database/datamodel/dataParam/form/view?id="+id,'80%', '70%');
  }
</script>