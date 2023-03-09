package com.wenky.ddd.customer.dubbo;

import com.wenky.ddd.ITest;
import com.wenky.provider.dao.entity.Customer;
import java.util.concurrent.CompletableFuture;
import org.apache.dubbo.rpc.RpcContext;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AsyncWrapperTest extends ITest {

    @Autowired private AsyncWrapper asyncWrapper;

    @Test
    public void test() {
        // 异步调用getByName方法不会直接返回结果，需要通过future获取最终执行结果
        Customer customer = asyncWrapper.getByName("wenky");
        Assert.assertNull(customer);

        // 不能多次异步调用getByName方法，RpcContext只会保留最后一次调用的future
        CompletableFuture<Customer> future = RpcContext.getContext().getCompletableFuture();
        customer = future.join();
        Assert.assertNotNull(customer);

        // 多次调用返回对象不同，但是序列化结果一致
        Customer customer1 = asyncWrapper.getByName("wenky");
        Assert.assertNull(customer1);
        future = RpcContext.getContext().getCompletableFuture();
        customer1 = future.join();
        Assert.assertFalse(customer == customer1);
        Assert.assertEquals(customer, customer1);

        Customer customer2 = asyncWrapper.getByAsyncFuture("wenky");
        Assert.assertNotNull(customer2);
    }
}
