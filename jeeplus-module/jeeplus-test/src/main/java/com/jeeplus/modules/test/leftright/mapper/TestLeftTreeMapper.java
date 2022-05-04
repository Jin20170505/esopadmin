/**
 *
 */
package com.jeeplus.modules.test.leftright.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.TreeMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.test.leftright.entity.TestLeftTree;

/**
 * 左树右表MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface TestLeftTreeMapper extends TreeMapper<TestLeftTree> {
	
}