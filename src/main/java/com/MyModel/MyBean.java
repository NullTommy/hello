package com.MyModel;


/**
 * Created by panghaichen on 2018-07-18 16:43
 **/
public class MyBean {

    private String name;
    private String id;
    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String test(){
        System.out.println("test");
        return "test";
    }

    @Override
    public String toString(){
        return "MyBean{" +
                "name='" + name +'\'' +
                ", id='" + id +'\'' +
                ", age='" + age +'\'' +
                '}';
    }

    @Override
    public void test(String test) {

    }
}
