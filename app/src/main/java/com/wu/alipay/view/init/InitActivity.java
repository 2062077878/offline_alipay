package com.wu.alipay.view.init;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.wu.alipay.R;
import com.wu.alipay.presenter.init.InitPresenter;
import com.wu.alipay.util.BleManager;
import com.wu.alipay.view.binding.BindingActivity;
import com.wu.alipay.view.password.PasswordInputActivity;
import com.wu.alipay.view.password.PasswordTipActivity;
import com.xtc.common.base.BaseActivity;


/**
 * Created by Administrator on 2016/8/10.
 */
public class InitActivity extends BaseActivity<InitView,InitPresenter> implements InitView{

    private ProgressDialog initDialog;
    /*覆盖BaseActivity的方法*/
    @NonNull
    @Override
    public InitPresenter createPresenter() {      //实现MvpBasePresenter的方法，实例化presenter
        return new InitPresenter(this);
    }

    @Override
    protected void onCreate(Bundle saveInstancceState){
        super.onCreate(saveInstancceState);
        setContentView(R.layout.activity_init);

      //  Log.e("TAG","init onCreate");
    }
    @Override
    protected void onStart(){
        super.onStart();
        initData();
    }
    @Override
    public void initData(){
        super.initData();
        initDialog =new ProgressDialog(this);
        initDialog.setMessage(getString(R.string.initing));
        initDialog.setCancelable(false);
        initDialog.show();
       // Log.e("TAG","init Data");
        if (!BleManager.getInstance().isOpen()) {
            BleManager.getInstance().openBle();
        }
        presenter.init();      //presenter继承自MvpActivity
    }

    @Override
    protected void onDestroy(){
        initDialog.dismiss();
        super.onDestroy();
    }

    @Override
    public void finishSelf() {
        InitActivity.this.finish();
    }

    @Override
    public void startToUse() {
        startActivity(new Intent(this, PasswordInputActivity.class));
        finishSelf();
    }

    @Override
    public void startToBinding() {
        finishSelf();
        startActivity(new Intent(this, BindingActivity.class));
    }

    @Override
    public void startToSetPassword() {
        startActivity(new Intent(this, PasswordTipActivity.class));
        finishSelf();
    }
}
