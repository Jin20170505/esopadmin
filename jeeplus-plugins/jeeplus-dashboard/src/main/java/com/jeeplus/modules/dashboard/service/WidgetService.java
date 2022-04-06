/**
 * Copyright &copy; 2015-2020 磐新科技 All rights reserved.
 */
package com.jeeplus.modules.dashboard.service;


import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.dashboard.entity.Widget;
import com.jeeplus.modules.dashboard.mapper.WidgetMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 图表组件Service
 *
 * @author 刘高峰
 * @version 2018-08-13
 */
@Service
@Transactional(readOnly = true)
public class WidgetService extends CrudService<WidgetMapper, Widget> {

    public Widget get(String id) {
        return super.get(id);
    }

    public List<Widget> findList(Widget widget) {
        return super.findList(widget);
    }

    public Page<Widget> findPage(Page page, Widget widget) {
        return super.findPage(page, widget);
    }

    @Transactional(readOnly = false)
    public void save(Widget widget) {
        super.save(widget);
    }

    @Transactional(readOnly = false)
    public void delete(Widget widget) {
        super.delete(widget);
    }

    @Transactional(readOnly = false)
    public void deleteNoUse() {
        mapper.deleteNoUse();
    }

}
