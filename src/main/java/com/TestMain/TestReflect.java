package com.TestMain;

import com.MyModel.MyBean;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by panghaichen on 2018-07-13 17:19
 * 参考：Java 反射 使用总结 - 赵彦军 - 博客园 https://www.cnblogs.com/zhaoyanjun/p/6074887.html
 **/
public class TestReflect {
    public static void main(String args[]){

        //创建类:3种方式：class1、class2、class3
        Class<?> class1 = com.MyModel.MyBean.class;
        Class<?> class2 = null;
        try {
            class2 = Class.forName("com.MyModel.MyBean");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        MyBean myBean = new MyBean();
        Class<?> class3 = myBean.getClass();

        System.out.println("class1:"+class1);
        System.out.println("class2:"+class2);
        System.out.println("class3:"+class3);

        Method[] methods = class1.getMethods();
        for (Method method:methods){
            //System.out.println("method:"+method);
        }

        try {
            //创建实例化：相当于 new 了一个对象
            Object o = class1.newInstance();
            //向下转型
            MyBean myBeanClass = (MyBean)o;
            myBeanClass.setName("名字");
            System.out.println("myBean1:"+myBeanClass.toString());

            //获得id 属性
            Field idField = class1.getDeclaredField( "id" ) ;

            //打破封装  实际上setAccessible是启用和禁用访问安全检查的开关,并不是为true就能访问为false就不能访问
            //由于JDK的安全检查耗时较多.所以通过setAccessible(true)的方式关闭安全检查就可以达到提升反射速度的目的
            //不然会有异常抛出
            idField.setAccessible(true);
            //给id 属性赋值
            idField.set(myBeanClass, "100102") ;
            //打印 person 的属性值
            System.out.println("Field:"+idField.get(myBeanClass));
            System.out.println("myBean1:"+myBeanClass.toString());

            //获取 setName() 方法
            Method test = class1.getDeclaredMethod("test");
            //打破封装
            test.setAccessible( true );
            test.invoke(myBeanClass);

            //获取 setName() 方法:第二个参数：是对应方法的参数...
            Method setAge = class1.getDeclaredMethod("setAge", String.class);
            //打破封装
            setAge.setAccessible( true );
            setAge.invoke(myBeanClass,"49");
            System.out.println("myBean1:"+myBeanClass.toString());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}