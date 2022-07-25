<script>
$(document).ready(function() {
	$('#businessCheckIPQCTable').bootstrapTable({
		 
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
    	       showExport: true,
    	       //显示切换分页按钮
    	       showPaginationSwitch: false,
    	       //最低显示2行
    	       minimumCountColumns: 2,
               //是否显示行间隔色
               striped: true,
               rightFixedColumns: false, //右侧冻结列
               rightFixedNumber: 1,
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
               pageList: [10, 25, 50, 100,200,500,1000,2000,'ALL'],
               //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据  
               url: "${ctx}/business/check/ipqc/businessCheckIPQC/data",
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
                   var deptId = $('#deptId').val();
                   searchParam.dept = deptId;
                   var startdate = $('#beginCheckdate').val();
                   var enddate = $('#endCheckdate').val();
                   if(startdate && enddate){
                       searchParam.beginCheckdate = startdate+" 00:00:00";
                       searchParam.endCheckdate = enddate + " 23:59:59";
                   }

                 return searchParam;
               },
               onShowSearch: function () {
               	 $("#import-collapse").hide();
				 $("#search-collapse").fadeToggle();
               },
               columns: [{
		        checkbox: true
		    },{
                   field: 'checkdate',
                   title: '质检日期',
                   sortable: true,
                   sortName: 'checkdate'

               }
			,{
		        field: 'code',
		        title: '单号',
		        sortable: true,
		        sortName: 'code'
		        ,formatter:function(value, row , index){
		        	  <% if(shiro.hasPermission("business:check:ipqc:businessCheckIPQC:edit") ){ %>
					   if(!value){
						  return "<a  href='#' onclick='edit(\""+row.id+"\")'>-</a>";
					   }else{
						  return "<a  href='#' onclick='edit(\""+row.id+"\")'>"+value+"</a>";
						}
                     <% }else if(shiro.hasPermission("business:check:ipqc:businessCheckIPQC:view")){ %>
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
		        field: 'sccode',
		        title: '生产订单号',
		        sortable: true,
		        sortName: 'sccode'
		       
		    }
			,{
		        field: 'linecode',
		        title: '行号',
		        sortable: true,
		        sortName: 'linecode'
		       
		    }
            ,{
                field: 'bgcode',
                title: '报工单号',
                sortable: true,
                sortName: 'bgcode'
            }
            // ,{
            //     field: 'sitename',
            //     title: '工序名称',
            //     sortable: true,
            //     sortName: 'sitename'
            // }
			,{
		        field: 'username',
		        title: '工号',
		        sortable: true,
		        sortName: 'username'
		       
		    }
			,{
		        field: 'checkname',
		        title: '质检人',
		        sortable: true,
		        sortName: 'checkname'
		       
		    },{
    field: 'cinvcode',
    title: '存货编码',
    sortable: true,
    sortName: 'cinvcode'

    },{
    field: 'cinvname',
    title: '存货名称',
    sortable: true,
    sortName: 'cinvname'

    }

			,{
		        field: 'checknum',
		        title: '检验数量',
		        sortable: true,
		        sortName: 'checknum'
		       
		    }
			,{
		        field: 'hegenum',
		        title: '合格数量',
		        sortable: true,
		        sortName: 'hegenum'
		       
		    }
			,{
		        field: 'nohegenum',
		        title: '不合格数量',
		        sortable: true,
		        sortName: 'nohegenum'
		       
		    }
			,{
		        field: 'badnum',
		        title: '不良品数量',
		        sortable: true,
		        sortName: 'badnum'
		    }
            ,{
                field: 'hglv',
                title: '合格率'
                ,formatter:function(value, row , index){
                    return ((value-0).toFixed(4) * 100).toFixed(2) +"%";
                }
            },{
                field: 'bzhglv',
                title: '标准合格率'
                ,formatter:function(value, row , index){
                    if(!value){
                        return '-';
                    }
                    return ((value-0).toFixed(4) * 100).toFixed(2) +"%";
                }
                }
			,{
		        field: 'remarks',
		        title: '备注信息',
		        sortable: true,
		        sortName: 'remarks'
		       
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
					<% if(shiro.hasPermission("business:check:ipqc:businessCheckIPQC:view")){ %>
					   '<a class="view btn btn-icon waves-effect waves-light btn-custom btn-xs m-r-5"> <i class="fa fa-search"></i></a>',
				   <% } %>
				   <% if(shiro.hasPermission("business:check:ipqc:businessCheckIPQC:edit")){ %>
					   '<a class="edit btn btn-icon waves-effect waves-light btn-success btn-xs m-r-5"> <i class="fa fa-pencil"></i></a>',
				   <% } %>
				   <% if(shiro.hasPermission("business:check:ipqc:businessCheckIPQC:del")){ %>
					   '<a class="del btn btn-icon waves-effect waves-light btn-danger btn-xs"> <i class="fa fa-trash-o"></a>'
				   <% } %>
				   ].join('');
			   }
		   }
		     ]
		
		});
		

	  $('#businessCheckIPQCTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#businessCheckIPQCTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#businessCheckIPQCTable').bootstrapTable('getSelections').length!=1);
        });

	 $("#import").click(function(){//显示导入面板
            $("#search-collapse").hide();
            $("#import-collapse").fadeToggle()

      })

	 $("#btnImportExcel").click(function(){//导入Excel
		 var importForm = $('#importForm')[0];
		 jp.block('#import-collapse',"文件上传中...");
		 jp.uploadFile(importForm,"${ctx}/business/check/ipqc/businessCheckIPQC/import",function (data) {
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
            jp.downloadFile('${ctx}/business/check/ipqc/businessCheckIPQC/import/template');
		})

	$("#export").click(function(){//导出Excel文件
	        var searchParam = $("#searchForm").serializeJSON();
	        searchParam.pageNo = 1;
	        searchParam.pageSize = -1;
            var sortName = $('#businessCheckIPQCTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#businessCheckIPQCTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/business/check/ipqc/businessCheckIPQC/export?'+values);
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

	 $('#checkdate').datepicker({//日期控件初始化
			toggleActive: true,
			language:"zh-CN",
    			format:"yyyy-mm-dd"
		});
		
	});

	//获取选中行
  function getIdSelections() {
        return $.map($("#businessCheckIPQCTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

  //删除
  function del(ids){
     if(!ids){
          ids = getIdSelections();
     }
	 jp.confirm('确认要删除该IPQC检验记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/business/check/ipqc/businessCheckIPQC/delete?ids=" + ids, function(data){
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
      $('#businessCheckIPQCTable').bootstrapTable('refresh');
  }

   //新增表单页面
 function add() {
     jp.openSaveDialog('新增IPQC检验', "${ctx}/business/check/ipqc/businessCheckIPQC/form/add",'90%', '90%');
 }
  //编辑表单页面
  function edit(id){
      if(!id){
          id = getIdSelections();
      }
	  jp.openSaveDialog('编辑IPQC检验', "${ctx}/business/check/ipqc/businessCheckIPQC/form/edit?id="+id,'90%', '90%');
  }
  //查看表单页面
  function view(id) {
      if(!id){
          id = getIdSelections();
      }
      jp.openViewDialog('查看IPQC检验', "${ctx}/business/check/ipqc/businessCheckIPQC/form/view?id="+id,'90%', '90%');
  }
</script>