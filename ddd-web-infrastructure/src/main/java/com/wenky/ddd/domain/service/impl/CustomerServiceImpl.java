package com.wenky.ddd.domain.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.wenky.ddd.domain.entity.Customer;
import com.wenky.ddd.domain.mapper.CustomerMapper;
import com.wenky.ddd.domain.service.CustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 客户信息记录表 服务实现类
 * </p>
 *
 * @author generator
 * @since 2023-01-03
 */
@Slf4j
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

    @Override
    public Customer getByName(String name) {
        log.info(JSONObject.toJSONString(baseMapper.findInfoByName(name)));
        return baseMapper.findByName(name);
//        return lambdaQuery().eq(Customer::getName, name).one();
    }
}
