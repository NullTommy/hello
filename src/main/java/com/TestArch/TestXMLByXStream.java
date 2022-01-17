package com.TestArch;

import com.MyModel.MyBeanXStreamXml;
import com.alibaba.fastjson.JSON;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import lombok.SneakyThrows;

public class TestXMLByXStream {
    @SneakyThrows
    public static void main(String args[]) {
        MyBeanXStreamXml myBeanXml = new MyBeanXStreamXml();
        myBeanXml.setAge("28");
        myBeanXml.setId("01");
        myBeanXml.setName("PHC");
        // 对象转 XML
        System.out.println("----------对象转 XML-------------------");
        XStream xStream = new XStream();
        String xml = xStream.toXML(myBeanXml);
        System.out.println(xml);

        System.out.println("----------对象转 XML：修改根节点标识-------------------");
        xStream.alias("ROOT", MyBeanXStreamXml.class);  // 修改根节点标识
        String xml2 = xStream.toXML(myBeanXml);
        System.out.println(xml2);

        System.out.println("----------XML 字符串转对象-------------------");
        XStream xStreamForStr = new XStream();
        xStreamForStr.addPermission(AnyTypePermission.ANY); //没有这行会报错：com.thoughtworks.xstream.security.ForbiddenClassException
        MyBeanXStreamXml person2 = (MyBeanXStreamXml) xStreamForStr.fromXML(xml);
        System.out.println("" + JSON.toJSONString(person2));
    }
}
