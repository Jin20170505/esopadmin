/**
 * Copyright &copy; 2015-2020 磐新科技 All rights reserved.
 */
package com.jeeplus.modules.dashboard.entity;


import com.jeeplus.core.persistence.DataEntity;

/**
 * 容器Entity
 *
 * @author 刘高峰
 * @version 2018-09-12
 */
public class Container extends DataEntity<Container> {

    private static final long serialVersionUID = 1L;
    private Widget widget;        // 组件
    private Integer x;        // x坐标
    private Integer y;        // y坐标
    private Integer width;        // 宽度
    private Integer height;        // 高度
    private DashBoard dashboard;        // 关联仪表盘 父类
    private String borderClass; //边框

    public Container() {
        super();
    }

    public Container(String id) {
        super(id);
    }

    public Container(DashBoard dashboard) {
        this.dashboard = dashboard;
    }

    public Widget getWidget() {
        return widget;
    }

    public void setWidget(Widget widget) {
        this.widget = widget;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public DashBoard getDashboard() {
        return dashboard;
    }

    public void setDashboard(DashBoard dashboard) {
        this.dashboard = dashboard;
    }

    public String getBorderClass() {
        return borderClass;
    }

    public void setBorderClass(String borderClass) {
        this.borderClass = borderClass;
    }
}
