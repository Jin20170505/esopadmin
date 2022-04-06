/**
 *
 */
package com.jeeplus.modules.qiyewx.daka.month.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 汇总信息Entity
 * @author Jin
 * @version 2021-08-31
 */
public class QiYeWxDaKaMonthSummary extends DataEntity<QiYeWxDaKaMonthSummary> {
	
	private static final long serialVersionUID = 1L;
	private QiYeWxDaKaMonth month;		// 月报主表 父类
	private Integer workDays;		// 应打卡天数
	private Integer regularDays;		// 正常天数
	private Integer exceptDays;		// 异常天数
	private Long regularWorkSec;		// 实际工作时长
	private Long standardWorkSec;		// 标准工作时长
	
	public QiYeWxDaKaMonthSummary() {
		super();
	}

	public QiYeWxDaKaMonthSummary(String id){
		super(id);
	}

	public QiYeWxDaKaMonthSummary(QiYeWxDaKaMonth month){
		this.month = month;
	}

	public QiYeWxDaKaMonth getMonth() {
		return month;
	}

	public void setMonth(QiYeWxDaKaMonth month) {
		this.month = month;
	}
	
	@ExcelField(title="应打卡天数", align=2, sort=8)
	public Integer getWorkDays() {
		return workDays;
	}

	public void setWorkDays(Integer workDays) {
		this.workDays = workDays;
	}
	
	@ExcelField(title="正常天数", align=2, sort=9)
	public Integer getRegularDays() {
		return regularDays;
	}

	public void setRegularDays(Integer regularDays) {
		this.regularDays = regularDays;
	}
	
	@ExcelField(title="异常天数", align=2, sort=10)
	public Integer getExceptDays() {
		return exceptDays;
	}

	public void setExceptDays(Integer exceptDays) {
		this.exceptDays = exceptDays;
	}
	
	@ExcelField(title="实际工作时长", align=2, sort=11)
	public Long getRegularWorkSec() {
		return regularWorkSec;
	}

	public void setRegularWorkSec(Long regularWorkSec) {
		this.regularWorkSec = regularWorkSec;
	}
	
	@ExcelField(title="标准工作时长", align=2, sort=12)
	public Long getStandardWorkSec() {
		return standardWorkSec;
	}

	public void setStandardWorkSec(Long standardWorkSec) {
		this.standardWorkSec = standardWorkSec;
	}
	
}