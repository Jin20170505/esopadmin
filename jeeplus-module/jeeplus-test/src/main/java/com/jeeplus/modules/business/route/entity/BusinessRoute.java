/**
 *
 */
package com.jeeplus.modules.business.route.entity;

import com.jeeplus.modules.business.product.archive.entity.BusinessProduct;
import javax.validation.constraints.NotNull;
import com.jeeplus.modules.base.site.entity.BaseSite;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 工艺路线Entity
 * @author Jin
 */
public class BusinessRoute extends DataEntity<BusinessRoute> {
	
	private static final long serialVersionUID = 1L;
	private BusinessProduct product;		// 存货档案
	private Integer no;		// 序号
	private BaseSite site;		// 工作站
	private String fileurl;		// 指导书
	private String version;		// 版本号
	private String status;		// 是否停用
	
	public BusinessRoute() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public BusinessRoute(String id){
		super(id);
	}

	public BusinessRoute(BusinessProduct product){
		this.product = product;
	}

	@NotNull(message="存货档案不能为空")
	@ExcelField(title="存货档案", fieldType=BusinessProduct.class, value="product.name", align=2, sort=6)
	public BusinessProduct getProduct() {
		return product;
	}

	public void setProduct(BusinessProduct product) {
		this.product = product;
	}
	
	@NotNull(message="序号不能为空")
	@ExcelField(title="序号", align=2, sort=7)
	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}
	
	@NotNull(message="工作站不能为空")
	@ExcelField(title="工作站", fieldType=BaseSite.class, value="site.name", align=2, sort=8)
	public BaseSite getSite() {
		return site;
	}

	public void setSite(BaseSite site) {
		this.site = site;
	}
	
	@ExcelField(title="指导书", align=2, sort=9)
	public String getFileurl() {
		return fileurl;
	}

	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}
	
	@ExcelField(title="版本号", align=2, sort=10)
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	@ExcelField(title="是否停用", dictType="yes_no", align=2, sort=11)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}