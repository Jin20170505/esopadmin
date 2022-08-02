package com.jeeplus.modules.task.base;

import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.modules.base.vendor.web.BaseVendorController;
import com.jeeplus.modules.monitor.entity.Task;
import org.quartz.DisallowConcurrentExecution;

@DisallowConcurrentExecution
public class GongYingShangTask extends Task {
    private static BaseVendorController controller = SpringContextHolder.getBean(BaseVendorController.class);
    @Override
    public void run() {
        controller.sychU8();
    }
}
