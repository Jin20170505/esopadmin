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
	@Select("select 1 from business_ruku_product where bgid = #{bgid} limit 1")
	Integer hasByBgid(@Param("bgid")String bgid);

	@Select("select max(code) from business_ruku_product where code like concat('CPRK',#{ymd},'%')")
	String getMaxCode(@Param("ymd") String ymd);

	@Select("select 1 from business_ruku_product where sccode = #{sccode} limit 1")
	Integer hasBySccode(@Param("sccode") String sccode);
}