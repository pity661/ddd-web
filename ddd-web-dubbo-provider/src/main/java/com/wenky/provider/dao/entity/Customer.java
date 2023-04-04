package com.wenky.provider.dao.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2022-12-30 09:49
 */
@Data
@Entity
public class Customer extends BaseEntity {

    private String name;
    private Integer age;
    private String phone;
    @Transient
    private String port;
}
