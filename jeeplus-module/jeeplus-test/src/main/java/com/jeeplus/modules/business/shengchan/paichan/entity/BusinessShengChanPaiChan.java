/**
 *
 */
package com.jeeplus.modules.business.shengchan.paichan.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import com.jeeplus.modules.sys.entity.Office;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 生产排产Entity
 * @author Jin
 */
public class BusinessShengChanPaiChan extends DataEntity<BusinessShengChanPaiChan> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 单号
	private Date pdate;		// 排产日期
	private Office dept;		// 部门
	private Double sumweight;		// 总重量
	private Date beginPdate;		// 开始 排产日期
	private Date endPdate;		// 结束 排产日期
	private List<BusinessShengChanPaiChaiMx> businessShengChanPaiChaiMxList = Lists.newArrayList();		// 子表列表
	
	public BusinessShengChanPaiChan() {
		super();
	}

	public BusinessShengChanPaiChan(String id){
		super(id);
	}

	@ExcelField(title="单号", align=2, sort=7)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message="排产日期不能为空")
	@ExcelField(title="排产日期", align=2, sort=8)
	public Date getPdate() {
		return pdate;
	}

	public void setPdate(Date pdate) {
		this.pdate = pdate;
	}
	
	@NotNull(message="部门不能为空")
	@ExcelField(title="部门", fieldType=Office.class, value="dept.name", align=2, sort=9)
	public Office getDept() {
		return dept;
	}

	public void setDept(Office dept) {
		this.dept = dept;
	}
	
	@NotNull(message="总重量不能为空")
	@ExcelField(title="总重量", align=2, sort=10)
	public Double getSumweight() {
		return sumweight;
	}

	public void setSumweight(Double sumweight) {
		this.sumweight = sumweight;
	}
	
	public Date getBeginPdate() {
		return beginPdate;
	}

	public void setBeginPdate(Date beginPdate) {
		this.beginPdate = beginPdate;
	}
	
	public Date getEndPdate() {
		return endPdate;
	}

	public void setEndPdate(Date endPdate) {
		this.endPdate = endPdate;
	}
		
	public List<BusinessShengChanPaiChaiMx> getBusinessShengChanPaiChaiMxList() {
		return businessShengChanPaiChaiMxList;
	}

	public void setBusinessShengChanPaiChaiMxList(List<BusinessShengChanPaiChaiMx> businessShengChanPaiChaiMxList) {
		this.businessShengChanPaiChaiMxList = businessShengChanPaiChaiMxList;
	}
}