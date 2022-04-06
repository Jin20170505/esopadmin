/**
 * Copyright &copy; 2015-2020 磐新科技 All rights reserved.
 */
package com.jeeplus.modules.dashboard.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.dashboard.entity.Widget;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Mapper;

/**
 * 图表组件MAPPER接口
 *
 * @author 刘高峰
 * @version 2018-08-13
 */
@Mapper
@Repository
public interface WidgetMapper extends BaseMapper<Widget> {

    public  void deleteNoUse();

}
