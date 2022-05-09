/**
 *
 */
package com.jeeplus.modules.base.route.entity;

import com.jeeplus.modules.business.product.archive.entity.BusinessProduct;
import javax.validation.constraints.NotNull;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 工艺路线Entity
 * @author Jin
 */
public class BaseRoteMain extends DataEntity<BaseRoteMain> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 编号
	private BusinessProduct product;		// 存货档案
	private String std;		// 规格型号
	private String version;		// 版本
	private String enable;		// 是否停用
	private List<BaseRoute> baseRouteList = Lists.newArrayList();		// 子表列表
	
	public BaseRoteMain() {
		super();
	}

	public BaseRoteMain(String id){
		super(id);
	}

	@ExcelField(title="编号", align=2, sort=4)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@NotNull(message="存货档案不能为空")
	@ExcelField(title="存货档案", fieldType=BusinessProduct.class, value="product.name", align=2, sort=5)
	public BusinessProduct getProduct() {
		return product;
	}

	public void setProduct(BusinessProduct product) {
		this.product = product;
	}
	
	@ExcelField(title="规格型号", align=2, sort=6)
	public String getStd() {
		return std;
	}

	public void setStd(String std) {
		this.std = std;
	}
	
	@ExcelField(title="版本", align=2, sort=7)
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	@ExcelField(title="是否停用", dictType="yes_no", align=2, sort=8)
	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}
	
	public List<BaseRoute> getBaseRouteList() {
		return baseRouteList;
	}

	public void setBaseRouteList(List<BaseRoute> baseRouteList) {
		this.baseRouteList = baseRouteList;
	}
}