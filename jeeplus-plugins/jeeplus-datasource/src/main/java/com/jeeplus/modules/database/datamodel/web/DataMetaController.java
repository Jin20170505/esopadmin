/**
 *
 */
package com.jeeplus.modules.database.datamodel.web;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.database.datamodel.entity.DataMeta;
import com.jeeplus.modules.database.datamodel.service.DataMetaService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 数据资源Controller
 *
 * @author 刘高峰
 * @version 2018-08-07
 */
@Controller
@RequestMapping(value = "${adminPath}/database/datamodel/dataMeta")
public class DataMetaController extends BaseController {

    @Autowired
    private DataMetaService dataMetaService;


    @ModelAttribute
    public DataMeta get(@RequestParam(value = "id", required = false) String id) {
        DataMeta entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = dataMetaService.get(id);
        }
        if (entity == null) {
            entity = new DataMeta();
        }
        return entity;
    }

    /**
     * 数据资源列表页面
     */
    @RequiresPermissions("database:datamodel:dataMeta:list")
    @RequestMapping(value = {"list", ""})
    public String list(DataMeta dataMeta, Model model) {
        model.addAttribute("dataMeta", dataMeta);
        return "modules/database/datamodel/dataMetaList";
    }

    /**
     * 数据资源列表数据
     */
    @ResponseBody
    @RequiresPermissions("database:datamodel:dataMeta:list")
    @RequestMapping(value = "data")
    public Map<String, Object> data(DataMeta dataMeta, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<DataMeta> page = dataMetaService.findPage(new Page<DataMeta>(request, response), dataMeta);
        return getBootstrapData(page);
    }

    /**
     * 查看，增加，编辑数据资源表单页面
     * params:
     * mode: add, edit, view, 代表三种模式的页面
     */
    @RequiresPermissions(value = {"database:datamodel:dataMeta:view", "database:datamodel:dataMeta:add", "database:datamodel:dataMeta:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form/{mode}")
    public String form(@PathVariable("mode") String mode, DataMeta dataMeta, Model model) {
        model.addAttribute("mode", mode);
        model.addAttribute("dataMeta", dataMeta);
        return "modules/database/datamodel/dataMetaForm";
    }

    /**
     * 保存数据资源
     */
    @ResponseBody
    @RequiresPermissions(value = {"database:datamodel:dataMeta:add", "database:datamodel:dataMeta:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public AjaxJson save(DataMeta dataMeta, Model model) throws Exception {
        AjaxJson j = new AjaxJson();
        /**
         * 后台hibernate-validation插件校验
         */
        String errMsg = beanValidator(dataMeta);
        if (StringUtils.isNotBlank(errMsg)) {
            j.setSuccess(false);
            j.setMsg(errMsg);
            return j;
        }
        //新增或编辑表单保存
        dataMetaService.save(dataMeta);//保存
        j.setSuccess(true);
        j.setMsg("保存数据资源成功");
        return j;
    }


    /**
     * 批量删除数据资源
     */
    @ResponseBody
    @RequiresPermissions("database:datamodel:dataMeta:del")
    @RequestMapping(value = "delete")
    public AjaxJson delete(@RequestParam(value = "ids", required = false)String ids) {
        AjaxJson j = new AjaxJson();
        String idArray[] = ids.split(",");
        for (String id : idArray) {
            dataMetaService.delete(dataMetaService.get(id));
        }
        j.setMsg("删除数据资源成功");
        return j;
    }




}
