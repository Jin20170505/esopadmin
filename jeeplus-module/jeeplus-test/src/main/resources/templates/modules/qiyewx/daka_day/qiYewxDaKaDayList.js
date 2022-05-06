<script>
$(document).ready(function() {
	$('#qiYewxDaKaDayTable').bootstrapTable({
		 
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
               url: "${ctx}/qiyewx/daka_day/qiYewxDaKaDay/data",
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
		        field: 'date',
		        title: '日期',
		        sortable: true,
		        sortName: 'date'
		    }
			,{
		        field: 'name',
		        title: '姓名',
		        sortable: true,
		        sortName: 'name'
		       
		    }
			,{
		        field: 'userid',
		        title: '用户id',
		        sortable: true,
		        sortName: 'userid'
		       
		    }
			,{
		        field: 'departsName',
		        title: '部门',
		        sortable: true,
		        sortName: 'departsName'
		       
		    }
			,{
		        field: 'recordType',
		        title: '打卡类型',
		        sortable: true,
		        sortName: 'recordType',
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fn.toJson(fn.getDictList('qywx_daka_type'))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'dayType',
		        title: '日报类型',
		        sortable: true,
		        sortName: 'dayType',
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fn.toJson(fn.getDictList('salary_jixiao_jidu'))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'otStatus',
		        title: '加班状态',
		        sortable: true,
		        sortName: 'otStatus',
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fn.toJson(fn.getDictList('sp_status'))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'otDuration',
		        title: '加班时长(单位s)',
		        sortable: true,
		        sortName: 'otDuration'
		       
		    }
			,{
		        field: 'checkinCount',
		        title: '当日打卡次数',
		        sortable: true,
		        sortName: 'checkinCount'
		       
		    }
			,{
		        field: 'regularWorkSec',
		        title: '当日实际工作时长(单位s)',
		        sortable: true,
		        sortName: 'regularWorkSec'
		       
		    }
			,{
		        field: 'standardWorkSec',
		        title: '当日标准工作时长(单位s)',
		        sortable: true,
		        sortName: 'standardWorkSec'
		       
		    }
		     ]
		
		});
		

	  $('#qiYewxDaKaDayTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#qiYewxDaKaDayTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#qiYewxDaKaDayTable').bootstrapTable('getSelections').length!=1);
        });

	 $("#import").click(function(){//显示导入面板
            $("#search-collapse").hide();
            $("#import-collapse").fadeToggle()

      })

	 $("#btnImportExcel").click(function(){//导入Excel
		 var importForm = $('#importForm')[0];
		 jp.block('#import-collapse',"文件上传中...");
		 jp.uploadFile(importForm,"${ctx}/qiyewx/daka_day/qiYewxDaKaDay/import",function (data) {
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
            jp.downloadFile('${ctx}/qiyewx/daka_day/qiYewxDaKaDay/import/template');
		})

	 $("#export").click(function(){//导出Excel文件
	        var searchParam = $("#searchForm").serializeJSON();
	        searchParam.pageNo = 1;
	        searchParam.pageSize = -1;
            var sortName = $('#qiYewxDaKaDayTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#qiYewxDaKaDayTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/qiyewx/daka_day/qiYewxDaKaDay/export?'+values);
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

	 $('#date').datepicker({//日期控件初始化
			toggleActive: true,
			language:"zh-CN",
    			format:"yyyy-mm-dd"
		});
		
	});

	//获取选中行
  function getIdSelections() {
        return $.map($("#qiYewxDaKaDayTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

    function sychdata(){
    jp.openSaveDialog('同步日期选择', "${ctx}/qiyewx/daka_day/qiYewxDaKaDay/goToSync",'80%', '70%');
    }
  //删除
  function del(ids){
     if(!ids){
          ids = getIdSelections();
     }
	 jp.confirm('确认要删除该打卡日报记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/qiyewx/daka_day/qiYewxDaKaDay/delete?ids=" + ids, function(data){
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
      $('#qiYewxDaKaDayTable').bootstrapTable('refresh');
  }

   //新增表单页面
 function add() {
     jp.openSaveDialog('新增打卡日报', "${ctx}/qiyewx/daka_day/qiYewxDaKaDay/form/add",'90%', '90%');
 }
  //编辑表单页面
  function edit(id){
      if(!id){
          id = getIdSelections();
      }
	  jp.openSaveDialog('编辑打卡日报', "${ctx}/qiyewx/daka_day/qiYewxDaKaDay/form/edit?id="+id,'90%', '90%');
  }
  //查看表单页面
  function view(id) {
      if(!id){
          id = getIdSelections();
      }
      jp.openViewDialog('查看打卡日报', "${ctx}/qiyewx/daka_day/qiYewxDaKaDay/form/view?id="+id,'90%', '90%');
  }
 //子表展示
		   
  function detailFormatter(index, row) {
	  var htmltpl =  $("#qiYewxDaKaDayChildrenTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
	  var html = Mustache.render(htmltpl, {
			idx:row.id
		});
	  $.get("${ctx}/qiyewx/daka_day/qiYewxDaKaDay/detail?id="+row.id, function(qiYewxDaKaDay){
    	var qiYewxDaKaDayChild1RowIdx = 0, qiYewxDaKaDayChild1Tpl = $("#qiYewxDaKaDayChild1Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data1 =  qiYewxDaKaDay.qiyewxDakaDaySpitemsList;
		for (var i=0; i<data1.length; i++){
			data1[i].dict = {};
			addRow('#qiYewxDaKaDayChild-'+row.id+'-1-List', qiYewxDaKaDayChild1RowIdx, qiYewxDaKaDayChild1Tpl, data1[i]);
			qiYewxDaKaDayChild1RowIdx = qiYewxDaKaDayChild1RowIdx + 1;
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
<script type="text/template" id="qiYewxDaKaDayChildrenTpl">//<!--
	<div class="card card-tabs">
	<div class="card-heading  pb-0">
	    <ul class="nav nav-pills float-left" role="tablist">
				<li class="nav-item"><a data-toggle="tab" class="nav-link show active" href="#tab-{{idx}}-1" aria-expanded="true">假勤信息</a></li>
		</ul>
		</div>
		<div class="card-body">
		<div class="tab-content">
				 <div id="tab-{{idx}}-1" class="tab-pane fade active show" >
						<table class="table table-bordered">
						<thead>
							<tr>
								<th>名称</th>
								<th>当日次数</th>
								<th>时长</th>
								<th>时长单位</th>
							</tr>
						</thead>
						<tbody id="qiYewxDaKaDayChild-{{idx}}-1-List">
						</tbody>
					</table>
				</div>
		</div>
		</div>
		</div>//-->
	</script>
	<script type="text/template" id="qiYewxDaKaDayChild1Tpl">//<!--
				<tr>
					<td>
						{{row.name}}
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
				</tr>//-->
	</script>
