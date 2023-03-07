package com.wenky.ddd.customer.dubbo;

import com.wenky.ddd.ITest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-06 15:29
 */
public class TokenWrapperTest extends ITest {

    @Autowired private TokenWrapper tokenWrapper;

    @Test
    public void test() {
        try {
            String result = tokenWrapper.getName();
            Assert.assertNull(result);
        } catch (Exception e) {
            Assert.assertNotNull(e);
        }
    }
}
