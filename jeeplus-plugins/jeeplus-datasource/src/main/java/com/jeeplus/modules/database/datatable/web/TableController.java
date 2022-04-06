package com.jeeplus.modules.database.datatable.web;

import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.database.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.jeeplus.database.datasource.DynamicRoutingDataSource;
import com.google.common.collect.Lists;
import com.jeeplus.modules.database.datalink.jdbc.DBPool;
import com.jeeplus.modules.database.datalink.service.DataSourceService;
import com.jeeplus.modules.database.datatable.entity.JTable;
import com.jeeplus.database.persistence.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.ddlutils.Platform;
import org.apache.ddlutils.PlatformFactory;
import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.model.Database;
import org.apache.ddlutils.model.Table;
import org.apache.ddlutils.model.TypeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据模型Controller
 *
 * @author 刘高峰
 * @version 2018-08-07
 */
@Controller
@RequestMapping(value = "${adminPath}/database/table")
public class TableController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    DataSourceService dataSourceService;

    @Autowired
    DataSource dataSource;

    @Autowired
    DynamicDataSourceProperties dynamicDataSourceProperties;


    @RequestMapping
    public String index(Model model) {
        return "modules/database/datatable/index";
    }

    private DataSource getBasicDataSource(String dataSourceId) {
        DynamicRoutingDataSource dynamicRoutingDataSource = SpringContextHolder.getBean(DynamicRoutingDataSource.class);
       return dynamicRoutingDataSource.getDataSource(dataSourceService.get(dataSourceId).getEnName());
    }



    @ResponseBody
    @RequestMapping(value = "data")
    public DsAjaxJson data(@RequestParam("dataSourceId") String dataSourceId, Model model) throws PropertyVetoException {
        DsAjaxJson j =  new DsAjaxJson();
        if(dataSourceId.equals("master")){
           if( dynamicDataSourceProperties.getDatasource().get("master").getDriverClassName().toLowerCase().contains("oracle")){
               j.setSuccess(false);
               j.setMsg("由于Apache DataBase插件版本原因，oracle数据库数据表管理功能被禁用!");
               return j;
           }
        }else if(dataSourceService.get(dataSourceId).getDriver().toLowerCase().contains("oracle")){
            j.setSuccess(false);
            j.setMsg("由于Apache DataBase插件版本原因，oracle数据库数据表管理功能被禁用!");
            return j;
        }


        DataSource basicDataSource = getBasicDataSource(dataSourceId);


        Platform platform = PlatformFactory.createNewPlatformInstance(basicDataSource);

        try {
            Connection conn = platform.getDataSource().getConnection();

            Database db = platform.readModelFromDatabase(conn, null,  conn.getCatalog(), null, null);
            conn.close();
            Table[] tables = db.getTables();
            List<JTable> jTableList = Lists.newArrayList();
            for (Table table : tables) {
                JTable jTable = new JTable();
                jTable.setName(table.getName());
                jTable.setCatalog(table.getCatalog());
                jTable.setDescription(table.getDescription());
                jTable.setSchema(table.getSchema());
                jTableList.add(jTable);
            }

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("rows", jTableList);
            map.put("total", jTableList.size());
            j.put("data",map);
            return j;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }




    /**
     * 创建新表
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/create")
    public String create(@RequestParam("dataSourceId") String dataSourceId, Model model) {
        DataSource basicDataSource = getBasicDataSource(dataSourceId);
        Platform platform = PlatformFactory.createNewPlatformInstance(basicDataSource);
        try {
            Connection conn = platform.getDataSource().getConnection();
            String catalog = conn.getCatalog();
            model.addAttribute("catalog", catalog);
            model.addAttribute("schema", "");

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        model.addAttribute("columnTypes", DsClassKit.getFields(TypeMap.class));
        model.addAttribute("dataSourceId", dataSourceId);
        return "modules/database/datatable/create";
    }

    @RequestMapping(value = "/create/do", method = {RequestMethod.POST})
    @ResponseBody
    public DsAjaxJson doCreate(@RequestParam("dataSourceId") String dataSourceId, Table table, @RequestParam(value = "columns", required = false)String columns) {
        DsAjaxJson j = new DsAjaxJson();
        try {
            DataSource basicDataSource = getBasicDataSource(dataSourceId);
            Platform platform = PlatformFactory.createNewPlatformInstance(basicDataSource);
            Connection conn = platform.getDataSource().getConnection();
            Database db = platform.readModelFromDatabase(conn, null,  conn.getCatalog(), null, null);
            Table eqTable = db.findTable(table.getName());
            if (DsObjectKit.isNotNull(eqTable)) {
                j.setSuccess(false);
                j.setMsg("表 '" + table.getName() + "' 已存在！");
                return j;
            }
            columns = HtmlUtils.htmlUnescape(columns);
            JSONArray jColumns = JSONArray.fromObject("[" + columns + "]");
            for (Object col : jColumns) {
                JSONObject jColumn = JSONObject.fromObject(col);

                Column column = new Column();
                column.setName(jColumn.getString("name"));
                column.setType(jColumn.getString("type"));
                if (!DsStringKit.isEmpty(jColumn.getString("size")))
                    column.setSize(jColumn.getString("size"));
                column.setPrimaryKey(jColumn.getBoolean("pk"));
                if (!column.isPrimaryKey())
                    column.setDefaultValue(jColumn.getString("def"));
                column.setRequired(jColumn.getBoolean("notNull"));
                column.setAutoIncrement(jColumn.getBoolean("autoIncr"));
                column.setDescription(jColumn.getString("comment"));

                table.addColumn(column);
            }
            Database db2 = new Database();
            db2.addTable(table);
            platform.createTables(db2, false, false);

            conn.close();
            return j;
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg(e.toString());
            return j;
        }
    }

    /**
     * 修改表
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/alter/{name}")
    public String alter(Model model, @RequestParam("dataSourceId") String dataSourceId, @PathVariable("name") String name) {
        DataSource basicDataSource = getBasicDataSource(dataSourceId);
        Platform platform = PlatformFactory.createNewPlatformInstance(basicDataSource);
        try {
            Connection conn = platform.getDataSource().getConnection();
            Database db = platform.readModelFromDatabase(conn, null,  conn.getCatalog(), null, null);
            Table table = db.findTable(name);
            model.addAttribute("table", table);

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        model.addAttribute("columnTypes", DsClassKit.getFields(TypeMap.class));
        model.addAttribute("dataSourceId", dataSourceId);
        return "modules/database/datatable/alter";
    }

    @RequestMapping(value = "/alter/do")
    @ResponseBody
    public DsAjaxJson doAlter(@RequestParam("dataSourceId") String dataSourceId, Table table,@RequestParam(value = "columns", required = false) String columns) {
        DsAjaxJson j = new DsAjaxJson();
        try {
            DataSource basicDataSource = getBasicDataSource(dataSourceId);
            Platform platform = PlatformFactory.createNewPlatformInstance(basicDataSource);
            try {
                Connection conn = platform.getDataSource().getConnection();
                Database db = platform.readModelFromDatabase(conn, null,  conn.getCatalog(), null, null);
                Table eqTable = db.findTable(table.getName());
                if (DsObjectKit.isNotNull(eqTable)) {
                    eqTable.setName(table.getName());
                    eqTable.setDescription(table.getDescription());
                    columns = HtmlUtils.htmlUnescape(columns);
                    JSONArray jColumns = JSONArray.fromObject("[" + columns + "]");
                    for (int i = 0; i < jColumns.size(); i++) {
                        JSONObject jColumn = JSONObject.fromObject(jColumns.get(i));
                        boolean isNew = false;
                        if (i + 1 > eqTable.getColumnCount())
                            isNew = true;
                        Column column = new Column();
                        if (!isNew)
                            column = eqTable.getColumn(i);
                        column.setName(jColumn.getString("name"));
                        column.setType(jColumn.getString("type"));
                        if (!DsStringKit.isEmpty(jColumn.getString("size")))
                            column.setSize(jColumn.getString("size"));
                        column.setPrimaryKey(jColumn.getBoolean("pk"));
                        if (!column.isPrimaryKey())
                            column.setDefaultValue(jColumn.getString("def"));
                        column.setRequired(jColumn.getBoolean("notNull"));
                        column.setAutoIncrement(jColumn.getBoolean("autoIncr"));
                        column.setDescription(jColumn.getString("comment"));

                        if (isNew)
                            eqTable.addColumn(column);
                    }
                    for (int i = jColumns.size(); i < eqTable.getColumnCount(); i++) {
                        eqTable.removeColumn(i);
                    }
                    platform.alterTables(conn, conn.getCatalog(),  null, null,db, false);
                }

                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return j;
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg(e.toString());
            return j;
        }
    }

    @RequestMapping(value = "/preview/{name}")
    public String preview(@RequestParam("dataSourceId") String dataSourceId, Model model, @PathVariable("name") String name) {
        model.addAttribute("name", name);
        model.addAttribute("dataSourceId", dataSourceId);
        return "modules/database/datatable/preview";
    }


    @ResponseBody
    @RequestMapping(value = "/executeSql/{name}")
    public DsAjaxJson executeSql(@RequestParam("dataSourceId") String dataSourceId, @PathVariable("name") String name) throws Exception {
        DsAjaxJson j = new DsAjaxJson();
        List<Map<String, Object>> result = DBPool.getInstance().getDataSource(dataSourceService.get(dataSourceId).getEnName()).queryForList("select * from " + name + " limit 100");
        j.getBody().put("list", result);

        DataSource basicDataSource = getBasicDataSource(dataSourceId);
        Platform platform = PlatformFactory.createNewPlatformInstance(basicDataSource);
        Connection conn = platform.getDataSource().getConnection();
        Database db = platform.readModelFromDatabase(conn, null,  conn.getCatalog(), null, null);
        Table table = db.findTable(name);
        Column[] columns = table.getColumns();
        conn.close();

        j.getBody().put("columns", columns);

        return j;
    }

}
