package com.jeeplus.common.utils.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jeeplus.common.utils.FileUtils;
import com.jeeplus.modules.sys.utils.FileKit;
import org.springframework.core.io.ClassPathResource;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @Auther: Jin
 * @Date: 2020/10/26
 * @Description:
 */
public class PDFTableUtils {

    public static final String generate(String title, List<String> headers, List<List<String>> body,List<String> prePList,List<String> sufixPList) throws IOException, DocumentException {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        String pdftemp = FileKit.getAttachmentDir() + "/pdftemp";
        FileUtils.createDirectory(pdftemp);
        BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font font = new Font(baseFont);
        String filepdfpath = pdftemp + "/" + title + ".pdf";
        PdfWriter writer = PdfWriter.getInstance(document,  new FileOutputStream(filepdfpath));
        document.open();
        Paragraph paragraphTitle = new Paragraph(title,font);
        Chapter chapter = new Chapter(paragraphTitle, 1);
        chapter.setNumberDepth(0);
        PdfPTable t = new PdfPTable(headers.size());
        t.setTotalWidth(PageSize.A4.getWidth()-20);
        t.setLockedWidth(true);
        t.setSpacingBefore(10);
        t.setSpacingAfter(10);
        headers.forEach(h->{
            font.setSize(12);
            t.addCell(new Paragraph(h,font));
        });
        body.forEach(row->row.forEach(e->{
            font.setSize(10);
            t.addCell(new Paragraph(e,font));
        }));
        document.add(chapter);
        if(prePList!=null){
            for(String p:prePList){
                document.add(new Paragraph(p,font));
            }
        }
        document.add(t);
        if(sufixPList!=null){
            for(int i=0,len = sufixPList.size();i<len;i++){
                String p = sufixPList.get(i);
                if(i<len-2){
                    document.add(new Paragraph(p,font));
                }else {
                    Paragraph paragraph = new Paragraph(p,font);
                    paragraph.setAlignment(Element.ALIGN_RIGHT);
                    document.add(paragraph);
                }
            }
        }
        document.close();
        return FileKit.getAttachmentUrl()+"/pdftemp/"+title+".pdf";
    }
    /**
     * 获取宋体字体文件路径
     */
    public static BaseFont getSongFont() throws IOException, DocumentException {
        ClassPathResource classPathResource = new ClassPathResource("font/simsun.ttf");
        BaseFont baseFont = BaseFont.createFont(classPathResource.getPath(),BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
        return baseFont;
    }
}
