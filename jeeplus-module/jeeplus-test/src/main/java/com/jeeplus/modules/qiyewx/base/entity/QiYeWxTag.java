/**
 *
 */
package com.jeeplus.modules.qiyewx.base.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 微信标签Entity
 * @author Jin
 */
public class QiYeWxTag extends DataEntity<QiYeWxTag> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	
	public QiYeWxTag() {
		super();
	}

	public QiYeWxTag(String id){
		super(id);
	}

	@ExcelField(title="名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}