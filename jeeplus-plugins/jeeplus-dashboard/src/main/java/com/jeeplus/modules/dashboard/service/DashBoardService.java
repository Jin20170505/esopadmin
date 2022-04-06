/**
 * Copyright &copy; 2015-2020 磐新科技 All rights reserved.
 */
package com.jeeplus.modules.dashboard.service;

import java.util.List;

import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.dashboard.entity.Container;
import com.jeeplus.modules.dashboard.entity.DashBoard;
import com.jeeplus.modules.dashboard.mapper.ContainerMapper;
import com.jeeplus.modules.dashboard.mapper.DashBoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 仪表盘Service
 *
 * @author 刘高峰
 * @version 2018-09-12
 */
@Service
@Transactional(readOnly = true)
public class DashBoardService extends CrudService<DashBoardMapper, DashBoard> {

    @Autowired
    private ContainerMapper containerMapper;

    public DashBoard get(String id) {
        return getD(super.get(id));
    }
    public DashBoard getD(DashBoard dashBoard) {
        dashBoard.setContainerList(containerMapper.findList(new Container(dashBoard)));
        return dashBoard;
    }

    public List<DashBoard> findList(DashBoard dashBoard) {
        return super.findList(dashBoard);
    }

    public Page<DashBoard> findPage(Page page, DashBoard dashBoard) {
        return super.findPage(page, dashBoard);
    }

    @Transactional(readOnly = false)
    public void save(DashBoard dashBoard) {
        super.save(dashBoard);
        for (Container container : dashBoard.getContainerList()) {
            if (container.getId() == null) {
                continue;
            }
            if (Container.DEL_FLAG_NORMAL.equals(container.getDelFlag())) {
                if (StringUtils.isBlank(container.getId())) {
                    container.setDashboard(dashBoard);
                    container.preInsert();
                    containerMapper.insert(container);
                } else {
                    container.preUpdate();
                    containerMapper.update(container);
                }
            } else {
                containerMapper.delete(container);
            }
        }
    }

    @Transactional(readOnly = false)
    public void delete(DashBoard dashBoard) {
        super.delete(dashBoard);
        containerMapper.delete(new Container(dashBoard));
    }

}
