package com.wenky.ddd.dto;

import com.alibaba.cola.dto.Query;
import lombok.Data;

@Data
public class CustomerListByNameQry extends Query {
    private String name;
    private String customerId;
}
