package com.bplow.deep.http;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 上传文件
 * 通过请求实体即为文件内容
 * 请求参数如果有特殊字符需要坐下 encode
 * 
 * @author wangxiaolei
 * @version $Id: FileSend.java, v 0.1 2016年7月6日 下午7:49:46 wangxiaolei Exp $
 */

public class FileSend {

    Log            log         = LogFactory.getLog(this.getClass());

    private String contextPath = "http://cmbc.3w.net579.com/bgw-front-cmbc";
    private String filePath    = "/jks/CCF_20160428_1.zip";
    private String requestUrl  = "/upload/CMBC/20160428/CCF_20160428_1.zip";
    private String queryStr    = "LhHfyv0c5fd2w8/kVNQzwGFxLoWp0tGe39PIfl+UxbfDrl8hrv0xczGUVw4+iSoTdPx+tCzc7ZFQQFOPJ080lQWyNqkK6/gxC+m6WzxSF5hkO1mWtrS081e9W4p+KTbA+n8wXvaxNoBTDJFOeq7/az3LjF8Oic8wt0kAjppKGP4=";
    
    public void init() {
        try {
            this.contextPath ="http://115.29.43.175/bgw-front-cmbc/";
            this.filePath ="/jks/notify.xml";
            this.requestUrl ="/file/fileNotify";
            upload(this.contextPath + this.requestUrl, this.filePath, this.queryStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean upload(String url, String fileName, String queryString) throws Exception {
        //XmlSignature xmlSignature = new XmlSignature();
        String filepath = this.getClass().getResource(fileName).getPath();
        File targetFile = new File(filepath);
        HttpClient client = new HttpClient();
        //log.info("文件上传地址：["+url+"]");
        PostMethod filePost = new PostMethod(url);
        Part[] parts = { new FilePart(targetFile.getName(), targetFile) };
        filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));
        filePost.setQueryString("sign="+java.net.URLEncoder.encode(queryString));
        client.getHttpConnectionManager().getParams().setSoTimeout(60000);
        client.getHttpConnectionManager().getParams().setConnectionTimeout(10000);
        log.info("post完整地址:[" + filePost.getURI() + "]");
        log.info("QueryString:[" + filePost.getQueryString() + "]");
        int status = client.executeMethod(filePost);
        log.debug("文件上传状态码:[status " + status + "]");
        if (status == HttpStatus.SC_OK) {
            String res = filePost.getResponseBodyAsString();
            log.info("==>接收FileAccept报文：" + res);
            //log.info("==>verify fileAccept:["+xmlSignature.signVerification(res,keyStore.getPublicKey())+"]");
            /*if (contains(res, "FileAccept")) {
                log.debug("文件上传成功................");
                return true;
            }*/
        }
        log.info("文件上传失败...........");
        return false;
    }

    public static void main(String[] args) {
        FileSend sd = new FileSend();
        sd.init();
    }

}
