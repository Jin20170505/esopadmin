package com.jeeplus.modules.task.base;

import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.modules.business.product.archive.web.BusinessProductController;
import com.jeeplus.modules.business.product.type.web.BusinessProductTypeController;
import com.jeeplus.modules.monitor.entity.Task;
import org.quartz.DisallowConcurrentExecution;

@DisallowConcurrentExecution
public class CunHuoTask  extends Task {

    private static BusinessProductTypeController typeController = SpringContextHolder.getBean(BusinessProductTypeController.class);
    private static BusinessProductController controller = SpringContextHolder.getBean(BusinessProductController.class);

    @Override
    public void run() {
        typeController.sychU8();
        controller.sychU8();
    }
}
