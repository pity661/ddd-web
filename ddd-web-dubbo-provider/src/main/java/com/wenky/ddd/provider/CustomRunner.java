package com.wenky.ddd.provider;

import com.wenky.ddd.provider.config.CustomProperties;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2022-12-29 09:24
 */
@Slf4j
// @Component
@RequiredArgsConstructor
public class CustomRunner implements ApplicationRunner {

    private final CustomProperties customProperties;
    // 在这里不能刷新配置文件
    @Override
    public void run(ApplicationArguments args) throws Exception {
        do {
            log.info(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            log.info(customProperties.toString());
            TimeUnit.SECONDS.sleep(5);
        } while (true);
    }
}
