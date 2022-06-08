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
    'printstatus':node.id
}
};
    $("#printstatus").val(node.id);
    $('#businessShengChanBeiLiaoApplyTable').bootstrapTable('refresh',opt);
}).on('loaded.jstree', function() {
    $("#businessProductTypeOnlyReadjsTree").jstree('open_all');
});
});
$(document).ready(function() {
	$('#businessShengChanBeiLiaoApplyTable').bootstrapTable({
		 
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
               url: "${ctx}/business/shengchan/beiliao/apply/businessShengChanBeiLiaoApply/data",
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
		        field: 'sccode',
		        title: '生产编码',
		        sortable: true,
		        sortName: 'sccode'
		        ,formatter:function(value, row , index){
		        	  <% if(shiro.hasPermission("business:shengchan:beiliao:apply:businessShengChanBeiLiaoApply:edit") ){ %>
					   if(!value){
						  return "<a  href='#' onclick='edit(\""+row.id+"\")'>-</a>";
					   }else{
						  return "<a  href='#' onclick='edit(\""+row.id+"\")'>"+value+"</a>";
						}
                     <% }else if(shiro.hasPermission("business:shengchan:beiliao:apply:businessShengChanBeiLiaoApply:view")){ %>
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
		        field: 'scline',
		        title: '生产行号',
		        sortable: true,
		        sortName: 'scline'
		       
		    }
			,{
		        field: 'dept.name',
		        title: '生产部门',
		        sortable: true,
		        sortName: 'dept.name'
		       
		    }
			,{
		        field: 'cinvcode',
		        title: '产品编码',
		        sortable: true,
		        sortName: 'cinvcode'
		       
		    }
			,{
		        field: 'cinvname',
		        title: '产品名称',
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
					<% if(shiro.hasPermission("business:shengchan:beiliao:apply:businessShengChanBeiLiaoApply:view")){ %>
					   '<a class="view btn btn-icon waves-effect waves-light btn-custom btn-xs m-r-5"> <i class="fa fa-search"></i></a>',
				   <% } %>
				   <% if(shiro.hasPermission("business:shengchan:beiliao:apply:businessShengChanBeiLiaoApply:edit")){ %>
					   '<a class="edit btn btn-icon waves-effect waves-light btn-success btn-xs m-r-5"> <i class="fa fa-pencil"></i></a>',
				   <% } %>
				   <% if(shiro.hasPermission("business:shengchan:beiliao:apply:businessShengChanBeiLiaoApply:del")){ %>
					   '<a class="del btn btn-icon waves-effect waves-light btn-danger btn-xs"> <i class="fa fa-trash-o"></a>'
				   <% } %>
				   ].join('');
			   }
		   }
		     ]
		
		});
		

	  $('#businessShengChanBeiLiaoApplyTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#businessShengChanBeiLiaoApplyTable').bootstrapTable('getSelections').length);
            $('#edit,#print').prop('disabled', $('#businessShengChanBeiLiaoApplyTable').bootstrapTable('getSelections').length!=1);
        });

	 $("#import").click(function(){//显示导入面板
            $("#search-collapse").hide();
            $("#import-collapse").fadeToggle()

      })

	 $("#btnImportExcel").click(function(){//导入Excel
		 var importForm = $('#importForm')[0];
		 jp.block('#import-collapse',"文件上传中...");
		 jp.uploadFile(importForm,"${ctx}/business/shengchan/beiliao/apply/businessShengChanBeiLiaoApply/import",function (data) {
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
            jp.downloadFile('${ctx}/business/shengchan/beiliao/apply/businessShengChanBeiLiaoApply/import/template');
		})

	 $("#export").click(function(){//导出Excel文件
	        var searchParam = $("#searchForm").serializeJSON();
	        searchParam.pageNo = 1;
	        searchParam.pageSize = -1;
            var sortName = $('#businessShengChanBeiLiaoApplyTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#businessShengChanBeiLiaoApplyTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/business/shengchan/beiliao/apply/businessShengChanBeiLiaoApply/export?'+values);
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
        return $.map($("#businessShengChanBeiLiaoApplyTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
 // 打印备料单
  function printbl(){
  var rid = getIdSelections();
  jp.windowOpen('${ctx}/business/shengchan/beiliao/apply/businessShengChanBeiLiaoApply/goToBeiLiaoPrint?rid='+rid,"备料单--打印",window.screen.height,window.screen.width);
}
  //删除
  function del(ids){
     if(!ids){
          ids = getIdSelections();
     }
	 jp.confirm('确认要删除该生产备料记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/business/shengchan/beiliao/apply/businessShengChanBeiLiaoApply/delete?ids=" + ids, function(data){
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
      $('#businessShengChanBeiLiaoApplyTable').bootstrapTable('refresh');
  }

   //新增表单页面
 function add() {
     jp.openSaveDialog('新增生产备料', "${ctx}/business/shengchan/beiliao/apply/businessShengChanBeiLiaoApply/form/add",'90%', '90%');
 }
  //编辑表单页面
  function edit(id){
      if(!id){
          id = getIdSelections();
      }
	  jp.openSaveDialog('编辑生产备料', "${ctx}/business/shengchan/beiliao/apply/businessShengChanBeiLiaoApply/form/edit?id="+id,'90%', '90%');
  }
  //查看表单页面
  function view(id) {
      if(!id){
          id = getIdSelections();
      }
      jp.openViewDialog('查看生产备料', "${ctx}/business/shengchan/beiliao/apply/businessShengChanBeiLiaoApply/form/view?id="+id,'90%', '90%');
  }
 //子表展示
		   
  function detailFormatter(index, row) {
	  var htmltpl =  $("#businessShengChanBeiLiaoApplyChildrenTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
	  var html = Mustache.render(htmltpl, {
			idx:row.id
		});
	  $.get("${ctx}/business/shengchan/beiliao/apply/businessShengChanBeiLiaoApply/detail?id="+row.id, function(businessShengChanBeiLiaoApply){
    	var businessShengChanBeiLiaoApplyChild1RowIdx = 0, businessShengChanBeiLiaoApplyChild1Tpl = $("#businessShengChanBeiLiaoApplyChild1Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data1 =  businessShengChanBeiLiaoApply.businessShengchanBeiliaoApplyMxList;
		for (var i=0; i<data1.length; i++){
			data1[i].dict = {};
			addRow('#businessShengChanBeiLiaoApplyChild-'+row.id+'-1-List', businessShengChanBeiLiaoApplyChild1RowIdx, businessShengChanBeiLiaoApplyChild1Tpl, data1[i]);
			businessShengChanBeiLiaoApplyChild1RowIdx = businessShengChanBeiLiaoApplyChild1RowIdx + 1;
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
<script type="text/template" id="businessShengChanBeiLiaoApplyChildrenTpl">//<!--
	<div class="card card-tabs">
	<div class="card-heading  pb-0">
	    <ul class="nav nav-pills float-left" role="tablist">
				<li class="nav-item"><a data-toggle="tab" class="nav-link show active" href="#tab-{{idx}}-1" aria-expanded="true">备料明细</a></li>
		</ul>
		</div>
		<div class="card-body">
		<div class="tab-content">
				 <div id="tab-{{idx}}-1" class="tab-pane fade active show" >
						<table class="table table-bordered">
						<thead>
							<tr>
								<th>序号</th>
								<th>存货编码</th>
								<th>存货名称</th>
								<th>规格型号</th>
								<th>数量</th>
								<th>单位</th>
							</tr>
						</thead>
						<tbody id="businessShengChanBeiLiaoApplyChild-{{idx}}-1-List">
						</tbody>
					</table>
				</div>
		</div>
		</div>
		</div>//-->
	</script>
	<script type="text/template" id="businessShengChanBeiLiaoApplyChild1Tpl">//<!--
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
						{{row.num}}
					</td>
					<td>
						{{row.unit}}
					</td>
				</tr>//-->
	</script>
