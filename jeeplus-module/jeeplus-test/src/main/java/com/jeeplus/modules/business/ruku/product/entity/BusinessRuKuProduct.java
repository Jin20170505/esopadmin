/**
 *
 */
package com.jeeplus.modules.business.ruku.product.entity;

import com.jeeplus.modules.base.cangku.entity.BaseCangKu;
import javax.validation.constraints.NotNull;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 产成品入库Entity
 * @author Jin
 */
public class BusinessRuKuProduct extends DataEntity<BusinessRuKuProduct> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 入库单号
	private String sccode;		// 生产订单号
	private String cinvcode;		// 存货编码
	private String cinvname;		// 存货名称
	private String cinvstd;		// 规格型号
	private String batchno;		// 批号
	private BaseCangKu cangku;		// 仓库
	private String bgid;		// 报工ID
	private Double num;  // 数量
	private String bgcode;		// 报工单号
	private String sych;		// 是否同步
	private List<BusinessRuKuProductMx> businessRuKuProductMxList = Lists.newArrayList();		// 子表列表
	
	public BusinessRuKuProduct() {
		super();
	}

	public BusinessRuKuProduct(String id){
		super(id);
	}

	@ExcelField(title="入库单号", align=2, sort=6)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="生产订单号", align=2, sort=7)
	public String getSccode() {
		return sccode;
	}

	public void setSccode(String sccode) {
		this.sccode = sccode;
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
	
	@ExcelField(title="生产批号", align=2, sort=11)
	public String getBatchno() {
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}
	
	@NotNull(message="仓库不能为空")
	@ExcelField(title="仓库", fieldType=BaseCangKu.class, value="cangku.name", align=2, sort=12)
	public BaseCangKu getCangku() {
		return cangku;
	}

	public void setCangku(BaseCangKu cangku) {
		this.cangku = cangku;
	}
	
	@ExcelField(title="报工ID", align=2, sort=14)
	public String getBgid() {
		return bgid;
	}

	public void setBgid(String bgid) {
		this.bgid = bgid;
	}
	
	@ExcelField(title="报工单号", align=2, sort=15)
	public String getBgcode() {
		return bgcode;
	}

	public void setBgcode(String bgcode) {
		this.bgcode = bgcode;
	}
	
	@ExcelField(title="是否同步", align=2, sort=16)
	public String getSych() {
		return sych;
	}

	public void setSych(String sych) {
		this.sych = sych;
	}
	
	public List<BusinessRuKuProductMx> getBusinessRuKuProductMxList() {
		return businessRuKuProductMxList;
	}

	public void setBusinessRuKuProductMxList(List<BusinessRuKuProductMx> businessRuKuProductMxList) {
		this.businessRuKuProductMxList = businessRuKuProductMxList;
	}

	public Double getNum() {
		return num;
	}

	public BusinessRuKuProduct setNum(Double num) {
		this.num = num;
		return this;
	}
}