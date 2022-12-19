package com.wenky.ddd.web;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.fastjson.JSON;
import com.wenky.ddd.api.CustomerServiceI;
import com.wenky.ddd.dto.CustomerAddCmd;
import com.wenky.ddd.dto.CustomerListByNameQry;
import com.wenky.ddd.dto.data.CustomerDTO;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Enumeration;

@RequiredArgsConstructor
@RestController
@Validated
@Slf4j
public class CustomerController {

    private final CustomerServiceI customerService;

    // curl http://127.0.0.1:8080/helloworld
    @GetMapping(value = "/helloworld")
    public String helloWorld(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            log.info("NAME:{}, VALUE:{}", headerName, request.getHeader(headerName));
        }
        return "Hello, welcome to COLA world!";
    }

    // curl "http://127.0.0.1:8080/add/customer" -X POST -H 'Content-Type: application/json' -d '{"customerDTO": {"customerId": "wenky", "companyName": "hengchang"}}'

    // curl "http://127.0.0.1:8080/add/customer" -X POST -d @customerData.json -H 'Content-Type: application/json'
    @PostMapping(value = "/add/customer")
    public Response addCustomer(@RequestBody @Valid CustomerAddCmd customerAddCmd) {
        return customerService.addCustomer(customerAddCmd);
    }

    // curl -X POST "http://127.0.0.1:8080/query/customer?name=wenky"

    // curl -X POST http://127.0.0.1:8080/query/customer -d 'name=wenky' -H 'Content-Type: application/x-www-form-urlencoded'
    @PostMapping(value = "/query/customer")
    public MultiResponse<CustomerDTO> listCustomerByName1(
            CustomerListByNameQry customerListByNameQry) {
        System.out.println(JSON.toJSONString(customerListByNameQry));
        return customerService.listByName(customerListByNameQry);
    }

    // curl -X GET "http://127.0.0.1:8080/get/customer?name=wenky"

    // curl -X GET http://127.0.0.1:8080/get/customer -F 'name=wenky' -H 'Content-Type: multipart/form-data'
    @GetMapping(value = "/get/customer")
    public MultiResponse<CustomerDTO> listCustomerByName(
            @RequestParam(required = false) String name) {
        CustomerListByNameQry customerListByNameQry = new CustomerListByNameQry();
        customerListByNameQry.setName(name);
        return customerService.listByName(customerListByNameQry);
    }

    // curl -X GET "http://127.0.0.1:8080/get/obj/customer?name=wenky"

    // curl -X GET http://127.0.0.1:8080/get/obj/customer -F 'name=wenky' -H 'Content-Type: multipart/form-data'
    @GetMapping(value = "/get/obj/customer")
    public MultiResponse<CustomerDTO> listCustomerByName(
            CustomerListByNameQry customerListByNameQry) {
        System.out.println(JSON.toJSONString(customerListByNameQry));
        return customerService.listByName(customerListByNameQry);
    }
}
