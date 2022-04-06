/**
 * Copyright &copy; 2015-2020 磐新科技 All rights reserved.
 */
package com.jeeplus.modules.dashboard.web;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.dashboard.entity.Container;
import com.jeeplus.modules.dashboard.entity.DashBoard;
import com.jeeplus.modules.dashboard.service.ContainerService;
import com.jeeplus.modules.dashboard.service.DashBoardService;
import com.jeeplus.modules.dashboard.service.WidgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 仪表盘Controller
 *
 * @author 刘高峰
 * @version 2018-09-12
 */
@Controller
@RequestMapping(value = "${adminPath}/dashBoard")
public class DashBoardController extends BaseController {

    @Autowired
    private DashBoardService dashBoardService;
    @Autowired
    private WidgetService widgetService;
    @Autowired
    private ContainerService containerService;

    @ModelAttribute
    public DashBoard get(@RequestParam(value = "id", required = false) String id) {
        DashBoard entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = dashBoardService.get(id);
        }
        if (entity == null) {
            entity = new DashBoard();
        }
        return entity;
    }

    /**
     * 仪表盘列表页面
     */
    @RequestMapping(value = {"list", ""})
    public String list(DashBoard dashBoard, Model model) {
        model.addAttribute("dashBoard", dashBoard);
        return "modules/dashboard/dashBoardList";
    }

    /**
     * 仪表盘列表数据
     */
    @ResponseBody
    @RequestMapping(value = "data")
    public Map<String, Object> data(DashBoard dashBoard, HttpServletRequest request, HttpServletResponse response, Model model) {

        Page<DashBoard> page = dashBoardService.findPage(new Page<Container>(request, response), dashBoard);
        return getBootstrapData(page);
    }

    /**
     * 查看，增加，编辑仪表盘表单页面
     * params:
     * mode: add, edit, view, 代表三种模式的页面
     */
    @RequestMapping(value = "form/{mode}")
    public String form(@PathVariable("mode") String mode, DashBoard dashBoard, Model model) {
        model.addAttribute("mode", mode);
        model.addAttribute("dashBoard", dashBoard);
        return "modules/dashboard/dashBoardForm";
    }


    /**
     * 查看，增加，编辑仪表盘表单页面
     * params:
     * mode: add, edit, view, 代表三种模式的页面
     */
    @RequestMapping(value = "design")
    public String design(@RequestParam(value = "id", required = false)String id, Model model, HttpServletResponse response) throws Exception {
        DashBoard dashBoard = dashBoardService.get(id);
        model.addAttribute("dashBoard", dashBoard);
        return "modules/dashboard/design";
    }


    @ResponseBody
    @RequestMapping(value = "serializedData")
    public String serializedData(@RequestParam(value = "id", required = false)String id, Model model) throws Exception{
        DashBoard dashBoard = dashBoardService.get(id);
       return  dashBoard.getSerializedData();
    }



    @RequestMapping(value = "preview")
    public String preview(@RequestParam(value = "id", required = false)String id, Model model) throws Exception{
        DashBoard dashBoard = dashBoardService.get(id);
        model.addAttribute("dashBoard", dashBoard);
        return "modules/dashboard/preview";
    }

    @RequestMapping(value = "createMenu")
    public String createMenu(@RequestParam(value = "id", required = false)String id, Model model) throws Exception{
        model.addAttribute("id", id);
        return "modules/dashboard/createMenuForm";
    }

    /**
     * 保存仪表盘
     */
    @ResponseBody
    @RequestMapping(value = "save")
    public AjaxJson save(DashBoard dashBoard, Model model) throws Exception {
        AjaxJson j = new AjaxJson();
        /**
         * 后台hibernate-validation插件校验
         */
        String errMsg = beanValidator(dashBoard);
        if (StringUtils.isNotBlank(errMsg)) {
            j.setSuccess(false);
            j.setMsg(errMsg);
            return j;
        }
        //新增或编辑表单保存
        dashBoardService.save(dashBoard);//保存

        //删除没用使用的widget。
        widgetService.deleteNoUse();
        j.setSuccess(true);
        j.setMsg("保存仪表盘成功");
        return j;
    }


    /**
     * 批量删除仪表盘
     */
    @ResponseBody
    @RequestMapping(value = "delete")
    public AjaxJson delete(@RequestParam(value = "ids", required = false)String ids) {
        AjaxJson j = new AjaxJson();
        if (jeePlusProperites.isDemoMode()) {
            j.setSuccess(false);
            j.setMsg("演示模式，不允许操作！");
            return j;
        }
        String idArray[] = ids.split(",");
        for (String id : idArray) {
            dashBoardService.delete(dashBoardService.get(id));
        }
        j.setMsg("删除仪表盘成功");
        return j;
    }

    @ResponseBody
    @RequestMapping(value = "detail")
    public DashBoard detail(@RequestParam(value = "id", required = false)String id) {
        return dashBoardService.get(id);
    }





}
