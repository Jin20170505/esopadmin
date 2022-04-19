package com.jeeplus.modules.esop.filemanger.web;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.modules.esop.filemanger.BaseFileUtil;
import com.jeeplus.modules.esop.filemanger.entity.FileBean;
import com.jeeplus.modules.esop.filemanger.service.EsopFileMangerService;
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
@RequestMapping("api/view")
public class EsopFileViewController {
    @Autowired
    private EsopFileMangerService esopFileMangerService;

    @ResponseBody
    @RequestMapping("findFileList")
    public AjaxJson findFileList(String name,int page,int size){
        AjaxJson json = new AjaxJson();
        try{
            List<FileBean> list = esopFileMangerService.findFiles(name,page,size);
            json.put("list",list);
            json.put("pages",esopFileMangerService.getPageSize(name,size));
            json.setSuccess(true);
            json.setMsg("查询成功");
        }catch (Exception e){
            e.printStackTrace();
            json.setSuccess(false);
            json.setMsg("查询失败");
        }
        return json;
    }

    @RequestMapping("getBase64OfFile/{id}")
    @ResponseBody
    public AjaxJson getBase64OfFile(@PathVariable("id")String id){
        AjaxJson json = new AjaxJson();
        try{
            String url = esopFileMangerService.getUrl(id);
//            File file = new File(FileKit.getAttachmentDir()+url.substring(url.indexOf("param/")+6));
            json.put("base", BaseFileUtil.encodeBase64File(FileKit.getAttachmentDir()+url.substring(url.indexOf("param/")+6)));
            json.setSuccess(true);
            json.setMsg("查询成功");
        }catch (Exception e){
            e.printStackTrace();
            json.setSuccess(false);
            json.setMsg("查询失败");
        }
        return json;
    }



    @RequestMapping("fileview/{id}")
    public void fileview(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
        String url = esopFileMangerService.getUrl(id);
        File file = new File(FileKit.getAttachmentDir()+url.substring(url.indexOf("param/")+6));
        response.reset();
        response.setContentType("application/pdf;charset=utf-8");
        ServletOutputStream out = null;
        BufferedInputStream in = null;
        try{
            in = new BufferedInputStream(new FileInputStream(file));
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
