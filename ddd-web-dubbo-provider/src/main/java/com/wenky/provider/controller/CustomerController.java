package com.wenky.provider.controller;

import com.wenky.provider.dao.entity.Customer;
import com.wenky.provider.service.CustomerService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2022-12-30 09:59
 */
@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // curl http://127.0.0.1:8081/info/customer
    @GetMapping(value = "/info/customer")
    public Customer name(HttpServletRequest request) {
        return customerService.getByName("wenky");
    }
}
