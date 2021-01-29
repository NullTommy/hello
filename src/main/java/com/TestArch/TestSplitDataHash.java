package com.TestArch;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述: <br>处理先知缓存的预测数据
 * 〈〉
 *
 * @return:
 * @since: 1.0.0
 * @Date: 2021/1/29 0029 11:26
 */
public class TestSplitDataHash {

    public static void main(String[] args){

        try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw

            /* 读入TXT文件 */
            // 相对路径,相对于project的路径
            String pathname = "src/main/resources/fileTemplate/dataHash.txt";
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
        stringBuilder = stringBuilder.trim();
        stringBuilder = stringBuilder.replace("{","");
        stringBuilder = stringBuilder.replace("}","");
        stringBuilder = stringBuilder.trim();
        String[] strings = stringBuilder.split(",");


        List<String> stringList = new ArrayList<>() ;
        for (String s : strings){
            s = s.trim();
            String[] strings2 = s.split(":");
            String build = "";
            boolean pass = false;
            for(int i = 1;i<strings2.length;i++){
                if(!pass){
                    if(i==1){
                        String tmp = strings2[i - 1] +":"+ strings2[i];
                        tmp = tmp.trim();
                        tmp = tmp.substring(1, tmp.length() - 1);
                        if(!tmp.startsWith(" ")){
                            build = build + "," + tmp;
                        }else {
                            pass=true;
                        }
                    }
                    if(i==2){
                        String tmp = strings2[i];
                        tmp = tmp.trim();
                        tmp = tmp.substring(1, tmp.length() - 1);
                        build = build + "," + tmp;
                    }
                }

            }
            if(!pass){
                stringList.add(build);
            }
        }
        return stringList;
    }
}