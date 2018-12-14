package yinao.qualityLife.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.catalina.core.ApplicationPart;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.ibatis.annotations.Param;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import yinao.qualityLife.dao.HouseKeeperMapper;
import yinao.qualityLife.dao.UserMapper;
import yinao.qualityLife.model.ResultMap;
import yinao.qualityLife.model.domain.AdminCertificate;
import yinao.qualityLife.model.domain.AnswerExam;
import yinao.qualityLife.model.domain.AppointmentMoney;
import yinao.qualityLife.model.domain.AuthData;
import yinao.qualityLife.model.domain.Balance;
import yinao.qualityLife.model.domain.Ben;
import yinao.qualityLife.model.domain.Bill;
import yinao.qualityLife.model.domain.Certificate;
import yinao.qualityLife.model.domain.ChargePropertices;
import yinao.qualityLife.model.domain.Comment;
import yinao.qualityLife.model.domain.Emid;
import yinao.qualityLife.model.domain.EmployerComment;
import yinao.qualityLife.model.domain.EmployerRecord;
import yinao.qualityLife.model.domain.Feedback;
import yinao.qualityLife.model.domain.Hkid;
import yinao.qualityLife.model.domain.HouseKeeper;
import yinao.qualityLife.model.domain.HousekeeperCertificate;
import yinao.qualityLife.model.domain.OfflineRecord;
import yinao.qualityLife.model.domain.OfflineTraining;
import yinao.qualityLife.model.domain.OfflineTrainingNotes;
import yinao.qualityLife.model.domain.OfflineTrainingRecord;
import yinao.qualityLife.model.domain.Order;
import yinao.qualityLife.model.domain.PersonalityNine;
import yinao.qualityLife.model.domain.PersonalityTest;
import yinao.qualityLife.model.domain.PersonalityTestDes;
import yinao.qualityLife.model.domain.ServiceRecord;
import yinao.qualityLife.model.domain.SixteenOption;
import yinao.qualityLife.model.domain.SixteenTest;
import yinao.qualityLife.model.domain.StudyOrder;
import yinao.qualityLife.model.domain.SysInfo;
import yinao.qualityLife.model.domain.User;
import yinao.qualityLife.utils.PageUtils;
import yinao.qualityLife.utils.TokenUtils;

@RestController
public class MeController {
	
	private final TokenUtils tokenUtils;
	private final UserMapper userMapper;
	private final HouseKeeperMapper housekeeperMapper ;
	
	@Value("${token.header}")
    private String tokenHeader;
	
	@Autowired
    public MeController(TokenUtils tokenUtils , UserMapper userMapper , HouseKeeperMapper housekeeperMapper ) {
        this.tokenUtils = tokenUtils ; 
        this.userMapper = userMapper ; 
        this.housekeeperMapper = housekeeperMapper ; 
    }

	@RequestMapping(value = "auth",method = RequestMethod.POST)
    public ResultMap authMe01(HttpServletRequest request){
		String token = request.getHeader(tokenHeader) ;
		String username = tokenUtils.getUsernameFromToken(token) ; 
		String usertype = tokenUtils.getUserTypeFromToken(token); 

		User user ; 
		if(usertype.equals("EMPLOYER")) {
			user = userMapper.loginEmployer(username) ;
		}else if(usertype.equals("ADMIN")){
			user = userMapper.loginAdmin(username) ;
		}else {
			user = userMapper.loginHousekeeper(username) ;  
		}
        return new ResultMap().success().message("success").data(user);	
    }
	
	@RequestMapping(value = "admin/userFeedback",method = RequestMethod.POST)
    public ResultMap userFeedback(HttpServletRequest request){
		if(request.getParameter("cid") == null) {
			return new ResultMap().fail("400").message("缺少参数cid");	
		}
		if(request.getParameter("reply") == null || request.getParameter("reply") == "") {
			return new ResultMap().fail("400").message("缺少参数reply");	
		}
				
		String cid = request.getParameter("cid") ;
		String reply = request.getParameter("reply") ;
		
		
		try {
			int count = userMapper.userFeedback(cid, reply);
			if(count == 1 ) {
				return new ResultMap().success().message("插入成功");		
			}else {
				return new ResultMap().fail("435").message("插入失败");		
			}
		} catch (Exception e) {
			return new ResultMap().fail("436").message("插入失败");		
		}
    }
	
	@RequestMapping(value = "submitFeedback",method = RequestMethod.POST)
    public ResultMap submitFeedback(HttpServletRequest request){
		if(request.getParameter("comment") == null || request.getParameter("comment") == "") {
			return new ResultMap().fail("401").message("缺少参数comment");	
		}
		String token = request.getHeader(tokenHeader) ;
		String username = tokenUtils.getUsernameFromToken(token) ; 
		String clienttype = tokenUtils.getUserTypeFromToken(token); 
		String userid ;
		if(clienttype.equals("EMPLOYER")) {
			Emid emidentity = userMapper.getEmidByUsername(username) ;
			userid = emidentity.getEmid() ; 
		}else {
			Hkid hkidentity = userMapper.getHkidByUsername(username) ;
			userid = hkidentity.getHkid() ; 
		}
		
		
		String comment = request.getParameter("comment") ;
		try {
			int count = userMapper.submitFeedback(userid, clienttype, comment);
			if(count == 1 ) {
				return new ResultMap().success().message("插入成功");		
			}else {
				return new ResultMap().fail("435").message("插入失败");		
			}
		} catch (Exception e) {
			return new ResultMap().fail("436").message("插入失败");		
		}
    }
	
	@RequestMapping(value = "myFeedback",method = RequestMethod.POST)
    public ResultMap myFeedback(HttpServletRequest request){
		String token = request.getHeader(tokenHeader) ;
		String username = tokenUtils.getUsernameFromToken(token) ; 
		String clienttype = tokenUtils.getUserTypeFromToken(token); 
		String userid ;
		if(clienttype.equals("EMPLOYER")) {
			Emid emidentity = userMapper.getEmidByUsername(username) ;
			userid = emidentity.getEmid() ; 
		}else {
			Hkid hkidentity = userMapper.getHkidByUsername(username) ;
			userid = hkidentity.getHkid() ; 
		}		
		List<Feedback> list = userMapper.myFeedback(userid, clienttype);		
		return new ResultMap().success().data(list);	
		
    }
	
