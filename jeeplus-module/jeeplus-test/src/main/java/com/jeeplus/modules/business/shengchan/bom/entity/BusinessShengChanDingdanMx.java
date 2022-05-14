/**
 *
 */
package com.jeeplus.modules.business.shengchan.bom.entity;

import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 生产子件Entity
 * @author Jin
 */
public class BusinessShengChanDingdanMx extends DataEntity<BusinessShengChanDingdanMx> {
	
	private static final long serialVersionUID = 1L;
	private String sccode;		// 生产单号
	private String lineno;		// 生产行号
	private String cinvcode;		// 存货编码
	private String cinvname;		// 存货名称
	private String cinvstd;		// 规格型号
	private String num;		// 数量
	private String unit;		// 单位
	private List<BusinessShengChanBom> businessShengChanBomList = Lists.newArrayList();		// 子表列表
	
	public BusinessShengChanDingdanMx() {
		super();
	}

	public BusinessShengChanDingdanMx(String id){
		super(id);
	}

	@ExcelField(title="生产单号", align=2, sort=7)
	public String getSccode() {
		return sccode;
	}

	public void setSccode(String sccode) {
		this.sccode = sccode;
	}
	
	@ExcelField(title="生产行号", align=2, sort=8)
	public String getLineno() {
		return lineno;
	}

	public void setLineno(String lineno) {
		this.lineno = lineno;
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
	
	@ExcelField(title="数量", align=2, sort=12)
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
	@ExcelField(title="单位", align=2, sort=13)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public List<BusinessShengChanBom> getBusinessShengChanBomList() {
		return businessShengChanBomList;
	}

	public void setBusinessShengChanBomList(List<BusinessShengChanBom> businessShengChanBomList) {
		this.businessShengChanBomList = businessShengChanBomList;
	}
}