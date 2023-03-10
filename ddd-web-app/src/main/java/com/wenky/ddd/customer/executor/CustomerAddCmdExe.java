package com.wenky.ddd.customer.executor;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.exception.BizException;
import com.wenky.ddd.dto.CustomerAddCmd;
import com.wenky.ddd.dto.data.ErrorCode;
import org.springframework.stereotype.Component;

/** 每个用户请求对应一个执行器 */
@Component
public class CustomerAddCmdExe {

    public Response execute(CustomerAddCmd cmd) {
        // The flow of usecase is defined here.
        // The core ablility should be implemented in Domain. or sink to Domian gradually
        if (cmd.getCustomerDTO().getCompanyName().equals("ConflictCompanyName")) {
            throw new BizException(ErrorCode.B_CUSTOMER_companyNameConflict.getErrCode(), "公司名冲突");
        }
        return Response.buildSuccess();
    }
}
