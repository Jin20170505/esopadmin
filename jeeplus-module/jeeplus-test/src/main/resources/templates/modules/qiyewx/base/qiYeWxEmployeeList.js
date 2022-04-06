<script>
var reg = /^(\d{3})\d{4}(\d{4})$/;
$(document).ready(function() {
	$('#qiYeWxEmployeeTable').bootstrapTable({
		 
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
               url: "${ctx}/qiyewx/base/qiYeWxEmployee/data",
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
               columns: [
                   {
                       checkbox: true
                   },
                   {
                       field: 'thumbvatar',
                       title: '头像(压缩)',
                       sortable: true,
                       sortName: 'thumbvatar',
                       formatter:function(value, row , index){
                           return '<img   onclick="jp.showPic(\''+value+'\')"'+' height="50px" src="'+value+'">';
                       }
                   }
                   ,{
                       field: 'name',
                       title: '姓名',
                       sortable: true,
                       sortName: 'name'
                   }
                   ,{
                       field: 'userid',
                       title: '员工',
                       sortable: true,
                       sortName: 'userid'
                   }
			,{
		        field: 'mainDept.name',
		        title: '主要部门'
		    }
			,{
		        field: 'position',
		        title: '职务信息',
		        sortable: true,
		        sortName: 'position'
		    }
			,{
		        field: 'mobile',
		        title: '手机号',
		        sortable: true,
		        sortName: 'mobile'
			   ,formatter:function(value, row , index){
				   return value.replace(reg, "$1****$2");
			   }
		    }
			,{
		        field: 'gender',
		        title: '性别',
		        sortable: true,
		        sortName: 'gender',
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fn.toJson(fn.getDictList('sex'))}, value, "-");
		        }
		    }
			,{
		        field: 'tag',
		        title: '标签',
		        sortable: true,
		        sortName: 'tag'
		    }
			,{
		        field: 'status',
		        title: '状态',
		        sortable: true,
		        sortName: 'status',
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fn.toJson(fn.getDictList('qywxemployee_status'))}, value, "-");
		        }
		       
		    }
		     ]
		
		});
		

	  
	  $('#qiYeWxEmployeeTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove,#status').prop('disabled', ! $('#qiYeWxEmployeeTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#qiYeWxEmployeeTable').bootstrapTable('getSelections').length!=1);
        });

	 $("#import").click(function(){//显示导入面板
            $("#search-collapse").hide();
            $("#import-collapse").fadeToggle()

      })

	 $("#btnImportExcel").click(function(){//导入Excel
		 var importForm = $('#importForm')[0];
		 jp.block('#import-collapse',"文件上传中...");
		 jp.uploadFile(importForm,"${ctx}/qiyewx/base/qiYeWxEmployee/import",function (data) {
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
            jp.downloadFile('${ctx}/qiyewx/base/qiYeWxEmployee/import/template');
		})

	 $("#export").click(function(){//导出Excel文件
	        var searchParam = $("#searchForm").serializeJSON();
	        searchParam.pageNo = 1;
	        searchParam.pageSize = -1;
            var sortName = $('#qiYeWxEmployeeTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#qiYeWxEmployeeTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/qiyewx/base/qiYeWxEmployee/export?'+values);
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
function updateStatus(ids) {
    if(!ids){
        ids = getIdSelections();
    }
    top.layer.open({
        type: 1,
        title:'状态选择',
        area:['400px','200px'],
        content:$('#statusSelect').html(),
        btn: ['确定','取消'],
        yes:function(index,layero){
            var status =  $(layero).find('#statusofwx').val();
            if(!status){
                jp.warning("请选择状态");
                return false;
            }
            jp.loading("更新中...");
            jp.post("${ctx}/qiyewx/base/qiYeWxEmployee/updateStatus",{'rids':ids.toString(),'status':status},function (rs) {
                jp.close();
                if(rs.success){
                    jp.success(rs.msg);
                    refresh();
                }else {
                    jp.error(rs.msg);
                }
            })
        }
    });
}
	//获取选中行
  function getIdSelections() {
        return $.map($("#qiYeWxEmployeeTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
	/** 同步微信数据 */
	function syncData() {
        var index =jp.loading();
        jp.get("${ctx}/qiyewx/base/qiYeWxEmployee/syncData", function(data){
            if(data.success){
                refresh();
                jp.toastr_success(data.msg);
            }else{
                jp.toastr_error(data.msg);
            }
            jp.close(index);
        })
    }

    function syncTagUser(){
        var index =jp.loading();
        jp.get("${ctx}/qiyewx/base/qiYeWxTag/sychTagUser", function(data){
            if(data.success){
            refresh();
            jp.toastr_success(data.msg);
        }else{
            jp.toastr_error(data.msg);
        }
            jp.close(index);
        })
    }
  //删除
  function del(ids){
     if(!ids){
          ids = getIdSelections();
     }
	 jp.confirm('确认要删除该企业微信_员工记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/qiyewx/base/qiYeWxEmployee/delete?ids=" + ids, function(data){
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
      $('#qiYeWxEmployeeTable').bootstrapTable('refresh');
  }

   //新增表单页面
 function add() {
     jp.openSaveDialog('新增企业微信_员工', "${ctx}/qiyewx/base/qiYeWxEmployee/form/add",'80%', '70%');
 }
  //编辑表单页面
  function edit(id){
      if(!id){
          id = getIdSelections();
      }
	  jp.openSaveDialog('编辑企业微信_员工', "${ctx}/qiyewx/base/qiYeWxEmployee/form/edit?id="+id,'80%', '70%');
  }
  //查看表单页面
  function view(id) {
      if(!id){
          id = getIdSelections();
      }
      jp.openViewDialog('查看企业微信_员工', "${ctx}/qiyewx/base/qiYeWxEmployee/form/view?id="+id,'80%', '70%');
  }
</script>