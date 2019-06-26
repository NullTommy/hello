package com.MyException;

public class CompareException extends RuntimeException  {

    // 错误代码（自己添加的提示）
    private String errorCode;
    // 默认错误代码
    public static final String ERROR = "ERROR";


    public CompareException(String message){
        this(ERROR,message);
    }


    public CompareException(String errorCode,String message){
        super(message);
        this.errorCode = errorCode;
    }

    public CompareException(String message, Throwable cause){
        this(ERROR,message,cause);
    }

    public CompareException(String errorCode, String message, Throwable cause){
        super(message, cause);
        this.errorCode=errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
