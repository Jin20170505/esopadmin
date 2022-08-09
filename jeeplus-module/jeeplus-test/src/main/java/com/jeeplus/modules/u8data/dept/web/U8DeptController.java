package com.jeeplus.modules.u8data.dept.web;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.u8data.dept.entity.U8Dept;
import com.jeeplus.modules.u8data.dept.service.SysDeptBeanService;
import com.jeeplus.modules.u8data.dept.service.U8DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("${adminPath}/u8dept")
public class U8DeptController extends BaseController {

    @Autowired
    private U8DeptService u8DeptService;
    @Autowired
    private SysDeptBeanService sysDeptBeanService;

    @ResponseBody
    @RequestMapping("sychu8")
    public AjaxJson sychu8(){
        AjaxJson json = new AjaxJson();
        try {
            U8Dept dept = new U8Dept();
            List<U8Dept> depts = u8DeptService.findList(dept);
            if(depts==null || depts.isEmpty()){
                json.setMsg("同步成功(ERP部门数据空)");
                json.setSuccess(true);
                return json;
            }
            sysDeptBeanService.sychu8(depts);
            json.setMsg("同步成功");
            json.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            json.setSuccess(false);
            json.setMsg("同步失败,原因："+e.getMessage());
        }
        return json;
    }

}
