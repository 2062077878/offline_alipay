package com.wu.alipay.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.xtc.common.log.LogUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 生成二维码
 * Created by Administrator on 2016/8/10.
 */
public class CreateQR {

    private int QR_WIDTH = 100;
    private int QR_HEIGHT = 100;

    public CreateQR(){

    }
    public CreateQR(int width,int height){
        QR_WIDTH = width;
        QR_HEIGHT = height;
    }

    /*
    * 利用zxing生成二维码
    * */
    public Bitmap createQRImage(String codeString,Bitmap logoImg){
        if(codeString ==null||codeString.equals("")){
            return null;
        }
        //配置参数
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        //容错级别
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        //设置空白边距的宽度
        hints.put(EncodeHintType.MARGIN, 1); //default is 4

        // 图像数据转换，使用了矩阵转换
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new QRCodeWriter().encode(codeString, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
        } catch (WriterException e) {
            LogUtil.e("生成二维码过程中出错");
            new RuntimeException(e);
        }
        int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
        // 下面这里按照二维码的算法，逐个生成二维码的图片，
        // 两个for循环是图片横列扫描的结果
        for (int y = 0; y < QR_HEIGHT; y++) {
            for (int x = 0; x < QR_WIDTH; x++) {
                if (bitMatrix.get(x, y)) {
                    pixels[y * QR_WIDTH + x] = 0xff000000;
                } else {
                    pixels[y * QR_WIDTH + x] = 0xffffffff;
                }
            }
        }

        // 生成二维码图片的格式，使用ARGB_8888
        Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH,QR_HEIGHT, Bitmap.Config.RGB_565);
        bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
        if (logoImg != null) {
            bitmap =  addLogo(bitmap, logoImg);
        }
        return bitmap;
    }

    private Bitmap addLogo(Bitmap bitmap, Bitmap logoImg) {
        if(bitmap==null){
            return null;
        }
        if(logoImg==null){
            return null;
        }

        int bitmapWidth =bitmap.getWidth();
        int bitmapHeight =bitmap.getHeight();
        int logoImgWidth =logoImg.getWidth();
        int logoImgHeight =logoImg.getHeight();

        if(bitmapWidth==0||bitmapHeight==0){
            return null;
        }
        if(logoImgHeight==0||logoImgWidth==0){
            return bitmap;
        }
        //logo大小为二维码大小1/5
        float scaleFactor = bitmapWidth*1.0f/5 / logoImgWidth; //伸缩
        Bitmap bm =Bitmap.createBitmap(bitmapWidth,bitmapHeight,Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bm);
        canvas.drawBitmap(bitmap,0,0,null);
        canvas.scale(scaleFactor,scaleFactor,bitmapWidth/2,bitmapHeight/2);
        canvas.drawBitmap(logoImg,(bitmapWidth-logoImgWidth)/2,(bitmapHeight -logoImgHeight)/2,null);

        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();

        return bitmap;
    }
}
