/**
 * 
 */
package com.jeeplus.modules.business.baogong.order.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.baogong.order.entity.BusinessBaoGongOrder;

import java.util.List;

/**
 * 报工单MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessBaoGongOrderMapper extends BaseMapper<BusinessBaoGongOrder> {

    BusinessBaoGongOrder getByCode(@Param("code") String code);

    @Select("select 1 from business_baogao_order where planid = #{planid}")
    Integer hasScOrderFromPlan(@Param("planid") String planid);

    @Select("select qrcode from business_baogao_order where id=#{id}")
    String getQrCodeById(@Param("id") String id);

    @Select("select num from business_baogao_order where id=#{id}")
    Double getNum(@Param("id") String id);

    @Update("update business_baogao_order set complate = '1' where id = #{id}")
    void completeBg(@Param("id") String id);
    @Update("update business_baogao_order set complate = '0' where id = #{id}")
    void uncompleteBg(@Param("id") String id);
    @Update("update business_baogao_order set isprint = '已打印' where id = #{id}")
    void print(@Param("id") String id);

    @Select("select complete from business_baogong_order_mingxi where pid = #{bgid}")
    List<String> findCompleteStatusByBgid(@Param("bgid") String bgid);

    @Update("update business_baogao_order set complate = '0' where id = #{id}")
    void restOrder(@Param("id") String id);
    @Update("update business_baogong_order_mingxi set complete = '0' where pid = #{bgid}")
    void restOrderMx(@Param("bgid") String bgid);

    @Select("select max(bgcode) from business_baogao_order where bgcode like concat('BGD',#{ymd},'%')")
    String getMaxCode(@Param("ymd") String ymd);

    @Select("SELECT sum(a.num - IFNULL(b.hgnum,0)) FROM (SELECT a.id,max(a.no) as maxno,a.num,a.pid FROM business_baogong_order_mingxi a LEFT JOIN business_baogao_order b on a.pid = b.id where b.orderlineid = #{schid} GROUP BY a.pid,a.id " +
            ") a LEFT JOIN business_baogong_record b on a.id = b.bghid")
    Double getNoDoneNumBySchid(@Param("schid") String schid);

    @Select("select id from business_shengchan_dingdan_mingxi where cinv_code =#{cinvcode} and status='开立'")
    List<String> getSchidByCinvcode(@Param("cinvcode") String cinvcode);

    @Select("select orderlineid from business_baogao_order where id=#{orderid}")
    String getSchidByOrderid(@Param("orderid") String orderid);

    @Select("select planid from business_baogao_order where id=#{orderid}")
    String getPlanidByOrderid(@Param("orderid") String orderid);

    @Select("select id from business_baogao_order where planid = #{planid}")
    List<String> findBgidByPlanid(@Param("planid") String planid);
    @Update("update business_baogao_order set  remarks = #{remarks} where id = #{rid}")
    void updateRemarks(@Param("rid") String rid,@Param("remarks") String remarks);

}