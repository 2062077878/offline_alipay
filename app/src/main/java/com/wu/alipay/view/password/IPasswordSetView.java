package com.wu.alipay.view.password;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Administrator on 2016/8/11.
 */
public interface IPasswordSetView extends MvpView {
    void finishActivity();
    void showPasswordConfirmView();
    void showPasswordSetView();
}
