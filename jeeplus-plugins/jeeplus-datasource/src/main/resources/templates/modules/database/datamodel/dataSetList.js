<script>
$(document).ready(function() {
	$('#dataSetTable').bootstrapTable({
		 
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
				rightFixedColumns: true, //右侧冻结列
				rightFixedNumber: 1,
               //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据  
               url: "${ctx}/database/datamodel/dataSet/data",
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
		        field: 'name',
		        title: '数据源名称',
		        sortable: true,
					   formatter:function(value, row , index){
                       <% if(shiro.hasPermission("database:datamodel:dataSet:edit") ){ %>
                               if(!value){
                                   return "<a href='${ctx}/database/datamodel/dataSet/form/edit?id="+row.id+"' >-</a>";
                               }else{
                                   return "<a href='${ctx}/database/datamodel/dataSet/form/edit?id="+row.id+"' >"+value+"</a>";
                               }
                           <% }else if(shiro.hasPermission("database:datamodel:dataSet:view")){ %>
                               if(!value){
                                   return "<a href='${ctx}/database/datamodel/dataSet/form/view?id="+row.id+"' >-</a>";
                               }else{
                                   return "<a href='${ctx}/database/datamodel/dataSet/form/view?id="+row.id+"'>"+value+"</a>";
                               }
                           <% }else{ %>
                               return value;
                           <% } %>
                       }


                   },{
                       field: 'db.name',
                       title: '目标数据库',
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
					<% if(shiro.hasPermission("database:datamodel:dataSet:view")){ %>
					   '<a class="view btn btn-xs btn-custom waves-effect waves-light m-r-5" title="查看"><i class="fa fa-search"></i></a>',
				   <% } %>
				   <% if(shiro.hasPermission("database:datamodel:dataSet:edit")){ %>
						   '<a  class="edit btn btn-xs btn-success waves-effect waves-light m-r-5" title="编辑"><i class="fa fa-pencil"></i></a>',
				   <% } %>
				   <% if(shiro.hasPermission("database:datamodel:dataSet:del")){ %>
					   '<a class="del btn btn-xs btn-danger waves-effect waves-light" title="删除"><i class="fa fa-trash-o"></i></a>'
				   <% } %>
				   ].join('');
			   }
		   }
		     ]
		
		});
		

	  $('#dataSetTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#dataSetTable').bootstrapTable('getSelections').length);
            $('#interface, #edit').prop('disabled', $('#dataSetTable').bootstrapTable('getSelections').length!=1);
        });
		  
	 $("#import").click(function(){//显示导入面板
            $("#search-collapse").hide();
            $("#import-collapse").fadeToggle()

      })

	 $("#btnImportExcel").click(function(){//导入Excel
		 var importForm = $('#importForm')[0];
		 jp.block('#import-collapse',"文件上传中...");
		 jp.uploadFile(importForm,"${ctx}/database/datamodel/dataSet/import",function (data) {
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
            jp.downloadFile('${ctx}/database/datamodel/dataSet/import/template');
		})

	 $("#export").click(function(){//导出Excel文件
			jp.downloadFile('${ctx}/database/datamodel/dataSet/export');
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


    $(window).resize(function () {
        $('#dataSetTable').bootstrapTable('resetView');//请填入你页面对应的TABLE ID
    });
		
	});

	//获取选中行
  function getIdSelections() {
        return $.map($("#dataSetTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  //删除
  function del(ids){
     if(!ids){
          ids = getIdSelections();
     }
	 jp.confirm('确认要删除该数据模型记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/database/datamodel/dataSet/delete?ids=" + ids, function(data){
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
      $('#dataSetTable').bootstrapTable('refresh');
  }

   //新增表单页面
 function add() {
     jp.go( "${ctx}/database/datamodel/dataSet/form/add" );
 }


//获取数据接口
function getInterface() {
	var id = getIdSelections();
	jp.alert(
		"json格式接口url: /database/datamodel/dataSet/getData/"+id+"/json<br/>"+
		"xml格式接口url: /database/datamodel/dataSet/getData/"+id+"/xml<br/>"+
		"table格式接口url：/database/datamodel/dataSet/getData/"+id+"/html<br/><br/>"+
		"接口传递参数说明: ${'$'}{ctx}/接口url?参数名=参数值, 如果不传递参数，则使用默认值<br/>"

	);
}
  //编辑表单页面
  function edit(id){
      if(!id){
          id = getIdSelections();
      }
	  jp.go( "${ctx}/database/datamodel/dataSet/form/edit?id=" + id);
  }
  //查看表单页面
  function view(id) {
      if(!id){
          id = getIdSelections();
      }
      jp.go( "${ctx}/database/datamodel/dataSet/form/view?id=" + id);
  }
</script>