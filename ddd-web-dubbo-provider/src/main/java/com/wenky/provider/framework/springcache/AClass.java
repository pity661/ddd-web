package com.wenky.provider.framework.springcache;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-01-20 16:15
 */
@Component
public class AClass {
    @Autowired @Lazy private BClass bClass;

    @PostConstruct
    public void init() {
        System.out.println(String.format("AClass:{%s}, bClass:{%s}", this, bClass));
    }
}
