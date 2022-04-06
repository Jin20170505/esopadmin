/**
 * Copyright &copy; 2015-2020 磐新科技 All rights reserved.
 */
package com.jeeplus.modules.dashboard.web;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.dashboard.entity.Container;
import com.jeeplus.modules.dashboard.entity.Widget;
import com.jeeplus.modules.dashboard.service.ContainerService;
import com.jeeplus.modules.dashboard.service.WidgetService;
import com.jeeplus.modules.database.datalink.jdbc.DBPool;
import com.jeeplus.modules.database.datalink.service.DataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

/**
 * 组件Controller
 *
 * @author 刘高峰
 * @version 2018-08-13
 */
@Controller
@RequestMapping(value = "${adminPath}/widget")
public class WidgetController extends BaseController {

    @Autowired
    private WidgetService widgetService;

    @Autowired
    private ContainerService containerService;

    @Autowired
    private DataSourceService dataSourceService;

    @Value("${productId}")
    private String productID;
    @Value("${license}")
    private String license;

    @ModelAttribute
    public Widget get(@RequestParam(value = "id", required = false) String id) {
        Widget entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = widgetService.get(id);
        }
        if(entity == null){
            entity = new Widget();
        }
        return entity;
    }


    /**
     * 查看，增加，编辑组件表单页面
     * params:
     * mode: add, edit, view, 代表三种模式的页面
     */
    @RequestMapping(value = "form/{mode}")
    public String form(@PathVariable("mode") String mode,
                       HttpServletResponse response,
                       @RequestParam(value = "containerId", required = false)String containerId, Widget widget, Model model) throws Exception{

        model.addAttribute("mode", mode);
        model.addAttribute("widget", widget);
        model.addAttribute("containerId",containerId);
        //	model.addAttribute("dataSet", dataSetService.findAllList(new DataSet()));
        Container container = new Container();
        container.setWidget(widget);
        List<Container> list = containerService.findList(container);
        if (list.size() == 1) {
            model.addAttribute("borderClass", list.get(0).getBorderClass());
        }

        return "modules/widget/"+mode;
    }

    /**
     * 保存组件
     */
    @ResponseBody
    @RequestMapping(value = "save")
    public AjaxJson save(Widget widget, Model model) throws Exception {
        AjaxJson j = new AjaxJson();
        /**
         * 后台hibernate-validation插件校验
         */
        String errMsg = beanValidator(widget);
        if (StringUtils.isNotBlank(errMsg)) {
            j.setSuccess(false);
            j.setMsg(errMsg);
            return j;
        }
        if(StringUtils.isBlank(widget.getId())){
            j.put("add",true);
        }else {
            j.put("add",false);
        }
        //新增或编辑表单保存
        widgetService.save(widget);//保存
        j.setSuccess(true);
        j.put("widget", widget);
        j.setMsg("保存组件成功");
        return j;
    }


    @ResponseBody
    @RequestMapping(value = "getResult")
    public  String getResult(Widget widget){
            String enName = dataSourceService.get(widget.getDataSource().getId()).getEnName();
            Collection<Object> collection = DBPool.getInstance().getDataSource(enName).queryForList(widget.getSql()).get(0).values();
            String result = "";
            for(Object object:collection ){
                result = result+object.toString() + ",";
            }
            if(result.endsWith(",")){
                result = result.substring(0, result.length()-1);
            }
           return result;

    }

    @ResponseBody
    @RequestMapping(value = "getRows")
    public  List getRows(Widget widget){
        String enName = dataSourceService.get(widget.getDataSource().getId()).getEnName();
       List list = DBPool.getInstance().getDataSource(enName).queryForList(widget.getSql());

        return list;

    }

    /**
     * 批量删除组件
     */
    @ResponseBody
    @RequestMapping(value = "delete")
    public AjaxJson delete(@RequestParam(value = "ids", required = false)String ids) {
        AjaxJson j = new AjaxJson();
        String idArray[] = ids.split(",");
        for (String id : idArray) {
            widgetService.delete(widgetService.get(id));
        }
        j.setMsg("删除组件成功");
        return j;
    }


}
