package com.jeeplus.modules.qiyewx.daka.month.entity;

/**
 * @Auther: Jin
 * @Date: 2021/9/9
 * @Description:
 */
public class KaoQin {
    /** 应出勤天数 */
    private Double yingchuqindays;
    /** 实际出勤天数 */
    private Double shijichuqindays;
    /** 缺勤天数 */
    private Double queqindays;
    /** 病假天数 */
    private Double bingjiadays;
    /** 出差天数 */
    private Double chuchaidays;
    /** 工作日加班天数 */
    private Double workOverDays;
    /** 节假日加班天数 */
    private Double holidayOverDays;
    /** 休息日加班天数 */
    private Double restOverDays;
    /** 调休 */
    private Double tiaoxiuDays;
    /** 年休 */
    private Double nianxiuDays;
    /** 事假 */
    private Double shijiaDay;
    /** 婚假 */
    private Double hunjiaDay;
    /** 丧假 */
    private Double sangjiaDay;


    public Double getYingchuqindays() {
        return yingchuqindays;
    }

    public KaoQin setYingchuqindays(Double yingchuqindays) {
        this.yingchuqindays = yingchuqindays;
        return this;
    }

    public Double getShijichuqindays() {
        return shijichuqindays;
    }

    public KaoQin setShijichuqindays(Double shijichuqindays) {
        this.shijichuqindays = shijichuqindays;
        return this;
    }

    public Double getQueqindays() {
        return queqindays;
    }

    public KaoQin setQueqindays(Double queqindays) {
        this.queqindays = queqindays;
        return this;
    }

    public Double getBingjiadays() {
        return bingjiadays;
    }

    public KaoQin setBingjiadays(Double bingjiadays) {
        this.bingjiadays = bingjiadays;
        return this;
    }

    public Double getChuchaidays() {
        return chuchaidays;
    }

    public KaoQin setChuchaidays(Double chuchaidays) {
        this.chuchaidays = chuchaidays;
        return this;
    }

    public Double getWorkOverDays() {
        return workOverDays;
    }

    public KaoQin setWorkOverDays(Double workOverDays) {
        this.workOverDays = workOverDays;
        return this;
    }

    public Double getHolidayOverDays() {
        return holidayOverDays;
    }

    public KaoQin setHolidayOverDays(Double holidayOverDays) {
        this.holidayOverDays = holidayOverDays;
        return this;
    }

    public Double getRestOverDays() {
        return restOverDays;
    }

    public KaoQin setRestOverDays(Double restOverDays) {
        this.restOverDays = restOverDays;
        return this;
    }

    public Double getTiaoxiuDays() {
        return tiaoxiuDays;
    }

    public KaoQin setTiaoxiuDays(Double tiaoxiuDays) {
        this.tiaoxiuDays = tiaoxiuDays;
        return this;
    }

    public Double getNianxiuDays() {
        return nianxiuDays;
    }

    public KaoQin setNianxiuDays(Double nianxiuDays) {
        this.nianxiuDays = nianxiuDays;
        return this;
    }

    public Double getShijiaDay() {
        return shijiaDay;
    }

    public KaoQin setShijiaDay(Double shijiaDay) {
        this.shijiaDay = shijiaDay;
        return this;
    }

    public Double getHunjiaDay() {
        return hunjiaDay;
    }

    public KaoQin setHunjiaDay(Double hunjiaDay) {
        this.hunjiaDay = hunjiaDay;
        return this;
    }

    public Double getSangjiaDay() {
        return sangjiaDay;
    }

    public KaoQin setSangjiaDay(Double sangjiaDay) {
        this.sangjiaDay = sangjiaDay;
        return this;
    }

    public Double getJiaBanDay(){
        return workOverDays+holidayOverDays+restOverDays;
    }
}
