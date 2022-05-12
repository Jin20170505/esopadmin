/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.baogong.order.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.baogong.order.entity.BusinessBaoGongOrderMingXi;

/**
 * 报工明细MAPPER接口
 * @author Jin
 * @version 2022-05-06
 */
@Mapper
@Repository
public interface BusinessBaoGongOrderMingXiMapper extends BaseMapper<BusinessBaoGongOrderMingXi> {

    @Update("update business_baogong_order_mingxi set complete = '1' where id = #{id}")
    void completeBg(@Param("id") String id);
}