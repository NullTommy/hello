package com.TestMain;

import com.MyException.CompareException;
import org.apache.log4j.Logger;

public class TestException {

    static final Logger log = Logger.getLogger(TestException.class);

    public static void main(String args[]){
        try {
            int i = 0 ;
            if(1>0){
                throw new CompareException("比0大！");
            }else {
                throw new CompareException("比0小！");
            }
        }catch (Exception e){
            //如果是自定义的比较异常
            if(e instanceof CompareException){
                log.info("自定义这种处理逻辑~~",e);
            }
            //其他异常
            log.error("这是真的异常处理呀~~",e);
        }finally {
            log.info("结束--");
        }

    }
}