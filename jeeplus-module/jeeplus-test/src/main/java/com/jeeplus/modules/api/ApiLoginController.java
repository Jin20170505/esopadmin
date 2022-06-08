package com.jeeplus.modules.api;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.mapper.UserMapper;
import com.jeeplus.modules.sys.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ApiLoginController {
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("login")
    public AjaxJson login(String account,String password){
        AjaxJson json = new AjaxJson();
        boolean flag = userMapper.isCode(account);
        if(!flag){
            json.setMsg("工号不存在.");
            json.setSuccess(false);
            return json;
        }
        User user = userMapper.getByNo(account);
        if(!"1".equals(user.getLoginFlag())){
            json.setMsg("该账号禁止登陆.");
            json.setSuccess(false);
            return json;
        }
        boolean pswFlag = SystemService.validatePassword(password,user.getPassword());
        if(!pswFlag){
            json.setMsg("密码错误.");
            json.setSuccess(false);
            return json;
        }
        json.setSuccess(true);
        json.put("user",user);
        json.setMsg("登陆成功");
        return json;
    }

    @RequestMapping("getMenus")
    public AjaxJson getMenus(String userid){
        AjaxJson json = new AjaxJson();
        String menus = userMapper.getAppMenu(userid);
        if(StringUtils.isEmpty(menus)){
            json.setMsg("该账号没有菜单权限，请设置后再操作");
            json.setSuccess(false);
            return json;
        }
        json.setSuccess(true);
        json.put("menus",menus);
        json.setMsg("成功");
        return json;
    }
}
