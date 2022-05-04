/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.filemanger.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.filemanger.entity.BussinessFileManger;

/**
 * 文件档案MAPPER接口
 * @author Jin
 * @version 2022-05-04
 */
@Mapper
@Repository
public interface BussinessFileMangerMapper extends BaseMapper<BussinessFileManger> {
	
}