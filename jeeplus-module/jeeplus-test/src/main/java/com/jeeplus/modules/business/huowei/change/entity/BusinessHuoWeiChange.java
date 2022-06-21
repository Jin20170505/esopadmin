/**
 *
 */
package com.jeeplus.modules.business.huowei.change.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.base.cangku.entity.BaseCangKu;

/**
 * 货位调整Entity
 * @author Jin
 */
public class BusinessHuoWeiChange extends DataEntity<BusinessHuoWeiChange> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 单号
	private BaseCangKu ck;		// 仓库
	private Date ddate;		// 调整时间
	private String cmaker;		// 调整人
	private Date beginDdate;		// 开始 调整时间
	private Date endDdate;		// 结束 调整时间
	private List<BusinessHuoWeiChangeMx> businessHuoWeiChangeMxList = Lists.newArrayList();		// 子表列表
	
	public BusinessHuoWeiChange() {
		super();
	}

	public BusinessHuoWeiChange(String id){
		super(id);
	}

	@ExcelField(title="单号", align=2, sort=7)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BaseCangKu getCk() {
		return ck;
	}

	public void setCk(BaseCangKu ck) {
		this.ck = ck;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="调整时间", align=2, sort=9)
	public Date getDdate() {
		return ddate;
	}

	public void setDdate(Date ddate) {
		this.ddate = ddate;
	}
	
	@ExcelField(title="调整人", align=2, sort=10)
	public String getCmaker() {
		return cmaker;
	}

	public void setCmaker(String cmaker) {
		this.cmaker = cmaker;
	}
	
	public Date getBeginDdate() {
		return beginDdate;
	}

	public void setBeginDdate(Date beginDdate) {
		this.beginDdate = beginDdate;
	}
	
	public Date getEndDdate() {
		return endDdate;
	}

	public void setEndDdate(Date endDdate) {
		this.endDdate = endDdate;
	}
		
	public List<BusinessHuoWeiChangeMx> getBusinessHuoWeiChangeMxList() {
		return businessHuoWeiChangeMxList;
	}

	public void setBusinessHuoWeiChangeMxList(List<BusinessHuoWeiChangeMx> businessHuoWeiChangeMxList) {
		this.businessHuoWeiChangeMxList = businessHuoWeiChangeMxList;
	}
}