package com.wenky.ddd.dto.command;

import lombok.Data;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2022-12-30 14:48
 */
@Data
public class CustomerQry extends AbstractCommand {
    private String name;
}
