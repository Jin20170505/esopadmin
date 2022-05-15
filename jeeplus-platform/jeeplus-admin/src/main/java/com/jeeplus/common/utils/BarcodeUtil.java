package com.jeeplus.common.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 条形码生成
 */
public final class BarcodeUtil {

    public final static void encode(String content, OutputStream out) throws IOException {
        Code128Writer writer = new Code128Writer();
        BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.CODE_128, 50, 25);
        MatrixToImageWriter.writeToStream(bitMatrix,"PNG",out);
    }
}
