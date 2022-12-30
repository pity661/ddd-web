package com.wenky.ddd.command.query;

import com.alibaba.cola.dto.SingleResponse;
import com.wenky.ddd.converter.CustomerConverter;
import com.wenky.ddd.domain.customer.CustomerDO;
import com.wenky.ddd.domain.customer.gateway.CustomerGateway;
import com.wenky.ddd.dto.clientobject.CustomerCO;
import com.wenky.ddd.dto.command.CustomerQry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2022-12-30 14:33
 */
@Component
@RequiredArgsConstructor
public class CustomerQryExe {

    private final CustomerGateway customerGateway;
    private final CustomerConverter customerConverter;

    public SingleResponse<CustomerCO> execute(CustomerQry qry) {
        // 这里可以跳过领域层，直接调用基础设施层
        // 这里为了演示完整流程通过领域层处理
        // 仅做调用，具体业务在领域层处理
        CustomerDO customerDO = customerGateway.getByName(qry.getName());
        return SingleResponse.of(customerConverter.toCO(customerDO));
    }
}
