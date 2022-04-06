/**
 *
 */
package com.jeeplus.modules.qiyewx.sp.entity;

import com.jeeplus.modules.qiyewx.base.entity.QiYeWxEmployee;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 申请审批Entity
 * @author Jin
 * @version 2021-08-31
 */
public class QiYeWxSp extends DataEntity<QiYeWxSp> {
	
	private static final long serialVersionUID = 1L;
	private String spNo;		// 审批号
	private String spName;		// 申请类型名称
	private QiYeWxEmployee apply;		// 申请人
	private String spStatus;		// 申请单状态
	private Date applyTime;		// 申请提交时间
	private Date starttime;
	private Date endtime;
	private String type;
	private Long timeLen; // 时长
	private String jiabanLen; // 加班时长
	private String templateId;		// 审批模板id
	private Date beginApplyTime;		// 开始 申请提交时间
	private Date endApplyTime;		// 结束 申请提交时间
	private List<QiYeWxSpApply> qiYeWxSpApplyList = Lists.newArrayList();		// 子表列表
	private List<QiYeWxSpRecord> qiYeWxSpRecordList = Lists.newArrayList();		// 子表列表
	private String ym;
	private String recordType; // 类型



	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getYm() {
		return ym;
	}

	public void setYm(String ym) {
		this.ym = ym;
	}

	public QiYeWxSp() {
		super();
	}

	public QiYeWxSp(String id){
		super(id);
	}

	@ExcelField(title="审批号", align=2, sort=7)
	public String getSpNo() {
		return spNo;
	}

	public void setSpNo(String spNo) {
		this.spNo = spNo;
	}
	
	@ExcelField(title="申请类型名称", align=2, sort=8)
	public String getSpName() {
		return spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}
	
	@ExcelField(title="申请人", fieldType=QiYeWxEmployee.class, value="apply.name", align=2, sort=9)
	public QiYeWxEmployee getApply() {
		return apply;
	}

	public void setApply(QiYeWxEmployee apply) {
		this.apply = apply;
	}
	
	@ExcelField(title="申请单状态", dictType="sp_status", align=2, sort=10)
	public String getSpStatus() {
		return spStatus;
	}

	public void setSpStatus(String spStatus) {
		this.spStatus = spStatus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="申请提交时间", align=2, sort=11)
	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	
	@ExcelField(title="审批模板id", align=2, sort=12)
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
	public Date getBeginApplyTime() {
		return beginApplyTime;
	}

	public void setBeginApplyTime(Date beginApplyTime) {
		this.beginApplyTime = beginApplyTime;
	}
	
	public Date getEndApplyTime() {
		return endApplyTime;
	}

	public void setEndApplyTime(Date endApplyTime) {
		this.endApplyTime = endApplyTime;
	}
		
	public List<QiYeWxSpApply> getQiYeWxSpApplyList() {
		return qiYeWxSpApplyList;
	}

	public void setQiYeWxSpApplyList(List<QiYeWxSpApply> qiYeWxSpApplyList) {
		this.qiYeWxSpApplyList = qiYeWxSpApplyList;
	}
	public List<QiYeWxSpRecord> getQiYeWxSpRecordList() {
		return qiYeWxSpRecordList;
	}

	public void setQiYeWxSpRecordList(List<QiYeWxSpRecord> qiYeWxSpRecordList) {
		this.qiYeWxSpRecordList = qiYeWxSpRecordList;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getTimeLen() {
		return timeLen;
	}

	public void setTimeLen(Long timeLen) {
		this.timeLen = timeLen;
	}

	public String getJiabanLen() {
		return jiabanLen;
	}

	public void setJiabanLen(String jiabanLen) {
		this.jiabanLen = jiabanLen;
	}
}