package com.jeeplus.modules.u8data.employee.web;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.u8data.employee.entity.U8Employee;
import com.jeeplus.modules.u8data.employee.service.SysEmployeeService;
import com.jeeplus.modules.u8data.employee.service.U8EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("${adminPath}/u8employee")
public class U8EmployeeController extends BaseController {
    @Autowired
    private SysEmployeeService sysEmployeeService;
    @Autowired
    private U8EmployeeService u8EmployeeService;


    @RequestMapping("sychdata")
    @ResponseBody
    public AjaxJson sychdata(){
        AjaxJson json = new AjaxJson();
        try{
            List<U8Employee> u8Employees = u8EmployeeService.findList(new U8Employee());
            if(u8Employees==null || u8Employees.isEmpty()){
                json.setMsg("同步成功(空)");
                json.setSuccess(true);
                return json;
            }
            sysEmployeeService.sychu8(u8Employees);
            json.setMsg("同步成功");
            json.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            json.setSuccess(false);
            json.setMsg("同步失败");
        }
        return json;
    }
}
