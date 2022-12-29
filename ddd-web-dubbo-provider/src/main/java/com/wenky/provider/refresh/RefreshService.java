package com.wenky.provider.refresh;

import com.wenky.provider.refresh.config.CustomProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2022-12-29 10:50
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshService {

    private final CustomProperties customProperties;

    public String getProperties() {
        log.info(customProperties.toString());
        return customProperties.toString();
    }
}
