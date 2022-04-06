/**
 *
 */
package com.jeeplus.modules.qiyewx.daka.entity;

import com.jeeplus.modules.qiyewx.base.entity.QiYeWxEmployee;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 打卡记录Entity
 * @author Jin
 * @version 2021-08-31
 */
public class QiYeWxDaKaRecord extends DataEntity<QiYeWxDaKaRecord> {
	
	private static final long serialVersionUID = 1L;
	private QiYeWxEmployee emplyee;		// 员工
	private String groupname;		// 打卡规则
	private String checkinType;		// 打卡类型
	private String exceptionType;		// 异常类型
	private Date checkinTime;		// 打卡时间
	private String locationTitle;		// 打卡地点
	private String locationDetail;		// 打卡地点详情
	private String wifiname;		// 打卡wifi名称
	private String notes;		// 打卡备注
	private String wifimac;		// 打卡的MAC地址/bssid
	private String mediaids;		// 打卡的附件media_id
	private String lat;		// 位置打卡地点纬度
	private String lng;		// 位置打卡地点经度
	private String deviceid;		// 打卡设备id
	private Date schCheckinTime;		// 标准打卡时间
	private String groupid;		// 规则id
	private String scheduleid;		// 班次id
	private String timelineid;		// 时段id
	private Date beginCheckinTime;		// 开始 打卡时间
	private Date endCheckinTime;		// 结束 打卡时间
	
	public QiYeWxDaKaRecord() {
		super();
	}

	public QiYeWxDaKaRecord(String id){
		super(id);
	}

	@ExcelField(title="员工", fieldType=QiYeWxEmployee.class, value="emplyee.name", align=2, sort=7)
	public QiYeWxEmployee getEmplyee() {
		return emplyee;
	}

	public QiYeWxDaKaRecord setEmplyee(QiYeWxEmployee emplyee) {
		this.emplyee = emplyee;
		return this;
	}
	
	@ExcelField(title="打卡规则", align=2, sort=8)
	public String getGroupname() {
		return groupname;
	}

	public QiYeWxDaKaRecord setGroupname(String groupname) {
		this.groupname = groupname;
		return this;
	}
	
	@ExcelField(title="打卡类型", align=2, sort=9)
	public String getCheckinType() {
		return checkinType;
	}

	public QiYeWxDaKaRecord setCheckinType(String checkinType) {
		this.checkinType = checkinType;
		return this;
	}
	
	@ExcelField(title="异常类型", align=2, sort=10)
	public String getExceptionType() {
		return exceptionType;
	}

	public QiYeWxDaKaRecord setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
		return this;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="打卡时间", align=2, sort=11)
	public Date getCheckinTime() {
		return checkinTime;
	}

	public QiYeWxDaKaRecord setCheckinTime(Date checkinTime) {
		this.checkinTime = checkinTime;
		return this;
	}
	
	@ExcelField(title="打卡地点", align=2, sort=12)
	public String getLocationTitle() {
		return locationTitle;
	}

	public QiYeWxDaKaRecord setLocationTitle(String locationTitle) {
		this.locationTitle = locationTitle;
		return this;
	}
	
	@ExcelField(title="打卡地点详情", align=2, sort=13)
	public String getLocationDetail() {
		return locationDetail;
	}

	public QiYeWxDaKaRecord setLocationDetail(String locationDetail) {
		this.locationDetail = locationDetail;
		return this;
	}
	
	@ExcelField(title="打卡wifi名称", align=2, sort=14)
	public String getWifiname() {
		return wifiname;
	}

	public QiYeWxDaKaRecord setWifiname(String wifiname) {
		this.wifiname = wifiname;
		return this;
	}
	
	@ExcelField(title="打卡备注", align=2, sort=15)
	public String getNotes() {
		return notes;
	}

	public QiYeWxDaKaRecord setNotes(String notes) {
		this.notes = notes;
		return this;
	}
	
	@ExcelField(title="打卡的MAC地址/bssid", align=2, sort=16)
	public String getWifimac() {
		return wifimac;
	}

	public QiYeWxDaKaRecord setWifimac(String wifimac) {
		this.wifimac = wifimac;
		return this;
	}
	
	@ExcelField(title="打卡的附件media_id", align=2, sort=17)
	public String getMediaids() {
		return mediaids;
	}

	public QiYeWxDaKaRecord setMediaids(String mediaids) {
		this.mediaids = mediaids;
		return this;
	}
	
	@ExcelField(title="位置打卡地点纬度", align=2, sort=18)
	public String getLat() {
		return lat;
	}

	public QiYeWxDaKaRecord setLat(String lat) {
		this.lat = lat;
		return this;
	}
	
	@ExcelField(title="位置打卡地点经度", align=2, sort=19)
	public String getLng() {
		return lng;
	}

	public QiYeWxDaKaRecord setLng(String lng) {
		this.lng = lng;
		return this;
	}
	
	@ExcelField(title="打卡设备id", align=2, sort=20)
	public String getDeviceid() {
		return deviceid;
	}

	public QiYeWxDaKaRecord setDeviceid(String deviceid) {
		this.deviceid = deviceid;
		return this;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="标准打卡时间", align=2, sort=21)
	public Date getSchCheckinTime() {
		return schCheckinTime;
	}

	public QiYeWxDaKaRecord setSchCheckinTime(Date schCheckinTime) {
		this.schCheckinTime = schCheckinTime;
		return this;
	}
	
	@ExcelField(title="规则id", align=2, sort=22)
	public String getGroupid() {
		return groupid;
	}

	public QiYeWxDaKaRecord setGroupid(String groupid) {
		this.groupid = groupid;
		return this;
	}
	
	@ExcelField(title="班次id", align=2, sort=23)
	public String getScheduleid() {
		return scheduleid;
	}

	public QiYeWxDaKaRecord setScheduleid(String scheduleid) {
		this.scheduleid = scheduleid;
		return this;
	}
	
	@ExcelField(title="时段id", align=2, sort=24)
	public String getTimelineid() {
		return timelineid;
	}

	public QiYeWxDaKaRecord setTimelineid(String timelineid) {
		this.timelineid = timelineid;
		return this;
	}
	
	public Date getBeginCheckinTime() {
		return beginCheckinTime;
	}

	public QiYeWxDaKaRecord setBeginCheckinTime(Date beginCheckinTime) {
		this.beginCheckinTime = beginCheckinTime;
		return this;
	}
	
	public Date getEndCheckinTime() {
		return endCheckinTime;
	}

	public QiYeWxDaKaRecord setEndCheckinTime(Date endCheckinTime) {
		this.endCheckinTime = endCheckinTime;
		return this;
	}
		
}