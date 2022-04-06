/**
 *
 */
package com.jeeplus.modules.qiyewx.daka.month.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 假勤统计信息Entity
 * @author Jin
 * @version 2021-08-31
 */
public class QiYeWxDaKaMonthSpItem extends DataEntity<QiYeWxDaKaMonthSpItem> {
	
	private static final long serialVersionUID = 1L;
	private QiYeWxDaKaMonth month;		// 月卡报表 父类
	private String type;		// 假勤类型
	private Integer count;		// 假勤次数
	private Long duration;		// 假勤时长
	private String timeType;		// 时长单位
	private String name;		// 统计项名称
	
	public QiYeWxDaKaMonthSpItem() {
		super();
	}

	public QiYeWxDaKaMonthSpItem(String id){
		super(id);
	}

	public QiYeWxDaKaMonthSpItem(QiYeWxDaKaMonth month){
		this.month = month;
	}

	public QiYeWxDaKaMonth getMonth() {
		return month;
	}

	public void setMonth(QiYeWxDaKaMonth month) {
		this.month = month;
	}
	
	@ExcelField(title="假勤类型", dictType="qywx_daka_month_vaction_type", align=2, sort=8)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@ExcelField(title="假勤次数", align=2, sort=9)
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	@ExcelField(title="假勤时长", align=2, sort=10)
	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}
	
	@ExcelField(title="时长单位", align=2, sort=11)
	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}
	
	@ExcelField(title="统计项名称", align=2, sort=12)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}