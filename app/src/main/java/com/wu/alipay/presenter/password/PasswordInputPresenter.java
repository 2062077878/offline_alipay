package com.wu.alipay.presenter.password;

import android.app.Service;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.wu.alipay.R;
import com.wu.alipay.module.contants.Constants;
import com.wu.alipay.module.shared.ShareTool;
import com.wu.alipay.util.ActivityTool;
import com.wu.alipay.util.ParseMD5;
import com.wu.alipay.view.password.IPasswordInputView;
import com.wu.alipay.view.user.PayActivity;
import com.xtc.common.data.shared.SharedTool;
import com.xtc.common.log.LogUtil;
import com.xtc.common.system.WatchDevice;
import com.xtc.common.util.SystemDateUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/11.
 */
public class PasswordInputPresenter extends MvpBasePresenter<IPasswordInputView>{
    private  PasswordCommon passwordCommon;
    private Context context;
    private int errorCount;    //错误次数

    public PasswordInputPresenter(Context context){
        this.context = context.getApplicationContext();
       passwordCommon = new PasswordCommon();
        passwordCommon.init();
        errorCount = ShareTool.getPasswordErrorCount(context); //
    }

    public void setTextViewList(List<TextView> textViewList) {
        passwordCommon.setTextViewList(textViewList);
    }

    public void checkError() {
        long lastErrorTime = ShareTool.getLastErrorTime(context);
        long currentTime = SystemDateUtil.getCurrentTimeInMillis();
        long value = currentTime - lastErrorTime;

        if(value>Constants.Retry.MIN_RETRY_TIME){       //错误时间间隔24小时以上
            errorCount =0;
            ShareTool.savePasswordErrorCount(context,0);
            ShareTool.saveLastErrorTime(context,0);
        }else{
            errorCount = ShareTool.getPasswordErrorCount(context);
            if(errorCount < Constants.Password.PASSWORD_ERROR_MAX){      //错误次数小于5，且错误时间间隔24小时以内
                return;
            }else{                  //错误次数大于5，且错误时间间隔24小时以内
                if(isViewAttached()){
                    getView().inputError(errorCount);
                }
            }
        }
    }

    /*删除*/
    public void deletePassword() {
        passwordCommon.deletePassword();
    }

    public void addPassword(String clickValue) {
        boolean ok = passwordCommon.addPassword(clickValue);
        if(ok){
            finishedInput();
        }
    }

    private void finishedInput() {
        if(checkPassword()){  //密码正确
            ShareTool.savePasswordErrorCount(context,0); //错误次数清0
            ActivityTool.startActivity(context,PayActivity.class);
            if (isViewAttached()){
                getView().finishActivity();
            }
        }else {  //密码错误
            errorCount++;
            ShareTool.savePasswordErrorCount(context,errorCount);  //保存错误次数
            if(errorCount==Constants.Password.PASSEORD_ERROR_TIP||errorCount==Constants.Password.PASSWORD_ERROR_MAX){
                passwordCommon.reset();
                if(errorCount==Constants.Password.PASSWORD_ERROR_MAX){
                    // 超过最大尝试次数
                    ShareTool.saveLastErrorTime(context,SystemDateUtil.getCurrentTimeInMillis());
                    //TODO 请求解绑 推送给服务器
                }
                if(isViewAttached()){
                    getView().inputError(errorCount);
                }
            }else{
                sharkView();
               passwordCommon.inputAfter2s();
            }
        }
    }

    /*抖动效果*/
    private void sharkView() {
        // 密码错误，震动500毫秒，并抖动输入框
        Vibrator vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.vibrate(500);// 震动500毫秒
        Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake);
        List<TextView> textList = passwordCommon.getTextViewList();
        for (int i = 0; i < textList.size(); i++) {
            textList.get(i).startAnimation(shake);
        }
    }

    private boolean checkPassword(){
        String getPassword = ShareTool.getPassword(context);
        String inputPassword = ParseMD5.parseStrToMd5L32(passwordCommon.currentInput+ new WatchDevice(context).getBindNumber());
        if(!TextUtils.isEmpty(getPassword)&&getPassword.equals(inputPassword)){  //加密后和之前保存的加密密码对比
            return true;
        }
        return false;
    }

}
