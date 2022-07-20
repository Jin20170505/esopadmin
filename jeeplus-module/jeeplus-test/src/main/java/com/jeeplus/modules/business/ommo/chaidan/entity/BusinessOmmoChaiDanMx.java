/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.ommo.chaidan.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.base.cangku.entity.BaseCangKu;
import com.jeeplus.modules.base.huowei.entity.BaseHuoWei;

/**
 * 委外发料拆单明细Entity
 * @author Jin
 * @version 2022-07-18
 */
public class BusinessOmmoChaiDanMx extends DataEntity<BusinessOmmoChaiDanMx> {
	
	private static final long serialVersionUID = 1L;
	private BusinessOmmoChaiDan main;		// 主表ID 父类
	private String wwid;		// 委外订单ID
	private String wwhid;		// 委外明细ID
	private String wwbomid;		// 委外用料子件ID
	private Integer no;		// 行号
	private String cinvcode;		// 存货编码
	private String cinvname;		// 存货名称
	private String cinvstd;		// 规格型号
	private String batchno;		// 批号
	private Double num;		// 数量
	private String unit;		// 单位
	private BaseCangKu ck;		// 仓库
	private BaseHuoWei hw;		// 货位
	private Date requireddate;		// 需求日期
	
	public BusinessOmmoChaiDanMx() {
		super();
	}

	public BusinessOmmoChaiDanMx(String id){
		super(id);
	}

	public BusinessOmmoChaiDanMx(BusinessOmmoChaiDan main){
		this.main = main;
	}

	public BusinessOmmoChaiDan getMain() {
		return main;
	}

	public void setMain(BusinessOmmoChaiDan main) {
		this.main = main;
	}
	
	@ExcelField(title="委外订单ID", align=2, sort=8)
	public String getWwid() {
		return wwid;
	}

	public void setWwid(String wwid) {
		this.wwid = wwid;
	}
	
	@ExcelField(title="委外明细ID", align=2, sort=9)
	public String getWwhid() {
		return wwhid;
	}

	public void setWwhid(String wwhid) {
		this.wwhid = wwhid;
	}
	
	@ExcelField(title="委外用料子件ID", align=2, sort=10)
	public String getWwbomid() {
		return wwbomid;
	}

	public void setWwbomid(String wwbomid) {
		this.wwbomid = wwbomid;
	}
	
	@ExcelField(title="行号", align=2, sort=11)
	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}
	
	@ExcelField(title="存货编码", align=2, sort=12)
	public String getCinvcode() {
		return cinvcode;
	}

	public void setCinvcode(String cinvcode) {
		this.cinvcode = cinvcode;
	}
	
	@ExcelField(title="存货名称", align=2, sort=13)
	public String getCinvname() {
		return cinvname;
	}

	public void setCinvname(String cinvname) {
		this.cinvname = cinvname;
	}
	
	@ExcelField(title="规格型号", align=2, sort=14)
	public String getCinvstd() {
		return cinvstd;
	}

	public void setCinvstd(String cinvstd) {
		this.cinvstd = cinvstd;
	}
	
	@ExcelField(title="批号", align=2, sort=15)
	public String getBatchno() {
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}
	
	@ExcelField(title="数量", align=2, sort=16)
	public Double getNum() {
		return num;
	}

	public void setNum(Double num) {
		this.num = num;
	}
	
	@ExcelField(title="单位", align=2, sort=17)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BaseCangKu getCk() {
		if(ck==null){
			ck = new BaseCangKu();
		}
		return ck;
	}

	public BusinessOmmoChaiDanMx setCk(BaseCangKu ck) {
		this.ck = ck;
		return this;
	}

	public BaseHuoWei getHw() {
		if(hw==null){
			hw = new BaseHuoWei();
		}
		return hw;
	}

	public BusinessOmmoChaiDanMx setHw(BaseHuoWei hw) {
		this.hw = hw;
		return this;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	@ExcelField(title="需求日期", align=2, sort=20)
	public Date getRequireddate() {
		return requireddate;
	}

	public void setRequireddate(Date requireddate) {
		this.requireddate = requireddate;
	}
	
}