package com.bplow.deep.base.domain;

/**
 * @desc 业务层返回对象，必须继承该对象
 * @author wangxiaolei
 * @date 2018年6月9日 下午5:42:47
 */

public class ServiceResult {

    public String responseCode;
    public String responseMessage;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

}
