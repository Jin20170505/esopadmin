/**
 *
 */
package com.jeeplus.modules.business.shengchan.beiliao.apply.entity;

import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.sys.entity.Office;

/**
 * 生产备料Entity
 * @author Jin
 */
public class BusinessShengChanBeiLiaoApply extends DataEntity<BusinessShengChanBeiLiaoApply> {
	
	private static final long serialVersionUID = 1L;
	private String sccode;		// 生产编码
	private String scline;		// 生产行号
	private Office dept;		// 生产部门
	private String cinvcode;		// 产品编码
	private String cinvname;		// 产品名称
	private String cinvstd;		// 规格型号
	private Double num;		// 数量
	private String unit;		// 单位
	private String scid;		// 生产ID
	private String schid;		// 生产行ID
	private String startdate;		// 开工日期
	private String enddate;		// 完工日期
	private String batchno; // 批号
	private String printstatus;// 打印状态
	private List<BusinessShengchanBeiliaoApplyMx> businessShengchanBeiliaoApplyMxList = Lists.newArrayList();		// 子表列表
	
	public BusinessShengChanBeiLiaoApply() {
		super();
	}

	public BusinessShengChanBeiLiaoApply(String id){
		super(id);
	}

	public String getPrintstatus() {
		return printstatus;
	}

	public BusinessShengChanBeiLiaoApply setPrintstatus(String printstatus) {
		this.printstatus = printstatus;
		return this;
	}

	@ExcelField(title="生产编码", align=2, sort=7)
	public String getSccode() {
		return sccode;
	}

	public void setSccode(String sccode) {
		this.sccode = sccode;
	}
	
	@ExcelField(title="生产行号", align=2, sort=8)
	public String getScline() {
		return scline;
	}

	public void setScline(String scline) {
		this.scline = scline;
	}
	
	@ExcelField(title="生产部门", fieldType=String.class, value="dept.name", align=2, sort=9)
	public Office getDept() {
		return dept;
	}

	public void setDept(Office dept) {
		this.dept = dept;
	}
	
	@ExcelField(title="产品编码", align=2, sort=10)
	public String getCinvcode() {
		return cinvcode;
	}

	public void setCinvcode(String cinvcode) {
		this.cinvcode = cinvcode;
	}
	
	@ExcelField(title="产品名称", align=2, sort=11)
	public String getCinvname() {
		return cinvname;
	}

	public void setCinvname(String cinvname) {
		this.cinvname = cinvname;
	}
	
	@ExcelField(title="规格型号", align=2, sort=12)
	public String getCinvstd() {
		if(cinvstd==null){
			cinvstd = "";
		}
		return cinvstd;
	}

	public void setCinvstd(String cinvstd) {
		this.cinvstd = cinvstd;
	}
	
	@ExcelField(title="数量", align=2, sort=13)
	public Double getNum() {
		return num;
	}

	public void setNum(Double num) {
		this.num = num;
	}
	
	@ExcelField(title="单位", align=2, sort=14)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@ExcelField(title="生产ID", align=2, sort=15)
	public String getScid() {
		return scid;
	}

	public void setScid(String scid) {
		this.scid = scid;
	}
	
	@ExcelField(title="生产行ID", align=2, sort=16)
	public String getSchid() {
		return schid;
	}

	public void setSchid(String schid) {
		this.schid = schid;
	}
	
	public List<BusinessShengchanBeiliaoApplyMx> getBusinessShengchanBeiliaoApplyMxList() {
		return businessShengchanBeiliaoApplyMxList;
	}

	public void setBusinessShengchanBeiliaoApplyMxList(List<BusinessShengchanBeiliaoApplyMx> businessShengchanBeiliaoApplyMxList) {
		this.businessShengchanBeiliaoApplyMxList = businessShengchanBeiliaoApplyMxList;
	}

	public String getStartdate() {
		return startdate;
	}

	public BusinessShengChanBeiLiaoApply setStartdate(String startdate) {
		this.startdate = startdate;
		return this;
	}

	public String getEnddate() {
		return enddate;
	}

	public BusinessShengChanBeiLiaoApply setEnddate(String enddate) {
		this.enddate = enddate;
		return this;
	}

	public String getBatchno() {
		return batchno;
	}

	public BusinessShengChanBeiLiaoApply setBatchno(String batchno) {
		this.batchno = batchno;
		return this;
	}
}