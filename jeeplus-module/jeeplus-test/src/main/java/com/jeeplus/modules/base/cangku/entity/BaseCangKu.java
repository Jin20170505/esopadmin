/**
 *
 */
package com.jeeplus.modules.base.cangku.entity;

import com.jeeplus.modules.sys.entity.Office;
import javax.validation.constraints.NotNull;
import com.jeeplus.modules.base.factory.entity.BaseFactory;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 仓库档案Entity
 * @author Jin
 */
public class BaseCangKu extends DataEntity<BaseCangKu> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 仓库编码
	private String name;		// 仓库名称
	private Office dept;		// 所属部门
	private BaseFactory factory;		// 所属工厂
	private String tel;		// 电话
	private String master;		// 负责人
	private String address;		// 仓库地址
	private String type;		// 仓库属性
	private Date indate;		// 停用日期
	private String usehw; // 是否启动货位
	
	public BaseCangKu() {
		super();
	}

	public BaseCangKu(String id){
		super(id);
	}

	@ExcelField(title="仓库编码", align=2, sort=7)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="仓库名称", align=2, sort=8)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="所属部门不能为空")
	@ExcelField(title="所属部门", fieldType=Office.class, value="dept.name", align=2, sort=9)
	public Office getDept() {
		return dept;
	}

	public void setDept(Office dept) {
		this.dept = dept;
	}
	
	@NotNull(message="所属工厂不能为空")
	@ExcelField(title="所属工厂", fieldType=BaseFactory.class, value="factory.name", align=2, sort=10)
	public BaseFactory getFactory() {
		return factory;
	}

	public void setFactory(BaseFactory factory) {
		this.factory = factory;
	}
	
	@ExcelField(title="电话", align=2, sort=11)
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	@ExcelField(title="负责人", align=2, sort=12)
	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}
	
	@ExcelField(title="仓库地址", align=2, sort=13)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title="仓库属性", dictType="cangku_shuxing", align=2, sort=14)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="停用日期 不能为空")
	@ExcelField(title="停用日期 ", align=2, sort=15)
	public Date getIndate() {
		return indate;
	}

	public void setIndate(Date indate) {
		this.indate = indate;
	}

	@NotNull(message="停用日期 不能为空")
	@ExcelField(title="是否启用货位 ", align=2, sort=15)
	public String getUsehw() {
		return usehw;
	}

	public BaseCangKu setUsehw(String usehw) {
		this.usehw = usehw;
		return this;
	}
}