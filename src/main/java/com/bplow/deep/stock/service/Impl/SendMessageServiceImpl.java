/**
 * www.bplow.com
 */
package com.bplow.deep.stock.service.Impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.bplow.deep.base.utils.DateUtils;
import com.bplow.deep.stock.service.SendMessageService;
import com.bplow.deep.stock.vo.Message;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2017年2月23日 下午9:45:52
 */
@Service
public class SendMessageServiceImpl implements SendMessageService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String url    = "   http://gw.api.taobao.com/router/rest";
    private String appkey = "";
    private String secret = "";

    @Override
    public void sendMessage(Message msg) {

        /*TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
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
        }*/
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "84boSOZYIsz9P3J52", "Ci44Yj0khLHLpQ32mObPjrxrNFVqvDQ");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("SignName", "内容管理");
        request.putQueryParameter("TemplateCode", "SMS_48730177");
        request.putQueryParameter("PhoneNumbers", "18221268154");
        request.putQueryParameter("TemplateParam", "{\"taskId\":\"201702240000008\",\"taskName\":\"文静，测试进行中\",\"date\":\"20170226\"}");
        
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        SendMessageServiceImpl obj = new SendMessageServiceImpl();
        Message msg = new Message();
        Map<String,Serializable> smsParam = new HashMap<String,Serializable>();
        smsParam.put("taskId", "11");
        smsParam.put("taskName", "22");
        smsParam.put("date", DateUtils.getShortDay());
        msg.setMobile("13681858154");
        msg.setParament(smsParam);
        obj.sendMessage(msg);
    }

}
