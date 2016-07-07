/**
 * www.bplow.com
 */
package com.bplow.deep.http;

import java.io.File;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * @desc
 * @author wangxiaolei
 * @date 2016年7月7日 下午9:42:59
 */
public class UploadFile {

	public void uploadFile() {
		String targetURL = "";
		PostMethod filePost = new PostMethod(targetURL);
		filePost.getParams().setBooleanParameter(
				HttpMethodParams.USE_EXPECT_CONTINUE, true);
		File targetFile = new File("");
		try {
			Part[] parts = { new FilePart(targetFile.getName(), targetFile) };
			filePost.setRequestEntity(new MultipartRequestEntity(parts,
					filePost.getParams()));
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams()
					.setConnectionTimeout(5000);
			int status = client.executeMethod(filePost);
			if (status == HttpStatus.SC_OK) {
				System.out.println("Upload complete, response="
						+ filePost.getResponseBodyAsString());
			} else {
				System.out.println("Upload failed, response="
						+ HttpStatus.getStatusText(status));
			}
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getClass().getName() + " "
					+ ex.getMessage());
			ex.printStackTrace();
		} finally {
			filePost.releaseConnection();
		}

	}

}
