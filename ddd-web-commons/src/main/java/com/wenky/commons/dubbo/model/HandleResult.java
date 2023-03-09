package com.wenky.commons.dubbo.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.dubbo.common.utils.CollectionUtils;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-09 11:18
 */
public interface HandleResult {

    Integer getCode();

    String getMessage();

    Class<? extends Exception> getException();

    static HandleResult fetch(Throwable exception) {
        return fetch(exception, Arrays.asList(HandleResultEnum.values()));
    }

    static HandleResult fetch(Throwable exception, List<HandleResult>... values) {
        List<HandleResult> source =
                Stream.of(values).flatMap(Collection::stream).collect(Collectors.toList());
        List<HandleResult> matchList = new ArrayList<>();
        for (HandleResult config : source) {
            Class<? extends Exception> clazz = config.getException();
            if (clazz == null) {
                continue;
            }
            if (clazz == exception.getClass()) {
                return config;
            }
            if (clazz.isInstance(exception)) {
                matchList.add(config);
            }
        }

        if (CollectionUtils.isEmpty(matchList)) {
            return HandleResultEnum.SYSTEM_ERROR;
        }

        HandleResult match = null;
        for (HandleResult config : matchList) {
            if (match == null) {
                match = config;
                continue;
            }
            // match是否为config的父类
            if (match.getException().isAssignableFrom(config.getException())) {
                match = config;
            }
        }
        return match;
    }
}
