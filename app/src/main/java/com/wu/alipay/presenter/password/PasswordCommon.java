package com.wu.alipay.presenter.password;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.widget.TextView;

import com.xtc.common.log.LogUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/16.
 */
public class PasswordCommon {

    public List<TextView> textViewList;
    public String currentInput;  // 当前已经输入的密码
    public int passwordNum = 4;  //密码的位数
    public MyHandler myHandler;

    public void init(){
        textViewList = new ArrayList<>();
        currentInput ="";
        myHandler = new MyHandler(this);
    }
    public List<TextView> getTextViewList() {
        return textViewList;
    }

    public void setTextViewList(List<TextView> textViewList) {
        this.textViewList =textViewList;
        passwordNum = textViewList.size();
        reset();
    }

    public void reset() {
        currentInput ="";
        for (int i=0;i<passwordNum;i++){
            myHandler.removeMessages(i);
            textViewList.get(i).setText("");
        }
    }

    /*删除*/
    public void deletePassword() {
        if (TextUtils.isEmpty(currentInput)) {
            return;
        }
        int index = currentInput.length() - 1;
        TextView textView = textViewList.get(index);
        myHandler.removeMessages(index);
        textView.setText("");
        currentInput = currentInput.substring(0, index);
    }

    public boolean addPassword(String clickValue) {
        int setCount = currentInput.length();
        if(setCount >=passwordNum){
            return false;
        }
        TextView textView = textViewList.get(setCount);
        textView.setText(clickValue);
        myHandler.sendEmptyMessageDelayed(setCount, 500);// 密码显示500毫秒后 * 化
        currentInput = currentInput +clickValue;
        if(currentInput.length()==passwordNum){
          //  finishedInput();
            return true;
        }
        return false;
    }

    public void inputAfter2s() {
        myHandler.sendEmptyMessageDelayed(4,2000);  //密码错误，两秒后重置界面
    }

    private static class MyHandler extends Handler {

        WeakReference<PasswordCommon> target;

        MyHandler(PasswordCommon target) {
            this.target = new WeakReference<>(target);
        }

        @Override
        public void handleMessage(Message msg) {
            if (target == null || target.get() == null) {
                return;
            }
            int what = msg.what;
            switch (what) {
                case 0:
                    target.get().getTextViewList().get(0).setText("*");
                    break;
                case 1:
                    target.get().getTextViewList().get(1).setText("*");
                    break;
                case 2:
                    target.get().getTextViewList().get(2).setText("*");
                    break;
                case 3:
                    target.get().getTextViewList().get(3).setText("*");
                    break;
                case 4:
                    //密码输入错误次数在5次内,重置
                    target.get().reset();
                    break;
                default:
                    LogUtil.w("no this type.");
                    break;
            }
        }
    }
}
