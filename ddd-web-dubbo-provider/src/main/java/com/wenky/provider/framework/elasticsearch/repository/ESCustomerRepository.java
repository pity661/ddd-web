package com.wenky.provider.framework.elasticsearch.repository;

import com.wenky.provider.framework.elasticsearch.entity.ESCustomer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author 克林
 * @date 2023/7/13 20:22
 */
public interface ESCustomerRepository extends ElasticsearchRepository<ESCustomer, Long> {}
