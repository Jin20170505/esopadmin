package com.jeeplus.modules.api;

import com.jeeplus.modules.business.filemanger.service.BussinessFileMangerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/file/view")
public class ApiFileViewController {
    @Autowired
    private BussinessFileMangerService bussinessFileMangerService;

}
