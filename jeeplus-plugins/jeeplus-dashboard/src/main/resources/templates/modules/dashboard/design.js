



function refresh(data) {

    if(data.add){

        var getTpl = '<div><div class="grid-layout-item-content" name="id" id="{{d.id}}">'+
            $("#network").html()+
            "</div></div>";
        laytpl(getTpl).render(data, function(html){
            this.grid = $('.grid-layout').data('gridlayout');
            this.grid.addWidget(html,
                0, 0, 2, 2);
            bindButton();
        });
    }else {


        var getTpl = $($("#network").html()).children("div").html();
        laytpl(getTpl).render(data, function(html){
            $("#"+data.id).html(html)
            bindButton();
        });
    }



}

function refresh2(data) {
    if(data.add){
        var getTpl = '<div><div class="grid-layout-item-content" name="id" id="{{d.id}}">'+
            $("#widget").html()+
            "</div></div>";
        laytpl(getTpl).render(data, function(html){
            this.grid = $('.grid-layout').data('gridlayout');
            this.grid.addWidget(html,
                0, 0, 2, 2);
            bindButton();
        });
    }else {


        var getTpl = $("#widget").html();
        laytpl(getTpl).render(data, function(html){
            $("#"+data.id).html(html)
            bindButton();
        });
    }


}
function refresh3(data) {
    if(data.add){
        var getTpl = '<div><div class="grid-layout-item-content" name="id" id="{{d.id}}">'+
            $("#echarts").html()+
            "</div></div>";
        laytpl(getTpl).render(data, function(html){
            this.grid = $('.grid-layout').data('gridlayout');
            this.grid.addWidget(html,
                0, 0, 2, 2);
            buildChart(data.widget.id, data.widget.url);
            bindButton();
        });
    }else {


        var getTpl = $("#echarts").html();
        laytpl(getTpl).render(data, function(html){
            $("#"+data.id).html(html)
            buildChart(data.widget.id, data.widget.url);
            bindButton();
        });
    }



}


function refresh4(data) {
    if(data.add){
        var getTpl = '<div><div class="grid-layout-item-content" name="id" id="{{d.id}}">'+
            $("#table").html()+
            "</div></div>";
        laytpl(getTpl).render(data, function(html){
            this.grid = $('.grid-layout').data('gridlayout');
            this.grid.addWidget(html,
                0, 0, 2, 2);
            bindButton();
        });
    }else {

        var getTpl = $("#table").html();
        laytpl(getTpl).render(data, function(html){
            $("#"+data.id).html(html)
            bindButton();
        });
    }


}




function buildChart(id, echartsId) {
    var myChart = echarts.init(document.getElementById(id));
    // window.addEventListener("resize",function (ev) {
    //     myChart.resize();
    //  });
    $("#"+id).bind('resize', function(e) {
        myChart.resize();
    });
    jp.get(ctx+"/echarts/getOption/"+echartsId, function (option) {
        // 指定图表的配置项和数据
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    })

}

function bindButton() {
    $("button.del").unbind("click").bind("click", function () {
        el = $(this).closest('.grid-layout-item')

        $('.grid-layout').data('gridlayout').remove_widget(el);

    });

    $("button.set").unbind("click").bind("click", function () {
        var id = $(this).closest('.grid-layout-item').find(".widget").attr("id");
        var type =  $(this).closest('.grid-layout-item').find(".widget").attr("data-type");
        var containerId = $(this).closest('.grid-layout-item-content').attr("id");

        if(type == '1'){
            open1(id, containerId);
        }else if(type == '2'){
            open2(id, containerId);
        }else if(type == '3'){
            open3(id,containerId);
        }else if(type == '4'){
            open4(id,containerId);
        }


    });
}

