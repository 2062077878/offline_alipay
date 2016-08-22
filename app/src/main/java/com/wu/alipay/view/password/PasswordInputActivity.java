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
import com.wu.alipay.module.contants.Constants;
import com.wu.alipay.module.data.KeyboardData;
import com.wu.alipay.presenter.password.PasswordInputPresenter;
import com.wu.alipay.util.AlipayUtil;
import com.wu.alipay.view.adapter.PasswordKeyboardAdapter;
import com.xtc.common.base.BaseActivity;
import com.xtc.common.log.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PasswordInputActivity extends BaseActivity<IPasswordInputView,PasswordInputPresenter> implements IPasswordInputView{

    @Bind(R.id.tv_input_password1)
    TextView tvInputPassword1;
    @Bind(R.id.tv_input_password2)
    TextView tvInputPassword2;
    @Bind(R.id.tv_input_password3)
    TextView tvInputPassword3;
    @Bind(R.id.tv_input_password4)
    TextView tvInputPassword4;
    @Bind(R.id.gv_password_input_keyboard)
    GridView gvPasswordInputKeyboard;
    @Bind(R.id.rl_input_password)
    RelativeLayout rlInputPassword;
    @Bind(R.id.rl_input_error1)
    RelativeLayout rlInputError1;
    @Bind(R.id.rl_input_error2)
    RelativeLayout rlInputError2;


    @NonNull
    @Override
    public PasswordInputPresenter createPresenter() {
        return new PasswordInputPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_input);
        ButterKnife.bind(this);


    }

    @Override
    public void onStart(){
        super.onStart();
        initView();
        initData();
    }
    @Override
    public void initData(){
        super.initData();
        List<TextView> textViewList = new ArrayList<>();
        textViewList.add(tvInputPassword1);
        textViewList.add(tvInputPassword2);
        textViewList.add(tvInputPassword3);
        textViewList.add(tvInputPassword4);

        presenter.setTextViewList(textViewList);
        presenter.checkError();
    }
    @Override
    public void initView(){
        super.initView();

        gvPasswordInputKeyboard.setAdapter(new PasswordKeyboardAdapter(this, KeyboardData.getValues()));

        setListener();
    }

    private void setListener() {
        gvPasswordInputKeyboard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        PasswordInputActivity.this.finish();
    }

    @Override
    public void inputError(int errorCount) {

        if(errorCount == Constants.Password.PASSEORD_ERROR_TIP){
            rlInputError1.setVisibility(View.VISIBLE);
            rlInputError2.setVisibility(View.GONE);
            rlInputPassword.setVisibility(View.GONE);
        }else if(errorCount ==Constants.Password.PASSWORD_ERROR_MAX){
            rlInputError1.setVisibility(View.GONE);
            rlInputError2.setVisibility(View.VISIBLE);
            rlInputPassword.setVisibility(View.GONE);
        }else{
            LogUtil.e("error couunt:"+errorCount);
        }
    }

    @OnClick({R.id.bt_input_try,R.id.bt_input_exit})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.bt_input_try:
                rlInputError1.setVisibility(View.GONE);
                rlInputError2.setVisibility(View.GONE);
                rlInputPassword.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_input_exit:
                AlipayUtil.unbind(this);
                break;
            default:
                break;
        }
    }
}
