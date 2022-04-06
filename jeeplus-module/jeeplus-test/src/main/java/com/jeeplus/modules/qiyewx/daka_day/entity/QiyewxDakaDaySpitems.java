/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.qiyewx.daka_day.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 假勤信息Entity
 * @author Jin
 * @version 2021-11-25
 */
public class QiyewxDakaDaySpitems extends DataEntity<QiyewxDakaDaySpitems> {
	
	private static final long serialVersionUID = 1L;
	private QiYewxDaKaDay day;		// 打卡日报主表 父类
	private String name;		// 名称
	private String type;		// 请假类别
	private String vacationId;		// 假勤id
	private Integer count;		// 当日次数
	private Integer duration;		// 时长
	private Integer timeType;		// 时长单位
	
	public QiyewxDakaDaySpitems() {
		super();
	}

	public QiyewxDakaDaySpitems(String id){
		super(id);
	}

	public QiyewxDakaDaySpitems(QiYewxDaKaDay day){
		this.day = day;
	}

	public QiYewxDaKaDay getDay() {
		return day;
	}

	public void setDay(QiYewxDaKaDay day) {
		this.day = day;
	}
	
	@ExcelField(title="名称", align=2, sort=8)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="请假类别", dictType="qywx_daka_month_vaction_type", align=2, sort=9)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@ExcelField(title="假勤id", align=2, sort=10)
	public String getVacationId() {
		return vacationId;
	}

	public void setVacationId(String vacationId) {
		this.vacationId = vacationId;
	}
	
	@ExcelField(title="当日次数", align=2, sort=11)
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	@ExcelField(title="时长", align=2, sort=12)
	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
	@ExcelField(title="时长单位", align=2, sort=13)
	public Integer getTimeType() {
		return timeType;
	}

	public void setTimeType(Integer timeType) {
		this.timeType = timeType;
	}
	
}