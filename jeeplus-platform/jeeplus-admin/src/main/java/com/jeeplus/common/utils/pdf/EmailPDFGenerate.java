package com.jeeplus.common.utils.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jeeplus.common.utils.FileUtils;
import com.jeeplus.modules.sys.utils.FileKit;
import gui.ava.html.image.generator.HtmlImageGenerator;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @Auther: Jin
 * @Date: 2020/12/13
 * @Description:  邮件附件PDF生成
 */
public final class EmailPDFGenerate {

    public final static String generate(String filename, String companyName,String yearmont1,String yearmonth2, String tabHtml,
                                        String sumMoney,String bigSumMoney,String money,String rate,String paymonth,
                                        String accoutName,String accout,String bank,String date,int cols) throws IOException, DocumentException {
        Document document = new Document(PageSize.A4, 20, 20, 50, 50);
        String pdftemp = FileKit.getAttachmentDir() + "/pdftemp/";
        FileUtils.createDirectory(pdftemp);
        String imgPath = pdftemp+System.currentTimeMillis()+".png";
        String filepdfpath = pdftemp + filename + ".pdf";
        PdfWriter writer = PdfWriter.getInstance(document,  new FileOutputStream(filepdfpath));
        document.open();
        ClassPathResource classPathResource = new ClassPathResource("font/simsun.ttf"); 
        BaseFont baseFont = BaseFont.createFont(classPathResource.getPath(),BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
        Font st18  = new Font(baseFont,18);
        Paragraph header = new Paragraph("“融汇通”技术服务平台技术服务费",st18);
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);
        Font st14 = new Font(baseFont,14);
        Paragraph smallHeader= new Paragraph("缴费通知书",st18);
        smallHeader.setAlignment(Element.ALIGN_CENTER);
        document.add(smallHeader);
        Paragraph nice = new Paragraph("尊敬的"+companyName+"：",st14);
        document.add(nice);
        Font st12 = new Font(baseFont,12);
        Font st13 = new Font(baseFont);
        Paragraph one = new Paragraph("根据我司与贵司签署的《“融汇通”技术平台合作服务协议》约定," +
                "现发起对"+yearmont1,st12);
        one.setFirstLineIndent(24);// 缩进
        document.add(one);
        Paragraph ten = new Paragraph("贵司使用 “融汇通”技术服务平台的服务费用核对。",st12);
        document.add(ten);
        Paragraph two = new Paragraph(yearmonth2+"贵司使用“融汇通”平台情况如下：",st12);
        two.setFirstLineIndent(24);// 缩进
        document.add(two);

        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
        imageGenerator.loadHtml(tabHtml);
        imageGenerator.getBufferedImage();
        imageGenerator.saveAsImage(imgPath);

        Image image = Image.getInstance(imgPath);
        float width = image.getWidth();
        float height = image.getHeight();
        float documentWidth = document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin();
        if(cols <=10)
        {
        documentWidth = (documentWidth/10)*cols;
        }
        
        float documentHeight = height * (documentWidth/width);
        image.scaleAbsolute(documentWidth, documentHeight);//重新设置宽高
        document.add(image);


        Paragraph three = new Paragraph("按照《“融汇通”技术平台合作服务协议》中的服务费用条款的约定。贵司共需支付服务费用",st12);
        three.setFirstLineIndent(24);// 缩进
        document.add(three);
        Paragraph four = new Paragraph();
        Font moreSt12 = new Font(baseFont,12,Font.ITALIC|Font.BOLD);
        Chunk sum = new Chunk("含增值税总价款人民币：  ",st12);
        Chunk sum1 = new Chunk(sumMoney,moreSt12);
        sum1.setUnderline(0.1f,-2f);
        Chunk sum2 = new Chunk(" 元     大写金额：  ",st12);
        Chunk sum3 = new Chunk(bigSumMoney,moreSt12);
        sum3.setUnderline(0.1f,-2f);
        four.add(sum);
        four.add(sum1);
        four.add(sum2);
        four.add(sum3);
        document.add(four);
        Paragraph five = new Paragraph();
        Chunk nonesum = new Chunk("不含增值税总价款人民币：",st12);
        Chunk nonesum1 = new Chunk(money,moreSt12);
        nonesum1.setUnderline(0.1f,-2f);
        Chunk nonesum2 = new Chunk(" 元     增值税税额：",st12);
        Chunk nonesum3 = new Chunk(rate,moreSt12);
        nonesum3.setUnderline(0.1f,-2f);
        Chunk nonesum4 = new Chunk(" 元，税率：6%",st12);
        five.add(nonesum);
        five.add(nonesum1);
        five.add(nonesum2);
        five.add(nonesum3);
        five.add(nonesum4);
        document.add(five);

        Paragraph six = new Paragraph("请贵司于"+paymonth+"月15日前将上述款项汇入下方账户",st12);
        six.setFirstLineIndent(24);// 缩进
        document.add(six);

        Paragraph seven = new Paragraph();
        seven.setFirstLineIndent(24);// 缩进
        Chunk accoutNameChunk = new Chunk("名称：",st12);
        Font bigSt12 = new Font(baseFont,12,Font.BOLD);
        Chunk accountNameKey = new Chunk(accoutName,bigSt12);
        seven.add(accoutNameChunk);
        seven.add(accountNameKey);
        document.add(seven);
        Paragraph eight = new Paragraph();
        eight.setFirstLineIndent(24);// 缩进
        Chunk accoutChunk = new Chunk("账户：",st12);
        Chunk accountKey = new Chunk(accout,bigSt12);
        eight.add(accoutChunk);
        eight.add(accountKey);
        document.add(eight);
        Paragraph nine = new Paragraph();
        nine.setFirstLineIndent(24);// 缩进
        Chunk bankChunk = new Chunk("开户行：",st12);
        Chunk banKey = new Chunk(bank,bigSt12);
        nine.add(bankChunk);
        nine.add(banKey);
        document.add(nine);

        Paragraph paragraph = new Paragraph(accoutName,st14);
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        document.add(paragraph);
        Paragraph dateP = new Paragraph(date,st14);
        dateP.setAlignment(Element.ALIGN_RIGHT);
        document.add(dateP);
        document.close();
        new File(imgPath).delete();
        return FileKit.getAttachmentUrl()+"/pdftemp/"+filename+".pdf";
    }
}
