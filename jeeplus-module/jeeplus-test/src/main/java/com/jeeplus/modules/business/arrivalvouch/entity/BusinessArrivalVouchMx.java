/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.arrivalvouch.entity;

import javax.validation.constraints.NotNull;
import com.jeeplus.modules.base.cangku.entity.BaseCangKu;
import com.jeeplus.modules.base.huowei.entity.BaseHuoWei;
import com.jeeplus.modules.base.vendor.entity.BaseVendor;
import com.jeeplus.modules.sys.entity.Office;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 到货单明细Entity
 * @author Jin
 * @version 2022-05-24
 */
public class BusinessArrivalVouchMx extends DataEntity<BusinessArrivalVouchMx> {
	
	private static final long serialVersionUID = 1L;
	private BusinessArrivalVouch p;		// 父键 父类
	private Integer no;		// 行号
	private BaseCangKu ck;		// 仓库
	private BaseHuoWei hw;		// 推荐货位
	private String cinvcode;		// 存货编码
	private String cinvname;		// 存货名称
	private String cinvstd;		// 规格型号
	private String batchno;		// 批次号
	private String scdate;		// 生产日期
	private Double num;		// 数量
	private String unit;		// 单位
	private Double minnum;		// 最小包装数
	private String printstatus;		// 打印状态
	private String qrcode;		// 二维码内容
	private String cordercode;		// 来源单号
	private String irowno;		// 来源行号
	private BaseVendor vendor;		// 供应商
	private Office dept;		// 部门
	private String isrk;	// 是否入库
	private Double rukunum; // 入库数量
	private Date beginArriveDate;		// 开始 到货日期
	private Date endArriveDate;		// 结束 到货日期

	public Date getBeginArriveDate() {
		return beginArriveDate;
	}

	public BusinessArrivalVouchMx setBeginArriveDate(Date beginArriveDate) {
		this.beginArriveDate = beginArriveDate;
		return this;
	}

	public Date getEndArriveDate() {
		return endArriveDate;
	}

	public BusinessArrivalVouchMx setEndArriveDate(Date endArriveDate) {
		this.endArriveDate = endArriveDate;
		return this;
	}

	public BusinessArrivalVouchMx() {
		super();
	}

	public BusinessArrivalVouchMx(String id){
		super(id);
	}

	public BusinessArrivalVouchMx(BusinessArrivalVouch p){
		this.p = p;
	}

	public BusinessArrivalVouch getP() {
		return p;
	}

	public void setP(BusinessArrivalVouch p) {
		this.p = p;
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
	
	@NotNull(message="推荐货位不能为空")
	@ExcelField(title="推荐货位", fieldType=BaseHuoWei.class, value="hw.name", align=2, sort=10)
	public BaseHuoWei getHw() {
		if(hw==null){
			hw = new BaseHuoWei();
			hw.setName("");
		}
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
		return cinvstd;
	}

	public void setCinvstd(String cinvstd) {
		this.cinvstd = cinvstd;
	}
	
	@ExcelField(title="批次号", align=2, sort=14)
	public String getBatchno() {
		if (batchno==null){
			batchno = "";
		}
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}
	
	@ExcelField(title="生产日期", align=2, sort=15)
	public String getScdate() {
		if(scdate==null){
			scdate = "";
		}
		return scdate;
	}

	public void setScdate(String scdate) {
		this.scdate = scdate;
	}
	
	@NotNull(message="数量不能为空")
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
	
	@NotNull(message="最小包装数不能为空")
	@ExcelField(title="最小包装数", align=2, sort=18)
	public Double getMinnum() {
		return minnum;
	}

	public void setMinnum(Double minnum) {
		this.minnum = minnum;
	}
	
	@ExcelField(title="打印状态", align=2, sort=19)
	public String getPrintstatus() {
		return printstatus;
	}

	public void setPrintstatus(String printstatus) {
		this.printstatus = printstatus;
	}
	
	@ExcelField(title="二维码内容", align=2, sort=20)
	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
	
	@ExcelField(title="来源单号", align=2, sort=21)
	public String getCordercode() {
		return cordercode;
	}

	public void setCordercode(String cordercode) {
		this.cordercode = cordercode;
	}
	
	@ExcelField(title="来源行号", align=2, sort=22)
	public String getIrowno() {
		return irowno;
	}

	public void setIrowno(String irowno) {
		this.irowno = irowno;
	}
	
	@ExcelField(title="供应商", align=2, sort=23)
	public BaseVendor getVendor() {
		return vendor;
	}

	public void setVendor(BaseVendor vendor) {
		this.vendor = vendor;
	}
	
	@ExcelField(title="部门", align=2, sort=24)
	public Office getDept() {
		return dept;
	}

	public void setDept(Office dept) {
		this.dept = dept;
	}

	public String getIsrk() {
		return isrk;
	}

	public BusinessArrivalVouchMx setIsrk(String isrk) {
		this.isrk = isrk;
		return this;
	}

	public Double getRukunum() {
		return rukunum;
	}

	public BusinessArrivalVouchMx setRukunum(Double rukunum) {
		this.rukunum = rukunum;
		return this;
	}
}