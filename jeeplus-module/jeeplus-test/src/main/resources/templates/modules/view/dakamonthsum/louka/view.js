<script>
function getLastMonth(){
    var date = new Date;
    var year = date.getFullYear();
    var month = date.getMonth();
    if(month == 0){
        year = year -1;
        month = 12;
    }
    if((month+"").length==1){
        month = "0"+month;
    }
    return year+"-"+month;
}
$(document).ready(function() {
    var ym = getLastMonth();
    $("#ym").val(ym);
    $('#viewTable').bootstrapTable({
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
        showColumns: false,
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
        sidePagination: "client",
        //排序方式
        sortOrder: "asc",
        pageNumber:1,
        pageSize: 25,
        pageList: [10, 25, 50, 100,200,400,500],
        url: "${ctx}/view/dakamonthsum/viewDakaMonthSum/loukaData",
        queryParams : function(params) {
            var searchParam = $("#searchForm").serializeJSON();
            searchParam.pageNo = params.limit === undefined? "1" :params.offset/params.limit+1;
            searchParam.pageSize = params.limit === undefined? -1 : params.limit;
            if(params.sort && params.order){
                searchParam.orderBy = params.sort+ " "+  params.order;
            }
            var dept = $('#officeName').val();
            searchParam.dept = dept;
            return searchParam;
        },
        onShowSearch: function () {
            $("#import-collapse").hide();
            $("#search-collapse").fadeToggle();
        },
        columns: [{
            field: 'ym',
            title: '年月'
        },{
            field: 'tag',
            title: '公司'
        },{
            field: 'dept',
            title: '部门'
        },{
            field: 'username',
            title: '姓名'
        }
            ,{
                field: 'num',
                title: '漏卡次数'
            }
        ]
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

    $('#ym').datetimepicker({
        format:'Y-m',
        formatDate:'y-m',
        timepicker: true,
        validateOnBlur: false
    });
});

//刷新列表
function refresh() {
    $('#viewTable').bootstrapTable('refresh');
}
</script>