/**
 *
 */
package com.jeeplus.modules.business.faliao.entity;

import com.jeeplus.modules.base.cangku.entity.BaseCangKu;
import java.util.Date;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 调拨单Entity
 * @author Jin
 */
public class BusinessFaLiao extends DataEntity<BusinessFaLiao> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 调拨单号
	private BaseCangKu fromck;		// 源仓库
	private BaseCangKu tock;		// 目标仓库
	private Date beginCreateDate;		// 开始 调拨时间
	private Date endCreateDate;		// 结束 调拨时间
	private List<BusinessFaLiaoMx> businessFaLiaoMxList = Lists.newArrayList();		// 子表列表
	
	public BusinessFaLiao() {
		super();
	}

	public BusinessFaLiao(String id){
		super(id);
	}

	@ExcelField(title="调拨单号", align=2, sort=5)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="源仓库", fieldType=BaseCangKu.class, value="fromck.name", align=2, sort=6)
	public BaseCangKu getFromck() {
		return fromck;
	}

	public void setFromck(BaseCangKu fromck) {
		this.fromck = fromck;
	}
	
	@ExcelField(title="目标仓库", fieldType=BaseCangKu.class, value="tock.name", align=2, sort=7)
	public BaseCangKu getTock() {
		return tock;
	}

	public void setTock(BaseCangKu tock) {
		this.tock = tock;
	}
	
	public Date getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}
	
	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}
		
	public List<BusinessFaLiaoMx> getBusinessFaLiaoMxList() {
		return businessFaLiaoMxList;
	}

	public void setBusinessFaLiaoMxList(List<BusinessFaLiaoMx> businessFaLiaoMxList) {
		this.businessFaLiaoMxList = businessFaLiaoMxList;
	}
}