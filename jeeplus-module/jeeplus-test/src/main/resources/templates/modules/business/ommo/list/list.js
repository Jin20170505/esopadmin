<script>
  $(document).ready(function() {
  var to = false;
  $('#search_q').keyup(function () {
  if(to) { clearTimeout(to); }
  to = setTimeout(function () {
  var v = $('#search_q').val();
  $('#businessProductTypeOnlyReadjsTree').jstree(true).search(v);
}, 250);
});
  $('#businessProductTypeOnlyReadjsTree').jstree({
  'core' : {
  "multiple": false,
  "animation": 0,
  "themes": {"icons": true, "stripes": false},
  'data' : {
  "url" : "${ctx}/business/shengchan/dingdan/businessShengChanDingDan/treeData",
  "dataType" : "json"
}
},
  "conditionalselect" : function (node, event) {
  return false;
},
  'plugins': ['types', 'wholerow', "search"],
  "types": {
  "default": {
  "icon": "fa fa-folder text-custom"
},
  "file": {
  "icon": "fa fa-file text-success"
}
}

}).bind("activate_node.jstree", function (obj, e) {
  var node = $('#businessProductTypeOnlyReadjsTree').jstree(true).get_selected(true)[0];
  var opt = {
  silent: true,
  query:{
  'ischaidan':node.text
}
};
  $("#ischaidan").val(node.text);
  $('#table').bootstrapTable('refresh',opt);
}).on('loaded.jstree', function() {
  $("#businessProductTypeOnlyReadjsTree").jstree('open_all');
});
});
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
        url: "${ctx}/business/ommo/businessOmMoMain/mxdata",
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
          ,{
            field: 'ischaidan',
            title: '拆单状态'
          }
            , {
                field: 'mo.code',
                title: '委外单号',
                sortable: true,
                sortName: 'b.code'
              }
              ,{
              field: 'no',
              title: '行号',
              sortable: true,
              sortName: 'no'
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
              field: 'num',
              title: '数量'
              }
          ,{
            field: 'donenum',
            title: '已拆数量'
          }
          ,{
            field: 'nonum',
            title: '未拆数量'
          }
            ,{
                field: 'unit',
                title: '单位'
            }
          ,{
            field: 'startdate',
            title: '计划下达日期',
            sortable: true,
            sortName: 'startdate'
          }
          ,{
            field: 'arrivedate',
            title: '计划到货日期',
            sortable: true,
            sortName: 'arrivedate'
          },{
            field: 'memo',
            title: '备注'
          }
    ]

});


    $('#table').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove,#jihua').prop('disabled', ! $('#table').bootstrapTable('getSelections').length);
            $('#chaidan,#print,#handler').prop('disabled', $('#table').bootstrapTable('getSelections').length!=1);
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
  function getRowSelections() {
  return $.map($("#table").bootstrapTable('getSelections'), function (row) {
  return row
});
}
  function printbl(){
  var rid = getIdSelections();
    jp.windowOpen('${ctx}/business/ommo/businessOmMoMain/goToPrint?rid='+rid,"委外订单--打印",window.screen.width*0.9,window.screen.height);
}
  // 拆单
  function chaidan(){
  var rid = getIdSelections();
  top.layer.open({
  type: 1,
  area: ['500px', '200px'],
  title:"输入每单的数量",
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
  jp.get('${ctx}/business/ommo/businessOmMoMain/chaidan?rid='+rid+'&num='+num,function (rs){
  if(rs.success){
  jp.toastr_success(rs.msg);
  refresh();
}else{
  jp.toastr_error(rs.msg);
}
  jp.close(index);
});
}
  // 手工拆单
  function handler(){
  var rows = getRowSelections();
  var row = rows[0];
  var gdnum = row.num;
  var nonum = row.nonum;
  top.layer.open({
  type: 1,
  area: ['500px', '300px'],
  title:"输入拆单数量",
  auto:true,
  maxmin: true, //开启最大化最小化按钮
  content: $('#handlerform').html(),
  btn: ['确定', '关闭'],
  yes: function(index, layero){
  var num = $(layero).find("#handnum").val();
  if(!num){
  jp.warning("请输入数量");
  return false;
}
  doHandler(row.id,gdnum,nonum,num);
  top.layer.close(index);
},
  cancel: function(index){
  top.layer.close(index);
},
  success: function(layero, index){
  $(layero).find("#gdnum").val(gdnum);
  $(layero).find("#nonum").val(nonum);
}
});

}
  function doHandler(rid,gdnum,nonum,num){
  var index  =  jp.loading('处理中...');
  jp.post('${ctx}/business/ommo/businessOmMoMain/handlerchaidan',{'rid':rid,'gdnum':gdnum,'nonum':nonum,'num':num},function(rs){
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