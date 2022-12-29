package com.wenky.ddd.domain.customer.gateway;

import com.wenky.ddd.domain.customer.Customer;

public interface CustomerGateway {
    Customer getByById(String customerId);

    String getName();
}
