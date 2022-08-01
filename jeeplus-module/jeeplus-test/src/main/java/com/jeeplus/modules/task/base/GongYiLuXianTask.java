package com.jeeplus.modules.task.base;

import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.modules.base.route.web.BaseRoteMainController;
import com.jeeplus.modules.monitor.entity.Task;
import org.quartz.DisallowConcurrentExecution;

@DisallowConcurrentExecution
public class GongYiLuXianTask extends Task {
    private static BaseRoteMainController controller = SpringContextHolder.getBean(BaseRoteMainController.class);
    @Override
    public void run() {
        controller.sychU8();
    }
}
