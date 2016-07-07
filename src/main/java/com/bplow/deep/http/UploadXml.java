/**
 * www.bplow.com
 */
package com.bplow.deep.http;

import java.io.File;
import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.FileRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2016年7月7日 下午9:54:01
 */
public class UploadXml {
	
	public void uploadXml(){
		 String strURL = "";
	        // Get file to be posted
	        String strXMLFilename = "";
	        File input = new File(strXMLFilename);
	        // Prepare HTTP post
	        PostMethod post = new PostMethod(strURL);
	        // Request content will be retrieved directly
	        // from the input stream
	        RequestEntity entity = new FileRequestEntity(input, "text/xml; charset=ISO-8859-1");
	        post.setRequestEntity(entity);
	        // Get HTTP client
	        HttpClient httpclient = new HttpClient();
	        // Execute request
	        try {
	            int result = httpclient.executeMethod(post);
	            // Display status code
	            System.out.println("Response status code: " + result);
	            // Display response
	            System.out.println("Response body: ");
	            System.out.println(post.getResponseBodyAsString());
	        } catch (HttpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	            // Release current connection to the connection pool once you are done
	            post.releaseConnection();
	        }
	}

}
