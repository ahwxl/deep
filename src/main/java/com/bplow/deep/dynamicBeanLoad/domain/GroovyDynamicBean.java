package com.bplow.deep.dynamicBeanLoad.domain;

public class GroovyDynamicBean extends DynamicBean{

	private String groovyId;
	
	public GroovyDynamicBean(String beanName) {
		super(beanName);
	}
	


	public String getGroovyId() {
		return groovyId;
	}

	public void setGroovyId(String groovyId) {
		this.groovyId = groovyId;
	}

	public String getGroovyName() {
		return groovyName;
	}

	public void setGroovyName(String groovyName) {
		this.groovyName = groovyName;
	}

	private String groovyName;
	
	@Override
	protected String getBeanXml() {
		
		String groovyXML ="<lang:groovy id=\""+beanName+"\" script-source=\"database:"+beanName+"\"/>";
		
		return groovyXML;
	}

}
