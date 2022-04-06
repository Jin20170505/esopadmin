/**
 * Copyright &copy; 2015-2020 磐新科技 All rights reserved.
 */
package com.jeeplus.modules.layim.mapper;


import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.layim.entity.ChatHistory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 聊天记录MAPPER接口
 *
 * @author Jin
 * @version 2015-12-29
 */
@Mapper
@Repository
public interface ChatHistoryMapper extends BaseMapper<ChatHistory> {


    /**
     * 查询列表数据
     *
     * @param entity
     * @return
     */
    public List<ChatHistory> findLogList(ChatHistory entity);


    /**
     * 查询群组列表数据
     *
     * @param entity
     * @return
     */
    public List<ChatHistory> findGroupLogList(ChatHistory entity);

    public int findUnReadCount(ChatHistory chatHistory);

}