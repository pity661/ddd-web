package com.wenky.ddd.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wenky.ddd.domain.entity.Customer;

/**
 * 客户信息记录表 服务类
 *
 * @author generator
 * @since 2023-01-03
 */
public interface CustomerService extends IService<Customer> {

    Customer getByName(String name);
}
