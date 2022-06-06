/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.shengchan.paichan.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 生产排产明细Entity
 * @author Jin
 * @version 2022-06-06
 */
public class BusinessShengChanPaiChaiMx extends DataEntity<BusinessShengChanPaiChaiMx> {
	
	private static final long serialVersionUID = 1L;
	private String sccode;		// 生产单号
	private Integer scline;		// 生产行号
	private Integer no;		// 行号
	private String cinvcode;		// 存货编码
	private String cinvname;		// 存货名称
	private String cinvstd;		// 规格型号
	private String guidao;		// 轨道
	private String time;		// 时间
	private String cengci;		// 层次
	private String sylz;		// 使用立柱
	private String num;		// 数量（只/板）
	private Double kezhong;		// 克重
	private Double weight;		// 重量
	private String memo;		// 备注
	private BusinessShengChanPaiChan p;		// 父键 父类
	
	public BusinessShengChanPaiChaiMx() {
		super();
	}

	public BusinessShengChanPaiChaiMx(String id){
		super(id);
	}

	public BusinessShengChanPaiChaiMx(BusinessShengChanPaiChan p){
		this.p = p;
	}

	@ExcelField(title="生产单号", align=2, sort=7)
	public String getSccode() {
		return sccode;
	}

	public void setSccode(String sccode) {
		this.sccode = sccode;
	}
	
	@ExcelField(title="生产行号", align=2, sort=8)
	public Integer getScline() {
		return scline;
	}

	public void setScline(Integer scline) {
		this.scline = scline;
	}
	
	@ExcelField(title="行号", align=2, sort=9)
	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
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
	
	@ExcelField(title="轨道", align=2, sort=13)
	public String getGuidao() {
		return guidao;
	}

	public void setGuidao(String guidao) {
		this.guidao = guidao;
	}
	
	@ExcelField(title="时间", align=2, sort=14)
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	@ExcelField(title="层次", align=2, sort=15)
	public String getCengci() {
		return cengci;
	}

	public void setCengci(String cengci) {
		this.cengci = cengci;
	}
	
	@ExcelField(title="使用立柱", align=2, sort=16)
	public String getSylz() {
		return sylz;
	}

	public void setSylz(String sylz) {
		this.sylz = sylz;
	}
	
	@ExcelField(title="数量（只/板）", align=2, sort=17)
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
	@ExcelField(title="克重", align=2, sort=18)
	public Double getKezhong() {
		return kezhong;
	}

	public void setKezhong(Double kezhong) {
		this.kezhong = kezhong;
	}
	
	@ExcelField(title="重量", align=2, sort=19)
	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}
	
	@ExcelField(title="备注", align=2, sort=20)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public BusinessShengChanPaiChan getP() {
		return p;
	}

	public void setP(BusinessShengChanPaiChan p) {
		this.p = p;
	}
	
}