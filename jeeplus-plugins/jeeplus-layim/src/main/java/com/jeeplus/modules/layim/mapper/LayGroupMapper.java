/**
 * Copyright &copy; 2015-2020 磐新科技 All rights reserved.
 */
package com.jeeplus.modules.layim.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.layim.entity.LayGroup;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 群组MAPPER接口
 *
 * @author lgf
 * @version 2016-08-07
 */
@Mapper
@Repository
public interface LayGroupMapper extends BaseMapper<LayGroup> {


}