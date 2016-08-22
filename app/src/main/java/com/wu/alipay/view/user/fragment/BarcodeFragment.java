package com.wu.alipay.view.user.fragment;

import android.graphics.Bitmap;
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
import com.wu.alipay.util.CreateBarcode;
import com.wu.alipay.util.LightManager;

/**
 * 付款条形码fragme
 * Created by Administrator on 2016/8/13.
 */
public class BarcodeFragment extends PayBaseFragment{

   private String barcode;
    @Override
    public MvpPresenter createPresenter() {
        return new MvpBasePresenter();
    }

    public static PayBaseFragment newInstance(String barcode){
        BarcodeFragment barcodeFragment = new BarcodeFragment();
        Bundle args = new Bundle();
        args.putString("barcode",barcode);
        barcodeFragment.setArguments(args);
        return barcodeFragment;
    }
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        barcode = getArguments()==null ? "":getArguments().getString("barcode");
    }
    @Override
    public void onResume(){
        super.onResume();
        LightManager.getInstance().setScreen(getActivity());       //getActivity() ---return FragmentActivity
    }
    @Override
    public void onPause(){
        super.onPause();
        LightManager.getInstance().revertScreen(getActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_barcode,null);
        ImageView ivBarcode = (ImageView)view.findViewById(R.id.iv_pay_barcode);

        if(!TextUtils.isEmpty(barcode)){
            Bitmap bitmap = CreateBarcode.createBarcode(getActivity(),barcode,
                              Constants.Barcode.BARCODE_WIDTH ,Constants.Barcode.BARCODE_HEIGHT ,false);
            ivBarcode.setImageBitmap(bitmap);
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
