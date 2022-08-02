package com.jeeplus.modules.task.business;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.common.utils.time.DateUtil;
import com.jeeplus.modules.business.ommoarrivalvouch.web.BusinessOmmoArrivalVouchController;
import com.jeeplus.modules.monitor.entity.Task;
import org.quartz.DisallowConcurrentExecution;

import java.util.Date;

/**
 * 委外到货单
 */
@DisallowConcurrentExecution
public class WeiWaiDaoHuoTask extends Task {
    private static BusinessOmmoArrivalVouchController controller = SpringContextHolder.getBean(BusinessOmmoArrivalVouchController.class);
    @Override
    public void run() {
        Date now = new Date();
        Date start = DateUtil.addDays(now,-2);
        controller.sychU8(DateUtils.formatDate(start,"yyyy-MM-dd")+" 00:00:00",DateUtils.formatDate(now,"yyyy-MM-dd")+" 23:59:59");
    }
}
