package com.wu.alipay.util;

import android.bluetooth.BluetoothAdapter;
import java.lang.ref.SoftReference;

/**
 * Created by Administrator on 2016/8/10.
 * 蓝牙管理工具
 */
public class BleManager {
    private BluetoothAdapter bluetoothAdapter;

    private static boolean hasOpenBle;  //是否调用开启蓝牙功能

    private static SoftReference<BleManager> bleManagerSoftReference;

    private BleManager(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }
    /*
    *用软引用获得BleManager对象
    */
    public static synchronized BleManager getInstance(){
        if(bleManagerSoftReference==null){
            bleManagerSoftReference =new SoftReference<>(new BleManager());
        }
        BleManager bleManager =bleManagerSoftReference.get();  //获得软引用对象
        if(bleManager==null){
            bleManager =new BleManager();
            bleManagerSoftReference =new SoftReference<>(bleManager);
        }
        return bleManager;
    }

    public boolean isOpen(){
        return BluetoothAdapter.STATE_ON == bluetoothAdapter.getState();
    }

    /*
    * 打开BLE
    * */
    public void openBle(){
        if(BluetoothAdapter.STATE_ON != bluetoothAdapter.getState()){
            bluetoothAdapter.enable();
            hasOpenBle = true;
        }
    }
    /*
    * 关闭BLE
    * */
    public void closeBle(){
        if(hasOpenBle && BluetoothAdapter.STATE_OFF !=bluetoothAdapter.getState()){
            bluetoothAdapter.disable();
            hasOpenBle =false;
        }
    }

}
