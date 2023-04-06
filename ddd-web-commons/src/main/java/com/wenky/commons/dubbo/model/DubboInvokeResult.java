package com.wenky.commons.dubbo.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.rpc.RpcException;

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
public class DubboInvokeResult<T> implements Serializable {

    private Integer code;

    private String message;

    private LocalDateTime responseTime = LocalDateTime.now();

    private T data;

    private Throwable exception;

    public LocalDateTime getResponseTime() {
        return responseTime == null ? LocalDateTime.now() : responseTime;
    }

    public DubboInvokeResult(HandleResult handleResult) {
        this.code = handleResult.getCode();
        this.message = handleResult.getMessage();
    }

    public static DubboInvokeResult<?> newInstance(HandleResult handleResult, Throwable exception) {
        return DubboInvokeResult.builder()
                .code(handleResult.getCode())
                .message(
                        StringUtils.isBlank(exception.getMessage())
                                ? handleResult.getMessage()
                                : exception.getMessage())
                .exception(exception)
                .build();
    }

    public static DubboInvokeResult<?> exception(Throwable exception) {
        DubboInvokeResult<?> result =
                DubboInvokeResult.newInstance(HandleResult.fetch(exception), exception);
        // 避免某些类导致不能序列化问题，将exception变为字符串
        result.setException(new Exception(result.getException().getMessage()));
        return result;
    }

    public static <T> DubboInvokeResult<T> success(T data) {
        DubboInvokeResult<T> dubboInvokeResult = new DubboInvokeResult(HandleResultEnum.SUCCESS);
        dubboInvokeResult.setData(data);
        return dubboInvokeResult;
    }

    public void wrapperRpcException() {
        this.exception = new RpcException(this.exception);
    }

    public Boolean success() {
        return HandleResultEnum.SUCCESS.getCode().equals(code);
    }

    public Boolean fail() {
        return Boolean.FALSE == success();
    }
}
