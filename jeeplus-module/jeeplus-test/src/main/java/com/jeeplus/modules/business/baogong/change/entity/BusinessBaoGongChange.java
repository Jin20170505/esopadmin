/**
 *
 */
package com.jeeplus.modules.business.baogong.change.entity;

import com.jeeplus.modules.sys.entity.User;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 报工修改Entity
 * @author Jin
 */
public class BusinessBaoGongChange extends DataEntity<BusinessBaoGongChange> {
	
	private static final long serialVersionUID = 1L;
	private String gbid;		// 报工ID
	private String bghid;		// 报工行ID
	private String recordid;		// 员工报工ID
	private String bgcode;		// 报工单号
	private String site;		// 工序名称
	private Double ylfnum;		// 料废数量(原)
	private Double ygfnum;		// 工废数量(原)
	private Double yfgnum;		// 返工数量(原)
	private Double yhgnum;		// 合格数量(原)
	private User ydouser;		// 实际做工人(原)
	private Double lfnum;		// 料废数量(现)
	private Double gfnum;		// 工废数量(现)
	private Double fgnum;		// 返工数量(现)
	private Double hgnum;		// 合格数量(现)
	private User douser;		// 实际做工人(现)
	public BusinessBaoGongChange() {
		super();
	}

	public BusinessBaoGongChange(String id){
		super(id);
	}

	@ExcelField(title="报工ID", align=2, sort=7)
	public String getGbid() {
		return gbid;
	}

	public void setGbid(String gbid) {
		this.gbid = gbid;
	}
	
	@ExcelField(title="报工行ID", align=2, sort=8)
	public String getBghid() {
		return bghid;
	}

	public void setBghid(String bghid) {
		this.bghid = bghid;
	}
	
	@ExcelField(title="员工报工ID", align=2, sort=9)
	public String getRecordid() {
		return recordid;
	}

	public void setRecordid(String recordid) {
		this.recordid = recordid;
	}
	
	@ExcelField(title="报工单号", align=2, sort=10)
	public String getBgcode() {
		return bgcode;
	}

	public void setBgcode(String bgcode) {
		this.bgcode = bgcode;
	}
	
	@ExcelField(title="工序名称", align=2, sort=11)
	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}
	
	@ExcelField(title="料废数量(原)", align=2, sort=12)
	public Double getYlfnum() {
		return ylfnum;
	}

	public void setYlfnum(Double ylfnum) {
		this.ylfnum = ylfnum;
	}
	
	@ExcelField(title="工废数量(原)", align=2, sort=13)
	public Double getYgfnum() {
		return ygfnum;
	}

	public void setYgfnum(Double ygfnum) {
		this.ygfnum = ygfnum;
	}
	
	@ExcelField(title="返工数量(原)", align=2, sort=14)
	public Double getYfgnum() {
		return yfgnum;
	}

	public void setYfgnum(Double yfgnum) {
		this.yfgnum = yfgnum;
	}
	
	@ExcelField(title="合格数量(原)", align=2, sort=15)
	public Double getYhgnum() {
		return yhgnum;
	}

	public void setYhgnum(Double yhgnum) {
		this.yhgnum = yhgnum;
	}
	
	@ExcelField(title="实际做工人(原)", fieldType=User.class, value="ydouser.name", align=2, sort=16)
	public User getYdouser() {
		return ydouser;
	}

	public void setYdouser(User ydouser) {
		this.ydouser = ydouser;
	}
	
	@ExcelField(title="料废数量(现)", align=2, sort=17)
	public Double getLfnum() {
		return lfnum;
	}

	public void setLfnum(Double lfnum) {
		this.lfnum = lfnum;
	}
	
	@ExcelField(title="工废数量(现)", align=2, sort=18)
	public Double getGfnum() {
		return gfnum;
	}

	public void setGfnum(Double gfnum) {
		this.gfnum = gfnum;
	}
	
	@ExcelField(title="返工数量(现)", align=2, sort=19)
	public Double getFgnum() {
		return fgnum;
	}

	public void setFgnum(Double fgnum) {
		this.fgnum = fgnum;
	}
	
	@ExcelField(title="合格数量(现)", align=2, sort=20)
	public Double getHgnum() {
		return hgnum;
	}

	public void setHgnum(Double hgnum) {
		this.hgnum = hgnum;
	}
	
	@ExcelField(title="实际做工人(现)", fieldType=User.class, value="douser.name", align=2, sort=21)
	public User getDouser() {
		return douser;
	}

	public void setDouser(User douser) {
		this.douser = douser;
	}
	
}