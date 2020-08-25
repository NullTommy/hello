package com.MyUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author jason
 * @Description: Spring 工具类
 * @email
 * @date 2020-01-02 20:04
 * @Version 1.0
 */
@Component
public class SpringUtils implements ApplicationContextAware {

    public static final Logger logger = LoggerFactory.getLogger(SpringUtils.class);

    /**
     * spring的全局applicationContext
     */
    private static ApplicationContext applicationContext;

    private SpringUtils(){
    }

    /**
     * 根据类来获取bean
     * @param clazz
     * @return
     */
    public static <T> T getBean(Class<T> clazz){
        T result = null;
        if(applicationContext!=null){
            try {
                result = applicationContext.getBean(clazz);
            } catch (Exception e) {
                logger.warn("can not find bean:{}", clazz);
            }
        }
        return result;
    }

    /**
     * 根据bean的名称获取bean的实例
     * @param beanName
     * @return
     */
    public static Object  getBean(String beanName){
        return getBean(beanName,false);
    }


    /**
     * 根据bean的名称获取bean的实例,
     * @param beanName
     * @param ableEmpty 为true的时候不输出日志
     * @return
     */
    public static Object getBean(String beanName, boolean ableEmpty) {
        Object result = null;
        if(applicationContext!=null){
            try {
                result = applicationContext.getBean(beanName);
            } catch (Exception e) {
                if(!ableEmpty){
                    logger.warn("can not find bean:{}", beanName);
                }
            }
        }
        return result;

    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        SpringUtils.applicationContext = applicationContext;

    }
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
