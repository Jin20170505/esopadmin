<script>
$(document).ready(function() {
	$('#businessRukuCaiGouTable').bootstrapTable({
		 
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
               url: "${ctx}/business/ruku/caigou/businessRukuCaiGou/data",
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
		        field: 'arrivalcode',
		        title: '到货单号',
		        sortable: true,
		        sortName: 'arrivalcode'
		       
		    }
			,{
		        field: 'arrivaldate',
		        title: '到货日期',
		        sortable: true,
		        sortName: 'arrivaldate'
		       
		    }
			// ,{
		    //     field: 'ck.name',
		    //     title: '仓库',
		    //     sortable: true,
		    //     sortName: 'ck.name'
		    //
		    // }
			,{
		        field: 'hw.name',
		        title: '货位',
		        sortable: true,
		        sortName: 'hw.name'
		       
		    }
			,{
		        field: 'u8code',
		        title: 'U8单号',
		        sortable: true,
		        sortName: 'u8code'
		       
		    }
		     ]
		
		});
		

	  $('#businessRukuCaiGouTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove,#u8in').prop('disabled', ! $('#businessRukuCaiGouTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#businessRukuCaiGouTable').bootstrapTable('getSelections').length!=1);
        });

	 $("#import").click(function(){//显示导入面板
            $("#search-collapse").hide();
            $("#import-collapse").fadeToggle()

      })

	 $("#btnImportExcel").click(function(){//导入Excel
		 var importForm = $('#importForm')[0];
		 jp.block('#import-collapse',"文件上传中...");
		 jp.uploadFile(importForm,"${ctx}/business/ruku/caigou/businessRukuCaiGou/import",function (data) {
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
            jp.downloadFile('${ctx}/business/ruku/caigou/businessRukuCaiGou/import/template');
		})

	 $("#export").click(function(){//导出Excel文件
	        var searchParam = $("#searchForm").serializeJSON();
	        searchParam.pageNo = 1;
	        searchParam.pageSize = -1;
            var sortName = $('#businessRukuCaiGouTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#businessRukuCaiGouTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/business/ruku/caigou/businessRukuCaiGou/export?'+values);
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
        return $.map($("#businessRukuCaiGouTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
    function getCodeSelections() {
    return $.map($("#businessRukuCaiGouTable").bootstrapTable('getSelections'), function (row) {
    return row.code
});
}
    function u8in(){
    var rids = getIdSelections();
    var codes = getCodeSelections();
    var index =jp.loading();
    jp.post("${ctx}/business/ruku/caigou/businessRukuCaiGou/u8in",{"rids":rids+"","codes":codes+""},function (data){
    if(data.success){
    refresh();
    jp.toastr_success(data.msg);
}else{
    jp.alert(data.msg);
}
    jp.close(index);
});
}
  //删除
  function del(ids){
     if(!ids){
          ids = getIdSelections();
     }
	 jp.confirm('确认要删除该采购入库记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/business/ruku/caigou/businessRukuCaiGou/delete?ids=" + ids, function(data){
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
      $('#businessRukuCaiGouTable').bootstrapTable('refresh');
  }

   //新增表单页面
 function add() {
     jp.openSaveDialog('新增采购入库', "${ctx}/business/ruku/caigou/businessRukuCaiGou/form/add",'90%', '90%');
 }
  //编辑表单页面
  function edit(id){
      if(!id){
          id = getIdSelections();
      }
	  jp.openSaveDialog('编辑采购入库', "${ctx}/business/ruku/caigou/businessRukuCaiGou/form/edit?id="+id,'90%', '90%');
  }
  //查看表单页面
  function view(id) {
      if(!id){
          id = getIdSelections();
      }
      jp.openViewDialog('查看采购入库', "${ctx}/business/ruku/caigou/businessRukuCaiGou/form/view?id="+id,'90%', '90%');
  }
 //子表展示
		   
  function detailFormatter(index, row) {
	  var htmltpl =  $("#businessRukuCaiGouChildrenTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
	  var html = Mustache.render(htmltpl, {
			idx:row.id
		});
	  $.get("${ctx}/business/ruku/caigou/businessRukuCaiGou/detail?id="+row.id, function(businessRukuCaiGou){
    	var businessRukuCaiGouChild1RowIdx = 0, businessRukuCaiGouChild1Tpl = $("#businessRukuCaiGouChild1Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data1 =  businessRukuCaiGou.businessRukuCaigouMxList;
		for (var i=0; i<data1.length; i++){
			data1[i].dict = {};
			addRow('#businessRukuCaiGouChild-'+row.id+'-1-List', businessRukuCaiGouChild1RowIdx, businessRukuCaiGouChild1Tpl, data1[i]);
			businessRukuCaiGouChild1RowIdx = businessRukuCaiGouChild1RowIdx + 1;
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
<script type="text/template" id="businessRukuCaiGouChildrenTpl">//<!--
	<div class="card card-tabs">
	<div class="card-heading  pb-0">
	    <ul class="nav nav-pills float-left" role="tablist">
				<li class="nav-item"><a data-toggle="tab" class="nav-link show active" href="#tab-{{idx}}-1" aria-expanded="true">采购入库明细</a></li>
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
								<th>数量</th>
								<th>单位</th>
								<th>批号</th>
								<th>生产日期</th>
							</tr>
						</thead>
						<tbody id="businessRukuCaiGouChild-{{idx}}-1-List">
						</tbody>
					</table>
				</div>
		</div>
		</div>
		</div>//-->
	</script>
	<script type="text/template" id="businessRukuCaiGouChild1Tpl">//<!--
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
					<td>
						{{row.batchno}}
					</td>
					<td>
						{{row.scdate}}
					</td>
				</tr>//-->
	</script>
