/**
 * Copyright &copy; 2015-2020 磐新科技 All rights reserved.
 */
package com.jeeplus.modules.tools.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.tools.entity.TestInterface;
import com.jeeplus.modules.tools.mapper.TestInterfaceMapper;

/**
 * 接口Service
 *
 * @author lgf
 * @version 2016-01-07
 */
@Service
@Transactional(readOnly = true)
public class TestInterfaceService extends CrudService<TestInterfaceMapper, TestInterface> {

    public TestInterface get(String id) {
        return super.get(id);
    }

    public List<TestInterface> findList(TestInterface testInterface) {
        return super.findList(testInterface);
    }

    public Page<TestInterface> findPage(Page<TestInterface> page, TestInterface testInterface) {
        return super.findPage(page, testInterface);
    }

    @Transactional(readOnly = false)
    public void save(TestInterface testInterface) {
        super.save(testInterface);
    }

    @Transactional(readOnly = false)
    public void delete(TestInterface testInterface) {
        super.delete(testInterface);
    }

}