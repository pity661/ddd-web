package com.wenky.provider.dubbo.service;

import com.wenky.commons.dubbo.model.DubboInvokeResult;
import com.wenky.provider.dao.entity.Customer;
import java.io.IOException;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-07 11:34
 */
// 必须和IHelloService在同一个包下
public class IHelloServiceMock implements IHelloService {

    @Override
    public String getName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Customer getByName(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public DubboInvokeResult getWrapperByName(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Customer customer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public DubboInvokeResult IOError() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public DubboInvokeResult RuntimeError() {
        throw new UnsupportedOperationException();
    }

    @Override
    public DubboInvokeResult BizError() {
        return DubboInvokeResult.exception(new IOException(""));
    }

    @Override
    public Integer timeout() throws InterruptedException {
        throw new UnsupportedOperationException();
    }
}
