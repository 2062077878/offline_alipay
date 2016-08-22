package com.wu.alipay.view.init;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Administrator on 2016/8/10.
 */
public interface InitView extends MvpView{
    void finishSelf();
    void startToUse();
    void startToBinding();
    void startToSetPassword();
}
