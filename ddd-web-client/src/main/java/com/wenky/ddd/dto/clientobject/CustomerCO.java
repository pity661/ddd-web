package com.wenky.ddd.dto.clientobject;

import lombok.Data;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2022-12-30 14:34
 */
@Data
public class CustomerCO extends AbstractCO {
    private String name;
    private String phone;
    private Integer age;
}
