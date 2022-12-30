package com.wenky.provider.dubbo.service;

import com.wenky.provider.dao.entity.Customer;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2022-12-29 14:01
 */
public interface IHelloService {

    String getName();

    Customer getByName(String name);
}
