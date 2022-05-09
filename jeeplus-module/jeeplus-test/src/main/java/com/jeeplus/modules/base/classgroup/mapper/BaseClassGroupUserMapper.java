/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.base.classgroup.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.base.classgroup.entity.BaseClassGroupUser;

/**
 * 班组成员MAPPER接口
 * @author Jin
 * @version 2022-05-09
 */
@Mapper
@Repository
public interface BaseClassGroupUserMapper extends BaseMapper<BaseClassGroupUser> {
	
}