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
        url: "${ctx}/u8data/U8InvPostionSum/data",
        queryParams: function (params) {
            var searchParam = $("#searchForm").serializeJSON();
            var ckcode = $("#ckId").val();
            searchParam.pageNo = params.limit === undefined ? "1" : params.offset / params.limit + 1;
            searchParam.pageSize = params.limit === undefined ? -1 : params.limit;
            if (params.sort && params.order) {
                searchParam.orderBy = params.sort + " " + params.order;
            }
            searchParam.cwhcode = ckcode;
            return searchParam;
        },
        onShowSearch: function () {
            $("#search-collapse").fadeToggle();
        },
        columns: [{
            checkbox: true

        }
            , {
                field: 'cwhcode',
                title: '仓库编码'
              }
              ,{
              field: 'cwhname',
              title: '仓库名称'
              },{
                field: 'cPosCode',
                title: '货位编码'
              }
              ,{
              field: 'cinvcode',
              title: '存货编号'
              }
              ,{
              field: 'cinvname',
              title: '存货名称'
              }
              ,{
              field: 'cinvstd',
              title: '规格型号'
              }
              ,{
              field: 'iquantity',
              title: '现存数量'
              }
            ,{
                field: 'cbatch',
                title: '批号'
            }
            ,{
                field: 'dMadeDate',
                title: '生产日期'
            }
            ,{
                field: 'iMassDates',
                title: '保质期天数'
            }
            ,{
                field: 'dVDate',
                title: '失效日期'
            }
    ]

});


    $('#table').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove,#jihua').prop('disabled', ! $('#table').bootstrapTable('getSelections').length);
            $('#chaidan,#print').prop('disabled', $('#table').bootstrapTable('getSelections').length!=1);
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

  $('#startdate').datetimepicker({
  format:'Y-m-d'
});
  $('#enddate').datetimepicker({
  format:'Y-m-d'
});
	});

	//获取选中行
  function getIdSelections() {
        return $.map($("#table").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  // 打印备料单
  function printbl(){
  var rid = getIdSelections();
  jp.windowOpen('${ctx}/business/shengchan/dingdan/businessShengChanDingDan/goToBeiLiaoPrint?rid='+rid,"备料单--打印",window.screen.height,window.screen.width);
}
    // 生成计划工单
  function jihua(){
    var rid = getIdSelections();
  jp.confirm('确认要生产计划单吗？', function(){
  var index =jp.loading();
  jp.post('${ctx}/business/shengchan/dingdan/businessShengChanDingDan/doPlan',{'rids':rid+""},function (rs){
    if(rs.success){
    jp.toastr_success(rs.msg);
    refresh();
  }else{
    jp.toastr_error(rs.msg);
  }
    jp.close(index);
  });
})

}
  // 拆单
    function chaidan(){
      var rid = getIdSelections();
      top.layer.open({
        type: 1,
        area: ['500px', '200px'],
        title:"输入能效(即计划工单每单的工单数量)",
        auto:true,
        maxmin: true, //开启最大化最小化按钮
        content: $('#scddform').html(),
        btn: ['确定', '关闭'],
        yes: function(index, layero){
          var num = $(layero).find("#num").val();
          if(!num){
            jp.warning("请输入数量");
            return false;
          }
          doChaidan(rid,num);
          top.layer.close(index);
        },
        cancel: function(index){
          top.layer.close(index);
        }
      });
    }
  function doChaidan(rid,num){
    var index  =  jp.loading('生成中...');
    jp.get('${ctx}/business/shengchan/dingdan/businessShengChanDingDan/chaidan?rid='+rid+'&num='+num,function (rs){
        if(rs.success){
          jp.toastr_success(rs.msg);
          refresh();
        }else{
          jp.toastr_error(rs.msg);
        }
        jp.close(index);
    });
  }
    //刷新列表
  function refresh() {
      $('#table').bootstrapTable('refresh');
  }

</script>