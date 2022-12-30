package com.wenky.provider.dao.repository;

import com.wenky.provider.dao.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2022-12-30 09:52
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByName(String name);
}
