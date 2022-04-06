/**
 *
 */
package com.jeeplus.modules.qiyewx.sp.entity;

import com.jeeplus.modules.qiyewx.base.entity.QiYeWxEmployee;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 审批流程信息Entity
 * @author Jin
 * @version 2021-08-31
 */
public class QiYeWxSpRecord extends DataEntity<QiYeWxSpRecord> {
	
	private static final long serialVersionUID = 1L;
	private QiYeWxSp sp;		// 审批id 父类
	private QiYeWxEmployee approver;		// 审批人
	private Date sptime;		// 审批时间
	private String speech;		// 审批意见
	private String spStatus;		// 审批状态
	private String mediaid;		// 附件id
	
	public QiYeWxSpRecord() {
		super();
	}

	public QiYeWxSpRecord(String id){
		super(id);
	}

	public QiYeWxSpRecord(QiYeWxSp sp){
		this.sp = sp;
	}

	public QiYeWxSp getSp() {
		return sp;
	}

	public void setSp(QiYeWxSp sp) {
		this.sp = sp;
	}
	
	@ExcelField(title="审批人", fieldType=QiYeWxEmployee.class, value="approver.name", align=2, sort=8)
	public QiYeWxEmployee getApprover() {
		return approver;
	}

	public void setApprover(QiYeWxEmployee approver) {
		this.approver = approver;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="审批时间", align=2, sort=9)
	public Date getSptime() {
		return sptime;
	}

	public void setSptime(Date sptime) {
		this.sptime = sptime;
	}
	
	@ExcelField(title="审批意见", align=2, sort=10)
	public String getSpeech() {
		return speech;
	}

	public void setSpeech(String speech) {
		this.speech = speech;
	}
	
	@ExcelField(title="审批状态", dictType="sp_status", align=2, sort=11)
	public String getSpStatus() {
		return spStatus;
	}

	public void setSpStatus(String spStatus) {
		this.spStatus = spStatus;
	}
	
	@ExcelField(title="附件id", align=2, sort=12)
	public String getMediaid() {
		return mediaid;
	}

	public void setMediaid(String mediaid) {
		this.mediaid = mediaid;
	}
	
}