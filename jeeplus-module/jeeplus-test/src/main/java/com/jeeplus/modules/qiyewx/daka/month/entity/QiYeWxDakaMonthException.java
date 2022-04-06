/**
 *
 */
package com.jeeplus.modules.qiyewx.daka.month.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 异常状态统计信息Entity
 * @author Jin
 * @version 2021-08-31
 */
public class QiYeWxDakaMonthException extends DataEntity<QiYeWxDakaMonthException> {
	
	private static final long serialVersionUID = 1L;
	private QiYeWxDaKaMonth month;		// 月报表 父类
	/** 1-迟到；2-早退；3-缺卡；4-旷工；5-地点异常；6-设备异常 */
	private String exception;		// 异常类型
	private Integer count;		// 异常次数
	private Long duration;		// 异常时长
	
	public QiYeWxDakaMonthException() {
		super();
	}

	public QiYeWxDakaMonthException(String id){
		super(id);
	}

	public QiYeWxDakaMonthException(QiYeWxDaKaMonth month){
		this.month = month;
	}

	public QiYeWxDaKaMonth getMonth() {
		return month;
	}

	public void setMonth(QiYeWxDaKaMonth month) {
		this.month = month;
	}
	
	@ExcelField(title="异常类型", dictType="qywx_daka_month_exception_type", align=2, sort=8)
	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}
	
	@ExcelField(title="异常次数", align=2, sort=9)
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	@ExcelField(title="异常时长", align=2, sort=10)
	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}
	
}