<script>
$(document).ready(function() {
	$('#businessShengChanDingdanMxTable').bootstrapTable({
		 
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
    	       detailView: true,
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
               url: "${ctx}/business/shengchan/bom/businessShengChanDingdanMx/data",
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
		        field: 'sccode',
		        title: '生产单号',
		        sortable: true,
		        sortName: 'sccode'
		        ,formatter:function(value, row , index){
		        	  <% if(shiro.hasPermission("business:shengchan:bom:businessShengChanDingdanMx:edit") ){ %>
					   if(!value){
						  return "<a  href='#' onclick='edit(\""+row.id+"\")'>-</a>";
					   }else{
						  return "<a  href='#' onclick='edit(\""+row.id+"\")'>"+value+"</a>";
						}
                     <% }else if(shiro.hasPermission("business:shengchan:bom:businessShengChanDingdanMx:view")){ %>
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
		        field: 'lineno',
		        title: '生产行号',
		        sortable: true,
		        sortName: 'lineno'
		       
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
		        field: 'num',
		        title: '数量',
		        sortable: true,
		        sortName: 'num'
		       
		    }
			,{
		        field: 'unit',
		        title: '单位',
		        sortable: true,
		        sortName: 'unit'
		       
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
					<% if(shiro.hasPermission("business:shengchan:bom:businessShengChanDingdanMx:view")){ %>
					   '<a class="view btn btn-icon waves-effect waves-light btn-custom btn-xs m-r-5"> <i class="fa fa-search"></i></a>',
				   <% } %>
				   <% if(shiro.hasPermission("business:shengchan:bom:businessShengChanDingdanMx:edit")){ %>
					   '<a class="edit btn btn-icon waves-effect waves-light btn-success btn-xs m-r-5"> <i class="fa fa-pencil"></i></a>',
				   <% } %>
				   <% if(shiro.hasPermission("business:shengchan:bom:businessShengChanDingdanMx:del")){ %>
					   '<a class="del btn btn-icon waves-effect waves-light btn-danger btn-xs"> <i class="fa fa-trash-o"></a>'
				   <% } %>
				   ].join('');
			   }
		   }
		     ]
		
		});
		

	  $('#businessShengChanDingdanMxTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#businessShengChanDingdanMxTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#businessShengChanDingdanMxTable').bootstrapTable('getSelections').length!=1);
        });

	 $("#import").click(function(){//显示导入面板
            $("#search-collapse").hide();
            $("#import-collapse").fadeToggle()

      })

	 $("#btnImportExcel").click(function(){//导入Excel
		 var importForm = $('#importForm')[0];
		 jp.block('#import-collapse',"文件上传中...");
		 jp.uploadFile(importForm,"${ctx}/business/shengchan/bom/businessShengChanDingdanMx/import",function (data) {
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
            jp.downloadFile('${ctx}/business/shengchan/bom/businessShengChanDingdanMx/import/template');
		})

	 $("#export").click(function(){//导出Excel文件
	        var searchParam = $("#searchForm").serializeJSON();
	        searchParam.pageNo = 1;
	        searchParam.pageSize = -1;
            var sortName = $('#businessShengChanDingdanMxTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#businessShengChanDingdanMxTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/business/shengchan/bom/businessShengChanDingdanMx/export?'+values);
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

	//获取选中行
  function getIdSelections() {
        return $.map($("#businessShengChanDingdanMxTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

  //删除
  function del(ids){
     if(!ids){
          ids = getIdSelections();
     }
	 jp.confirm('确认要删除该生产子件记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/business/shengchan/bom/businessShengChanDingdanMx/delete?ids=" + ids, function(data){
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
      $('#businessShengChanDingdanMxTable').bootstrapTable('refresh');
  }

   //新增表单页面
 function add() {
     jp.openSaveDialog('新增生产子件', "${ctx}/business/shengchan/bom/businessShengChanDingdanMx/form/add",'800px', '500px');
 }
  //编辑表单页面
  function edit(id){
      if(!id){
          id = getIdSelections();
      }
	  jp.openSaveDialog('编辑生产子件', "${ctx}/business/shengchan/bom/businessShengChanDingdanMx/form/edit?id="+id,'800px', '500px');
  }
  //查看表单页面
  function view(id) {
      if(!id){
          id = getIdSelections();
      }
      jp.openViewDialog('查看生产子件', "${ctx}/business/shengchan/bom/businessShengChanDingdanMx/form/view?id="+id,'800px', '500px');
  }
 //子表展示
		   
  function detailFormatter(index, row) {
	  var htmltpl =  $("#businessShengChanDingdanMxChildrenTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
	  var html = Mustache.render(htmltpl, {
			idx:row.id
		});
	  $.get("${ctx}/business/shengchan/bom/businessShengChanDingdanMx/detail?id="+row.id, function(businessShengChanDingdanMx){
    	var businessShengChanDingdanMxChild1RowIdx = 0, businessShengChanDingdanMxChild1Tpl = $("#businessShengChanDingdanMxChild1Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data1 =  businessShengChanDingdanMx.businessShengChanBomList;
		for (var i=0; i<data1.length; i++){
			data1[i].dict = {};
			addRow('#businessShengChanDingdanMxChild-'+row.id+'-1-List', businessShengChanDingdanMxChild1RowIdx, businessShengChanDingdanMxChild1Tpl, data1[i]);
			businessShengChanDingdanMxChild1RowIdx = businessShengChanDingdanMxChild1RowIdx + 1;
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
<script type="text/template" id="businessShengChanDingdanMxChildrenTpl">//<!--
	<div class="card card-tabs">
	<div class="card-heading  pb-0">
	    <ul class="nav nav-pills float-left" role="tablist">
				<li class="nav-item"><a data-toggle="tab" class="nav-link show active" href="#tab-{{idx}}-1" aria-expanded="true">生产订单子件</a></li>
		</ul>
		</div>
		<div class="card-body">
		<div class="tab-content">
				 <div id="tab-{{idx}}-1" class="tab-pane fade active show" >
						<table class="table table-bordered">
						<thead>
							<tr>
								<th>备注信息</th>
								<th>生产行ID</th>
								<th>生产行号</th>
								<th>子件行号</th>
								<th>数量</th>
								<th>已领数量</th>
								<th>存货编码</th>
								<th>存货名称</th>
								<th>规格型号</th>
								<th>产出类型</th>
								<th>单位编码</th>
								<th>单位名称</th>
								<th>换算率</th>
								<th>基本用量_分子</th>
								<th>基本用量_分母</th>
								<th>辅助基本用量</th>
							</tr>
						</thead>
						<tbody id="businessShengChanDingdanMxChild-{{idx}}-1-List">
						</tbody>
					</table>
				</div>
		</div>
		</div>
		</div>//-->
	</script>
	<script type="text/template" id="businessShengChanDingdanMxChild1Tpl">//<!--
				<tr>
					<td>
						{{row.remarks}}
					</td>
					<td>
						{{row.schid.id}}
					</td>
					<td>
						{{row.scline}}
					</td>
					<td>
						{{row.no}}
					</td>
					<td>
						{{row.num}}
					</td>
					<td>
						{{row.donenum}}
					</td>
					<td>
						{{row.cinvcode}}
					</td>
					<td>
						{{row.cinvname}}
					</td>
					<td>
						{{row.cinvstd}}
					</td>
					<td>
						{{row.producttype}}
					</td>
					<td>
						{{row.unitcode}}
					</td>
					<td>
						{{row.unitname}}
					</td>
					<td>
						{{row.rate}}
					</td>
					<td>
						{{row.baseqtyn}}
					</td>
					<td>
						{{row.baseqtyd}}
					</td>
					<td>
						{{row.auxbaseqtyn}}
					</td>
				</tr>//-->
	</script>
