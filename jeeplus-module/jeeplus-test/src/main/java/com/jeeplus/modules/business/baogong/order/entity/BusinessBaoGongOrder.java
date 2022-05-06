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
	private String ordercode;		// 生产订单号
	private String orderline;		// 行号
	private String plancode;		// 计划单号
	private String dept;		// 生产部门
	private String cinvcode;		// 存货编码
	private String cinvname;		// 存货名称
	private String cinvstd;		// 规格型号
	private Double num;		// 数量
	private String start_date;		// 开始日期
	private String end_date;		// 结束日期
	private String end_date;		// 报工单号
	private String complate;		// 是否完成
	private List<BusinessBaoGongOrderMingXi> businessBaoGongOrderMingXiList = Lists.newArrayList();		// 子表列表
	
	public BusinessBaoGongOrder() {
		super();
	}

	public BusinessBaoGongOrder(String id){
		super(id);
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
	
	@ExcelField(title="开始日期", align=2, sort=14)
	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	
	@ExcelField(title="结束日期", align=2, sort=15)
	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	
	@ExcelField(title="报工单号", align=2, sort=16)
	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
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
}