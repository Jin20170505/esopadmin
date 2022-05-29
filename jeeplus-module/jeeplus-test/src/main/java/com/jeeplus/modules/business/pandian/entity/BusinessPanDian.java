/**
 *
 */
package com.jeeplus.modules.business.pandian.entity;

import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.base.cangku.entity.BaseCangKu;
import com.jeeplus.modules.base.huowei.entity.BaseHuoWei;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 盘点单Entity
 * @author Jin
 */
public class BusinessPanDian extends DataEntity<BusinessPanDian> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 盘点单号
	private String ddate;		// 盘点日期
	private User duser;		// 盘点人
	private BaseCangKu ck;		// 盘点仓库
	private BaseHuoWei hw;		// 盘点货位
	private String beginDdate;		// 开始 盘点日期
	private String endDdate;		// 结束 盘点日期
	private List<BusinessPanDianMx> businessPanDianMxList = Lists.newArrayList();		// 子表列表
	
	public BusinessPanDian() {
		super();
	}

	public BusinessPanDian(String id){
		super(id);
	}

	@ExcelField(title="盘点单号", align=2, sort=7)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="盘点日期", align=2, sort=8)
	public String getDdate() {
		return ddate;
	}

	public void setDdate(String ddate) {
		this.ddate = ddate;
	}
	
	@ExcelField(title="盘点人", fieldType=User.class, value="duser.name", align=2, sort=9)
	public User getDuser() {
		return duser;
	}

	public void setDuser(User duser) {
		this.duser = duser;
	}
	
	@ExcelField(title="盘点仓库", fieldType=BaseCangKu.class, value="ck.name", align=2, sort=10)
	public BaseCangKu getCk() {
		return ck;
	}

	public void setCk(BaseCangKu ck) {
		this.ck = ck;
	}
	
	@ExcelField(title="盘点货位", fieldType=BaseHuoWei.class, value="hw.name", align=2, sort=11)
	public BaseHuoWei getHw() {
		return hw;
	}

	public void setHw(BaseHuoWei hw) {
		this.hw = hw;
	}
	
	public String getBeginDdate() {
		return beginDdate;
	}

	public void setBeginDdate(String beginDdate) {
		this.beginDdate = beginDdate;
	}
	
	public String getEndDdate() {
		return endDdate;
	}

	public void setEndDdate(String endDdate) {
		this.endDdate = endDdate;
	}
		
	public List<BusinessPanDianMx> getBusinessPanDianMxList() {
		return businessPanDianMxList;
	}

	public void setBusinessPanDianMxList(List<BusinessPanDianMx> businessPanDianMxList) {
		this.businessPanDianMxList = businessPanDianMxList;
	}
}