function open1(id, containerId){
    var title = '添加页面组件';
    var url = ctx+"/widget/form/widgetForm1";
    if(id){
        url = url +"?id="+id;
        if (containerId){
            url = url +"&containerId="+containerId;
        }
    }
    jp.openSaveDialog(title,url,"600px","300px");

}
function open2(id, containerId){
    var title = '添加统计组件向导';
    var url = ctx+"/widget/form/widgetForm2";
    if(id){
        url = url +"?id="+id;
        if (containerId){
            url = url +"&containerId="+containerId;
        }
    }

    var auto = false;//是否使用响应式，使用百分比时，应设置为false
    top.layer.open({
        type: 2,
        area: ['710px', '500px'],
        title: title,
        auto: auto,
        maxmin: true, //开启最大化最小化按钮
        content: url,
        btn: ['上一步', '下一步', '完成', '关闭'],
        yes: function (index, layero) {
            var iframeWin = layero.find('iframe')[0]; //得到弹出的窗口对象，执行窗口内iframe页的方法：iframeWin.method();

            var api = iframeWin.contentWindow.$('#widgetForm').data('wizard');
            api.back();
            var currentIndex = api.currentIndex();
            var lastIndex = api.lastIndex();

            if (currentIndex == 0) {
                $(".layui-layer-btn0").attr("disabled", "disabled");
            } else {
                $(".layui-layer-btn0").removeAttr("disabled");
            }
            if (currentIndex < lastIndex) {
                $(".layui-layer-btn1").removeAttr("disabled");
                $(".layui-layer-btn2").attr("disabled", "dsiabled");
            } else {
                $(".layui-layer-btn1").attr("disabled", "disabled");
                $(".layui-layer-btn2").removeAttr("disabled");
            }

            return false;
        },
        btn2: function (index, layero) {
            var iframeWin = layero.find('iframe')[0]; //得到弹出的窗口对象，执行窗口内iframe页的方法：iframeWin.method();
            var api = iframeWin.contentWindow.$('#widgetForm').data('wizard');
            api.next();
            var currentIndex = api.currentIndex();
            var lastIndex = api.lastIndex();

            if (currentIndex == 0) {
                $(".layui-layer-btn0").attr("disabled", "disabled");
            } else {
                $(".layui-layer-btn0").removeAttr("disabled");
            }
            if (currentIndex < lastIndex) {
                $(".layui-layer-btn1").removeAttr("disabled");
                $(".layui-layer-btn2").attr("disabled", "dsiabled");
            } else {
                $(".layui-layer-btn1").attr("disabled", "disabled");
                $(".layui-layer-btn2").removeAttr("disabled");
            }

            return false;


        },
        btn3: function (index, layero) {
            var iframeWin = layero.find('iframe')[0]; //得到弹出的窗口对象，执行窗口内iframe页的方法：iframeWin.method();
            iframeWin.contentWindow.$().wizard('finish');
            ;//调用保存事件，在 弹出页内，需要定义save方法。处理保存事件。
            iframeWin.contentWindow.save();
            return false;
        },
        cancel: function (index) {
        }
    });
}

function open3(id, containerId){
    var title = '添加图表组件';
    var url = ctx+"/widget/form/widgetForm3";
    if(id){
        url = url +"?id="+id;
        if (containerId){
            url = url +"&containerId="+containerId;
        }
    }
    jp.openSaveDialog(title,url,"600px","300px");

}

