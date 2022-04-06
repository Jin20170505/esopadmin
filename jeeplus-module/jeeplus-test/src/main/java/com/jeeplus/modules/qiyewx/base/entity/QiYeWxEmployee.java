/**
 *
 */
package com.jeeplus.modules.qiyewx.base.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 企业微信_员工Entity
 * @author Jin
 * @version 2021-08-25
 */
public class QiYeWxEmployee extends DataEntity<QiYeWxEmployee> {
	
	private static final long serialVersionUID = 1L;
	private QiYeWxDept mainDept;		// 主要部门 父类
	private String name;		// 姓名
	private String userid;		// 员工ID
	private String deptmentids;		// 部门ID组
	private String position;		// 职务信息
	private String mobile;		// 手机号
	private String gender;		// 性别
	private String email;		// 邮箱
	private String avatar;		// 头像
	private String thumbvatar;		// 头像(压缩)
	private String telephone;		// 座机
	private String alias;		// 别名
	private Integer status;		// 激活状态: 1=已激活，2=已禁用，4=未激活，5=退出企业。
	private String openid;
	private String tag;	// 标签
	public QiYeWxEmployee() {
		super();
	}

	public QiYeWxEmployee(String id){
		super(id);
	}

	public QiYeWxEmployee(QiYeWxDept mainDept){
		this.mainDept = mainDept;
	}

	public QiYeWxDept getMainDept() {
		return mainDept;
	}

	public void setMainDept(QiYeWxDept mainDept) {
		this.mainDept = mainDept;
	}
	
	@ExcelField(title="姓名", align=2, sort=8)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="员工ID", align=2, sort=9)
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	@ExcelField(title="部门ID组", align=2, sort=10)
	public String getDeptmentids() {
		return deptmentids;
	}

	public void setDeptmentids(String deptmentids) {
		this.deptmentids = deptmentids;
	}
	
	@ExcelField(title="职务信息", align=2, sort=11)
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	@ExcelField(title="手机号", align=2, sort=12)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@ExcelField(title="性别", dictType="sex", align=2, sort=13)
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	@ExcelField(title="邮箱", align=2, sort=14)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@ExcelField(title="头像", align=2, sort=15)
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	@ExcelField(title="头像(压缩)", align=2, sort=16)
	public String getThumbvatar() {
		return thumbvatar;
	}

	public void setThumbvatar(String thumbvatar) {
		this.thumbvatar = thumbvatar;
	}
	
	@ExcelField(title="座机", align=2, sort=17)
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@ExcelField(title="别名", align=2, sort=18)
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	@ExcelField(title="激活状态: 1=已激活，2=已禁用，4=未激活，5=退出企业。", dictType="qywxemployee_status", align=2, sort=19)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	@ExcelField(title="标签", align=2, sort=30)
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
}