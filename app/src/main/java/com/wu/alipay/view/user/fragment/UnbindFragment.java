package com.wu.alipay.view.user.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.wu.alipay.R;
import com.wu.alipay.util.AlipayUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 解除绑定界面
 * Created by Administrator on 2016/8/17.
 */
public class UnbindFragment extends PayBaseFragment{

    @Override
    public MvpPresenter createPresenter() {
        return new MvpBasePresenter();
    }

    public static PayBaseFragment newInstance(){
        UnbindFragment unbindFragment = new UnbindFragment();
        return unbindFragment;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_unbind,null);
        ButterKnife.bind(this,view);
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.bt_alipay_unbind)
    public void onClick(){
        AlipayUtil.unbind(this.getActivity());
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
