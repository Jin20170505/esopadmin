<script>
$(document).ready(function() {
	$('#dashBoardTable').bootstrapTable({
		 
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
    	       //显示详情按钮
    	       detailView: false,
    	       	//显示详细内容函数
	           detailFormatter: "detailFormatter",
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
               url: "${ctx}/dashBoard/data",
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
		        title: '名字',
		        sortable: true
		        ,formatter:function(value, row , index){
						   if(!value){
							   return "<a href='#' onclick='edit(\""+row.id+"\")'>-</a>";
						   }else{
							   return "<a href='#' onclick='edit(\""+row.id+"\")'>"+value+"</a>";
						   }
						   if(!value){
							   return "<a href='#' onclick='view(\""+row.id+"\")'>-</a>";
						   }else{
							   return "<a href='#' onclick='view(\""+row.id+"\")'>"+value+"</a>";
						   }
						   return value;
		         }
		       
		    }
			,{
		        field: 'remarks',
		        title: '备注信息',
		        sortable: true
		       
		    }
			,{
			   field: 'operate',
			   title: '操作',
			   align: 'center',
               class: 'text-nowrap',
			   events: {
				   'click .view': function (e, value, row, index) {
					   preview(row);
				   },
				   'click .edit': function (e, value, row, index) {
					   design(row)
				   },
				   'click .del': function (e, value, row, index) {
					   del(row.id);

				   }
			   },
			   formatter:  function operateFormatter(value, row, index) {
				   return [
					   '<a href="javascript: return false" class="view btn btn-xs btn-custom m-r-5" title="预览"> 预览</a>',
						'<a href="javascript: return false" class="edit btn btn-xs btn-success m-r-5" title="设计"> 设计 </a>',
					   '<a href="javascript: return false" class="del btn btn-xs btn-danger" title="删除"> 删除 </a>'
				   ].join('');
			   }
		   }
		     ]
		
		});
		

	  $('#dashBoardTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', !$('#dashBoardTable').bootstrapTable('getSelections').length);
            $('#edit, #design, #preview, #createMenu').prop('disabled', $('#dashBoardTable').bootstrapTable('getSelections').length!=1);
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

		
	});

	//获取选中行
  function getIdSelections() {
        return $.map($("#dashBoardTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

//获取选中行
function getSelections() {
	return $.map($("#dashBoardTable").bootstrapTable('getSelections'), function (row) {
		return row
	});
}


//删除
  function del(ids){
     if(!ids){
          ids = getIdSelections();
     }
	 jp.confirm('确认要删除该仪表盘记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/dashBoard/delete?ids=" + ids, function(data){
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
      $('#dashBoardTable').bootstrapTable('refresh');
  }


//新增表单页面
function add() {
    jp.openSaveDialog('新增仪表盘', "${ctx}/dashBoard/form/add",'80%', '70%');
}
//编辑表单页面
function edit(id){
    if(!id){
        id = getIdSelections();
    }
    jp.openSaveDialog('编辑仪表盘', "${ctx}/dashBoard/form/edit?id=" + id,'80%', '70%');
}
  //查看表单页面
  function view(id) {
      if(!id){
          id = getIdSelections();
      }
      jp.go( "${ctx}/dashBoard/form/view?id=" + id);
  }

  function design(row) {
      if(!row){
          row = getSelections()[0];
      }
      jp.openTab("${ctx}/dashBoard/design?id=" + row.id, row.name, false);
  }
  function preview(row) {
	  if(!row){
		  row = getSelections()[0];
	  }
    jp.openTab("${ctx}/dashBoard/preview?id=" + row.id, row.name, false);
  }

  function createMenu(id) {
  	if(!id){
  		id = getIdSelections();
	}
      jp.openSaveDialog('新建菜单', '${ctx}/dashBoard/createMenu?id='+id,'80%', '70%')
  }
 //子表展示
		   
  function detailFormatter(index, row) {
	  var htmltpl =  $("#dashBoardChildrenTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
	  var html = Mustache.render(htmltpl, {
			idx:row.id
		});
	  $.get("${ctx}/dashBoard/detail?id="+row.id, function(dashBoard){
    	var dashBoardChild1RowIdx = 0, dashBoardChild1Tpl = $("#dashBoardChild1Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data1 =  dashBoard.containerList;
		for (var i=0; i<data1.length; i++){
			data1[i].dict = {};
			addRow('#dashBoardChild-'+row.id+'-1-List', dashBoardChild1RowIdx, dashBoardChild1Tpl, data1[i]);
			dashBoardChild1RowIdx = dashBoardChild1RowIdx + 1;
		}
				
      	  			
      })
     
        return html;
    }
  
	function addRow(list, idx, tpl, row){
		$(list).append(Mustache.render(tpl, {
			idx: idx, delBtn: true, row: row
		}));
	}
</script>

