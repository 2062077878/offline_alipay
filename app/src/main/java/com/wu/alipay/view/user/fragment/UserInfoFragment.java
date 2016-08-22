package com.wu.alipay.view.user.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alipay.android.watchsdk.api.AlipayWatchApi;
import com.alipay.android.watchsdk.api.service.IAlipayWatchBarcodeService;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.wu.alipay.R;
import com.wu.alipay.module.shared.ShareTool;
import com.wu.alipay.util.ActivityTool;
import com.wu.alipay.util.AlipayUtil;
import com.wu.alipay.view.password.PasswordSetActivity;
import com.wu.alipay.view.password.PasswordTipActivity;
import com.xtc.common.log.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 付款用户信息
 * Created by Administrator on 2016/8/13.
 */
public class UserInfoFragment extends PayBaseFragment{

    @Bind(R.id.tv_user_name)
    TextView tvUserName;
    @Bind(R.id.tv_user_account)
    TextView tvUserAccount;

    @Override
    public MvpPresenter createPresenter() {
        return new MvpBasePresenter();
    }
    public static PayBaseFragment newInstance(){
        UserInfoFragment userInfoFragment = new UserInfoFragment();
        return userInfoFragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view  = inflater.inflate(R.layout.fragment_user_info,null);

        ButterKnife.bind(this,view);

  /*      tvUserName = (TextView) view.findViewById(R.id.tv_user_name);
        tvUserAccount = (TextView) view.findViewById(R.id.tv_user_account);*/

        init();
        return view;
    }

    private void init() {
        IAlipayWatchBarcodeService watchBarcodeService = AlipayWatchApi.getAlipayWatchBarcodeService();
        String userInfo = watchBarcodeService.getUserInfo();   //获得用户信息

        try {
            JSONObject jsonObj = new JSONObject(userInfo);
            String userName = jsonObj.getString("userName");
            String userAccount = jsonObj.getString("userAccount");
            tvUserName.setText(userName);
            tvUserAccount.setText(userAccount);
        } catch (JSONException e) {
            LogUtil.e(e);
        }

    }

    @OnClick(R.id.bt_alipay_reset)    //重置密码
    public void onClick() {
       // ShareTool.savePassword(getContext(),"");
        ActivityTool.startActivity(getContext(), PasswordTipActivity.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    @Override
    public void onClickButton() {

    }

    @Override
    public boolean onUpSlide() {
        return false;
    }

    @Override
    public boolean onDownSlide() {
        return false;
    }

    @Override
    public boolean onLeftSlide() {
        return false;
    }

    @Override
    public boolean onRightSlide() {
        return false;
    }

}
