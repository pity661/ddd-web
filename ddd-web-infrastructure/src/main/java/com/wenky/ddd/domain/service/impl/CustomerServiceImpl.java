package com.wenky.ddd.domain.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wenky.ddd.domain.entity.Customer;
import com.wenky.ddd.domain.mapper.CustomerMapper;
import com.wenky.ddd.domain.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 客户信息记录表 服务实现类
 *
 * @author generator
 * @since 2023-01-03
 */
@Slf4j
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer>
        implements CustomerService {

    @Override
    public Customer getByName(String name) {
        log.info(JSONObject.toJSONString(baseMapper.findInfoByName(name)));
        return baseMapper.findByName(name);
        //        return lambdaQuery().eq(Customer::getName, name).one();
    }
}
