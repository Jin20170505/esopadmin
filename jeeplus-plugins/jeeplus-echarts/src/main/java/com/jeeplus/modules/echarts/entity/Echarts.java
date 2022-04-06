/**
 * Copyright &copy; 2015-2020 磐新科技 All rights reserved.
 */
package com.jeeplus.modules.echarts.entity;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.database.datamodel.entity.DataSet;


/**
 * 图表组件Entity
 *
 * @author 刘高峰
 * @version 2018-08-13
 */
public class Echarts extends DataEntity<Echarts> {

    private static final long serialVersionUID = 1L;
    private String name;        // 组件名称
    private DataSet dataSet;        // 关联数据模型
    private String option;        // echarts定义结构体
    private String type; //图表类型
    private String xIds; //x轴
    private String yIds; //y轴

    public Echarts() {
        super();
    }

    public Echarts(String id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataSet getDataSet() {
        return dataSet;
    }

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getxIds() {
        return xIds;
    }

    public void setxIds(String xIds) {
        this.xIds = xIds;
    }

    public String getyIds() {
        return yIds;
    }

    public void setyIds(String yIds) {
        this.yIds = yIds;
    }
}
