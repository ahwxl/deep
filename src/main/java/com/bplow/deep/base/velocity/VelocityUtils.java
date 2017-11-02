package com.bplow.deep.base.velocity;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class VelocityUtils {

    {
        Velocity.init();
    }

    public static String evaluate(Map<String, Serializable> param, String srcipt) {

        VelocityContext context = new VelocityContext();

        for (Map.Entry<String, Serializable> entry : param.entrySet()) {
            context.put(entry.getKey(), entry.getValue());
        }

        StringWriter w = new StringWriter();

        w = new StringWriter();
        Velocity.evaluate(context, w, "deep", srcipt);

        return w.toString();
    }

    public static void main(String[] args) {
        //VelocityUtils u = new VelocityUtils();
        Map<String, Serializable> param = new HashMap<String, Serializable>();
        param.put("project", "123456");
        param.put("name", "希望的田野上");

        String str = VelocityUtils.evaluate(param, "We are using $project $name to render this.");

        System.out.println(str);

    }

}
