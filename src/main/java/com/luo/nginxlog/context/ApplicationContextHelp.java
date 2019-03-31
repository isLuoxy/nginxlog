package com.luo.nginxlog.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 代码获取 spring bean
 * @author L99
 * @createDate 2019/3/30
 * @see ApplicationContextAware
 */
@Component
public class ApplicationContextHelp implements ApplicationContextAware {
    private static ApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHelp.context = applicationContext;
    }

    public static Object getBean(String name) {
        return context.getBean(name);
    }
}
