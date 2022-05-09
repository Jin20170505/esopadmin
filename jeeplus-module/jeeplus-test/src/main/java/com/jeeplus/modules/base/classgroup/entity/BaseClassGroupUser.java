/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.base.classgroup.entity;

import javax.validation.constraints.NotNull;
import com.jeeplus.modules.sys.entity.User;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 班组成员Entity
 * @author Jin
 * @version 2022-05-09
 */
public class BaseClassGroupUser extends DataEntity<BaseClassGroupUser> {
	
	private static final long serialVersionUID = 1L;
	private Integer no;		// 序号
	private User user;		// 工号
	private String username;		// 姓名
	private BaseClassGroup p;		// 父键 父类
	
	public BaseClassGroupUser() {
		super();
	}

	public BaseClassGroupUser(String id){
		super(id);
	}

	public BaseClassGroupUser(BaseClassGroup p){
		this.p = p;
	}

	@NotNull(message="序号不能为空")
	@ExcelField(title="序号", align=2, sort=6)
	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}
	
	@NotNull(message="工号不能为空")
	@ExcelField(title="工号", fieldType=User.class, value="user.code", align=2, sort=7)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@ExcelField(title="姓名", align=2, sort=8)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public BaseClassGroup getP() {
		return p;
	}

	public void setP(BaseClassGroup p) {
		this.p = p;
	}
	
}