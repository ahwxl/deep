/**
 * www.bplow.com
 */
package com.bplow.deep.stock.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bplow.deep.base.jackson.JsonHelper;
import com.bplow.deep.stock.service.SendMessageService;
import com.bplow.deep.stock.vo.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2017年2月23日 下午9:45:52
 */
@Service
public class SendMessageServiceImpl implements SendMessageService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String url    = "   http://gw.api.taobao.com/router/rest";
    private String appkey = "23650268";
    private String secret = "37c0442750378cd62ad539de16b8f777";

    @Override
    public void sendMessage(Message msg) {

        TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend("");
        req.setSmsType("normal");
        req.setRecNum(msg.getMobile());
        req.setSmsTemplateCode("SMS_48730177");

        req.setSmsFreeSignName("内容管理");
        String smsParamentString = null;
        if(null == msg.getParament()){
        	smsParamentString = "{\"taskId\":\"201702240000008\",\"taskName\":\"文静，测试进行中\",\"date\":\"20170226\"}";
        }else{
        	try {
				smsParamentString = JsonHelper.toString(msg.getParament());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
        }
        
        req.setSmsParamString(smsParamentString);

        AlibabaAliqinFcSmsNumSendResponse rsp;
        try {
            rsp = client.execute(req);

            System.out.println(rsp.getBody());
        } catch (ApiException e) {
            logger.error("", e);
        }

    }

    public static void main(String[] args) {

        SendMessageServiceImpl obj = new SendMessageServiceImpl();
        Message msg = new Message();
        msg.setMobile("13681858154");
        obj.sendMessage(msg);
    }

}
