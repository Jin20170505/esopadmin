/**
 *
 */
package com.jeeplus.modules.business.filemanger.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 文件档案Entity
 * @author Jin
 */
public class BussinessFileManger extends DataEntity<BussinessFileManger> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String url;		// 地址
	private String type;		// 类型
	private String path;		// 物理地址
	private String filename;		// 文件名
	private Integer version;		// 版本
	private String status;		// 状态
	
	public BussinessFileManger() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public BussinessFileManger(String id){
		super(id);
	}

	@ExcelField(title="名称", align=2, sort=6)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="地址", align=2, sort=7)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@ExcelField(title="类型", align=2, sort=8)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@ExcelField(title="物理地址", align=2, sort=9)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	@ExcelField(title="文件名", align=2, sort=10)
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	// @ExcelField(title="版本", align=2, sort=11)
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	// @ExcelField(title="状态", align=2, sort=12)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}