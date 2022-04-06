package com.jeeplus.common.utils.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.core.io.ClassPathResource;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Auther: Jin
 * @Date: 2020/12/9
 * @Description:
 */
public class TestFontPdf {
    public static void main(String[] args) throws IOException, DocumentException {
        Document document = new Document(PageSize.A4, 0, 0, 0, 0);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("d:\\20201209.pdf"));
        document.open();
        document.newPage();
        ClassPathResource classPathResource = new ClassPathResource("font/simsun.ttf");
        BaseFont baseFont = BaseFont.createFont(classPathResource.getPath(),BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
        Font st18  = new Font(baseFont,18);
        Paragraph header = new Paragraph("“融汇通”技术服务平台技术服务费",st18);
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);
        Font st14 = new Font(baseFont,14);
        Paragraph smallHeader= new Paragraph("缴费通知书",st14);
        smallHeader.setAlignment(Element.ALIGN_CENTER);
        document.add(smallHeader);
        Paragraph title = new Paragraph("xxx公司：",st14);
        document.add(title);
        Font st12 = new Font(baseFont,12);
        Paragraph first = new Paragraph("根据我司与贵司签署的《心学校嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻》约定，钱钱钱钱钱钱钱钱钱钱钱钱钱钱钱钱钱钱钱钱钱钱钱钱钱钱钱钱钱钱钱钱钱钱钱钱钱钱：",st12);
        first.setFirstLineIndent(24);// 缩进
        document.add(first);
        Image image = Image.getInstance("d:\\tab.png");
        float width = image.getWidth();
        float height = image.getHeight();
        float documentWidth = document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin();
        float documentHeight = height * (documentWidth/width);
        image.scaleAbsolute(documentWidth, documentHeight);//重新设置宽高
        document.add(image);
        Font bigSt12 = new Font(baseFont,12,Font.BOLD);
        Paragraph two = new Paragraph("粗体"
                ,bigSt12);
        two.setFirstLineIndent(24);// 缩进
        document.add(two);
        Font lineSt12 = new Font(baseFont,12,Font.UNDERLINE);
        Paragraph three = new Paragraph("下划线"
                ,lineSt12);
        three.setFirstLineIndent(24);// 缩进
        document.add(three);
        Font xtSt12 = new Font(baseFont,12,Font.ITALIC);
        Paragraph four = new Paragraph("斜体"
                ,xtSt12);
        four.setFirstLineIndent(24);// 缩进
        document.add(four);
        // 一段样式
        Chunk five1 = new Chunk("金额:  ",st12);
        Font moreSt12 = new Font(baseFont,12,Font.ITALIC|Font.BOLD);
        Chunk five = new Chunk("斜体加粗下划线"
                ,moreSt12);
        five.setUnderline(0.1f,-2f);
        Paragraph p = new Paragraph();
        p.add(five1);
        p.add(five);
        p.setFirstLineIndent(24);
        document.add(p);
        // 表格 合并单元格
        PdfPTable table = new PdfPTable(3);
        table.setWidths(new int[]{100,100,100});
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        PdfPCell pdfCell = new PdfPCell();
        pdfCell.setFixedHeight(30);
        pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfCell.addElement(new Paragraph("月份",bigSt12));
        table.addCell(pdfCell);
        PdfPCell pdfCell2 = new PdfPCell();
        pdfCell2.setFixedHeight(30);
        pdfCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfCell2.addElement(new Paragraph("产品",bigSt12));
        table.addCell(pdfCell2);
        PdfPCell pdfCell3 = new PdfPCell();
        pdfCell3.setFixedHeight(30);
        pdfCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfCell3.addElement(new Paragraph("金额",bigSt12));
        table.addCell(pdfCell3);
        PdfPCell body = new PdfPCell();
        body.setColspan(3);
        // 表体
        PdfPTable btable = new PdfPTable(2);
        btable.setWidths(new int[]{100,200});
        PdfPCell leftcell = new PdfPCell();
        PdfPTable leftTable = new PdfPTable(1);
        PdfPCell leftc = new PdfPCell();
        leftc.setFixedHeight(60);
        leftc.addElement(new Paragraph("12",st12));
        leftTable.addCell(leftc);
        leftcell.addElement(leftTable);

        PdfPCell rightcell = new PdfPCell();
        PdfPTable rightTable = new PdfPTable(2);
        rightTable.setWidths(new int[]{100,100});
        PdfPCell rightc = new PdfPCell();
        rightc.setPadding(0);
        rightc.setFixedHeight(30);
        rightc.addElement(new Paragraph("同行收款",st12));
        rightTable.addCell(rightc);
        PdfPCell rightc1 = new PdfPCell();
        rightc1.setPadding(0);
        rightc1.setFixedHeight(30);
        rightc1.addElement(new Paragraph("122",st12));
        rightTable.addCell(rightc1);


        PdfPCell rightc2 = new PdfPCell();
        rightc2.setFixedHeight(30);
        rightc2.setPadding(0);
        rightc2.addElement(new Paragraph("同行收款2",st12));
        rightTable.addCell(rightc2);
        PdfPCell rightc12 = new PdfPCell();
        rightc12.setPadding(0);
        rightc12.setFixedHeight(30);
        rightc12.addElement(new Paragraph("0000",st12));
        rightTable.addCell(rightc12);

        rightcell.addElement(rightTable);
        btable.addCell(leftcell);
        btable.addCell(rightcell);
        body.addElement(btable);
        table.addCell(body);
        document.add(table);
        document.close();
    }
}
