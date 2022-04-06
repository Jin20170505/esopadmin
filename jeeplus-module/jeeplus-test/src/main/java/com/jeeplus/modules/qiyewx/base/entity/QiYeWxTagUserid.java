/**
 *
 */
package com.jeeplus.modules.qiyewx.base.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 标签-用户对照Entity
 * @author Jin
 */
public class QiYeWxTagUserid extends DataEntity<QiYeWxTagUserid> {
	
	private static final long serialVersionUID = 1L;
	private String userid;		// 用户id
	private String tagid;		// 标签id
	
	public QiYeWxTagUserid() {
		super();
	}

	public QiYeWxTagUserid(String id){
		super(id);
	}

	@ExcelField(title="用户id", align=2, sort=1)
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	@ExcelField(title="标签id", align=2, sort=2)
	public String getTagid() {
		return tagid;
	}

	public void setTagid(String tagid) {
		this.tagid = tagid;
	}
	
}