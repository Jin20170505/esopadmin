/**
 *
 */
package com.jeeplus.modules.business.ommo.bom.entity;

import java.util.Date;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 委外用料Entity
 * @author Jin
 */
public class BussinessOmMoDetailOnly extends DataEntity<BussinessOmMoDetailOnly> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 委外单号
	private Integer no;		// 委外明细行号
	private String cinvcode;		// 存货编码
	private String cinvname;		// 存货名称
	private String cinvstd;		// 规格型号
	private String num;
	private String vendor;
	private Date ddate;		// 订单日期
	private String qrcode;

	public String getQrcode() {
		qrcode  = code+";"+no;
		return qrcode;
	}

	private String ddrq;

	public String getDdrq() {
		if(ddate==null){
			ddrq="";
		}else {
			ddrq  = DateUtils.formatDate(ddate,"yyyy-MM-dd");
		}
		return ddrq;
	}

	public BussinessOmMoDetailOnly setDdrq(String ddrq) {
		this.ddrq = ddrq;
		return this;
	}

	private String memo;		// 备注
	private List<BussinessOmMoYongItem> bussinessOmMoYongItemList = Lists.newArrayList();		// 子表列表
	
	public BussinessOmMoDetailOnly() {
		super();
	}

	public BussinessOmMoDetailOnly(String id){
		super(id);
	}

	@ExcelField(title="委外单号", align=2, sort=7)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="委外明细行号", align=2, sort=8)
	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}
	
	@ExcelField(title="存货编码", align=2, sort=9)
	public String getCinvcode() {
		return cinvcode;
	}

	public void setCinvcode(String cinvcode) {
		this.cinvcode = cinvcode;
	}
	
	@ExcelField(title="存货名称", align=2, sort=10)
	public String getCinvname() {
		return cinvname;
	}

	public void setCinvname(String cinvname) {
		this.cinvname = cinvname;
	}
	
	@ExcelField(title="规格型号", align=2, sort=11)
	public String getCinvstd() {
		return cinvstd;
	}

	public void setCinvstd(String cinvstd) {
		this.cinvstd = cinvstd;
	}
	
	@ExcelField(title="备注", align=2, sort=12)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public List<BussinessOmMoYongItem> getBussinessOmMoYongItemList() {
		return bussinessOmMoYongItemList;
	}

	public void setBussinessOmMoYongItemList(List<BussinessOmMoYongItem> bussinessOmMoYongItemList) {
		this.bussinessOmMoYongItemList = bussinessOmMoYongItemList;
	}

	public String getNum() {
		return num;
	}

	public BussinessOmMoDetailOnly setNum(String num) {
		this.num = num;
		return this;
	}

	public String getVendor() {
		return vendor;
	}

	public BussinessOmMoDetailOnly setVendor(String vendor) {
		this.vendor = vendor;
		return this;
	}

	public Date getDdate() {
		return ddate;
	}

	public BussinessOmMoDetailOnly setDdate(Date ddate) {
		this.ddate = ddate;
		return this;
	}
}