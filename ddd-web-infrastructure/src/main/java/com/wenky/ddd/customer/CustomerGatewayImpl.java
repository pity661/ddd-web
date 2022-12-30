package com.wenky.ddd.customer;

import com.wenky.ddd.converter.ConsumerConverter;
import com.wenky.ddd.domain.customer.CustomerDO;
import com.wenky.ddd.domain.customer.gateway.CustomerGateway;
import com.wenky.provider.dubbo.service.IHelloService;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerGatewayImpl implements CustomerGateway {
    //    @Autowired private CustomerMapper customerMapper;

    @DubboReference(version = "1.0")
    private IHelloService iHelloService;

    private final ConsumerConverter consumerConverter;

    @Override
    public String getName() {
        return iHelloService.getName();
    }

    @Override
    public CustomerDO getByName(String name) {
        com.wenky.provider.dao.entity.Customer customer = iHelloService.getByName(name);
        if (customer == null) {
            throw new RuntimeException(String.format("name: %s, customer not exists!", name));
        }
        return consumerConverter.toDO(customer);
    }
}
