package com.jeeplus.modules.ireport;

import com.jeeplus.common.utils.Base64Utils;
import com.jeeplus.common.utils.FileUtils;
import com.jeeplus.config.properties.JeePlusProperites;
import com.jeeplus.modules.sys.utils.FileKit;
import net.sf.jasperreports.engine.JasperRunManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *  IReport预览和打印
 * @Auther: Jin
 * @Date: 2020/8/19
 * @Description:
 */
@Service
public class PDFService {
    @Autowired
    private DataSource dataSource;
    public void view(String filename,List<Map> paramList, HttpServletRequest request, HttpServletResponse response) throws SQLException {
        byte[] tempbyte;
        List<InputStream> inputs = new ArrayList<>();
        Connection conn =dataSource.getConnection();
        try {
            for (Map parameters : paramList) {
                ClassPathResource classPathResource = new ClassPathResource("report/"+ filename + ".jasper");
                InputStream inputStream =classPathResource.getInputStream();
                tempbyte = JasperRunManager.runReportToPdf(inputStream, parameters, conn);//1,报表文件2,参数map3,数据库连接
                inputs.add(Base64Utils.byte2Input(tempbyte));
            }
            String pdftemp = FileKit.getAttachmentUrl() + "/pdftemp";
            FileUtils.createDirectory(pdftemp);
            String uuid = UUID.randomUUID().toString();
            String filepdfpath = pdftemp + "/" + uuid + ".pdf";
            Base64Utils.hbpdf(inputs, filepdfpath);
            byte[] bytes = Base64Utils.File2byte(filepdfpath);
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes, 0, bytes.length);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(conn!=null){
                conn.close();
            }
        }
    }

    public String view(String filename,Map parameters) throws SQLException {
        byte[] tempbyte;
        List<InputStream> inputs = new ArrayList<>();
        Connection conn =dataSource.getConnection();
        try {
            ClassPathResource classPathResource = new ClassPathResource("report/"+ filename + ".jasper");
            InputStream inputStream =classPathResource.getInputStream();
            tempbyte = JasperRunManager.runReportToPdf(inputStream, parameters, conn);
            inputs.add(Base64Utils.byte2Input(tempbyte));
            String pdftemp = FileKit.getAttachmentDir() + "/pdftemp";
            FileUtils.createDirectory(pdftemp);
            String uuid = UUID.randomUUID().toString();
            String filepdfpath = pdftemp + "/" + uuid + ".pdf";
            Base64Utils.hbpdf(inputs, filepdfpath);
            return FileKit.getAttachmentUrl()+"/pdftemp/"+uuid+".pdf";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            if(conn!=null){
                conn.close();
            }
        }
    }
}

