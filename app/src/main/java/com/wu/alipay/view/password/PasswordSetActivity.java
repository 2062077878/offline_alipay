package com.wu.alipay.view.password;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wu.alipay.R;
import com.wu.alipay.module.data.KeyboardData;
import com.wu.alipay.presenter.password.PasswordSetPresenter;
import com.wu.alipay.view.adapter.PasswordKeyboardAdapter;
import com.xtc.common.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
* 首次设置密码界面
* */
public class PasswordSetActivity extends BaseActivity<IPasswordSetView,PasswordSetPresenter> implements IPasswordSetView{

    @Bind(R.id.tv_set_password1)
    TextView tvSetPassword1;
    @Bind(R.id.tv_set_password2)
    TextView tvSetPassword2;
    @Bind(R.id.tv_set_password3)
    TextView tvSetPassword3;
    @Bind(R.id.tv_set_password4)
    TextView tvSetPassword4;
    @Bind(R.id.gv_password_set_keyboard)
    GridView gvPasswordSetKeyboard;
    @Bind(R.id.rl_show_password)
    RelativeLayout rlShowPassword;
    @Bind(R.id.rl_password_confirm)
    RelativeLayout rlPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_set);
        ButterKnife.bind(this);

        initData();
        initView();
    }

    @NonNull
    @Override
    public PasswordSetPresenter createPresenter() {
        return new PasswordSetPresenter(this);
    }

    @OnClick(R.id.bt_confirm_agree)
    public void onClick(){
        showPasswordSetView();
    }

    @Override
    public void initData(){
        super.initData();
        List<TextView> textViewList = new ArrayList<>();
        textViewList.add(tvSetPassword1);
        textViewList.add(tvSetPassword2);
        textViewList.add(tvSetPassword3);
        textViewList.add(tvSetPassword4);

        presenter.setPasswordText(textViewList);
    }
    @Override
    public void initView(){
        super.initView();
        showPasswordSetView();
        gvPasswordSetKeyboard.setAdapter(new PasswordKeyboardAdapter(this, KeyboardData.getValues()));

        setListener();
    }

    private void setListener() {
        gvPasswordSetKeyboard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                String clickValue = (String) adapterView.getAdapter().getItem(pos);
                if(TextUtils.isEmpty(clickValue)){  //值为空的按钮
                    return;
                }
                if(pos ==adapterView.getAdapter().getCount()-1){  //最后一个按钮
                    presenter.deletePassword();
                }else{
                    presenter.addPassword(clickValue);
                }
            }
        });
    }

    @Override
    public void finishActivity() {
        PasswordSetActivity.this.finish();
    }

    @Override
    public void showPasswordConfirmView() {
        rlShowPassword.setVisibility(View.GONE);
        rlPasswordConfirm.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPasswordSetView() {
        rlShowPassword.setVisibility(View.VISIBLE);
        rlPasswordConfirm.setVisibility(View.GONE);
    }
}
