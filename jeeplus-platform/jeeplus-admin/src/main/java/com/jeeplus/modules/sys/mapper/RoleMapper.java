/**
 *
 */
package com.jeeplus.modules.sys.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Date;

import javax.annotation.Resource;

/**
 * 角色MAPPER接口
 * @author Jin
 * @version 2016-12-05
 */
@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {

	public Role getByName(Role role);
	
	public Role getByEnname(Role role);

	/**
	 * 维护角色与菜单权限关系
	 * @param role
	 * @return
	 */
	public int deleteRoleMenu(Role role);

	public int insertRoleMenu(Role role);
	
	/**
	 * 维护角色与数据权限关系
	 * @param role
	 * @return
	 */
	public int deleteRoleDataRule(Role role);

	public int insertRoleDataRule(Role role);
	
	 @Select("select  o.id, o.name from sys_role a LEFT JOIN sys_office o ON o.id = a.office_id where a.id = #{id}")
	 Office getOfficeid(@Param("id") String id);

}
