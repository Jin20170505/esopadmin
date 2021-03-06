<script>
$(document).ready(function() {
	$('#actModelTable').bootstrapTable({

				method: 'post',
				//类型json
				dataType: "json",
				contentType: "application/x-www-form-urlencoded",
				//移动端自适应
				mobileResponsive: true,
               //显示刷新按钮
               showRefresh: true,
               //显示切换手机试图按钮
               showToggle: false,
               //显示 内容列下拉框
    	       showColumns: true,
		       showSearch:true,
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
               url: "${ctx}/act/model/data",
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
				onShowSearch: function () {
					$("#search-collapse").fadeToggle();
				},
              
               onClickRow: function(row, $el){
               },
               columns: [{
		        checkbox: true
		       
		    }
			,{
		        field: 'category',
		        title: '流程分类',
		        sortable: false,
		        formatter:function(value, row , index){
		        	return "<a href='#'>"+jp.getDictLabel(${fn.toJson(fn.getDictList('act_category'))}, value, "-")+"</a>";
		        }
		       
		    }
			,{
		        field: 'key',
		        title: '模型标识',
		        sortable: false
		       
		    }
			,{
		        field: 'name',
		        title: '模型名称',
		        sortable: true
		       
		    }
			,{
		        field: 'version',
		        title: '版本号',
		        sortable: false,
			   formatter:function (value, row, index) {
					return "<font class='badge badge-pill badge-custom'>" + value +"</font>";
				}

		    }
			,{
		        field: 'createTime',
		        title: '创建时间',
		        sortable: false,
			    formatter:function (value, row, index) {
				   return jp.dateFormat(value,"yyyy-MM-dd hh:mm:ss");
			    }
		       
		    }
			,{
		        field: 'lastUpdateTime',
		        title: '最后更新时间',
		        sortable: false,
			   formatter:function (value, row, index) {
				   return jp.dateFormat(value,"yyyy-MM-dd hh:mm:ss");
			   }
		       
		    }, {
                       field: 'operate',
                       title: '操作',
                       align: 'center',
                       class: 'text-nowrap',
                       events: {
                           'click .deploy': function (e, value, row, index) {
								   jp.confirm('确认要部署该模型吗？',function () {
                                       jp.get("${ctx}/act/model/deploy?id="+row.id,function (data) {
                                           if(data.success){
                                               jp.success(data.msg);
                                               $('#actModelTable').bootstrapTable('refresh');

                                           }else{
                                               jp.error(data.msg);
                                           }

                                       })
                                   })

                           },
                           'click .del': function (e, value, row, index) {
                           		del(row.id);
                           }
                       },
                       formatter:  function operateFormatter(value, row, index) {
                           return [
                               <% if(shiro.hasPermission("act:model:edit") ){ %>
                                   '<a class="btn btn-custom btn-xs waves-effect m-r-5" href="${ctxPath}/act/rest/modeler.html?modelId='+row.id+'" target="_blank">在线设计</a>',
							   <% } %>
                               <% if(shiro.hasPermission("act:model:deploy") ){ %>
                                   '<a  class="deploy btn btn-success btn-xs waves-effect m-r-5">部署</a>',
						       <% } %>
						       <% if(shiro.hasPermission("act:model:export") ){ %>
                                   '<a  class="btn btn-info btn-xs waves-effect m-r-5" href="${ctx}/act/model/export?id='+row.id+'" target="_blank">导出</a>',
							   <% } %>
                           		<% if(shiro.hasPermission("act:model:del") ){ %>
                                   '<a  class="del btn btn-danger btn-xs waves-effect m-r-5">删除</a>'
                               <% } %>
                           ].join('');
                       }
                   }
		     ]
		
		});
		
		  

	  $('#actModelTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#actModelTable').bootstrapTable('getSelections').length);
        });

		    
	  $("#search").click("click", function() {// 绑定查询按扭
		  $('#actModelTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#actModelTable').bootstrapTable('refresh');
		});
		
		
	});

Date.prototype.format = function(format) {

};
  function getIdSelections() {
        return $.map($("#actModelTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该模型记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/act/model/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#actModelTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
	function del(id) {
        jp.confirm('确认要删除该模型记录吗？', function() {
            jp.loading();
            jp.get("${ctx}/act/model/delete?id=" + id, function (data) {
                if (data.success) {
                    $('#actModelTable').bootstrapTable('refresh');
                    jp.success(data.msg);
                } else {
                    jp.error(data.msg);
                }
            })
        })
	}
   function add(){
	  jp.openSaveDialog('新增模型', "${ctx}/act/model/create",'80%', '70%');
  };

  function refresh() {
      $('#actModelTable').bootstrapTable('refresh');
  }

</script>