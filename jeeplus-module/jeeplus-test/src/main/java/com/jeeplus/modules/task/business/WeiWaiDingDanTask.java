package com.jeeplus.modules.task.business;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.common.utils.time.DateUtil;
import com.jeeplus.modules.business.ommo.web.BusinessOmMoMainController;
import com.jeeplus.modules.monitor.entity.Task;
import org.quartz.DisallowConcurrentExecution;

import java.util.Date;

/**
 * 委外订单
 */
@DisallowConcurrentExecution
public class WeiWaiDingDanTask extends Task {

    private static BusinessOmMoMainController controller = SpringContextHolder.getBean(BusinessOmMoMainController.class);
    @Override
    public void run() {
        Date now = new Date();
        Date start = DateUtil.addDays(now,-2);
        controller.sychU8(DateUtils.formatDate(start,"yyyy-MM-dd")+" 00:00:00",
                DateUtils.formatDate(now,"yyyy-MM-dd")+" 23:59:59",null);
    }
}
