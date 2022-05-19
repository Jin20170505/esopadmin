/**
 *
 */
package com.jeeplus.modules.business.chuku.lingliao.entity;

import javax.validation.constraints.NotNull;
import com.jeeplus.modules.base.cangku.entity.BaseCangKu;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 材料出库单Entity
 * @author Jin
 */
public class BusinessChuKuLingLiao extends DataEntity<BusinessChuKuLingLiao> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 单号
	private String sccode;		// 生产订单号
	private String sclinecode;		// 生产行号
	private String cinvcode;		// 存货编码
	private String cinvname;		// 存货名称
	private String cinvstd;		// 规格型号
	private Double num;		// 数量
	private String unit;		// 单位
	private BaseCangKu ck;		// 仓库
	private String planid; // 计划工单id
	private String plancode; // 计划单号
	private String bgid;		// 报工ID
	private String bgcode;		// 报工单号
	private String sych;		// 是否同步
	private List<BusinessChuKuLingLiaoMx> businessChuKuLingLiaoMxList = Lists.newArrayList();		// 子表列表
	
	public BusinessChuKuLingLiao() {
		super();
	}

	public BusinessChuKuLingLiao(String id){
		super(id);
	}

	@ExcelField(title="单号", align=2, sort=6)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="生产订单号", align=2, sort=7)
	public String getSccode() {
		return sccode;
	}

	public void setSccode(String sccode) {
		this.sccode = sccode;
	}
	
	@ExcelField(title="存货编码", align=2, sort=8)
	public String getCinvcode() {
		return cinvcode;
	}

	public void setCinvcode(String cinvcode) {
		this.cinvcode = cinvcode;
	}
	
	@ExcelField(title="存货名称", align=2, sort=9)
	public String getCinvname() {
		return cinvname;
	}

	public void setCinvname(String cinvname) {
		this.cinvname = cinvname;
	}
	
	@ExcelField(title="规格型号", align=2, sort=10)
	public String getCinvstd() {
		return cinvstd;
	}

	public void setCinvstd(String cinvstd) {
		this.cinvstd = cinvstd;
	}
	
	@NotNull(message="数量不能为空")
	@ExcelField(title="数量", align=2, sort=11)
	public Double getNum() {
		return num;
	}

	public void setNum(Double num) {
		this.num = num;
	}
	
	@ExcelField(title="单位", align=2, sort=12)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@NotNull(message="仓库不能为空")
	@ExcelField(title="仓库", fieldType=BaseCangKu.class, value="ck.name", align=2, sort=13)
	public BaseCangKu getCk() {
		return ck;
	}

	public void setCk(BaseCangKu ck) {
		this.ck = ck;
	}
	
	@ExcelField(title="报工ID", align=2, sort=14)
	public String getBgid() {
		return bgid;
	}

	public void setBgid(String bgid) {
		this.bgid = bgid;
	}
	
	@ExcelField(title="报工单号", align=2, sort=15)
	public String getBgcode() {
		return bgcode;
	}

	public void setBgcode(String bgcode) {
		this.bgcode = bgcode;
	}
	
	@ExcelField(title="是否同步", align=2, sort=16)
	public String getSych() {
		return sych;
	}

	public void setSych(String sych) {
		this.sych = sych;
	}
	
	public List<BusinessChuKuLingLiaoMx> getBusinessChuKuLingLiaoMxList() {
		return businessChuKuLingLiaoMxList;
	}

	public void setBusinessChuKuLingLiaoMxList(List<BusinessChuKuLingLiaoMx> businessChuKuLingLiaoMxList) {
		this.businessChuKuLingLiaoMxList = businessChuKuLingLiaoMxList;
	}

	public String getSclinecode() {
		return sclinecode;
	}

	public BusinessChuKuLingLiao setSclinecode(String sclinecode) {
		this.sclinecode = sclinecode;
		return this;
	}

	public String getPlanid() {
		return planid;
	}

	public BusinessChuKuLingLiao setPlanid(String planid) {
		this.planid = planid;
		return this;
	}

	public String getPlancode() {
		return plancode;
	}

	public BusinessChuKuLingLiao setPlancode(String plancode) {
		this.plancode = plancode;
		return this;
	}
}