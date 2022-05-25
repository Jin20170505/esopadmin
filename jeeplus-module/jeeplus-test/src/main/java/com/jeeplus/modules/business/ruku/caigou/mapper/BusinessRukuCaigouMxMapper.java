/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.ruku.caigou.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.ruku.caigou.entity.BusinessRukuCaigouMx;

/**
 * 采购入库明细MAPPER接口
 * @author Jin
 * @version 2022-05-24
 */
@Mapper
@Repository
public interface BusinessRukuCaigouMxMapper extends BaseMapper<BusinessRukuCaigouMx> {

    @Select("select sum(num) from business_ruku_caigou_mx where cghid = #{cghid}")
    Double sumRukuNumByCghid(String cghid);
}