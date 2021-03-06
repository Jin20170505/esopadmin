/**
 * Copyright &copy; 2015-2020 磐新科技 All rights reserved.
 */
package com.jeeplus.modules.oa.mapper;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.oa.entity.OaNotifyRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 通知通告记录MAPPER接口
 *
 * @author Jin
 * @version 2017-05-16
 */
@Mapper
@Repository
public interface OaNotifyRecordMapper extends BaseMapper<OaNotifyRecord> {

    /**
     * 插入通知记录
     *
     * @param oaNotifyRecordList
     * @return
     */
    public int insertAll(List<OaNotifyRecord> oaNotifyRecordList);

    /**
     * 根据通知ID删除通知记录
     *
     * @param oaNotifyId 通知ID
     * @return
     */
    public int deleteByOaNotifyId(String oaNotifyId);

}