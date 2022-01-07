package com.TestArch;

import com.MyModel.MyBeanXml;
import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

public class TestXML {
    @SneakyThrows
    public static void main(String args[]) {
        MyBeanXml myBeanXml = new MyBeanXml();
        myBeanXml.setAge("28");
        myBeanXml.setId("01");
        myBeanXml.setName("PHC");

        // 对象转 XML
        System.out.println("----------对象转 XML-------------------");
        JAXBContext jaxbContext = JAXBContext.newInstance(MyBeanXml.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        // output pretty printed
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(myBeanXml, System.out);

        // XML 字符串转对象
        String xmlStr = "<ROOT>\n" +
                "    <AGE>28-Str</AGE>\n" +
                "    <ID>01-Str</ID>\n" +
                "    <NAME>PHC-Str</NAME>\n" +
                "</ROOT>";
        Unmarshaller u = jaxbContext.createUnmarshaller();
        StringReader sr = new StringReader(xmlStr);
        Object resp = u.unmarshal(sr);
        System.out.println("----------XML 字符串转对象-------------------");
        System.out.println("" + JSON.toJSONString(resp));
    }
}