function open4(id, containerId){
    var title = '添加表格组件向导';
    var url = ctx+"/widget/form/widgetForm4";
    if(id){
        url = url +"?id="+id;
        if (containerId){
            url = url +"&containerId="+containerId;
        }
    }

    var auto = false;//是否使用响应式，使用百分比时，应设置为false
    top.layer.open({
        type: 2,
        area: ['710px', '500px'],
        title: title,
        auto: auto,
        maxmin: true, //开启最大化最小化按钮
        content: url,
        btn: ['上一步', '下一步', '完成', '关闭'],
        yes: function (index, layero) {
            var iframeWin = layero.find('iframe')[0]; //得到弹出的窗口对象，执行窗口内iframe页的方法：iframeWin.method();

            var api = iframeWin.contentWindow.$('#widgetForm').data('wizard');
            api.back();
            var currentIndex = api.currentIndex();
            var lastIndex = api.lastIndex();

            if (currentIndex == 0) {
                $(".layui-layer-btn0").attr("disabled", "disabled");
            } else {
                $(".layui-layer-btn0").removeAttr("disabled");
            }
            if (currentIndex < lastIndex) {
                $(".layui-layer-btn1").removeAttr("disabled");
                $(".layui-layer-btn2").attr("disabled", "dsiabled");
            } else {
                $(".layui-layer-btn1").attr("disabled", "disabled");
                $(".layui-layer-btn2").removeAttr("disabled");
            }

            return false;
        },
        btn2: function (index, layero) {
            var iframeWin = layero.find('iframe')[0]; //得到弹出的窗口对象，执行窗口内iframe页的方法：iframeWin.method();
            var api = iframeWin.contentWindow.$('#widgetForm').data('wizard');
            api.next();
            var currentIndex = api.currentIndex();
            var lastIndex = api.lastIndex();

            if (currentIndex == 0) {
                $(".layui-layer-btn0").attr("disabled", "disabled");
            } else {
                $(".layui-layer-btn0").removeAttr("disabled");
            }
            if (currentIndex < lastIndex) {
                $(".layui-layer-btn1").removeAttr("disabled");
                $(".layui-layer-btn2").attr("disabled", "dsiabled");
            } else {
                $(".layui-layer-btn1").attr("disabled", "disabled");
                $(".layui-layer-btn2").removeAttr("disabled");
            }

            return false;


        },
        btn3: function (index, layero) {
            var iframeWin = layero.find('iframe')[0]; //得到弹出的窗口对象，执行窗口内iframe页的方法：iframeWin.method();
            iframeWin.contentWindow.$().wizard('finish');
            ;//调用保存事件，在 弹出页内，需要定义save方法。处理保存事件。
            iframeWin.contentWindow.save();
            return false;
        },
        cancel: function (index) {
        }
    });
}
$(function () {




    var options = {
        width: 12,
        float: true,
        resizable: {
            handles: 'e, se, s, sw, w'
        },
        cellHeight: 80,
        verticalMargin: 10,
        acceptWidgets: '.grid-layout-item'

    };

    $('.grid-layout').gridlayout(options);




    $('.grid-layout').on('gsresizestop', function (event, elem) {
        var id = $(elem).find(".echart").attr("id");
        if(!id){
            return;
        }
        var myChart = echarts.getInstanceByDom(document.getElementById(id));
        myChart.resize();
    });


    new function () {
        this.grid = $('.grid-layout').data('gridlayout');

        var oldItems;
        this.loadGrid = function () {
            _this = this;
            jp.get(ctx+"/dashBoard/serializedData?id="+dashboardId,function (data) {
                _this.serializedData = eval(data);
                _this.grid.removeAll();

                var items = GridLayoutUI.Utils.sort(_this.serializedData);
                oldItems = items;
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

                        bindButton();
                    });


                }.bind(_this));



                return false;
            })

        }.bind(this);


        this.saveGrid = function (succFn) {
            this.serializedData = _.map($('.grid-layout > .grid-layout-item:visible'), function (el, i) {
                el = $(el);
                var node = el.data('_gridlayout_node');
                return {
                    id: $($("div[name='id']")[i]).attr("id"),
                    x: node.x,
                    y: node.y,
                    width: node.width,
                    height: node.height,
                    widget: {
                        id: $($("div[name='widget.id']")[i]).attr("id")
                    },
                    borderClass: $($("div[name='widget.id']")[i]).attr("data-border")
                };
            });
            var params = {
                id: dashboardId,
                style: $("body").attr("style")

            }

            var ids = ",";
            var indexFlag = 0;
            for (var i = 0; i < this.serializedData.length; i++) {
                if (this.serializedData[i].id) {
                    ids = ids + this.serializedData[i].id + ",";
                }

                params['containerList[' + i + '].id'] = this.serializedData[i].id == undefined ? "" : this.serializedData[i].id;
                params['containerList[' + i + '].x'] = this.serializedData[i].x;
                params['containerList[' + i + '].y'] = this.serializedData[i].y;
                params['containerList[' + i + '].width'] = this.serializedData[i].width;
                params['containerList[' + i + '].height'] = this.serializedData[i].height;
                params['containerList[' + i + '].widget.id'] = this.serializedData[i].widget.id == undefined ? "" : this.serializedData[i].widget.id;
                params['containerList[' + i + '].borderClass'] = this.serializedData[i].borderClass;
                indexFlag = i;
            }

            _.each(oldItems, function (node) {

                if (ids.indexOf("," + node.id + ",") < 0) {
                    indexFlag++;
                    params['containerList[' + indexFlag + '].id'] = node.id;
                    params['containerList[' + indexFlag + '].delFlag'] = "1";
                }
            });


            jp.post(ctx+"/dashBoard/save", params, function (data) {

                if (data.success) {
                    jp.info("保存成功!");
                    if(succFn){
                        succFn();
                    }

                }
            })


            return false;
        }.bind(this);

        this.clearGrid = function () {
            this.grid.removeAll();
            return false;
        }.bind(this);



        $('#save-grid').click(function () {
            jp.block("#dashboard");
            this.saveGrid(function (){
                this.loadGrid();
                jp.unblock("#dashboard");
            }.bind(this));
        }.bind(this));
        $('#load-grid').click(this.loadGrid);
        $('#clear-grid').click(this.clearGrid);

        this.loadGrid();
    };
});
