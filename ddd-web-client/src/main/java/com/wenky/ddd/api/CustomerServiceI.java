package com.wenky.ddd.api;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.wenky.ddd.dto.CustomerAddCmd;
import com.wenky.ddd.dto.CustomerListByNameQry;
import com.wenky.ddd.dto.data.CustomerDTO;

public interface CustomerServiceI {

    Response addCustomer(CustomerAddCmd customerAddCmd);

    MultiResponse<CustomerDTO> listByName(CustomerListByNameQry customerListByNameQry);
}
