<script>
$(document).ready(function() {
	$('#formmain0153Table').bootstrapTable({
		 
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
    	       //显示详情按钮
    	       detailView: true,
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
               url: "${ctx}/oadata/tiaoxin/formmain0153/data",
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
		       
		    }/*
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
		        field: 'field0008',
		        title: '审批意见',
		        sortable: true,
		        sortName: 'field0008'
		       
		    }*/
			,{
		        field: 'field0027',
		        title: '调薪理由',
		        sortable: true,
		        sortName: 'field0027'
		       
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
					<% if(shiro.hasPermission("oadata:tiaoxin:formmain0153:view")){ %>
					   '<a class="view btn btn-icon waves-effect waves-light btn-custom btn-xs m-r-5"> <i class="fa fa-search"></i></a>',
				   <% } %>
				   <% if(shiro.hasPermission("oadata:tiaoxin:formmain0153:edit")){ %>
					   '<a class="edit btn btn-icon waves-effect waves-light btn-success btn-xs m-r-5"> <i class="fa fa-pencil"></i></a>',
				   <% } %>
				   <% if(shiro.hasPermission("oadata:tiaoxin:formmain0153:del")){ %>
					   '<a class="del btn btn-icon waves-effect waves-light btn-danger btn-xs"> <i class="fa fa-trash-o"></a>'
				   <% } %>
				   ].join('');
			   }
		   }
		     ]
		
		});
		

	  $('#formmain0153Table').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#formmain0153Table').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#formmain0153Table').bootstrapTable('getSelections').length!=1);
        });

	 $("#import").click(function(){//显示导入面板
            $("#search-collapse").hide();
            $("#import-collapse").fadeToggle()

      })

	 $("#btnImportExcel").click(function(){//导入Excel
		 var importForm = $('#importForm')[0];
		 jp.block('#import-collapse',"文件上传中...");
		 jp.uploadFile(importForm,"${ctx}/oadata/tiaoxin/formmain0153/import",function (data) {
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
            jp.downloadFile('${ctx}/oadata/tiaoxin/formmain0153/import/template');
		})

	 $("#export").click(function(){//导出Excel文件
	        var searchParam = $("#searchForm").serializeJSON();
	        searchParam.pageNo = 1;
	        searchParam.pageSize = -1;
            var sortName = $('#formmain0153Table').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#formmain0153Table').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/oadata/tiaoxin/formmain0153/export?'+values);
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
        return $.map($("#formmain0153Table").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

  //删除
  function del(ids){
     if(!ids){
          ids = getIdSelections();
     }
	 jp.confirm('确认要删除该调薪申请记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/oadata/tiaoxin/formmain0153/delete?ids=" + ids, function(data){
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
      $('#formmain0153Table').bootstrapTable('refresh');
  }

   //新增表单页面
 function add() {
     jp.openSaveDialog('新增调薪申请', "${ctx}/oadata/tiaoxin/formmain0153/form/add",'90%', '90%');
 }
  //编辑表单页面
  function edit(id){
      if(!id){
          id = getIdSelections();
      }
	  jp.openSaveDialog('编辑调薪申请', "${ctx}/oadata/tiaoxin/formmain0153/form/edit?id="+id,'90%', '90%');
  }
  //查看表单页面
  function view(id) {
      if(!id){
          id = getIdSelections();
      }
      jp.openViewDialog('查看调薪申请', "${ctx}/oadata/tiaoxin/formmain0153/form/view?id="+id,'90%', '90%');
  }
 //子表展示
		   
  function detailFormatter(index, row) {
	  var htmltpl =  $("#formmain0153ChildrenTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
	  var html = Mustache.render(htmltpl, {
			idx:row.id
		});
	  $.get("${ctx}/oadata/tiaoxin/formmain0153/detail?id="+row.id, function(formmain0153){
    	var formmain0153Child1RowIdx = 0, formmain0153Child1Tpl = $("#formmain0153Child1Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data1 =  formmain0153.formson0154List;
		for (var i=0; i<data1.length; i++){
			data1[i].dict = {};
			addRow('#formmain0153Child-'+row.id+'-1-List', formmain0153Child1RowIdx, formmain0153Child1Tpl, data1[i]);
			formmain0153Child1RowIdx = formmain0153Child1RowIdx + 1;
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
<script type="text/template" id="formmain0153ChildrenTpl">//<!--
	<div class="card card-tabs">
	<div class="card-heading  pb-0">
	    <ul class="nav nav-pills float-left" role="tablist">
				<li class="nav-item"><a data-toggle="tab" class="nav-link show active" href="#tab-{{idx}}-1" aria-expanded="true">调薪申请明细表</a></li>
		</ul>
		</div>
		<div class="card-body">
		<div class="tab-content">
				 <div id="tab-{{idx}}-1" class="tab-pane fade active show" >
						<table class="table table-bordered">
						<thead>
							<tr>
								<th>序号</th>
								<th>员工id</th>
								<th>员工姓名</th>
								<th>现基本工资</th>
								<th>现岗位工资</th>
								<th>现绩效工资</th>
								<th>现保密费</th>
								<th>现通讯费</th>
								<th>调前总月薪</th>
								<th>调薪生效时间</th>
								<th>调整后基本工资</th>
								<th>调整后岗位工资</th>
								<th>调整后绩效工资</th>
								<th>调整后保密费</th>
								<th>调整后通讯费</th>
								<th>调后总月薪</th>
								<th>调薪幅度</th>
							</tr>
						</thead>
						<tbody id="formmain0153Child-{{idx}}-1-List">
						</tbody>
					</table>
				</div>
		</div>
		</div>
		</div>//-->
	</script>
	<script type="text/template" id="formmain0153Child1Tpl">//<!--
				<tr>
					<td>
						{{row.field0006}}
					</td>
					<td>
						{{row.field0011}}
					</td>
					<td>
						{{row.field0012}}
					</td>
					<td>
						{{row.field0007}}
					</td>
					<td>
						{{row.field0013}}
					</td>
					<td>
						{{row.field0014}}
					</td>
					<td>
						{{row.field0015}}
					</td>
					<td>
						{{row.field0025}}
					</td>
					<td>
						{{row.field0016}}
					</td>
					<td>
						{{row.field0017}}
					</td>
					<td>
						{{row.field0018}}
					</td>
					<td>
						{{row.field0019}}
					</td>
					<td>
						{{row.field0020}}
					</td>
					<td>
						{{row.field0021}}
					</td>
					<td>
						{{row.field0026}}
					</td>
					<td>
						{{row.field0022}}
					</td>
					<td>
						{{row.field0023}}
					</td>
				</tr>//-->
	</script>
