package com.jeeplus.modules.task.base;

import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.modules.base.huowei.web.BaseHuoWeiController;
import com.jeeplus.modules.monitor.entity.Task;
import org.quartz.DisallowConcurrentExecution;

@DisallowConcurrentExecution
public class HuoWeiTask extends Task {
    private static BaseHuoWeiController controller = SpringContextHolder.getBean(BaseHuoWeiController.class);
    @Override
    public void run() {
        controller.sychU8();
    }
}
