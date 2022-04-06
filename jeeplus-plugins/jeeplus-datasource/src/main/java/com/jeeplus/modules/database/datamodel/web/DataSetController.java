/**
 *
 */
package com.jeeplus.modules.database.datamodel.web;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.database.datalink.entity.DataSource;
import com.jeeplus.modules.database.datalink.jdbc.DBPool;
import com.jeeplus.modules.database.datalink.service.DataSourceService;
import com.jeeplus.modules.database.datamodel.entity.DataSet;
import com.jeeplus.modules.database.datamodel.service.DataMetaService;
import com.jeeplus.modules.database.datamodel.service.DataParamService;
import com.jeeplus.modules.database.datamodel.service.DataSetService;
import net.sf.json.JSONArray;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * 数据模型Controller
 *
 * @author 刘高峰
 * @version 2018-08-07
 */
@Controller
@RequestMapping(value = "${adminPath}/database/datamodel/dataSet")
public class DataSetController extends BaseController {

    @Autowired
    private DataSetService dataSetService;
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private DataMetaService dataMetaService;
    @Autowired
    private DataParamService dataParamService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @ModelAttribute
    public DataSet get(@RequestParam(value = "id", required = false) String id) {
        DataSet entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = dataSetService.get(id);
        }
        if (entity == null) {
            entity = new DataSet();
        }
        return entity;
    }

    /**
     * 数据模型列表页面
     */
    @RequiresPermissions("database:datamodel:dataSet:list")
    @RequestMapping(value = {"list", ""})
    public String list(DataSet dataSet, Model model) {
        model.addAttribute("dataSet", dataSet);
        return "modules/database/datamodel/dataSetList";
    }

    /**
     * 数据模型列表数据
     */
    @ResponseBody
    @RequiresPermissions("database:datamodel:dataSet:list")
    @RequestMapping(value = "data")
    public Map<String, Object> data(DataSet dataSet, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<DataSet> page = dataSetService.findPage(new Page<DataSet>(request, response), dataSet);
        return getBootstrapData(page);
    }

    /**
     * 查看，增加，编辑数据模型表单页面
     * params:
     * mode: add, edit, view, 代表三种模式的页面
     */
    @RequiresPermissions(value = {"database:datamodel:dataSet:view", "database:datamodel:dataSet:add", "database:datamodel:dataSet:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form/{mode}")
    public String form(@PathVariable("mode") String mode, DataSet dataSet, Model model) {
        dataSet = dataSetService.detail(dataSet.getId());
        model.addAttribute("mode", mode);
        model.addAttribute("dataSet", dataSet);
        return "modules/database/datamodel/dataSetForm";
    }

    /**
     * 保存数据模型
     */
    @ResponseBody
    @RequiresPermissions(value = {"database:datamodel:dataSet:add", "database:datamodel:dataSet:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public AjaxJson save(DataSet dataSet, Model model) throws Exception {
        AjaxJson j = new AjaxJson();
        /**
         * 后台hibernate-validation插件校验
         */
        String errMsg = beanValidator(dataSet);
        if (StringUtils.isNotBlank(errMsg)) {
            j.setSuccess(false);
            j.setMsg(errMsg);
            return j;
        }
        //新增或编辑表单保存
        dataSetService.save(dataSet);//保存
        j.setSuccess(true);
        j.setMsg("保存数据模型成功");
        return j;
    }


    /**
     * 批量删除数据模型
     */
    @ResponseBody
    @RequiresPermissions("database:datamodel:dataSet:del")
    @RequestMapping(value = "delete")
    public AjaxJson delete(@RequestParam(value = "ids", required = false)String ids) {
        AjaxJson j = new AjaxJson();
        String idArray[] = ids.split(",");
        StringBuffer msg = new StringBuffer();
        boolean flag = false;
        for (String id : idArray) {
            List <Map<String, Object>>  list  = jdbcTemplate.queryForList("select name from plugin_echarts where model_id = '" + id +"'");
            if(list.size() > 0 ){
                j.setSuccess(false);
                String name = "";
                for(Map<String, Object> map : list){
                    name = name +" ["+map.get("name").toString()+"]";
                }
                msg.append("数据模型 ["+dataSetService.get(id).getName()+"] 已被图表"+name+" 使用，无法删除。请先删除图表，再删除该数据模型。<br/>");
            }else {
                flag = true;
                dataMetaService.deleteByDsId(id);
                dataParamService.deleteByDataSetId(id);
                msg.append("删除数据模型 ["+dataSetService.get(id).getName()+"] 成功。<br/>");
                dataSetService.delete(dataSetService.get(id));

            }

        }
        j.setSuccess(flag);
        j.setMsg(msg.toString());
        return j;
    }


    /**
     * 执行sql 获取表结构
     */

    @ResponseBody
    @RequestMapping(value = "getMeta")
    public List query(@RequestParam(value = "db", required = false)String db, @RequestParam(value = "sql", required = false)String sql, @RequestParam(value = "field[]", required = false)String[] field, @RequestParam(value = "defaultValue[]", required = false)String[] defaultValue) throws IOException, SQLException {
        List columnList = new ArrayList();

        DataSource dataSource = dataSourceService.get(db);
        if (dataSource == null) {
            return null;
        }

        JdbcTemplate jdbcTemplate = DBPool.getInstance().getDataSource(dataSource.getEnName());
        sql = dataSetService.mergeSql(sql, field, defaultValue);
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
        int count = rs.getMetaData().getColumnCount();
        for (int i = 1; i <= count; i++) {
            Map map = new HashMap();
            map.put("name", rs.getMetaData().getColumnName(i));
            map.put("type", rs.getMetaData().getColumnTypeName(i));
            map.put("label", rs.getMetaData().getColumnLabel(i));
            columnList.add(map);
        }

        return columnList;
    }

    /**
     * 执行sql,预览数据
     *
     * @param sql
     * @return
     */
    @RequestMapping(value = "/exec", method = {RequestMethod.POST})
    @ResponseBody
    public AjaxJson exec(@RequestParam(value = "db", required = false)String db, @RequestParam(value = "sql", required = false)String sql, @RequestParam(value = "field[]", required = false)String[] field, @RequestParam(value = "defaultValue[]", required = false)String[] defaultValue) {
        AjaxJson j = new AjaxJson();
        try {
            DataSource dataSource = dataSourceService.get(db);
            if (dataSource == null) {
                j.setSuccess(false);
                j.setMsg("数据库链接不存在!");
                return j;
            }

            JdbcTemplate jdbcTemplate = DBPool.getInstance().getDataSource(dataSource.getEnName());
            if (sql.contains("delete") || sql.contains("update")) {
                j.setSuccess(false);
                j.setMsg("只允许查询操作!");
                return j;
            }
            sql = dataSetService.mergeSql(sql, field, defaultValue);

            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
            //数据格式转换
            JSONArray data = dataSetService.toJSON(list);

            //返回结果
            j.put("html", dataSetService.toHTML(data));
            j.put("json", list);
            j.put("xml", dataSetService.toXML(data));
            return j;
        } catch (Exception e) {
            j.setSuccess(false);
            e.printStackTrace();
            return j;
        }
    }

    /**
     * 执行sql,预览数据
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getData/{id}/{type}")
    @ResponseBody
    public AjaxJson getData(@PathVariable("id") String id, @PathVariable("type") String type,  HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        Enumeration<String> names = request.getParameterNames();
        DataSet dataSet = dataSetService.get(id);
        try {
            DataSource dataSource = dataSourceService.get(dataSet.getDb());
            if (dataSource == null) {
                j.setSuccess(false);
                j.setMsg("数据库链接不存在!");
                return j;
            }
            JdbcTemplate jdbcTemplate = DBPool.getInstance().getDataSource(dataSource.getEnName());

            Map paramsMap = dataParamService.getParamsForMap(get(id));
            while(names.hasMoreElements()){
                String param = names.nextElement().toString();
                paramsMap.put(param, request.getParameter(param));
            }

            String sql = dataSetService.mergeSql(get(id).getSqlcmd(), paramsMap);
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
            //数据格式转换
            JSONArray data = dataSetService.toJSON(list);

            if("html".equals(type)){
                j.put("result", dataSetService.toHTML(data));
            }else if("xml".equals(type)){
                j.put("result", dataSetService.toXML(data));
            }else {
                j.put("result", list);
            }
            return j;
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg(e.getCause().getLocalizedMessage());
            return j;
        }
    }



}
