<script>
$(document).ready(function() {
	$('#dataSourceTable').bootstrapTable({
		 
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
				leftFixedColumns: true,  //左侧冻结列
				leftFixedNumber: 2,
				rightFixedColumns: true, //右侧冻结列
				rightFixedNumber: 1,
               //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据  
               url: "${ctx}/database/datalink/dataSource/data",
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
		        title: '连接名称',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	  <% if(shiro.hasPermission("database:datalink:dataSource:edit") ){ %>
					   if(!value){
						  return "<a href='#' onclick='edit(\""+row.id+"\")'>-</a>";
					   }else{
						  return "<a href='#' onclick='edit(\""+row.id+"\")'>"+value+"</a>";
						}
                     <% }else if(shiro.hasPermission("database:datalink:dataSource:view")){ %>
					   if(!value){
						  return "<a href='#' onclick='view(\""+row.id+"\")'>-</a>";
                       }else{
                          return "<a href='#' onclick='view(\""+row.id+"\")'>"+value+"</a>";
                       }
                     <% }else{ %>
					      return value;
					 <% } %>
		         }
		       
		    },{
                       field:'enName',
                       title:'连接英文名',
                       sortable: true

                   }
			,{
		        field: 'type',
		        title: '数据库类型',
		        sortable: true,
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fn.toJson(fn.getDictList('db_type'))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'host',
		        title: '主机地址',
		        sortable: true
		       
		    }
			,{
		        field: 'port',
		        title: '端口',
		        sortable: true
		       
		    }
			,{
		        field: 'dbname',
		        title: '数据库名',
		        sortable: true
		       
		    }
			,{
		        field: 'username',
		        title: '数据库用户名',
		        sortable: true
		       
		    }
			,{
		        field: 'password',
		        title: '数据库密码',
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
					<% if(shiro.hasPermission("database:datalink:dataSource:view")){ %>
					   '<a href="javascript: return false" class="view btn btn-xs btn-custom waves-effect waves-light m-r-5"><i class="fa fa-search"></i></a>',
				   <% } %>
				   <% if(shiro.hasPermission("database:datalink:dataSource:edit")){ %>
						   '<a href="javascript: return false" class="edit btn btn-xs btn-success waves-effect waves-light m-r-5"><i class="fa fa-pencil"></i></a>',
				   <% } %>
				   <% if(shiro.hasPermission("database:datalink:dataSource:del")){ %>
					   '<a href="javascript: return false" class="del btn btn-xs btn-danger waves-effect waves-light" ><i class="fa fa-trash-o"></i></a>'
				   <% } %>
				   ].join('');
			   }
		   }
		     ]
		
		});
		
	  $('#dataSourceTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#dataSourceTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#dataSourceTable').bootstrapTable('getSelections').length!=1);
        });

	 $("#import").click(function(){//显示导入面板
            $("#search-collapse").hide();
            $("#import-collapse").fadeToggle()

      })

	 $("#btnImportExcel").click(function(){//导入Excel
		 var importForm = $('#importForm')[0];
		 jp.block('#import-collapse',"文件上传中...");
		 jp.uploadFile(importForm,"${ctx}/database/datalink/dataSource/import",function (data) {
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
            jp.downloadFile('${ctx}/database/datalink/dataSource/import/template');
		})

	 $("#export").click(function(){//导出Excel文件
			jp.downloadFile('${ctx}/database/datalink/dataSource/export');
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
        return $.map($("#dataSourceTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

  //删除
  function del(ids){
     if(!ids){
          ids = getIdSelections();
     }
	 jp.confirm('确认要删除该数据库连接记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/database/datalink/dataSource/delete?ids=" + ids, function(data){
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
      $('#dataSourceTable').bootstrapTable('refresh');
  }

   //新增表单页面
 function add() {
     jp.open({
         type: 2,
         area: ['900px', '500px'],
         title: '新建数据库连接',
         auto:true,
         maxmin: true, //开启最大化最小化按钮
         btnAlign: 'c',
         content: "${ctx}/database/datalink/dataSource/form/add" ,
         btn: ['测试连接','确定', '关闭'],
		 btn1: function(index, layero){
             var iframeWin = layero.find('iframe')[0]; //得到弹出的窗口对象，执行窗口内iframe页的方法：iframeWin.method();
             iframeWin.contentWindow.testDbLink();//调用保存事件，在 弹出页内，需要定义save方法。处理保存事件。
         	return false;
		 },
         btn2: function(index, layero){
             var iframeWin = layero.find('iframe')[0]; //得到弹出的窗口对象，执行窗口内iframe页的方法：iframeWin.method();
             iframeWin.contentWindow.save();//调用保存事件，在 弹出页内，需要定义save方法。处理保存事件。
			 return false;
         },
         cancel: function(index){
         }
     });
 }
  //编辑表单页面
  function edit(id){
      if(!id){
          id = getIdSelections();
      }
      jp.open({
          type: 2,
          area: ['900px', '500px'],
          title: '编辑数据库连接',
          auto:true,
          maxmin: true, //开启最大化最小化按钮
          btnAlign: 'c',
          content: "${ctx}/database/datalink/dataSource/form/edit?id="+id,
          btn: ['测试连接','确定', '关闭'],
          btn1: function(index, layero){
              var iframeWin = layero.find('iframe')[0]; //得到弹出的窗口对象，执行窗口内iframe页的方法：iframeWin.method();
              iframeWin.contentWindow.testDbLink();//调用保存事件，在 弹出页内，需要定义save方法。处理保存事件。
              return false;
          },
          btn2: function(index, layero){
              var iframeWin = layero.find('iframe')[0]; //得到弹出的窗口对象，执行窗口内iframe页的方法：iframeWin.method();
              iframeWin.contentWindow.save();//调用保存事件，在 弹出页内，需要定义save方法。处理保存事件。
              return false;
          },
          cancel: function(index){
          }
      });
  }
  //查看表单页面
  function view(id) {
      if(!id){
          id = getIdSelections();
      }
      jp.openViewDialog('查看数据库连接', "${ctx}/database/datalink/dataSource/form/view?id="+id,'900px', '500px');
  }
</script>