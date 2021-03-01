package com.TestMain;

import com.MyModel.MyBean;
import com.MyModel.MyBeanFather;
import com.MyModel.MyBeanInterface;

public class TestInstance {
    public static void main(String args[]) {

        MyBean myBean = new MyBean();
        MyBeanFather myBeanFather = new MyBeanFather();
        System.out.println("instanceof：前面(对象)是后面(类)的实例，不适用于接口判断");
        System.out.println((myBean instanceof MyBean));
        System.out.println((myBean instanceof MyBeanFather));
        System.out.println((myBean instanceof MyBeanInterface));
        System.out.println((myBeanFather instanceof MyBean));
        System.out.println((myBeanFather instanceof MyBeanFather));
        System.out.println((myBeanFather instanceof MyBeanInterface));

        System.out.println("isInstance:后面(对象)是前面(类)的实例，不适用于接口判断");
        System.out.println(MyBean.class.isInstance(myBean));
        System.out.println(MyBeanFather.class.isInstance(myBean));
        System.out.println(MyBeanInterface.class.isInstance(myBean));
        System.out.println(MyBean.class.isInstance(myBeanFather));
        System.out.println(MyBeanFather.class.isInstance(myBeanFather));
        System.out.println(MyBeanInterface.class.isInstance(myBeanFather));

        System.out.println("isAssignableFrom:后面(类)是前面(类)的实例，适用于接口判断");
        System.out.println(MyBean.class.isAssignableFrom(myBean.getClass()));
        System.out.println(MyBeanFather.class.isAssignableFrom(myBean.getClass()));
        System.out.println(MyBeanInterface.class.isAssignableFrom(myBean.getClass()));
        System.out.println(MyBean.class.isAssignableFrom(myBeanFather.getClass()));
        System.out.println(MyBeanFather.class.isAssignableFrom(myBeanFather.getClass()));
        System.out.println(MyBeanInterface.class.isAssignableFrom(myBeanFather.getClass()));
        System.out.println(MyBean.class.isAssignableFrom(MyBeanInterface.class));
        System.out.println(MyBeanFather.class.isAssignableFrom(MyBeanInterface.class));
        System.out.println(MyBeanInterface.class.isAssignableFrom(MyBeanInterface.class));


    }


}