/**
 * 
 */
package com.jeeplus.modules.business.ruku.product.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.ruku.product.entity.BusinessRuKuProduct;

/**
 * 产成品入库MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessRuKuProductMapper extends BaseMapper<BusinessRuKuProduct> {
	@Select("select sum(num) from business_ruku_product where bgid = #{bgid}")
    Double getRuKuNumByBgid(@Param("bgid") String bgid);
	
	String getMaxBatchno(@Param("ymd") String yyyymmdd);
}