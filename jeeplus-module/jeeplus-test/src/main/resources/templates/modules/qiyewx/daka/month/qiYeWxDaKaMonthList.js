<script>
$(document).ready(function() {
	$('#qiYeWxDaKaMonthTable').bootstrapTable({
		 
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
               url: "${ctx}/qiyewx/daka/month/qiYeWxDaKaMonth/data",
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
               columns: [
                   {
                       field: 'ym',
                       title: '同步年月',
                       sortable: true,
                       sortName: 'ym'
                   }
			,{
		        field: 'recordType',
		        title: '记录类型',
		        sortable: true,
		        sortName: 'recordType',
		        formatter:function(value, row , index){
                    return jp.getDictLabel(${fn.toJson(fn.getDictList('qywx_daka_month_type'))}, value, "-");
		        }
		    }
			,{
		        field: 'name',
		        title: '姓名',
		        sortable: true,
		        sortName: 'name'
		    }
			,{
		        field: 'acctid',
		        title: '帐号',
		        sortable: true,
		        sortName: 'acctid'
		       
		    }
			,{
		        field: 'departsName',
		        title: '打卡人员所在部门',
		        sortable: true,
		        sortName: 'departs_name'
		    }
		     ]
		});
		

	  $('#qiYeWxDaKaMonthTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#qiYeWxDaKaMonthTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#qiYeWxDaKaMonthTable').bootstrapTable('getSelections').length!=1);
        });

	 $("#import").click(function(){//显示导入面板
            $("#search-collapse").hide();
            $("#import-collapse").fadeToggle()

      })

	 $("#btnImportExcel").click(function(){//导入Excel
		 var importForm = $('#importForm')[0];
		 jp.block('#import-collapse',"文件上传中...");
		 jp.uploadFile(importForm,"${ctx}/qiyewx/daka/month/qiYeWxDaKaMonth/import",function (data) {
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
            jp.downloadFile('${ctx}/qiyewx/daka/month/qiYeWxDaKaMonth/import/template');
		})

	 $("#export").click(function(){//导出Excel文件
	        var searchParam = $("#searchForm").serializeJSON();
	        searchParam.pageNo = 1;
	        searchParam.pageSize = -1;
            var sortName = $('#qiYeWxDaKaMonthTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#qiYeWxDaKaMonthTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/qiyewx/daka/month/qiYeWxDaKaMonth/export?'+values);
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
        return $.map($("#qiYeWxDaKaMonthTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

  //删除
  function del(ids){
     if(!ids){
          ids = getIdSelections();
     }
	 jp.confirm('确认要删除该打卡月报记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/qiyewx/daka/month/qiYeWxDaKaMonth/delete?ids=" + ids, function(data){
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
      $('#qiYeWxDaKaMonthTable').bootstrapTable('refresh');
  }
function goToSync(){
    jp.openSaveDialog('同步年月选择', "${ctx}/qiyewx/daka/month/qiYeWxDaKaMonth/goToSync",'600px', '500px');
}
   //新增表单页面
 function add() {
     jp.openSaveDialog('新增打卡月报', "${ctx}/qiyewx/daka/month/qiYeWxDaKaMonth/form/add",'80%', '70%');
 }
  //编辑表单页面
  function edit(id){
      if(!id){
          id = getIdSelections();
      }
	  jp.openSaveDialog('编辑打卡月报', "${ctx}/qiyewx/daka/month/qiYeWxDaKaMonth/form/edit?id="+id,'80%', '70%');
  }
  //查看表单页面
  function view(id) {
      if(!id){
          id = getIdSelections();
      }
      jp.openViewDialog('查看打卡月报', "${ctx}/qiyewx/daka/month/qiYeWxDaKaMonth/form/view?id="+id,'80%', '70%');
  }
 //子表展示
		   
  function detailFormatter(index, row) {
	  var htmltpl =  $("#qiYeWxDaKaMonthChildrenTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
	  var html = Mustache.render(htmltpl, {
			idx:row.id
		});
	  $.get("${ctx}/qiyewx/daka/month/qiYeWxDaKaMonth/detail?id="+row.id, function(qiYeWxDaKaMonth){
    	var qiYeWxDaKaMonthChild1RowIdx = 0, qiYeWxDaKaMonthChild1Tpl = $("#qiYeWxDaKaMonthChild1Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data1 =  qiYeWxDaKaMonth.qiYeWxDakaMonthExceptionList;
		for (var i=0; i<data1.length; i++){
			data1[i].dict = {};
						data1[i].dict.exception = jp.getDictLabel(${fn.toJson(fn.getDictList('qywx_daka_month_exception_type'))}, data1[i].exception, "-");
			addRow('#qiYeWxDaKaMonthChild-'+row.id+'-1-List', qiYeWxDaKaMonthChild1RowIdx, qiYeWxDaKaMonthChild1Tpl, data1[i]);
			qiYeWxDaKaMonthChild1RowIdx = qiYeWxDaKaMonthChild1RowIdx + 1;
		}
				
    	var qiYeWxDaKaMonthChild2RowIdx = 0, qiYeWxDaKaMonthChild2Tpl = $("#qiYeWxDaKaMonthChild2Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data2 =  qiYeWxDaKaMonth.qiYeWxDaKaMonthOverworkList;
		for (var i=0; i<data2.length; i++){
			data2[i].dict = {};
			addRow('#qiYeWxDaKaMonthChild-'+row.id+'-2-List', qiYeWxDaKaMonthChild2RowIdx, qiYeWxDaKaMonthChild2Tpl, data2[i]);
			qiYeWxDaKaMonthChild2RowIdx = qiYeWxDaKaMonthChild2RowIdx + 1;
		}
				
    	var qiYeWxDaKaMonthChild3RowIdx = 0, qiYeWxDaKaMonthChild3Tpl = $("#qiYeWxDaKaMonthChild3Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data3 =  qiYeWxDaKaMonth.qiYeWxDaKaMonthSpItemList;
		for (var i=0; i<data3.length; i++){
			data3[i].dict = {};
						data3[i].dict.type = jp.getDictLabel(${fn.toJson(fn.getDictList('qywx_daka_month_vaction_type'))}, data3[i].type, "-");
			addRow('#qiYeWxDaKaMonthChild-'+row.id+'-3-List', qiYeWxDaKaMonthChild3RowIdx, qiYeWxDaKaMonthChild3Tpl, data3[i]);
			qiYeWxDaKaMonthChild3RowIdx = qiYeWxDaKaMonthChild3RowIdx + 1;
		}
				
    	var qiYeWxDaKaMonthChild4RowIdx = 0, qiYeWxDaKaMonthChild4Tpl = $("#qiYeWxDaKaMonthChild4Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data4 =  qiYeWxDaKaMonth.qiYeWxDaKaMonthSummaryList;
		for (var i=0; i<data4.length; i++){
			data4[i].dict = {};
			addRow('#qiYeWxDaKaMonthChild-'+row.id+'-4-List', qiYeWxDaKaMonthChild4RowIdx, qiYeWxDaKaMonthChild4Tpl, data4[i]);
			qiYeWxDaKaMonthChild4RowIdx = qiYeWxDaKaMonthChild4RowIdx + 1;
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
<script type="text/template" id="qiYeWxDaKaMonthChildrenTpl">//<!--
	<div class="card card-tabs">
	<div class="card-heading  pb-0">
	    <ul class="nav nav-pills float-left" role="tablist">
				<li class="nav-item"><a data-toggle="tab" class="nav-link show active" href="#tab-{{idx}}-1" aria-expanded="true">异常状态统计信息</a></li>
				<li class="nav-item"><a data-toggle="tab" class="nav-link show" href="#tab-{{idx}}-2" aria-expanded="true">加班情况</a></li>
				<li class="nav-item"><a data-toggle="tab" class="nav-link show" href="#tab-{{idx}}-3" aria-expanded="true">假勤统计信息</a></li>
				<li class="nav-item"><a data-toggle="tab" class="nav-link show" href="#tab-{{idx}}-4" aria-expanded="true">汇总信息</a></li>
		</ul>
		</div>
		<div class="card-body">
		<div class="tab-content">
				 <div id="tab-{{idx}}-1" class="tab-pane fade active show" >
						<table class="table table-bordered">
						<thead>
							<tr>
								<th>异常类型</th>
								<th>异常次数</th>
								<th>异常时长</th>
							</tr>
						</thead>
						<tbody id="qiYeWxDaKaMonthChild-{{idx}}-1-List">
						</tbody>
					</table>
				</div>
				<div id="tab-{{idx}}-2" class="tab-pane fade show">
					<table class="ani table">
						<thead>
							<tr>
								<th>工作日加班时长</th>
								<th>节假日加班时长</th>
								<th>休息日加班时长</th>
							</tr>
						</thead>
						<tbody id="qiYeWxDaKaMonthChild-{{idx}}-2-List">
						</tbody>
					</table>
				</div>
				<div id="tab-{{idx}}-3" class="tab-pane fade show">
					<table class="ani table">
						<thead>
							<tr>
								<th>假勤类型</th>
								<th>假勤次数</th>
								<th>假勤时长</th>
								<th>时长单位</th>
								<th>统计项名称</th>
							</tr>
						</thead>
						<tbody id="qiYeWxDaKaMonthChild-{{idx}}-3-List">
						</tbody>
					</table>
				</div>
				<div id="tab-{{idx}}-4" class="tab-pane fade show">
					<table class="ani table">
						<thead>
							<tr>
								<th>应打卡天数</th>
								<th>正常天数</th>
								<th>异常天数</th>
								<th>实际工作时长</th>
								<th>标准工作时长</th>
							</tr>
						</thead>
						<tbody id="qiYeWxDaKaMonthChild-{{idx}}-4-List">
						</tbody>
					</table>
				</div>
		</div>
		</div>
		</div>//-->
	</script>
	<script type="text/template" id="qiYeWxDaKaMonthChild1Tpl">//<!--
				<tr>
					<td>
						{{row.dict.exception}}
					</td>
					<td>
						{{row.count}}
					</td>
					<td>
						{{row.duration}}
					</td>
				</tr>//-->
	</script>
	<script type="text/template" id="qiYeWxDaKaMonthChild2Tpl">//<!--
				<tr>
					<td>
						{{row.workdayOverSec}}
					</td>
					<td>
						{{row.holidaysOverSec}}
					</td>
					<td>
						{{row.restdaysOverSec}}
					</td>
				</tr>//-->
	</script>
	<script type="text/template" id="qiYeWxDaKaMonthChild3Tpl">//<!--
				<tr>
					<td>
						{{row.dict.type}}
					</td>
					<td>
						{{row.count}}
					</td>
					<td>
						{{row.duration}}
					</td>
					<td>
						{{row.timeType}}
					</td>
					<td>
						{{row.name}}
					</td>
				</tr>//-->
	</script>
	<script type="text/template" id="qiYeWxDaKaMonthChild4Tpl">//<!--
				<tr>
					<td>
						{{row.workDays}}
					</td>
					<td>
						{{row.regularDays}}
					</td>
					<td>
						{{row.exceptDays}}
					</td>
					<td>
						{{row.regularWorkSec}}
					</td>
					<td>
						{{row.standardWorkSec}}
					</td>
				</tr>//-->
	</script>
