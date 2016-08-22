package com.wu.alipay.module.contants;

/**
 * Created by Administrator on 2016/8/12.
 */
public interface Constants {

        interface Retry {
            /*
             * 错误过多能重试的时间间隔
             */
            long MIN_RETRY_TIME = 1000L * 60 * 60 *24;
        }

        interface Password {
            /*
            * 重置密码
            * */
            String RESET ="reset_password";
            /**
             * 密码
             */
            String PASSWORD = "password";

            /**
             * 上次达到最大尝试的时间
             */
            String LAST_ERROR_TIME = "last_error_time";

            /**
             * 密码错误次数
             */
            String PASSWORD_ERROR_COUNT = "password_error_count";
            /*
            * 错误几次给出提示
            * */
            int PASSEORD_ERROR_TIP =3;
            /*
            * 错误几次24小时内禁用
            * */
            int PASSWORD_ERROR_MAX =5;
        }

    interface Qrcode{
        /*
        * 二维码背景宽度
        * */
        int QRCODE_WIDTH = 108;
        /*
      * 二维码背景高度
      * */
        int QRCODE_HEIGHT = 108;
    }

    interface Barcode{
        int BARCODE_WIDTH = 268;
        int BARCODE_HEIGHT = 85;
    }
}
