package com.bplow.deep.base.utils;

public class StringHelper {

    public static String toPreSmall(String str) {

        if (str == null || "".equals(str.trim())) {
            return "";
        }

        char[] className = str.toCharArray();
        className[0] = Character.toLowerCase(className[0]);

        return new String(className);

    }

    public static String toPreBig(String str) {

        if (str == null || "".equals(str.trim())) {
            return "";
        }

        char[] className = str.toCharArray();
        className[0] = Character.toUpperCase(className[0]);

        return new String(className);

    }

    public static String getClassName(String paramclass) {
        if (paramclass == null || "".equals(paramclass.trim())) {
            return "";
        }

        return paramclass = paramclass.substring(paramclass.lastIndexOf(".") + 1);
    }

}
