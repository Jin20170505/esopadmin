/**
 *
 */
package com.jeeplus.modules.sys.mapper;

import java.util.List;

import com.jeeplus.core.persistence.TreeMapper;
import com.jeeplus.modules.sys.entity.Office;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;;

/**
 * 机构MAPPER接口
 * @author Jin
 * @version 2017-05-16
 */
@Mapper
@Repository
public interface OfficeMapper extends TreeMapper<Office> {
	
	Office getByCode(String code);

	@Delete("delete from sys_office")
	void deleteAll();

	@Update("insert into sys_office (id,code,name,parent_id,sort,type,grade,USEABLE,del_flag) select id,id as code,name,parent_id,sort,'2' as type,'2' as grade,'1' as USEABLE,'0' as del_flag from qiyewx_dept where del_flag='0'")
	void synchWxData();
}
