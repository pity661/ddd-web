package com.wenky.ddd.domain.customer.gateway;

import com.wenky.ddd.domain.customer.Credit;

// Assume that the credit info is in another distributed Service
public interface CreditGateway {
    Credit getCredit(String customerId);
}
