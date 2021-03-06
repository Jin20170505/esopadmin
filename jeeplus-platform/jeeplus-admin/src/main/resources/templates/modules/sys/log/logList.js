<script type="text/javascript">
var $table;
$(document).ready(function() {
	$table =  $('#table').bootstrapTable({

			method: 'post',
			//类型json
			dataType: "json",
			contentType: "application/x-www-form-urlencoded",
			//移动端自适应
			mobileResponsive: true,
             //是否显示行间隔色
            striped: false,
            //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）     
            cache: false,    
            //是否显示分页（*）  
            pagination: true,
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
             //排序方式 
            sortOrder: "asc",  
            //初始化加载第一页，默认第一页
            //我设置了这一项，但是貌似没起作用，而且我这默认是0,- -
            pageNumber:1,   
            //每页的记录行数（*）   
            pageSize: 10,  
            //可供选择的每页的行数（*）    
            pageList: [10, 25, 50, 100],
			//显示详情按钮
			detailView: true,
			//显示详细内容函数
			detailFormatter: "detailFormatter",
            //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据  
            url: "${ctx}/sys/log/data",
            //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
            //queryParamsType:'',   
            ////查询参数,每次调用是会带上这个参数，可自定义
			onShowSearch: function () {
				$("#search-collapse").slideToggle();
			},
            queryParams : function(params) {
            	var searchParam = $("#searchForm").serializeJSON();
            	searchParam.pageNo = params.limit === undefined? "1" :params.offset/params.limit+1;
            	searchParam.pageSize = params.limit === undefined? -1 : params.limit;
                if(params.sort && params.order){
                    searchParam.orderBy = params.sort+ " "+  params.order;
                }
                return searchParam;
            },
            //分页方式：client客户端分页，server服务端分页（*）
            sidePagination: "server",
           
            onClickRow: function(row, $el){
            },
            columns: [{
		        checkbox: true
		       
		    }, {
		        field: 'title',
		        title: '操作菜单',
		        sortable: true
		       
		    }, {
		        field: 'createBy.name',
		        title: '操作用户',
		        sortable: true,
				sortName: 'u.name'
		    }, {
		        field: 'createBy.company.name',
		        title: '所在公司',
		        sortable: true,
				sortName: 'c.name'
		    }, {
		        field: 'createBy.office.name',
		        title: '所在部门',
		        sortable: true,
				sortName: 'o.name'
		    }, {
		        field: 'requestUri',
		        title: 'URI',
		        sortable: true,
				sortName: 'request_uri'
		    }, {
		        field: 'method',
		        title: '提交方式',
		        sortable: true,
				sortName: 'user_agent'
		    }, {
		        field: 'remoteAddr',
		        title: '操作者IP',
		        sortable: true,
				sortName: 'remote_addr'
		    }, {
		        field: 'createDate',
		        title: '操作时间',
		        sortable: true,
				sortName:'create_date'
		    }, {
		        field: 'exception',
		        title: '异常信息',
		        sortable: true,
                class: 'text-nowrap',
		        formatter:function(value, row, index){
		        	if(value){
		        		return '<a class="detail-icon" href="javascript:detailException('+index+')">[查看异常]</a><a class="detail-icon" href="javascript:closeDetail('+index+')">[关闭]</a>';
		        	}else{
		        		return '--';
		        	}
		        }
		    }]
		
		});
	
	  

	  $('#table').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#table').bootstrapTable('getSelections').length);
        });

	  $("#search").click("click", function() {// 绑定查询按扭
		  $('#table').bootstrapTable('refresh');
		});
	  $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $('#table').bootstrapTable('refresh');
		});
    $('#beginDate').datetimepicker({
        format:'Y-m-d H:i'
    });
    $('#endDate').datetimepicker({
        format:'Y-m-d H:i'
    });

});

  function getIdSelections() {
        return $.map($("#table").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(ids){
	  if(!ids){
		  ids = getIdSelections();
	  }

	  jp.confirm('确认要删除该日志吗？', function(){
		  		jp.loading();
         	  	$.get("${ctx}/sys/log/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
                    	jp.success(data.msg);
                    	$table.bootstrapTable('refresh');
                    }else{
        	  			jp.error(data.msg);
                    }
                    
         	  	})
          	   
		})
  }
  function empty(){
	  jp.confirm("确认要清空日志吗？",function(){
		 jp.loading();
	    $.get("${ctx}/sys/log/empty", function(data){
   	  		if(data.success){
            	jp.success(data.msg);
            	$table.bootstrapTable('refresh');
            }else{
	  			jp.error(data.msg);
            }
            
 	  	})
	  })
  }
  function detailException(index) {
    	   $('#table').bootstrapTable('expandRow', index);
	 
  }
  
  function closeDetail(index) {
	   $('#table').bootstrapTable('collapseRow', index); 
 
  }
  
  function detailFormatter(index, row) {
	 return '<font color="red">'+jp.escapeHTML(row.exception)+'</font>';
	  
  }
  
</script>