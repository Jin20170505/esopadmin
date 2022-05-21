<script>
$(document).ready(function() {
	$('#businessBaoGongOrderTable').bootstrapTable({
		 
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
               url: "${ctx}/business/baogong/order/businessBaoGongOrder/data",
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
                       field: 'bgcode',
                       title: '报工单号',
                       sortable: true,
                       sortName: 'bgcode'
                       ,formatter:function(value, row , index){
                           if(!value){
                               return "<a  href='#' onclick='view(\""+row.id+"\")'>-</a>";
                           }else{
                               return "<a  href='#' onclick='view(\""+row.id+"\")'>"+value+"</a>";
                           }
                       }

                   }
			,{
		        field: 'ordercode',
		        title: '生产订单号',
		        sortable: true,
		        sortName: 'ordercode'
		       
		    }
			,{
		        field: 'orderline',
		        title: '行号',
		        sortable: true,
		        sortName: 'orderline'
		       
		    }
			,{
		        field: 'plancode',
		        title: '计划单号',
		        sortable: true,
		        sortName: 'plancode'
		       
		    }
			,{
		        field: 'deptName',
		        title: '生产部门',
		        sortable: true,
		        sortName: 'deptname'
		       
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
                       field: 'isprint',
                       title: '是否打印',
                       sortable: true,
                       sortName: 'isprint'

                   }
		     ]
		
		});
		

	  $('#businessBaoGongOrderTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#businessBaoGongOrderTable').bootstrapTable('getSelections').length);
            $('#edit,#print').prop('disabled', $('#businessBaoGongOrderTable').bootstrapTable('getSelections').length!=1);
        });

	 $("#import").click(function(){//显示导入面板
            $("#search-collapse").hide();
            $("#import-collapse").fadeToggle()

      })

	 $("#btnImportExcel").click(function(){//导入Excel
		 var importForm = $('#importForm')[0];
		 jp.block('#import-collapse',"文件上传中...");
		 jp.uploadFile(importForm,"${ctx}/business/baogong/order/businessBaoGongOrder/import",function (data) {
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
            jp.downloadFile('${ctx}/business/baogong/order/businessBaoGongOrder/import/template');
		})

	 $("#export").click(function(){//导出Excel文件
	        var searchParam = $("#searchForm").serializeJSON();
	        searchParam.pageNo = 1;
	        searchParam.pageSize = -1;
            var sortName = $('#businessBaoGongOrderTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#businessBaoGongOrderTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/business/baogong/order/businessBaoGongOrder/export?'+values);
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

    // 生成报工单
    function shengchengbaogongdan(){
    top.layer.open({
        type: 2,
        area: ['90%', '90%'],
        title:"计划工单---选择",
        auto:false,
        name:'friend',
        content: "${ctx}/tag/gridselect?url="+encodeURIComponent("${ctx}/business/jihuadingdan/businessJiHuaGongDan/data?status=已下发")+"&fieldLabels="+encodeURIComponent("单号|生产订单号|生产订单行号|生产批号|存货编码|存货名称|规格型号|计量单位|开始日期|结束日期|生产数量|工单数量|生产部门")+"&fieldKeys="+encodeURIComponent("code|dd.code|orderno|batchno|cinvcode|cinvname|cinvstd|unit|startdate|enddate|scnum|gdnum|dept.name")+"&searchLabels="+encodeURIComponent("单号|批号")+"&searchKeys="+encodeURIComponent("code|batchno")+"&isMultiSelected=false",
        btn: ['确定', '关闭'],
        yes: function(index, layero){
            var iframeWin = layero.find('iframe')[0].contentWindow; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
            var items = iframeWin.getSelections();
            if(items == ""){
                jp.warning("必须选择一条数据!");
                return;
            }
            var index =jp.loading();
            jp.get("${ctx}/business/jihuadingdan/businessJiHuaGongDan/shengchengbaogongdan?rids=" + items[0].id, function(data){
                if(data.success){
                    refresh();
                    jp.toastr_success(data.msg);
                }else{
                    jp.toastr_error(data.msg);
                }
                jp.close(index);
            })
            top.layer.close(index);//关闭对话框。
        },
        cancel: function(index){
        }
    });
}
    // 打印报工单
    function printbgd(){
        var rid = getIdSelections();
        jp.windowOpen('${ctx}/business/baogong/order/businessBaoGongOrder/goToPrint?rid='+rid,"报工单--打印",window.screen.height,window.screen.width);
    }
	//获取选中行
  function getIdSelections() {
        return $.map($("#businessBaoGongOrderTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

  //删除
  function del(ids){
     if(!ids){
          ids = getIdSelections();
     }
	 jp.confirm('确认要删除该报工单记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/business/baogong/order/businessBaoGongOrder/delete?ids=" + ids, function(data){
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
      $('#businessBaoGongOrderTable').bootstrapTable('refresh');
  }

   //新增表单页面
 function add() {
     jp.openSaveDialog('新增报工单', "${ctx}/business/baogong/order/businessBaoGongOrder/form/add",'90%', '90%');
 }
  //编辑表单页面
  function edit(id){
      if(!id){
          id = getIdSelections();
      }
	  jp.openSaveDialog('编辑报工单', "${ctx}/business/baogong/order/businessBaoGongOrder/form/edit?id="+id,'90%', '90%');
  }
  //查看表单页面
  function view(id) {
      if(!id){
          id = getIdSelections();
      }
      jp.openViewDialog('查看报工单', "${ctx}/business/baogong/order/businessBaoGongOrder/form/view?id="+id,'90%', '90%');
  }
 //子表展示
		   
  function detailFormatter(index, row) {
	  var htmltpl =  $("#businessBaoGongOrderChildrenTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
	  var html = Mustache.render(htmltpl, {
			idx:row.id
		});
	  $.get("${ctx}/business/baogong/order/businessBaoGongOrder/detail?id="+row.id, function(businessBaoGongOrder){
    	var businessBaoGongOrderChild1RowIdx = 0, businessBaoGongOrderChild1Tpl = $("#businessBaoGongOrderChild1Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data1 =  businessBaoGongOrder.businessBaoGongOrderMingXiList;
		for (var i=0; i<data1.length; i++){
			data1[i].dict = {};
			addRow('#businessBaoGongOrderChild-'+row.id+'-1-List', businessBaoGongOrderChild1RowIdx, businessBaoGongOrderChild1Tpl, data1[i]);
			businessBaoGongOrderChild1RowIdx = businessBaoGongOrderChild1RowIdx + 1;
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
<script type="text/template" id="businessBaoGongOrderChildrenTpl">//<!--
	<div class="card card-tabs">
	<div class="card-heading  pb-0">
	    <ul class="nav nav-pills float-left" role="tablist">
				<li class="nav-item"><a data-toggle="tab" class="nav-link show active" href="#tab-{{idx}}-1" aria-expanded="true">报工明细</a></li>
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
								<th>操作人</th>
								<th>员工编号</th>
								<th>班组</th>
								<th>加工数量</th>
							</tr>
						</thead>
						<tbody id="businessBaoGongOrderChild-{{idx}}-1-List">
						</tbody>
					</table>
				</div>
		</div>
		</div>
		</div>//-->
	</script>
	<script type="text/template" id="businessBaoGongOrderChild1Tpl">//<!--
				<tr>
					<td>
						{{row.no}}
					</td>
					<td>
						{{row.site}}
					</td>
					<td>
						{{row.opname}}
					</td>
					<td>
						{{row.opcode}}
					</td>
					<td>
						{{row.classgroup}}
					</td>
					<td>
						{{row.num}}
					</td>
				</tr>//-->
	</script>
