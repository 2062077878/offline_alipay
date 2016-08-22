package com.wu.alipay.util;

import android.app.Activity;
import android.widget.Toast;

import com.alipay.android.watchsdk.api.AlipayWatchApi;
import com.alipay.android.watchsdk.api.common.ResultCode;
import com.alipay.android.watchsdk.api.service.IAlipayWatchFsmService;
import com.wu.alipay.R;
import com.wu.alipay.module.shared.ShareTool;
import com.wu.alipay.view.binding.BindingActivity;

/**
 * 支付宝操作
 * Created by Administrator on 2016/8/12.
 */
public class AlipayUtil {

    public static void unbind(Activity activity){
        IAlipayWatchFsmService watchFsmService = AlipayWatchApi.getAlipayWatchFsmService();
        ResultCode resultCode = watchFsmService.manualUnbond();

        if(resultCode == ResultCode.SUCCESS){
            Toast.makeText(activity,R.string.pay_unbind_success,Toast.LENGTH_SHORT).show();
            ShareTool.savePassword(activity,"");
            ShareTool.saveLastErrorTime(activity,0);
            ShareTool.savePasswordErrorCount(activity,0);

            ActivityTool.startActivity(activity,BindingActivity.class);
            activity.finish();
        }
    }
}
