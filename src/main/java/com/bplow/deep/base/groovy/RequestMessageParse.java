package com.bplow.deep.base.groovy;

import com.bplow.deep.base.groovy.bean.MockMessage;


public interface RequestMessageParse {
	
	public MockMessage parseText(String msg);

}
