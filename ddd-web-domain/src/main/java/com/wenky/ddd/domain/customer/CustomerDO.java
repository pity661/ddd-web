package com.wenky.ddd.domain.customer;

import lombok.Data;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2022-12-30 15:42
 */
@Data
public class CustomerDO {

    private Long id;
    private String name;
    private Integer age;
    private String phone;

    private String port;
}
