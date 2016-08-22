package com.wu.alipay.util;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2016/8/12.
 */
public class ActivityTool {
    public static void startActivity(Context context, Class<?> clazz){
        Intent intent = new Intent(context, clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
