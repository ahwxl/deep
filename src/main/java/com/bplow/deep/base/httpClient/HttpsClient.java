/**
 * www.bplow.com
 */
package com.bplow.deep.base.httpClient;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.transaction.Transactional;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.HTTP;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @desc  https://sycm.taobao.com/ipoll/live/visitor/getRtVisitor.json?device=2&limit=20&page=1&token=aaf65d00e1&type=Y&_=1514444693085
 * @author wangxiaolei
 * @date 2017年5月30日 下午3:29:47
 */
@ContextConfiguration(locations = { "/applicationContext.xml", "/applicationContext-myclient.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ActiveProfiles("development")
public class HttpsClient {

    public final ObjectMapper mapper     = new ObjectMapper();

    Logger                    logger     = LoggerFactory.getLogger(getClass());

    private String            httpUrl    = "https://sycm.taobao.com/ipoll/live/visitor/getRtVisitor.json?device=2&limit=20&page=1&token=aaf65d00e1&type=Y&_=1514444693085"; //Toronto
    // 客户端密钥库
    private String            sslKeyStorePath;
    private String            sslKeyStorePassword;
    private String            sslKeyStoreType;
    // 客户端信任的证书
    private String            sslTrustStore;
    private String            sslTrustStorePassword;

    SSLContext                sslContext = null;

    HttpClient                httpClient = null;

    @Before
    public void setUp() {
        sslKeyStorePath = "C:\\www\\home.jks";
        sslKeyStorePassword = "123456";
        sslKeyStoreType = "JKS"; // 密钥库类型，有JKS PKCS12等
        sslTrustStore = "C:\\www\\home.jks";
        sslTrustStorePassword = "123456";
        System.setProperty("javax.net.ssl.keyStore", sslKeyStorePath);
        System.setProperty("javax.net.ssl.keyStorePassword", sslKeyStorePassword);
        System.setProperty("javax.net.ssl.keyStoreType", sslKeyStoreType);
        // 设置系统参数
        System.setProperty("javax.net.ssl.trustStore", sslTrustStore);
        System.setProperty("javax.net.ssl.trustStorePassword", sslTrustStorePassword);

        //testHttpsClient();
    }

    @Test
    public void testHttpsClient() {
        try {
            KeyStore kstore = KeyStore.getInstance("jks");
            kstore.load(new FileInputStream(sslKeyStorePath), sslKeyStorePassword.toCharArray());
            KeyManagerFactory keyFactory = KeyManagerFactory.getInstance("sunx509");
            keyFactory.init(kstore, sslKeyStorePassword.toCharArray());
            KeyStore tstore = KeyStore.getInstance("jks");
            tstore.load(new FileInputStream(sslTrustStore), sslTrustStorePassword.toCharArray());
            TrustManager[] tm;
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("sunx509");
            tmf.init(tstore);
            tm = tmf.getTrustManagers();
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(keyFactory.getKeyManagers(), tm, null);

            //httpClient = new DefaultHttpClient();

            CookieStore cookieStore = addCookie();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslContext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
            httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).setSSLSocketFactory(sslsf).build();

            /*SSLSocketFactory socketFactory = new SSLSocketFactory(sslContext);
            Scheme sch = new Scheme("https", 443, socketFactory);
            httpClient.getConnectionManager().getSchemeRegistry().register(sch);*/
            
            query(httpUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private CookieStore addCookie() {
        CookieStore cookieStore = new BasicCookieStore();
        
        BasicClientCookie uc3cookie = new BasicClientCookie("uc3", "sg2=UU6oKN6CHlzaNUjjLjJ0fGWG8WmkyUtbqJ0QerQ0hf0%3D&nk2=CdKE71Qlfw%3D%3D&id2=UNiDS05we6k%3D&vt3=F8dBzLeMUtuDb2k4nz0%3D&lg2=W5iHLLyFOGW7aA%3D%3D");
        uc3cookie.setDomain("");
        cookieStore.addCookie(uc3cookie);
        
        BasicClientCookie uc1cookie = new BasicClientCookie("uc1", "cookie14=UoTdf1Q%2FZh847w%3D%3D&lng=zh_CN&cookie16=VFC%2FuZ9az08KUQ56dCrZDlbNdA%3D%3D&existShop=true&cookie21=UtASsssmfaCOMId3WwGQmg%3D%3D&tag=8&cookie15=WqG3DMC9VAQiUQ%3D%3D&pas=0");
        uc1cookie.setDomain("");
        cookieStore.addCookie(uc1cookie);
        
        InputStream in = this.getClass().getResourceAsStream("/http/cookies.txt");

        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(in));

        try {
            String lineStr = null;
            while ((lineStr = reader.readLine()) != null) {
                String[] value = lineStr.split("=");
                if (value != null && value.length > 1) {
                    BasicClientCookie tmpcookie = new BasicClientCookie(value[0], value[1]);
                    System.out.println(value[0]+"="+java.net.URLDecoder.decode(value[1]));
                    tmpcookie.setDomain("");
                    cookieStore.addCookie(tmpcookie);
                }
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

        return cookieStore;
    }

    public String query(String queryUrl) throws Exception {

       // HttpPost httpPost = createHttpPost(queryUrl);
        HttpGet httpGet  = createHttpGet(queryUrl);

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        //			nvps.add(new BasicNameValuePair("user", "abin"));
        //			nvps.add(new BasicNameValuePair("pwd", "abing"));
        //httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        HttpResponse httpResponse = httpClient.execute(httpGet);
        String spt = System.getProperty("line.separator");
        BufferedReader buffer = new BufferedReader(new InputStreamReader(httpResponse.getEntity()
            .getContent()));
        StringBuffer stb = new StringBuffer();
        String line = null;
        while ((line = buffer.readLine()) != null) {
            stb.append(line);
        }
        buffer.close();
        httpGet.completed();
        String result = stb.toString();
        System.out.println("result=" + result);
        return result;
    }

    private HttpPost createHttpPost(String queryUrl) {
        HttpPost httpPost = new HttpPost(queryUrl);

        //httpPost.setHeader("Accept",
        //   "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        /*httpPost.setHeader("Accept-Encoding", "gzip, deflate, sdch, br");
        httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpPost.setHeader("Cache-Control", "max-age=0");
        httpPost.setHeader("Connection", "keep-alive");
        httpPost.setHeader("Host", "www.homadorma.com");
        httpPost.setHeader("Upgrade-Insecure-Requests", "1");
        httpPost.setHeader("Content-Type", "text/html; charset=UTF-8");
        httpPost
            .setHeader(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");*/

        InputStream in = this.getClass().getResourceAsStream("/http/header.txt");

        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(in));

        try {
            String lineStr = null;
            while ((lineStr = reader.readLine()) != null) {
                String[] value = lineStr.split(":");
                if (value != null && value.length > 1) {
                    httpPost.setHeader(value[0], value[1]);
                }
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

        return httpPost;
    }
    
    private HttpGet createHttpGet(String queryUrl) {
        HttpGet httpGet = new HttpGet(queryUrl);

        //httpPost.setHeader("Accept",
        //   "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        /*httpPost.setHeader("Accept-Encoding", "gzip, deflate, sdch, br");
        httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpPost.setHeader("Cache-Control", "max-age=0");
        httpPost.setHeader("Connection", "keep-alive");
        httpPost.setHeader("Host", "www.homadorma.com");
        httpPost.setHeader("Upgrade-Insecure-Requests", "1");
        httpPost.setHeader("Content-Type", "text/html; charset=UTF-8");
        httpPost
            .setHeader(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");*/

        InputStream in = this.getClass().getResourceAsStream("/http/header.txt");

        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(in));

        try {
            String lineStr = null;
            while ((lineStr = reader.readLine()) != null) {
                String[] value = lineStr.split(":");
                if (value != null && value.length > 1) {
                    httpGet.setHeader(value[0], value[1]);
                }
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

        return httpGet;
    }

    public JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

}
