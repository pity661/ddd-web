package com.wenky.ddd.api;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.wenky.ddd.dto.CustomerAddCmd;
import com.wenky.ddd.dto.CustomerListByNameQry;
import com.wenky.ddd.dto.clientobject.CustomerCO;
import com.wenky.ddd.dto.command.CustomerQry;
import com.wenky.ddd.dto.data.CustomerDTO;

/** API接口类 */
public interface CustomerServiceI {

    Response addCustomer(CustomerAddCmd customerAddCmd);

    MultiResponse<CustomerDTO> listByName(CustomerListByNameQry customerListByNameQry);

    SingleResponse<CustomerCO> getCustomerInfo(CustomerQry qry);
}
