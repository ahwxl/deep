package com.bplow.deep.base.utils;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import freemarker.core.Environment;
import freemarker.ext.beans.StringModel;
import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class FieldDirective implements TemplateDirectiveModel {

    @Override
    public void execute(Environment env, Map map, TemplateModel[] tplModel,
                        TemplateDirectiveBody body) throws TemplateException, IOException {

        Object templatemodel = map.get("method");
        Writer out = env.getOut();

        if (templatemodel instanceof freemarker.template.SimpleScalar) {
            SimpleScalar ss = ((SimpleScalar) templatemodel);
            ss.getAsString();
        } else if (templatemodel instanceof freemarker.template.DefaultObjectWrapper) {
            Method method = (Method) templatemodel;
        } else if (templatemodel instanceof StringModel) {
            StringModel stringModel = (StringModel) templatemodel;
            Method method = (Method) stringModel.getWrappedObject();

            Class testClass = method.getDeclaringClass();
            String testClassName = testClass.getName();
            String testMethodName = method.getName();
            String testReturnClassName = method.getReturnType().getName();
            
            Class[] params = method.getParameterTypes();
            StringBuilder sb = new StringBuilder();

            for (Class tmpparam : params) {
                String paramclass = tmpparam.getName();
                paramclass = StringHelper.getClassName(paramclass);
                String classNameSmallPre = StringHelper.toPreSmall(paramclass);
                
                //testMethod
                sb.append("\t@Test \npublic void test").append(testMethodName).append("(){\n");
                
                //new 实例
                sb.append("\t\t").append(paramclass).append(" ").append(classNameSmallPre).append(" = new ").append(paramclass).append("();\n");
                
                Field[] fields = tmpparam.getDeclaredFields();
                Method[] domainMethods = tmpparam.getDeclaredMethods();
                for(Field field : fields){
                    //out.write(field.getName());
                }
                
                for(Method domainMtd : domainMethods){
                    String methodName = domainMtd.getName();
                    if("set".equalsIgnoreCase(methodName.substring(0, 3))){
                        sb.append("\t\t").append(classNameSmallPre).append(".").append(methodName).append("(")
                        .append("\"\");\n");
                    }
                }
                
                sb.append("\n");
                if(!testReturnClassName.contains("void")){
                    sb.append("\t\t").append(StringHelper.getClassName(testReturnClassName)).append(" ")
                    .append(StringHelper.toPreSmall(StringHelper.getClassName(testReturnClassName)))
                    .append(" = ");
                }
                
                sb.append(StringHelper.toPreSmall(StringHelper.getClassName(testClassName)))
                .append(".").append(testMethodName).append("(").append(classNameSmallPre)
                .append(");\n");
                
                sb.append("\n");
                
                sb.append("};");
            }

            out.write(sb.toString());
        }

        env.setVariable("product", ObjectWrapper.DEFAULT_WRAPPER.wrap(""));
        env.setVariable("pagecontent", ObjectWrapper.DEFAULT_WRAPPER.wrap(""));

        body.render(env.getOut());

    }

}
