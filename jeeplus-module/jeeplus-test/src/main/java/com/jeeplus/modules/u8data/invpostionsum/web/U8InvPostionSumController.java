package com.jeeplus.modules.u8data.invpostionsum.web;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.business.baogong.order.entity.BusinessBaoGongOrder;
import com.jeeplus.modules.u8data.invpostionsum.entity.U8InvPostionSum;
import com.jeeplus.modules.u8data.invpostionsum.service.U8InvPostionSumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping(value = "${adminPath}/u8data/U8InvPostionSum")
public class U8InvPostionSumController extends BaseController {
    @Autowired
    private U8InvPostionSumService u8InvPostionSumService;
    @RequestMapping(value = {"list", ""})
    public String list(U8InvPostionSum u8InvPostionSum, Model model) {
        model.addAttribute("u8InvPostionSum", u8InvPostionSum);
        return "modules/u8data/xiancunliang/list";
    }

    /**
     * 报工单列表数据
     */
    @ResponseBody
    @RequestMapping(value = "data")
    public Map<String, Object> data(U8InvPostionSum u8InvPostionSum, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<U8InvPostionSum> page = u8InvPostionSumService.findPage(new Page<U8InvPostionSum>(request, response),u8InvPostionSum);
        return getBootstrapData(page);
    }
}
