package com.wenky.commons.dubbo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-03 14:38
 */
@AllArgsConstructor
public enum HandleResultEnum {
    SUCCESS(0000, "调用成功"),

    SYSTEM_ERROR(1000, "系统异常，调用失败"),
    ;
    @Getter private Integer code;
    @Getter private String message;
}
