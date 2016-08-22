package com.wu.alipay.view.user.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.wu.alipay.R;
import com.wu.alipay.module.contants.Constants;
import com.wu.alipay.util.CreateQR;
import com.wu.alipay.util.ImageCacheUtil;
import com.wu.alipay.util.LightManager;
/**
 * 付款二维码fragme
 * Created by Administrator on 2016/8/13.
 */
public class QrFragment extends PayBaseFragment{

    private String qrcode;
    @Override
    public MvpPresenter createPresenter() {
        return new MvpBasePresenter();
    }
    public static PayBaseFragment newInstance(String qrcode){
        QrFragment qrFragment =new QrFragment();
        Bundle args =new Bundle();
        args.putString("qrcode",qrcode);   //保存二维码数据
        qrFragment.setArguments(args);
        return qrFragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        qrcode = getArguments() ==null ? "" :getArguments().getString("qrcode");
    }
    @Override
    public void onResume(){
        super.onResume();
        LightManager.getInstance().setScreen(getActivity());
    }
    @Override
    public void onPause(){
        super.onPause();
        LightManager.getInstance().revertScreen(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_qrcode,null);
        ImageView ivQrcode = (ImageView)view.findViewById(R.id.iv_pay_qrcode);
        if(!TextUtils.isEmpty(qrcode)){
            CreateQR qr =new CreateQR(Constants.Qrcode.QRCODE_WIDTH ,Constants.Qrcode.QRCODE_HEIGHT);
            Bitmap logo = ImageCacheUtil.getInstance().getBitmap(getActivity(),R.mipmap.alipay_logo1);
            Bitmap bitmap = qr.createQRImage(qrcode,logo);
            ivQrcode.setBackground(new BitmapDrawable(bitmap));
        }
        return view;
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
