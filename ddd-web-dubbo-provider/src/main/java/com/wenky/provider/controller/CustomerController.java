package com.wenky.provider.controller;

import com.alibaba.cola.dto.SingleResponse;
import com.wenky.provider.dao.entity.Customer;
import com.wenky.provider.service.CustomerService;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Value("${server.port}")
    private String port;

    // curl http://127.0.0.1:8081/info/customer
    @GetMapping(value = "/info/customer")
    public SingleResponse<Customer> name(HttpServletRequest request) {
        Customer customer = customerService.getByName("wenky");
        customer.setPort(port);
        return SingleResponse.of(customer);
    }

    // curl http://127.0.0.1:8081/sleep
    @GetMapping(value = "/sleep")
    public String sleep(HttpServletRequest request) throws InterruptedException {
        TimeUnit.MINUTES.sleep(2);
        return "SUCCESS";
    }

    // should invoke by gateway
    // curl "http://127.0.0.1:8081/sentinel?name=wenky"
    @GetMapping(value = "/sentinel")
    public SingleResponse<Customer> sentinel(
            @RequestParam(required = false) String name, HttpServletRequest request) {
        Customer customer = customerService.getByName(name);
        customer.setPort(port);
        return SingleResponse.of(customer);
    }

    // curl "http://127.0.0.1:8081/elastic/move?name=wenky"
    @GetMapping(value = "/elastic/move")
    public void elasticMove(
            @RequestParam(required = false) String name, HttpServletRequest request) {
        customerService.elasticMove(name);
    }
}
