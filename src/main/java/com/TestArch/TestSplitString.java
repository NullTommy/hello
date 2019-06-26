package com.TestArch;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NullTommy on 2018-07-27 14:38
 **/
public class TestSplitString {

    public static void main(String[] args){

        try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw

            /* 读入TXT文件 */
            // 相对路径,相对于project的路径
            String pathname = "src/main/resources/fileTemplate/input.txt";
            // 要读取以上路径的input.txt文件
            File filename = new File(pathname);
            // 建立一个输入流对象reader
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename));
            // 建立一个对象，它把文件内容转成计算机能读懂的语言
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            StringBuilder stringBuilder =  new StringBuilder();
            while (line != null) {
                // 一次读入一行数据
                line = br.readLine();
                if(line != null){
                    stringBuilder.append(line);
                }
            }
            //构建返回的StringList
            List<String> stringList = split(stringBuilder.toString());

            /* 将StringList输出到控制台，同时写入Txt文件 */
            // 相对路径，如果没有则要建立一个新的output.txt文件
            File writename = new File("src/main/resources/fileTemplate/output.txt");
            writename.createNewFile(); // 创建新文件
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            for (String s :stringList){
                System.out.println(s);
                // \r\n即为换行
                out.write(s+"\r\n");
            }
            // 把缓存区内容压入文件
            out.flush();
            // 最后记得关闭文件
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能描述: 将输入内容根据条件分割<br>
     *
     * @since: 1.0.0
     * @Date: 2019/5/30 17:22
     */
    static private List<String> split(String stringBuilder){
        String[] strings = stringBuilder.split("_");
        List<String> stringList = new ArrayList<>() ;
        for (String s : strings){
            List<String> list = parse140StringList(s);
            stringList.addAll(list);
        }
        return stringList;
    }

    /**
     * 功能描述: 将字符串拆分为140字分割的多行<br>
     *
     * @since: 1.0.0
     * @Date: 2019/5/30 17:22
     */
    static private  List<String> parse140StringList(String s){
        Double splitSize = 140.0;
        //截取后的大小
        int resultSize = 130;
        List<String> stringList = new ArrayList<>();

        //需要分割
        if(s.length()>splitSize){
            //向上取整
            Double v2 = Math.ceil(s.length()/splitSize);
            //doubule转int防止输出字符串带小数位。
            int size = v2.intValue();
            String stringTemp;
            for (int i=0;i< size;i++){
                //构建后缀
                String suffixString = "("+String.valueOf(i+1)+"/"+String.valueOf(size)+")";
                if(i+1==v2){
                    //最后一行：最高到字符串的size
                    stringTemp = s.substring(i*resultSize,s.length())+suffixString;
                }else {
                    stringTemp = s.substring(i*resultSize,(i+1)*resultSize)+suffixString;
                }
                stringList.add(stringTemp);
            }
        }else {
            //不需要分割
            stringList.add(s);
        }
        return stringList;
    }

}