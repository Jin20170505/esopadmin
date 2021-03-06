/**
 *
 */
package com.jeeplus.modules.business.check.ipqc.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.google.common.collect.Lists;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * IPQC检验Entity
 * @author Jin
 */
public class BusinessCheckIPQC extends DataEntity<BusinessCheckIPQC> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 单号
	private String sccode;		// 生产订单号
	private String linecode;		// 行号
	private String username;		// 工号
	private String cinvcode; // 存货编码
	private String cinvname;	// 存货名称
	private String bgid; // 报工ID
	private String bgcode; // 报工单号
	private String bghid; // 报工行ID
	private String dept;		// 生产部门ID
	private String deptName;	// 生产部门名称
	private String siteid; // 工序ID
	private String sitename; // 工序名
	private Double bzhglv; // 标准合格率
	private String checkname;		// 质检人
	private Date checkdate;		// 质检日期
	private Double checknum;		// 检验数量
	private Double hegenum;		// 合格数量
	private Double nohegenum;		// 不合格数量
	private Double badnum;		// 不良品数量
	private Double hglv;	// 合格率
	private Date beginCheckdate;		// 开始 质检日期
	private Date endCheckdate;		// 结束 质检日期
	private List<BusinessCheckIPQCFile> businessCheckIPQCFileList = Lists.newArrayList();

	public List<BusinessCheckIPQCFile> getBusinessCheckIPQCFileList() {
		return businessCheckIPQCFileList;
	}

	public BusinessCheckIPQC setBusinessCheckIPQCFileList(List<BusinessCheckIPQCFile> businessCheckIPQCFileList) {
		this.businessCheckIPQCFileList = businessCheckIPQCFileList;
		return this;
	}

	public BusinessCheckIPQC() {
		super();
	}

	public BusinessCheckIPQC(String id){
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
	
	@ExcelField(title="行号", align=2, sort=8)
	public String getLinecode() {
		return linecode;
	}

	public void setLinecode(String linecode) {
		this.linecode = linecode;
	}
	
	@ExcelField(title="工号", align=2, sort=9)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@ExcelField(title="质检人", align=2, sort=10)
	public String getCheckname() {
		return checkname;
	}

	public void setCheckname(String checkname) {
		this.checkname = checkname;
	}

	public String getCinvcode() {
		return cinvcode;
	}

	public BusinessCheckIPQC setCinvcode(String cinvcode) {
		this.cinvcode = cinvcode;
		return this;
	}

	public String getCinvname() {
		return cinvname;
	}

	public BusinessCheckIPQC setCinvname(String cinvname) {
		this.cinvname = cinvname;
		return this;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="质检日期不能为空")
	@ExcelField(title="质检日期", align=2, sort=11)
	public Date getCheckdate() {
		return checkdate;
	}

	public void setCheckdate(Date checkdate) {
		this.checkdate = checkdate;
	}
	
	@NotNull(message="检验数量不能为空")
	@ExcelField(title="检验数量", align=2, sort=12)
	public Double getChecknum() {
		return checknum;
	}

	public void setChecknum(Double checknum) {
		this.checknum = checknum;
	}
	
	@NotNull(message="合格数量不能为空")
	@ExcelField(title="合格数量", align=2, sort=13)
	public Double getHegenum() {
		return hegenum;
	}

	public void setHegenum(Double hegenum) {
		this.hegenum = hegenum;
	}
	
	@ExcelField(title="不合格数量", align=2, sort=14)
	public Double getNohegenum() {
		return nohegenum;
	}

	public void setNohegenum(Double nohegenum) {
		this.nohegenum = nohegenum;
	}
	
	@ExcelField(title="不良品数量", align=2, sort=15)
	public Double getBadnum() {
		return badnum;
	}

	public void setBadnum(Double badnum) {
		this.badnum = badnum;
	}
	
	public Date getBeginCheckdate() {
		return beginCheckdate;
	}

	public void setBeginCheckdate(Date beginCheckdate) {
		this.beginCheckdate = beginCheckdate;
	}
	
	public Date getEndCheckdate() {
		return endCheckdate;
	}

	public void setEndCheckdate(Date endCheckdate) {
		this.endCheckdate = endCheckdate;
	}

	public Double getHglv() {
		if(hegenum==null||checknum==null||checknum<0.00001){
			hglv=0.00;
		}else {
			hglv = hegenum/checknum;
		}
		return hglv;
	}

	public BusinessCheckIPQC setHglv(Double hglv) {
		this.hglv = hglv;
		return this;
	}

	public String getBgid() {
		return bgid;
	}

	public BusinessCheckIPQC setBgid(String bgid) {
		this.bgid = bgid;
		return this;
	}

	public String getBgcode() {
		return bgcode;
	}

	public BusinessCheckIPQC setBgcode(String bgcode) {
		this.bgcode = bgcode;
		return this;
	}

	public String getBghid() {
		return bghid;
	}

	public BusinessCheckIPQC setBghid(String bghid) {
		this.bghid = bghid;
		return this;
	}

	public String getSiteid() {
		return siteid;
	}

	public BusinessCheckIPQC setSiteid(String siteid) {
		this.siteid = siteid;
		return this;
	}

	public String getSitename() {
		return sitename;
	}

	public BusinessCheckIPQC setSitename(String sitename) {
		this.sitename = sitename;
		return this;
	}

	public Double getBzhglv() {
		return bzhglv;
	}

	public BusinessCheckIPQC setBzhglv(Double bzhglv) {
		this.bzhglv = bzhglv;
		return this;
	}

	public String getDept() {
		return dept;
	}

	public BusinessCheckIPQC setDept(String dept) {
		this.dept = dept;
		return this;
	}

	public String getDeptName() {
		return deptName;
	}

	public BusinessCheckIPQC setDeptName(String deptName) {
		this.deptName = deptName;
		return this;
	}
}