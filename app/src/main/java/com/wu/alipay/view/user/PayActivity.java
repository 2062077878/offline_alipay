package com.wu.alipay.view.user;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.Toast;

import com.alipay.android.watchsdk.api.AlipayWatchApi;
import com.alipay.android.watchsdk.api.service.IAlipayWatchBarcodeService;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.wu.alipay.R;
import com.wu.alipay.module.EventBusData;
import com.wu.alipay.view.adapter.PayFragmentAdapter;
import com.xtc.common.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PayActivity extends BaseActivity {

    @Bind(R.id.pay_viewpager)
    ViewPager payViewpage;
    @Bind(R.id.iv_select_sign0)
    ImageView ivSelectSign0;
    @Bind(R.id.iv_select_sign1)
    ImageView ivSelectSign1;
    @Bind(R.id.iv_select_sign2)
    ImageView ivSelectSign2;
    @Bind(R.id.iv_select_sign3)
    ImageView ivSelectSign3;

    private PayFragmentAdapter mAdapter;

    @NonNull
    @Override
    public MvpPresenter createPresenter() {
        return new MvpBasePresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

    }

    @Override
    public void onStart(){
        super.onStart();
        initData();
        initView();
    }
    protected void onDestroy(){
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEventMainThread(EventBusData event){
        int type = event.getType();
        boolean data = (boolean)event.getData();
        if(type == EventBusData.ON_SWITCH_TO_UNBOND && data){
            finish();
        }
    }

    @Override
    public void initView(){
        super.initView();
        IAlipayWatchBarcodeService iAlipayWatchBarcodeService = AlipayWatchApi.getAlipayWatchBarcodeService();
        if(iAlipayWatchBarcodeService==null){
            Toast.makeText(this,R.string.create_barcode_error,Toast.LENGTH_SHORT).show();
            PayActivity.this.finish();
            return;
        }
        String code = iAlipayWatchBarcodeService.nativeGenerateBarcode(); //条码数据

        mAdapter = new PayFragmentAdapter(getSupportFragmentManager(),code);
        payViewpage.setAdapter(mAdapter);                 // 适配条形码、二维码和用户信息的fragment
        payViewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ivSelectSign0.setBackgroundResource(R.drawable.alipay_selecte_hollow);
                ivSelectSign1.setBackgroundResource(R.drawable.alipay_selecte_hollow);
                ivSelectSign2.setBackgroundResource(R.drawable.alipay_selecte_hollow);
                ivSelectSign3.setBackgroundResource(R.drawable.alipay_selecte_hollow);
                switch (position){
                    case 0: ivSelectSign0.setBackgroundResource(R.drawable.alipay_selecte_solid);
                        break;
                    case 1:ivSelectSign1.setBackgroundResource(R.drawable.alipay_selecte_solid);
                        break;
                    case 2:ivSelectSign2.setBackgroundResource(R.drawable.alipay_selecte_solid);
                        break;
                    case 3:ivSelectSign3 .setBackgroundResource(R.drawable.alipay_selecte_solid);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
