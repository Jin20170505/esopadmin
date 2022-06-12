<script>
$(document).ready(function() {
	$('#businessBaoGongRecordTable').bootstrapTable({
		 
		  //请求方法
               method: 'post',
               //类型json
               dataType: "json",
               contentType: "application/x-www-form-urlencoded",
               //移动端自适应
               mobileResponsive: false,
               //允许列拖动大小
               resizable: false,
               //固定表头
               stickyHeader: false,
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
               striped: true,
               rightFixedColumns: false, //右侧冻结列
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
               url: "${ctx}/business/baogong/record/businessBaoGongRecord/data",
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
		        field: 'bgdate',
		        title: '报工时间',
		        sortable: true,
		        sortName: 'bgdate'
		       
		    }
           ,{
               field: 'douser.name',
               title: '实际报工人',
               sortable: true,
               sortName: 'douser.name'
           }
			,{
		        field: 'bgcode',
		        title: '报工单号',
		        sortable: true,
		        sortName: 'bgcode'
               ,formatter:function(value, row , index){
                   if(!value){
                       return "<a  href='#' onclick='view(\""+row.id+"\")'>-</a>";
                   }else{
                       return "<a  href='#' onclick='view(\""+row.id+"\")'>"+value+"</a>";
                   }
               }
		    }
			,{
		        field: 'ordercode',
		        title: '生产订单号',
		        sortable: true,
		        sortName: 'ordercode'
		       
		    }
			,{
		        field: 'orderline',
		        title: '生产订单行号',
		        sortable: true,
		        sortName: 'orderline'
		       
		    }
			,{
		        field: 'site',
		        title: '工序名称',
		        sortable: true,
		        sortName: 'site'
		       
		    }
			,{
		        field: 'username',
		        title: '姓名',
		        sortable: true,
		        sortName: 'username'
		       
		    }
                   ,{
                       field: 'cinvcode',
                       title: '存货编码',
                       sortable: true,
                       sortName: 'cinvcode'

                   }
                   ,{
                       field: 'cinvname',
                       title: '存货名称',
                       sortable: true,
                       sortName: 'cinvname'

                   }
                   ,{
                       field: 'cinvstd',
                       title: '规格型号',
                       sortable: true,
                       sortName: 'cinvstd'

                   }
			,{
		        field: 'gdnum',
		        title: '工单数量',
		        sortable: true,
		        sortName: 'gdnum'
		       
		    }
			,{
		        field: 'lfnum',
		        title: '料废数量',
		        sortable: true,
		        sortName: 'lfnum'
		       
		    }
			,{
		        field: 'gfnum',
		        title: '工废数量',
		        sortable: true,
		        sortName: 'gfnum'
		       
		    }
			,{
		        field: 'fgnum',
		        title: '返工数量',
		        sortable: true,
		        sortName: 'fgnum'
		       
		    }
			,{
		        field: 'bhgnum',
		        title: '不合格数量',
		        sortable: true,
		        sortName: 'bhgnum'
		       
		    }
			,{
		        field: 'hgnum',
		        title: '合格数量',
		        sortable: true,
		        sortName: 'hgnum'
		    }
           ,{
               field: 'hglv',
               title: '合格率'
               ,formatter:function(value, row , index){
                    return (value-0).toFixed(2);
               }
           }
			,{
		        field: 'remarks',
		        title: '备注信息',
		        sortable: true,
		        sortName: 'remarks'
		       
		    }
		     ]
		
		});
		

	  $('#businessBaoGongRecordTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#businessBaoGongRecordTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#businessBaoGongRecordTable').bootstrapTable('getSelections').length!=1);
        });

	 $("#import").click(function(){//显示导入面板
            $("#search-collapse").hide();
            $("#import-collapse").fadeToggle()

      })

	 $("#btnImportExcel").click(function(){//导入Excel
		 var importForm = $('#importForm')[0];
		 jp.block('#import-collapse',"文件上传中...");
		 jp.uploadFile(importForm,"${ctx}/business/baogong/record/businessBaoGongRecord/import",function (data) {
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
            jp.downloadFile('${ctx}/business/baogong/record/businessBaoGongRecord/import/template');
		})

	$("#export").click(function(){//导出Excel文件
	        var searchParam = $("#searchForm").serializeJSON();
	        searchParam.pageNo = 1;
	        searchParam.pageSize = -1;
            var sortName = $('#businessBaoGongRecordTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#businessBaoGongRecordTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/business/baogong/record/businessBaoGongRecord/export?'+values);
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

	 $('#bgdate').datepicker({//日期控件初始化
			toggleActive: true,
			language:"zh-CN",
    			format:"yyyy-mm-dd"
		});
		
	});

	//获取选中行
  function getIdSelections() {
        return $.map($("#businessBaoGongRecordTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
    function bgedit(id){
        if(!id){
        id = getIdSelections();
    }
    jp.openSaveDialog('编辑报工修改', "${ctx}/business/baogong/change/businessBaoGongChange/form/edit?recordid="+id,'800px', '500px');
}
  //删除
  function del(ids){
     if(!ids){
          ids = getIdSelections();
     }
	 jp.confirm('确认要删除该员工报工记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/business/baogong/record/businessBaoGongRecord/delete?ids=" + ids, function(data){
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
      $('#businessBaoGongRecordTable').bootstrapTable('refresh');
  }

   //新增表单页面
 function add() {
     jp.openSaveDialog('新增员工报工', "${ctx}/business/baogong/record/businessBaoGongRecord/form/add",'90%', '90%');
 }
  //编辑表单页面
  function edit(id){
      if(!id){
          id = getIdSelections();
      }
	  jp.openSaveDialog('编辑员工报工', "${ctx}/business/baogong/record/businessBaoGongRecord/form/edit?id="+id,'90%', '90%');
  }
  //查看表单页面
  function view(id) {
      if(!id){
          id = getIdSelections();
      }
      jp.openViewDialog('查看员工报工', "${ctx}/business/baogong/record/businessBaoGongRecord/form/view?id="+id,'90%', '90%');
  }
</script>