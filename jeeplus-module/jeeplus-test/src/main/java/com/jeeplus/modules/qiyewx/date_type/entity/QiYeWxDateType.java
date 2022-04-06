/**
 *
 */
package com.jeeplus.modules.qiyewx.date_type.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 日期所属Entity
 * @author Jin
 * @version 2021-09-11
 */
public class QiYeWxDateType extends DataEntity<QiYeWxDateType> {
	
	private static final long serialVersionUID = 1L;
	private String ymd;		// 日期
	private String type;		// 类型
	
	public QiYeWxDateType() {
		super();
	}

	public QiYeWxDateType(String id){
		super(id);
	}

	@ExcelField(title="日期", align=2, sort=7)
	public String getYmd() {
		return ymd;
	}

	public void setYmd(String ymd) {
		this.ymd = ymd;
	}
	
	@ExcelField(title="类型", dictType="qiyewx_date_type", align=2, sort=8)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}