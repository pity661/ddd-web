package com.wenky.ddd.customer;

import com.alibaba.cola.catchlog.CatchAndLog;
import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.wenky.ddd.api.CustomerServiceI;
import com.wenky.ddd.customer.executor.CustomerAddCmdExe;
import com.wenky.ddd.customer.executor.query.CustomerListByNameQryExe;
import com.wenky.ddd.dto.CustomerAddCmd;
import com.wenky.ddd.dto.CustomerListByNameQry;
import com.wenky.ddd.dto.data.CustomerDTO;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
@CatchAndLog
public class CustomerServiceImpl implements CustomerServiceI {

    @Resource private CustomerAddCmdExe customerAddCmdExe;

    @Resource private CustomerListByNameQryExe customerListByNameQryExe;

    public Response addCustomer(CustomerAddCmd customerAddCmd) {
        return customerAddCmdExe.execute(customerAddCmd);
    }

    @Override
    public MultiResponse<CustomerDTO> listByName(CustomerListByNameQry customerListByNameQry) {
        return customerListByNameQryExe.execute(customerListByNameQry);
    }
}
