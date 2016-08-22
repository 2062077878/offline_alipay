package com.wu.alipay.util;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;

import java.lang.ref.SoftReference;

import static android.provider.Settings.System.SCREEN_BRIGHTNESS;
/**
 * Created by Administrator on 2016/8/10.
 */
public class LightManager {

    private static final int MAX_LIGHT_TIME = 180000;
    private static final int MAX_LIGHTNESS = 255;

    private static SoftReference<LightManager> softRefLightManager;

    private boolean isSet;

    private int currentLightTime =  30000;
    private int currentLightness = 100;

    private LightManager(){}

    public static synchronized LightManager getInstance(){
        if(softRefLightManager ==null){
            softRefLightManager = new SoftReference<>(new LightManager());
        }
        LightManager lightManager = softRefLightManager.get();

        if(lightManager ==null){
            lightManager = new LightManager();
            softRefLightManager = new SoftReference<>(lightManager);
        }
        return lightManager;
    }

    /*
    * 设置屏幕亮度和亮屏时间
    * */

    public void setScreen(Context context) {
       if(isSet){
           return;
       }
        isSet =true;
        ContentResolver contentResolver = context.getContentResolver();

        currentLightness = Settings.System.getInt(contentResolver,SCREEN_BRIGHTNESS,30000);
        currentLightTime = Settings.System.getInt(contentResolver, Settings.System.SCREEN_OFF_TIMEOUT, 100);

        Settings.System.putInt(contentResolver, Settings.System.SCREEN_OFF_TIMEOUT, MAX_LIGHT_TIME);
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, MAX_LIGHTNESS);
    }
    /*
    * 恢复屏幕亮度和时间
    * */
    public void revertScreen(Context context){
        if(! isSet){
            return;
        }
        isSet =false;
        ContentResolver contentResolver = context.getContentResolver();
        Settings.System.putInt(contentResolver,
                Settings.System.SCREEN_OFF_TIMEOUT, currentLightTime);
        Settings.System.putInt(contentResolver,
                Settings.System.SCREEN_BRIGHTNESS, currentLightness);
    }
}
