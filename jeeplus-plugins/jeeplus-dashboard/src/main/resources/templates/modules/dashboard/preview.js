
function buildChart(id, echartsId) {
    var myChart = echarts.init(document.getElementById(id));
    $("#"+id).bind('resize', function(e) {
        myChart.resize();
    });
    jp.get(ctx+"/echarts/getOption/"+echartsId, function (option) {
        // 指定图表的配置项和数据
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    })

}

$(function () {



    var options = {
        width: 12,
        float: false,
        cellHeight: 80,
        verticalMargin: 10,
        disableDrag: true,
        disableResize: true
    };


    $('.grid-layout').gridlayout(options);

    new function () {

        this.grid = $('.grid-layout').data('gridlayout');
        // this.serializedData = ${serializedData!};
        // this.loadGrid = function () {
        //     var _this = this;
        //     this.grid.removeAll();
        //     var items = GridLayoutUI.Utils.sort(this.serializedData);
        //     _.each(items, function (node) {
        //
        //         var getTpl = "";
        //         if(node.widget.type == '1'){
        //             getTpl = $("#network").html();
        //         }else if(node.widget.type == '2') {
        //             getTpl = $("#widget").html();
        //         }else if(node.widget.type == '3'){
        //             getTpl = $("#echarts").html();
        //
        //         }else if(node.widget.type == '4'){
        //             getTpl = $("#table").html();
        //
        //         }
        //
        //         getTpl = '<div><div class="grid-layout-item-content" name="id" id="{{d.id}}">'+ getTpl+ "</div></div>";
        //
        //
        //         laytpl(getTpl).render(node, function(html){
        //             _this.grid.addWidget(html,
        //                 node.x, node.y, node.width, node.height);
        //             if(node.widget.type == '3'){
        //                 buildChart(node.widget.id, node.widget.url);
        //             }
        //         });
        //
        //
        //
        //
        //     }.bind(this));
        //     return false;
        // }.bind(this);


        this.loadGrid = function () {
            _this = this;
            jp.get(ctx+"/dashBoard/serializedData?id="+dashboardId,function (data) {
                _this.serializedData = eval(data);
                var items = GridLayoutUI.Utils.sort(_this.serializedData);
                _.each(items, function (node) {
                    var getTpl = "";
                    if(node.widget.type == '1'){
                        getTpl = $("#network").html();
                    }else if(node.widget.type == '2') {
                        getTpl = $("#widget").html();
                    }else if(node.widget.type == '3'){
                        getTpl = $("#echarts").html();

                    }else if(node.widget.type == '4'){
                        getTpl = $("#table").html();

                    }

                    getTpl = '<div><div class="grid-layout-item-content" name="id" id="{{d.id}}">'+ getTpl+ "</div></div>";

                    laytpl(getTpl).render(node, function(html){
                        _this.grid.addWidget(html,
                            node.x, node.y, node.width, node.height);
                        if(node.widget.type == '3'){
                            buildChart(node.widget.id, node.widget.url);
                        }
                    });


                }.bind(_this));



                return false;
            })

        }.bind(this);



        this.loadGrid();
    };
});