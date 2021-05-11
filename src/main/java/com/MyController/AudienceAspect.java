package com.MyController;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
/*@Aspect注解表明Audience类是一个切面。*/
public class AudienceAspect {

    @Pointcut("execution(public * com.MyController.MainController.*(..))")
    public void pointCut() {

    }

    /**
     * 表演之前,观众就座
     */
    @Around(value = "pointCut()")
    public void takeSeats() {
        System.out.println("AOP Around");
    }

    /**
     * 表演之前,将手机调至静音
     */
    @Before(value = "pointCut()")
    public void silenceCellPhones() {
        System.out.println("AOP Before");
    }

    /**
     * 表演结束,不管表演成功或者失败
     */
    @After(value = "pointCut()")
    public void finish() {
        System.out.println("After");
    }

    /**
     * 表演之后,鼓掌
     */
    @AfterReturning(value = "pointCut()")
    public void applause() {
        System.out.println("AfterReturning");
    }

}
