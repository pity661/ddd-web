package com.wenky.ddd.converter;

import com.wenky.ddd.domain.customer.CustomerDO;
import com.wenky.ddd.dto.clientobject.CustomerCO;
import org.mapstruct.Mapper;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2022-12-30 16:26
 */
@Mapper(componentModel = "spring")
public interface CustomerConverter {
    CustomerCO toCO(CustomerDO customerDO);
}
