package com.TestArch;

import com.MyModel.MyBeanItemXStreamXml;
import com.MyModel.MyBeanXStreamXml;
import com.alibaba.fastjson.JSON;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

public class TestXMLByXStream {
    @SneakyThrows
    public static void main(String args[]) {
        //1、构建对象
        List<MyBeanItemXStreamXml> xmls = new ArrayList<>();
        MyBeanItemXStreamXml  itemXStreamXml= new MyBeanItemXStreamXml();
        itemXStreamXml.setNameItem("nameItem");
        itemXStreamXml.setId("idItem");
        itemXStreamXml.setAge("ageItem");
        xmls.add(itemXStreamXml);
        MyBeanItemXStreamXml  itemXStreamXml2= new MyBeanItemXStreamXml();
        itemXStreamXml2.setNameItem("nameItem2");
        itemXStreamXml2.setId("idItem2");
        itemXStreamXml2.setAge("ageItem2");
        xmls.add(itemXStreamXml2);

        MyBeanXStreamXml myBeanXml = new MyBeanXStreamXml();
        myBeanXml.setAge("28");
        myBeanXml.setId("01");
        myBeanXml.setName("PHC");
        myBeanXml.setItemList(xmls);
        // 2、对象转 XML
        System.out.println("----------对象转 XML-------------------");
        XStream xStream = new XStream();
        xStream.autodetectAnnotations(true);
        String xml = xStream.toXML(myBeanXml);
        System.out.println(xml);
        // 3、对象转 XML：修改根节点标识
        System.out.println("----------对象转 XML：修改根节点标识-------------------");
        xStream.alias("ROOT", MyBeanXStreamXml.class);  // 修改根节点标识
        String xml2 = xStream.toXML(myBeanXml);
        System.out.println(xml2);
        // 4、XML 字符串转对象
        System.out.println("----------XML 字符串转对象-------------------");
        XStream xStreamForStr = new XStream();
        xStreamForStr.autodetectAnnotations(true);
        xStreamForStr.addPermission(AnyTypePermission.ANY); //没有这行会报错：com.thoughtworks.xstream.security.ForbiddenClassException
        MyBeanXStreamXml person2 = (MyBeanXStreamXml) xStreamForStr.fromXML(xml);
        System.out.println("" + JSON.toJSONString(person2));
    }
}
