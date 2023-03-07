package com.wenky.ddd.web;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.fastjson.JSON;
import com.wenky.ddd.api.CustomerServiceI;
import com.wenky.ddd.dto.CustomerAddCmd;
import com.wenky.ddd.dto.CustomerListByNameQry;
import com.wenky.ddd.dto.clientobject.CustomerCO;
import com.wenky.ddd.dto.command.CustomerQry;
import com.wenky.ddd.dto.data.CustomerDTO;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class CustomerController {

    // service由client定义并由app实现
    private final CustomerServiceI customerService;

    // curl http://127.0.0.1:8080/info/error
    @GetMapping(value = "/info/error")
    public Response error(HttpServletRequest request) {
        customerService.error();
        return Response.buildSuccess();
    }

    // curl http://127.0.0.1:8080/info/wenky
    @GetMapping(value = "/info/wenky")
    public SingleResponse<CustomerCO> getWenky(HttpServletRequest request) {
        CustomerQry customerQry = new CustomerQry();
        customerQry.setName("wenky");
        return customerService.getCustomerInfo(customerQry);
    }

    // curl http://127.0.0.1:8080/info/wendy
    @GetMapping(value = "/info/wendy")
    public SingleResponse<CustomerCO> getWendy(HttpServletRequest request) {
        CustomerQry customerQry = new CustomerQry();
        customerQry.setName("wendy");
        return customerService.getDBCustomerInfo(customerQry);
    }

    // curl http://127.0.0.1:8080/helloworld
    @GetMapping(value = "/helloworld")
    public String helloWorld(HttpServletRequest request) {
        log.info("*****************start*****************");
        // 打印所有请求头信息
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            log.info("NAME:{}, VALUE:{}", headerName, request.getHeader(headerName));
        }
        log.info("******************end******************");
        return "Hello, welcome to COLA world!";
    }

    // curl "http://127.0.0.1:8080/add/customer" -X POST -H 'Content-Type: application/json' -d
    // '{"customerDTO": {"customerId": "wenky", "companyName": "hengchang"}}'

    // curl "http://127.0.0.1:8080/add/customer" -X POST -d @customerData.json -H 'Content-Type:
    // application/json'
    @PostMapping(value = "/add/customer")
    public Response addCustomer(@RequestBody @Valid CustomerAddCmd customerAddCmd) {
        log.info("*****************start*****************");
        log.info("request:{}", JSON.toJSONString(customerAddCmd));
        log.info("******************end******************");
        return customerService.addCustomer(customerAddCmd);
    }

    // curl -X POST "http://127.0.0.1:8080/query/customer?name=wenky&customerId=1+"

    // curl -X POST http://127.0.0.1:8080/query/customer -d 'name=wenky&customerId=1' -H
    // 'Content-Type:
    // application/x-www-form-urlencoded'
    @PostMapping(value = "/query/customer")
    public MultiResponse<CustomerDTO> listCustomerByName1(
            CustomerListByNameQry customerListByNameQry) {
        log.info("*****************start*****************");
        System.out.println(JSON.toJSONString(customerListByNameQry));
        log.info("******************end******************");
        return customerService.listByName(customerListByNameQry);
    }

    // curl -X GET "http://127.0.0.1:8080/get/customer?name=wenky"

    // curl -X GET http://127.0.0.1:8080/get/customer -F 'name=wenky' -H 'Content-Type:
    // multipart/form-data'
    @GetMapping(value = "/get/customer")
    public MultiResponse<CustomerDTO> listCustomerByName(
            @RequestParam(required = false) String name) {
        CustomerListByNameQry customerListByNameQry = new CustomerListByNameQry();
        customerListByNameQry.setName(name);
        return customerService.listByName(customerListByNameQry);
    }

    // curl -X GET "http://127.0.0.1:8080/get/obj/customer?name=wenky"

    // curl -X GET http://127.0.0.1:8080/get/obj/customer -F 'name=wenky' -H 'Content-Type:
    // multipart/form-data'

    // curl -G -d 'name=wenky' http://127.0.0.1:8080/get/obj/customer
    @GetMapping(value = "/get/obj/customer")
    public MultiResponse<CustomerDTO> listCustomerByName(
            CustomerListByNameQry customerListByNameQry) {
        log.info("*****************start*****************");
        System.out.println(JSON.toJSONString(customerListByNameQry));
        log.info("******************end******************");
        return customerService.listByName(customerListByNameQry);
    }
}
