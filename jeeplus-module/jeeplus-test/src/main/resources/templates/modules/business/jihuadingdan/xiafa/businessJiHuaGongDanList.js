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
    "url" : "${ctx}//business/jihuadingdan/businessJiHuaGongDan/treeData",
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
    'isshengcheng':node.id
}
};
    $("#isshengcheng").val(node.id);
    $('#businessJiHuaGongDanTable').bootstrapTable('refresh',opt);
}).on('loaded.jstree', function() {
    $("#businessProductTypeOnlyReadjsTree").jstree('open_all');
});
});
$(document).ready(function() {
	$('#businessJiHuaGongDanTable').bootstrapTable({
		 
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
               url: "${ctx}/business/jihuadingdan/businessJiHuaGongDan/data",
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
               	searchParam.status = '已下发';
			    return searchParam;
               },
               onShowSearch: function () {
               	 $("#import-collapse").hide();
				 $("#search-collapse").fadeToggle();
               },
               columns: [{
		        checkbox: true
		       
		    }
                   , {
                       field: 'isshengcheng',
                       title: '是否生产报工单'
                   }
			,{
		        field: 'code',
		        title: '单号',
		        sortable: true,
		        sortName: 'code'
		        ,formatter:function(value, row , index){
                   if(!value){
                       return "<a  href='#' onclick='view(\""+row.id+"\")'>-</a>";
                   }else{
                       return "<a  href='#' onclick='view(\""+row.id+"\")'>"+value+"</a>";
                   }
		         }
		       
		    }
			,{
		        field: 'dd.code',
		        title: '生产订单号',
		        sortable: true,
		        sortName: 'dd.code'
		       
		    }
			,{
		        field: 'orderno',
		        title: '生产订单行号',
		        sortable: true,
		        sortName: 'orderno'
		       
		    }
           ,{
               field: 'dept.name',
               title: '生产部门',
               sortable: true,
               sortName: 'dept.name'
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
		        field: 'unit',
		        title: '计量单位',
		        sortable: true,
		        sortName: 'unit'
		       
		    }
			,{
		        field: 'startdate',
		        title: '开始日期',
		        sortable: true,
		        sortName: 'startdate'
		       
		    }
			,{
		        field: 'enddate',
		        title: '结束日期',
		        sortable: true,
		        sortName: 'enddate'
		       
		    }
			,{
		        field: 'scnum',
		        title: '生产数量',
		        sortable: true,
		        sortName: 'scnum'
		       
		    }
			,{
		        field: 'gdnum',
		        title: '工单数量',
		        sortable: true,
		        sortName: 'gdnum'
		       
		    },{
                       field: 'batchno',
                       title: '生产批号',
                       sortable: true,
                       sortName: 'batchno'

                   }
		     ]
		
		});
		

	  $('#businessJiHuaGongDanTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove,#chehui,#shengchengbaogongdan').prop('disabled', ! $('#businessJiHuaGongDanTable').bootstrapTable('getSelections').length);
            $('#edit,#xiafa').prop('disabled', $('#businessJiHuaGongDanTable').bootstrapTable('getSelections').length!=1);
        });

	 $("#import").click(function(){//显示导入面板
            $("#search-collapse").hide();
            $("#import-collapse").fadeToggle()

      })

	 $("#btnImportExcel").click(function(){//导入Excel
		 var importForm = $('#importForm')[0];
		 jp.block('#import-collapse',"文件上传中...");
		 jp.uploadFile(importForm,"${ctx}/business/jihuadingdan/businessJiHuaGongDan/import",function (data) {
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
            jp.downloadFile('${ctx}/business/jihuadingdan/businessJiHuaGongDan/import/template');
		})

	 $("#export").click(function(){//导出Excel文件
	        var searchParam = $("#searchForm").serializeJSON();
	        searchParam.pageNo = 1;
	        searchParam.pageSize = -1;
            var sortName = $('#businessJiHuaGongDanTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#businessJiHuaGongDanTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/business/jihuadingdan/businessJiHuaGongDan/export?'+values);
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

  $('#startdate').datetimepicker({
  format:'Y-m-d'
});
  $('#enddate').datetimepicker({
  format:'Y-m-d'
});
		
	});
   // 下发
function xiafa(){
    var ids = getIdSelections();
    jp.confirm('确认要下发该计划工单吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/business/jihuadingdan/businessJiHuaGongDan/xiafa?ids=" + ids, function(data){
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
   // 撤回
function chehui(){
    var ids = getIdSelections();
    jp.confirm('确认要撤回该计划工单吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/business/jihuadingdan/businessJiHuaGongDan/chehui?ids=" + ids, function(data){
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
// 生成报工单
function shengchengbaogongdan(){
    var rows = getRowSelections();
    var ids = getIdSelections();
    var deptid  = rows[0].dept.id;
    var deptname = rows[0].dept.name;
	 jp.confirm('确认要生成报工单吗？', function(){
	     jp.get("${ctx}/business/shengchan/paichan/dept/businessShengChanPaiChanDept/isYaoHao?deptid="+deptid,function (rs){
	         if(rs.success){
                jp.prompt('请输入【'+deptname+'】对应的窑炉号',function(yaocode){
                    if(yaocode){
                        shengchan(ids,yaocode);
                    }else{

                    }
                })
            }else{
                shengchan(ids,"");
            }
        });
	 })
}

function shengchan(ids,yaocode){
    var index =jp.loading();
    jp.get("${ctx}/business/jihuadingdan/businessJiHuaGongDan/shengchengbaogongdan?rids=" + ids+"&yaocode="+yaocode, function(data){
    if(data.success){
    refresh();
    jp.toastr_success(data.msg);
}else{
    jp.toastr_error(data.msg);
}
    jp.close(index);
})
}
	//获取选中行
  function getIdSelections() {
        return $.map($("#businessJiHuaGongDanTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

    function getRowSelections() {
        return $.map($("#businessJiHuaGongDanTable").bootstrapTable('getSelections'), function (row) {
        return row
    });
    }

  //删除
  function del(ids){
     if(!ids){
          ids = getIdSelections();
     }
	 jp.confirm('确认要删除该计划工单记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/business/jihuadingdan/businessJiHuaGongDan/delete?ids=" + ids, function(data){
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
      $('#businessJiHuaGongDanTable').bootstrapTable('refresh');
  }

   //新增表单页面
 function add() {
     jp.openSaveDialog('新增计划工单', "${ctx}/business/jihuadingdan/businessJiHuaGongDan/form/add",'90%', '90%');
 }
  //编辑表单页面
  function edit(id){
      if(!id){
          id = getIdSelections();
      }
	  jp.openSaveDialog('编辑计划工单', "${ctx}/business/jihuadingdan/businessJiHuaGongDan/form/edit?id="+id,'90%', '90%');
  }
  //查看表单页面
  function view(id) {
      if(!id){
          id = getIdSelections();
      }
      jp.openViewDialog('查看计划工单', "${ctx}/business/jihuadingdan/businessJiHuaGongDan/form/view?id="+id,'90%', '90%');
  }
 //子表展示
		   
  function detailFormatter(index, row) {
	  var htmltpl =  $("#businessJiHuaGongDanChildrenTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
	  var html = Mustache.render(htmltpl, {
			idx:row.id
		});
	  $.get("${ctx}/business/jihuadingdan/businessJiHuaGongDan/detail?id="+row.id, function(businessJiHuaGongDan){
    	var businessJiHuaGongDanChild1RowIdx = 0, businessJiHuaGongDanChild1Tpl = $("#businessJiHuaGongDanChild1Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data1 =  businessJiHuaGongDan.businessJiHuaGongDanMingXiList;
		for (var i=0; i<data1.length; i++){
			data1[i].dict = {};
			addRow('#businessJiHuaGongDanChild-'+row.id+'-1-List', businessJiHuaGongDanChild1RowIdx, businessJiHuaGongDanChild1Tpl, data1[i]);
			businessJiHuaGongDanChild1RowIdx = businessJiHuaGongDanChild1RowIdx + 1;
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
<script type="text/template" id="businessJiHuaGongDanChildrenTpl">//<!--
	<div class="card card-tabs">
	<div class="card-heading  pb-0">
	    <ul class="nav nav-pills float-left" role="tablist">
				<li class="nav-item"><a data-toggle="tab" class="nav-link show active" href="#tab-{{idx}}-1" aria-expanded="true">计划工单明细</a></li>
		</ul>
		</div>
		<div class="card-body">
		<div class="tab-content">
				 <div id="tab-{{idx}}-1" class="tab-pane fade active show" >
						<table class="table table-bordered">
						<thead>
							<tr>
								<th>序号</th>
								<th>工作站</th>
								<th>加工数量</th>
								<th>人员工号</th>
								<th>人员名称</th>
								<th>班组</th>
								<th>备注信息</th>
							</tr>
						</thead>
						<tbody id="businessJiHuaGongDanChild-{{idx}}-1-List">
						</tbody>
					</table>
				</div>
		</div>
		</div>
		</div>//-->
	</script>
	<script type="text/template" id="businessJiHuaGongDanChild1Tpl">//<!--
				<tr>
					<td>
						{{row.no}}
					</td>
					<td>
						{{row.site.name}}
					</td>
					<td>
						{{row.num}}
					</td>
					<td>
						{{row.userno}}
					</td>
					<td>
						{{row.username}}
					</td>
					<td>
						{{row.classgroup.name}}
					</td>
					<td>
						{{row.remarks}}
					</td>
				</tr>//-->
	</script>
