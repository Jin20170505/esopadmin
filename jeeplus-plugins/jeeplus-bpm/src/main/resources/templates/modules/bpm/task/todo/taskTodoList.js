<script>
$(document).ready(function() {
	$('#actTable').bootstrapTable({
		 
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
               //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）     
               cache: false,    
               //是否显示分页（*）  
               pagination: true,   
                //排序方式 
               sortOrder: "asc",  
               //初始化加载第一页，默认第一页
               pageNumber:1,   
               //每页的记录行数（*）   
               pageSize: 10,  
               //可供选择的每页的行数（*）    
               pageList: [10, 25, 50, 100],
               //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据  
               url: "${ctx}/act/task/todo/data",
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
               //分页方式：client客户端分页，server服务端分页（*）
               sidePagination: "server",

               onClickRow: function(row, $el){
               },
			onShowSearch: function () {
				$("#search-collapse").fadeToggle();
			},
               columns: [{
		        checkbox: true
		       
		    }

			,{
		        field: 'vars.title',
		        title: '实例标题'
		       
		    },{
                       field: 'procDef.name',
                       title: '流程名称'

                   }
			,{
		        field: 'task.name',
		        title: '当前环节',
			   formatter:function (value, row, index) {
					   return "<font class='badge badge-custom'>" + value +"</font>";
			   }

		       
		    }

			,{
		        field: 'vars.userName',
		        title: '流程发起人'
		       
		    }
			,{
		        field: 'task.createTime',
		        title: '创建时间',
			   formatter:function (value, row, index) {
				   return jp.dateFormat(value, "yyyy-MM-dd hh:mm:ss");
			   }
		       
		    }, {
                       field: 'operate',
                       title: '操作',
                       align: 'center',
                       class: 'text-nowrap',
                       formatter: function operateFormatter(value, row, index) {
						   var arra = new Array();
                       	  if(row['task.assignee'] == undefined || row['task.assignee'] == ""){
                       	  	arra.push('<a class="btn btn-custom btn-xs waves-effect m-r-5" href="javascript:claim(\''+row['task.id']+'\');">签收任务</a>');
						  }else{
                       	  	arra.push('<a class="btn btn-success btn-xs waves-effect m-r-5" href="${ctx}/act/task/form?taskId='+row['task.id']+'&taskName='+encodeURI(row['task.name'])+'&taskDefKey='+row['task.taskDefinitionKey']+'&procInsId='+row['task.processInstanceId']+'&procDefId='+row['task.processDefinitionId']+'&status='+row.status+'" >任务办理</a>');

						  };
                           <% if(shiro.hasPermission("act:process:edit")){ %>
                               if(row['task.executionId'] == undefined || row['task.executionId'] == ""){
                       	  			arra.push( '<a class="btn btn-danger btn-xs waves-effect m-r-5" href="${ctx}/act/task/deleteTask?taskId='+row['task.id']+'&reason=" onclick="return jp.prompt(\'删除原因\',this.href);">删除任务</a>')
                               };

                           <% } %>
                           arra.push('<a class="btn btn-primary btn-xs waves-effect m-r-5" href="javascript:jp.openViewDialog(\'进度\',\'${ctx}/act/task/trace/photo/'+row['task.processDefinitionId']+'/'+row['task.executionId']+'\',\'1000px\', \'600px\')">进度</a>');

							return arra.join("");

                       }
                   }
		     ]
		
		});
		
	  $('#actTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#actTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#actTable').bootstrapTable('getSelections').length!=1);
        });
		  

	  $("#search").click("click", function() {// 绑定查询按扭
		  $('#actTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#actTable').bootstrapTable('refresh');
		});
		
		$('#beginDate').datetimepicker({
			 format:'Y-m-d H:i'
		});
		$('#endDate').datetimepicker({
			 format:'Y-m-d H:i'
		});
		
	});
		
  function getIdSelections() {
        return $.map($("#actTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该发起任务记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/task/todo/act/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#actTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
function claim(taskId) {
    $.post('${ctx}/act/task/claim' ,{taskId: taskId}, function(data) {
        if (data == 'true'){
            jp.success('签收完成');
            $('#actTable').bootstrapTable('refresh');
        }else{
            jp.error('签收失败');
        }
    });
}

</script>