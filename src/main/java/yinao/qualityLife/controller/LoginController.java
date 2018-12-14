package yinao.qualityLife.controller;

import yinao.qualityLife.dao.UserMapper;
import yinao.qualityLife.dao.adminUserMapper;
import yinao.qualityLife.model.LoginDetail;
import yinao.qualityLife.model.ResultMap;
import yinao.qualityLife.model.TokenDetail;
import yinao.qualityLife.model.domain.AdminUser;
import yinao.qualityLife.model.domain.User;
import yinao.qualityLife.model.vo.Data;
import yinao.qualityLife.model.vo.RequestLoginUser;
import yinao.qualityLife.model.vo.RequestLoginUser01;
import yinao.qualityLife.model.vo.RequestLoginUser02;
import yinao.qualityLife.service.LoginService;
import yinao.qualityLife.utils.Md5Utils;
import yinao.qualityLife.utils.TokenUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class LoginController {

    private final LoginService loginService;
    private final UserMapper userMapper;
    private final TokenUtils tokenUtils;
    private final adminUserMapper adminUsermapper ;
    
   
    @Value("${token.header}")
    private String tokenHeader;
    @Value("${KEY}")
    private String KEY;

    @Autowired
    public LoginController(LoginService loginService , UserMapper userMapper ,TokenUtils tokenUtils , adminUserMapper adminUsermapper) {
        this.loginService = loginService;
        this.userMapper = userMapper ; 
        this.tokenUtils = tokenUtils ; 
        this.adminUsermapper = adminUsermapper ; 
    }

    @RequestMapping(value = "/user/loginbypassword",method = RequestMethod.POST)
    public ResultMap login(@Valid RequestLoginUser requestLoginUser, BindingResult bindingResult){
        // 检查有没有输入用户名密码和格式对不对
        if (bindingResult.hasErrors()){
            return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
        }

        LoginDetail loginDetail = loginService.getLoginDetail(requestLoginUser.getUsername() , requestLoginUser.getUsertype());
        
        ResultMap ifLoginFail = checkAccount(requestLoginUser, loginDetail);
        if (ifLoginFail != null){
            return ifLoginFail;
        }
        if(!"ADMIN".equals(requestLoginUser.getUsertype())) {
            return new ResultMap().success().message("success").data(new Data().addObj(tokenHeader, loginService.generateToken((TokenDetail) loginDetail, requestLoginUser.getUsertype())));
        }else {
        	String token = loginService.generateToken((TokenDetail) loginDetail, requestLoginUser.getUsertype()) ;
        	//System.out.println(token);
        	
        	List<AdminUser> adminUsers=this.userMapper.getAdminUser(requestLoginUser.getUsername());
        	//System.out.println(adminUsers.toString() );
        	if(adminUsers.size() == 1) {
        		adminUsers.get(0).setToken(token);
        		return new ResultMap().success().message("success").data(adminUsers);
        	}else {
        		return new ResultMap().fail("434").message("账号异常") ;
        	}
    		
        }
    }

    //登录验证
    private ResultMap checkAccount(RequestLoginUser requestLoginUser, LoginDetail loginDetail){
        if (loginDetail == null){
            return new ResultMap().fail("434").message("账号不存在！").data("");
        }else {
            if (loginDetail.enable() == false){
                return new ResultMap().fail("452").message("账号在黑名单中").data("");
            }
            Md5PasswordEncoder md5= new Md5PasswordEncoder();
        	md5.setEncodeHashAsBase64(false);
            String password= md5.encodePassword(requestLoginUser.getPassword(), requestLoginUser.getUsername()); 
            //System.out.println(loginDetail.getPassword());
            //System.out.println(password);
            if (!loginDetail.getPassword().equals(password)){
                return new ResultMap().fail("438").message("密码错误！").data("");
            }
        }
        return null;
    }
    
    @RequestMapping(value = "/user/loginbyverifycode",method = RequestMethod.POST)
    public ResultMap login(@Valid RequestLoginUser01 requestLoginUser01, BindingResult bindingResult,HttpServletRequest request){
        // 检查有没有输入用户名密码和格式对不对
        if (bindingResult.hasErrors()){
            return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
        }
        
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar c = Calendar.getInstance();
        String currentTime = sf.format(c.getTime());
        
        String requestHash = request.getParameter("hash").toString();
        String tamp = request.getParameter("tamp").toString();
        String msgNum = request.getParameter("verifycode").toString();
        String hash = Md5Utils.getMd5(KEY + "@" + tamp + "@" + msgNum);
        
        if (tamp.compareTo(currentTime) > 0) {
        	if (hash.equalsIgnoreCase(requestHash)){
        	//校验成功
        		LoginDetail loginDetail = loginService.getLoginDetail(requestLoginUser01.getUsername() , requestLoginUser01.getUsertype());
                ResultMap ifLoginFail = checkAccount02(requestLoginUser01, loginDetail);
                if (ifLoginFail != null){
                    return ifLoginFail;
                }
                return new ResultMap().success().message("success").data(new Data().addObj(tokenHeader, tokenUtils.generateToken(requestLoginUser01.getUsername() ,requestLoginUser01.getUsertype()))); 
        	}else {
        	//验证码不正确，校验失败
        		return new ResultMap().fail("403").message("验证码错误");
        	}
    	} else {
        	// 超时
            	return new ResultMap().fail("402").message("超时"); 
    	} 
    }
    
  //短信登录验证
    private ResultMap checkAccount02(RequestLoginUser01 requestLoginUser01, LoginDetail loginDetail){
        if (loginDetail == null){
        	 Md5PasswordEncoder md5= new Md5PasswordEncoder();
         	md5.setEncodeHashAsBase64(false);
             String password= md5.encodePassword("", requestLoginUser01.getUsername());
        	if(requestLoginUser01.getUsertype().equals("EMPLOYER")) {
            	//将数据写入数据库 并生成token
                userMapper.registerLoginEmployer(requestLoginUser01.getUsername(),password ) ; 
               // return new ResultMap().success().message("success").data(new Data().addObj(tokenHeader, tokenUtils.generateToken(requestLoginUser01.getUsername() ,requestLoginUser01.getUsertype()))); 
        	}else {
        		//将数据写入数据库 并生成token
                userMapper.registerLoginHousekeeper(requestLoginUser01.getUsername(),password) ; 
               // return new ResultMap().success().message("success").data(new Data().addObj(tokenHeader, tokenUtils.generateToken(requestLoginUser01.getUsername() ,requestLoginUser01.getUsertype()))); 
        	}
        }else {
            if (loginDetail.enable() == false){
                return new ResultMap().fail("452").message("账号在黑名单中").data("");
            }
        }
        return null;
    }
    
    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    public ResultMap register(@Valid RequestLoginUser02 requestLoginUser02, BindingResult bindingResult,HttpServletRequest request){
        // 检查有没有输入用户名密码和格式对不对
        if (bindingResult.hasErrors()){
            return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
        }
        //根据  usertype BUTLER（家政），EMPLOYER（ 雇主）  判断是哪个角色的注册
        //数据库查询 是否存在相关用户
        
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar c = Calendar.getInstance();
        String currentTime = sf.format(c.getTime());
        
        
        String requestHash = request.getParameter("hash").toString();
        String tamp = request.getParameter("tamp").toString();
        String msgNum = request.getParameter("verifycode").toString();
        String hash = Md5Utils.getMd5(KEY + "@" + tamp + "@" + msgNum);
        if (tamp.compareTo(currentTime) > 0) {
	        if (hash.equalsIgnoreCase(requestHash)){
	        //校验成功
	        	//数据库查询 是否存在相关用户
	            LoginDetail loginDetail = loginService.getLoginDetail(requestLoginUser02.getUsername(), requestLoginUser02.getUsertype());
	            ResultMap ifLoginFail = checkAccount03(requestLoginUser02,loginDetail);
	            if (ifLoginFail != null){
	                return ifLoginFail;
	            }
	            Md5PasswordEncoder md5= new Md5PasswordEncoder();
            	md5.setEncodeHashAsBase64(false);
                String password= md5.encodePassword(requestLoginUser02.getPassword(), requestLoginUser02.getUsername()); 
                //System.out.println(password);
                if(requestLoginUser02.getRegistertype().equals("0")){
                	if(requestLoginUser02.getUsertype().equals("EMPLOYER")) {
    	            	//将数据写入数据库 并生成token
    	                userMapper.registerEmployer(requestLoginUser02.getUsername() , password , requestLoginUser02.getName()) ; 
    	                return new ResultMap().success().message("success").data(new Data().addObj(tokenHeader, tokenUtils.generateToken(requestLoginUser02.getUsername() ,requestLoginUser02.getUsertype()))); 
    	        	}else {
    	        		//将数据写入数据库 并生成token
    	                userMapper.registerHousekeeper(requestLoginUser02.getUsername() , password ,requestLoginUser02.getName() ) ; 
    	                return new ResultMap().success().message("success").data(new Data().addObj(tokenHeader, tokenUtils.generateToken(requestLoginUser02.getUsername() ,requestLoginUser02.getUsertype()))); 
    	        	}
                }else {
                	if(requestLoginUser02.getUsertype().equals("EMPLOYER")) {
    	            	//将数据写入数据库 并生成token
    	                userMapper.updateEmployer(requestLoginUser02.getUsername() , password) ; 
    	                return new ResultMap().success().message("success").data(new Data().addObj(tokenHeader, tokenUtils.generateToken(requestLoginUser02.getUsername() ,requestLoginUser02.getUsertype()))); 
    	        	}else {
    	        		//将数据写入数据库 并生成token
    	                userMapper.updateHousekeeper(requestLoginUser02.getUsername() , password) ; 
    	                return new ResultMap().success().message("success").data(new Data().addObj(tokenHeader, tokenUtils.generateToken(requestLoginUser02.getUsername() ,requestLoginUser02.getUsertype()))); 
    	        	}
                }
	        }else {
	        	//验证码不正确，校验失败
	        	return new ResultMap().fail("403").message("验证码错误"); 
	        }
        } else {
        // 超时
        	return new ResultMap().fail("402").message("超时"); 
        }
     }
    
    //注册验证
    private ResultMap checkAccount03(RequestLoginUser02 requestLoginUser02,LoginDetail loginDetail){
    	if(requestLoginUser02.getRegistertype().equals("0")) {
    		if (loginDetail != null){
            	return new ResultMap().fail("434").message("改账号已经被注册！").data("");
            }
    	}
        return null;
    }

}
