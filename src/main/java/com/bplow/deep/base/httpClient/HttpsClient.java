/**
 * www.bplow.com
 */
package com.bplow.deep.base.httpClient;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.base.pagination.PageInfo;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
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

        testHttpsClient();
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

            httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

            SSLSocketFactory socketFactory = new SSLSocketFactory(sslContext);
            Scheme sch = new Scheme("https", 443, socketFactory);
            httpClient.getConnectionManager().getSchemeRegistry().register(sch);
            
            //query(httpUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private CookieStore addCookie() {
        CookieStore cookieStore = new BasicCookieStore();
        //BasicClientCookie cookie = new BasicClientCookie("MEIQIA_EXTRA_TRACK_ID", "f473bf74238411e7bf2602356707f53e");
        //cookie.setDomain(".mycompany.com");
        //cookie.setPath("/");
        //cookieStore.addCookie(cookie);
        cookieStore.addCookie(new BasicClientCookie("TawkConnectionTime", "0"));
        InputStream in = this.getClass().getResourceAsStream("/http/cookies.txt");

        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(in));

        try {
            String lineStr = null;
            while ((lineStr = reader.readLine()) != null) {
                String[] value = lineStr.split("=");
                if (value != null && value.length > 1) {
                    cookieStore.addCookie(new BasicClientCookie(value[0], value[1]));
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

        HttpPost httpPost = createHttpPost(queryUrl);

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        //			nvps.add(new BasicNameValuePair("user", "abin"));
        //			nvps.add(new BasicNameValuePair("pwd", "abing"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        HttpResponse httpResponse = httpClient.execute(httpPost);
        String spt = System.getProperty("line.separator");
        BufferedReader buffer = new BufferedReader(new InputStreamReader(httpResponse.getEntity()
            .getContent()));
        StringBuffer stb = new StringBuffer();
        String line = null;
        while ((line = buffer.readLine()) != null) {
            stb.append(line);
        }
        buffer.close();
        httpPost.completed();
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

    public JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

}
