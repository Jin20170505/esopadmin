/**
 * 
 */
package com.jeeplus.modules.business.chuku.lingliao.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.chuku.lingliao.entity.BusinessChuKuLingLiao;

/**
 * 材料出库单MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessChuKuLingLiaoMapper extends BaseMapper<BusinessChuKuLingLiao> {

    @Select("select 1 from business_chuku_lingliao where bgid = #{bgid} limit 1")
	Integer isDoneLingLiao(@Param("bgid") String bgid);

    @Select("select max(code) from business_chuku_lingliao where code like concat('LLD',#{ymd},'%')")
    String getMaxCode(@Param("ymd") String ymd);
}