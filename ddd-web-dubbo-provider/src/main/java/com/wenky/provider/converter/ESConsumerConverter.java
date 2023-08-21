package com.wenky.provider.converter;

import com.wenky.provider.dao.entity.Customer;
import com.wenky.provider.framework.elasticsearch.entity.ESCustomer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author 克林
 * @date 2023/7/13 20:47
 */
@Mapper(componentModel = "spring")
public interface ESConsumerConverter {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sid", source = "id")
    ESCustomer toES(Customer customer);
}
