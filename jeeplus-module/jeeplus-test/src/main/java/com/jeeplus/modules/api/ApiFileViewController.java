package com.jeeplus.modules.api;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.api.bean.ApiFileViewBean;
import com.jeeplus.modules.business.filemanger.service.BussinessFileMangerService;
import com.jeeplus.modules.business.product.archive.service.BusinessProductService;
import com.jeeplus.modules.esop.filemanger.BaseFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

@Controller
@RequestMapping("api/file/view")
public class ApiFileViewController {
    @Autowired
    private BussinessFileMangerService bussinessFileMangerService;
    @Autowired
    private BusinessProductService businessProductService;

    @ResponseBody
    @RequestMapping("findFileList")
    public AjaxJson findFileList(String siteid,String name, int page, int size){
        AjaxJson json = new AjaxJson();
        try{
            List<ApiFileViewBean> list = businessProductService.findFileBySite(siteid,name,page,size);
            json.put("list",list);
            json.put("pages",businessProductService.countFileBySite(siteid,name,size));
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
            String path = bussinessFileMangerService.getFilePath(id);
            if(StringUtils.isEmpty(path)){
                json.setSuccess(false);
                json.setMsg("文件地址不存在");
                return json;
            }
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
        String path = bussinessFileMangerService.getFilePath(id);
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
