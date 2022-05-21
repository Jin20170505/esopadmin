<script>
$(document).ready(function() {
	$('#businessShengChanDingDanTable').bootstrapTable({
		 
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
               url: "${ctx}/business/shengchan/dingdan/businessShengChanDingDan/data",
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
                   searchParam.status = '已审核';
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
		        title: '生产单号',
		        sortable: true,
		        sortName: 'code'
		        ,formatter:function(value, row , index){
		        	  <% if(shiro.hasPermission("business:shengchan:dingdan:businessShengChanDingDan:edit") ){ %>
					   if(!value){
						  return "<a  href='#' onclick='edit(\""+row.id+"\")'>-</a>";
					   }else{
						  return "<a  href='#' onclick='edit(\""+row.id+"\")'>"+value+"</a>";
						}
                     <% }else if(shiro.hasPermission("business:shengchan:dingdan:businessShengChanDingDan:view")){ %>
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
		        field: 'dept.name',
		        title: '生产部门',
		        sortable: true,
		        sortName: 'dept.name'
		       
		    }
            ,{
            field: 'startdate',
            title: '开工日期',
            sortable: true,
            sortName: 'startdate'

            }
            ,{
            field: 'enddate',
            title: '完工日期',
            sortable: true,
            sortName: 'enddate'
            }
		     ]
		
		});
		

	  $('#businessShengChanDingDanTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#businessShengChanDingDanTable').bootstrapTable('getSelections').length);
            $('#edit,#jihua,#shenhe,#fanshen').prop('disabled', $('#businessShengChanDingDanTable').bootstrapTable('getSelections').length!=1);
        });

	 $("#import").click(function(){//显示导入面板
            $("#search-collapse").hide();
            $("#import-collapse").fadeToggle()

      })

	 $("#btnImportExcel").click(function(){//导入Excel
		 var importForm = $('#importForm')[0];
		 jp.block('#import-collapse',"文件上传中...");
		 jp.uploadFile(importForm,"${ctx}/business/shengchan/dingdan/businessShengChanDingDan/import",function (data) {
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
            jp.downloadFile('${ctx}/business/shengchan/dingdan/businessShengChanDingDan/import/template');
		})

	 $("#export").click(function(){//导出Excel文件
	        var searchParam = $("#searchForm").serializeJSON();
	        searchParam.pageNo = 1;
	        searchParam.pageSize = -1;
            var sortName = $('#businessShengChanDingDanTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#businessShengChanDingDanTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/business/shengchan/dingdan/businessShengChanDingDan/export?'+values);
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
    // 审核
    function shenhe(){
        var ids = getIdSelections();
        jp.confirm('确认要审核该生产订单记录吗？', function(){
            var index =jp.loading();
            jp.get("${ctx}/business/shengchan/dingdan/businessShengChanDingDan/shenhe?ids=" + ids, function(data){
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
    // 反审
    function fanshen(){
        var ids = getIdSelections();
        jp.confirm('确认要反审该生产订单记录吗？', function(){
            var index =jp.loading();
            jp.get("${ctx}/business/shengchan/dingdan/businessShengChanDingDan/fanshen?ids=" + ids, function(data){
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
	//获取选中行
  function getIdSelections() {
        return $.map($("#businessShengChanDingDanTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

  //删除
  function del(ids){
     if(!ids){
          ids = getIdSelections();
     }
	 jp.confirm('确认要删除该生产订单记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/business/shengchan/dingdan/businessShengChanDingDan/delete?ids=" + ids, function(data){
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
function sychu8(){
	 jp.openSaveDialog('同步日期选择', "${ctx}/business/shengchan/dingdan/businessShengChanDingDan/goToDateSelect",'80%', '70%');
}
    //刷新列表
  function refresh() {
      $('#businessShengChanDingDanTable').bootstrapTable('refresh');
  }

   //新增表单页面
 function add() {
     jp.openSaveDialog('新增生产订单', "${ctx}/business/shengchan/dingdan/businessShengChanDingDan/form/add",'100%', '100%');
 }
  //编辑表单页面
  function edit(id){
      if(!id){
          id = getIdSelections();
      }
	  jp.openSaveDialog('编辑生产订单', "${ctx}/business/shengchan/dingdan/businessShengChanDingDan/form/edit?id="+id,'100%', '100%');
  }
  //查看表单页面
  function view(id) {
      if(!id){
          id = getIdSelections();
      }
      jp.openViewDialog('查看生产订单', "${ctx}/business/shengchan/dingdan/businessShengChanDingDan/form/view?id="+id,'100%', '100%');
  }
 //子表展示
		   
  function detailFormatter(index, row) {
	  var htmltpl =  $("#businessShengChanDingDanChildrenTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
	  var html = Mustache.render(htmltpl, {
			idx:row.id
		});
	  $.get("${ctx}/business/shengchan/dingdan/businessShengChanDingDan/detail?id="+row.id, function(businessShengChanDingDan){
    	var businessShengChanDingDanChild1RowIdx = 0, businessShengChanDingDanChild1Tpl = $("#businessShengChanDingDanChild1Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data1 =  businessShengChanDingDan.businessShengChanDingDanMingXiList;
		for (var i=0; i<data1.length; i++){
			data1[i].dict = {};
			addRow('#businessShengChanDingDanChild-'+row.id+'-1-List', businessShengChanDingDanChild1RowIdx, businessShengChanDingDanChild1Tpl, data1[i]);
			businessShengChanDingDanChild1RowIdx = businessShengChanDingDanChild1RowIdx + 1;
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
<script type="text/template" id="businessShengChanDingDanChildrenTpl">//<!--
	<div class="card card-tabs">
	<div class="card-heading  pb-0">
	    <ul class="nav nav-pills float-left" role="tablist">
				<li class="nav-item"><a data-toggle="tab" class="nav-link show active" href="#tab-{{idx}}-1" aria-expanded="true">生产订单明细</a></li>
		</ul>
		</div>
		<div class="card-body">
		<div class="tab-content">
				 <div id="tab-{{idx}}-1" class="tab-pane fade active show" >
						<table class="table table-bordered">
						<thead>
							<tr>
								<th>行号</th>
                                <th>类型</th>
								<th>存货编码</th>
								<th>存货名称</th>
								<th>存货规格型号</th>
								<th>单位</th>
								<th>数量</th>
								<th>开工日期</th>
								<th>完工日期</th>
                                <th>批号</th>
                                <th>生产部门</th>
                                <th>状态</th>
							</tr>
						</thead>
						<tbody id="businessShengChanDingDanChild-{{idx}}-1-List">
						</tbody>
					</table>
				</div>
		</div>
		</div>
		</div>//-->
	</script>
	<script type="text/template" id="businessShengChanDingDanChild1Tpl">//<!--
				<tr>
					<td>
						{{row.no}}
					</td>
                    <td>
                    {{row.type}}
                    </td>
					<td>
						{{row.cinv.code}}
					</td>
					<td>
						{{row.cinvname}}
					</td>
					<td>
						{{row.std}}
					</td>
					<td>
						{{row.unit}}
					</td>
					<td>
						{{row.num}}
					</td>
					<td>
						{{row.startdate}}
					</td>
					<td>
						{{row.enddate}}
					</td>
                    <td>
                    {{row.batchno}}
                    </td>
                    <td>
                    {{row.dept.name}}
                    </td>
                    <td>
                    {{row.status}}
                    </td>
				</tr>//-->
	</script>
