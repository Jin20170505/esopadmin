package com.jeeplus.modules.task.base;

import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.modules.base.cangku.web.BaseCangKuController;
import com.jeeplus.modules.monitor.entity.Task;
import org.quartz.DisallowConcurrentExecution;

/**
 * 仓库同步
 */
@DisallowConcurrentExecution
public class CangKuTask extends Task {

    private static BaseCangKuController controller = SpringContextHolder.getBean(BaseCangKuController.class);
    @Override
    public void run() {
        controller.sychU8();
    }

}
