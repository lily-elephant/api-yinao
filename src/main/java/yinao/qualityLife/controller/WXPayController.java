package yinao.qualityLife.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.weixin4j.WeixinException;
import org.weixin4j.http.HttpsClient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import yinao.qualityLife.dao.courseCatagoryMapper;
import yinao.qualityLife.dao.employerMapper;
import yinao.qualityLife.model.ResultMap;
import yinao.qualityLife.model.domain.CourseCatagoryCount;
import yinao.qualityLife.model.domain.GetHkid;
import yinao.qualityLife.model.domain.ViewCard;
import yinao.qualityLife.model.domain.WeChat;
import yinao.qualityLife.utils.TokenUtils;
import yinao.qualityLife.utils.wxpay.IpUtils;
import yinao.qualityLife.utils.wxpay.Json;
import yinao.qualityLife.utils.wxpay.OAuthJsToken;
import yinao.qualityLife.utils.wxpay.PayUtil;
import yinao.qualityLife.utils.wxpay.StringUtils;
import yinao.qualityLife.utils.wxpay.WxPayConfig;
	@RestController
	public class WXPayController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private final TokenUtils tokenUtils;
	private final courseCatagoryMapper courseCatagoryMapper;
	private final employerMapper employerMapper;
	private static final String grant_type = "authorization_code";
	private static   String secret = "";//44252959dcb822bdc8c1d7df98524872
	private static   String appid = "";//wxdd268f672e8306c0
	private static   String mch_id = "";
	@Value("${token.header}")
	private String tokenHeader;
	@Value("${EMPLOY_APPID}")
	private String EMPLOY_APPID;
	@Value("${EMPLOY_APPSECRET}")
	private String EMPLOY_APPSECRET;
	@Value("${BUTLER_APPID}")
	private String BUTLER_APPID;
	@Value("${BUTLER_APPSECRET}")
	private String BUTLER_APPSECRET;
	@Value("${MCH_ID}")
	private String MCH_ID;
	public WXPayController(courseCatagoryMapper courseCatagoryMapper,employerMapper employerMapper,TokenUtils tokenUtils) {
		this.tokenUtils=tokenUtils;
		this.courseCatagoryMapper=courseCatagoryMapper;
		this.employerMapper=employerMapper;
		// TODO Auto-generated constructor stub
	}
	   /**
     * 小程序后台登录，向微信平台发送获取access_token请求，并返回openId
     *
     * @param code
     * @return openid
     * @throws WeixinException
     * @throws IOException
     * @since Weixin4J 1.0.0
     */
    @RequestMapping(value="wx/getopenid",method=RequestMethod.POST)
    public Map<String, Object> login(String code, HttpServletRequest request) throws WeixinException, IOException {
	if (code == null || code.equals("")) {
	    throw new WeixinException("invalid null, code is null.");
	}
	HttpServletRequest httpRequest = (HttpServletRequest) request;
	String token = httpRequest.getHeader(this.tokenHeader);
	String userType=this.tokenUtils.getUserTypeFromToken(token);
	if(userType.equals("EMPLOYER")) {
		appid = EMPLOY_APPID;
		secret= EMPLOY_APPSECRET;
	}else {
		appid=BUTLER_APPID;
		secret=BUTLER_APPSECRET;
	}
	Map<String, Object> ret = new HashMap<String, Object>();
	//拼接参数
	String param = "?grant_type=" + grant_type + "&appid=" + appid + "&secret=" + secret + "&js_code=" + code;

	//创建请求对象
	HttpsClient http = new HttpsClient();
	//调用获取access_token接口
	org.weixin4j.http.Response res = http.get("https://api.weixin.qq.com/sns/jscode2session" + param);
    //根据请求结果判定，是否验证成功
    JSONObject jsonObj = res.asJSONObject();
    if (jsonObj != null) {
	    Object errcode = jsonObj.get("errcode");
	    if (errcode != null) {
			System.err.println(errcode.toString());
			//返回异常信息
			ret.put("errcode", errcode.toString());
			return ret;
	    }
        ObjectMapper mapper = new ObjectMapper();
        OAuthJsToken oauthJsToken = mapper.readValue(jsonObj.toJSONString(),OAuthJsToken.class);

        ret.put("openid", oauthJsToken.getOpenid());
        }
        return ret;
    }
    
    /**
     * @Description: 发起微信支付
     * @param openid
     * @param request
     * @author: wcf
     * @date: 2017年8月28日
     */
    @RequestMapping(value="wx/wxPay",method=RequestMethod.POST)
    public Json wxPay(String openid, HttpServletRequest request){
    	Json json = new Json();
    	 String osid="";
    	 String money=request.getParameter("money");
    	 String productBrief=request.getParameter("productBrief");
    	 String transactionid=request.getParameter("transactionid");
    	 String businesstype=request.getParameter("businesstype");
    	 osid=request.getParameter("osid");
    	 String emid="";
    	 String hkid="";
    	if(money==null || productBrief==null || transactionid==null || businesstype==null) {
    		json.setMsg("缺少参数");
    		return json;
    	}
    	 HttpServletRequest httpRequest = (HttpServletRequest) request;
    	 String token = httpRequest.getHeader(this.tokenHeader);
    	 String username = this.tokenUtils.getUsernameFromToken(token);
    	 String usertype=this.tokenUtils.getUserTypeFromToken(token);
    	 if(usertype.equals("EMPLOYER")) {
    		 if(osid==null) {
    			 json.setMsg("缺少参数");
    	    		return json;
    		 }
    		 appid = EMPLOY_APPID;
    		 List<GetHkid> getEmids=this.courseCatagoryMapper.getEmid(username);
    		 emid=getEmids.get(0).getHkid();
    	 }else {
    		 appid=BUTLER_APPID;
    		 List<GetHkid> getHkids=this.courseCatagoryMapper.getHkid(username);
    		 hkid=getHkids.get(0).getHkid();	
    	 }
    	
        mch_id=MCH_ID;
        WeChat entity = new WeChat(emid , transactionid , businesstype , osid , hkid,usertype);
        String attach = JSON.toJSONString(entity) ; 
         try{
            //生成的随机字符串
            String nonce_str = StringUtils.getRandomStringByLength(32);
            //商品名称
            String body = productBrief;
            //获取本机的ip地址
            String spbill_create_ip = IpUtils.getIpAddr(request);

            String orderNo = StringUtils.getRandomCode();
           // String money = request.getParameter("money");//支付金额，单位：分，这边需要转成字符串类型，否则后面的签名会失败

            Map<String, String> packageParams = new HashMap<String, String>();
            packageParams.put("appid", appid);
            packageParams.put("mch_id", mch_id);
            packageParams.put("nonce_str", nonce_str);
            packageParams.put("body", body);
            packageParams.put("out_trade_no", orderNo);//商户订单号
            packageParams.put("total_fee", money);//支付金额，这边需要转成字符串类型，否则后面的签名会失败
            packageParams.put("spbill_create_ip", spbill_create_ip);
            packageParams.put("notify_url", WxPayConfig.v2_notify_url);
            packageParams.put("trade_type", WxPayConfig.TRADETYPE);
            packageParams.put("openid", openid);
            packageParams.put("attach", attach);
            // 除去数组中的空值和签名参数
            packageParams = PayUtil.paraFilter(packageParams);
            String prestr = PayUtil.createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串

            //MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
            String mysign = PayUtil.sign(prestr, WxPayConfig.key, "utf-8").toUpperCase();
            logger.info("=======================第一次签名：" + mysign + "=====================");

            //拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
            String xml = "<xml>" + "<appid>" + appid + "</appid>"
                    + "<body><![CDATA[" + body + "]]></body>"
                    + "<mch_id>" + mch_id + "</mch_id>"
                    + "<nonce_str>" + nonce_str + "</nonce_str>"
                    + "<notify_url>" + WxPayConfig.v2_notify_url + "</notify_url>"
                    + "<openid>" + openid + "</openid>"
                    + "<out_trade_no>" + orderNo + "</out_trade_no>"
                    + "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>"
                    + "<total_fee>" + money + "</total_fee>"
                    + "<trade_type>" + WxPayConfig.TRADETYPE + "</trade_type>"
                    + "<sign>" + mysign + "</sign>"
                    + "<attach>" + attach + "</attach>"
                    + "</xml>";

            System.out.println("调试模式_统一下单接口 请求XML数据：" + xml);

            //调用统一下单接口，并接受返回的结果
            String result = PayUtil.httpRequest(WxPayConfig.pay_url, "POST", xml);

            System.out.println("调试模式_统一下单接口 返回XML数据：" + result);

            // 将解析结果存储在HashMap中
            Map map = PayUtil.doXMLParse(result);

            String return_code = (String) map.get("return_code");//返回状态码
            

            //返回给移动端需要的参数
            Map<String, Object> response = new HashMap<String, Object>();
            if(return_code == "SUCCESS" || return_code.equals(return_code)){
                // 业务结果
                String prepay_id = (String) map.get("prepay_id");//返回的预付单信息
                response.put("nonceStr", nonce_str);
                response.put("package", "prepay_id=" + prepay_id);
                Long timeStamp = System.currentTimeMillis() / 1000;
                response.put("timeStamp", timeStamp + "");//这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误

                String stringSignTemp = "appId=" + appid + "&nonceStr=" + nonce_str + "&package=prepay_id=" + prepay_id+ "&signType=" + WxPayConfig.SIGNTYPE + "&timeStamp=" + timeStamp;
                //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
                String paySign = PayUtil.sign(stringSignTemp, WxPayConfig.key, "utf-8").toUpperCase();
                logger.info("=======================第二次签名：" + paySign + "=====================");

                response.put("paySign", paySign);
                //更新订单信息，业务逻辑代码
                if(usertype.equals("BUTLER")) {
                	this.courseCatagoryMapper.addHouseKeepOrder(hkid,orderNo,money,transactionid,businesstype);
           		 	
           	 	}else {
           	 		if(businesstype.equals("预约金")) {
           	 			this.courseCatagoryMapper.addEmployOrder(emid,orderNo,money,transactionid,businesstype,osid);
           	 		}
           	 		// v2版本新增，订单表增加订单数据，payflag=0（未支付状态）
           	 		else if(businesstype.equals("V2_购买查询卡")){
           	 			this.courseCatagoryMapper.addEmployOrder(emid,orderNo,money,transactionid,businesstype,osid);	
           	 		}
           	 		else {
           	 			if(request.getParameter("billid")==null || request.getParameter("billid")=="") {
	           	 			json.setMsg("缺少参数");
	        	    		return json;
           	 			}
           	 			this.courseCatagoryMapper.updateEmployer(request.getParameter("billid"),orderNo);
           	 		}
           	 		
       	 		} 
             }
            response.put("appid", appid);
            json.setSuccess(true);
            json.setData(response);
        }catch(Exception e){
            e.printStackTrace();
            json.setSuccess(false);
            json.setMsg("发起失败");
        }
        return json;
    }
	
    @RequestMapping(value="wx/wxNotify")
    public void wxNotify(HttpServletRequest request,HttpServletResponse response) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while((line = br.readLine())!=null){
            sb.append(line);
        }
        br.close();
        //sb为微信返回的xml
        String notityXml = sb.toString();
        String resXml = "";
        System.out.println("接收到的报文：" + notityXml);
        Map map = PayUtil.doXMLParse(notityXml);
        String returnCode = (String) map.get("return_code");
        if("SUCCESS".equals(returnCode)){
        	 Map ben_map = PayUtil.paraFilter(map);
             String prestr = PayUtil.createLinkString(ben_map); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
            //验证签名是否正确
            if(PayUtil.verify(prestr, (String)map.get("sign"), WxPayConfig.key, "utf-8")){
                
            	/**此处添加自己的业务逻辑代码start**/
            	String money=(String)map.get("total_fee");
            	String attach=(String)map.get("attach");
            	String orderNum=(String)map.get("out_trade_no");
            	String threeNum=(String)map.get("transaction_id");
            	JSONObject jsStr = JSONObject.parseObject(attach);
            	String usertype = (String) jsStr.get("usertype");
            	String businesstype=(String) jsStr.get("businesstype");
            	if(usertype.equals("BUTLER")) {
            		String hkid=(String) jsStr.get("hkid");
            		String transactionid=(String) jsStr.get("transactionid");
            		int balance;
            		List<CourseCatagoryCount> balances=this.courseCatagoryMapper.getBalance(hkid);
            		this.courseCatagoryMapper.billOrder(money,orderNum,threeNum,businesstype,hkid,transactionid);
					 balance=balances.get(0).getCount()+Integer.parseInt(money);
            		this.courseCatagoryMapper.updateHouseBalance(hkid,balance);
            	}else {
            		String osid=(String) jsStr.get("osid");
            		String emid=(String) jsStr.get("emid");
            		this.courseCatagoryMapper.billEmployer(emid,orderNum,threeNum,businesstype,money,osid);
            		// V2版本新增查询卡功能 start
            		if(usertype.equals("V2_购买查询卡")) {
            			// 更新viewcard表
            			String transactionid=(String) jsStr.get("transactionid");
            			ViewCard viewcard=this.employerMapper.getViewCard(emid, osid);
            			if(viewcard!=null) {
            				// transactionid当数量用
            				this.employerMapper.addViewCard(emid, osid,transactionid,transactionid);
            			}else {
            				this.employerMapper.updateViewCard(emid, osid,transactionid);
            			}
            		}
            		// V2版本新增查询卡功能 end
            	}
            	/**此处添加自己的业务逻辑代码end**/

                //通知微信服务器已经支付成功
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
            }
        }else{
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
        System.out.println(resXml);
        System.out.println("微信支付回调数据结束");

        BufferedOutputStream out = new BufferedOutputStream(
                response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }
    
    @RequestMapping(value="wx/consume",method=RequestMethod.POST)
    public ResultMap consume(HttpServletRequest request) {
    	 String ccid="";
	   	 String cid="";
	     int balance;
   		 String orderNo = StringUtils.getRandomCode();
	   	 String money=request.getParameter("money");
	   	 String transactionid=request.getParameter("transactionid");
	   	 String businesstype=request.getParameter("businesstype");
	   	 cid=request.getParameter("cid");
	   	 ccid=request.getParameter("ccid");
	   	 String hkid="";
	   	if(money==null || transactionid==null || businesstype==null) {
	   		return new ResultMap().fail("400").message("缺少参数").data("");
	   	}
	   	 HttpServletRequest httpRequest = (HttpServletRequest) request;
	   	 String token = httpRequest.getHeader(this.tokenHeader);
	   	 String username = this.tokenUtils.getUsernameFromToken(token);
	   		if(cid==null && ccid==null) {
	   			return new ResultMap().fail("400").message("缺少参数").data("");
	   		 }
	   		 List<GetHkid> getHkids=this.courseCatagoryMapper.getHkid(username);
	   		 hkid=getHkids.get(0).getHkid();
	   		 List<CourseCatagoryCount> balances=this.courseCatagoryMapper.getBalance(hkid);
    		 balance=balances.get(0).getCount()-Integer.parseInt(money);
    		 this.courseCatagoryMapper.updateHouseBalance(hkid,balance);
    		 if(businesstype.equals("购买课程")){
    	 			List<CourseCatagoryCount> orderCourse=this.courseCatagoryMapper.getOrderCourse(hkid,cid);
    	 			if(orderCourse.get(0).getCount()!=0) {
    	 				this.courseCatagoryMapper.deteleOrderCourse(hkid,cid);
    	 			}
    	 			this.courseCatagoryMapper.addOrder(hkid,cid);
	 			}else if(businesstype.equals("购买模块")){
	 				List<GetHkid> cids=this.courseCatagoryMapper.getCid(ccid);
	 				for(GetHkid value : cids) {
    	 				List<CourseCatagoryCount> orderCourse=this.courseCatagoryMapper.getOrderCourse(hkid,value.getHkid());
        	 			if(orderCourse.get(0).getCount()!=0) {
        	 				this.courseCatagoryMapper.deteleOrderCourse(hkid,value.getHkid());
        	 			}
    	 					this.courseCatagoryMapper.addOrder(hkid,value.getHkid());
	 				}	
	 			}
    		 int results=this.courseCatagoryMapper.addHouseKeep(hkid,orderNo,money,transactionid,businesstype);
    		 if(results==1) {
 				return new ResultMap().success().message("购买成功");
			 }else {
 				return new ResultMap().fail("1").message("购买失败");
 			}	   	
	 }
    
}
