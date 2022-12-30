package com.wenky.provider.service;

import com.wenky.provider.dao.entity.Customer;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2022-12-30 09:55
 */
public interface CustomerService {

    Customer getByName(String name);
}
