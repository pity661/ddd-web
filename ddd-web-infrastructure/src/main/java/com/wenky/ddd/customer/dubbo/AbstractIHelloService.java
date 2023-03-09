package com.wenky.ddd.customer.dubbo;

import com.wenky.commons.dubbo.model.DubboInvokeResult;
import com.wenky.provider.dao.entity.Customer;
import com.wenky.provider.dubbo.service.IHelloService;
import java.io.IOException;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-03 14:11
 */
public abstract class AbstractIHelloService implements IHelloService {

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
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer timeout() throws InterruptedException {
        throw new UnsupportedOperationException();
    }
}
