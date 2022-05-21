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

    @Update("update business_baogao_order set complate = '1' where id = #{id}")
    void completeBg(@Param("id") String id);

    @Update("update business_baogao_order set isprint = '已打印' where id = #{id}")
    void print(@Param("id") String id);

    @Select("select complete from business_baogong_order_mingxi where pid = #{bgid}")
    List<String> findCompleteStatusByBgid(@Param("bgid") String bgid);
}