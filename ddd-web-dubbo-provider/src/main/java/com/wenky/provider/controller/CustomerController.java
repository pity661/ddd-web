package com.wenky.provider.controller;

import com.wenky.provider.dao.entity.Customer;
import com.wenky.provider.service.CustomerService;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2022-12-30 09:59
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // curl http://127.0.0.1:8081/info/customer
    @GetMapping(value = "/info/customer")
    public Customer name(HttpServletRequest request) {
        return customerService.getByName("wenky");
    }

    // curl http://127.0.0.1:8081/sleep
    @GetMapping(value = "/sleep")
    public String sleep(HttpServletRequest request) throws InterruptedException {
        TimeUnit.MINUTES.sleep(2);
        return "SUCCESS";
    }
}
