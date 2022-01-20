package com.MyModel;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ROW")
public class MyBeanItemXStreamXml {

    private String nameItem;
    private String id;
    private String age;


    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
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

}
