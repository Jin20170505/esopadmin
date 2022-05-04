/**
 *
 */
package com.jeeplus.modules.esop.manger.entity;

import com.jeeplus.modules.business.product.archive.entity.BusinessProduct;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * ESOP工单Entity
 * @author Jin
 */
public class EsopManger extends DataEntity<EsopManger> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 工单号
	private String line;		// 行号
	private BusinessProduct product;		// 存货档案
	private String specification;		// 规格型号
	private String version;		// 版本号
	private String status;		// 状态
	private List<EsopMangerSub> esopMangerSubList = Lists.newArrayList();		// 子表列表
	
	public EsopManger() {
		super();
	}

	public EsopManger(String id){
		super(id);
	}

	@ExcelField(title="工单号", align=2, sort=7)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="行号", align=2, sort=8)
	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}
	
	@ExcelField(title="存货档案", fieldType=BusinessProduct.class, value="product.name", align=2, sort=9)
	public BusinessProduct getProduct() {
		return product;
	}

	public void setProduct(BusinessProduct product) {
		this.product = product;
	}
	
	@ExcelField(title="规格型号", align=2, sort=10)
	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}
	
	@ExcelField(title="版本号", align=2, sort=11)
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	@ExcelField(title="状态", align=2, sort=12)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public List<EsopMangerSub> getEsopMangerSubList() {
		return esopMangerSubList;
	}

	public void setEsopMangerSubList(List<EsopMangerSub> esopMangerSubList) {
		this.esopMangerSubList = esopMangerSubList;
	}
}