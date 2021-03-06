/**
 *
 */
package com.jeeplus.modules.sys.mapper;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.sys.entity.DataRule;
import com.jeeplus.modules.sys.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;;

/**
 * 数据权限MAPPER接口
 * @author lgf
 * @version 2017-04-02
 */
@Mapper
@Repository
public interface DataRuleMapper extends BaseMapper<DataRule> {

	public void deleteRoleDataRule(DataRule dataRule);
	
	public List<DataRule> findByUserId(User user);
}