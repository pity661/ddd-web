package com.wenky.ddd.dto.command;

import com.alibaba.cola.dto.Command;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2022-12-30 14:46
 */
abstract class AbstractCommand extends Command {
    private String operator;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
