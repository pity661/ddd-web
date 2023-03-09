package com.wenky.commons.dubbo.model;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.wenky.commons.dubbo.model.exception.BizException;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-03 14:38
 */
@AllArgsConstructor
public enum HandleResultEnum implements HandleResult {
    SUCCESS(0000, "调用成功"),

    SYSTEM_ERROR(1000, "系统异常，调用失败", Exception.class),
    BIZ_EXCEPTION(2000, "业务异常，调用失败", BizException.class),
    SENTINEL_BLOCK_EXCEPTION(3000, "sentinel异常，调用失败", BlockException.class),
    SENTINEL_FLOW_EXCEPTION(3001, "sentinel接口限流，调用失败", FlowException.class),
    SENTINEL_DEGRADE_EXCEPTION(3002, "sentinel服务降级，调用失败", DegradeException.class),
    SENTINEL_PARAM_FLOW_EXCEPTION(3003, "sentinel热点参数限流，调用失败", ParamFlowException.class),
    SENTINEL_AUTHORITY_EXCEPTION(3004, "sentinel授权规则异常，调用失败", AuthorityException.class),
    SENTINEL_SYSTEM_BLOCK_EXCEPTION(3005, "sentinel系统硬件限流，调用失败", SystemBlockException.class),

    IO_EXCEPTION(4000, "IO异常，调用失败", IOException.class),
    ;

    HandleResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Getter private Integer code;
    @Getter private String message;
    @Getter private Class<? extends Exception> exception;
}
