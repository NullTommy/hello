package com.TestArch;

import org.apache.log4j.Logger;

import static org.apache.log4j.Priority.*;

public class TestLog {
    public static void main(String[] args) {
        //BasicConfigurator.configure(); //加载的应该是log4j的默认配置文件，自行设置的配置文件不会生效
        new Test().test();
    }
}

class Test {
    //注意这两个方法的不同。里面的参数："Test"和Test.class实际对应的是log4j.xml的logger的name属性（String类型）。
    final Logger log = Logger.getLogger(Test.class);
    //final Logger log = Logger.getLogger("Test");
    public void test() {
        log.debug("hello this is log4j debug log");
        log.info( "hello this is log4j info log");
        log.warn( "hello this is log4j warn log");
        log.error("hello this is log4j error log");
        log.fatal("hello this is log4j fatal log");

        log.log(DEBUG,"hello this is log4j DEBUG log");
        log.log(INFO, "hello this is log4j INFO log");
        log.log(WARN, "hello this is log4j WARN log");
        log.log(ERROR,"hello this is log4j ERROR log");
        log.log(FATAL,"hello this is log4j FATAL log");
    }
}