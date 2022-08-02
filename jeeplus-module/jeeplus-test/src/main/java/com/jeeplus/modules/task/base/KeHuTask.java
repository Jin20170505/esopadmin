package com.jeeplus.modules.task.base;

import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.modules.base.customer.web.BaseCustomerController;
import com.jeeplus.modules.monitor.entity.Task;
import org.quartz.DisallowConcurrentExecution;

@DisallowConcurrentExecution
public class KeHuTask  extends Task{
    private static BaseCustomerController controller = SpringContextHolder.getBean(BaseCustomerController.class);
    @Override
    public void run() {
        controller.sychU8();
    }
}
