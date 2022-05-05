package com.jeeplus.modules.api;

import com.google.common.collect.Lists;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.api.bean.ApiFileViewBean;
import com.jeeplus.modules.business.filemanger.service.BussinessFileMangerService;
import com.jeeplus.modules.esop.filemanger.BaseFileUtil;
import com.jeeplus.modules.esop.manger.service.EsopMangerService;
import com.jeeplus.modules.sys.utils.FileKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("api/file/view")
public class ApiFileViewController {
    @Autowired
    private EsopMangerService esopMangerService;

    @ResponseBody
    @RequestMapping("findFileList")
    public AjaxJson findFileList(String siteid,String name, int page, int size){
        AjaxJson json = new AjaxJson();
        try{
            List<ApiFileViewBean> list = esopMangerService.findFile(null,siteid,name,page,size);
            json.put("list",list);
            json.put("pages",esopMangerService.countPageSize(null,siteid,name,size));
            json.setSuccess(true);
            json.setMsg("查询成功");
        }catch (Exception e){
            e.printStackTrace();
            json.setSuccess(false);
            json.setMsg("查询失败");
        }
        return json;
    }

    @RequestMapping("pdf/{id}")
    @ResponseBody
    public AjaxJson getBase64OfFile(@PathVariable("id")String id){
        AjaxJson json = new AjaxJson();
        try{
            String path = esopMangerService.getFileUrl(id);
            if(StringUtils.isEmpty(path)){
                json.setSuccess(false);
                json.setMsg("文件地址不存在");
                return json;
            }
            path = FileKit.getAttachmentDir()+ path.substring(path.lastIndexOf("/param/")+7);
            File file = new File(path);
            if(!file.exists()){
                json.setSuccess(false);
                json.setMsg("文件不存在");
                return json;
            }
            json.put("base", BaseFileUtil.encodeBase64File(path));
            json.setSuccess(true);
            json.setMsg("查询成功");
        }catch (Exception e){
            e.printStackTrace();
            json.setSuccess(false);
            json.setMsg("查询失败");
        }
        return json;
    }

    @RequestMapping("/img/{id}")
    public void getImage(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
        String path = esopMangerService.getFileUrl(id);
        path = FileKit.getAttachmentDir()+ path.substring(path.lastIndexOf("/param/")+7);
        response.reset();
        response.setContentType("image/png");
        ServletOutputStream out = null;
        BufferedInputStream in = null;
        try{
            in = new BufferedInputStream(new FileInputStream(path));
            out = response.getOutputStream();
            // 读取文件流
            int len = 0;
            while ((len = in.read()) != -1) {
                out.write(len);
            }
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(out!=null){
                out.close();
            }
            if(in !=null){
                in.close();
            }
        }
    }
}
