package com.wu.alipay.presenter.password;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.wu.alipay.module.shared.ShareTool;
import com.wu.alipay.util.ActivityTool;
import com.wu.alipay.util.ParseMD5;
import com.wu.alipay.view.password.IPasswordSetView;
import com.wu.alipay.view.password.PasswordConfictActivity;
import com.wu.alipay.view.password.PasswordFinishedActivity;
import com.xtc.common.log.LogUtil;
import com.xtc.common.system.WatchDevice;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 首次设置密码 P
 * Created by Administrator on 2016/8/11.
 */
public class PasswordSetPresenter extends MvpBasePresenter<IPasswordSetView> {

    private  PasswordCommon passwordCommon;
    private Context context;
    private boolean isConfirm =false;
    private String firstPassword ;

    public PasswordSetPresenter(Context context){
        firstPassword ="";
        this.context = context;
        passwordCommon = new PasswordCommon();
        passwordCommon.init();

    }

    public void setPasswordText(List<TextView> textViewList) {
        isConfirm = false;
        passwordCommon.setTextViewList(textViewList);
    }

    private void reset() {
        isConfirm = false;
        passwordCommon.reset();
    }
    public void deletePassword() {
         passwordCommon.deletePassword();
    }

    public void addPassword(String clickValue) {
        boolean ok = passwordCommon.addPassword(clickValue);
        if(ok){
            finishedSet();
        }
    }

    private void finishedSet() {
        if(isConfirm){
            //密码一致
            if(!TextUtils.isEmpty(firstPassword)&&(passwordCommon.currentInput).equals(firstPassword)){
                String password = firstPassword + new WatchDevice(context).getBindNumber(); //设备码
                password = ParseMD5.parseStrToMd5L32(password);   //加密
                ShareTool.savePassword(context,password);     //保存
                ActivityTool.startActivity(context, PasswordFinishedActivity.class);
                if(isViewAttached()){
                    getView().finishActivity();
                }
            }else{
                LogUtil.i("两次输入密码不一致");
                reset();
                firstPassword="";
                ActivityTool.startActivity(context,PasswordConfictActivity.class);
            }
        }else{
            firstPassword = passwordCommon.currentInput; //保存第一次的密码
            reset();
            isConfirm = true;
            if(isViewAttached()){
                getView().showPasswordConfirmView();
            }
        }
    }
}
