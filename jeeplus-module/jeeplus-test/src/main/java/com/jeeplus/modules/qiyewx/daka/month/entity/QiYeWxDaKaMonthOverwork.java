/**
 *
 */
package com.jeeplus.modules.qiyewx.daka.month.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 加班情况Entity
 * @author Jin
 * @version 2021-08-31
 */
public class QiYeWxDaKaMonthOverwork extends DataEntity<QiYeWxDaKaMonthOverwork> {
	
	private static final long serialVersionUID = 1L;
	private QiYeWxDaKaMonth month;		// 月报数据 父类
	private Long workdayOverSec;		// 工作日加班时长
	private Long holidaysOverSec;		// 节假日加班时长
	private Long restdaysOverSec;		// 休息日加班时长
	
	public QiYeWxDaKaMonthOverwork() {
		super();
	}

	public QiYeWxDaKaMonthOverwork(String id){
		super(id);
	}

	public QiYeWxDaKaMonthOverwork(QiYeWxDaKaMonth month){
		this.month = month;
	}

	public QiYeWxDaKaMonth getMonth() {
		return month;
	}

	public void setMonth(QiYeWxDaKaMonth month) {
		this.month = month;
	}
	
	@ExcelField(title="工作日加班时长", align=2, sort=8)
	public Long getWorkdayOverSec() {
		if(workdayOverSec==null){
			workdayOverSec=0L;
		}
		return workdayOverSec;
	}

	public void setWorkdayOverSec(Long workdayOverSec) {
		this.workdayOverSec = workdayOverSec;
	}
	
	@ExcelField(title="节假日加班时长", align=2, sort=9)
	public Long getHolidaysOverSec() {
		if(holidaysOverSec==null){
			holidaysOverSec=0L;
		}
		return holidaysOverSec;
	}

	public void setHolidaysOverSec(Long holidaysOverSec) {
		this.holidaysOverSec = holidaysOverSec;
	}
	
	@ExcelField(title="休息日加班时长", align=2, sort=10)
	public Long getRestdaysOverSec() {
		if(restdaysOverSec==null){
			restdaysOverSec=0L;
		}
		return restdaysOverSec;
	}

	public void setRestdaysOverSec(Long restdaysOverSec) {
		this.restdaysOverSec = restdaysOverSec;
	}
	
}