package com.wenky.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextHolder
        implements ApplicationContextAware, InitializingBean, DisposableBean {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static volatile ApplicationContext applicationContext = null;

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("SpringContextHolder初始化");
    }

    @Override
    public void destroy() throws Exception {
        SpringContextHolder.clear();
        logger.info("SpringContextHolder被销毁");
    }

    private static void clear() {
        SpringContextHolder.applicationContext = null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringContextHolder.applicationContext == null) {
            synchronized (SpringContextHolder.class) {
                if (SpringContextHolder.applicationContext == null) {
                    SpringContextHolder.applicationContext = applicationContext;
                }
            }
        }
    }

    public static ApplicationContext getApplicationContext() {
        assertContextInjected();
        return applicationContext;
    }

    public static Boolean contains(String name) {
        return applicationContext.containsBean(name);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }

    private static void assertContextInjected() {
        if (applicationContext == null) {
            throw new IllegalStateException("ApplicationContext未注入");
        }
    }
}
