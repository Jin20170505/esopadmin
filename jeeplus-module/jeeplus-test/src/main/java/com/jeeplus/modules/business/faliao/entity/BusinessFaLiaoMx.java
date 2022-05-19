/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.faliao.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.base.huowei.entity.BaseHuoWei;

/**
 * 调拨明细Entity
 * @author Jin
 * @version 2022-05-19
 */
public class BusinessFaLiaoMx extends DataEntity<BusinessFaLiaoMx> {
	
	private static final long serialVersionUID = 1L;
	private Integer no;		// 序号
	private String cinvcode;		// 存货编码
	private String cinvname;		// 存货名称
	private String cinvstd;		// 型号规格
	private String batchno;		// 批号
	private String scdate;		// 生产日期
	private Double num;		// 数量
	private String unit;		// 单位
	private BaseHuoWei huowei; // 货位
	private BusinessFaLiao pid;		// 父键 父类
	
	public BusinessFaLiaoMx() {
		super();
	}

	public BusinessFaLiaoMx(String id){
		super(id);
	}

	public BusinessFaLiaoMx(BusinessFaLiao pid){
		this.pid = pid;
	}

	@ExcelField(title="序号", align=2, sort=7)
	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}
	
	@ExcelField(title="存货编码", align=2, sort=8)
	public String getCinvcode() {
		return cinvcode;
	}

	public void setCinvcode(String cinvcode) {
		this.cinvcode = cinvcode;
	}
	
	@ExcelField(title="存货名称", align=2, sort=9)
	public String getCinvname() {
		return cinvname;
	}

	public void setCinvname(String cinvname) {
		this.cinvname = cinvname;
	}
	
	@ExcelField(title="型号规格", align=2, sort=10)
	public String getCinvstd() {
		return cinvstd;
	}

	public void setCinvstd(String cinvstd) {
		this.cinvstd = cinvstd;
	}
	
	@ExcelField(title="批号", align=2, sort=11)
	public String getBatchno() {
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}
	
	@ExcelField(title="生产日期", align=2, sort=12)
	public String getScdate() {
		return scdate;
	}

	public void setScdate(String scdate) {
		this.scdate = scdate;
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
	
	public BusinessFaLiao getPid() {
		return pid;
	}

	public void setPid(BusinessFaLiao pid) {
		this.pid = pid;
	}

	public BaseHuoWei getHuowei() {
		return huowei;
	}

	public BusinessFaLiaoMx setHuowei(BaseHuoWei huowei) {
		this.huowei = huowei;
		return this;
	}
}