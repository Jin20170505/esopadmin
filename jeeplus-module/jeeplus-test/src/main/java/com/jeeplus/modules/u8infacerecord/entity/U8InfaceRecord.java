/**
 *
 */
package com.jeeplus.modules.u8infacerecord.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * ERP接口记录Entity
 * @author Jin
 */
public class U8InfaceRecord extends DataEntity<U8InfaceRecord> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 接口名
	private String url;		// 接口地址
	private String params;		// 参数
	private String result;		// 结果
	
	public U8InfaceRecord() {
		super();
	}

	public U8InfaceRecord(String id){
		super(id);
	}

	@ExcelField(title="接口名", align=2, sort=3)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="接口地址", align=2, sort=4)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@ExcelField(title="参数", align=2, sort=5)
	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}
	
	@ExcelField(title="结果", align=2, sort=6)
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}