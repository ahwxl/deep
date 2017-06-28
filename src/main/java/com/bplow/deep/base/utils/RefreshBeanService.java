package com.bplow.deep.base.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class RefreshBeanService implements ApplicationContextAware, BeanFactoryAware {

    private ApplicationContext applicationContext;

    private BeanFactory        beanFactory;

    @Autowired
    private CompileMemery      compileMemery;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public void fresh(String name) throws Exception {

        AutowireCapableBeanFactory cbeanFactory = applicationContext
            .getAutowireCapableBeanFactory();
        GenericApplicationContext gac = (GenericApplicationContext) this.applicationContext;
        
        if(cbeanFactory.containsBean(name)){
            gac.removeBeanDefinition(name);
        }

        Class targetClass = compileMemery.comppile(name);

        Map m = new HashMap();
        m.put("name", "Roderick");

        GenericBeanDefinition bd = new GenericBeanDefinition();
        bd.setBeanClass(targetClass);
        bd.setPropertyValues(new MutablePropertyValues());
        bd.setDescription("测试");

        gac.registerBeanDefinition(name, bd);
        
        gac.setClassLoader(compileMemery.getClassLoader());
        
        //gac.refresh();

        Message msg = (Message)gac.getBean(name);
        
        msg.print();

    }

}
