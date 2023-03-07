package com.wenky.ddd.customer;

import com.alibaba.cola.catchlog.CatchAndLog;
import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.wenky.ddd.api.CustomerServiceI;
import com.wenky.ddd.command.query.CustomerQryExe;
import com.wenky.ddd.customer.executor.CustomerAddCmdExe;
import com.wenky.ddd.customer.executor.query.CustomerListByNameQryExe;
import com.wenky.ddd.dto.CustomerAddCmd;
import com.wenky.ddd.dto.CustomerListByNameQry;
import com.wenky.ddd.dto.clientobject.CustomerCO;
import com.wenky.ddd.dto.command.CustomerQry;
import com.wenky.ddd.dto.data.CustomerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@CatchAndLog
@RequiredArgsConstructor
public class CustomerServiceIImpl implements CustomerServiceI {

    // 负责获取输入，组装上下文，参数校验，调用领域层做业务处理
    private final CustomerQryExe customerQryExe;
    private final CustomerAddCmdExe customerAddCmdExe;
    private final CustomerListByNameQryExe customerListByNameQryExe;

    @Override
    public Response addCustomer(CustomerAddCmd customerAddCmd) {
        return customerAddCmdExe.execute(customerAddCmd);
    }

    @Override
    public MultiResponse<CustomerDTO> listByName(CustomerListByNameQry customerListByNameQry) {
        return customerListByNameQryExe.execute(customerListByNameQry);
    }

    @Override
    public SingleResponse<CustomerCO> getCustomerInfo(CustomerQry qry) {
        return customerQryExe.execute(qry);
    }

    @Override
    public SingleResponse<CustomerCO> getDBCustomerInfo(CustomerQry qry) {
        return customerQryExe.executeDB(qry);
    }

    @Override
    public void error() {
        customerQryExe.error();
    }
}
