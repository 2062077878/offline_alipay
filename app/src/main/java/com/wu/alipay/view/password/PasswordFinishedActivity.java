package com.wu.alipay.view.password;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.wu.alipay.R;
import com.xtc.common.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PasswordFinishedActivity extends BaseActivity {

    @NonNull
    @Override
    public MvpBasePresenter createPresenter() {
        return new MvpBasePresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_finished);

        ButterKnife.bind(this);
    }


    @OnClick(R.id.bt_password_use)
    public void onClick(){
        startActivity(new Intent(this,PasswordInputActivity.class));
        finish();
    }
}
