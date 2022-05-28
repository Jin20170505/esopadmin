/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.ommo.bom.entity;

import javax.validation.constraints.NotNull;
import com.jeeplus.modules.base.cangku.entity.BaseCangKu;
import com.jeeplus.modules.base.huowei.entity.BaseHuoWei;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 委外用料明细Entity
 * @author Jin
 * @version 2022-05-28
 */
public class BussinessOmMoYongItem extends DataEntity<BussinessOmMoYongItem> {
	
	private static final long serialVersionUID = 1L;
	private BussinessOmMoDetailOnly ommo;		// 委外行ID 父类
	private Integer no;		// 行号
	private BaseCangKu ck;		// 仓库
	private BaseHuoWei hw;		// 货位
	private String cinvcode;		// 存货编码
	private String cinvname;		// 存货名称
	private String cinvstd;		// 规格型号
	private String batchno;		// 批号
	private Double num;		// 数量
	private String unit;		// 单位
	private Date requireddate;		// 需求日期
	private String wwid;	// 	委外订单ID
	private String qrcode;

	public String getQrcode() {
		qrcode  = no+";" +cinvcode;
		return qrcode;
	}
	public BussinessOmMoYongItem() {
		super();
	}

	public BussinessOmMoYongItem(String id){
		super(id);
	}

	public BussinessOmMoYongItem(BussinessOmMoDetailOnly ommo){
		this.ommo = ommo;
	}

	public BussinessOmMoDetailOnly getOmmo() {
		return ommo;
	}

	public void setOmmo(BussinessOmMoDetailOnly ommo) {
		this.ommo = ommo;
	}

	@NotNull(message="行号不能为空")
	@ExcelField(title="行号", align=2, sort=8)
	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}
	
	@NotNull(message="仓库不能为空")
	@ExcelField(title="仓库", fieldType=BaseCangKu.class, value="ck.name", align=2, sort=9)
	public BaseCangKu getCk() {
		return ck;
	}

	public void setCk(BaseCangKu ck) {
		this.ck = ck;
	}
	
	@ExcelField(title="货位", fieldType=BaseHuoWei.class, value="hw.name", align=2, sort=10)
	public BaseHuoWei getHw() {
		return hw;
	}

	public void setHw(BaseHuoWei hw) {
		this.hw = hw;
	}
	
	@ExcelField(title="存货编码", align=2, sort=11)
	public String getCinvcode() {
		return cinvcode;
	}

	public void setCinvcode(String cinvcode) {
		this.cinvcode = cinvcode;
	}
	
	@ExcelField(title="存货名称", align=2, sort=12)
	public String getCinvname() {
		return cinvname;
	}

	public void setCinvname(String cinvname) {
		this.cinvname = cinvname;
	}
	
	@ExcelField(title="规格型号", align=2, sort=13)
	public String getCinvstd() {
		if(cinvstd==null){
			cinvstd="";
		}
		return cinvstd;
	}

	public void setCinvstd(String cinvstd) {
		this.cinvstd = cinvstd;
	}
	
	@ExcelField(title="批号", align=2, sort=14)
	public String getBatchno() {
		if(batchno==null){
			batchno="";
		}
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}
	
	@NotNull(message="数量不能为空")
	@ExcelField(title="数量", align=2, sort=15)
	public Double getNum() {
		return num;
	}

	public void setNum(Double num) {
		this.num = num;
	}
	
	@ExcelField(title="单位", align=2, sort=16)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@ExcelField(title="需求日期", align=2, sort=17)
	public Date getRequireddate() {
		return requireddate;
	}

	public void setRequireddate(Date requireddate) {
		this.requireddate = requireddate;
	}

	public String getWwid() {
		return wwid;
	}

	public BussinessOmMoYongItem setWwid(String wwid) {
		this.wwid = wwid;
		return this;
	}
}