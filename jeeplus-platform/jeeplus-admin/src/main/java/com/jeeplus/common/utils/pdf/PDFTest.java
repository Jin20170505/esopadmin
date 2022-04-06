package com.jeeplus.common.utils.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;


/**
 * @Auther: Jin
 * @Date: 2020/10/26
 * @Description:
 */
public class PDFTest {
    public static void main(String[] args) throws FileNotFoundException, DocumentException {
        /** 实例化文档对象 */
        Document document = new Document(PageSize.A4,10,10,10,10);
        /** 创建PdfWriter对象 */
        PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream("D:\\testPdf.pdf"));
        document.open();
        /** 段落 */
        document.add(new Paragraph("Some more text on the  first page with different color and font type.",
        FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD,    new CMYKColor(0, 255, 0, 0))));
        document.close();
    }
}
