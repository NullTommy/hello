package com.TestArch;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

public class TestList {
    public static void main(String args[]) {

        String s = "异常";
        try {
            System.out.println("【测试】是否可以添加null：");
            List<String> stringList = new ArrayList<>();
            stringList.add("0");
            stringList.add("1");
            stringList.add("2");
            stringList.add("3");
            stringList.add("4");
            stringList.add("5");
            System.out.println("size:" + stringList.size());
            stringList.add(null);
            stringList.add(null);
            System.out.println("size:" + stringList.size());
            System.out.println("List中可以添加多个null，且size一直增加！");

            System.out.println("【测试】get方法：");
            System.out.println("原始字符串：" + JSON.toJSONString(stringList));
            System.out.println("原始字符串的最后一个索引（size-1）值：" + JSON.toJSONString(stringList.get(stringList.size() - 1)));
            System.out.println("原始字符串的get(-1)：异常-ArrayIndexOutOfBoundsException");
            System.out.println("get方法的参数：如果大于最后一个索引值则异常！小于0也异常");

            System.out.println("【测试】subList方法：");
            System.out.println("subList子索引-含前不含后：subList(0,2)：" + stringList.subList(0, 2));
            System.out.println("subList子索引-含前不含后：subList(1,2)：" + stringList.subList(1, 2));
            System.out.println("subList子索引-含前不含后（相同则没有）：subList(2,2)：" + stringList.subList(2, 2));//含前不含后

            System.out.println("【测试】addAll方法：");
            List<String> stringList2 = new ArrayList<>();
            System.out.println("addAll方法：空List正常" + JSON.toJSONString(stringList.addAll(stringList2)));
            //stringList.addAll(null);
            System.out.println("addAll方法：null异常");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("" + s);
        } finally {

        }
    }

}