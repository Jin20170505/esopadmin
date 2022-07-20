/**
 *
 */
package com.jeeplus.modules.base.huowei.entity;

import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.base.cangku.entity.BaseCangKu;
import javax.validation.constraints.NotNull;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 货位档案Entity
 * @author Jin
 */
public class BaseHuoWei extends DataEntity<BaseHuoWei> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 货位编码
	private String name;		// 货位名称
	private BaseCangKu ck;		// 所属仓库
	private Double maxv;		// 最大体积
	private Double maxw;		// 最大重量
	
	public BaseHuoWei() {
		super();
	}

	public BaseHuoWei(String id){
		super(id);
	}

	@ExcelField(title="货位编码", align=2, sort=7)
	public String getCode() {
		if(StringUtils.isEmpty(code)){
			code = "";
		}
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="货位名称", align=2, sort=8)
	public String getName() {
		if(StringUtils.isEmpty(name)){
			name = "";
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="所属仓库不能为空")
	@ExcelField(title="所属仓库", fieldType=BaseCangKu.class, value="ck.name", align=2, sort=9)
	public BaseCangKu getCk() {
		return ck;
	}

	public void setCk(BaseCangKu ck) {
		this.ck = ck;
	}
	
	@ExcelField(title="最大体积", align=2, sort=10)
	public Double getMaxv() {
		return maxv;
	}

	public void setMaxv(Double maxv) {
		this.maxv = maxv;
	}
	
	@ExcelField(title="最大重量", align=2, sort=11)
	public Double getMaxw() {
		return maxw;
	}

	public void setMaxw(Double maxw) {
		this.maxw = maxw;
	}
	
}