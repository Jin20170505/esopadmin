/**
 * Copyright &copy; 2015-2020 磐新科技 All rights reserved.
 */
package com.jeeplus.modules.tools.web;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.tools.entity.TestInterface;
import com.jeeplus.modules.tools.service.TestInterfaceService;
import com.jeeplus.modules.tools.utils.HttpPostTest;

/**
 * 接口Controller
 *
 * @author lgf
 * @version 2016-01-07
 */
@Controller
@RequestMapping(value = "${adminPath}/tools/testInterface")
public class TestInterfaceController extends BaseController {

    @Autowired
    private TestInterfaceService testInterfaceService;

    @ModelAttribute
    public TestInterface get(@RequestParam(required = false) String id) {
        TestInterface entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = testInterfaceService.get(id);
        }
        if (entity == null) {
            entity = new TestInterface();
        }
        return entity;
    }

    /**
     * 接口列表页面
     */
    @RequiresPermissions("tools:testInterface:list")
    @RequestMapping(value = {"list", ""})
    public String list(TestInterface testInterface, HttpServletRequest request, HttpServletResponse response, Model model) {
        return "modules/tools/interface/testInterfaceList";
    }

    @ResponseBody
    @RequiresPermissions("tools:testInterface:list")
    @RequestMapping(value = "data")
    public Map<String, Object> data(TestInterface testInterface, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<TestInterface> page = testInterfaceService.findPage(new Page<TestInterface>(request, response), testInterface);
        model.addAttribute("page", page);
        return super.getBootstrapData(page);
    }

    /**
     * 查看，增加，编辑接口表单页面
     */
    @RequiresPermissions(value = {"tools:testInterface:view", "tools:testInterface:add", "tools:testInterface:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(TestInterface testInterface, Model model) {
        model.addAttribute("testInterface", testInterface);
        return "modules/tools/interface/testInterfaceForm";
    }

    /**
     * 保存接口
     */
    @ResponseBody
    @RequiresPermissions(value = {"tools:testInterface:add", "tools:testInterface:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public AjaxJson save(TestInterface testInterface, Model model) {
        AjaxJson j = new AjaxJson();
        /**
         * 后台hibernate-validation插件校验
         */
        String errMsg = beanValidator(testInterface);
        if (StringUtils.isNotBlank(errMsg)) {
            j.setSuccess(false);
            j.setMsg(errMsg);
            return j;
        }
        testInterfaceService.save(testInterface);
        j.setMsg("保存接口成功");
        return j;
    }

    /**
     * 删除接口
     */
    @ResponseBody
    @RequiresPermissions("tools:testInterface:del")
    @RequestMapping(value = "delete")
    public AjaxJson delete(TestInterface testInterface) {
        AjaxJson j = new AjaxJson();
        testInterfaceService.delete(testInterface);
        j.setSuccess(true);
        j.setMsg("删除接口成功！");
        return j;
    }

    /**
     * 批量删除接口
     */
    @ResponseBody
    @RequiresPermissions("tools:testInterface:del")
    @RequestMapping(value = "deleteAll")
    public AjaxJson deleteAll(String ids) {
        AjaxJson j = new AjaxJson();
        String idArray[] = ids.split(",");
        for (String id : idArray) {
            testInterfaceService.delete(testInterfaceService.get(id));
        }
        j.setSuccess(true);
        j.setMsg("删除接口成功！");
        return j;
    }

    /**
     * 导出excel文件
     */
    @RequiresPermissions("tools:testInterface:export")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(TestInterface testInterface, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "接口" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<TestInterface> page = testInterfaceService.findPage(new Page<TestInterface>(request, response, -1), testInterface);
            new ExportExcel("接口", TestInterface.class).setDataList(page.getList()).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出接口记录失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/tools/testInterface/?repage";
    }

    /**
     * 导入Excel数据
     */
    @RequiresPermissions("tools:testInterface:import")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<TestInterface> list = ei.getDataList(TestInterface.class);
            for (TestInterface testInterface : list) {
                testInterfaceService.save(testInterface);
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条接口记录");
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入接口失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/tools/testInterface/?repage";
    }

    /**
     * 下载导入接口数据模板
     */
    @RequiresPermissions("tools:testInterface:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "接口数据导入模板.xlsx";
            List<TestInterface> list = Lists.newArrayList();
            new ExportExcel("接口数据", TestInterface.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/tools/testInterface/?repage";
    }


    @RequiresPermissions("tools:testInterface:test")
    @RequestMapping(value = "/test")
    public String form(String id, HttpServletRequest request, HttpServletResponse response, Model model) {
        TestInterface entity = testInterfaceService.get(id);
        model.addAttribute("testInterface", entity);
        return "modules/tools/interface/interfaceTest";
    }

    /**
     * 接口内部请求
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/severTest")
    @ResponseBody
    public Object severTest(HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, String> map = new HashMap<String, String>();
        String errInfo = "success", str = "", rTime = "";
        try {
            long startTime = System.currentTimeMillis();
            String s_url = request.getParameter("serverUrl");//请求起始时间_毫秒
            String type = request.getParameter("requestMethod");
            String requestBody = request.getParameter("requestBody");
            URL url;
            if (type.equals("POST")) {//请求类型  POST or GET
                Map<String, String> params = new HashMap<String, String>();

                if (requestBody != null && !requestBody.equals("")) {
                    String[] paramList = requestBody.split("&");

                    for (String param : paramList) {
                        if (param.split("=").length == 2) {
                            params.put(param.split("=")[0], param.split("=")[1]);
                        } else {
                            params.put(param.split("=")[0], "");
                        }
                    }
                }
                HttpPostTest test = new HttpPostTest(s_url, params);

                str = test.post();
            } else {
                url = new URL(s_url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
                //请求结束时间_毫秒
                String temp = "";
                while ((temp = in.readLine()) != null) {
                    str = str + temp;
                }

            }

            long endTime = System.currentTimeMillis();
            rTime = String.valueOf(endTime - startTime);
        } catch (Exception e) {
            errInfo = "error";
            str = e.getMessage();
        }
        map.put("errInfo", errInfo);    //状态信息
        map.put("result", str);            //返回结果
        map.put("rTime", rTime);        //服务器请求时间 毫秒
        return map;
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();//网页的二进制数据
        outStream.close();
        inStream.close();
        return data;
    }

}