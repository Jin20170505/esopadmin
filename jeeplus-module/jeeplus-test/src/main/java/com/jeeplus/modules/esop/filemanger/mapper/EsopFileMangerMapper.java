/**
 *
 */
package com.jeeplus.modules.esop.filemanger.mapper;

import com.jeeplus.modules.esop.filemanger.entity.FileBean;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.esop.filemanger.entity.EsopFileManger;

import java.util.List;

/**
 * ESOP文件管理MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface EsopFileMangerMapper extends BaseMapper<EsopFileManger> {

    void updateStatus(@Param("id") String id,@Param("status") String stats);

    List<FileBean> findFiles(@Param("name") String name,@Param("size") int size,@Param("from") int from);

    int countFiles(@Param("name") String name);

    @Select("select url from esop_file_manger where id = #{id}")
    String getUrl(@Param("id") String id);
}