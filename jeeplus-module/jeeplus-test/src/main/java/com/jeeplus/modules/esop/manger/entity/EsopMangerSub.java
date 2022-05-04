/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.esop.manger.entity;

import javax.validation.constraints.NotNull;
import com.jeeplus.modules.base.site.entity.BaseSite;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 路线Entity
 * @author Jin
 * @version 2022-05-04
 */
public class EsopMangerSub extends DataEntity<EsopMangerSub> {
	
	private static final long serialVersionUID = 1L;
	private EsopManger m;		// 主表ID 父类
	private Integer no;		// 序号
	private BaseSite site;		// 工作站
	private String fileurl;		// 指导书
	
	public EsopMangerSub() {
		super();
	}

	public EsopMangerSub(String id){
		super(id);
	}

	public EsopMangerSub(EsopManger m){
		this.m = m;
	}

	public EsopManger getM() {
		return m;
	}

	public void setM(EsopManger m) {
		this.m = m;
	}
	
	@NotNull(message="序号不能为空")
	@ExcelField(title="序号", align=2, sort=8)
	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}
	
	@NotNull(message="工作站不能为空")
	@ExcelField(title="工作站", align=2, sort=9)
	public BaseSite getSite() {
		return site;
	}

	public void setSite(BaseSite site) {
		this.site = site;
	}
	
	@ExcelField(title="指导书", align=2, sort=10)
	public String getFileurl() {
		return fileurl;
	}

	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}
	
}