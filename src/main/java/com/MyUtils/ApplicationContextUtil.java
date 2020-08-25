package com.MyUtils;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 功能描述: <br>业务类型工具类
 *
 * @since: 1.0.0
 * @Author:panghaichen
 * @Date: 2019/7/25 0018 14:28
 */

@Component
public class ApplicationContextUtil implements ApplicationContextAware {

    /**
     * spring的全局applicationContext
     */
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }

}