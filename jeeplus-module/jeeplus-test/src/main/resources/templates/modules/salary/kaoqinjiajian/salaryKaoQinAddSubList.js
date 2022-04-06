<script>
$(document).ready(function() {
	$('#salaryKaoQinAddSubTable').bootstrapTable({
		 
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
               url: "${ctx}/salary/kaoqinjiajian/salaryKaoQinAddSub/data",
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
                 searchParam.type = '加班费';
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
		        field: 'yearmonth',
		        title: '考勤年月',
		        sortable: true,
		        sortName: 'yearmonth'
		        ,formatter:function(value, row , index){
		        	  <% if(shiro.hasPermission("salary:kaoqinjiajian:salaryKaoQinAddSub:edit") ){ %>
					   if(!value){
						  return "<a  href='#' onclick='edit(\""+row.id+"\")'>-</a>";
					   }else{
						  return "<a  href='#' onclick='edit(\""+row.id+"\")'>"+value+"</a>";
						}
                     <% }else if(shiro.hasPermission("salary:kaoqinjiajian:salaryKaoQinAddSub:view")){ %>
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
		        field: 'employee.name',
		        title: '员工',
		        sortable: true,
		        sortName: 'employee.name'
		       
		    }
			,{
		        field: 'xmztype',
		        title: '项目组类型',
		        sortable: true,
		        sortName: 'xmztype'
		       
		    }
			,{
		        field: 'fadingdays',
		        title: '法定节假日加班天数',
		        sortable: true,
		        sortName: 'fadingdays'
		       
		    }
			,{
		        field: 'gongzuoridays',
		        title: '工作日加班天数',
		        sortable: true,
		        sortName: 'gongzuoridays'
		       
		    }
			,{
		        field: 'zhoumodays',
		        title: '周末加班天数',
		        sortable: true,
		        sortName: 'zhoumodays'
		    }
			,{
		        field: 'jiejiarisalary',
		        title: '节假日加班工资',
		        sortable: true,
		        sortName: 'jiejiarisalary'
		       
		    }
			,{
		        field: 'gongzuorisalary',
		        title: '工作日加班工资',
		        sortable: true,
		        sortName: 'gongzuorisalary'
		       
		    }
			,{
		        field: 'zhoumosalary',
		        title: '周末加班工资',
		        sortable: true,
		        sortName: 'zhoumosalary'
		       
		    }
			,{
		        field: 'jiabanSalary',
		        title: '本月加班工资总和',
		        sortable: true,
		        sortName: 'jiabanSalary'
		       
		    }
		     ]
		
		});
		

	  $('#salaryKaoQinAddSubTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#salaryKaoQinAddSubTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#salaryKaoQinAddSubTable').bootstrapTable('getSelections').length!=1);
        });

	 $("#import").click(function(){//显示导入面板
            $("#search-collapse").hide();
            $("#import-collapse").fadeToggle()

      })

	 $("#btnImportExcel").click(function(){//导入Excel
		 var importForm = $('#importForm')[0];
		 jp.block('#import-collapse',"文件上传中...");
		 jp.uploadFile(importForm,"${ctx}/salary/kaoqinjiajian/salaryKaoQinAddSub/import",function (data) {
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
            jp.downloadFile('${ctx}/salary/kaoqinjiajian/salaryKaoQinAddSub/import/template');
		})

	$("#export").click(function(){//导出Excel文件
	        var searchParam = $("#searchForm").serializeJSON();
	        searchParam.pageNo = 1;
	        searchParam.pageSize = -1;
            searchParam.type = '加班费';
            var sortName = $('#salaryKaoQinAddSubTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#salaryKaoQinAddSubTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/salary/kaoqinjiajian/salaryKaoQinAddSub/export?'+values);
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

	$('#yearmonth').datetimepicker({
        format:'Y-m',
        formatDate:'y-m',
        timepicker: true,
        validateOnBlur: false
		});
		
	});

	//获取选中行
  function getIdSelections() {
        return $.map($("#salaryKaoQinAddSubTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

  //删除
  function del(ids){
     if(!ids){
          ids = getIdSelections();
     }
	 jp.confirm('确认要删除该考勤加减记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/salary/kaoqinjiajian/salaryKaoQinAddSub/delete?ids=" + ids, function(data){
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
      $('#salaryKaoQinAddSubTable').bootstrapTable('refresh');
  }

   //新增表单页面
function add() {
    jp.openSaveDialog('计算——选择年月', "${ctx}/salary/kaoqinjiajian/salaryKaoQinAddSub/goToJs",'500px', '500px');
}

function tbday() {
    jp.openSaveDialog('同步出勤情况——选择年月', "${ctx}/salary/kaoqinjiajian/salaryKaoQinAddSub/goToTbday",'500px', '500px');
}
  //编辑表单页面
  function edit(id){
      if(!id){
          id = getIdSelections();
      }
	  jp.openSaveDialog('编辑考勤加减', "${ctx}/salary/kaoqinjiajian/salaryKaoQinAddSub/form/edit?id="+id,'80%', '70%');
  }
  //查看表单页面
  function view(id) {
      if(!id){
          id = getIdSelections();
      }
      jp.openViewDialog('查看考勤加减', "${ctx}/salary/kaoqinjiajian/salaryKaoQinAddSub/form/view?id="+id,'80%', '70%');
  }
</script>