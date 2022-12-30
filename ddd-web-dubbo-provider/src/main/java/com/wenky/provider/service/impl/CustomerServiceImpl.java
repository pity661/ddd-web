package com.wenky.provider.service.impl;

import com.wenky.provider.dao.entity.Customer;
import com.wenky.provider.dao.repository.CustomerRepository;
import com.wenky.provider.service.CustomerService;
import lombok.RequiredArgsConstructor;
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

    private final CustomerRepository customerRepository;

    @Override
    public Customer getByName(String name) {
        return customerRepository.findByName(name);
    }
}
