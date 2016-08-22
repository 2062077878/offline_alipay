package com.wu.alipay.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;

/**
 * Created by Administrator on 2016/8/10.
 */
public class ImageCacheUtil {

    private LruCache<Integer,Bitmap> mLruCache;

    private static ImageCacheUtil mInstance;

    private ImageCacheUtil(){
        setUseMemorySize();
    }

    public static ImageCacheUtil getInstance(){
        if(mInstance ==null){
            synchronized (ImageCacheUtil.class){
                if(mInstance==null){
                    mInstance = new ImageCacheUtil();
                }
            }
        }
        return mInstance;
    }

    private void setUseMemorySize() {
      /*获取最大可用缓存*/
        int maxMemory = (int)Runtime.getRuntime().maxMemory()/1024;      //KB
        int chacheMemory = maxMemory/8;

        mLruCache = new LruCache<Integer, Bitmap>(chacheMemory){
            @Override
            protected int sizeOf(Integer key,Bitmap value){
                return value.getRowBytes()*value.getHeight()/1024;
            };
        };
    }

    /*
    * 加载资源id中的图片Bitmap
    * */
    public Bitmap getBitmap(Context context,int resourceId){
        Bitmap bitmap = null;
        try {
            bitmap = mLruCache.get(resourceId);         //由资源id获得bitmap缓存
            if (bitmap != null && !bitmap.isRecycled()) {
                return bitmap;
            }
            bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
            if (bitmap != null) {
                mLruCache.put(resourceId, bitmap);
            }
        }catch (OutOfMemoryError err){    //内存溢出异常
            clearCache();
            System.gc();
            return getBitmap(context,resourceId);
        }
        return bitmap;
    }

    private void clearCache() {
        if(mLruCache!= null){
            if(mLruCache.size() >0){
                mLruCache.evictAll();
            }
        }
        System.gc();
    }
}
