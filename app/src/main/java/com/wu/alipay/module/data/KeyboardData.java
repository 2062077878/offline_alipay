package com.wu.alipay.module.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/11.
 */
public class KeyboardData {

    public static List<String> getValues(){
        List<String> values = new ArrayList<String>() ;
        values.add("1");
        values.add("2");
        values.add("3");

        values.add("4");
        values.add("5");
        values.add("6");

        values.add("7");
        values.add("8");
        values.add("9");

        values.add("");
        values.add("0");
        values.add("←");
        return values;
    }
}
