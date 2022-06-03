/**
 *
 */
package com.jeeplus.modules.base.cangku.mapper;

import com.jeeplus.modules.api.bean.ckandhw.CkBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.base.cangku.entity.BaseCkUser;

import java.util.List;

/**
 * 仓库可见人MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BaseCkUserMapper extends BaseMapper<BaseCkUser> {

    List<CkBean> findCks(@Param("userid") String userid);
}