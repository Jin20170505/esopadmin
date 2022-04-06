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
    refresh();
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
    function refresh(){
    var ym = $("#ym").val();
    if(!ym){
        ym = getLastMonth();
        $("#ym").val(ym);
    }
    var dept = $('#officeName').val();
    var userid = $("#userid").val();
    var tag = $("#tag").val();
    $.ajax({
        url:"${ctx}/view/dakamonthsum/viewDakaMonthSum/viewTitle",
        data:{"ym":ym,"dept":dept,"userid":userid},
        type:"post",
        dataType:"json",
        success:function (rs){
            var cols = [{"field":"tag","title":"公司","width":100},{"field":"dept","title":"部门","width":100},{"field":"user","title":"姓名","width":100}];
            if(rs!=null){
                for(var i=0;i<rs.length;i++){
                    cols.push({"field":rs[i],"title":rs[i],"formatter":function(value, row , index,field){
                        if(!value){
                            return "";
                        }
                        var state = [];
                        var ss = value.split(';');
                        var lenss = row.timeLen.split(',');
                        var types = row.queType.split(',');
                        var indx = 0;
                        if(!isNaN(field)){
                            indx = parseInt(field);
                        }
                        for(var j=0;j<ss.length;j++){
                            if(ss[j]=='加班'){
                                state.push(jp.getDictLabel(${fn.toJson(fn.getDictList('stateofkaoqin'))}, ss[j], ss[j])+"("+lenss[indx-1]+")");
                            }else if(ss[j]=='矿工'){
                                state.push(jp.getDictLabel(${fn.toJson(fn.getDictList('stateofkaoqin'))}, ss[j], ss[j])+"("+types[indx-1]+")");
                            }else{
                                state.push(jp.getDictLabel(${fn.toJson(fn.getDictList('stateofkaoqin'))}, ss[j], ss[j]));
                            }

                        }
                        return state.join(' ')
                    }});
                }
            }
            createTable(cols,ym,dept,userid,tag);
        },
        error:function(){
            jp.error("打开失败")
        }
    })
}

function goToSync(){
    var ym = $("#ym").val();
    if(!ym){
        jp.warning("请选择年月");
        return false;
    }
    var index = jp.loading();
    jp.post("${ctx}/view/dakamonthsum/viewDakaMonthSum/syncData",{"ym":ym},function(data){
        if(data.success){
            refresh();
        }else{
            jp.error(data.msg);
        }
        jp.close(index);
    })
}

function createTable(titles,ym,dept,userid,tag){
    $('#viewTable').bootstrapTable('destroy').bootstrapTable({
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
        stickyHeaderOffsetY: 1,
        //显示检索按钮
        showSearch: false,
        //显示刷新按钮
        showRefresh: false,
        //显示切换手机试图按钮
        showToggle: false,
        //显示 内容列下拉框
        showColumns: false,
        //显示到处按钮
        showExport: true,
        //显示切换分页按钮
        showPaginationSwitch: true,
        //最低显示2行
        minimumCountColumns: 2,
        //是否显示行间隔色
        striped: true,
        rightFixedColumns: true, //右侧冻结列
        rightFixedNumber: 2,
        //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性(*)
        cache: false,
        //是否显示分页(*)
        pagination: true,
        sidePagination: "client",
        //排序方式
        sortOrder: "asc",
        //初始化加载第一页，默认第一页
        pageNumber:1,
        //每页的记录行数(*)
        pageSize: 25,
        //可供选择的每页的行数(*)
        pageList: [10, 25, 50, 100,200,400,500],
        //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据
        url: "${ctx}/view/dakamonthsum/viewDakaMonthSum/viewdata",
        //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
        //queryParamsType:'',
        ////查询参数,每次调用是会带上这个参数，可自定义
        queryParams : function(params) {

            var searchParam = {
                "ym":ym,"userid":userid,"dept":dept,"tag":tag
            }
            return searchParam;
        },
        onShowSearch: function () {
        },
        columns: titles
    });
}
</script>