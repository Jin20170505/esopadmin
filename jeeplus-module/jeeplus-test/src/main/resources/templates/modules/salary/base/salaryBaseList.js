<script>
$(document).ready(function() {
	$('#salaryBaseTable').bootstrapTable({
		 
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
               url: "${ctx}/salary/base/salaryBase/data",
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
                       field: 'tag',
                       title: '所属公司'
                   },{
		        field: 'employee.name',
		        title: '员工姓名',
		        sortable: true,
		        sortName: 'employee.name'
		        ,formatter:function(value, row , index){
		             <% if(shiro.hasPermission("salary:base:salaryBase:edit") ){ %>
					   if(!value){
						  return "<a  href='#' onclick='edit(\""+row.id+"\")'>-</a>";
					   }else{
						  return "<a  href='#' onclick='edit(\""+row.id+"\")'>"+value+"</a>";
						}
                     <% }else if(shiro.hasPermission("salary:base:salaryBase:view")){ %>
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
		        field: 'userid',
		        title: '员工编号',
		        sortable: true,
		        sortName: 'userid'
		    },{
                       field: 'dept.name',
                       title: '部门',
                       sortable: true,
                       sortName: 'dept.name'
                   }
			,{
		        field: 'groupType',
		        title: '组别',
		        sortable: true,
		        sortName: 'groupType',
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fn.toJson(fn.getDictList('salary_employee_group_type'))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'baseSalary',
		        title: '基本薪资',
		        sortable: true,
		        sortName: 'baseSalary'
		       
		    }
			,{
		        field: 'gangweiSalary',
		        title: '岗位工资',
		        sortable: true,
		        sortName: 'gangweiSalary'
		       
		    }
			,{
		        field: 'baomiFee',
		        title: '保密费',
		        sortable: true,
		        sortName: 'baomiFee'
		       
		    }
			,{
		        field: 'jixiaobiaozhunSalary',
		        title: '绩效工资标准',
		        sortable: true,
		        sortName: 'jixiaobiaozhunSalary'
		       
		    }
			,{
		        field: 'shebaoBase',
		        title: '社保基数',
		        sortable: true,
		        sortName: 'shebaoBase'
		       
		    }
			,{
		        field: 'gongjijinBase',
		        title: '公积金基数',
		        sortable: true,
		        sortName: 'gongjijinBase'
		       
		    }
				   ,{
					   field: 'tongxunfei',
					   title: '通讯费',
					   sortable: true,
					   sortName: 'tongxunfei'

				   },{
                       field: 'sumSalary',
                       title: '工资合计'
                   }
			,{
		        field: 'smallSalary',
		        title: '最低工资',
		        sortable: true,
		        sortName: 'smallSalary'
		       
		    }
			,{
		        field: 'status',
		        title: '员工状态',
		        sortable: true,
		        sortName: 'status',
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fn.toJson(fn.getDictList('salary_employee_status'))}, value, "-");
		        }
		    }
                   ,{
                       field: 'sfkq',
                       title: '是否参与考勤',
                       sortable: true,
                       sortName: 'sfkq',
                       formatter:function(value, row , index){
                           return jp.getDictLabel(${fn.toJson(fn.getDictList('yes_no'))}, value, "-");
                       }
                   }
			,{
		        field: 'inDate',
		        title: '入职日期',
		        sortable: true,
		        sortName: 'inDate'
		       
		    }
			,{
		        field: 'outDate',
		        title: '离职日期',
		        sortable: true,
		        sortName: 'outDate'
		       
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
					<% if(shiro.hasPermission("salary:base:salaryBase:view")){ %>
					   '<a class="view btn btn-icon waves-effect waves-light btn-custom btn-xs m-r-5"> <i class="fa fa-search"></i></a>',
				   <% } %>
				   <% if(shiro.hasPermission("salary:base:salaryBase:edit")){ %>
					   '<a class="edit btn btn-icon waves-effect waves-light btn-success btn-xs m-r-5"> <i class="fa fa-pencil"></i></a>',
				   <% } %>
				   <% if(shiro.hasPermission("salary:base:salaryBase:del")){ %>
					   '<a class="del btn btn-icon waves-effect waves-light btn-danger btn-xs"> <i class="fa fa-trash-o"></a>'
				   <% } %>
				   ].join('');
			   }
		   }
		     ]
		
		});
		

	  $('#salaryBaseTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#salaryBaseTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#salaryBaseTable').bootstrapTable('getSelections').length!=1);
        });

	 $("#import").click(function(){//显示导入面板
            $("#search-collapse").hide();
            $("#import-collapse").fadeToggle()

      })

	 $("#btnImportExcel").click(function(){//导入Excel
		 var importForm = $('#importForm')[0];
		 jp.block('#import-collapse',"文件上传中...");
		 jp.uploadFile(importForm,"${ctx}/salary/base/salaryBase/import",function (data) {
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
            jp.downloadFile('${ctx}/salary/base/salaryBase/import/template');
		})

	$("#export").click(function(){//导出Excel文件
	        var searchParam = $("#searchForm").serializeJSON();
	        searchParam.pageNo = 1;
	        searchParam.pageSize = -1;
            var sortName = $('#salaryBaseTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#salaryBaseTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/salary/base/salaryBase/export?'+values);
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
    $('#upDate').datepicker({//日期控件初始化
        toggleActive: true,
        language:"zh-CN",
        format:"yyyy-mm-dd"
    });
		
	});

	//获取选中行
  function getIdSelections() {
        return $.map($("#salaryBaseTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

  //删除
  function del(ids){
     if(!ids){
          ids = getIdSelections();
     }
	 jp.confirm('确认要删除该员工工资信息记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/salary/base/salaryBase/delete?ids=" + ids, function(data){
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
      $('#salaryBaseTable').bootstrapTable('refresh');
  }
  /** 录用同步 */
  function goToSync(){
      jp.openSaveDialog('同步数据', "${ctx}/salary/base/salaryBase/goToSync",'600px', '500px');
  }
   //新增表单页面
 function add() {
     jp.openSaveDialog('新增员工工资信息', "${ctx}/salary/base/salaryBase/form/add",'80%', '70%');
 }
  //编辑表单页面
  function edit(id){
      if(!id){
          id = getIdSelections();
      }
	  jp.openSaveDialog('编辑员工工资信息', "${ctx}/salary/base/salaryBase/form/edit?id="+id,'80%', '70%');
  }
  //查看表单页面
  function view(id) {
      if(!id){
          id = getIdSelections();
      }
      jp.openViewDialog('查看员工工资信息', "${ctx}/salary/base/salaryBase/form/view?id="+id,'80%', '70%');
  }
</script>