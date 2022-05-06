/**
 *
 */
package com.jeeplus.modules.business.shengchan.dingdan.entity;

import com.jeeplus.modules.sys.entity.Office;
import javax.validation.constraints.NotNull;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 生产订单Entity
 * @author Jin
 */
public class BusinessShengChanDingDan extends DataEntity<BusinessShengChanDingDan> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 生产单号
	private Office dept;		// 生产部门
	private List<BusinessShengChanDingDanMingXi> businessShengChanDingDanMingXiList = Lists.newArrayList();		// 子表列表
	
	public BusinessShengChanDingDan() {
		super();
	}

	public BusinessShengChanDingDan(String id){
		super(id);
	}

	@ExcelField(title="生产单号", align=2, sort=7)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@NotNull(message="生产部门不能为空")
	@ExcelField(title="生产部门", fieldType=Office.class, value="dept.name", align=2, sort=8)
	public Office getDept() {
		return dept;
	}

	public void setDept(Office dept) {
		this.dept = dept;
	}
	
	public List<BusinessShengChanDingDanMingXi> getBusinessShengChanDingDanMingXiList() {
		return businessShengChanDingDanMingXiList;
	}

	public void setBusinessShengChanDingDanMingXiList(List<BusinessShengChanDingDanMingXi> businessShengChanDingDanMingXiList) {
		this.businessShengChanDingDanMingXiList = businessShengChanDingDanMingXiList;
	}
}