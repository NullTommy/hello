package com.TestArch;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by NullTommy on 2018-07-27 14:38
 **/
public class TestMatch {

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
            List<String> stringList = new ArrayList<>();
            while (line != null) {
                // 一次读入一行数据
                line = br.readLine();
                if(line != null){
                    stringList.addAll(getMatchers("#\\S+",line));
                }
            }
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


    static private  List<String> getMatchers(String regex, String source){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }

}