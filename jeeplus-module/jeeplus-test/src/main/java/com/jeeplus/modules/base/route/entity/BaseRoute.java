/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.base.route.entity;

import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.product.archive.entity.BusinessProduct;
import javax.validation.constraints.NotNull;
import com.jeeplus.modules.base.site.entity.BaseSite;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 工艺路线Entity
 * @author Jin
 * @version 2022-05-09
 */
public class BaseRoute extends DataEntity<BaseRoute> {
	
	private static final long serialVersionUID = 1L;
	private BusinessProduct product;		// 存货档案
	private Integer no;		// 序号
	private BaseSite site;		// 工作站
	private String fileurl;		// 指导书
	private String filename;	// 文件名称
	private String version;		// 版本号
	private BaseRoteMain p;		// 父键 父类
	
	public BaseRoute() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public BaseRoute(String id){
		super(id);
	}

	public BaseRoute(BaseRoteMain p){
		this.p = p;
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

	public String getFilename() {
		if(StringUtils.isNotEmpty(fileurl)){
			filename=fileurl.substring(fileurl.lastIndexOf("/")+1);
		}
		return filename;
	}

	public BaseRoute setFilename(String filename) {
		this.filename = filename;
		return this;
	}

	@ExcelField(title="版本号", align=2, sort=10)
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public BaseRoteMain getP() {
		return p;
	}

	public void setP(BaseRoteMain p) {
		this.p = p;
	}
	
}