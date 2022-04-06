/**
 * Copyright &copy; 2015-2020 磐新科技 All rights reserved.
 */
package com.jeeplus.modules.dashboard.service;

import java.util.List;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.dashboard.entity.Container;
import com.jeeplus.modules.dashboard.mapper.ContainerMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 容器Service
 *
 * @author 刘高峰
 * @version 2018-09-11
 */
@Service
@Transactional(readOnly = true)
public class ContainerService extends CrudService<ContainerMapper, Container> {

    public Container get(String id) {
        return super.get(id);
    }

    public List<Container> findList(Container container) {
        return super.findList(container);
    }

    public Page findPage(Page page, Container container) {
        return super.findPage(page, container);
    }

    @Transactional(readOnly = false)
    public void save(Container container) {
        super.save(container);
    }

    @Transactional(readOnly = false)
    public void delete(Container container) {
        super.delete(container);
    }

}
