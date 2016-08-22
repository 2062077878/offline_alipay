package com.wu.alipay;

import com.wu.alipay.http.AlipayUrl;
import com.wu.alipay.http.IAlipayHttp;
import com.wu.alipay.http.bean.Alipay;
import com.xtc.common.net.watch.WatchHttpCreator;
import com.xtc.common.net.watch.WatchHttpResultPrep;
import com.xtc.common.net.watch.bean.NetBaseResult;

import org.junit.Test;

import rx.Observable;
import rx.Observer;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
}