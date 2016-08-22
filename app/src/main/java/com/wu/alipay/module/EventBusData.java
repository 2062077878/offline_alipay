package com.wu.alipay.module;

/**
 * Created by Administrator on 2016/8/10.
 */
public class EventBusData {
    /**
     * 绑定成功
     */
    public static final int ON_BONDING_SUCCESS = 0x01;

    /**
     * 解绑
     */
    public static final int ON_SWITCH_TO_UNBOND = 0x02;

    /**
     * type
     */
    private int type;

    /**
     * contacts data
     */
    private Object data;

    public EventBusData(int type, Object data) {
        this.type = type;
        this.data = data;
    }

    public int getType() {
        return type;
    }


    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return "EventBusData{" +
                "type=" + type +
                ", data=" + data +
                '}';
    }
}
