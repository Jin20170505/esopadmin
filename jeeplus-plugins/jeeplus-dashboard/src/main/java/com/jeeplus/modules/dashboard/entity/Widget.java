/**
 * Copyright &copy; 2015-2020 磐新科技 All rights reserved.
 */
package com.jeeplus.modules.dashboard.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.database.datalink.entity.DataSource;


/**
 * 图表组件Entity
 *
 * @author 刘高峰
 * @version 2018-08-13
 */
public class Widget extends DataEntity<Widget> {


    private static final long serialVersionUID = 1L;
    private String name;        // 组件名称
    private String url;//功能地址
    private String type;//组件类型， type =1,显示组件，type=2 显示表格 ，type=3 显示chart，type=4 显示外链
    private String icon;//组件图标
    private DataSource dataSource;
    private String sql;
    private String target;

    public Widget() {
        super();
    }

    public Widget(String id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
