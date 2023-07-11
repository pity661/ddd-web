package com.wenky.provider.refresh;

import java.util.concurrent.ThreadLocalRandom;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2022-12-29 10:45
 */
@RefreshScope
@RestController
@RequiredArgsConstructor
public class RefreshController {

    // 需要在类上添加@RefreshScope才能刷新配置
    @Value("${custom.name}")
    private String name;

    private final RefreshService refreshService;

    // curl http://127.0.0.1:8081/name
    @GetMapping(value = "/name")
    public String name(HttpServletRequest request) {
        return name;
    }

    // curl http://127.0.0.1:8081/all
    @GetMapping(value = "/all")
    public String all(HttpServletRequest request) {
        return refreshService.getProperties();
    }

    public static int random(int bound) {
        return ThreadLocalRandom.current().nextInt(bound);
    }
}
