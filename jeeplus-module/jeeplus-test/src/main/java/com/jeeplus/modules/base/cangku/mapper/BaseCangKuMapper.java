/**
 *
 */
package com.jeeplus.modules.base.cangku.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.base.cangku.entity.BaseCangKu;

import java.util.List;

/**
 * 仓库档案MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BaseCangKuMapper extends BaseMapper<BaseCangKu> {

	List<BaseCangKu> findAllCk();

	@Select("select code from base_cangku where id = #{id}")
	String getCodeById(@Param("id") String id);
	@Select("select id from base_cangku where code = #{code}")
    String getIdByCode(@Param("code") String code);

	void batchInsert(List<BaseCangKu> list);
}