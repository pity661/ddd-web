package com.wenky.ddd.customer;

import com.wenky.ddd.domain.customer.Customer;
import com.wenky.ddd.domain.customer.gateway.CustomerGateway;
import com.wenky.provider.dubbo.service.IHelloService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerGatewayImpl implements CustomerGateway {
//    @Autowired private CustomerMapper customerMapper;

    @DubboReference(version = "1.0")
    private IHelloService iHelloService;

    public Customer getByById(String customerId) {
//        CustomerDO customerDO = customerMapper.getById(customerId);
        // Convert to Customer
        return null;
    }

    @Override
    public String getName() {
        return iHelloService.getName();
    }
}
