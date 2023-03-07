package com.wenky.commons.dubbo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-03 14:34
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DubboInvokeResult<T> {

    private Integer code;

    private String message;

    private T data;

    public DubboInvokeResult(HandleResultEnum handleResultEnum) {
        this.code = handleResultEnum.getCode();
        this.message = handleResultEnum.getMessage();
    }

    public static DubboInvokeResult newInstance(
            HandleResultEnum handleResultEnum, Throwable exception) {
        return DubboInvokeResult.builder()
                .code(handleResultEnum.getCode())
                .message(
                        StringUtils.isBlank(exception.getMessage())
                                ? handleResultEnum.getMessage()
                                : exception.getMessage())
                .build();
    }

    public static DubboInvokeResult newInstance(Object data) {
        DubboInvokeResult dubboInvokeResult = new DubboInvokeResult(HandleResultEnum.SUCCESS);
        dubboInvokeResult.setData(data);
        return dubboInvokeResult;
    }
}
