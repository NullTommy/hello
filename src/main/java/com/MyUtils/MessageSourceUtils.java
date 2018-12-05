package com.MyUtils;

/**
 * @ClassName: MessageSource
 * @Description:
 * @date 2015年9月16日 下午7:46:55
 */
public class MessageSourceUtils {

    private static MessageSourceHelper helper;

    public static String getMessage(String code) {
        return helper.getMessage(code);
    }

    public static String getMessage(String code, Object[] args) {
        return helper.getMessage(code, args);
    }

    public void setHelper(MessageSourceHelper helper) {
        MessageSourceUtils.helper = helper;
    }

}
