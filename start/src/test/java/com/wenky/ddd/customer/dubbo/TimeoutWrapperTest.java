package com.wenky.ddd.customer.dubbo;

import com.wenky.ddd.ITest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-06 10:20
 */
public class TimeoutWrapperTest extends ITest {

    @Autowired private TimeoutWrapper timeoutWrapper;

    @Test
    public void test() throws InterruptedException {
        Integer result = timeoutWrapper.timeout();
        Assert.assertEquals(result.intValue(), 4);
        result = timeoutWrapper.timeout();
        Assert.assertEquals(result.intValue(), 5);
    }
}
