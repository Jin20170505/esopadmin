package com.jeeplus.modules.task.base;

import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.modules.base.site.web.BaseSiteController;
import com.jeeplus.modules.monitor.entity.Task;
import org.quartz.DisallowConcurrentExecution;

@DisallowConcurrentExecution
public class GongXuTask extends Task {

    private static BaseSiteController controller = SpringContextHolder.getBean(BaseSiteController.class);
    @Override
    public void run() {
        controller.sychU8();
    }
}
