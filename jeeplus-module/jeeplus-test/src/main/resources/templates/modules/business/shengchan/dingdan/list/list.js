<script>
    $(document).ready(function() {
    $('#table').bootstrapTable({
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
        pageNumber: 1,
        //每页的记录行数(*)
        pageSize: 10,
        //可供选择的每页的行数(*)
        pageList: [10, 25, 50, 100],
        url: "${ctx}/business/shengchan/dingdan/businessShengChanDingDan/mxdata",
        queryParams: function (params) {
            var searchParam = $("#searchForm").serializeJSON();
            searchParam.pageNo = params.limit === undefined ? "1" : params.offset / params.limit + 1;
            searchParam.pageSize = params.limit === undefined ? -1 : params.limit;
            if (params.sort && params.order) {
                searchParam.orderBy = params.sort + " " + params.order;
            }
            return searchParam;
        },
        onShowSearch: function () {
            $("#search-collapse").fadeToggle();
        },
        columns: [{
            checkbox: true

        }
            , {
                field: 'p.code',
                title: '生产单号',
                sortable: true,
                sortName: 'p.code'
    }
    ,{
    field: 'no',
    title: '行号',
    sortable: true,
    sortName: 'no'
    }
    ,{
    field: 'cinv.code',
    title: '存货编号'

    }
    ,{
    field: 'cinvname',
    title: '存货名称'
    }
    ,{
    field: 'std',
    title: '规格型号'
    }
    ,{
    field: 'num',
    title: '数量'
    }
            ,{
                field: 'unit',
                title: '计量单位'
            }
            ,{
                field: 'startdate',
                title: '开工日期'
            }
            ,{
                field: 'enddate',
                title: '完工日期'
            }
            ,{
                field: 'dept.name',
                title: '生产部门'
            }
    ]

});


    $('#baseProductionLineTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#baseProductionLineTable').bootstrapTable('getSelections').length);
            $('#jihua').prop('disabled', $('#baseProductionLineTable').bootstrapTable('getSelections').length!=1);
        });

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
        return $.map($("#baseProductionLineTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }



    //刷新列表
  function refresh() {
      $('#baseProductionLineTable').bootstrapTable('refresh');
  }

</script>