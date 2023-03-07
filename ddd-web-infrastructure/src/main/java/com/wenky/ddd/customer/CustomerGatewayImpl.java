package com.wenky.ddd.customer;

import com.wenky.ddd.converter.ConsumerConverter;
import com.wenky.ddd.domain.customer.CustomerDO;
import com.wenky.ddd.domain.customer.gateway.CustomerGateway;
import com.wenky.provider.dao.entity.Customer;
import com.wenky.provider.dubbo.service.IHelloService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component(value = "customerGatewayImpl")
@RequiredArgsConstructor
public class CustomerGatewayImpl implements CustomerGateway {

    @DubboReference(
            // 指定timeout覆盖配置文件全局consumer配置
            timeout = 2 * 1000,
            version = "1.0",
            group = "custom1")
    private IHelloService iHelloService;

    private final ConsumerConverter consumerConverter;

    @Override
    public String getName() {
        return iHelloService.getName();
    }

    @Override
    public CustomerDO getByName(String name) {
        // 传递隐式参数
        RpcContext.getContext().setAttachment("index", "1");
        Customer customer = iHelloService.getByName(name);
        return consumerConverter.toDO(customer);
    }

    @Override
    public void error() {
        try {
            iHelloService.RuntimeError();
        } catch (Exception e) {
            log.error("consumer invoke exception", e);
        }
    }
}
