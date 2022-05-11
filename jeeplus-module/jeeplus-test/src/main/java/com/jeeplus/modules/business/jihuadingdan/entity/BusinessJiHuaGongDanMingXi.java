/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.jihuadingdan.entity;

import javax.validation.constraints.NotNull;
import com.jeeplus.modules.base.site.entity.BaseSite;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.base.classgroup.entity.BaseClassGroup;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 计划工单明细Entity
 * @author Jin
 * @version 2022-05-06
 */
public class BusinessJiHuaGongDanMingXi extends DataEntity<BusinessJiHuaGongDanMingXi> {
	
	private static final long serialVersionUID = 1L;
	private BusinessJiHuaGongDan p;		// 计划工单 父类
	private Integer no;		// 序号
	private BaseSite site;		// 工作站
	private Double num;		// 加工数量
	private String userno;		// 人员工号
	private String username;		// 人员名称
	private BaseClassGroup classgroup;		// 班组
	private String incomplete;		// 是否完成
	private String qrcade;		// 二维码
	
	public BusinessJiHuaGongDanMingXi() {
		super();
	}

	public BusinessJiHuaGongDanMingXi(String id){
		super(id);
	}

	public BusinessJiHuaGongDanMingXi(BusinessJiHuaGongDan p){
		this.p = p;
	}

	public BusinessJiHuaGongDan getP() {
		return p;
	}

	public void setP(BusinessJiHuaGongDan p) {
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
	
	@NotNull(message="加工数量不能为空")
	@ExcelField(title="加工数量", align=2, sort=9)
	public Double getNum() {
		return num;
	}

	public void setNum(Double num) {
		this.num = num;
	}
	
	@ExcelField(title="人员工号", align=2, sort=10)
	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}
	
	@ExcelField(title="人员名称", align=2, sort=11)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@ExcelField(title="班组", fieldType=BaseClassGroup.class, value="classgroup.name", align=2, sort=12)
	public BaseClassGroup getClassgroup() {
		if(classgroup == null){
			classgroup = new BaseClassGroup();
		}
		return classgroup;
	}

	public void setClassgroup(BaseClassGroup classgroup) {
		this.classgroup = classgroup;
	}
	
	@ExcelField(title="是否完成", align=2, sort=14)
	public String getIncomplete() {
		return incomplete;
	}

	public void setIncomplete(String incomplete) {
		this.incomplete = incomplete;
	}
	
	@ExcelField(title="二维码", align=2, sort=15)
	public String getQrcade() {
		return qrcade;
	}

	public void setQrcade(String qrcade) {
		this.qrcade = qrcade;
	}
	
}