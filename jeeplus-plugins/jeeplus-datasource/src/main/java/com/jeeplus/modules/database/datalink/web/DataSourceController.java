/**
 *
 */
package com.jeeplus.modules.database.datalink.web;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.database.datalink.entity.DataSource;
import com.jeeplus.modules.database.datalink.jdbc.DBPool;
import com.jeeplus.modules.sys.utils.DictUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

import com.jeeplus.modules.database.datalink.service.DataSourceService;

/**
 * 数据库连接Controller
 *
 * @author 刘高峰
 * @version 2018-08-06
 */
@Controller
@RequestMapping(value = "${adminPath}/database/datalink/dataSource")
public class DataSourceController extends BaseController {

    @Autowired
    private DataSourceService dataSourceService;


    @ModelAttribute
    public DataSource get(@RequestParam(value = "id", required = false) String id) {
        DataSource entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = dataSourceService.get(id);
        }
        if (entity == null) {
            entity = new DataSource();
        }
        return entity;
    }

    /**
     * 数据库连接列表页面
     */
    @RequiresPermissions("database:datalink:dataSource:list")
    @RequestMapping(value = {"list", ""})
    public String list(DataSource dataSource, Model model) {
        model.addAttribute("dataSource", dataSource);
        return "modules/database/datalink/dataSourceList";
    }

    /**
     * 数据库连接列表数据
     */
    @ResponseBody
    @RequiresPermissions("database:datalink:dataSource:list")
    @RequestMapping(value = "data")
    public Map<String, Object> data(DataSource dataSource, HttpServletRequest request, HttpServletResponse response, Model model) {

        Page<DataSource> page = dataSourceService.findPage(new Page<DataSource>(request, response), dataSource);
        return getBootstrapData(page);
    }

    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<DataSource> list = dataSourceService.findList(new DataSource());
        HashSet<String> set = new HashSet();
        for (int i = 0; i < list.size(); i++) {
            DataSource e = list.get(i);
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", e.getId());
            map.put("parent", e.getHost());
            map.put("text", e.getName());
            map.put("type", "db");
            mapList.add(map);
            set.add(e.getHost());
        }
        for (String host : set) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", host);
            map.put("parent", "#");
            map.put("text", host);
            map.put("type", "host");
            Map<String, Object> state = Maps.newHashMap();
            state.put("opened", true);
            map.put("state", state);
            mapList.add(map);
        }
        return mapList;
    }

    @ResponseBody
    @RequestMapping(value = "treeData2")
    public List<Map<String, Object>> treeData2(HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        //添加默认数据源
        Map<String, Object> map1 = Maps.newHashMap();
        map1.put("id", "master-parent");
        map1.put("parent", "#");
        map1.put("text", "默认数据源");
        map1.put("type", "host");
        mapList.add(map1);

        Map<String, Object> map2 = Maps.newHashMap();
        map2.put("id", "master");
        map2.put("parent", "master-parent");
        map2.put("text", "本地数据库");
        map2.put("enName", "master");
        map2.put("type", "db");
        mapList.add(map2);

        List<DataSource> list = dataSourceService.findList(new DataSource());
        HashSet<String> set = new HashSet();
        for (int i = 0; i < list.size(); i++) {
            DataSource e = list.get(i);
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", e.getId());
            map.put("parent", e.getHost());
            map.put("enName", e.getEnName());
            map.put("text", e.getName());
            map.put("type", "db");
            mapList.add(map);
            set.add(e.getHost());
        }
        for (String host : set) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", host);
            map.put("parent", "#");
            map.put("text", host);
            map.put("type", "host");
            Map<String, Object> state = Maps.newHashMap();
            state.put("opened", true);
            map.put("state", state);
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * 查看，增加，编辑数据库连接表单页面
     * params:
     * mode: add, edit, view, 代表三种模式的页面
     */
    @RequiresPermissions(value = {"database:datalink:dataSource:view", "database:datalink:dataSource:add", "database:datalink:dataSource:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form/{mode}")
    public String form(@PathVariable("mode") String mode, DataSource dataSource, Model model) {
        model.addAttribute("mode", mode);
        model.addAttribute("dataSource", dataSource);
        return "modules/database/datalink/dataSourceForm";
    }

    /**
     * 保存数据库连接
     */
    @ResponseBody
    @RequiresPermissions(value = {"database:datalink:dataSource:add", "database:datalink:dataSource:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public AjaxJson save(DataSource dataSource, Model model) throws Exception {
        AjaxJson j = new AjaxJson();
        /**
         * 后台hibernate-validation插件校验
         */
        String errMsg = beanValidator(dataSource);
        if (StringUtils.isNotBlank(errMsg)) {
            j.setSuccess(false);
            j.setMsg(errMsg);
            return j;
        }
        String oldName = "";
        if(StringUtils.isNotBlank(dataSource.getId())){
             oldName = dataSourceService.get(dataSource.getId()).getEnName();
        }

        String driver = DictUtils.getDictValue( dataSource.getType(), "db_driver", "mysql");
        dataSource.setDriver(driver);
        dataSource.setUrl(dataSourceService.toUrl(dataSource.getType(), dataSource.getHost(), Integer.valueOf(dataSource.getPort()), dataSource.getDbname()));
        //新增或编辑表单保存
        dataSourceService.save(dataSource);//保存
        if(StringUtils.isNotBlank(oldName)){
            DBPool.getInstance().destroy(oldName);
        }
        DBPool.getInstance().create(dataSource);
        j.setSuccess(true);
        j.setMsg("保存数据库连接成功");
        return j;
    }


    /**
     * 批量删除数据库连接
     */
    @ResponseBody
    @RequiresPermissions("database:datalink:dataSource:del")
    @RequestMapping(value = "delete")
    public AjaxJson delete(@RequestParam(value = "ids", required = false)String ids) {
        AjaxJson j = new AjaxJson();
        String idArray[] = ids.split(",");
        for (String id : idArray) {
            DataSource dataSource = dataSourceService.get(id);
            dataSourceService.delete(dataSource);
            DBPool.getInstance().destroy(dataSource.getEnName());
        }
        j.setMsg("删除数据库连接成功");
        return j;
    }




    /**
     * 验证数据库唯一key是否存在
     * @param oldEnName
     * @param enName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "checkEnName")
    public String checkLoginName(@RequestParam(value = "oldEnName", required = false)String oldEnName, @RequestParam(value = "enName", required = false)String enName) {
        if (enName !=null && enName.equals(oldEnName)) {
            return "true";
        } else if (enName !=null && dataSourceService.findUniqueByProperty("enName", enName) == null) {
            return "true";
        }
        return "false";
    }
        /**
         * 测试数据源是否可用
         *
         * @param type     数据库类型
         * @param host
         * @param port
         * @param dbname
         * @param username
         * @param password
         * @return
         */
    @RequestMapping("/test")
    @ResponseBody
    public AjaxJson test(@RequestParam(value = "type", required = false)String type, @RequestParam(value = "host", required = false)String host, @RequestParam(value = "port", required = false)Integer port, @RequestParam(value = "dbname", required = false)String dbname, @RequestParam(value = "username", required = false)String username, @RequestParam(value = "password", required = false)String password) {
        AjaxJson j = new AjaxJson();
        if (StringUtils.isBlank(type) || StringUtils.isBlank(host) || StringUtils.isBlank(dbname) || StringUtils.isBlank(username)) {
            j.setSuccess(false);
            j.setMsg("配置信息不全");
            return j;
        }
        if (DBPool.getInstance().test(dataSourceService.getDriver(type), dataSourceService.toUrl(type, host, port, dbname), username, password)) {
            j.setSuccess(true);
            j.setMsg("连接成功");
            return j;
        } else {
            j.setSuccess(false);
            j.setMsg("连接失败");
            return j;
        }
    }

}
