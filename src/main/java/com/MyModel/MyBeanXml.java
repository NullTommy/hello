package com.MyModel;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "ROOT")
public class MyBeanXml {

    private String name;
    private String id;
    private String age;

    public String getName() {
        return name;
    }

    @XmlElement(name = "NAME")
    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    @XmlElement(name = "ID")
    public void setId(String id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    @XmlElement(name = "AGE")
    public void setAge(String age) {
        this.age = age;
    }


}
