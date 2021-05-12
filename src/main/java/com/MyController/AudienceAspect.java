package com.MyController;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;

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
    public void takeSeats(ProceedingJoinPoint proceedingJoinPoint) {
        System.out.println("AOP Around - BEGIN");

        /*获取类名*/
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        System.out.println("AOP Around - className-" + className);
        /*获取方法名*/
        String methodName = proceedingJoinPoint.getSignature().getName();
        System.out.println("AOP Around - methodName-" + methodName);
        /*获取参数*/
        Object[] args = proceedingJoinPoint.getArgs();
        System.out.println("AOP Around - args-" + JSON.toJSONString(args));

        /*获取注解，以及注解中的信息*/
        Signature signature = proceedingJoinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        RequestMapping annoObj = method.getAnnotation(RequestMapping.class);
        System.out.println("AOP Around - annoObj-" + JSON.toJSONString(annoObj));
        System.out.println("AOP Around - annoObj-" + annoObj.toString());

        try {
            /*获取切点执行结果*/
            Object o = proceedingJoinPoint.proceed();
            System.out.println("AOP Around - result-" + JSON.toJSONString(o));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

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
