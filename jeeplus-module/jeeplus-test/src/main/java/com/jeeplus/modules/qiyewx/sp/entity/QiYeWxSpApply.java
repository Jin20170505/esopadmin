/**
 *
 */
package com.jeeplus.modules.qiyewx.sp.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 审批申请详情Entity
 * @author Jin
 * @version 2021-08-31
 */
public class QiYeWxSpApply extends DataEntity<QiYeWxSpApply> {
	
	private static final long serialVersionUID = 1L;
	private QiYeWxSp sp;		// 申请审批 父类
	private String control;		// 控件类型
	private String controlid;		// 控件id
	private String title;		// 控件名称
	private String valueJson;		// 控件值
	private String valueText;		// 控件值-文本
	private String vacationSelector;		// 请假类型
	private String vacationAttendanceType;		// 假勤分类
	private String vacationAttendanceDateRangeType;		// 时间展示类型
	private Date vacationAttendanceDateRangeBegin;		// 开始时间
	private Date vacationAttendanceDateRangeEnd;		// 结束时间
	private Long vacationAttendanceDateRangeDuration;		// 时长范围
	private String valueMembers;		// 人员
	private String valueDepartments;		// 部门
	private String valueFiles;		// 文件id
	private String punchCorrectionState;		// 补卡状态
	private Date punchCorrectionTime;		// 补卡时间
	
	public QiYeWxSpApply() {
		super();
	}

	public QiYeWxSpApply(String id){
		super(id);
	}

	public QiYeWxSpApply(QiYeWxSp sp){
		this.sp = sp;
	}

	public QiYeWxSp getSp() {
		return sp;
	}

	public void setSp(QiYeWxSp sp) {
		this.sp = sp;
	}
	
	@ExcelField(title="控件类型", align=2, sort=8)
	public String getControl() {
		return control;
	}

	public void setControl(String control) {
		this.control = control;
	}
	
	@ExcelField(title="控件id", align=2, sort=9)
	public String getControlid() {
		return controlid;
	}

	public void setControlid(String controlid) {
		this.controlid = controlid;
	}
	
	@ExcelField(title="控件名称", align=2, sort=10)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="控件值", align=2, sort=11)
	public String getValueJson() {
		return valueJson;
	}

	public void setValueJson(String valueJson) {
		this.valueJson = valueJson;
	}
	
	@ExcelField(title="控件值-文本", align=2, sort=12)
	public String getValueText() {
		return valueText;
	}

	public void setValueText(String valueText) {
		this.valueText = valueText;
	}
	
	@ExcelField(title="请假类型", align=2, sort=13)
	public String getVacationSelector() {
		return vacationSelector;
	}

	public void setVacationSelector(String vacationSelector) {
		this.vacationSelector = vacationSelector;
	}
	
	@ExcelField(title="假勤分类", align=2, sort=14)
	public String getVacationAttendanceType() {
		return vacationAttendanceType;
	}

	public void setVacationAttendanceType(String vacationAttendanceType) {
		this.vacationAttendanceType = vacationAttendanceType;
	}
	
	@ExcelField(title="时间展示类型", align=2, sort=15)
	public String getVacationAttendanceDateRangeType() {
		return vacationAttendanceDateRangeType;
	}

	public void setVacationAttendanceDateRangeType(String vacationAttendanceDateRangeType) {
		this.vacationAttendanceDateRangeType = vacationAttendanceDateRangeType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="开始时间", align=2, sort=16)
	public Date getVacationAttendanceDateRangeBegin() {
		return vacationAttendanceDateRangeBegin;
	}

	public void setVacationAttendanceDateRangeBegin(Date vacationAttendanceDateRangeBegin) {
		this.vacationAttendanceDateRangeBegin = vacationAttendanceDateRangeBegin;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="结束时间", align=2, sort=17)
	public Date getVacationAttendanceDateRangeEnd() {
		return vacationAttendanceDateRangeEnd;
	}

	public void setVacationAttendanceDateRangeEnd(Date vacationAttendanceDateRangeEnd) {
		this.vacationAttendanceDateRangeEnd = vacationAttendanceDateRangeEnd;
	}
	
	@ExcelField(title="时长范围", align=2, sort=18)
	public Long getVacationAttendanceDateRangeDuration() {
		return vacationAttendanceDateRangeDuration;
	}

	public void setVacationAttendanceDateRangeDuration(Long vacationAttendanceDateRangeDuration) {
		this.vacationAttendanceDateRangeDuration = vacationAttendanceDateRangeDuration;
	}
	
	@ExcelField(title="人员", align=2, sort=19)
	public String getValueMembers() {
		return valueMembers;
	}

	public void setValueMembers(String valueMembers) {
		this.valueMembers = valueMembers;
	}
	
	@ExcelField(title="部门", align=2, sort=20)
	public String getValueDepartments() {
		return valueDepartments;
	}

	public void setValueDepartments(String valueDepartments) {
		this.valueDepartments = valueDepartments;
	}
	
	@ExcelField(title="文件id", align=2, sort=21)
	public String getValueFiles() {
		return valueFiles;
	}

	public void setValueFiles(String valueFiles) {
		this.valueFiles = valueFiles;
	}
	
	@ExcelField(title="补卡状态", align=2, sort=22)
	public String getPunchCorrectionState() {
		return punchCorrectionState;
	}

	public void setPunchCorrectionState(String punchCorrectionState) {
		this.punchCorrectionState = punchCorrectionState;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="补卡时间", align=2, sort=23)
	public Date getPunchCorrectionTime() {
		return punchCorrectionTime;
	}

	public void setPunchCorrectionTime(Date punchCorrectionTime) {
		this.punchCorrectionTime = punchCorrectionTime;
	}
	
}