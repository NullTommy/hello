/*
 * Copyright Dmall Life Network Technology Co., Ltd.
 *
 */
package com.MyUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpConnectionPoolUtil {

    /*保证时间不超过vapc调度间隔10s >= 3000 + 1500 + 5500 */
    /**
     * 从连接池获取连接的timeout
     */
    private static final int POOL_CONNECT_TIMEOUT = 3000;
    /**
     * 客户端和服务器建立连接的timeout
     */
    private static final int CONNECT_TIMEOUT = 4000;
    /**
     * 客户端和服务器建立连接后，客户端从服务器读取数据的timeout
     */
    private static final int SOCKET_TIMEOUT = 5500;
    /**
     * 连接池最大连接数
     */
    private static final int MAX_CONN = 400;
    /**
     * 默认每路由最大连接数
     */
    private static final int DEFAULT_MAX_CONN_PRE_ROUTE = 400;
    /**
     * 空闲链接最大存活时间
     */
    private static final int IDLE_TIMEOUT = 120000;
    /**
     * 空闲链接扫描间隔
     */
    private static final int IDLE_SCAN_INTERVAL = 60000;
    /**
     * 最大重试次数（不宜太多，vapc已经有重试，这里太多可能会短时间内占满链接）
     */
    private static final int MAX_RETRY_TIMES = 0;
    /**
     * 编码
     */
    private static final String CHARSET = "utf-8";

    /**
     * form格式
     */
    private static final String PARAM_FORM = "form";
    /**
     * json格式
     */
    private static final String PARAM_JSON = "json";


    /**
     * 发送请求的客户端单例
     * (volatile解决DCL问题)
     */
    private volatile static CloseableHttpClient httpClient;
    /**
     * 连接池管理类
     */
    private static PoolingHttpClientConnectionManager manager;
    /**
     * 监控线程，对异常和空闲线程进行关闭
     */
    private static ScheduledExecutorService monitorExecutor;

    /**
     * 线程锁,用于线程安全
     */
    private final static Object syncLock = new Object();

    /**
     * 对http请求进行基本设置
     *
     * @param httpRequestBase http请求
     */
    private static void setRequestConfig(HttpRequestBase httpRequestBase) {
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(POOL_CONNECT_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT).build();

        httpRequestBase.setConfig(requestConfig);
    }

    public static CloseableHttpClient getHttpClient() {

        if (httpClient == null) {
            //多线程下多个线程同时调用getHttpClient容易导致重复创建httpClient对象的问题,所以加上了同步锁
            synchronized (syncLock) {
                if (httpClient == null) {
                    httpClient = createHttpClient();
                    //开启监控线程,对异常和空闲线程进行关闭
                    monitorExecutor = Executors.newScheduledThreadPool(1);
                    monitorExecutor.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            //关闭异常连接
                            manager.closeExpiredConnections();
                            //关闭空闲的连接
                            manager.closeIdleConnections(IDLE_TIMEOUT, TimeUnit.MILLISECONDS);
                        }
                    }, IDLE_SCAN_INTERVAL, IDLE_SCAN_INTERVAL, TimeUnit.MILLISECONDS);
                }
            }
        }
        return httpClient;
    }

    /**
     * 根据host和port构建httpclient实例
     *
     * @return
     */
    private static CloseableHttpClient createHttpClient() {
        ConnectionSocketFactory plainSocketFactory = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", plainSocketFactory)
                .register("https", sslSocketFactory).build();

        manager = new PoolingHttpClientConnectionManager(registry);
        //设置连接参数
        manager.setMaxTotal(MAX_CONN);
        manager.setDefaultMaxPerRoute(DEFAULT_MAX_CONN_PRE_ROUTE);

        final String logHead = "[http-client][createHttpClient]";
        //请求失败时,进行请求重试
        HttpRequestRetryHandler handler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException e, int i, HttpContext httpContext) {
                if (i > MAX_RETRY_TIMES) {
                    //重试超过MAX_RETRY_TIMES次,放弃请求
                    log.error("{} retry more than {} times, give up!", logHead, MAX_RETRY_TIMES, e);
                    return false;
                }
                if (e instanceof NoHttpResponseException) {
                    //服务器没有响应,可能是服务器断开了连接,应该重试
                    log.error("{} receive no response from server, retry!", logHead, e);
                    return true;
                }
                HttpClientContext context = HttpClientContext.adapt(httpContext);
                HttpRequest request = context.getRequest();
                //如果请求类型不是HttpEntityEnclosingRequest，被认为是幂等的，那么就重试
                //HttpEntityEnclosingRequest指的是有请求体的request，比HttpRequest多一个Entity属性
                //而常用的GET请求是没有请求体的，POST、PUT都是有请求体的
                //Rest一般用GET请求获取数据，故幂等，POST用于新增数据，故不幂等
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    log.error("{} receive no response from server, retry!", logHead, e);
                    return true;
                }
                log.error("{} has error, give up!", logHead, e);
                return false;
            }
        };

        CloseableHttpClient client = HttpClients.custom().setConnectionManager(manager).setRetryHandler(handler).build();
        return client;
    }

    /**
     * 设置post请求头
     *
     * @param httpPost
     * @param header
     */
    private static void setHeader(HttpPost httpPost, Map<String, String> header) {
        if (header != null && header.size() > 0) {
            for (Entry<String, String> entry : header.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * 设置post请求的forms参数
     *
     * @param httpPost
     * @param forms
     */
    @SneakyThrows
    private static void setPostForm(HttpPost httpPost, Map<String, String> forms) {
        if (forms != null && forms.size() > 0) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            for (Entry<String, String> entry : forms.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
            }

            httpPost.setEntity(new UrlEncodedFormEntity(nvps, CHARSET));

        }
    }

    /**
     * 设置post请求的Json参数
     *
     * @param httpPost
     * @param json
     */
    private static void setPostJson(HttpPost httpPost, String json) {
        if (!StringUtils.isEmpty(json)) {
            httpPost.setEntity(new StringEntity(json, ContentType.create("application/json", CHARSET)));
        }
    }

    /**
     * postJson
     *
     * @param url
     * @param json
     * @return
     */
    public static String postJson(String url, String json) {
        return post(url, null, null, json, "json");
    }
    
    /**
     * postJson
     *
     * @param url
     * @return
     */
    public static String sendGet(String url, Map<String,String> map) {
        return get(url, getParams(map));
    }
    
    /**
     * postJson
     *
     * @param url
     * @return
     */
    public static String sendPost(String url, Map<String,String> map) {
        return post(url, getParams(map));
    }
    
    

    /**
     * postJson
     *
     * @param url
     * @param header
     * @param json
     * @return
     */
    public static String postJson(String url, Map<String, String> header, String json) {
        return post(url, header, null, json, "json");
    }

    /**
     * postForm
     *
     * @param url
     * @param header
     * @param forms
     * @return
     */
    public static String postForm(String url, Map<String, String> header, Map<String, String> forms) {
        return post(url, header, forms, null, "form");
    }

    /**
     * 统一封装的post请求（日志尽量详尽，便于同第三方排查问题）
     * p.s 暂未实现xml，如有需要请自行扩展
     *
     * @param url
     * @param header
     * @param forms
     * @param block
     * @param type
     * @return
     */
    @SneakyThrows
    private static String post(String url, Map<String, String> header, Map<String, String> forms, String block, String type) {
        String logHead = String.format("[http-client][post] url:%s, header:%s, forms:%s, block:%s, type:%s", url, header, forms, block, type);
        HttpPost httpPost = new HttpPost(url);
        setRequestConfig(httpPost);
        setHeader(httpPost, header);
        if (PARAM_FORM.equals(type)) {
            setPostForm(httpPost, forms);
        } else if (PARAM_JSON.equals(type)) {
            setPostJson(httpPost, block);
        }
        CloseableHttpResponse response = null;
        String result = null;
        try {
            response = getHttpClient().execute(httpPost, HttpClientContext.create());
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();

            // 构建完整的返回
            StringBuilder builder = new StringBuilder();

            // 响应状态
            builder.append("status:" + response.getStatusLine());
            builder.append("headers:");
            HeaderIterator iterator = response.headerIterator();
            while (iterator.hasNext()) {
                builder.append("\t" + iterator.next());
            }
            // 判断响应实体是否为空
            if (entity != null) {
                result = EntityUtils.toString(entity,"UTF-8");
                builder.append("response length:" + result.length());
                builder.append("response content:" + result.replace("\r\n", ""));
            }
            String fullResp = builder.toString();
            if (statusCode == HttpStatus.SC_OK) {
                log.info("{} success! response: {}", logHead, fullResp);
            } else {
                log.error("{} fail! response: {}", logHead, fullResp);
            }
        } finally {
            IOUtils.closeQuietly(response);
        }
        return result;
    }
    
    /**
     * 统一封装的post请求（日志尽量详尽，便于同第三方排查问题）
     * p.s 暂未实现xml，如有需要请自行扩展
     *
     * @param url
     * @return
     */
    @SneakyThrows
    private static String get(String url, List<NameValuePair> nameValuePairList) {
        String logHead = String.format("[http-client][get] url:%s, param:%s", url, nameValuePairList);
        HttpGet  httpGet = new HttpGet(url);
        setRequestConfig(httpGet);
        httpGet.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
        httpGet.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
        //创建URIBuilder
        URIBuilder uriBuilder = new URIBuilder(url);
        //设置参数
        uriBuilder.addParameters(nameValuePairList);
        httpGet.setURI(uriBuilder.build());
        
        CloseableHttpResponse response = null;
        String result = null;
        try {
        	log.info("chargeUrl : {}", httpGet.getURI().toString());
            response = getHttpClient().execute(httpGet, HttpClientContext.create());
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();

            // 构建完整的返回
            StringBuilder builder = new StringBuilder();

            // 响应状态
            builder.append("status:" + response.getStatusLine());
            builder.append("headers:");
            HeaderIterator iterator = response.headerIterator();
            while (iterator.hasNext()) {
                builder.append("\t" + iterator.next());
            }
            // 判断响应实体是否为空
            if (entity != null) {
                result = EntityUtils.toString(entity);
                builder.append("response length:" + result.length());
                builder.append("response content:" + result.replace("\r\n", ""));
            }
            String fullResp = builder.toString();
            if (statusCode == HttpStatus.SC_OK) {
                log.info("{} success! response: {}", logHead, fullResp);
            } else {
                log.error("{} fail! response: {}", logHead, fullResp);
            }
        } finally {
            IOUtils.closeQuietly(response);
        }
        return result;
    }
    
    @SneakyThrows
    private static String post(String url, List<NameValuePair> nameValuePairList) {
        String logHead = String.format("[http-client][get] url:%s, param:%s", url, nameValuePairList);
        HttpPost  httpPost = new HttpPost(url);
        setRequestConfig(httpPost);
        httpPost.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
        httpPost.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
        //创建URIBuilder
        URIBuilder uriBuilder = new URIBuilder(url);
        //设置参数
        uriBuilder.addParameters(nameValuePairList);
        httpPost.setURI(uriBuilder.build());
        
        CloseableHttpResponse response = null;
        String result = null;
        try {
        	log.info("chargeUrl : {}", httpPost.getURI().toString());
            response = getHttpClient().execute(httpPost, HttpClientContext.create());
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();

            // 构建完整的返回
            StringBuilder builder = new StringBuilder();

            // 响应状态
            builder.append("status:" + response.getStatusLine());
            builder.append("headers:");
            HeaderIterator iterator = response.headerIterator();
            while (iterator.hasNext()) {
                builder.append("\t" + iterator.next());
            }
            // 判断响应实体是否为空
            if (entity != null) {
                result = EntityUtils.toString(entity);
                builder.append("response length:" + result.length());
                builder.append("response content:" + result.replace("\r\n", ""));
            }
            String fullResp = builder.toString();
            if (statusCode == HttpStatus.SC_OK) {
                log.info("{} success! response: {}", logHead, fullResp);
            } else {
                log.error("{} fail! response: {}", logHead, fullResp);
            }
        } finally {
            IOUtils.closeQuietly(response);
        }
        return result;
    }


    /**
     * 关闭连接池
     */
    public static void closeConnectionPool() {
        try {
            httpClient.close();
            manager.close();
            monitorExecutor.shutdown();
        } catch (IOException e) {
            log.error("[http-client]closeConnectionPool error!", e);
        }
    }
    
    /**
          * 组织请求参数{参数名和参数值下标保持一致}
	  * @return 参数对象
	  */
         public static List<NameValuePair> getParams(Map<String,String> map){
             /**
              * 校验参数合法性
              */
             boolean flag = CollectionUtils.isEmpty(map);
             if (!flag){
                 List<NameValuePair> nameValuePairList = new ArrayList<>();
                 Iterator<Entry<String,String>> ite = map.entrySet().iterator();
                 while(ite.hasNext()) {
                	 Entry<String,String> ent = ite.next();
                	 String key = ent.getKey();
                	 String value = ent.getValue();
                	 nameValuePairList.add(new BasicNameValuePair(key,value));
                 }
                 return nameValuePairList;
             }else{
            	 log.error("HttpClientService-line: {}, errorMsg：{}", 197, "请求参数为空且参数长度不一致");
             }
             return null;
         }

    public static void main(String[] args) {
        Map header = new HashMap();
        header.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        header.put("Origin", "https://passport.csdn.net");
        header.put("Referer", "https://passport.csdn.net/login");
        header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.157 Safari/537.36");
        Map form = new HashMap();
        form.put("data", "106!RI0mc0clZmmcUfmuHmjz5Frca5nTQzJh5UamzLHtKj7U5xjetD0aoghmzEHCni3KEEBA1NMejMrxFMgLYNlUq4xfcr9p3a1Et84YJvbVVrOiGDQCMTqMD5YGsUgautLOWBkyns1qIXtoGTKOo1Sm49+/JOHHi+MRHT61O4rkysYJPfogzVV5s7LxDIbcNXUBSvMCHnVtxYvwvfz7UnjYwT13OHELlT/Q4xDj67/Tr8tvyMU8sQQ9XrkU9Iys/ysK42Uj9T/Q66EAp8bXxXcEXLxgqNDZhJxIp+vDxue1s6VOGCaLZ8YrpSDavke2hX/D0obEpI+x7gT8I0M0HB/l2pQaZhpPHBkrtBpox3FqQrxBuaXb5SndkLwm/acsdY+yuMJhRdTSHWsguuz8x9CnEpIn+Iuu3vc5tv7oeQkMxG2pCQHhdDQV+fE3b5nJD8CSog6QbKRmLcRMfFI4HnmIU94zM+B0R28VfhXqucz87oEZi6YLxsA74j+9pew51fU+3GyB2W4uynSjNqDLUBp+y5RCyS+Uo4hGYTxd/fW5J4VJsS5IprFXNmvxEeD0n7sUIt1d5qcE+g5vi80kQa4KmXqMwl3mTq445UgQ0uLVz1xOTnMHmz4ycjQTgq0S3MeiJHPfQDVH6quK3h7nNQFyjJhbjtC8kBHFeLKNVrf5O914GWfOhHQietD8f6==");
        form.put("xa", "saf-aliyun-com");
        System.out.println(HttpConnectionPoolUtil.postForm("https://ynuf.aliapp.org/service/um.json", header, form));
        System.out.println(HttpConnectionPoolUtil.postJson("https://ynuf.aliapp.org/service/um.json", header, null));
        System.out.println(HttpConnectionPoolUtil.postForm("http://tool.chinaz.com/Tools/httptest.aspx", null, form));
        System.out.println(HttpConnectionPoolUtil.postJson("http://tool.chinaz.com/Tools/httptest.aspx", null, "{\"data\":null,\"message\":\"account.pwd.notmatch\",\"success\":false}"));

    }

}
