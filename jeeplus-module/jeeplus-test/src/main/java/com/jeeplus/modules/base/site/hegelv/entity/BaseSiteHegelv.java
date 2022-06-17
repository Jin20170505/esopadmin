/**
 *
 */
package com.jeeplus.modules.base.site.hegelv.entity;

import javax.validation.constraints.NotNull;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 工序合格率Entity
 * @author Jin
 */
public class BaseSiteHegelv extends DataEntity<BaseSiteHegelv> {
	
	private static final long serialVersionUID = 1L;
	private String sitecode;		// 工序编码
	private String sitename;		// 工序名称
	private String workshop;		// 工作中心
	private Double hegelv;		// 合格率
	
	public BaseSiteHegelv() {
		super();
	}

	public BaseSiteHegelv(String id){
		super(id);
	}

	@ExcelField(title="工序编码", align=2, sort=7)
	public String getSitecode() {
		return sitecode;
	}

	public void setSitecode(String sitecode) {
		this.sitecode = sitecode;
	}
	
	@ExcelField(title="工序名称", align=2, sort=8)
	public String getSitename() {
		return sitename;
	}

	public void setSitename(String sitename) {
		this.sitename = sitename;
	}
	
	@ExcelField(title="工作中心", align=2, sort=9)
	public String getWorkshop() {
		return workshop;
	}

	public void setWorkshop(String workshop) {
		this.workshop = workshop;
	}
	
	@NotNull(message="合格率不能为空")
	@ExcelField(title="合格率", align=2, sort=10)
	public Double getHegelv() {
		return hegelv;
	}

	public void setHegelv(Double hegelv) {
		this.hegelv = hegelv;
	}
	
}