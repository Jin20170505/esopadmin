/**
 *
 */
package com.jeeplus.modules.business.baogong.order.entity;

import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 报工单Entity
 * @author Jin
 */
public class BusinessBaoGongOrder extends DataEntity<BusinessBaoGongOrder> {
	
	private static final long serialVersionUID = 1L;
	private String orderlineid;		// 生成订单行ID
	private String ordercode;		// 生产订单号
	private String batchno;		// 批号
	private String orderline;		// 行号
	private String plancode;		// 计划单号
	private String planid;	// 计划ID
	private String dept;		// 生产部门ID
	private String deptName;	// 生产部门名称
	private String cinvcode;		// 存货编码
	private String cinvname;		// 存货名称
	private String cinvstd;		// 规格型号
	private Double num;		// 数量
	private String unit;	// 计量单位
	private String startdate;		// 开始日期
	private String enddate;		// 结束日期
	private String bgcode;		// 报工单号
	private String yaocode; // 窑炉号
	private String isprint; // 是否打印
	private String complate;		// 是否完成
	private String qrcode;		// 二维码内容
	private List<BusinessBaoGongOrderMingXi> businessBaoGongOrderMingXiList = Lists.newArrayList();		// 子表列表
	
	public BusinessBaoGongOrder() {
		super();
	}

	public BusinessBaoGongOrder(String id){
		super(id);
	}

	public String getIsprint() {
		return isprint;
	}

	public BusinessBaoGongOrder setIsprint(String isprint) {
		this.isprint = isprint;
		return this;
	}

	@ExcelField(title="生产订单号", align=2, sort=6)
	public String getOrdercode() {
		return ordercode;
	}

	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}
	
	@ExcelField(title="行号", align=2, sort=7)
	public String getOrderline() {
		return orderline;
	}

	public void setOrderline(String orderline) {
		this.orderline = orderline;
	}
	
	@ExcelField(title="计划单号", align=2, sort=8)
	public String getPlancode() {
		return plancode;
	}

	public void setPlancode(String plancode) {
		this.plancode = plancode;
	}
	
	@ExcelField(title="生产部门", align=2, sort=9)
	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
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
	
	@ExcelField(title="数量", align=2, sort=13)
	public Double getNum() {
		return num;
	}

	public void setNum(Double num) {
		this.num = num;
	}

	public String getStartdate() {
		return startdate;
	}

	public BusinessBaoGongOrder setStartdate(String startdate) {
		this.startdate = startdate;
		return this;
	}

	public String getEnddate() {
		return enddate;
	}

	public BusinessBaoGongOrder setEnddate(String enddate) {
		this.enddate = enddate;
		return this;
	}

	public String getQrcode() {
		return qrcode;
	}

	public BusinessBaoGongOrder setQrcode(String qrcode) {
		this.qrcode = qrcode;
		return this;
	}

	public String getYaocode() {
		return yaocode;
	}

	public BusinessBaoGongOrder setYaocode(String yaocode) {
		this.yaocode = yaocode;
		return this;
	}

	@ExcelField(title="报工单号", align=2, sort=16)
	public String getBgcode() {
		return bgcode;
	}

	public void setBgcode(String bgcode) {
		this.bgcode = bgcode;
	}
	
	@ExcelField(title="是否完成", align=2, sort=17)
	public String getComplate() {
		return complate;
	}

	public void setComplate(String complate) {
		this.complate = complate;
	}
	
	public List<BusinessBaoGongOrderMingXi> getBusinessBaoGongOrderMingXiList() {
		return businessBaoGongOrderMingXiList;
	}

	public void setBusinessBaoGongOrderMingXiList(List<BusinessBaoGongOrderMingXi> businessBaoGongOrderMingXiList) {
		this.businessBaoGongOrderMingXiList = businessBaoGongOrderMingXiList;
	}

	public String getOrderlineid() {
		return orderlineid;
	}

	public BusinessBaoGongOrder setOrderlineid(String orderlineid) {
		this.orderlineid = orderlineid;
		return this;
	}

	public String getBatchno() {
		return batchno;
	}

	public BusinessBaoGongOrder setBatchno(String batchno) {
		this.batchno = batchno;
		return this;
	}

	public String getPlanid() {
		return planid;
	}

	public BusinessBaoGongOrder setPlanid(String planid) {
		this.planid = planid;
		return this;
	}

	public String getDeptName() {
		return deptName;
	}

	public BusinessBaoGongOrder setDeptName(String deptName) {
		this.deptName = deptName;
		return this;
	}

	public String getUnit() {
		return unit;
	}

	public BusinessBaoGongOrder setUnit(String unit) {
		this.unit = unit;
		return this;
	}
}