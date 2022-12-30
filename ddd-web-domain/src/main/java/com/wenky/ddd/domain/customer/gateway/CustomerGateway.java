package com.wenky.ddd.domain.customer.gateway;

import com.wenky.ddd.domain.customer.CustomerDO;

public interface CustomerGateway {

    String getName();

    CustomerDO getByName(String name);
}
