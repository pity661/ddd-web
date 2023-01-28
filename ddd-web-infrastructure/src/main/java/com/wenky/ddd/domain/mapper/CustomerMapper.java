package com.wenky.ddd.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wenky.ddd.customer.CustomerInfoDTO;
import com.wenky.ddd.domain.entity.Customer;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 客户信息记录表 Mapper 接口
 *
 * @author generator
 * @since 2023-01-03
 */
public interface CustomerMapper extends BaseMapper<Customer> {

    @Select("select * from customer where name = #{name}")
    Customer findByName(@Param("name") String name);

    @Select("select name,age from customer where name = #{name}")
    CustomerInfoDTO findInfoByName(@Param("name") String name);
}
