package com.bplow.deep.dynamicBeanLoad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import com.bplow.deep.base.groovy.ScriptFactoryPostProcessorCustom;
import com.bplow.deep.dynamicBeanLoad.domain.DynamicBean;

@Service("dynamicBeanReader")
public class DynamicBeanReaderImpl implements DynamicBeanReader,
		ApplicationContextAware, InitializingBean {

	private Logger logger = LoggerFactory
			.getLogger(DynamicBeanReaderImpl.class);

	private ConfigurableApplicationContext applicationContext = null;

	private XmlBeanDefinitionReader beanDefinitionReader;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = (ConfigurableApplicationContext) applicationContext;
	}

	public void init() {
		beanDefinitionReader = new XmlBeanDefinitionReader(
				(BeanDefinitionRegistry) applicationContext.getBeanFactory());
		beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(
				applicationContext));
	}

	public void loadBean(DynamicBean dynamicBean) {
		long startTime = System.currentTimeMillis();
		String beanName = dynamicBean.getBeanName();
		BeanDefinitionRegistry beanDefinitionRegistry = (DefaultListableBeanFactory) applicationContext.getBeanFactory();
		if (applicationContext.containsBean(beanName)) {
			logger.warn("卸载bean【" + beanName + "】！");
			destroyScriptsFromSpringContext(beanName);
			//beanDefinitionRegistry.removeBeanDefinition(beanName);
		}
		
		beanDefinitionReader.loadBeanDefinitions(new DynamicResource(
				dynamicBean));
		//BeanDefinition abc = beanDefinitionRegistry.getBeanDefinition(beanName);
		//beanDefinitionRegistry.
		//abc.setBeanClassName(beanName);
		//beanDefinitionRegistry.registerBeanDefinition(beanName, abc);
		//applicationContext.refresh();
		
		// 添加BeanPostProcessor，主要用来确保CustomScriptFactoryPostProcessor生效
        String[] postProcessorNames = applicationContext.getBeanFactory().getBeanNamesForType(
            BeanPostProcessor.class, true, false);

        for (String postProcessorName : postProcessorNames) {
            applicationContext.getBeanFactory().addBeanPostProcessor(
                (BeanPostProcessor) applicationContext.getBean(postProcessorName));
        }
		
		logger.info("初始化bean【" + dynamicBean.getBeanName() + "】耗时"
				+ (System.currentTimeMillis() - startTime) + "毫秒。");
	}
	
	/** register the bean */
    public static void registerBean(String beanId,String className) {
        // get the BeanDefinitionBuilder
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(className);
        // get the BeanDefinition
        BeanDefinition beanDefinition=beanDefinitionBuilder.getBeanDefinition();
        
        // register the bean
        //beanDefinitionReader.registerBeanDefinition(beanId,beanDefinition);
    }
    /** unregister the bean */
    public static void unregisterBean(String beanId){
    	//beanDefinitionReader.removeBeanDefinition(beanId);
    }

	@Override
	public void afterPropertiesSet() throws Exception {
		init();
	}
	
    /**
     * 销毁变更过的脚本
     * 
     * @param destroyTemplates
     */
    private void destroyScriptsFromSpringContext(String beanName) {

        String[] postProcessorNames = applicationContext.getBeanFactory().getBeanNamesForType(
            ScriptFactoryPostProcessorCustom.class, true, false);

        ScriptFactoryPostProcessorCustom processor = (ScriptFactoryPostProcessorCustom) applicationContext
            .getBean(postProcessorNames[0]);

        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext
            .getAutowireCapableBeanFactory();

        // 先清理script加载的bean
        processor.destoryBean(beanName);

        // 再清理全局bean
        beanFactory.removeBeanDefinition(beanName);

        logger.info("已创建的bean清理完成");
    }

}
