package com.bplow.deep.base;

import com.bplow.deep.base.utils.Message;

public class MyPrinter2 implements Message{

	@Override
	public String print() {
		System.out.println("这是测试打印!");
		return "";
	}

}
