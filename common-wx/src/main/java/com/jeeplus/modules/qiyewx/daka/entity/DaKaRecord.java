package com.jeeplus.modules.qiyewx.daka.entity;

/**
 * @Auther: Jin
 * @Date: 2021/8/27
 * @Description:
 */
public class DaKaRecord {
    private String userid;
    private String groupname;
    private String checkin_type;
    private String exception_type;
    private Long checkin_time;
    private String location_title;
    private String location_detail;
    private String wifiname;
    private String notes;
    private String wifimac;
    private String mediaids;
    private Long lat;
    private Long lng;
    private String deviceid;
    private Long sch_checkin_time;
    private Integer groupid;
    private Integer schedule_id;
    private String timeline_id;

    public String getUserid() {
        return userid;
    }

    public DaKaRecord setUserid(String userid) {
        this.userid = userid;
        return this;
    }

    public String getGroupname() {
        return groupname;
    }

    public DaKaRecord setGroupname(String groupname) {
        this.groupname = groupname;
        return this;
    }

    public String getCheckin_type() {
        return checkin_type;
    }

    public DaKaRecord setCheckin_type(String checkin_type) {
        this.checkin_type = checkin_type;
        return this;
    }

    public String getException_type() {
        return exception_type;
    }

    public DaKaRecord setException_type(String exception_type) {
        this.exception_type = exception_type;
        return this;
    }

    public Long getCheckin_time() {
        return checkin_time;
    }

    public DaKaRecord setCheckin_time(Long checkin_time) {
        this.checkin_time = checkin_time;
        return this;
    }

    public String getLocation_title() {
        return location_title;
    }

    public DaKaRecord setLocation_title(String location_title) {
        this.location_title = location_title;
        return this;
    }

    public String getLocation_detail() {
        return location_detail;
    }

    public DaKaRecord setLocation_detail(String location_detail) {
        this.location_detail = location_detail;
        return this;
    }

    public String getWifiname() {
        return wifiname;
    }

    public DaKaRecord setWifiname(String wifiname) {
        this.wifiname = wifiname;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public DaKaRecord setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public String getWifimac() {
        return wifimac;
    }

    public DaKaRecord setWifimac(String wifimac) {
        this.wifimac = wifimac;
        return this;
    }

    public String getMediaids() {
        return mediaids;
    }

    public DaKaRecord setMediaids(String mediaids) {
        this.mediaids = mediaids;
        return this;
    }

    public Long getLat() {
        return lat;
    }

    public DaKaRecord setLat(Long lat) {
        this.lat = lat;
        return this;
    }

    public Long getLng() {
        return lng;
    }

    public DaKaRecord setLng(Long lng) {
        this.lng = lng;
        return this;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public DaKaRecord setDeviceid(String deviceid) {
        this.deviceid = deviceid;
        return this;
    }

    public Long getSch_checkin_time() {
        return sch_checkin_time;
    }

    public DaKaRecord setSch_checkin_time(Long sch_checkin_time) {
        this.sch_checkin_time = sch_checkin_time;
        return this;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public DaKaRecord setGroupid(Integer groupid) {
        this.groupid = groupid;
        return this;
    }

    public Integer getSchedule_id() {
        return schedule_id;
    }

    public DaKaRecord setSchedule_id(Integer schedule_id) {
        this.schedule_id = schedule_id;
        return this;
    }

    public String getTimeline_id() {
        return timeline_id;
    }

    public DaKaRecord setTimeline_id(String timeline_id) {
        this.timeline_id = timeline_id;
        return this;
    }

    @Override
    public String toString() {
        return "DaKaRecord{" +
                "userid='" + userid + '\'' +
                ", groupname='" + groupname + '\'' +
                ", checkin_type='" + checkin_type + '\'' +
                ", exception_type='" + exception_type + '\'' +
                ", checkin_time=" + checkin_time +
                ", location_title='" + location_title + '\'' +
                ", location_detail='" + location_detail + '\'' +
                ", wifiname='" + wifiname + '\'' +
                ", notes='" + notes + '\'' +
                ", wifimac='" + wifimac + '\'' +
                ", mediaids='" + mediaids + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", deviceid='" + deviceid + '\'' +
                ", sch_checkin_time=" + sch_checkin_time +
                ", groupid=" + groupid +
                ", schedule_id=" + schedule_id +
                ", timeline_id=" + timeline_id +
                '}';
    }
}
