/**
 * Copyright &copy; 2015-2020 磐新科技 All rights reserved.
 */
package com.jeeplus.modules.dashboard.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.dashboard.entity.Container;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Mapper;

/**
 * 容器MAPPER接口
 *
 * @author 刘高峰
 * @version 2018-09-12
 */
@Mapper
@Repository
public interface ContainerMapper extends BaseMapper<Container> {

}
