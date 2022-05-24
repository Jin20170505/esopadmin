/**
 *
 */
package com.jeeplus.modules.business.ruku.caigou.entity;

import com.jeeplus.modules.base.cangku.entity.BaseCangKu;
import javax.validation.constraints.NotNull;
import com.jeeplus.modules.base.huowei.entity.BaseHuoWei;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 采购入库Entity
 * @author Jin
 */
public class BusinessRukuCaiGou extends DataEntity<BusinessRukuCaiGou> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 单号
	private String arrivalcode;		// 到货单号
	private String arrivaldate;		// 到货日期
	private BaseCangKu ck;		// 仓库
	private BaseHuoWei hw;		// 货位
	private String u8code;		// U8单号
	private List<BusinessRukuCaigouMx> businessRukuCaigouMxList = Lists.newArrayList();		// 子表列表
	
	public BusinessRukuCaiGou() {
		super();
	}

	public BusinessRukuCaiGou(String id){
		super(id);
	}

	@ExcelField(title="单号", align=2, sort=7)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="到货单号", align=2, sort=8)
	public String getArrivalcode() {
		return arrivalcode;
	}

	public void setArrivalcode(String arrivalcode) {
		this.arrivalcode = arrivalcode;
	}
	
	@ExcelField(title="到货日期", align=2, sort=9)
	public String getArrivaldate() {
		return arrivaldate;
	}

	public void setArrivaldate(String arrivaldate) {
		this.arrivaldate = arrivaldate;
	}

	@NotNull(message="仓库不能为空")
	@ExcelField(title="仓库", fieldType=BaseCangKu.class, value="ck.name", align=2, sort=10)
	public BaseCangKu getCk() {
		return ck;
	}

	public void setCk(BaseCangKu ck) {
		this.ck = ck;
	}
	
	@NotNull(message="货位不能为空")
	@ExcelField(title="货位", fieldType=BaseHuoWei.class, value="hw.name", align=2, sort=11)
	public BaseHuoWei getHw() {
		return hw;
	}

	public void setHw(BaseHuoWei hw) {
		this.hw = hw;
	}
	
	@ExcelField(title="U8单号", align=2, sort=12)
	public String getU8code() {
		return u8code;
	}

	public void setU8code(String u8code) {
		this.u8code = u8code;
	}
	
	public List<BusinessRukuCaigouMx> getBusinessRukuCaigouMxList() {
		return businessRukuCaigouMxList;
	}

	public void setBusinessRukuCaigouMxList(List<BusinessRukuCaigouMx> businessRukuCaigouMxList) {
		this.businessRukuCaigouMxList = businessRukuCaigouMxList;
	}
}