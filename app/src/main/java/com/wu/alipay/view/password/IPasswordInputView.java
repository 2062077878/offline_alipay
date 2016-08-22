package com.wu.alipay.view.password;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Administrator on 2016/8/11.
 */
public interface IPasswordInputView extends MvpView{
    void finishActivity();

    void inputError(int errorCount);
}
