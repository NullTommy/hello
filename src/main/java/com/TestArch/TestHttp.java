package com.TestArch;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * 功能描述: <br>
 * 〈〉
 *  [参考: Java工具类--通过HttpClient发送http请求_程高伟-CSDN博客_httpclient发送请求 https://blog.csdn.net/frankcheng5143/article/details/50070591 ]
 * @return:
 * @since: 1.0.0
 * @Date: 2021/6/17 0017 13:40
 */
public class TestHttp {
    public static void main(String args[]) throws IOException {

        //1.获得一个httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //2.生成一个get请求
        HttpGet httpget = new HttpGet("http://www.baidu.com/");
        //附：如何设置Cookie
        httpget.setHeader("Cookie", "RK=Gga0SaEucU");
        //3.执行get请求并返回结果
        CloseableHttpResponse response = httpclient.execute(httpget);

        try {
            //4.处理结果
            String result = null;
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity,"UTF-8");
            System.out.println("" + result);
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}