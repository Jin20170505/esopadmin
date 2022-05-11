/**
 *
 */
package com.jeeplus.modules.business.baogong.record.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 员工报工Entity
 * @author Jin
 */
public class BusinessBaoGongRecord extends DataEntity<BusinessBaoGongRecord> {
	
	private static final long serialVersionUID = 1L;
	private Date bgdate;		// 报工时间
	private String bgid;	// 报工ID
	private String bgcode;		// 报工单号
	private String planid;	// 计划工单ID
	private String ordercode;		// 生产订单号
	private String lineid;	// 生产订单行ID
	private String orderline;		// 生产订单行号
	private String bghid;	// 报工行ID
	private String site;		// 工序名称
	private String username;		// 姓名
	private Double gdnum;		// 工单数量
	private Double doingnum;		// 待报工数量
	private Double lfnum;		// 料废数量
	private Double gfnum;		// 工废数量
	private Double fgnum;		// 返工数量
	private Double bhgnum;		// 不合格数量
	private Double hgnum;		// 合格数量
	private String unit;	// 计量单位
	private Date beginBgdate;		// 开始 报工时间
	private Date endBgdate;		// 结束 报工时间
	
	public BusinessBaoGongRecord() {
		super();
	}

	public BusinessBaoGongRecord(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="报工时间", align=2, sort=6)
	public Date getBgdate() {
		return bgdate;
	}

	public void setBgdate(Date bgdate) {
		this.bgdate = bgdate;
	}
	
	@ExcelField(title="报工单号", align=2, sort=7)
	public String getBgcode() {
		return bgcode;
	}

	public void setBgcode(String bgcode) {
		this.bgcode = bgcode;
	}
	
	@ExcelField(title="生产订单号", align=2, sort=8)
	public String getOrdercode() {
		return ordercode;
	}

	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}
	
	@ExcelField(title="生产订单行号", align=2, sort=9)
	public String getOrderline() {
		return orderline;
	}

	public void setOrderline(String orderline) {
		this.orderline = orderline;
	}
	
	@ExcelField(title="工序名称", align=2, sort=10)
	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}
	
	@ExcelField(title="姓名", align=2, sort=11)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@ExcelField(title="工单数量", align=2, sort=12)
	public Double getGdnum() {
		return gdnum;
	}

	public void setGdnum(Double gdnum) {
		this.gdnum = gdnum;
	}
	
	@ExcelField(title="待报工数量", align=2, sort=13)
	public Double getDoingnum() {
		return doingnum;
	}

	public void setDoingnum(Double doingnum) {
		this.doingnum = doingnum;
	}
	
	@ExcelField(title="料废数量", align=2, sort=14)
	public Double getLfnum() {
		return lfnum;
	}

	public void setLfnum(Double lfnum) {
		this.lfnum = lfnum;
	}
	
	@ExcelField(title="工废数量", align=2, sort=15)
	public Double getGfnum() {
		return gfnum;
	}

	public void setGfnum(Double gfnum) {
		this.gfnum = gfnum;
	}
	
	@ExcelField(title="返工数量", align=2, sort=16)
	public Double getFgnum() {
		return fgnum;
	}

	public void setFgnum(Double fgnum) {
		this.fgnum = fgnum;
	}
	
	@ExcelField(title="不合格数量", align=2, sort=17)
	public Double getBhgnum() {
		return bhgnum;
	}

	public void setBhgnum(Double bhgnum) {
		this.bhgnum = bhgnum;
	}
	
	@ExcelField(title="合格数量", align=2, sort=18)
	public Double getHgnum() {
		return hgnum;
	}

	public void setHgnum(Double hgnum) {
		this.hgnum = hgnum;
	}
	
	public Date getBeginBgdate() {
		return beginBgdate;
	}

	public void setBeginBgdate(Date beginBgdate) {
		this.beginBgdate = beginBgdate;
	}
	
	public Date getEndBgdate() {
		return endBgdate;
	}

	public void setEndBgdate(Date endBgdate) {
		this.endBgdate = endBgdate;
	}

	public String getBgid() {
		return bgid;
	}

	public BusinessBaoGongRecord setBgid(String bgid) {
		this.bgid = bgid;
		return this;
	}

	public String getPlanid() {
		return planid;
	}

	public BusinessBaoGongRecord setPlanid(String planid) {
		this.planid = planid;
		return this;
	}

	public String getLineid() {
		return lineid;
	}

	public BusinessBaoGongRecord setLineid(String lineid) {
		this.lineid = lineid;
		return this;
	}

	public String getBghid() {
		return bghid;
	}

	public BusinessBaoGongRecord setBghid(String bghid) {
		this.bghid = bghid;
		return this;
	}
	@ExcelField(title="计量单位", align=2, sort=28)
	public String getUnit() {
		return unit;
	}

	public BusinessBaoGongRecord setUnit(String unit) {
		this.unit = unit;
		return this;
	}
}