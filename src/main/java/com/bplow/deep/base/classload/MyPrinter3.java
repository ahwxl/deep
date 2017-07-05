package com.bplow.deep.base.classload;

import com.bplow.deep.base.utils.Message;

public class MyPrinter3 implements Message{

    @Override
    public String print() {
        
        System.out.println("测试中");
        
        return "";
    }

}
