/**
 *
 */
package com.jeeplus.modules.qiyewx.base.mapper;

import com.jeeplus.modules.qiyewx.base.entity.QiYeWxTagUserid;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.qiyewx.base.entity.QiYeWxTag;

import java.util.List;

/**
 * 微信标签MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface QiYeWxTagMapper extends BaseMapper<QiYeWxTag> {

    void batchInsert(List<QiYeWxTag> list);

    @Delete("delete from qiyewx_tag")
    void deleteAll();

    void batchInsertTagUser(List<QiYeWxTagUserid> list);

    @Delete("delete from qiyewx_tag_userid")
    void deleteAllTagUserid();
    @Select("select id from qiyewx_tag")
    List<String> findAllTags();

    List<QiYeWxTag> findTagUser();

    void updateUserTagBatch(List<QiYeWxTag> list);
}