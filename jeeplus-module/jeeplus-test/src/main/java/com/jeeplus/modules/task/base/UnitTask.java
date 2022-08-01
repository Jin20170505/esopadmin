package com.jeeplus.modules.task.base;

import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.modules.base.unit.web.BaseUnitController;
import com.jeeplus.modules.monitor.entity.Task;
import org.quartz.DisallowConcurrentExecution;

@DisallowConcurrentExecution
public class UnitTask extends Task {
    private static BaseUnitController controller = SpringContextHolder.getBean(BaseUnitController.class);
    @Override
    public void run() {
       controller.sychU8();
    }
}
