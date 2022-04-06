<script>
$(document).ready(function() {
	$('#formmain0151Table').bootstrapTable({
		 
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
    	       showExport: true,
    	       //显示切换分页按钮
    	       showPaginationSwitch: true,
    	       //最低显示2行
    	       minimumCountColumns: 2,
               //是否显示行间隔色
               striped: false,
               rightFixedColumns: true, //右侧冻结列
               rightFixedNumber: 1,
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
               url: "${ctx}/oadata/zhuanzheng/formmain0151/data",
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
		        field: 'field0005',
		        title: '编号',
		        sortable: true,
		        sortName: 'field0005'
		    }
			,{
		        field: 'field0002',
		        title: '申请人',
		        sortable: true,
		        sortName: 'field0002'
		       
		    }
			,{
		        field: 'field0004',
		        title: '申请日期',
		        sortable: true,
		        sortName: 'field0004'
		       
		    }
			,{
		        field: 'field0003',
		        title: '所属部门',
		        sortable: true,
		        sortName: 'field0003'
		       
		    }
			,{
		        field: 'field0009',
		        title: '所属单位',
		        sortable: true,
		        sortName: 'field0009'
		       
		    }
			,{
		        field: 'field0012',
		        title: '申请人姓名',
		        sortable: true,
		        sortName: 'field0012'
		       
		    }/*
			,{
		        field: 'field0008',
		        title: '审批意见',
		        sortable: true,
		        sortName: 'field0008'
		       
		    }
			,{
		        field: 'field0010',
		        title: '符合公司价值观的本人事迹',
		        sortable: true,
		        sortName: 'field0010'
		       
		    }*/
			,{
		        field: 'field0013',
		        title: '状态',
		        sortable: true,
		        sortName: 'field0013'
		       
		    }/*
			,{
		        field: 'field0011',
		        title: '转正申请',
		        sortable: true,
		        sortName: 'field0011'
		       
		    }*/
			,{
		        field: 'field0024',
		        title: '同意转正',
		        sortable: true,
		        sortName: 'field0024'
		       
		    }
			,{
		        field: 'field0025',
		        title: '延迟转正',
		        sortable: true,
		        sortName: 'field0025'
		       
		    }/*
			,{
		        field: 'field0026',
		        title: '解除劳动合同',
		        sortable: true,
		        sortName: 'field0026'
		       
		    }*/
			,{
		        field: 'field0027',
		        title: '转正日期',
		        sortable: true,
		        sortName: 'field0027'
		       
		    }
			,{
		        field: 'field0028',
		        title: '基本工资',
		        sortable: true,
		        sortName: 'field0028'
		       
		    }
			,{
		        field: 'field0029',
		        title: '岗位工资',
		        sortable: true,
		        sortName: 'field0029'
		       
		    }
			,{
		        field: 'field0030',
		        title: '绩效工资',
		        sortable: true,
		        sortName: 'field0030'
		       
		    }
			,{
		        field: 'field0031',
		        title: '保密费',
		        sortable: true,
		        sortName: 'field0031'
		       
		    }
			,{
		        field: 'field0032',
		        title: '延期日期',
		        sortable: true,
		        sortName: 'field0032'
		       
		    }
			,{
		        field: 'field0033',
		        title: '通讯费',
		        sortable: true,
		        sortName: 'field0033'
		       
		    }
			,{
		        field: 'field0034',
		        title: '总工资',
		        sortable: true,
		        sortName: 'field0034'
		       
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
					<% if(shiro.hasPermission("oadata:zhuanzheng:formmain0151:view")){ %>
					   '<a class="view btn btn-icon waves-effect waves-light btn-custom btn-xs m-r-5"> <i class="fa fa-search"></i></a>',
				   <% } %>
				   <% if(shiro.hasPermission("oadata:zhuanzheng:formmain0151:edit")){ %>
					   '<a class="edit btn btn-icon waves-effect waves-light btn-success btn-xs m-r-5"> <i class="fa fa-pencil"></i></a>',
				   <% } %>
				   <% if(shiro.hasPermission("oadata:zhuanzheng:formmain0151:del")){ %>
					   '<a class="del btn btn-icon waves-effect waves-light btn-danger btn-xs"> <i class="fa fa-trash-o"></a>'
				   <% } %>
				   ].join('');
			   }
		   }
		     ]
		
		});
		

	  $('#formmain0151Table').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#formmain0151Table').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#formmain0151Table').bootstrapTable('getSelections').length!=1);
        });

	 $("#import").click(function(){//显示导入面板
            $("#search-collapse").hide();
            $("#import-collapse").fadeToggle()

      })

	 $("#btnImportExcel").click(function(){//导入Excel
		 var importForm = $('#importForm')[0];
		 jp.block('#import-collapse',"文件上传中...");
		 jp.uploadFile(importForm,"${ctx}/oadata/zhuanzheng/formmain0151/import",function (data) {
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
            jp.downloadFile('${ctx}/oadata/zhuanzheng/formmain0151/import/template');
		})

	$("#export").click(function(){//导出Excel文件
	        var searchParam = $("#searchForm").serializeJSON();
	        searchParam.pageNo = 1;
	        searchParam.pageSize = -1;
            var sortName = $('#formmain0151Table').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#formmain0151Table').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/oadata/zhuanzheng/formmain0151/export?'+values);
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

	 $('#field0004').datepicker({//日期控件初始化
			toggleActive: true,
			language:"zh-CN",
    			format:"yyyy-mm-dd"
		});
		
	});

	//获取选中行
  function getIdSelections() {
        return $.map($("#formmain0151Table").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

  //删除
  function del(ids){
     if(!ids){
          ids = getIdSelections();
     }
	 jp.confirm('确认要删除该转正审批记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/oadata/zhuanzheng/formmain0151/delete?ids=" + ids, function(data){
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
      $('#formmain0151Table').bootstrapTable('refresh');
  }

   //新增表单页面
 function add() {
     jp.openSaveDialog('新增转正审批', "${ctx}/oadata/zhuanzheng/formmain0151/form/add",'800px', '500px');
 }
  //编辑表单页面
  function edit(id){
      if(!id){
          id = getIdSelections();
      }
	  jp.openSaveDialog('编辑转正审批', "${ctx}/oadata/zhuanzheng/formmain0151/form/edit?id="+id,'800px', '500px');
  }
  //查看表单页面
  function view(id) {
      if(!id){
          id = getIdSelections();
      }
      jp.openViewDialog('查看转正审批', "${ctx}/oadata/zhuanzheng/formmain0151/form/view?id="+id,'800px', '500px');
  }
</script>