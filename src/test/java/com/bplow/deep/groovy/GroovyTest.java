package com.bplow.deep.groovy;

import java.io.File;
import java.io.IOException;

import groovy.lang.GroovyClassLoader;

import org.codehaus.groovy.control.CompilationFailedException;
import org.junit.Test;

import com.bplow.deep.base.groovy.Foo;

public class GroovyTest {

    @Test
    public void groovyTest() throws InstantiationException, IllegalAccessException, CompilationFailedException, IOException {

        GroovyClassLoader gclassload = new GroovyClassLoader();
        String a = "";
        Class c = /*gclassload.parseClass("class Foo { void doIt() { println \"ok\" } }");*/
        gclassload.parseClass(new File("C://Users//wangxiaolei.FFT//git//deep//src//main//java//com//bplow//deep//base//groovy//FooImpl.groovy"));
        Foo obj = (Foo) c.newInstance();

        obj.doIt();
        obj.execute("");
    }
    

}
