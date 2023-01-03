package com.wenky.ddd.domain.mapper;

import com.wenky.ddd.customer.CustomerInfoDTO;
import com.wenky.ddd.domain.entity.Customer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 客户信息记录表 Mapper 接口
 * </p>
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
