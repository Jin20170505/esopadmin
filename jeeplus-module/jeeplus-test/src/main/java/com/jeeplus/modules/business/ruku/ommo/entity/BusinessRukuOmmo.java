/**
 *
 */
package com.jeeplus.modules.business.ruku.ommo.entity;

import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.base.vendor.entity.BaseVendor;
import com.jeeplus.modules.base.cangku.entity.BaseCangKu;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 委外入库Entity
 * @author Jin
 */
public class BusinessRukuOmmo extends DataEntity<BusinessRukuOmmo> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 单号
	private String wdcode;		// 委外到货单号
	private Office dept;		// 部门
	private BaseVendor vendor;		// 供应商
	private String ddate;		// 到货时间
	private String rdate;		// 入库时间
	private BaseCangKu ck;		// 仓库
	private String u8code;		// u8单号
	private String beginRdate;		// 开始 入库时间
	private String endRdate;		// 结束 入库时间
	private List<BusinessRukuOmmoMx> businessRukuOmmoMxList = Lists.newArrayList();		// 子表列表
	
	public BusinessRukuOmmo() {
		super();
	}

	public BusinessRukuOmmo(String id){
		super(id);
	}

	@ExcelField(title="单号", align=2, sort=7)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="委外到货单号", align=2, sort=8)
	public String getWdcode() {
		return wdcode;
	}

	public void setWdcode(String wdcode) {
		this.wdcode = wdcode;
	}
	
	@ExcelField(title="部门", fieldType=Office.class, value="dept.name", align=2, sort=9)
	public Office getDept() {
		return dept;
	}

	public void setDept(Office dept) {
		this.dept = dept;
	}
	
	@ExcelField(title="供应商", fieldType=BaseVendor.class, value="vendor.name", align=2, sort=10)
	public BaseVendor getVendor() {
		return vendor;
	}

	public void setVendor(BaseVendor vendor) {
		this.vendor = vendor;
	}
	
	@ExcelField(title="到货时间", align=2, sort=11)
	public String getDdate() {
		return ddate;
	}

	public void setDdate(String ddate) {
		this.ddate = ddate;
	}
	
	@ExcelField(title="入库时间", align=2, sort=12)
	public String getRdate() {
		return rdate;
	}

	public void setRdate(String rdate) {
		this.rdate = rdate;
	}
	
	@ExcelField(title="仓库", fieldType=BaseCangKu.class, value="ck.name", align=2, sort=13)
	public BaseCangKu getCk() {
		return ck;
	}

	public void setCk(BaseCangKu ck) {
		this.ck = ck;
	}
	
	@ExcelField(title="u8单号", align=2, sort=14)
	public String getU8code() {
		return u8code;
	}

	public void setU8code(String u8code) {
		this.u8code = u8code;
	}
	
	public String getBeginRdate() {
		return beginRdate;
	}

	public void setBeginRdate(String beginRdate) {
		this.beginRdate = beginRdate;
	}
	
	public String getEndRdate() {
		return endRdate;
	}

	public void setEndRdate(String endRdate) {
		this.endRdate = endRdate;
	}
		
	public List<BusinessRukuOmmoMx> getBusinessRukuOmmoMxList() {
		return businessRukuOmmoMxList;
	}

	public void setBusinessRukuOmmoMxList(List<BusinessRukuOmmoMx> businessRukuOmmoMxList) {
		this.businessRukuOmmoMxList = businessRukuOmmoMxList;
	}
}