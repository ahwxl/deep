package com.bplow.deep.dynamicBeanLoad.domain;

public abstract class DynamicBean {

	protected String beanName;  
	  
    public DynamicBean(String beanName) {  
        this.beanName = beanName;  
    }  
  
    public String getBeanName() {  
        return beanName;  
    }
    
    public void setBeanName(String beanName) {  
        this.beanName = beanName;  
    }  
      
    /** 
     * 获取bean 的xml描述 
     * @return 
     */  
    protected abstract String getBeanXml();  
      
    /** 
     * 生成完整的xml字符串 
     * @return 
     */  
    public String getXml(){  
        StringBuffer buf = new StringBuffer();  
        buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n")  
            .append("<beans xmlns=\"http://www.springframework.org/schema/beans\"  \n ")
	        .append("  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n ")
		    .append("  xmlns:lang=\"http://www.springframework.org/schema/lang\"\n ")
			.append("  xmlns:context=\"http://www.springframework.org/schema/context\"\n ")
			.append("  xsi:schemaLocation=\"http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd\n ")
			.append("  http://www.springframework.org/schema/lang http://www.springframework.org/schema/lang/spring-lang.xsd\n ")
			.append("  http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.0.xsd\">\n ")  
            .append(getBeanXml())  
            .append("</beans>");
        return buf.toString();
    }  
}
