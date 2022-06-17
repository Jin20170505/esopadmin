package com.jeeplus.modules.u8data.customercinvcode.web;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.u8data.customercinvcode.entity.U8CusInvContrapose;
import com.jeeplus.modules.u8data.customercinvcode.service.U8CusInvContraposeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping(value = "${adminPath}/u8data/U8CusInvContrapose")
public class U8CusInvContraposeController  extends BaseController {
    @Autowired
    private U8CusInvContraposeService u8CusInvContraposeService;
    /**
     * 报工单列表数据
     */
    @ResponseBody
    @RequestMapping(value = "data")
    public Map<String, Object> data(U8CusInvContrapose u8CusInvContrapose, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<U8CusInvContrapose> page = u8CusInvContraposeService.findPage(new Page<U8CusInvContrapose>(request, response),u8CusInvContrapose);
        return getBootstrapData(page);
    }
}
