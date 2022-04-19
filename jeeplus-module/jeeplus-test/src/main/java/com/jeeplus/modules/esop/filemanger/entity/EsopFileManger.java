/**
 *
 */
package com.jeeplus.modules.esop.filemanger.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * ESOP文件管理Entity
 * @author Jin
 */
public class EsopFileManger extends DataEntity<EsopFileManger> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 文件编号
	private String name;		// 文件名称
	private String url;		// 文件
	private String status;		// 状态
	
	public EsopFileManger() {
		super();
	}

	public EsopFileManger(String id){
		super(id);
	}

	@ExcelField(title="文件编号", align=2, sort=6)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="文件名称", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="文件", align=2, sort=8)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@ExcelField(title="状态", align=2, sort=9)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}