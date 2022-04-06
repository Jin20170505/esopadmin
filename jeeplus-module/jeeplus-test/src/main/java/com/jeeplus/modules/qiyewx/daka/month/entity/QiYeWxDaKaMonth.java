/**
 *
 */
package com.jeeplus.modules.qiyewx.daka.month.entity;

import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 打卡月报Entity
 * @author Jin
 * @version 2021-08-31
 */
public class QiYeWxDaKaMonth extends DataEntity<QiYeWxDaKaMonth> {
	
	private static final long serialVersionUID = 1L;
	private String ym;	// 年月
	private String recordType;		// 记录类型
	private String name;		// 打卡人员姓名
	private String acctid;		// 打卡人员帐号
	private String departsName;		// 打卡人员所在部门
	private List<QiYeWxDakaMonthException> qiYeWxDakaMonthExceptionList = Lists.newArrayList();		// 子表列表
	private List<QiYeWxDaKaMonthOverwork> qiYeWxDaKaMonthOverworkList = Lists.newArrayList();		// 子表列表
	private List<QiYeWxDaKaMonthSpItem> qiYeWxDaKaMonthSpItemList = Lists.newArrayList();		// 子表列表
	private List<QiYeWxDaKaMonthSummary> qiYeWxDaKaMonthSummaryList = Lists.newArrayList();		// 子表列表
	
	public QiYeWxDaKaMonth() {
		super();
	}

	public QiYeWxDaKaMonth(String id){
		super(id);
	}

	public String getYm() {
		return ym;
	}

	public void setYm(String ym) {
		this.ym = ym;
	}

	@ExcelField(title="记录类型", dictType="qywx_daka_month_type", align=2, sort=7)
	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	
	@ExcelField(title="打卡人员姓名", align=2, sort=8)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="打卡人员帐号", align=2, sort=9)
	public String getAcctid() {
		return acctid;
	}

	public void setAcctid(String acctid) {
		this.acctid = acctid;
	}
	
	@ExcelField(title="打卡人员所在部门", align=2, sort=10)
	public String getDepartsName() {
		return departsName;
	}

	public void setDepartsName(String departsName) {
		this.departsName = departsName;
	}
	
	public List<QiYeWxDakaMonthException> getQiYeWxDakaMonthExceptionList() {
		return qiYeWxDakaMonthExceptionList;
	}

	public void setQiYeWxDakaMonthExceptionList(List<QiYeWxDakaMonthException> qiYeWxDakaMonthExceptionList) {
		this.qiYeWxDakaMonthExceptionList = qiYeWxDakaMonthExceptionList;
	}
	public List<QiYeWxDaKaMonthOverwork> getQiYeWxDaKaMonthOverworkList() {
		return qiYeWxDaKaMonthOverworkList;
	}

	public void setQiYeWxDaKaMonthOverworkList(List<QiYeWxDaKaMonthOverwork> qiYeWxDaKaMonthOverworkList) {
		this.qiYeWxDaKaMonthOverworkList = qiYeWxDaKaMonthOverworkList;
	}
	public List<QiYeWxDaKaMonthSpItem> getQiYeWxDaKaMonthSpItemList() {
		return qiYeWxDaKaMonthSpItemList;
	}

	public void setQiYeWxDaKaMonthSpItemList(List<QiYeWxDaKaMonthSpItem> qiYeWxDaKaMonthSpItemList) {
		this.qiYeWxDaKaMonthSpItemList = qiYeWxDaKaMonthSpItemList;
	}
	public List<QiYeWxDaKaMonthSummary> getQiYeWxDaKaMonthSummaryList() {
		return qiYeWxDaKaMonthSummaryList;
	}

	public void setQiYeWxDaKaMonthSummaryList(List<QiYeWxDaKaMonthSummary> qiYeWxDaKaMonthSummaryList) {
		this.qiYeWxDaKaMonthSummaryList = qiYeWxDaKaMonthSummaryList;
	}
}