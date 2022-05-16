/**
 *
 */
package com.jeeplus.modules.business.shengchan.beiliao.entity;

import com.jeeplus.modules.sys.entity.Office;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 生产备料Entity
 * @author Jin
 */
public class BusinessShengChanBeiLiao extends DataEntity<BusinessShengChanBeiLiao> {
	
	private static final long serialVersionUID = 1L;
	private String sccode;		// 生产单号
	private String scline;		// 生产行号
	private String cinvcode;		// 产品编码
	private String cinvname;		// 产品名称
	private String cinvstd;		// 产品规格
	private Double num;		// 数量
	private String unit;		// 单位
	private Office dept;		// 生产部门
	private String schid;		// 生产行ID
	private String scid;		// 生产ID
	private String u8code;		// U8编号
	private List<BusinessShengChanBeiLiaoMx> businessShengChanBeiLiaoMxList = Lists.newArrayList();		// 子表列表
	
	public BusinessShengChanBeiLiao() {
		super();
	}

	public BusinessShengChanBeiLiao(String id){
		super(id);
	}

	@ExcelField(title="生产单号", align=2, sort=7)
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
	
	@ExcelField(title="产品编码", align=2, sort=9)
	public String getCinvcode() {
		return cinvcode;
	}

	public void setCinvcode(String cinvcode) {
		this.cinvcode = cinvcode;
	}
	
	@ExcelField(title="产品名称", align=2, sort=10)
	public String getCinvname() {
		return cinvname;
	}

	public void setCinvname(String cinvname) {
		this.cinvname = cinvname;
	}
	
	@ExcelField(title="产品规格", align=2, sort=11)
	public String getCinvstd() {
		return cinvstd;
	}

	public void setCinvstd(String cinvstd) {
		this.cinvstd = cinvstd;
	}
	
	@ExcelField(title="数量", align=2, sort=12)
	public Double getNum() {
		return num;
	}

	public void setNum(Double num) {
		this.num = num;
	}
	
	@ExcelField(title="单位", align=2, sort=13)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@ExcelField(title="生产部门", fieldType=Office.class, value="dept.name", align=2, sort=14)
	public Office getDept() {
		return dept;
	}

	public void setDept(Office dept) {
		this.dept = dept;
	}
	
	@ExcelField(title="生产行ID", align=2, sort=15)
	public String getSchid() {
		return schid;
	}

	public void setSchid(String schid) {
		this.schid = schid;
	}
	
	@ExcelField(title="生产ID", align=2, sort=16)
	public String getScid() {
		return scid;
	}

	public void setScid(String scid) {
		this.scid = scid;
	}
	
	@ExcelField(title="U8编号", align=2, sort=17)
	public String getU8code() {
		return u8code;
	}

	public void setU8code(String u8code) {
		this.u8code = u8code;
	}
	
	public List<BusinessShengChanBeiLiaoMx> getBusinessShengChanBeiLiaoMxList() {
		return businessShengChanBeiLiaoMxList;
	}

	public void setBusinessShengChanBeiLiaoMxList(List<BusinessShengChanBeiLiaoMx> businessShengChanBeiLiaoMxList) {
		this.businessShengChanBeiLiaoMxList = businessShengChanBeiLiaoMxList;
	}
}