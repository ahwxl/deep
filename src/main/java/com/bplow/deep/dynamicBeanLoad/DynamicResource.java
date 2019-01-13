package com.bplow.deep.dynamicBeanLoad;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import org.springframework.core.io.Resource;

import com.bplow.deep.dynamicBeanLoad.domain.DynamicBean;

public class DynamicResource implements Resource {
	
	private DynamicBean dynamicBean;
    
    public DynamicResource(DynamicBean dynamicBean){  
        this.dynamicBean = dynamicBean;  
    }

	@Override
	public InputStream getInputStream() throws IOException {
		System.out.println(dynamicBean.getXml());
		return new ByteArrayInputStream(dynamicBean.getXml().getBytes("UTF-8"));
	}

	@Override
	public boolean exists() {
		return false;
	}

	@Override
	public boolean isReadable() {
		return false;
	}

	@Override
	public boolean isOpen() {
		return false;
	}

	@Override
	public URL getURL() throws IOException {
		return null;
	}

	@Override
	public URI getURI() throws IOException {
		return null;
	}

	@Override
	public File getFile() throws IOException {
		return null;
	}

	@Override
	public long contentLength() throws IOException {
		return 0;
	}

	@Override
	public long lastModified() throws IOException {
		return 0;
	}

	@Override
	public Resource createRelative(String relativePath) throws IOException {
		return null;
	}

	@Override
	public String getFilename() {
		return null;
	}

	@Override
	public String getDescription() {
		return null;
	}

}
