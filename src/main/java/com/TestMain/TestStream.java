package com.TestMain;

import com.alibaba.fastjson.JSON;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestStream {

    public static void main(String args[]) throws Exception {
        List<String> stringList = new ArrayList<>();
        stringList.add("1");
        stringList.add("2");
        stringList.add("3");
        stringList.add("4");
        System.out.println(JSON.toJSONString(stringList));
        Stream<String> stream = stringList.stream();
        System.out.println("stream:"+JSON.toJSONString(stream));
        //全部符合+任一符合+不符合
        boolean a = stringList.stream().allMatch((s) -> Integer.parseInt(s) > 3);
        System.out.println(JSON.toJSONString(a));
        a = stringList.stream().anyMatch((s) -> Integer.parseInt(s) > 3);
        System.out.println(JSON.toJSONString(a));
        a = stringList.stream().noneMatch((s) -> Integer.parseInt(s) > 3);
        System.out.println(JSON.toJSONString(a));
        //将List转换为Set
        Set<String> set=  stringList.stream().collect(Collectors.toSet());
        System.out.println(JSON.toJSONString(set));
        //将List转换为Map
        //Map map = stringList.stream().collect(Collectors.toMap(String::new,String::new));
        Map map = stringList.stream().collect(Collectors.toMap((s)->{return "0"+s;},String::new));
        System.out.println(JSON.toJSONString(map));
        //count 和 distinct
        long count = stringList.stream().count();
        String max = stringList.stream().max(String::compareToIgnoreCase).toString();
        String min = stringList.stream().max(String::compareToIgnoreCase).toString();
        System.out.println("count:"+JSON.toJSONString(count));
        System.out.println("max:"+JSON.toJSONString(max));
        System.out.println("min:"+JSON.toJSONString(min));
        stringList.add("4");
        System.out.println("stream:"+JSON.toJSONString(stringList));
        List<String> dis = stringList.stream().distinct().collect(Collectors.toList());
        System.out.println("dis:"+JSON.toJSONString(dis));
        //concat
        List<String> stringList2 = new ArrayList<>();
        stringList2.add("6");
        stringList2.add("7");
        stringList2.add("8");
        stringList2.add("9");
        List<String> concact = Stream.concat(stringList.stream(),stringList2.stream()).collect(Collectors.toList());
        System.out.println("concact:"+JSON.toJSONString(concact));
        //empty
        Stream nullStream = Stream.empty();
        //filter
        List<String> filter = stringList.stream().filter((s)->{return Integer.parseInt(s)>=4;}).collect(Collectors.toList());
        System.out.println("filter:"+JSON.toJSONString(filter));
        //findAny 和 findFirst
        String s1 = stringList.stream().findAny().get();
        System.out.println("s:"+JSON.toJSONString(s1));
        String s2 = stringList.stream().findFirst().get();
        System.out.println("s:"+JSON.toJSONString(s2));
        //	flatMap 不知道怎么用，暂时不写
        //List<String> flatMap = stringList.stream().flatMap().collect(Collectors.toList());
        //	forEach
        stringList.stream().forEach((s) -> System.out.println("forEach: " + s));
        //	limit
        List<String> limit = stringList.stream().limit(2).collect(Collectors.toList());
        System.out.println("limit:"+JSON.toJSONString(limit));
        //map
        List<String> map1 = stringList.stream().map((s)->{return s+s;}).collect(Collectors.toList());
        System.out.println("map1:"+JSON.toJSONString(map1));
        //of
        List<String> list = Stream.of("1","2").collect(Collectors.toList());
        System.out.println("list:"+JSON.toJSONString(list));
        //	peek:感觉功能很重复呀，目前，没发现什么其他用处
        list =    stringList.stream().peek((s)->{Integer.parseInt(s);}).collect(Collectors.toList());
        System.out.println("peek:"+JSON.toJSONString(list));
        list =    stringList.stream().peek((s)->{System.out.println("peek:"+s);}).collect(Collectors.toList());
        System.out.println("peek:"+JSON.toJSONString(list));
        //	reduce：不知道怎么用，暂时算了
        //这是全部求和的用法：acc表示上一次执行结果，item表示下一个要执行是数据
        Optional accResult = Stream.of(1, 2, 3, 4)
                .reduce((acc, item) -> {
                    System.out.println("acc : "  + acc);
                    acc += item;
                    System.out.println("item: " + item);
                    System.out.println("acc+ : "  + acc);
                    System.out.println("--------");
                    return acc;
                });
        System.out.println("accResult: " + accResult.get());
        System.out.println("--------");

        //reduce的第二个函数：开始的5表示起始结果。相当于求和时sum的初始值
        int accResult2 = Stream.of(1, 2, 3, 4)
                .reduce(5, (acc, item) -> {
                    System.out.println("acc : "  + acc);
                    acc += item;
                    System.out.println("item: " + item);
                    System.out.println("acc+ : "  + acc);
                    System.out.println("--------");
                    return acc;
                });
        System.out.println("accResult2: " + accResult2);
        System.out.println("--------");
        //reduce的第二个函数：

        //skip:丢弃第n之前（从1开始，包含n）的元素，得到剩下的数据
        list =    stringList.stream().skip(4).collect(Collectors.toList());
        System.out.println("skip:"+JSON.toJSONString(list));
        //sorted 和 toArray：挺基本的功能，就是排序和数组
        list =    stringList.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        System.out.println("sorted:"+JSON.toJSONString(list));
        Object[] array =    stringList.stream().toArray();
        System.out.println("array:"+JSON.toJSONString(array));
    }

}
