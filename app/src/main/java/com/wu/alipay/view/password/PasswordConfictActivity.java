package com.wu.alipay.view.password;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.wu.alipay.R;
import com.wu.alipay.util.ActivityTool;
import com.xtc.common.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/*
*密码不一致提示
* */
public class PasswordConfictActivity extends BaseActivity {

    @NonNull
    @Override
    public MvpPresenter createPresenter() {
        return new MvpBasePresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_confict);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.bt_password_reset)
    public void onClick(){
        ActivityTool.startActivity(this,PasswordSetActivity.class);
        finish();
    }

}
