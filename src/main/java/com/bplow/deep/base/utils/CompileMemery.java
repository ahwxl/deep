package com.bplow.deep.base.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.security.PermissionCollection;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.bplow.deep.base.classload.WebAppClassLoader;

@Service
public class CompileMemery implements InitializingBean {

    private Logger            logger   = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JdbcTemplate      jdbcTemplate;

    private WebAppClassLoader newclassload;

    private String            rootPath = "D:/logs/";

    public Class comppile(String className) throws Exception {

        String webroot = System.getProperty("webapp.root");

        /*DynamicClassLoader newclassload = new DynamicClassLoader(null);
        newclassload.addClassPath("d:/logs");*/

        String str = obtainScripte(className);
        //生成源代码的JavaFileObject
        SimpleJavaFileObject fileObject = new JavaSourceFromString(className, str);
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        //被修改后的JavaFileManager
        JavaFileManager fileManager = new ClassFileManager(compiler.getStandardFileManager(null,
            null, null), null, rootPath);

        String jars = this.getClass().getResource("/").getPath();

        Iterable<String> options = Arrays.asList("-encoding", "GBK", "-classpath", jars, "-d",
            "D:/logs", "-sourcepath", "D:/logs");

        //执行编译
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, options,
            null, Arrays.asList(fileObject));
        task.call();
        //获得ClassLoader，加载class文件
        fileManager.getClassLoader(null);
        newclassload = new WebAppClassLoader(new MyWebAppContext());
        newclassload.addClassPath(this.rootPath + File.separator);

        Class printerClass = newclassload.loadClass(/*"com.bplow.deep.base.classload."+*/className);
        
//        Class printerClass = fileManager.getClassLoader(null).loadClass(className);

        Method m = printerClass.getMethod("print", null);
        m.invoke(printerClass.newInstance(), null);

        //获得实例
        //Printer printer = (Printer) printerClass.newInstance();
        //printer.print();
        return printerClass;
    }

    public ClassLoader getClassLoader() {

        return this.newclassload;

    }

    public String obtainScripte(String className) {

        String scripteCxt = jdbcTemplate.queryForObject(
            "select a.scripte_cxt from t_scripte a where a.scripte_name = ? ",
            new String[] { className }, String.class);

        /*String scripte = "" + "public class MyPrinter2  {" + "public void print() {"
                         + "System.out.println(\"test2\");" + "}}";*/

        logger.info("{}", scripteCxt.replace("\\", ""));

        return scripteCxt.replace("\\", "");

    }

    public static void main(String[] args) {

        CompileMemery cm = new CompileMemery();
        try {
            cm.comppile("MyPrinter2");

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {

        newclassload = new WebAppClassLoader(new MyWebAppContext());
        //newclassload.addClassPath(this.rootPath);

        String webroot = System.getProperty("webapp.root");

        if (StringUtils.isNotEmpty(webroot)) {
            rootPath = webroot;
        }

        newclassload.addClassPath(this.rootPath + File.separator);
    }

}

class JavaSourceFromString extends SimpleJavaFileObject {

    final String code;

    /**
     * Constructs a new JavaSourceFromString.
     * @param name the name of the compilation unit represented by this file object
     * @param code the source code for the compilation unit represented by this file object
     */
    JavaSourceFromString(String name, String code) {
        super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension),
            Kind.SOURCE);
        this.code = code;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return code;
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
