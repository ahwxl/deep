package com.bplow.deep.base;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;
import java.security.PermissionCollection;

import javax.transaction.Transactional;

import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bplow.deep.base.classload.DynamicClassLoader;
import com.bplow.deep.base.utils.CompileMemery;
import com.bplow.deep.base.utils.ReloadBeanService;

@ContextConfiguration(locations = { "/applicationContext.xml", "/applicationContext-myclient.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ActiveProfiles("development")
public class CompileMemeryTest {

    private Logger             logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CompileMemery      compileMemery;

    @Autowired
    private ReloadBeanService refreshBeanService;

    @Test
    public void testCompile() {

        try {
            compileMemery.comppile("MyPrinter2");
        } catch (ClassNotFoundException e) {
            logger.error("", e);
        } catch (NoSuchMethodException e) {
            logger.error("", e);
        } catch (SecurityException e) {
            logger.error("", e);
        } catch (IllegalAccessException e) {
            logger.error("", e);
        } catch (IllegalArgumentException e) {
            logger.error("", e);
        } catch (InvocationTargetException e) {
            logger.error("", e);
        } catch (InstantiationException e) {
            logger.error("", e);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testFresh() throws Exception {
        for (int i = 0; i < 5; i++) {
            refreshBeanService.fresh("MyPrinter2");
        }

    }

    @Test
    public void testBeanInfo() throws Exception {

        Class beanClass = compileMemery.comppile("MyPrinter2");

        System.out.println(beanClass.getClassLoader());

        BeanInfo beanInfo = Introspector.getBeanInfo(beanClass, Introspector.IGNORE_ALL_BEANINFO);

        System.out.println(beanInfo);
    }

    @Test
    public void testClassLoad() throws Exception {

        String s = "file:d://logs";
        URL url = new URL(s);

        s = "file:c:/test1.jar";
        s = "C:\\Users\\wangxiaolei.FFT\\git\\deep\\target\\test-classes";
        s = "D:\\logs";

        URL url1 = null;
        url1 = new URL("file", null, new File(s).getCanonicalPath() + File.separator);
        String repository = url1.toString();
        URLStreamHandler streamHandler = null;
        url1 = new URL(null, repository, streamHandler);

        /*URLClassLoader myClassLoader = new URLClassLoader(new URL[] { url1 }, Thread.currentThread()
            .getContextClassLoader());*/
        /*DynamicClassLoader myClassLoader = new DynamicClassLoader(null);
        
        myClassLoader.addClassPath("d:/logs");
        
        Class myClass = myClassLoader.loadClass("MyPrinter2");
        
        System.out.println(myClass.getClassLoader());*/
        //WebAppContext app =new WebAppContext();

        WebAppClassLoader wac = new WebAppClassLoader(new MyWebAppContext());
        wac.addClassPath("d:/logs/");
        Class myClass2 = wac.loadClass("MyPrinter2");

        System.out.println(myClass2.getClassLoader());
    }

}

class MyWebAppContext implements WebAppClassLoader.Context {

    @Override
    public Resource newResource(String urlOrPath) throws IOException {
        return Resource.newResource(urlOrPath);
    }

    @Override
    public PermissionCollection getPermissions() {
        return null;
    }

    @Override
    public boolean isSystemClass(String clazz) {
        return false;
    }

    @Override
    public boolean isServerClass(String clazz) {
        return false;
    }

    @Override
    public boolean isParentLoaderPriority() {
        return false;
    }

    @Override
    public String getExtraClasspath() {
        return null;
    }

}
