package com.wenky.ddd.converter;

import com.wenky.ddd.domain.customer.CustomerDO;
import com.wenky.provider.dao.entity.Customer;
import org.mapstruct.Mapper;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2022-12-30 16:22
 */
@Mapper(componentModel = "spring")
public interface ConsumerConverter {
    CustomerDO toDO(Customer customer);
}
