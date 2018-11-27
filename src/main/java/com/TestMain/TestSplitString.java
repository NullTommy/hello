package com.TestMain;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by panghaichen on 2018-07-27 14:38
 **/
public class TestSplitString {

    public static void main(String[] args){

        try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw

            /* 读入TXT文件 */
            String pathname = "src/main/resources/fileTemplate/input.txt"; // 相对路径,相对于project的路径
            File filename = new File(pathname); // 要读取以上路径的input.txt文件
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = "";
            StringBuilder stringBuilder =  new StringBuilder();
            while (line != null) {
                line = br.readLine(); // 一次读入一行数据
                if(line != null){
                    stringBuilder.append(line);
                    //System.out.println(line);
                }
            }
            //构建返回的StringList
            List<String> stringList = parse140StringList(stringBuilder);

            /* 将StringList输出到控制台，同时写入Txt文件 */
            File writename = new File("src/main/resources/fileTemplate/output.txt"); // 相对路径，如果没有则要建立一个新的output.txt文件
            writename.createNewFile(); // 创建新文件
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            for (String s :stringList){
                System.out.println(s);
                out.write(s+"\r\n"); // \r\n即为换行
            }
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static private  List<String> parse140StringList(StringBuilder stringBuilder){
        String s = stringBuilder.toString();
        Double splitSize = 140.0;
        int resultSize = 130;//截取后的大小
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