	@RequestMapping(value = "adminFeedback",method = RequestMethod.POST)
    public ResultMap adminFeedback(HttpServletRequest request){	
		if(request.getParameter("pageindex") == null) {
			return new ResultMap().fail("400").message("缺少参数pageindex");
		}
		if(request.getParameter("pagecount") == null) {
			return new ResultMap().fail("400").message("缺少参数pagecount");
		}
		String isanswer = request.getParameter("isanswer") ;
		int pageindex = Integer.parseInt(request.getParameter("pageindex")); 
		int pagecount = Integer.parseInt(request.getParameter("pagecount")); 
		List<Feedback> list = userMapper.adminFeedback(isanswer);	
		for(Feedback entity : list ) {
			String usertype = entity.getClienttype() ;
			String userid = entity.getUserid() ; 
			if("EMPLOYER".equals(usertype)) {
				List<Ben> list01 = userMapper.getEMInfoByUserid(userid) ;
				entity.setUsername(list01.get(0).getUsername());
				entity.setName(list01.get(0).getName());
			}else if ("BUTLER".equals(usertype)){
				List<Ben> list02 = userMapper.getHKInfoByUserid(userid) ;
				entity.setUsername(list02.get(0).getUsername());
				entity.setName(list02.get(0).getName());
			}else {
				entity.setUsername("");
				entity.setName("");
			}
			
		}
		List<Feedback> list01  = PageUtils.getList(list , pageindex , pagecount ) ; 
		return new ResultMap().success().count(list.size()).data(list01);	
    }

	@RequestMapping(value = "noticeIsRead",method = RequestMethod.POST)
    public ResultMap noticeIsRead(HttpServletRequest request){
		String token = request.getHeader(tokenHeader) ;
		String username = tokenUtils.getUsernameFromToken(token) ; 
		String clienttype = tokenUtils.getUserTypeFromToken(token); 
		String userid ;
		if(clienttype.equals("EMPLOYER")) {
			Emid emidentity = userMapper.getEmidByUsername(username) ;
			userid = emidentity.getEmid() ; 
		}else {
			Hkid hkidentity = userMapper.getHkidByUsername(username) ;
			userid = hkidentity.getHkid() ; 
		}
		String type = request.getParameter("type") ;
		String id = request.getParameter("id") ;
		if("sys".equals(type)) {
			try {
				int count = userMapper.setSysInfoIsRead(userid, id);
				if(count == 1 ) {
					return new ResultMap().success().message("插入成功");		
				}else {
					return new ResultMap().fail("435").message("插入失败");		
				}
			} catch (Exception e) {
				return new ResultMap().fail("436").message("插入失败");		
			}
		}else if("user".equals(type)){
			try {
				int count = userMapper.setUserInfoIsRead(userid, clienttype, id);
				if(count == 1 ) {
					return new ResultMap().success().message("插入成功");		
				}else {
					return new ResultMap().fail("435").message("插入失败");		
				}
			} catch (Exception e) {
				return new ResultMap().fail("436").message("插入失败");		
			}
		}
		return new ResultMap().fail("401").message("type参数错误");	
		
	
    }
	
