package yinao.qualityLife.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import yinao.qualityLife.model.LoginDetail;
import yinao.qualityLife.model.ResultMap;
import yinao.qualityLife.model.vo.RequestLoginUser03;
import yinao.qualityLife.service.LoginService;
import yinao.qualityLife.utils.Md5Utils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


/**
 * @author shan
 *
 */
@RestController
public class SendSmsController {
	//产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";
    private final LoginService loginService;
    @Value("${KEY}")
    private String KEY;
    @Autowired
    public SendSmsController(LoginService loginService) {
        this.loginService = loginService;
       
        
    }
    
 // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
   //@Value("${accessKeyId}");
   //@Value("${accessKeySecret}");
    static final String accessKeyId="LTAI7bkmvaIAQHT8";//"LTAI8Iu9OevZOFP6";
    static final String accessKeySecret ="Qq7i62RQyH1PGSKwoDY8Ygv05nAVea"; //"7TFTEtNVJgAxTzgKpUMJRfsh1PkvXL";
  
    public static String sendSms(String code,String userName,String userType) throws ClientException {
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest requests = new SendSmsRequest();
        //必填:待发送手机号
        requests.setPhoneNumbers(userName);
        //必填:短信签名-可在短信控制台中找到
        if(userType.equals("EMPLOYER")) {
        	  requests.setSignName("要管家");
        }else {
        	  requests.setSignName("管家大学");
        }
        //必填:短信模板-可在短信控制台中找到
        requests.setTemplateCode("SMS_140722134");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
       // requests.setTemplateParam("\"code\":\"code\"}");
        requests.setTemplateParam("{\"code\":\""+code+"\"}");
        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        requests.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        requests.setOutId("yourOutId");

      //  hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(requests);
        //return sendSmsResponse;
        if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
        	//请求成功
        	return "success";
    	}else {
    		return "failed";
    	} 
    }
	@RequestMapping(value = "/user/getverifycode", method = RequestMethod.POST)
	public ResultMap sendMsg(@Valid RequestLoginUser03 requestLoginUser03, BindingResult bindingResult , HttpServletRequest request) {
	//String phoneNumber = request.getParameter("username");
	 if (bindingResult.hasErrors()){
         return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
     }	
	 String userType = request.getParameter("usertype");
	 LoginDetail loginDetail = loginService.getLoginDetail(requestLoginUser03.getUsername(), requestLoginUser03.getUsertype());
     ResultMap ifLoginFail = checkAccount03(requestLoginUser03,loginDetail);
     if (ifLoginFail != null){
         return ifLoginFail;
     }
	String randomNum = getCode();// 生成随机数
	SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
	Calendar c = Calendar.getInstance();
	c.add(Calendar.MINUTE, 5);
	String currentTime = sf.format(c.getTime());// 生成5分钟后时间，用户校验是否过期
	try {
		String GetReturMsg=sendSms(randomNum, requestLoginUser03.getUsername(),userType);
		if(GetReturMsg.equals("success")) {
			String hash = Md5Utils.getMd5(KEY + "@" + currentTime + "@" + randomNum);//生成MD5值
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("hash", hash);
			resultMap.put("tamp", currentTime);
			return new ResultMap().success().message("成功").data(resultMap);
		}else {
			return new ResultMap().fail("405").message("发送失败").data("");
		}
		
	} catch (ClientException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} //此处执行发送短信验证码方法
	return null ; 
}
	
	 //注册验证
    private ResultMap checkAccount03(RequestLoginUser03 requestLoginUser03,LoginDetail loginDetail){
    	switch (requestLoginUser03.getRegistertype()) {
		case "0":
			if (loginDetail != null){
            	return new ResultMap().fail("434").message("改账号已经被注册！").data("");
            }
			break;
		case "1":
			if (loginDetail == null){
            	return new ResultMap().fail("433").message("尚未注册").data("");
            }
			break;
		case "2":
			if (loginDetail == null){
            	return null;
            }
			break;

		default:
			break;
		}
        return null;
    }
	
	 /** 
     * 随机生成6位数字作为验证码 
     * @return 
     */  
    public static String getCode(){  
    	
        int code=(int)(Math.random()*9000+100000);  
        return code + "";  
    }
    
    
	
}
