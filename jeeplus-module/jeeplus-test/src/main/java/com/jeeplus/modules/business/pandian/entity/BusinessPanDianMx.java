/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.pandian.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 盘点明细Entity
 * @author Jin
 * @version 2022-05-29
 */
public class BusinessPanDianMx extends DataEntity<BusinessPanDianMx> {
	
	private static final long serialVersionUID = 1L;
	private BusinessPanDian p;		// 父键ID 父类
	private Integer no;		// 序号
	private String cinvcode;		// 存货编码
	private String cinvname;		// 存货名称
	private String cinvstd;		// 规格型号
	private String scdate;		// 生产日期
	private String batchno;		// 批号
	private Double num;		// 现存数量
	private String unit;		// 单位
	private String hwcode;		// 货位编码
	private String ckcode;		// 仓库编码
	private Double pannum;		// 实盘数量
	private Double cha;		// 差值
	
	public BusinessPanDianMx() {
		super();
	}

	public BusinessPanDianMx(String id){
		super(id);
	}

	public BusinessPanDianMx(BusinessPanDian p){
		this.p = p;
	}

	public BusinessPanDian getP() {
		return p;
	}

	public void setP(BusinessPanDian p) {
		this.p = p;
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
	
	@ExcelField(title="规格型号", align=2, sort=10)
	public String getCinvstd() {
		return cinvstd;
	}

	public void setCinvstd(String cinvstd) {
		this.cinvstd = cinvstd;
	}
	
	@ExcelField(title="生产日期", align=2, sort=11)
	public String getScdate() {
		return scdate;
	}

	public void setScdate(String scdate) {
		this.scdate = scdate;
	}
	
	@ExcelField(title="批号", align=2, sort=12)
	public String getBatchno() {
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}
	
	@ExcelField(title="现存数量", align=2, sort=13)
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
	
	@ExcelField(title="货位编码", align=2, sort=15)
	public String getHwcode() {
		return hwcode;
	}

	public void setHwcode(String hwcode) {
		this.hwcode = hwcode;
	}
	
	@ExcelField(title="仓库编码", align=2, sort=16)
	public String getCkcode() {
		return ckcode;
	}

	public void setCkcode(String ckcode) {
		this.ckcode = ckcode;
	}
	
	@ExcelField(title="实盘数量", align=2, sort=18)
	public Double getPannum() {
		return pannum;
	}

	public void setPannum(Double pannum) {
		this.pannum = pannum;
	}
	
	@ExcelField(title="差值", align=2, sort=19)
	public Double getCha() {
		return cha;
	}

	public void setCha(Double cha) {
		this.cha = cha;
	}
	
}