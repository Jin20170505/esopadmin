<script>
$(document).ready(function() {
	$('#businessPanDianTable').bootstrapTable({
		 
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
               url: "${ctx}/business/pandian/businessPanDian/data",
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
		        title: '盘点单号',
		        sortable: true,
		        sortName: 'code'
		        ,formatter:function(value, row , index){
                           return "<a  href='#' onclick='view(\""+row.id+"\")'>"+value+"</a>";
		         }
		       
		    }
			,{
		        field: 'ddate',
		        title: '盘点日期',
		        sortable: true,
		        sortName: 'ddate'
		       
		    }
			,{
		        field: 'duser.name',
		        title: '盘点人',
		        sortable: true,
		        sortName: 'duser.name'
		       
		    }
			,{
		        field: 'ck.name',
		        title: '盘点仓库',
		        sortable: true,
		        sortName: 'ck.name'
		       
		    }
			,{
		        field: 'hw.name',
		        title: '盘点货位',
		        sortable: true,
		        sortName: 'hw.name'
		       
		    }
		     ]
		
		});
		

	  $('#businessPanDianTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#businessPanDianTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#businessPanDianTable').bootstrapTable('getSelections').length!=1);
        });

	 $("#import").click(function(){//显示导入面板
            $("#search-collapse").hide();
            $("#import-collapse").fadeToggle()

      })

	 $("#btnImportExcel").click(function(){//导入Excel
		 var importForm = $('#importForm')[0];
		 jp.block('#import-collapse',"文件上传中...");
		 jp.uploadFile(importForm,"${ctx}/business/pandian/businessPanDian/import",function (data) {
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
            jp.downloadFile('${ctx}/business/pandian/businessPanDian/import/template');
		})

	 $("#export").click(function(){//导出Excel文件
	        var searchParam = $("#searchForm").serializeJSON();
	        searchParam.pageNo = 1;
	        searchParam.pageSize = -1;
            var sortName = $('#businessPanDianTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#businessPanDianTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/business/pandian/businessPanDian/export?'+values);
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

	 $('#ddate').datepicker({//日期控件初始化
			toggleActive: true,
			language:"zh-CN",
    			format:"yyyy-mm-dd"
		});
		
	});

	//获取选中行
  function getIdSelections() {
        return $.map($("#businessPanDianTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

  //删除
  function del(ids){
     if(!ids){
          ids = getIdSelections();
     }
	 jp.confirm('确认要删除该盘点单记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/business/pandian/businessPanDian/delete?ids=" + ids, function(data){
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
      $('#businessPanDianTable').bootstrapTable('refresh');
  }

   //新增表单页面
 function add() {
     jp.openSaveDialog('新增盘点单', "${ctx}/business/pandian/businessPanDian/form/add",'90%', '90%');
 }
  //编辑表单页面
  function edit(id){
      if(!id){
          id = getIdSelections();
      }
	  jp.openSaveDialog('编辑盘点单', "${ctx}/business/pandian/businessPanDian/form/edit?id="+id,'90%', '90%');
  }
  //查看表单页面
  function view(id) {
      if(!id){
          id = getIdSelections();
      }
      jp.openViewDialog('查看盘点单', "${ctx}/business/pandian/businessPanDian/form/view?id="+id,'90%', '90%');
  }
 //子表展示
		   
  function detailFormatter(index, row) {
	  var htmltpl =  $("#businessPanDianChildrenTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
	  var html = Mustache.render(htmltpl, {
			idx:row.id
		});
	  $.get("${ctx}/business/pandian/businessPanDian/detail?id="+row.id, function(businessPanDian){
    	var businessPanDianChild1RowIdx = 0, businessPanDianChild1Tpl = $("#businessPanDianChild1Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data1 =  businessPanDian.businessPanDianMxList;
		for (var i=0; i<data1.length; i++){
			data1[i].dict = {};
			addRow('#businessPanDianChild-'+row.id+'-1-List', businessPanDianChild1RowIdx, businessPanDianChild1Tpl, data1[i]);
			businessPanDianChild1RowIdx = businessPanDianChild1RowIdx + 1;
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
<script type="text/template" id="businessPanDianChildrenTpl">//<!--
	<div class="card card-tabs">
	<div class="card-heading  pb-0">
	    <ul class="nav nav-pills float-left" role="tablist">
				<li class="nav-item"><a data-toggle="tab" class="nav-link show active" href="#tab-{{idx}}-1" aria-expanded="true">盘点明细</a></li>
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
								<th>生产日期</th>
								<th>批号</th>
								<th>现存数量</th>
								<th>单位</th>
								<th>货位编码</th>
								<th>仓库编码</th>
								<th>备注信息</th>
								<th>实盘数量</th>
								<th>差值</th>
							</tr>
						</thead>
						<tbody id="businessPanDianChild-{{idx}}-1-List">
						</tbody>
					</table>
				</div>
		</div>
		</div>
		</div>//-->
	</script>
	<script type="text/template" id="businessPanDianChild1Tpl">//<!--
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
						{{row.scdate}}
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
						{{row.hwcode}}
					</td>
					<td>
						{{row.ckcode}}
					</td>
					<td>
						{{row.remarks}}
					</td>
					<td>
						{{row.pannum}}
					</td>
					<td>
						{{row.cha}}
					</td>
				</tr>//-->
	</script>
