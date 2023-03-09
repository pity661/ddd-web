package com.wenky.commons.dubbo.model.exception;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-09 10:36
 */
public class BizException extends RuntimeException {

    public BizException() {
        super();
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }
}
