/**
 *
 */
package com.jeeplus.modules.business.jihuadingdan.entity;

import com.jeeplus.modules.base.route.entity.BaseRoteMain;
import com.jeeplus.modules.business.shengchan.dingdan.entity.BusinessShengChanDingDan;
import com.jeeplus.modules.sys.entity.Office;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 计划工单Entity
 * @author Jin
 */
public class BusinessJiHuaGongDan extends DataEntity<BusinessJiHuaGongDan> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 单号
	private BusinessShengChanDingDan dd;		// 生产订单号
	private String orderno;		// 生产订单行号
	private String batchno;		// 生产批号
	private String cinvcode;		// 存货编码
	private String cinvname;		// 存货名称
	private String cinvstd;		// 规格型号
	private String unit;		// 计量单位
	private BaseRoteMain route; // 工艺路线
	private String startdate;		// 开始日期
	private String enddate;		// 结束日期
	private Double scnum;		// 生产数量
	private Double gdnum;		// 工单数量
	private Double synum;		// 剩余数量
	private Office dept;		// 生产部门
	private String status;		// 状态
	private List<BusinessJiHuaGongDanMingXi> businessJiHuaGongDanMingXiList = Lists.newArrayList();		// 子表列表
	
	public BusinessJiHuaGongDan() {
		super();
	}

	public BusinessJiHuaGongDan(String id){
		super(id);
	}

	@ExcelField(title="单号", align=2, sort=7)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="生产订单号", fieldType=BusinessShengChanDingDan.class, value="dd.code", align=2, sort=8)
	public BusinessShengChanDingDan getDd() {
		return dd;
	}

	public void setDd(BusinessShengChanDingDan dd) {
		this.dd = dd;
	}
	
	@ExcelField(title="生产订单行号", align=2, sort=9)
	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getBatchno() {
		return batchno;
	}

	public BusinessJiHuaGongDan setBatchno(String batchno) {
		this.batchno = batchno;
		return this;
	}

	@ExcelField(title="存货编码", align=2, sort=10)
	public String getCinvcode() {
		return cinvcode;
	}

	public void setCinvcode(String cinvcode) {
		this.cinvcode = cinvcode;
	}
	
	@ExcelField(title="存货名称", align=2, sort=11)
	public String getCinvname() {
		return cinvname;
	}

	public void setCinvname(String cinvname) {
		this.cinvname = cinvname;
	}
	
	@ExcelField(title="规格型号", align=2, sort=12)
	public String getCinvstd() {
		return cinvstd;
	}

	public void setCinvstd(String cinvstd) {
		this.cinvstd = cinvstd;
	}
	
	@ExcelField(title="计量单位", align=2, sort=13)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BaseRoteMain getRoute() {
		return route;
	}

	public BusinessJiHuaGongDan setRoute(BaseRoteMain route) {
		this.route = route;
		return this;
	}

	@ExcelField(title="开始日期", align=2, sort=14)
	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	
	@ExcelField(title="结束日期", align=2, sort=15)
	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	
	@ExcelField(title="生产数量", align=2, sort=16)
	public Double getScnum() {
		return scnum;
	}

	public void setScnum(Double scnum) {
		this.scnum = scnum;
	}
	
	@ExcelField(title="工单数量", align=2, sort=17)
	public Double getGdnum() {
		return gdnum;
	}

	public void setGdnum(Double gdnum) {
		this.gdnum = gdnum;
	}
	
	@ExcelField(title="剩余数量", align=2, sort=18)
	public Double getSynum() {
		return synum;
	}

	public void setSynum(Double synum) {
		this.synum = synum;
	}
	
	@ExcelField(title="生产部门", fieldType=Office.class, value="dept.name", align=2, sort=19)
	public Office getDept() {
		return dept;
	}

	public void setDept(Office dept) {
		this.dept = dept;
	}
	
	@ExcelField(title="状态", align=2, sort=20)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public List<BusinessJiHuaGongDanMingXi> getBusinessJiHuaGongDanMingXiList() {
		return businessJiHuaGongDanMingXiList;
	}

	public void setBusinessJiHuaGongDanMingXiList(List<BusinessJiHuaGongDanMingXi> businessJiHuaGongDanMingXiList) {
		this.businessJiHuaGongDanMingXiList = businessJiHuaGongDanMingXiList;
	}
}