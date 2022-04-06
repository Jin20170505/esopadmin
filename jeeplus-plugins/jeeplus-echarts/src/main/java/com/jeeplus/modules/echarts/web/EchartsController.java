/**
 * Copyright &copy; 2015-2020 磐新科技 All rights reserved.
 */
package com.jeeplus.modules.echarts.web;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.json.GsonUtil;
import com.google.common.collect.Lists;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.database.datamodel.entity.DataMeta;
import com.jeeplus.modules.database.datamodel.entity.DataSet;
import com.jeeplus.modules.database.datamodel.service.DataMetaService;
import com.jeeplus.modules.database.datamodel.service.DataSetService;
import com.jeeplus.modules.echarts.entity.Echarts;
import com.jeeplus.modules.echarts.service.EchartsService;
import com.jeeplus.modules.echarts.utils.OptionUtil;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyVetoException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 图表组件Controller
 *
 * @author 刘高峰
 * @version 2018-08-13
 */
@Controller
@RequestMapping(value = "${adminPath}/echarts")
public class EchartsController extends BaseController {


    @Value("${productId}")
    private String productID;
    @Value("${license}")
    private String license;

    @Autowired
    private DataMetaService dataMetaService;

    @Autowired
    private DataSetService dataSetService;

    @Autowired
    private EchartsService echartsService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @ModelAttribute
    public Echarts get(@RequestParam(value = "id", required = false) String id) {
        Echarts entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = echartsService.get(id);
        }
        if (entity == null) {
            entity = new Echarts();
        }
        return entity;
    }


    /**
     * 图表组件列表页面
     */
    @RequiresPermissions("echarts:list")
    @RequestMapping(value = {"list", ""})
    public String list(Echarts echarts, Model model) {
        model.addAttribute("echarts", echarts);
        return "modules/echarts/echartsList";
    }

    @RequestMapping(value = "preview")
    public String preview(Echarts echarts, Model model) {
        model.addAttribute("echarts", echarts);
        return "modules/echarts/preview";
    }

    @RequestMapping(value = "link/{id}")
    public String link(@PathVariable("id") String id, HttpServletRequest request, Model model) {
        Enumeration<String> names = request.getParameterNames();
        String params = "";
        while(names.hasMoreElements()){
            String param = names.nextElement().toString();
            params = params +"&"+ param+"="+request.getParameter(param);
        }
        model.addAttribute("params", params);
        model.addAttribute("echarts", get(id));
        return "modules/echarts/link";
    }
    /**
     * 图表组件列表数据
     */
    @ResponseBody
    @RequiresPermissions("echarts:list")
    @RequestMapping(value = "data")
    public Map<String, Object> data(Echarts echarts, HttpServletRequest request, HttpServletResponse response, Model model) {

        Page<Echarts> page = echartsService.findPage(new Page<Echarts>(request, response), echarts);
        return getBootstrapData(page);
    }

    /**
     * 查看，增加，编辑图表组件表单页面
     * params:
     * mode: add, edit, view, 代表三种模式的页面
     */
    @RequiresPermissions(value = {"echarts:view", "echarts:add", "echarts:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form/{mode}")
    public String form(@PathVariable("mode") String mode, Echarts echarts, Model model, HttpServletResponse response)throws Exception {
        model.addAttribute("mode", mode);
        DataMeta dataMeta = new DataMeta();
        dataMeta.setDataSet(echarts.getDataSet());
        List<DataMeta> xColumnList = Lists.newArrayList();
        List<DataMeta> yColumnList = Lists.newArrayList();
        List<DataMeta> columnList = dataMetaService.findList(dataMeta);
        Map xMap = new HashMap();
        Map yMap = new HashMap();
        for (DataMeta column : columnList) {

            if ((","+echarts.getxIds()+",").contains("," + column.getId() + ",")) {
                xColumnList.add(column);
            }
            ;
            if ((","+echarts.getyIds()+",").contains("," + column.getId() + ",")) {
                yColumnList.add(column);

            }
        }
        model.addAttribute("xColumnList", xColumnList);
        model.addAttribute("yColumnList", yColumnList);
        model.addAttribute("echarts", echarts);
        return "modules/echarts/echartsForm";
    }

    /**
     * 保存图表组件
     */
    @ResponseBody
    @RequiresPermissions(value = {"echarts:add", "echarts:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public AjaxJson save(Echarts echarts, Model model) throws Exception {
        AjaxJson j = new AjaxJson();
        /**
         * 后台hibernate-validation插件校验
         */
        String errMsg = beanValidator(echarts);
        if (StringUtils.isNotBlank(errMsg)) {
            j.setSuccess(false);
            j.setMsg(errMsg);
            return j;
        }
        //新增或编辑表单保存
        echartsService.save(echarts);//保存
        j.setSuccess(true);
        j.put("echarts", echarts);
        j.setMsg("保存图表组件成功");
        return j;
    }


    @RequestMapping(value = "createMenu")
    public String createMenu(@RequestParam(value = "id", required = false)String id, Model model) throws Exception{
        model.addAttribute("id", id);
        return "modules/echarts/createMenuForm";
    }

    /**
     * 批量删除图表组件
     */
    @ResponseBody
    @RequiresPermissions("echarts:del")
    @RequestMapping(value = "delete")
    public AjaxJson delete(@RequestParam(value = "ids", required = false)String ids) {
        AjaxJson j = new AjaxJson();
        StringBuffer msg = new StringBuffer();
        boolean flag = false;
        String idArray[] = ids.split(",");
        for (String id : idArray) {
            List <Map<String, Object>>  list  = jdbcTemplate.queryForList("        select c.name from   plugin_dashboard c  left join plugin_dashboard_widget a left join plugin_dashboard_container b  on  a.id = b.widget_id on c.id = b.dashboard_id where a.url=  '" + id +"'");
            if(list.size() > 0 ){
                j.setSuccess(false);
                String name = "";
                for(Map<String, Object> map : list){
                    name = name +" ["+map.get("name").toString()+"]";
                }
                msg.append("图表组件 ["+echartsService.get(id).getName()+"] 已被仪表盘"+name+" 使用，无法删除。请先删除仪表盘或者从仪表盘中移除该组件，然后再删除该图表组件。<br/>");
            }else {
                flag = true;
                msg.append("删除图表组件 ["+echartsService.get(id).getName()+"] 成功。<br/>");
                echartsService.delete(echartsService.get(id));

            }

        }
        j.setSuccess(flag);
        j.setMsg(msg.toString());

        return j;
    }

    @ResponseBody
    @RequestMapping("/dataMeta")
    public AjaxJson datasource(Model model,@RequestParam(value = "id", required = false) String id) throws PropertyVetoException {
        AjaxJson j = new AjaxJson();
        DataSet dataSet = dataSetService.get(id);
        j.getBody().put("dataSet", dataSet);
        DataMeta dataMeta = new DataMeta();
        dataMeta.setDataSet(dataSet);
        dataMeta.setIsNeed("on");
        j.getBody().put("meta", dataMetaService.findList(dataMeta));
        return j;
    }

    /**
     * 获取组件option
     */
    @ResponseBody
    @RequestMapping(value = "getOption/{id}")
    public Option getOption(@PathVariable("id")String id, HttpServletRequest request) throws Exception {
        Echarts echarts = get(id);
        String type = echarts.getType();
        if(StringUtils.isBlank(echarts.getOption())){
            throw new Exception("Id="+echarts.getId()+"的echarts图表组件不存在!");
        }
        String optionJson = HtmlUtils.htmlUnescape(echarts.getOption());
        DataSet dataSet = echarts.getDataSet();
        if (StringUtils.isBlank(dataSet.getId())) {
            return GsonUtil.fromJSON(HtmlUtils.htmlUnescape(optionJson), GsonOption.class);
        }
        String xIds = "," + echarts.getxIds() + ",";
        String yIds = "," + echarts.getyIds() + ",";

        return OptionUtil.option(type, optionJson, dataSet, xIds, yIds, request);
    }


    @ResponseBody
    @RequestMapping("mergeOption")
    protected Option mergeOption(@RequestParam(value = "type", required = false) String type, @RequestParam(value = "optionJson", required = false)String optionJson, @RequestParam(value = "dataSetId", required = false)String dataSetId, @RequestParam(value = "xIds", required = false)String xIds, @RequestParam(value = "yIds", required = false)String yIds) throws Exception {
        DataSet dataSet = new DataSet();
        dataSet.setId(dataSetId);

        return OptionUtil.option(type, optionJson, dataSet, xIds, yIds, null);
    }
}
