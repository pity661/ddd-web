package com.wenky.provider.framework.canal;

import com.wenky.provider.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author 克林
 * @date 2023/7/12 14:25
 */
@Slf4j
@Component
public class CanalRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (SpringContextHolder.contains(CanalClient.CANAL_CLIENT_BEAN_NAME)) {
            CanalClient canalClient = SpringContextHolder.getBean(CanalClient.class);
            canalClient.listen();
            return;
        }
        log.info("canal监听未启用");
    }
}
