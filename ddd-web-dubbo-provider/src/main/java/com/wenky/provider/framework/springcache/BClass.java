package com.wenky.provider.framework.springcache;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-01-20 16:15
 */
@Component
@Transactional
@RequiredArgsConstructor
public class BClass {
    // DefaultSingletonBeanRegistry#singletonFactories
    private final AClass aClass;

    @PostConstruct
    public void init() {
        System.out.println(String.format("BClass:{%s}, aClass:{%s}", this, aClass));
    }
}
