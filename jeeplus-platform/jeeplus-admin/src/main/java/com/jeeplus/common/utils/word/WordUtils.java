package com.jeeplus.common.utils.word;

import com.aspose.words.Document;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * word工具
 */
public final class WordUtils {

    public static void main(String[] args) {
        // wordConverterToPdf("/Users/jin/Downloads/test.docx","/Users/jin/Downloads/test.pdf");
    }
    public static boolean isWordLicense() {
        boolean result = false;
        try {
            String s = "<License><Data><Products><Product>Aspose.Total for Java</Product><Product>Aspose.Words for Java</Product></Products><EditionType>Enterprise</EditionType><SubscriptionExpiry>20991231</SubscriptionExpiry><LicenseExpiry>20991231</LicenseExpiry><SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber></Data><Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature></License>";
            ByteArrayInputStream inputStream = new ByteArrayInputStream(s.getBytes());
            com.aspose.words.License license = new com.aspose.words.License();
            license.setLicense(inputStream);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public static void wordConverterToPdf(String wordPath, String pdfPath) {
        if (!isWordLicense()) {          // 验证License 若不验证则转化出的pdf文档会有水印产生
            return;
        }
        try {
            File file = new File(pdfPath);  //新建一个pdf文档
            FileOutputStream os = new FileOutputStream(file);
            Document doc = new Document(wordPath);                    //Address是将要被转化的word文档
            doc.save(os, com.aspose.words.SaveFormat.PDF);//全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
