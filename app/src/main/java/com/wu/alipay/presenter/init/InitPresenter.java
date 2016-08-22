package com.wu.alipay.presenter.init;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.android.watchsdk.api.AlipayWatchApi;
import com.alipay.android.watchsdk.api.common.FsmState;
import com.alipay.android.watchsdk.api.common.ResultCode;
import com.alipay.android.watchsdk.api.service.IAlipayWatchFsmService;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.wu.alipay.AppApplication;
import com.wu.alipay.R;
import com.wu.alipay.module.shared.ShareTool;
import com.wu.alipay.util.AlipayUtil;
import com.wu.alipay.util.BleManager;
import com.wu.alipay.view.init.InitView;
import com.xtc.common.log.LogUtil;

/**
 * Created by Administrator on 2016/8/10.
 */
public class InitPresenter extends MvpBasePresenter<InitView>{

    private Context context;

     public InitPresenter(Context context){
         this.context = context.getApplicationContext();
     }

    public void init(){
        checkBleStatus();
    }

    //检测蓝牙开启状态
   private void checkBleStatus() {
        if(BleManager.getInstance().isOpen()){
            checkBindingState();
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkBleStatus();
                }
            },500);
        }
    }

    /*
    * 检查支付宝绑定状态
    * */
    private void checkBindingState() {
        IAlipayWatchFsmService watchFsmService = AlipayWatchApi.getAlipayWatchFsmService();  //获取状态机服务
        AppApplication appContext =  (AppApplication) context;
        ResultCode resultCode = watchFsmService.initFsm(context,appContext.getIVendorWatchUICallback());//状态机服务初始化

        LogUtil.i("resultCode = " + resultCode);

        FsmState fsmState = watchFsmService.getCurrentFsmState();  //获取当前状态机状态
        LogUtil.i("fsmState = " + fsmState);

        if(!isViewAttached()){return;}

        if(fsmState ==FsmState.UNBOND){
            if(isViewAttached()) {
                getView().startToBinding();
            }
        }else if(fsmState ==FsmState.BONDED){
            if(hasSetpassword()){
                if(isViewAttached()){
                    getView().startToUse();
                }
            }else {
                if(isViewAttached()){
                    getView().startToSetPassword();
                }
            }
        }else if(fsmState ==FsmState.BONDING){
            Toast.makeText(context,R.string.init_binding,Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, R.string.init_invalid, Toast.LENGTH_SHORT).show();
        }

    }

    private boolean hasSetpassword(){
        String passeord = ShareTool.getPassword(context);
        return !(TextUtils.isEmpty(passeord));
    }
}
