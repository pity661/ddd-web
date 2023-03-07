package com.wenky.ddd.customer.dubbo;

import com.wenky.commons.dubbo.model.DubboInvokeResult;
import com.wenky.commons.dubbo.model.HandleResultEnum;
import com.wenky.ddd.ITest;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
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
    public void mockMvcTest() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/info/wenky")).andReturn();
        log.info("mockMvcResult: {}", result.getResponse().getContentAsString());
    }

    @Test
    public void cacheTest() throws InterruptedException {
        // 缓存预热，避免并发导致所有请求直接打到provider
        sentinelWrapper.getWrapperByNameCached("wenky");

        TimeUnit.SECONDS.sleep(1);

        invoke(sentinelWrapper::getWrapperByName, HandleResultEnum.SUCCESS.getCode());
    }

    @Test
    public void wrapperTest() {
        invoke(sentinelWrapper::getWrapperByName, 1000);
    }

    public void invoke(Function<String, DubboInvokeResult> function, Integer code) {
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

        Assert.assertTrue(
                futures.stream()
                        .anyMatch(
                                future -> {
                                    try {
                                        return Objects.equals(future.get().getCode(), code);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    }
                                    return Boolean.FALSE;
                                }));
    }
}
