package com.wenky.ddd.config;

import com.alibaba.cola.dto.Response;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2022-12-18 14:01
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response methodArgumentNotValidException(
            MethodArgumentNotValidException exception, HttpServletRequest httpServletRequest) {
        log.error("MethodArgumentNotValidException", exception);
        return Response.buildFailure("MethodArgumentNotValidException", exception.getMessage());
    }
}
