package com.MyModel;

import java.util.List;

public class MyBeanXStreamXml {

    private String name;
    private String id;
    private String age;

    private List<MyBeanItemXStreamXml> itemList;

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

    public List<MyBeanItemXStreamXml> getItemList() {
        return itemList;
    }

    public void setItemList(List<MyBeanItemXStreamXml> itemList) {
        this.itemList = itemList;
    }
}
