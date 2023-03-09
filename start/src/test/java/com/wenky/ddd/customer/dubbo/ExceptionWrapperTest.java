package com.wenky.ddd.customer.dubbo;

import com.wenky.commons.dubbo.model.DubboInvokeResult;
import com.wenky.commons.dubbo.model.HandleResultEnum;
import com.wenky.ddd.ITest;
import java.io.IOException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-03 14:14
 */
@Slf4j
public class ExceptionWrapperTest extends ITest {

    @Autowired private ExceptionWrapper exceptionWrapper;

    @Test
    public void test() throws IOException {
        DubboInvokeResult first = exceptionWrapper.IOError();
        log.info("---ExceptionWrapperTest---, first:{}", first);
        Assert.assertNull(first);

        DubboInvokeResult second = exceptionWrapper.RuntimeError();
        log.info("---ExceptionWrapperTest---, second:{}", second);
        Assert.assertEquals(
                Optional.ofNullable(second).map(DubboInvokeResult::getCode).orElse(null),
                HandleResultEnum.SYSTEM_ERROR.getCode());
    }
}
