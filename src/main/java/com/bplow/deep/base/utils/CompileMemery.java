package com.bplow.deep.base.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


@Service
public class CompileMemery {
    
    private Logger          logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private ClassLoader classLoader;

    public Class comppile(String className) throws ClassNotFoundException, NoSuchMethodException,
                                          SecurityException, IllegalAccessException,
                                          IllegalArgumentException, InvocationTargetException,
                                          InstantiationException {

        String str = obtainScripte(className);
        //生成源代码的JavaFileObject
        SimpleJavaFileObject fileObject = new JavaSourceFromString(className, str);
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        //被修改后的JavaFileManager
        JavaFileManager fileManager = new ClassFileManager(compiler.getStandardFileManager(null,
            null, null));
        //执行编译
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null,
            Arrays.asList(fileObject));
        task.call();
        //获得ClassLoader，加载class文件
        this.classLoader = fileManager.getClassLoader(null);

        Class printerClass = classLoader.loadClass(className);

        /*Method m = printerClass.getMethod("print", null);
        m.invoke(printerClass.newInstance(), null);*/
        //获得实例
        //Printer printer = (Printer) printerClass.newInstance();
        //printer.print();
        return printerClass;
    }
    
    public ClassLoader getClassLoader(){
        
        return this.classLoader;
    }

    public String obtainScripte(String className) {

        String scripteCxt = jdbcTemplate.queryForObject("select a.scripte_cxt from t_scripte a where a.scripte_name = ? ", new String[] { className },
            String.class);

        /*String scripte = "" + "public class MyPrinter2  {" + "public void print() {"
                         + "System.out.println(\"test2\");" + "}}";*/

        logger.info("{}",scripteCxt.replace("\\", ""));
        
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
        }

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
