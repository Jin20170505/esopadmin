/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.qiyewx.daka_day.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 打卡日报Entity
 * @author Jin
 * @version 2021-11-25
 */
public class QiYewxDaKaDay extends DataEntity<QiYewxDaKaDay> {
	
	private static final long serialVersionUID = 1L;
	private Date date;		// 日期
	private String name;		// 姓名
	private String userid;		// 用户id
	private String departsName;		// 部门
	private String recordType;		// 打卡类型
	private String dayType;		// 日报类型
	private String otStatus;		// 加班状态
	private Integer otDuration;		// 加班时长(单位s)
	private Integer checkinCount;		// 当日打卡次数
	private Integer regularWorkSec;		// 当日实际工作时长(单位s)
	private Integer standardWorkSec;		// 当日标准工作时长(单位s)
	private Date beginDate;		// 开始 日期
	private Date endDate;		// 结束 日期
	private List<QiyewxDakaDaySpitems> qiyewxDakaDaySpitemsList = Lists.newArrayList();		// 子表列表
	
	public QiYewxDaKaDay() {
		super();
	}

	public QiYewxDaKaDay(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	@ExcelField(title="日期", align=2, sort=7)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@ExcelField(title="姓名", align=2, sort=8)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="用户id", align=2, sort=9)
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	@ExcelField(title="部门", align=2, sort=10)
	public String getDepartsName() {
		return departsName;
	}

	public void setDepartsName(String departsName) {
		this.departsName = departsName;
	}
	
	@ExcelField(title="打卡类型", dictType="qywx_daka_type", align=2, sort=11)
	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	
	@ExcelField(title="日报类型", dictType="salary_jixiao_jidu", align=2, sort=12)
	public String getDayType() {
		return dayType;
	}

	public void setDayType(String dayType) {
		this.dayType = dayType;
	}
	
	@ExcelField(title="加班状态", dictType="sp_status", align=2, sort=13)
	public String getOtStatus() {
		return otStatus;
	}

	public void setOtStatus(String otStatus) {
		this.otStatus = otStatus;
	}
	
	@ExcelField(title="加班时长(单位s)", align=2, sort=14)
	public Integer getOtDuration() {
		return otDuration;
	}

	public void setOtDuration(Integer otDuration) {
		this.otDuration = otDuration;
	}
	
	@ExcelField(title="当日打卡次数", align=2, sort=15)
	public Integer getCheckinCount() {
		return checkinCount;
	}

	public void setCheckinCount(Integer checkinCount) {
		this.checkinCount = checkinCount;
	}
	
	@ExcelField(title="当日实际工作时长(单位s)", align=2, sort=16)
	public Integer getRegularWorkSec() {
		return regularWorkSec;
	}

	public void setRegularWorkSec(Integer regularWorkSec) {
		this.regularWorkSec = regularWorkSec;
	}
	
	@ExcelField(title="当日标准工作时长(单位s)", align=2, sort=17)
	public Integer getStandardWorkSec() {
		return standardWorkSec;
	}

	public void setStandardWorkSec(Integer standardWorkSec) {
		this.standardWorkSec = standardWorkSec;
	}
	
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
		
	public List<QiyewxDakaDaySpitems> getQiyewxDakaDaySpitemsList() {
		return qiyewxDakaDaySpitemsList;
	}

	public void setQiyewxDakaDaySpitemsList(List<QiyewxDakaDaySpitems> qiyewxDakaDaySpitemsList) {
		this.qiyewxDakaDaySpitemsList = qiyewxDakaDaySpitemsList;
	}
}