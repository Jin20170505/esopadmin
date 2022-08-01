package com.jeeplus.modules.task.business;

import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.modules.business.shengchan.dingdan.web.BusinessShengChanDingDanController;
import com.jeeplus.modules.monitor.entity.Task;
import org.quartz.DisallowConcurrentExecution;

/**
 * 生产订单
 */
@DisallowConcurrentExecution
public class ShengChanDingDanTask extends Task {
    private static BusinessShengChanDingDanController controller = SpringContextHolder.getBean(BusinessShengChanDingDanController.class);
    @Override
    public void run() {
        controller.sychu8new();
    }
}
