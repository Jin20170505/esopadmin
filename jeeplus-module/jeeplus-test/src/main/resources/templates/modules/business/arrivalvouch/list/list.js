<script>
    $(document).ready(function() {
    var to = false;
    $('#search_q').keyup(function () {
    if(to) { clearTimeout(to); }
    to = setTimeout(function () {
    var v = $('#search_q').val();
    $('#businessProductTypeOnlyReadjsTree').jstree(true).search(v);
}, 250);
});
    $('#businessProductTypeOnlyReadjsTree').jstree({
    'core' : {
    "multiple": false,
    "animation": 0,
    "themes": {"icons": true, "stripes": false},
    'data' : {
    "url" : "${ctx}/business/baogong/order/businessBaoGongOrder/treeData",
    "dataType" : "json"
}
},
    "conditionalselect" : function (node, event) {
    return false;
},
    'plugins': ['types', 'wholerow', "search"],
    "types": {
    "default": {
    "icon": "fa fa-folder text-custom"
},
    "file": {
    "icon": "fa fa-file text-success"
}
}

}).bind("activate_node.jstree", function (obj, e) {
    var node = $('#businessProductTypeOnlyReadjsTree').jstree(true).get_selected(true)[0];
    var opt = {
    silent: true,
    query:{
    'isprint':node.id
}
};
    $("#printstatus").val(node.id);
    $('#businessArrivalVouchTable').bootstrapTable('refresh',opt);
}).on('loaded.jstree', function() {
    $("#businessProductTypeOnlyReadjsTree").jstree('open_all');
});
});
$(document).ready(function() {
	$('#businessArrivalVouchTable').bootstrapTable({
		 
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
    	       //显示详情按钮
    	       detailView: false,
    	       	//显示详细内容函数
	           detailFormatter: "detailFormatter",
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
               url: "${ctx}/business/arrivalvouch/businessArrivalVouch/mxdata",
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
               field: 'printstatus',
               title: '打印状态'
           }
			,{
		        field: 'p.code',
		        title: '到货单号',
		        sortable: true,
		        sortName: 'p.code'
		       
		    }
			,{
		        field: 'p.arriveDate',
		        title: '到货日期',
		        sortable: true,
		        sortName: 'p.arriveDate'
		       
		    }
			,{
		        field: 'dept.name',
		        title: '部门',
		        sortable: true,
		        sortName: 'dept.name'
		       
		    }
			,{
		        field: 'vendor.name',
		        title: '供应商',
		        sortable: true,
		        sortName: 'vendor.name'
		       
		    }
                   ,{
                       field: 'no',
                       title: '行号'
                   }
                   ,{
                       field: 'ck.name',
                       title: '仓库'
                   }
                   ,{
                       field: 'hw.name',
                       title: '推荐货位'
                   }
                   ,{
                       field: 'cinvcode',
                       title: '存货编码'
                   }
                   ,{
                       field: 'cinvname',
                       title: '存货名称'
                   }
                   ,{
                       field: 'cinvstd',
                       title: '规格型号'
                   }
                   ,{
                       field: 'batchno',
                       title: '批号'
                   }
                   ,{
                       field: 'scdate',
                       title: '生产日期'
                   }
                   ,{
                       field: 'num',
                       title: '数量'
                   }
                   ,{
                       field: 'unit',
                       title: '单位'
                   }
                   ,{
                       field: 'minnum',
                       title: '最小包装数'
                   }
		     ]
		
		});
		

	  $('#businessArrivalVouchTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#businessArrivalVouchTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#businessArrivalVouchTable').bootstrapTable('getSelections').length!=1);
        });


	  $("#search").click("click", function() {// 绑定查询按扭
		  refresh();

		});

	 $("#reset").click("click", function() { //绑定重置按钮
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  refresh();
		});

	 $('#arriveDate').datepicker({//日期控件初始化
			toggleActive: true,
			language:"zh-CN",
    		format:"yyyy-mm-dd"
		});
		
	});
    function doPrint(){
    var rids = getIdSelections();
    jp.windowOpen('${ctx}/business/arrivalvouch/businessArrivalVouch/goToTagPrint?rids='+rids,"打印",1200,1200);
}
	//获取选中行
  function getIdSelections() {
        return $.map($("#businessArrivalVouchTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

</script>
