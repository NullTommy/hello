package com.MyUtils;


import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * 多语言国际化工具
 */
public class MessageSourceHelper {
	
    private MessageSource messageSource;
    
    /**
     * 获取对应语言的消息
     * @param code 消息key
     * @param args
     * @param defaultMessage  默认消息
     * @param locale  语言类型
     * @return
     */
    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        String msg = messageSource.getMessage(code, args, defaultMessage, locale);
        return msg != null ? msg.trim() : msg;
    }
    
    public String getMessage(String code, Object[] args) {
        String msg = messageSource.getMessage(code, args, "", LocaleContextHolder.getLocale());
        return msg != null ? msg.trim() : msg;
    }
    
    public String getMessage(String code) {
        String msg = messageSource.getMessage(code, null, "", LocaleContextHolder.getLocale());
        return msg != null ? msg.trim() : msg;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

}