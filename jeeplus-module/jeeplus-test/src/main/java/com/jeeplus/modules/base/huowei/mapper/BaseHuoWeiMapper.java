/**
 *
 */
package com.jeeplus.modules.base.huowei.mapper;

import com.jeeplus.modules.api.bean.ckandhw.HwBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.base.huowei.entity.BaseHuoWei;

import java.util.List;

/**
 * 货位档案MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BaseHuoWeiMapper extends BaseMapper<BaseHuoWei> {

    List<HwBean> findHwItem(@Param("ckid") String ckid);
}