package com.TestArch;

import com.MyEnum.RedisKeyEnum;

public class TestMainStringFormat {
    public static void main(String args[]) {
        /*枚举类：字符串格式化*/
        System.out.println("" + RedisKeyEnum.KEY.format("PHC"));
        System.out.println("" + RedisKeyEnum.KEY_TWO_STR.format("PHC", "DXF"));
        /*字符串格式化*/
        System.out.println("" + String.format("string_%s", 123));
    }

}