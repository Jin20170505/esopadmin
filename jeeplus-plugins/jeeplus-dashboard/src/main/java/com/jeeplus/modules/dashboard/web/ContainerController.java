/**
 * Copyright &copy; 2015-2020 磐新科技 All rights reserved.
 */
package com.jeeplus.modules.dashboard.web;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.dashboard.entity.Container;
import com.jeeplus.modules.dashboard.service.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 容器Controller
 *
 * @author 刘高峰
 * @version 2018-09-11
 */
@Controller
@RequestMapping(value = "${adminPath}/container")
public class ContainerController extends BaseController {

    @Autowired
    private ContainerService containerService;

    @ModelAttribute
    public Container get(@RequestParam(value = "id", required = false) String id) {
        Container entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = containerService.get(id);
        }
        if (entity == null) {
            entity = new Container();
        }
        return entity;
    }

    /**
     * 容器列表页面
     */
    @RequestMapping(value = {"list", ""})
    public String list(Container container, Model model) {
        model.addAttribute("container", container);
        return "modules/container/containerList";
    }

    /**
     * 容器列表数据
     */
    @ResponseBody
    @RequestMapping(value = "data")
    public Map<String, Object> data(Container container, HttpServletRequest request, HttpServletResponse response, Model model) {

        Page<Container> page = containerService.findPage(new Page<Container>(request, response), container);
        return getBootstrapData(page);
    }

    /**
     * 查看，增加，编辑容器表单页面
     * params:
     * mode: add, edit, view, 代表三种模式的页面
     */
    @RequestMapping(value = "form/{mode}")
    public String form(@PathVariable("mode") String mode, Container container, Model model) {
        model.addAttribute("mode", mode);
        model.addAttribute("container", container);
        return "modules/container/containerForm";
    }

    /**
     * 保存容器
     */
    @ResponseBody
    @RequestMapping(value = "save")
    public AjaxJson save(Container container, Model model) throws Exception {
        AjaxJson j = new AjaxJson();
        /**
         * 后台hibernate-validation插件校验
         */
        String errMsg = beanValidator(container);
        if (StringUtils.isNotBlank(errMsg)) {
            j.setSuccess(false);
            j.setMsg(errMsg);
            return j;
        }
        //新增或编辑表单保存
        containerService.save(container);//保存
        j.setSuccess(true);
        j.setMsg("保存容器成功");
        return j;
    }


    /**
     * 批量删除容器
     */
    @ResponseBody
    @RequestMapping(value = "delete")
    public AjaxJson delete(@RequestParam(value = "ids", required = false)String ids) {
        AjaxJson j = new AjaxJson();
        String idArray[] = ids.split(",");
        for (String id : idArray) {
            containerService.delete(containerService.get(id));
        }
        j.setMsg("删除容器成功");
        return j;
    }


}
