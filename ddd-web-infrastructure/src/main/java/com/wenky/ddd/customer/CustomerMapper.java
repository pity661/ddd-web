package com.wenky.ddd.customer;

//import org.apache.ibatis.annotations.Mapper;

//@Mapper
public interface CustomerMapper {

    CustomerDO getById(String customerId);
}
