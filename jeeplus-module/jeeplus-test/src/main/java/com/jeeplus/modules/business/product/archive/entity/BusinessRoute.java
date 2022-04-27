/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.product.archive.entity;

import javax.validation.constraints.NotNull;
import com.jeeplus.modules.base.site.entity.BaseSite;
import com.jeeplus.modules.business.filemanger.entity.BussinessFileManger;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotNull;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 工艺路线Entity
 * @author Jin
 * @version 2022-04-27
 */
public class BusinessRoute extends DataEntity<BusinessRoute> {
	
	private static final long serialVersionUID = 1L;
	private BusinessProduct p;		// 产品ID 父类
	private Integer no;		// 序号
	private BaseSite site;		// 工作站
	private BussinessFileManger file;		// 文件
	private Date startdate;		// 有效期(始)
	private Date enddate;		// 有效期(止)
	private String status;		// 状态
	
	public BusinessRoute() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public BusinessRoute(String id){
		super(id);
	}

	public BusinessRoute(BusinessProduct p){
		this.p = p;
	}

	public BusinessProduct getP() {
		return p;
	}

	public void setP(BusinessProduct p) {
		this.p = p;
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
	
	@NotNull(message="文件不能为空")
	@ExcelField(title="文件", fieldType=BussinessFileManger.class, value="file.name", align=2, sort=9)
	public BussinessFileManger getFile() {
		return file;
	}

	public void setFile(BussinessFileManger file) {
		this.file = file;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="有效期(始)不能为空")
	@ExcelField(title="有效期(始)", align=2, sort=10)
	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="有效期(止)不能为空")
	@ExcelField(title="有效期(止)", align=2, sort=11)
	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	
	@ExcelField(title="状态", align=2, sort=12)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}