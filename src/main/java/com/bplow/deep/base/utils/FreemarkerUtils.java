package com.bplow.deep.base.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import freemarker.cache.ClassTemplateLoader;
import freemarker.core.HTMLOutputFormat;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateNotFoundException;

public class FreemarkerUtils {
    
    private Configuration cfg;
    
    public void init() throws IOException{
        this.cfg = new Configuration(Configuration.VERSION_2_3_25);
        cfg.setTemplateLoader(new ClassTemplateLoader(getClass(), "/ftl"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setOutputFormat(HTMLOutputFormat.INSTANCE);
    }
    
    public void product(String classPath,String tml) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException, ClassNotFoundException{
        
        Template temp = cfg.getTemplate(tml);
        Map<String, Object> root = new HashMap<String,Object>();
        
        root.put("Field", new FieldDirective());
        
        //datamodul
        String classname = classPath;
        Class<?> targetClass = Class.forName(classname);
        Method[] methodArray = targetClass.getMethods();//测试类所有方法
        String testClassName = StringHelper.getClassName(classname);
        
        root.put("metheds", methodArray);
        root.put("testClassName", testClassName);
        root.put("testClassNamePreSmall", StringHelper.toPreSmall(testClassName));
        
        Writer out = new StringWriter();
        temp.process(root, out);
        File newfile = new File("D:/opt/"+testClassName+".java");
        if(!newfile.exists()){
            newfile.createNewFile();
        }else{
            System.out.println("文件已存在");
        }
        FileWriter fileWriter = new FileWriter(newfile);
        temp.process(root, fileWriter);

        String content = out.toString();
        
        System.out.println(content);
        fileWriter.close();
        
        out.flush();
        out.close();
        
        
        
    }
    
    public static void main(String[] args) {
        FreemarkerUtils free = new FreemarkerUtils();
        try {
            free.init();
            free.product("com.bplow.deep.stock.service.JobService","interfaceUint.ftl");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
