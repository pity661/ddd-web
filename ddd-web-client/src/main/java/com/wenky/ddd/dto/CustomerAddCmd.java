package com.wenky.ddd.dto;

import com.wenky.ddd.dto.data.CustomerDTO;
import javax.validation.Valid;
import lombok.Data;

/** 用户请求参数 */
@Data
public class CustomerAddCmd {

    @Valid private CustomerDTO customerDTO;
}
