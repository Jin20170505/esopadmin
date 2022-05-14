/**
 *
 */
package com.jeeplus.modules.base.cangku.mapper;

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
}