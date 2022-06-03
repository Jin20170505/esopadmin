/**
 *
 */
package com.jeeplus.modules.base.cangku.entity;

import com.jeeplus.modules.base.cangku.entity.BaseCangKu;
import javax.validation.constraints.NotNull;
import com.jeeplus.modules.sys.entity.User;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 仓库可见人Entity
 * @author Jin
 */
public class BaseCkUser extends DataEntity<BaseCkUser> {
	
	private static final long serialVersionUID = 1L;
	private BaseCangKu ck;		// 仓库
	private User user;		// 可见人
	
	public BaseCkUser() {
		super();
	}

	public BaseCkUser(String id){
		super(id);
	}

	private String ckcode; //仓库ID/编码

	@ExcelField(title="仓库编码",  align=2, sort=6)
	public String getCkcode() {
		return ckcode;
	}

	public BaseCkUser setCkcode(String ckcode) {
		this.ckcode = ckcode;
		return this;
	}

	@NotNull(message="仓库不能为空")
	public BaseCangKu getCk() {
		return ck;
	}

	public void setCk(BaseCangKu ck) {
		this.ck = ck;
	}
	
	@NotNull(message="可见人不能为空")
	@ExcelField(title="可见人", fieldType=User.class, value="user.name", align=2, sort=7)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}