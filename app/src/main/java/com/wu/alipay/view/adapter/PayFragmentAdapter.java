package com.wu.alipay.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wu.alipay.view.user.fragment.BarcodeFragment;
import com.wu.alipay.view.user.fragment.QrFragment;
import com.wu.alipay.view.user.fragment.UnbindFragment;
import com.wu.alipay.view.user.fragment.UserInfoFragment;

/**
 * 支付界面viewpage适配器
 * Created by Administrator on 2016/8/13.
 */
public class PayFragmentAdapter extends FragmentPagerAdapter{

    private String  code;
    private static final int pageNum =4;
    public PayFragmentAdapter(FragmentManager fm,String code) {
        super(fm);
        this.code =code;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return  QrFragment.newInstance(code);
            case 1:
                return BarcodeFragment.newInstance(code);
            case 2:
                 return UserInfoFragment.newInstance();
            case 3:
                return UnbindFragment.newInstance();
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return pageNum;
    }
}
