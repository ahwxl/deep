package com.bplow.deep.base.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;

public class DynamicCompile {

    public void compile() throws MalformedURLException, ClassNotFoundException,
                         NoSuchMethodException, SecurityException, IllegalAccessException,
                         IllegalArgumentException, InvocationTargetException,
                         InstantiationException {
        // 1.将代码写入内存中
        StringWriter writer = new StringWriter(); // 内存字符串输出流
        PrintWriter out = new PrintWriter(writer);
        out.println("package com.dongtai.hello;");
        out.println("public class Hello{");
        out.println("public static void main(String[] args){");
        out.println("System.out.println(\"HelloWorld! 2\");");
        out.println("}");
        out.println("}");
        out.flush();
        out.close();

        // 2.开始编译
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        JavaFileObject fileObject = new StringJavaObject("Hello", writer.toString());
        CompilationTask task = javaCompiler.getTask(null, null, null, Arrays.asList("-d", "./bin"),
            null, Arrays.asList(fileObject));
        boolean success = task.call();
        if (!success) {
            System.out.println("编译失败");
        } else {
            System.out.println("编译成功");
        }
        URL[] urls = new URL[] { new URL("file:/" + "./bin/") };
        URLClassLoader classLoader = new URLClassLoader(urls);
        Class classl = classLoader.loadClass("com.dongtai.hello.Hello");
        Method method = classl.getDeclaredMethod("main", String[].class);
        String[] argsl = { null };
        method.invoke(classl.newInstance(), argsl);
    }

}

class StringJavaObject extends SimpleJavaFileObject {
    //源代码  
    private String content = "";

    //遵循Java规范的类名及文件  
    public StringJavaObject(String _javaFileName, String _content) {
        super(_createStringJavaObjectUri(_javaFileName), Kind.SOURCE);
        content = _content;
    }

    //产生一个URL资源路径  
    private static URI _createStringJavaObjectUri(String name) {
        //注意此处没有设置包名  
        return URI.create("String:///" + name + Kind.SOURCE.extension);
    }

    //文本文件代码  
    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return content;
    }
}
