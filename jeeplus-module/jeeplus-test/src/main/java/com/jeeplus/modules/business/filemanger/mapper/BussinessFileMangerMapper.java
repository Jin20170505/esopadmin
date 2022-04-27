/**
 *
 */
package com.jeeplus.modules.business.filemanger.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.filemanger.entity.BussinessFileManger;

/**
 * 文件档案MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BussinessFileMangerMapper extends BaseMapper<BussinessFileManger> {

    @Update("update business_file_manger set status = #{status} where id = #{id}")
    void updatestatus(@Param("id") String id, @Param("status") String status);

    @Select("select path from business_file_manger where id = #{id}")
    String getFilePath(@Param("id") String id);
}