package com.jeeplus.modules.qrcode;

import com.jeeplus.common.utils.BarcodeUtil;
import com.jeeplus.common.utils.QRCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 二维码 条形码
 */
@Controller
@RequestMapping("${adminPath}/qrcode")
public class QrcodeController {

    @RequestMapping("/qr/{qrcode}")
    public void getImage(@PathVariable("qrcode") String qrcode, HttpServletResponse response) throws IOException {
        response.reset();
        response.setContentType("image/jpg");
        ServletOutputStream out = null;
        try{
            out = response.getOutputStream();
            QRCodeUtil.encode(qrcode,out);
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(out!=null){
                out.close();
            }
        }
    }

    @RequestMapping("/barcode/{barcode}")
    public void barcode(@PathVariable("barcode") String barcode, HttpServletResponse response) throws IOException {
        response.reset();
        response.setContentType("image/jpg");
        ServletOutputStream out = null;
        try{
            out = response.getOutputStream();
            BarcodeUtil.encode(barcode,out);
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(out!=null){
                out.close();
            }
        }
    }
}
