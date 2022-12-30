package com.wenky.ddd.customer.executor.query;

import com.alibaba.cola.dto.MultiResponse;
import com.wenky.ddd.dto.CustomerListByNameQry;
import com.wenky.ddd.dto.data.CustomerDTO;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerListByNameQryExe {

    public MultiResponse<CustomerDTO> execute(CustomerListByNameQry cmd) {
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerName("wenky");
        customerDTOList.add(customerDTO);
        return MultiResponse.of(customerDTOList);
    }
}
