package com.wu.alipay.util;

import com.xtc.common.log.LogUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密
 * Created by Administrator on 2016/8/12.
 */
public class ParseMD5 {
    /**
     * @param str
     * @return 32位小写MD5
     */
    public static String parseStrToMd5L32(String str) {
        String reStr = null;
        byte[] bytes =null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5"); //获得一个MessageDigest对象
            bytes = md5.digest(str.getBytes());     //把字符串转为byte
        } catch (NoSuchAlgorithmException e) {
            LogUtil.e("NoSuchAlgorithmException");
            e.printStackTrace();
        }
       StringBuffer sb =new StringBuffer();
        for(byte b:bytes){
            int bt = b&0xff;
            if(bt<16){
                sb.append(0);
            }
            sb.append(Integer.toHexString(bt));
        }
        reStr = sb.toString();
        return reStr;
    }
    /**
     * 32位大写MD5
     *
     * @param str
     * @return 32位大写MD5
     */
    public static String parseStrToMd5U32(String str) {
        String reStr = parseStrToMd5L32(str);
        if (reStr != null) {
            reStr = reStr.toUpperCase();
        }
        return reStr;
    }
    /**
     * @param str
     * @return 16位小写MD5
     */
    public static String parseStrToMd5U16(String str) {
        String reStr = parseStrToMd5L32(str);
        if (reStr != null) {
            reStr = reStr.toUpperCase().substring(8, 24);
        }
        return reStr;
    }
    /**
     * @param str
     * @return 16位大写MD5
     */
    public static String parseStrToMd5L16(String str) {
        String reStr = parseStrToMd5L32(str);
        if (reStr != null) {
            reStr = reStr.substring(8, 24);
        }
        return reStr;
    }
}
