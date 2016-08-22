package com.wu.alipay.view.binding;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alipay.android.watchsdk.api.AlipayWatchApi;
import com.alipay.android.watchsdk.api.service.IAlipayWatchFsmService;
import com.alipay.android.watchsdk.biz.impl.AlipayWatchFsmServiceImpl;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.wu.alipay.R;
import com.wu.alipay.module.EventBusData;
import com.wu.alipay.module.contants.Constants;
import com.wu.alipay.util.CreateQR;
import com.wu.alipay.util.ImageCacheUtil;
import com.wu.alipay.util.LightManager;
import com.wu.alipay.view.password.PasswordTipActivity;
import com.xtc.common.base.BaseActivity;
import com.xtc.common.log.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/8/10.
 */
public class BindingActivity extends BaseActivity {

    @Bind(R.id.iv_alipay_binding_img)
     ImageView ivAlipayBindingImg;
    @Bind(R.id.rl_alipay_binding_protocol)
     RelativeLayout rlAlipayBindingProtocol;
    @Bind(R.id.rl_alipay_binding_tip)
     RelativeLayout rlAlipayBindingTip;
    @Bind(R.id.ll_alipay_binding_qrcode)
     LinearLayout llAlipayBindingQrcode;

    private IAlipayWatchFsmService watchFsmService;
    @NonNull
    @Override
    public MvpPresenter createPresenter() {
        return new MvpBasePresenter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding);

        ButterKnife.bind(this);

        EventBus.getDefault().register(this);

    }

    @Override
    public void onStart(){
        super.onStart();
        initView();
        initData();
        showScanCode();
    }
    public void initView(){
        super.initView();
        rlAlipayBindingProtocol.setVisibility(View.VISIBLE);
        rlAlipayBindingTip.setVisibility(View.GONE);
        llAlipayBindingQrcode.setVisibility(View.GONE);
    }
    public void initData(){
        super.initData();
        watchFsmService = AlipayWatchApi.getAlipayWatchFsmService();
    }
    private void showScanCode() {
        String scanCode = watchFsmService.getScanCode();     //获取扫描码
        LogUtil.i("scanCode = " + scanCode);
        if(!TextUtils.isEmpty(scanCode)){
            CreateQR qr =new CreateQR(Constants.Qrcode.QRCODE_WIDTH ,Constants.Qrcode.QRCODE_HEIGHT);
            Bitmap logo = ImageCacheUtil.getInstance().getBitmap(this,R.mipmap.alipay_logo1);  //图片复用，进行缓存
            Bitmap bitmap = qr.createQRImage(scanCode,logo);
            ivAlipayBindingImg.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
    }
    @Override
    protected void onPause(){
        super.onPause();
    }
    @Override
    protected void onDestroy(){
        LightManager.getInstance().revertScreen(this);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /*
    绑定或解绑触发
    */
    @Subscribe
    public void onEventMainThread(EventBusData eventBusData){
        int type = eventBusData.getType();
        if(type ==EventBusData.ON_BONDING_SUCCESS){
            startActivity(new Intent(this,PasswordTipActivity.class));
            finish();
        }
    }

    @OnClick({R.id.bt_alipay_protocol_agree, R.id.bt_alipay_binding_agree})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_alipay_protocol_agree:
                rlAlipayBindingProtocol.setVisibility(View.GONE);
                rlAlipayBindingTip.setVisibility(View.VISIBLE);
                llAlipayBindingQrcode.setVisibility(View.GONE);
                break;
            case R.id.bt_alipay_binding_agree:
                rlAlipayBindingProtocol.setVisibility(View.GONE);
                rlAlipayBindingTip.setVisibility(View.GONE);
                llAlipayBindingQrcode.setVisibility(View.VISIBLE);

               LightManager.getInstance().setScreen(this);
                break;
            default:
                LogUtil.e("no such view");
                break;
        }
    }
}
