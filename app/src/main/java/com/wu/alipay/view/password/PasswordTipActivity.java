package com.wu.alipay.view.password;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.wu.alipay.R;
import com.xtc.common.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/*
* 密码设置提示界面
* */
public class PasswordTipActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_tip);
        ButterKnife.bind(this);
    }

    @NonNull
    @Override
    public MvpPresenter createPresenter() {
        return new MvpBasePresenter();
    }

    @OnClick(R.id.bt_alipay_password_set)
    public void onClick(){
        startActivity(new Intent(this,PasswordSetActivity.class));
        finish();
    }

}