	@RequestMapping(value = "myNotice",method = RequestMethod.POST)
    public ResultMap myNotice(HttpServletRequest request){
		String token = request.getHeader(tokenHeader) ;
		String username = tokenUtils.getUsernameFromToken(token) ; 
		String clienttype = tokenUtils.getUserTypeFromToken(token); 
		String userid ;
		if(clienttype.equals("EMPLOYER")) {
			Emid emidentity = userMapper.getEmidByUsername(username) ;
			userid = emidentity.getEmid() ; 
		}else {
			Hkid hkidentity = userMapper.getHkidByUsername(username) ;
			userid = hkidentity.getHkid() ; 
		}		
		List<SysInfo> list_sys = userMapper.getSysInfoList(clienttype);	
		for(SysInfo sysInfo : list_sys) {
			sysInfo.setIsread("0");
			sysInfo.setType("sys");
			List<SysInfo> ben = userMapper.getSysCount(sysInfo.getId(), userid) ;
			if(ben.size() != 0) {
				sysInfo.setIsread("1");
			}
		}
		List<SysInfo> list_user = userMapper.getNoticeInfoList(userid , clienttype);
		for(SysInfo sysInfo : list_user) {
			sysInfo.setType("user");
		}
		list_sys.addAll(list_user) ;
		Collections.sort(list_sys, new Comparator<SysInfo>() {
            @Override
            public int compare(SysInfo o1, SysInfo o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date dt1 = format.parse(o1.getCreate_time());
                    Date dt2 = format.parse(o2.getCreate_time());
                    if (dt1.getTime() > dt2.getTime()) {
                        return -1;
                    } else if (dt1.getTime() < dt2.getTime()) {
                        return 1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
		return new ResultMap().success().data(list_sys);	
    }
	
	
	@RequestMapping(value = "myOrder",method = RequestMethod.POST)
    public ResultMap myOrder(@Valid Order params, BindingResult bindingResult , HttpServletRequest request){
		// 检查用户输入的参数是否正确
        if (bindingResult.hasErrors()){
            return new ResultMap().fail("400").message("缺少参数或者参数格式不对");
        }
		String token = request.getHeader(tokenHeader) ;
		String username = tokenUtils.getUsernameFromToken(token) ; 
		String clienttype = tokenUtils.getUserTypeFromToken(token); 
		String userid ;
		if(clienttype.equals("EMPLOYER")) {
			Emid emidentity = userMapper.getEmidByUsername(username) ;
			userid = emidentity.getEmid() ; 
		}else {
			return new ResultMap().fail("401").message("此接口为employer端接口");	
//			Hkid hkidentity = userMapper.getHkidByUsername(username) ;
//			userid = hkidentity.getHkid() ; 
		}	
		
		List<Order> list = userMapper.getOrderList(userid, params.getPayflag());		
		return new ResultMap().success().data(list);	
		
    }
	
	@RequestMapping(value = "getCertificate",method = RequestMethod.POST)
    public ResultMap getCertificate(){
		List<Certificate> list = userMapper.getCertificate();		
		return new ResultMap().success().data(list);	
    }
	
	@RequestMapping(value = "getAuthData",method = RequestMethod.POST)
    public ResultMap getAuthData(HttpServletRequest request){
		if (request.getParameter("hkid") == null){
            return new ResultMap().fail("400").message("缺少参数hkid");
        }
		String hkid = request.getParameter("hkid") ;
		List<AuthData> list = userMapper.getAuthData(hkid);		
		return new ResultMap().success().data(list);	
    }
	
	@RequestMapping(value = "admin/getCertificate",method = RequestMethod.POST)
    public ResultMap getAdminCertificate(HttpServletRequest request){
		if(request.getParameter("hkid")!=null) {
			String hkid = request.getParameter("hkid") ;
			List<AdminCertificate> lists = new ArrayList<AdminCertificate>() ;
			List<Certificate> list = userMapper.getCertificate();	
			for(Certificate entity : list) {
				String cid = entity.getCid() ; 
				String name = entity.getName() ; 
				String picture = entity.getPicture() ; 
				String state = entity.getState() ; 
				String create_time = entity.getCreate_time() ; 
				String isHas = "false" ; 
				List<HousekeeperCertificate> list_ben = this.userMapper.admincertificatelist(hkid , cid ) ;
				if(list_ben.size() != 0) {
					picture = list_ben.get(0).getPicture() ; 
					create_time = list_ben.get(0).getCreate_time() ;  
					isHas = "true" ;
				}
				AdminCertificate domain = new AdminCertificate() ;
				domain.setCid(cid);
				domain.setName(name);
				domain.setPicture(picture);
				domain.setState(state);
				domain.setCreate_time(create_time);
				domain.setHkid(hkid);
				domain.setIsHas(isHas);
				lists.add(domain) ; 
			}
			return new ResultMap().success().data(lists);	
		}else {
			List<AdminCertificate> lists = new ArrayList<AdminCertificate>() ;
			List<Certificate> list = userMapper.getCertificate();	
			for(Certificate entity : list) {
				String cid = entity.getCid() ; 
				String name = entity.getName() ; 
				String picture = entity.getPicture() ; 
				String state = entity.getState() ; 
				String create_time = entity.getCreate_time() ; 
				String isHas = "false" ; 
				AdminCertificate domain = new AdminCertificate() ;
				domain.setCid(cid);
				domain.setName(name);
				domain.setPicture(picture);
				domain.setState(state);
				domain.setCreate_time(create_time);
				domain.setHkid("");
				domain.setIsHas(isHas);
				lists.add(domain) ; 
			}
			return new ResultMap().success().data(lists);	
		}
    }
	
	
	
	@RequestMapping(value = "getHKOrderList",method = RequestMethod.POST)
    public ResultMap getHKOrderList(@Valid StudyOrder params, BindingResult bindingResult , HttpServletRequest request){
		// 检查用户输入的参数是否正确
        if (bindingResult.hasErrors()){
            return new ResultMap().fail("400").message("缺少参数或者参数格式不对");
        }
		String token = request.getHeader(tokenHeader) ;
		String username = tokenUtils.getUsernameFromToken(token) ; 
		String clienttype = tokenUtils.getUserTypeFromToken(token); 
		String userid ;
		if(clienttype.equals("EMPLOYER")) {
			return new ResultMap().fail("401").message("此接口为housekeeper端接口");	
//			Emid emidentity = userMapper.getEmidByUsername(username) ;
//			userid = emidentity.getEmid() ; 
		}else {			
			Hkid hkidentity = userMapper.getHkidByUsername(username) ;
			userid = hkidentity.getHkid() ; 
		}
		if(request.getParameter("pageindex") == null) {
			return new ResultMap().fail("400").message("缺少参数pageindex");
		}
		if(request.getParameter("pagecount") == null) {
			return new ResultMap().fail("400").message("缺少参数pagecount");
		}
		int pageindex = Integer.parseInt(request.getParameter("pageindex")); //
		int pagecount = Integer.parseInt(request.getParameter("pagecount")); //
		if("1".equals(params.getIsbuy())) {
			params.setIsbuy("0");
		}else if("0".equals(params.getIsbuy())){
			params.setIsbuy("1");
		}
		List<StudyOrder> list = userMapper.getHKOrderList(userid, params.getIsbuy());	
		List<StudyOrder> list01  = PageUtils.getList(list , pageindex , pagecount ) ; 
		return new ResultMap().success().count(list.size()).data(list01);	
    }
	
	
	@RequestMapping(value = "admin/getHKOrderList",method = RequestMethod.POST)
    public ResultMap getadminHKOrderList(@Valid StudyOrder params, BindingResult bindingResult , HttpServletRequest request){
//        if (request.getParameter("isbuy") == null){
//            return new ResultMap().fail("400").message("缺少参数isbuy");
//        }
        if(request.getParameter("usertype") == null) {
        	 return new ResultMap().fail("400").message("缺少参数usertype");
        }
        if(request.getParameter("pageindex") == null) {
			return new ResultMap().fail("400").message("缺少参数pageindex");
		}
		if(request.getParameter("pagecount") == null) {
			return new ResultMap().fail("400").message("缺少参数pagecount");
		}
		if( !"BUTLER".equals(request.getParameter("usertype") )) {
			return new ResultMap().fail("400").message("参数usertype不合法");
		}
        //String username = "" ;
        if(request.getParameter("username") == null ) {
        	//username = request.getParameter("username") ; 
//    		Hkid hkidentity = userMapper.getHkidByUsername(username) ;
//			if(hkidentity != null) {
//				userid = hkidentity.getHkid() ; 
//			}
        	String userid = "";
    		int pageindex = Integer.parseInt(request.getParameter("pageindex")); //
    		int pagecount = Integer.parseInt(request.getParameter("pagecount")); //
    		List<StudyOrder> list = userMapper.getHKOrderList(userid, params.getIsbuy());	
    		List<StudyOrder> list01  = PageUtils.getList(list , pageindex , pagecount ) ; 
    		return new ResultMap().success().count(list.size()).data(list01);	
        }else {
        	String username = request.getParameter("username") ; 
    		Hkid hkidentity = userMapper.getHkidByUsername(username) ;
//			if(hkidentity = null) {
//				userid = hkidentity.getHkid() ; 
//			}
			if(hkidentity == null) {
				return new ResultMap().success().count(0).data("");	
			}else {
				String userid = hkidentity.getHkid() ; 
				int pageindex = Integer.parseInt(request.getParameter("pageindex")); //
	    		int pagecount = Integer.parseInt(request.getParameter("pagecount")); //
	    		List<StudyOrder> list = userMapper.getHKOrderList(userid, params.getIsbuy());	
	    		List<StudyOrder> list01  = PageUtils.getList(list , pageindex , pagecount ) ; 
	    		return new ResultMap().success().count(list.size()).data(list01);	
			}
        }
      	
    }
	
//	@RequestMapping(value = "getRecord",method = RequestMethod.POST)
//    public ResultMap getRecord(@Valid EmployerRecord params, BindingResult bindingResult , HttpServletRequest request){
//		// 检查用户输入的参数是否正确
//        if (bindingResult.hasErrors()){
//            return new ResultMap().fail("400").message("缺少参数或者参数格式不对");
//        }
//		String token = request.getHeader(tokenHeader) ;
//		String username = tokenUtils.getUsernameFromToken(token) ; 
//		String clienttype = tokenUtils.getUserTypeFromToken(token); 
//		String emid ="";
//		String hkid =""; 
//		if(clienttype.equals("EMPLOYER")) {
//			Emid emidentity = userMapper.getEmidByUsername(username) ;
//			emid = emidentity.getEmid() ; 
//		}else {
//			Hkid hkidentity = userMapper.getHkidByUsername(username) ;
//			hkid = hkidentity.getHkid() ; 
//		}	
//		List<EmployerRecord> list = userMapper.getEmployerRecord(emid , hkid);	
//		for(EmployerRecord entity : list) {
//			List<EmployerComment> list01 = this.housekeeperMapper.housekeepercomment(entity.getHkid() , entity.getEmid()) ;
//			if(list01.size() != 0) {
//				entity.setScore(list01.get(0).getScore());
//				entity.setCommenttime(list01.get(0).getCreate_time());
//				entity.setContent(list01.get(0).getContent());
//				entity.setCid(list01.get(0).getCid());
//			}
//		}
//		return new ResultMap().success().data(list);	
//    }
	
	@RequestMapping(value = "getRecord",method = RequestMethod.POST)
    public ResultMap getRecord(@Valid ServiceRecord params, BindingResult bindingResult , HttpServletRequest request){
		
		String token = request.getHeader(tokenHeader) ;
		String username = tokenUtils.getUsernameFromToken(token) ; 
		String clienttype = tokenUtils.getUserTypeFromToken(token); 
		String emid ="";
		String hkid =""; 
		if(clienttype.equals("EMPLOYER")) {
			Emid emidentity = userMapper.getEmidByUsername(username) ;
			emid = emidentity.getEmid() ; 
		}else {
			Hkid hkidentity = userMapper.getHkidByUsername(username) ;
			hkid = hkidentity.getHkid() ; 
		}
		
		
		List<ServiceRecord> list = userMapper.getServiceRecord(emid , hkid);
		
//		for(EmployerRecord entity : list) {
//			List<EmployerComment> list01 = this.housekeeperMapper.housekeepercomment(entity.getHkid() , entity.getEmid()) ;
//			if(list01.size() != 0) {
//				entity.setScore(list01.get(0).getScore());
//				entity.setCommenttime(list01.get(0).getCreate_time());
//				entity.setContent(list01.get(0).getContent());
//				entity.setCid(list01.get(0).getCid());
//			}
//		}
		return new ResultMap().success().data(list);	
    }
	
	// 后台 服务订单
	@RequestMapping(value = "admin/getRecord",method = RequestMethod.POST)
    public ResultMap getAdminRecord(@Valid EmployerRecord params, BindingResult bindingResult , HttpServletRequest request){ 
		if(request.getParameter("pageindex") == null) {
			return new ResultMap().fail("400").message("缺少参数pageindex");
		}
		if(request.getParameter("pagecount") == null) {
			return new ResultMap().fail("400").message("缺少参数pagecount");
		}
//		String hkid =""; 
//		String emid =""; 
//		if(request.getParameter("username") != null && !"".equals(request.getParameter("username"))) {
//			String username = request.getParameter("username") ;
//			Emid emidentity = userMapper.getEmidByUsername(username) ;
//			if(emidentity == null ) {
//				return new ResultMap().fail("401").message("不存在当前username");	
//			}
//			emid = emidentity.getEmid() ; 
//		}
		List<EmployerRecord> list = userMapper.getAdminEmployerRecord(request.getParameter("username"), params.getEmname() , params.getHkphone() , params.getHkname() );	
		for(EmployerRecord entity : list) {
			List<EmployerComment> list01 = this.housekeeperMapper.housekeepercomment(entity.getHkid() , entity.getEmid()) ;
			if(list01.size() != 0) {
				entity.setScore(list01.get(0).getScore());
				entity.setCommenttime(list01.get(0).getCreate_time());
				entity.setContent(list01.get(0).getContent());
				entity.setCid(list01.get(0).getCid());
			}
		}
		int pageindex = Integer.parseInt(request.getParameter("pageindex")); 
		int pagecount = Integer.parseInt(request.getParameter("pagecount")); 
		List<EmployerRecord> list01  = PageUtils.getList(list , pageindex , pagecount ) ; 
		return new ResultMap().success().count(list.size()).data(list01);	
    }
	
	
	
	@RequestMapping(value = "submitComment",method = RequestMethod.POST)
    public ResultMap submitComment(@Valid Comment params, BindingResult bindingResult , HttpServletRequest request){
		// 检查用户输入的参数是否正确
        if (bindingResult.hasErrors()){
            return new ResultMap().fail("400").message("缺少参数或者参数格式不对");
        }
		String token = request.getHeader(tokenHeader) ;
		String username = tokenUtils.getUsernameFromToken(token) ; 
		String clienttype = tokenUtils.getUserTypeFromToken(token); 
		String emid ;
		if(clienttype.equals("EMPLOYER")) {
			Emid emidentity = userMapper.getEmidByUsername(username) ;
			emid = emidentity.getEmid() ; 
		}else {
			return new ResultMap().fail("401").message("此接口为employer端接口");	
		}
		
		List<Comment> list = userMapper.getCommentList(emid , params.getHkid()) ;
		//System.out.println("留言个数" + list.size() );
		if(list.size() != 0) {
			//将所有留言设置为删除状态   然后新增留言
			for(Comment entity : list) {
				userMapper.deleteComment(entity.getEmid(), entity.getHkid()) ;
			}
			try {
				int count = userMapper.insertComment(emid,params.getHkid() , params.getScore(), params.getContent());
				if(count == 1 ) {
					return new ResultMap().success().message("插入成功");		
				}else {
					return new ResultMap().fail("435").message("插入失败");		
				}
			} catch (Exception e) {
				return new ResultMap().fail("436").message("插入失败");		
			}
		}else {
			//新增留言
			try {
				int count = userMapper.insertComment(emid,params.getHkid() , params.getScore(), params.getContent());
				if(count == 1 ) {
					return new ResultMap().success().message("插入成功");		
				}else {
					return new ResultMap().fail("435").message("插入失败");		
				}
			} catch (Exception e) {
				return new ResultMap().fail("436").message("插入失败");		
			}
		}
    }
	
	@RequestMapping(value = "getDetailsOfflineTraining",method = RequestMethod.POST)
    public ResultMap getDetailsOfflineTraining(HttpServletRequest request){
		if(request.getParameter("otid") == null) {
			return new ResultMap().fail("400").message("缺少参数otid");
		}
		String otid = request.getParameter("otid") ;
		if(request.getHeader(tokenHeader) != null && !"".equals(request.getHeader(tokenHeader) )) {
			String token = request.getHeader(tokenHeader) ;
			String username = tokenUtils.getUsernameFromToken(token) ; 
			String clienttype = tokenUtils.getUserTypeFromToken(token); 
			String hkid = "" ;
			if(!clienttype.equals("EMPLOYER")) {
				Hkid hkidentity = userMapper.getHkidByUsername(username) ;
				hkid = hkidentity.getHkid() ; 
			}
			List<OfflineTraining> list = userMapper.getOfflineTraining(otid);
			for(OfflineTraining entity:list) {
				entity.setPayflag("0");
				entity.setBuycount("0");
				List<OfflineTraining> list01 = userMapper.getOfflineTrainingIsBuy(hkid, entity.getOtid()) ;
				List<OfflineTraining> list02 = userMapper.getOfflineTrainingBuyCount(entity.getOtid()) ;
				if(list02.size() != 0 ) {
					entity.setBuycount(list02.size() + "");
				}
				if(list01.size() != 0 ) {
					for(OfflineTraining entity01:list01) {
						if("1".equals(entity01.getPayflag())) {
							entity.setPayflag("1");
						}
					}
				}
			}
			return new ResultMap().success().data(list);	
		}else {
			List<OfflineTraining> list = userMapper.getOfflineTraining(otid);
			for(OfflineTraining entity:list) {
				entity.setPayflag("0");
				entity.setBuycount("0");
				List<OfflineTraining> list02 = userMapper.getOfflineTrainingBuyCount(entity.getOtid()) ;
				if(list02.size() != 0 ) {
					entity.setBuycount(list02.size() + "");
				}
			}
			return new ResultMap().success().data(list);	
		}
		
    }
	
	
	@RequestMapping(value = "getOfflineTraining",method = RequestMethod.POST)
    public ResultMap getOfflineTraining(HttpServletRequest request){
		String otid = request.getParameter("otid") ;
		if(request.getHeader(tokenHeader) != null && !"".equals(request.getHeader(tokenHeader) )) {
			String token = request.getHeader(tokenHeader) ;
			String username = tokenUtils.getUsernameFromToken(token) ; 
			String clienttype = tokenUtils.getUserTypeFromToken(token); 
			String hkid = "" ;
			if(!clienttype.equals("EMPLOYER")) {
				Hkid hkidentity = userMapper.getHkidByUsername(username) ;
				hkid = hkidentity.getHkid() ; 
			}
			List<OfflineTraining> list = userMapper.getOfflineTraining(otid);
			for(OfflineTraining entity:list) {
				entity.setPayflag("0");
				entity.setBuycount("0");
				List<OfflineTraining> list01 = userMapper.getOfflineTrainingIsBuy(hkid, entity.getOtid()) ;
				List<OfflineTraining> list02 = userMapper.getOfflineTrainingBuyCount(entity.getOtid()) ;
				if(list02.size() != 0 ) {
					entity.setBuycount(list02.size() + "");
				}
				if(list01.size() != 0 ) {
					for(OfflineTraining entity01:list01) {
						if("1".equals(entity01.getPayflag())) {
							entity.setPayflag("1");
						}
					}
				}
			}
			return new ResultMap().success().data(list);	
		}else {
			List<OfflineTraining> list = userMapper.getOfflineTraining(otid);
			for(OfflineTraining entity:list) {
				entity.setPayflag("0");
				entity.setBuycount("0");
				List<OfflineTraining> list02 = userMapper.getOfflineTrainingBuyCount(entity.getOtid()) ;
				if(list02.size() != 0 ) {
					entity.setBuycount(list02.size() + "");
				}
			}
			return new ResultMap().success().data(list);	
		}
		
    }
		
	@RequestMapping(value = "admin/getOfflineTrainingRecord",method = RequestMethod.POST)
    public ResultMap getOfflineTrainingRecord(HttpServletRequest request){
		String hkid = request.getParameter("hkid") ;
		String otid = request.getParameter("otid") ;
		if(request.getParameter("hkid") != null) {
			List<OfflineRecord> list_ben = new ArrayList<>() ;
			List<OfflineTraining> list = userMapper.getOfflineTraining(otid);
			for(OfflineTraining entity:list) {
				List<OfflineTrainingRecord> list01 = userMapper.getOfflineTrainingRecord(hkid, entity.getOtid()) ;
				OfflineRecord obj = new OfflineRecord() ;
				obj.setOtid(entity.getOtid());
				obj.setName(entity.getName());
				obj.setScore("0");
				obj.setHkid(hkid);
				obj.setTime("");
				if(list01.size() != 0) {
					obj.setScore(list01.get(0).getScore());
					obj.setTime(list01.get(0).getCreate_time());
				}
				list_ben.add(obj) ;
			}
			return new ResultMap().success().data(list_ben);	
		}else {
			List<OfflineRecord> list_ben = new ArrayList<>() ;
			List<OfflineTraining> list = userMapper.getOfflineTraining(otid);
			for(OfflineTraining entity:list) {
				
				OfflineRecord obj = new OfflineRecord() ;
				
				obj.setOtid(entity.getOtid());
				obj.setName(entity.getName());
				obj.setScore("0");
				obj.setHkid("");
				obj.setTime("");
				
				list_ben.add(obj) ;
			}
			return new ResultMap().success().data(list_ben);	
		}
    }
	
	@RequestMapping(value = "getAppointMentMoney",method = RequestMethod.POST)
    public ResultMap getAppointMentMoney(HttpServletRequest request){
		List<AppointmentMoney>  list = userMapper.getAppointMentMoney() ;
		if(list.size() != 0) {
			return new ResultMap().success().data(list);		
		}else {
			return new ResultMap().fail("403").message("不存在相关预约金数据，数据异常");		
		}
		
    }
	
	@RequestMapping(value = "uploadImage",method = RequestMethod.POST ,consumes ="multipart/form-data")
	@ResponseBody
	public ResultMap uploadImage(HttpServletRequest request )throws Exception{
		String token = request.getHeader(tokenHeader) ;
		String username = tokenUtils.getUsernameFromToken(token) ; 
		String clienttype = tokenUtils.getUserTypeFromToken(token); 
		String id = "" ;
		if(clienttype.equals("EMPLOYER")) {
			Emid emidentity = userMapper.getEmidByUsername(username) ;
			id = emidentity.getEmid() ; 
		}else {
			Hkid hkidentity = userMapper.getHkidByUsername(username) ;
			id = hkidentity.getHkid() ; 
		}
		 
		Long time = System.currentTimeMillis() ;
		Random ran1 = new Random(100000) ;
		int number = ran1.nextInt(10) ;
		//1.获取文件请求过来的图片
		ApplicationPart image = (ApplicationPart) request.getPart("pic");
		//2.获得图片的输入流
		InputStream in = image.getInputStream();
		//3.获得资源文件后缀并重新命名
		String lds = image.getSubmittedFileName() ; 
		String benben = lds.substring(lds.lastIndexOf(".")) ; 
		String path_params = "/images/" + time + number + benben ; 
		//4.指定文件存储路径
		String path = "/usr/yinao/imgupload" + path_params ;
		//String path = "E:\\imgupload\\"+  path_params ;
		//5.把文件流转成文件
		inputstreamtofile(in,new File(path));
		try {
			int count = userMapper.changeIcon(id ,clienttype , path_params) ;
			if(count == 1 ) {
				return new ResultMap().success().message("插入成功");		
			}else {
				return new ResultMap().fail("435").message("插入失败");		
			}
		} catch (Exception e) {
			return new ResultMap().fail("436").message("插入失败");		
		}

	}
	
	@RequestMapping(value = "admin/uploadImage",method = RequestMethod.POST ,consumes ="multipart/form-data")
	@ResponseBody
	public ResultMap uploadimage(HttpServletRequest request )throws Exception{
		Long time = System.currentTimeMillis() ;
		Random ran1 = new Random(100000) ;
		int number = ran1.nextInt(10) ;
		//1.获取文件请求过来的图片
		ApplicationPart image = (ApplicationPart) request.getPart("pic");
		//2.获得图片的输入流
		InputStream in = image.getInputStream();
		//3.获得资源文件后缀并重新命名
		String lds = image.getSubmittedFileName() ; 
		String benben = lds.substring(lds.lastIndexOf(".")) ; 
		String path_params = "/images/" + time + number + benben ; 
		//4.指定文件存储路径
		String path = "/usr/yinao/imgupload/" + path_params ;
		//String path = "E:\\imgupload\\"+  path_params ;
		//5.把文件流转成文件
		return new ResultMap().success().message("插入成功").data(path_params);		
	}

	//InputStream,String,File相互转化
	private void inputstreamtofile(InputStream ins, File file){
		OutputStream os =null;
		try{
			os =new FileOutputStream(file);
			int bytesRead =0;
			byte[] buffer =new byte[8192];
			while((bytesRead = ins.read(buffer,0,8192)) != -1){
				os.write(buffer,0, bytesRead);
			}
			//System.out.println("inputstreamtofile");
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				if(os !=null){
				os.close();
				}
				if(ins !=null){
					ins.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	//增加线下考试结果集
    @RequestMapping(value = "admin/savaOfflineTraining",method = RequestMethod.POST)
    public ResultMap addresults(HttpServletRequest request){
    	
		String info = request.getParameter("info") ; 
		String hkid = request.getParameter("hkid") ; 
		
		List<OfflineRecord> list = JSON.parseArray(JSON.parse(info).toString(), OfflineRecord.class ) ;	
		 try {
	        for(int i=0 ; i< list.size() ; i++) {
	        	this.userMapper.deleteOfflineTraining(hkid, list.get(i).getOtid());
	        	int count = this.userMapper.savaOfflineTraining(hkid , list.get(i).getOtid() , list.get(i).getScore());
	        	if(count != 1) {
	        		return new ResultMap().fail("435").message("插入失败");	
	        	}
	    	}
		} catch (Exception e) {
			return new ResultMap().fail("436").message("插入失败").data(e);		
		}
		 return new ResultMap().success().message("插入成功");		 
    }
    
    //获取线下考试报名列表
    @RequestMapping(value = "admin/getOfflineTrainingNotes",method = RequestMethod.POST)
    public ResultMap getOfflineTrainingNotes(HttpServletRequest request){
    	if(request.getParameter("pageindex") == null) {
			return new ResultMap().fail("400").message("缺少参数pageindex");
		}
		if(request.getParameter("pagecount") == null) {
			return new ResultMap().fail("400").message("缺少参数pagecount");
		}
		String address = request.getParameter("address") ; 
		String name = request.getParameter("name") ; 
		String username = request.getParameter("username") ; 
		
		int pageindex = Integer.parseInt(request.getParameter("pageindex")); 
		int pagecount = Integer.parseInt(request.getParameter("pagecount")); 
		
		List<OfflineTrainingNotes> list = this.userMapper.getOfflineTrainingNotes(address , name , username );
		
		List<OfflineTrainingNotes> list01 = PageUtils.getList(list, pageindex, pagecount) ;
		 return new ResultMap().success().message("success").count(list.size()).data(list01);		 
    }
    
    //改变线下培训通知状态
    @RequestMapping(value = "admin/changeNoticeState",method = RequestMethod.POST)
    public ResultMap changeNoticeState(HttpServletRequest request){
		if(request.getParameter("hkid") == null) {
			return new ResultMap().fail("400").message("缺少参数hkid");
		}
		if( request.getParameter("transactionid")== null) {
			return new ResultMap().fail("400").message("缺少参数transactionid");
		}
		String hkid = request.getParameter("hkid") ; 
		String transactionid = request.getParameter("transactionid") ; 
		try {
			int count = this.userMapper.changeNoticeState(hkid, transactionid) ;
			if(count == 1 ) {
				return new ResultMap().success().message("插入成功");		
			}else {
				return new ResultMap().fail("435").message("插入失败");		
			}
		} catch (Exception e) {
			return new ResultMap().fail("436").message("插入失败");		
		}
    }
    
    //获取线下实操列表
    @RequestMapping(value = "admin/getOfflineTraining",method = RequestMethod.POST)
    public ResultMap getadminOfflineTraining(HttpServletRequest request){
    	if(request.getParameter("pageindex") == null) {
			return new ResultMap().fail("400").message("缺少参数pageindex");
		}
		if(request.getParameter("pagecount") == null) {
			return new ResultMap().fail("400").message("缺少参数pagecount");
		}
		String otid = request.getParameter("otid")  ; 
		String name = request.getParameter("name")  ; 
		int pageindex = Integer.parseInt(request.getParameter("pageindex")); 
		int pagecount = Integer.parseInt(request.getParameter("pagecount")); 
		List<OfflineTraining> list = userMapper.getadminOfflineTraining(otid , name );
		List<OfflineTraining> list01 = PageUtils.getList(list, pageindex, pagecount) ;
		return new ResultMap().success().message("success").count(list.size()).data(list01);		 
    }
    
    
  //新增线下实操列表
    @RequestMapping(value = "admin/addOfflineTraining",method = RequestMethod.POST)
    public ResultMap addOfflineTraining(@Valid OfflineTraining params){
    	
    	try {
			int count = this.userMapper.addOfflineTraining(params) ;
			if(count == 1 ) {
				return new ResultMap().success().message("插入成功");		
			}else {
				return new ResultMap().fail("435").message("插入失败");		
			}
		} catch (Exception e) {
			return new ResultMap().fail("436").message("插入失败");		
		}	 
    }
    
    //编辑线下实操列表
    @RequestMapping(value = "admin/editOfflineTraining",method = RequestMethod.POST)
    public ResultMap editOfflineTraining(@Valid OfflineTraining params){
    	if(params.getOtid() == null ) {
    		return new ResultMap().fail("400").message("缺少参数otid");
    	}
    	try {
			int count = this.userMapper.editOfflineTraining(params) ;
			if(count == 1 ) {
				return new ResultMap().success().message("修改成功");		
			}else {
				return new ResultMap().fail("435").message("修改失败");		
			}
		} catch (Exception e) {
			return new ResultMap().fail("436").message("修改失败");		
		}	 
    }
    
  //编辑线下实操列表
    @RequestMapping(value = "admin/deleteOfflineTraining",method = RequestMethod.POST)
    public ResultMap deleteOfflineTraining(@Valid OfflineTraining params){
    	if(params.getOtid() == null ) {
    		return new ResultMap().fail("400").message("缺少参数otid");
    	}
    	try {
			int count = this.userMapper.deleteOfflineTraininga(params) ;
			if(count == 1 ) {
				return new ResultMap().success().message("刪除成功");		
			}else {
				return new ResultMap().fail("435").message("刪除失败");		
			}
		} catch (Exception e) {
			return new ResultMap().fail("436").message("刪除失败");		
		}	 
    }
    
    //获取账户余额接口
    @RequestMapping(value = "getbalance",method = RequestMethod.POST)
    public ResultMap getbalance(HttpServletRequest request){
    	String token = request.getHeader(tokenHeader) ;
		String username = tokenUtils.getUsernameFromToken(token) ; 
		String clienttype = tokenUtils.getUserTypeFromToken(token); 
		String userid ;
		if(clienttype.equals("EMPLOYER")) {
			return new ResultMap().fail("401").message("此接口为housekeeper端接口");	
		}else {			
			Hkid hkidentity = userMapper.getHkidByUsername(username) ;
			userid = hkidentity.getHkid() ; 
		} 
		List<Balance> list = userMapper.getbalance(userid) ;
		
		if(list.size() == 1 ) {
			int balance = list.get(0).getBalance() ;
			return new ResultMap().success().data(balance);		
		}else {
			return new ResultMap().fail("403").message("无有效数据");		
		}
    }
    //获取充值金额
    @RequestMapping(value = "getChargePropertices",method = RequestMethod.POST)
    public ResultMap getChargePropertices(HttpServletRequest request){
		List<ChargePropertices> list = userMapper.getChargePropertices() ;
		return new ResultMap().success().data(list);		
    }
    
  //获取账户余额接口
    @RequestMapping(value = "getbill",method = RequestMethod.POST)
    public ResultMap getbill(HttpServletRequest request){
    	String token = request.getHeader(tokenHeader) ;
		String username = tokenUtils.getUsernameFromToken(token) ; 
		String clienttype = tokenUtils.getUserTypeFromToken(token); 
		String userid ;
		if(clienttype.equals("EMPLOYER")) {
			return new ResultMap().fail("401").message("此接口为housekeeper端接口");	
		}else {			
			Hkid hkidentity = userMapper.getHkidByUsername(username) ;
			userid = hkidentity.getHkid() ; 
		} 
		List<Bill> list = userMapper.getbill(userid) ;
		return new ResultMap().success().data(list);
	
    }
    
    //获取九型人格测试试题列表
    @RequestMapping(value = "getPersonalityTest",method = RequestMethod.POST)
    public ResultMap getPersonalityTest(HttpServletRequest request){
    	
    	
		List<PersonalityTest> list = userMapper.getPersonalityTest() ;
		for(PersonalityTest entity : list ) {
			String no = entity.getNo() ;
			List<PersonalityTestDes> list01= userMapper.getAnswer(no) ;
			entity.setList(list01);
		}
		return new ResultMap().success().data(list);
	
    }
    
  //获取16性格测试试题列表
    @RequestMapping(value = "getSixteenTest",method = RequestMethod.POST)
    public ResultMap getSixteenTest(HttpServletRequest request){
    	
		List<SixteenTest> list = userMapper.getSixteenTest() ;
		
		for(SixteenTest entity : list ) {
			String no = entity.getNo() ;
			List<SixteenOption> list01= userMapper.getSixteenAnswer(no) ;
			entity.setList(list01);
		}
		return new ResultMap().success().data(list);
	
    }
    
    //新增九型人格测试答案
    @RequestMapping(value = "savePersonalityAnswer",method = RequestMethod.POST)
    public ResultMap savePersonalityAnswer(HttpServletRequest request){
    	String token = request.getHeader(tokenHeader) ;
		String username = tokenUtils.getUsernameFromToken(token) ; 
		String clienttype = tokenUtils.getUserTypeFromToken(token); 
		String userid ;
		if(clienttype.equals("EMPLOYER")) {
			return new ResultMap().fail("401").message("此接口为housekeeper端专用接口");	
		}else {			
			Hkid hkidentity = userMapper.getHkidByUsername(username) ;
			userid = hkidentity.getHkid() ; 
		} 
		String result = request.getParameter("result") ;
		
		try {
			int count = userMapper.savePersonalityAnswer(userid , result ) ;
			if(count == 1 ) {
				return new ResultMap().success().message("添加成功");		
			}else {
				return new ResultMap().fail("435").message("添加失败");		
			}
		} catch (Exception e) {
			return new ResultMap().fail("436").message("添加失败");		
		}			
    }
    
  //新增16人格测试答案
    @RequestMapping(value = "savePersonalityAnswerTwo",method = RequestMethod.POST)
    public ResultMap savePersonalityAnswerTwo(HttpServletRequest request){
    	String token = request.getHeader(tokenHeader) ;
		String username = tokenUtils.getUsernameFromToken(token) ; 
		String clienttype = tokenUtils.getUserTypeFromToken(token); 
		String userid ;
		if(clienttype.equals("EMPLOYER")) {
			return new ResultMap().fail("401").message("此接口为housekeeper端专用接口");	
		}else {			
			Hkid hkidentity = userMapper.getHkidByUsername(username) ;
			userid = hkidentity.getHkid() ; 
		} 
		String result = request.getParameter("result") ;
		
		try {
			int count = userMapper.savePersonalityAnswerTwo(userid , result ) ;
			if(count == 1 ) {
				return new ResultMap().success().message("添加成功");		
			}else {
				return new ResultMap().fail("435").message("添加失败");		
			}
		} catch (Exception e) {
			return new ResultMap().fail("436").message("添加失败");		
		}			
    }
    
  //获取九型人格测试答案
    @RequestMapping(value = "admin/getPersonalityAnswer",method = RequestMethod.POST)
    public ResultMap getPersonalityAnswer(HttpServletRequest request){
    	if(request.getParameter("hkid") == null ) {
    		return new ResultMap().fail("400").message("缺少参数hkid");
    	}
		String hkid = request.getParameter("hkid") ;
		List<PersonalityNine> list =  userMapper.getPersonalityAnswer(hkid) ;
		return new ResultMap().success().data(list);
					
    } 
    
  //获取卡特尔人格测试答案
    @RequestMapping(value = "admin/getPersonalityAnswerTwo",method = RequestMethod.POST)
    public ResultMap getPersonalityAnswer2(HttpServletRequest request){
    	if(request.getParameter("hkid") == null ) {
    		return new ResultMap().fail("400").message("缺少参数hkid");
    	}
		String hkid = request.getParameter("hkid") ;
		List<PersonalityNine> list =  userMapper.getPersonalityAnswer2(hkid) ;
		return new ResultMap().success().data(list);
					
    } 
			
}
