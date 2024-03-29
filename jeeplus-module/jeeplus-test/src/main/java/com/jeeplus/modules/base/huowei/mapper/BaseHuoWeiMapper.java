/**
 *
 */
package com.jeeplus.modules.base.huowei.mapper;

import com.jeeplus.modules.api.bean.ckandhw.HwBean;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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

    @Select("select code from base_huowei where id = #{id}")
    String getCodeById(@Param("id") String id);
    @Select("select cangku from base_huowei where id = #{hwid}")
    String getckofhw(@Param("hwid") String hwid);

    @Select("select code,name from base_huowei where id = #{id}")
    BaseHuoWei getInfoById(@Param("id") String id);

    void batchInsert(List<BaseHuoWei> list);
}