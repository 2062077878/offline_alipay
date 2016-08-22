package com.wu.alipay.module.shared;

import android.content.Context;

import com.wu.alipay.module.contants.Constants;
import com.xtc.common.data.shared.SharedManager;

/**
 * SharedPreference数据存储工具类，保存密码错误次数和时间
 * Created by Administrator on 2016/8/12.
 */
public class ShareTool {
    /**
     * 获取使用密码
     */
    public static String getPassword(Context context) {
        return SharedManager.getInstance(context).getString(Constants.Password.PASSWORD, "");
    }

    /**
     * 保存使用密码
     *
     * @param password 密码
     */
    public static boolean savePassword(Context context, String password) {
        return SharedManager.getInstance(context).saveString(Constants.Password.PASSWORD, password);
    }

    /**
     * 获取上次密码错误的时间
     */
    public static long getLastErrorTime(Context context) {
        return SharedManager.getInstance(context)
                .getLong(Constants.Password.LAST_ERROR_TIME, 0);
    }

    /**
     * 保存上次密码错误的时间
     *
     * @param lastTime 密码错误的时间
     */
    public static boolean saveLastErrorTime(Context context, long lastTime) {
        return SharedManager.getInstance(context).saveLong(Constants.Password.LAST_ERROR_TIME, lastTime);
    }


    /**
     * 获取密码错误次数
     */
    public static int getPasswordErrorCount(Context context) {
        return SharedManager.getInstance(context)
                .getInt(Constants.Password.PASSWORD_ERROR_COUNT, 0);
    }

    /**
     * 保存密码错误次数
     *
     * @param count 密码错误次数
     */
    public static boolean savePasswordErrorCount(Context context, int count) {
        return SharedManager.getInstance(context)
                .saveInt(Constants.Password.PASSWORD_ERROR_COUNT, count);
    }

    /*
    * 是否需要重置密码
    * */
  /*  public static boolean saveResetPassword(Context context, boolean reset) {
        return SharedManager.getInstance(context)
                .saveBoolean(Constants.Password.RESET, reset);
    }
    public static boolean getResetPassword(Context context) {
        return SharedManager.getInstance(context)
                .getBoolean(Constants.Password.RESET, false);
    }*/
}
