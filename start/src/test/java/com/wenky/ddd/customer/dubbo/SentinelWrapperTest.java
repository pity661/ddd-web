package com.wenky.ddd.customer.dubbo;

import com.wenky.commons.dubbo.model.DubboInvokeResult;
import com.wenky.commons.dubbo.model.HandleResultEnum;
import com.wenky.ddd.ITest;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.RpcException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-07 18:27
 */
@Slf4j
@AutoConfigureMockMvc
public class SentinelWrapperTest extends ITest {

    @Autowired private MockMvc mockMvc;

    @Autowired private SentinelWrapper sentinelWrapper;

    @Test
    @Before
    public void mockMvcTest() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/info/wenky")).andReturn();
        log.info("mockMvcResult: {}", result.getResponse().getContentAsString());
    }

    // 直接抛出异常会导致熔断
    @Test
    public void IOErrorTest() throws InterruptedException {
        DubboInvokeResult result = IOErrorInvoke();
        log.info("---SentinelWrapperTest---, first:{}", result);
        Assert.assertNull(result); // 1: null
        result = IOErrorInvoke();
        log.info("---SentinelWrapperTest---, second:{}", result);
        Assert.assertNull(result); // 2: null
        result = IOErrorInvoke();
        log.info("---SentinelWrapperTest---, third:{}", result);
        Assert.assertNull(result); // 3: null
        result = IOErrorInvoke();
        log.info("---SentinelWrapperTest---, forth:{}", result);
        Assert.assertNotNull(result); // 4

        TimeUnit.SECONDS.sleep(1);
        result = IOErrorInvoke();
        log.info("---SentinelWrapperTest---, fifth:{}", result);
        Assert.assertNotNull(result);

        TimeUnit.SECONDS.sleep(5);
        result = IOErrorInvoke();
        log.info("---SentinelWrapperTest---, sixth:{}", result);
        Assert.assertNull(result);
    }

    // 返回BizError也会造成熔断
    @Test
    public void RuntimeErrorTest() throws InterruptedException {
        DubboInvokeResult result = sentinelWrapper.BizError();
        log.info("---SentinelWrapperTest---, first:{}", result);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getCode(), HandleResultEnum.SYSTEM_ERROR.getCode());
        result = sentinelWrapper.BizError();
        log.info("---SentinelWrapperTest---, second:{}", result);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getCode(), HandleResultEnum.SYSTEM_ERROR.getCode());
        result = sentinelWrapper.BizError();
        log.info("---SentinelWrapperTest---, third:{}", result);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getCode(), HandleResultEnum.SYSTEM_ERROR.getCode());
        result = sentinelWrapper.BizError();
        log.info("---SentinelWrapperTest---, forth:{}", result);
        Assert.assertNotNull(result);
        Assert.assertEquals(
                result.getCode(), HandleResultEnum.SENTINEL_DEGRADE_EXCEPTION.getCode());

        TimeUnit.SECONDS.sleep(1);
        result = sentinelWrapper.BizError();
        log.info("---SentinelWrapperTest---, fifth:{}", result);
        Assert.assertNotNull(result);
        Assert.assertEquals(
                result.getCode(), HandleResultEnum.SENTINEL_DEGRADE_EXCEPTION.getCode());

        TimeUnit.SECONDS.sleep(5);
        result = sentinelWrapper.BizError();
        log.info("---SentinelWrapperTest---, sixth:{}", result);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getCode(), HandleResultEnum.SYSTEM_ERROR.getCode());
    }

    private DubboInvokeResult IOErrorInvoke() {
        try {
            return sentinelWrapper.IOError();
        } catch (RpcException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 走consumer本地缓存，不会触发熔断
    @Test
    public void cacheTest() throws InterruptedException {
        // 缓存预热，避免并发导致所有请求直接打到provider
        sentinelWrapper.getWrapperByNameCached("wenky");

        TimeUnit.MILLISECONDS.sleep(10);

        List<Future<DubboInvokeResult>> futures = invoke(sentinelWrapper::getWrapperByNameCached);
        Assert.assertTrue(
                futures.stream()
                        .allMatch(
                                future -> {
                                    try {
                                        return Objects.equals(
                                                future.get().getCode(),
                                                HandleResultEnum.SUCCESS.getCode());
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    }
                                    return Boolean.FALSE;
                                }));
    }

    // 接口限流
    @Test
    public void wrapperTest() {
        List<Future<DubboInvokeResult>> futures = invoke(sentinelWrapper::getWrapperByName);
        Assert.assertTrue(
                futures.stream()
                        .anyMatch(
                                future -> {
                                    try {
                                        return Objects.equals(
                                                future.get().getCode(),
                                                HandleResultEnum.SENTINEL_FLOW_EXCEPTION.getCode());
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    }
                                    return Boolean.FALSE;
                                }));
    }

    public List<Future<DubboInvokeResult>> invoke(Function<String, DubboInvokeResult> function) {
        Integer COUNT = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(COUNT);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(COUNT);
        List<Future<DubboInvokeResult>> futures =
                IntStream.range(0, COUNT)
                        .mapToObj(
                                i ->
                                        executorService.submit(
                                                () -> {
                                                    try {
                                                        cyclicBarrier.await();
                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    } catch (BrokenBarrierException e) {
                                                        e.printStackTrace();
                                                    }
                                                    return function.apply("wenky");
                                                }))
                        .collect(Collectors.toList());
        executorService.shutdown();
        return futures;
    }
}
