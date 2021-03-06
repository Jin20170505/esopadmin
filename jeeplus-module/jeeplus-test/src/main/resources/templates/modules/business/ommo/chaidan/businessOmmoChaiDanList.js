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
    $("#isprint").val(node.id);
    $('#businessOmmoChaiDanTable').bootstrapTable('refresh',opt);
}).on('loaded.jstree', function() {
    $("#businessProductTypeOnlyReadjsTree").jstree('open_all');
});
});
$(document).ready(function() {
	$('#businessOmmoChaiDanTable').bootstrapTable({
		 
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
               url: "${ctx}/business/ommo/chaidan/businessOmmoChaiDan/data",
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
               title: '打印状态',
               sortable: true,
               sortName: 'printstatus'

           }
			,{
		        field: 'code',
		        title: '单号',
		        sortable: true,
		        sortName: 'code'
		        ,formatter:function(value, row , index){
		        	  <% if(shiro.hasPermission("business:ommo:chaidan:businessOmmoChaiDan:edit") ){ %>
					   if(!value){
						  return "<a  href='#' onclick='edit(\""+row.id+"\")'>-</a>";
					   }else{
						  return "<a  href='#' onclick='edit(\""+row.id+"\")'>"+value+"</a>";
						}
                     <% }else if(shiro.hasPermission("business:ommo:chaidan:businessOmmoChaiDan:view")){ %>
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
		        field: 'wwcode',
		        title: '委外单号',
		        sortable: true,
		        sortName: 'wwcode'
		       
		    }
			,{
		        field: 'wwline',
		        title: '委外行号',
		        sortable: true,
		        sortName: 'wwline'

		    },{
            field: 'vendorname',
            title: '供应商名称'
            },{
            field: 'ddrq',
            title: '订单日期'
            }
			,{
		        field: 'startdate',
		        title: '计划下达日期',
		        sortable: true,
		        sortName: 'startdate'
		       
		    }
			,{
		        field: 'arrivedate',
		        title: '预计到货日期',
		        sortable: true,
		        sortName: 'arrivedate'
		       
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
		        field: 'memo',
		        title: '备注',
		        sortable: true,
		        sortName: 'memo'
		       
		    }
		     ]
		
		});
		

	  $('#businessOmmoChaiDanTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#businessOmmoChaiDanTable').bootstrapTable('getSelections').length);
            $('#edit,#print').prop('disabled', $('#businessOmmoChaiDanTable').bootstrapTable('getSelections').length!=1);
        });

	 $("#import").click(function(){//显示导入面板
            $("#search-collapse").hide();
            $("#import-collapse").fadeToggle()

      })

	 $("#btnImportExcel").click(function(){//导入Excel
		 var importForm = $('#importForm')[0];
		 jp.block('#import-collapse',"文件上传中...");
		 jp.uploadFile(importForm,"${ctx}/business/ommo/chaidan/businessOmmoChaiDan/import",function (data) {
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
            jp.downloadFile('${ctx}/business/ommo/chaidan/businessOmmoChaiDan/import/template');
		})

	 $("#export").click(function(){//导出Excel文件
	        var searchParam = $("#searchForm").serializeJSON();
	        searchParam.pageNo = 1;
	        searchParam.pageSize = -1;
            var sortName = $('#businessOmmoChaiDanTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#businessOmmoChaiDanTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/business/ommo/chaidan/businessOmmoChaiDan/export?'+values);
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

	 $('#startdate').datepicker({//日期控件初始化
			toggleActive: true,
			language:"zh-CN",
    			format:"yyyy-mm-dd"
		});
	 $('#arrivedate').datepicker({//日期控件初始化
			toggleActive: true,
			language:"zh-CN",
    			format:"yyyy-mm-dd"
		});
		
	});

	//获取选中行
  function getIdSelections() {
        return $.map($("#businessOmmoChaiDanTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

  //删除
  function del(ids){
     if(!ids){
          ids = getIdSelections();
     }
	 jp.confirm('确认要删除该委外拆单记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/business/ommo/chaidan/businessOmmoChaiDan/delete?ids=" + ids, function(data){
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
      $('#businessOmmoChaiDanTable').bootstrapTable('refresh');
  }
  function printbl(){
  var rid = getIdSelections();
    jp.windowOpen('${ctx}/business/ommo/chaidan/businessOmmoChaiDan/goToPrint?rid='+rid,"委外订单--打印",window.screen.width*0.9,window.screen.height);
}
   //新增表单页面
 function add() {
     jp.openSaveDialog('新增委外拆单', "${ctx}/business/ommo/chaidan/businessOmmoChaiDan/form/add",'100%', '100%');
 }
  //编辑表单页面
  function edit(id){
      if(!id){
          id = getIdSelections();
      }
	  jp.openSaveDialog('编辑委外拆单', "${ctx}/business/ommo/chaidan/businessOmmoChaiDan/form/edit?id="+id,'100%', '100%');
  }
  //查看表单页面
  function view(id) {
      if(!id){
          id = getIdSelections();
      }
      jp.openViewDialog('查看委外拆单', "${ctx}/business/ommo/chaidan/businessOmmoChaiDan/form/view?id="+id,'100%', '100%');
  }
 //子表展示
		   
  function detailFormatter(index, row) {
	  var htmltpl =  $("#businessOmmoChaiDanChildrenTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
	  var html = Mustache.render(htmltpl, {
			idx:row.id
		});
	  $.get("${ctx}/business/ommo/chaidan/businessOmmoChaiDan/detail?id="+row.id, function(businessOmmoChaiDan){
    	var businessOmmoChaiDanChild1RowIdx = 0, businessOmmoChaiDanChild1Tpl = $("#businessOmmoChaiDanChild1Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data1 =  businessOmmoChaiDan.businessOmmoChaiDanMxList;
		for (var i=0; i<data1.length; i++){
			data1[i].dict = {};
			addRow('#businessOmmoChaiDanChild-'+row.id+'-1-List', businessOmmoChaiDanChild1RowIdx, businessOmmoChaiDanChild1Tpl, data1[i]);
			businessOmmoChaiDanChild1RowIdx = businessOmmoChaiDanChild1RowIdx + 1;
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
<script type="text/template" id="businessOmmoChaiDanChildrenTpl">//<!--
	<div class="card card-tabs">
	<div class="card-heading  pb-0">
	    <ul class="nav nav-pills float-left" role="tablist">
				<li class="nav-item"><a data-toggle="tab" class="nav-link show active" href="#tab-{{idx}}-1" aria-expanded="true">委外发料拆单明细</a></li>
		</ul>
		</div>
		<div class="card-body">
		<div class="tab-content">
				 <div id="tab-{{idx}}-1" class="tab-pane fade active show" >
						<table class="table table-bordered">
						<thead>
							<tr>
								<th>行号</th>
								<th>存货编码</th>
								<th>存货名称</th>
								<th>规格型号</th>
								<th>批号</th>
								<th>数量</th>
								<th>单位</th>
								<th>仓库</th>
								<th>货位</th>
								<th>需求日期</th>
							</tr>
						</thead>
						<tbody id="businessOmmoChaiDanChild-{{idx}}-1-List">
						</tbody>
					</table>
				</div>
		</div>
		</div>
		</div>//-->
	</script>
	<script type="text/template" id="businessOmmoChaiDanChild1Tpl">//<!--
				<tr>
					<td>
						{{row.no}}
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
						{{row.batchno}}
					</td>
					<td>
						{{row.num}}
					</td>
					<td>
						{{row.unit}}
					</td>
					<td>
						{{row.ck}}
					</td>
					<td>
						{{row.hw}}
					</td>
					<td>
						{{row.requireddate}}
					</td>
				</tr>//-->
	</script>
