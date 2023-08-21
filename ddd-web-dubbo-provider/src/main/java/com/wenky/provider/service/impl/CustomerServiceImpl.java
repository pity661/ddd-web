package com.wenky.provider.service.impl;

import com.wenky.commons.dubbo.model.exception.BizException;
import com.wenky.provider.converter.ESConsumerConverter;
import com.wenky.provider.dao.entity.Customer;
import com.wenky.provider.dao.repository.CustomerRepository;
import com.wenky.provider.framework.elasticsearch.entity.ESCustomer;
import com.wenky.provider.framework.elasticsearch.repository.ESCustomerRepository;
import com.wenky.provider.service.CustomerService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2022-12-30 09:55
 */
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final ESCustomerRepository esCustomerRepository;
    private final CustomerRepository customerRepository;
    private final ESConsumerConverter ESConsumerConverter;

    @Override
    public Customer getByName(String name) {
        return customerRepository.findByName(name);
    }

    @Override
    public void update(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public void elasticMove(String name) {
        Customer customer =
                Optional.of(name)
                        .filter(StringUtils::isNotBlank)
                        .map(customerRepository::findByName)
                        .orElseThrow(
                                () -> new BizException(String.format("用户不存在, name: %s", name)));

        ESCustomer esCustomer = ESConsumerConverter.toES(customer);

        esCustomerRepository.save(esCustomer);
    }
}
