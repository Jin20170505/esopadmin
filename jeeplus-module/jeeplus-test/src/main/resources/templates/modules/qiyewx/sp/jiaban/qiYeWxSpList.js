<script>
$(document).ready(function() {
	$('#qiYeWxSpTable').bootstrapTable({
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
               url: "${ctx}/qiyewx/sp/qiYeWxSp/data",
               //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
               //queryParamsType:'',   
               ////查询参数,每次调用是会带上这个参数，可自定义                         
               queryParams : function(params) {
               	var searchParam = $("#searchForm").serializeJSON();
               	searchParam.recordType="5";
               	searchParam.pageNo = params.limit === undefined? "1" :params.offset/params.limit+1;
               	searchParam.pageSize = params.limit === undefined? -1 : params.limit;
               	if(params.sort && params.order){
                    searchParam.orderBy = params.sort+ " "+  params.order;
				}
                   var endSqsj = $('#endApplyTime').val();
                   if(endSqsj){
                       searchParam.endApplyTime = endSqsj +" 23:59:59";
                   }
			    return searchParam;
               },
               onShowSearch: function () {
               	 $("#import-collapse").hide();
				 $("#search-collapse").fadeToggle();
               },
               columns: [{
		        field: 'spNo',
		        title: '审批号',
		        sortable: true,
		        sortName: 'spNo'
		    }
			,{
		        field: 'spName',
		        title: '申请类型名称',
		        sortable: true,
		        sortName: 'spName'
		       
		    }
			,{
		        field: 'apply.name',
		        title: '申请人',
		        sortable: true,
		        sortName: 'apply.name'
		       
		    }
			,{
		        field: 'spStatus',
		        title: '申请单状态',
		        sortable: true,
		        sortName: 'spStatus',
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fn.toJson(fn.getDictList('sp_status'))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'applyTime',
		        title: '申请提交时间',
		        sortable: true,
		        sortName: 'applyTime'
		    }
		     ]
		
		});
		

	  $('#qiYeWxSpTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#qiYeWxSpTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#qiYeWxSpTable').bootstrapTable('getSelections').length!=1);
        });

	 $("#import").click(function(){//显示导入面板
            $("#search-collapse").hide();
            $("#import-collapse").fadeToggle()

      })

	 $("#btnImportExcel").click(function(){//导入Excel
		 var importForm = $('#importForm')[0];
		 jp.block('#import-collapse',"文件上传中...");
		 jp.uploadFile(importForm,"${ctx}/qiyewx/sp/qiYeWxSp/import",function (data) {
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
            jp.downloadFile('${ctx}/qiyewx/sp/qiYeWxSp/import/template');
		})

	 $("#export").click(function(){//导出Excel文件
	        var searchParam = $("#searchForm").serializeJSON();
         	searchParam.recordType="5";
	        searchParam.pageNo = 1;
	        searchParam.pageSize = -1;
            var sortName = $('#qiYeWxSpTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#qiYeWxSpTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/qiyewx/sp/qiYeWxSp/export?'+values);
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

	 $('#applyTime').datepicker({//日期控件初始化
			toggleActive: true,
			language:"zh-CN",
    			format:"yyyy-mm-dd"
		});
		
	});

	//获取选中行
  function getIdSelections() {
        return $.map($("#qiYeWxSpTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

  //删除
  function del(ids){
     if(!ids){
          ids = getIdSelections();
     }
	 jp.confirm('确认要删除该申请审批记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/qiyewx/sp/qiYeWxSp/delete?ids=" + ids, function(data){
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
      $('#qiYeWxSpTable').bootstrapTable('refresh');
  }
function goToSync(){
    jp.openSaveDialog('同步数据', "${ctx}/qiyewx/sp/qiYeWxSp/goToSyncByType?type=5",'600px', '500px');
}
   //新增表单页面
 function add() {
     jp.openSaveDialog('新增申请审批', "${ctx}/qiyewx/sp/qiYeWxSp/form/add",'80%', '70%');
 }
  //编辑表单页面
  function edit(id){
      if(!id){
          id = getIdSelections();
      }
	  jp.openSaveDialog('编辑申请审批', "${ctx}/qiyewx/sp/qiYeWxSp/form/edit?id="+id,'80%', '70%');
  }
  //查看表单页面
  function view(id) {
      if(!id){
          id = getIdSelections();
      }
      jp.openViewDialog('查看申请审批', "${ctx}/qiyewx/sp/qiYeWxSp/form/view?id="+id,'80%', '70%');
  }
 //子表展示
		   
  function detailFormatter(index, row) {
	  var htmltpl =  $("#qiYeWxSpChildrenTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
	  var html = Mustache.render(htmltpl, {
			idx:row.id
		});
	  $.get("${ctx}/qiyewx/sp/qiYeWxSp/detail?id="+row.id, function(qiYeWxSp){
    	var qiYeWxSpChild1RowIdx = 0, qiYeWxSpChild1Tpl = $("#qiYeWxSpChild1Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data1 =  qiYeWxSp.qiYeWxSpApplyList;
		for (var i=0; i<data1.length; i++){
			data1[i].dict = {};
			addRow('#qiYeWxSpChild-'+row.id+'-1-List', qiYeWxSpChild1RowIdx, qiYeWxSpChild1Tpl, data1[i]);
			qiYeWxSpChild1RowIdx = qiYeWxSpChild1RowIdx + 1;
		}
				
    	var qiYeWxSpChild2RowIdx = 0, qiYeWxSpChild2Tpl = $("#qiYeWxSpChild2Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data2 =  qiYeWxSp.qiYeWxSpRecordList;
		for (var i=0; i<data2.length; i++){
			data2[i].dict = {};
						data2[i].dict.spStatus = jp.getDictLabel(${fn.toJson(fn.getDictList('sp_status'))}, data2[i].spStatus, "-");
			addRow('#qiYeWxSpChild-'+row.id+'-2-List', qiYeWxSpChild2RowIdx, qiYeWxSpChild2Tpl, data2[i]);
			qiYeWxSpChild2RowIdx = qiYeWxSpChild2RowIdx + 1;
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
<script type="text/template" id="qiYeWxSpChildrenTpl">//<!--
	<div class="card card-tabs">
	<div class="card-heading  pb-0">
	    <ul class="nav nav-pills float-left" role="tablist">
				<li class="nav-item"><a data-toggle="tab" class="nav-link show active" href="#tab-{{idx}}-1" aria-expanded="true">审批申请详情</a></li>
				<li class="nav-item"><a data-toggle="tab" class="nav-link show" href="#tab-{{idx}}-2" aria-expanded="true">审批流程信息</a></li>
		</ul>
		</div>
		<div class="card-body">
		<div class="tab-content">
				 <div id="tab-{{idx}}-1" class="tab-pane fade active show" >
						<table class="table table-bordered">
						<thead>
							<tr>
								<th>控件名称</th>
								<th>说明</th>
								<th>开始时间</th>
								<th>结束时间</th>
							</tr>
						</thead>
						<tbody id="qiYeWxSpChild-{{idx}}-1-List">
						</tbody>
					</table>
				</div>
				<div id="tab-{{idx}}-2" class="tab-pane fade show">
					<table class="ani table">
						<thead>
							<tr>
								<th>审批人</th>
								<th>审批时间</th>
								<th>审批意见</th>
								<th>审批状态</th>
							</tr>
						</thead>
						<tbody id="qiYeWxSpChild-{{idx}}-2-List">
						</tbody>
					</table>
				</div>
		</div>
		</div>
		</div>//-->
	</script>
	<script type="text/template" id="qiYeWxSpChild1Tpl">//<!--
				<tr>
					<td>
						{{row.title}}
					</td>
					<td>
						{{row.valueText}}
					</td>
					<td>
						{{row.vacationAttendanceDateRangeBegin}}
					</td>
					<td>
						{{row.vacationAttendanceDateRangeEnd}}
					</td>
				</tr>//-->
	</script>
	<script type="text/template" id="qiYeWxSpChild2Tpl">//<!--
				<tr>
					<td>
						{{row.approver.name}}
					</td>
					<td>
						{{row.sptime}}
					</td>
					<td>
						{{row.speech}}
					</td>
					<td>
						{{row.dict.spStatus}}
					</td>
				</tr>//-->
	</script>
