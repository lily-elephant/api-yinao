package yinao.qualityLife.utils.wxpay;

/**
 * @Description:
 * @Date: 2018/4/8
 * @Author: wcf
 */
public class WxPayConfig {
    //小程序appid
	//  public static final String appid = "";
    //微信支付的商户id
   // public static final String mch_id = "";
    //微信支付的商户密钥
    public static final String key = "yinaoxiaochengxu1234567890123456";
    //支付成功后的服务器回调url
    public static final String notify_url = "http://47.94.103.215:8888/wxNotify";
    //V2版本支付成功后的服务器回调url
    public static final String v2_notify_url = "https://api.ukbg.cn/qualityLife/wx/wxNotify";
    //签名方式
    public static final String SIGNTYPE = "MD5";
    //交易类型
    public static final String TRADETYPE = "JSAPI";
    //微信统一下单接口地址
    public static final String pay_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
}
