package com.wu.alipay;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;

import com.alipay.android.watchsdk.api.callback.IVendorWatchUICallback;
import com.wu.alipay.module.EventBusData;
import com.wu.alipay.module.shared.ShareTool;
import com.wu.alipay.util.ActivityTool;
import com.wu.alipay.util.BleManager;
import com.wu.alipay.view.binding.BindingActivity;
import com.xtc.common.log.LogUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static com.wu.alipay.module.EventBusData.*;

/**
 * Created by Administrator on 2016/8/10.
 */
public class AppApplication extends Application {

    private List<Activity> startActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        startActivity =new ArrayList<>();

        observeActivityLifecycle();      //生命周期管理

    }

    public IVendorWatchUICallback getIVendorWatchUICallback(){
        return iVendorWatchUICallback;
    }
    private IVendorWatchUICallback iVendorWatchUICallback = new IVendorWatchUICallback() {
        @Override
        public void onSwitchToUnbondUI(boolean b) {
            LogUtil.e("onSwitchToUnbondUI，解除绑定：" + b);
            //Log.e("TAG","app onSwitchToUnbondUI");
            EventBus.getDefault().post(new EventBusData(EventBusData.ON_SWITCH_TO_UNBOND,b)); //解绑成功
            if(b){

                ShareTool.savePassword(getApplicationContext(),"");
                ActivityTool.startActivity(getApplicationContext(),BindingActivity.class);
                finishAll(BindingActivity.class.getSimpleName());
            }
        }

        @Override
        public void onBondingAtStart() {
            LogUtil.i("onBondingAtStart，开始绑定");
        }

        @Override
        public void onBondingAtHalf() {
            LogUtil.i("onBondingAtHalf");
        }

        @Override
        public void onBondingAtFailedEnd() {
            LogUtil.e("onBondingAtFailedEnd，绑定失败");
        }

        @Override
        public void onBondingAtSuccessEnd() {
            LogUtil.w("onBondingAtSuccessEnd，绑定成功");
            EventBus.getDefault().post(new EventBusData(EventBusData.ON_BONDING_SUCCESS, null));//绑定成功

        }

        @Override
        public void onSwitchToBondedUI() {
            LogUtil.i("onSwitchToBondedUI");
        }

        @Override
        public void onSwitchToInvalidUI() {
            LogUtil.i("onSwitchToInvalidUI");
        }
    };
    private void observeActivityLifecycle() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                startActivity.add(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                startActivity.remove(activity);
                if(startActivity.size()<=0){
                    BleManager.getInstance().closeBle();   //退出时关闭Ble
                    System.exit(0);
                }
            }
        });
    }

    public void finishAll(String activityName){
        if(startActivity ==null){
            return;
        }
        LogUtil.i("finishAll "+activityName);
        for(Activity activity :startActivity){
            String className = activity.getLocalClassName();
            if(!TextUtils.isEmpty(className)&&!className.contains(activityName)){
                activity.finish();
            }
        }
    }
}
