/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.baogong.order.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 报工明细Entity
 * @author Jin
 * @version 2022-05-06
 */
public class BusinessBaoGongOrderMingXi extends DataEntity<BusinessBaoGongOrderMingXi> {
	
	private static final long serialVersionUID = 1L;
	private Integer no;		// 序号
	private String site;		// 工作站
	private String opname;		// 操作人
	private String opcode;		// 员工编号
	private Double num;		// 加工数量
	private String complete;		// 是否完成
	private String qrcode;		// 二维码
	private String classgroup;	// 班组
	private BusinessBaoGongOrder pid;		// 报工单 父类
	
	public BusinessBaoGongOrderMingXi() {
		super();
	}

	public BusinessBaoGongOrderMingXi(String id){
		super(id);
	}

	public BusinessBaoGongOrderMingXi(BusinessBaoGongOrder pid){
		this.pid = pid;
	}

	@ExcelField(title="序号", align=2, sort=7)
	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}
	
	@ExcelField(title="工作站", align=2, sort=8)
	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		if(site==null){
			site ="";
		}
		this.site = site;
	}
	
	@ExcelField(title="操作人", align=2, sort=9)
	public String getOpname() {
		return opname;
	}

	public void setOpname(String opname) {
		if(opname==null){
			opname ="";
		}
		this.opname = opname;
	}
	
	@ExcelField(title="员工编号", align=2, sort=10)
	public String getOpcode() {
		return opcode;
	}

	public void setOpcode(String opcode) {
		if(opcode==null){
			opcode ="";
		}
		this.opcode = opcode;
	}
	
	@ExcelField(title="加工数量", align=2, sort=11)
	public Double getNum() {
		return num;
	}

	public void setNum(Double num) {
		this.num = num;
	}
	
	@ExcelField(title="是否完成", align=2, sort=12)
	public String getComplete() {
		return complete;
	}

	public void setComplete(String complete) {
		this.complete = complete;
	}
	
	@ExcelField(title="二维码", align=2, sort=13)
	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
	
	public BusinessBaoGongOrder getPid() {
		return pid;
	}

	public void setPid(BusinessBaoGongOrder pid) {
		this.pid = pid;
	}

	public String getClassgroup() {
		return classgroup;
	}

	public BusinessBaoGongOrderMingXi setClassgroup(String classgroup) {
		if(classgroup==null){
			classgroup ="";
		}
		this.classgroup = classgroup;
		return this;
	}
}