package yinao.qualityLife.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import yinao.qualityLife.dao.HouseKeeperMapper;
import yinao.qualityLife.dao.UserMapper;
import yinao.qualityLife.model.ResultMap;
import yinao.qualityLife.model.domain.EmployerComment;
import yinao.qualityLife.model.domain.HouseKeeper;
import yinao.qualityLife.model.domain.HousekeeperCertificate;
import yinao.qualityLife.model.domain.LikeBean;
import yinao.qualityLife.model.domain.User;
import yinao.qualityLife.model.vo.DataCount;
import yinao.qualityLife.utils.PageUtils;
import yinao.qualityLife.utils.TokenUtils;

@RestController
public class HouseKeeperController {
	
	private final UserMapper userMapper;
	private final HouseKeeperMapper housekeeperMapper;
	private final TokenUtils tokenUtils;
	
	@Value("${token.header}")
    private String tokenHeader;
	
	@Autowired
    public HouseKeeperController(UserMapper userMapper , HouseKeeperMapper housekeeperMapper , TokenUtils tokenUtils) {
        this.housekeeperMapper = housekeeperMapper ; 
        this.userMapper = userMapper ; 
        this.tokenUtils = tokenUtils ; 
    }
	
	@RequestMapping(value = "housekeeper/housekeeperlist",method = RequestMethod.GET)
    public ResultMap houseKeeperList(HttpServletRequest request){
		String username_token = "" ; 
		String token = request.getHeader(tokenHeader) ;
		if(token != null ) {
			username_token = tokenUtils.getUsernameFromToken(token) ; 
		}
		String username = request.getParameter("username");
		String name = request.getParameter("name");
		String state = request.getParameter("state");
		int currPage = Integer.parseInt(request.getParameter("currPage"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		List<HouseKeeper> housekeeperlist = this.housekeeperMapper.houseKeeperList(username,name,state);
		for(int i= 0 ; i< housekeeperlist.size() ; i++ ) {
			housekeeperlist.get(i).setIsLike("0");
			List<LikeBean> list = this.housekeeperMapper.houseKeeperLikeCount(housekeeperlist.get(i).getHkid() + "") ;
			//System.out.println(list.toString() );
			int count = list.size() ;
			housekeeperlist.get(i).setLikeCount(count + "");
			for(int j= 0 ; j< list.size() ; j++ ) {
				if(username_token.equals(list.get(j).getUsername())){
					housekeeperlist.get(i).setIsLike("1");
				}
			}
		}
		List<HouseKeeper> list  = PageUtils.getList(housekeeperlist , currPage , pageSize ) ; 
		if(list != null) {
			return new ResultMap().success().message("success").count(housekeeperlist.size()).data(list );		
		}else {
			return new ResultMap().success().message("没有更多数据");
		}
    }
	// 后台家政人员列表，可根据不同参数过滤
	@RequestMapping(value = "admin/housekeeper/housekeeperlist",method = RequestMethod.GET)
    public ResultMap adminhouseKeeperList(HttpServletRequest request){
		String username = request.getParameter("username");
		String name = request.getParameter("name");
		String state = request.getParameter("state");
		String servicestate = request.getParameter("servicestate");
		String age = request.getParameter("age");
		String sage =null;
		String eage =null;
		if(age!=null &&age!="") {
			String[] temp=age.split("-");
			sage =temp[0];
			eage =temp[1];
		}

		String nativeplace = request.getParameter("nativeplace");
		String education = request.getParameter("education");
		if(request.getParameter("currPage") == null) {
			return new ResultMap().fail("401").message("缺少参数currPage");
		}
		if(request.getParameter("pageSize") == null) {
			return new ResultMap().fail("401").message("缺少参数pageSize");
		}
		int currPage = Integer.parseInt(request.getParameter("currPage"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		// 获得记录总数
		DataCount dataCount=this.housekeeperMapper.dataCount(username,name,state,servicestate,sage,eage,nativeplace,education);
		List<HouseKeeper> list = this.housekeeperMapper.adminhouseKeeperList(currPage, pageSize,username,name,state,servicestate,sage,eage,nativeplace,education);
//		List<HouseKeeper> list  = PageUtils.getList(housekeeperlist , currPage , pageSize ); 
		if(list != null) {
			return new ResultMap().success().message("success").count(dataCount.getRecordcount()).data(list );
		}else {
			return new ResultMap().success().message("没有更多数据");
		}
    }
	
	@RequestMapping(value = "housekeeper/editHousekeeperInfo", method = RequestMethod.POST)
	public ResultMap editHousekeeperInfo(HttpServletRequest request) {
		String token = request.getHeader(tokenHeader) ;
		String username_token = tokenUtils.getUsernameFromToken(token) ; 
		String username = request.getParameter("username");
		String name = request.getParameter("name");
		String headimageurl = request.getParameter("headimageurl");
		String headvideourl = request.getParameter("headvideourl");
		String idcard = request.getParameter("idcard"); 
		String education = request.getParameter("education"); 
		String address1 = request.getParameter("address1");
		String address2 = request.getParameter("address2");
		String address3 = request.getParameter("address3");
		String sex = request.getParameter("sex");
		String role_id = request.getParameter("role_id");
		String brief = request.getParameter("brief");
		String salary = request.getParameter("salary");
		String workdate = request.getParameter("workdate");
		String marry = request.getParameter("marry");
		String isdrive = request.getParameter("isdrive");
		String describes = request.getParameter("describes");
		String nativeplace = request.getParameter("nativeplace");
		String servicestate = request.getParameter("servicestate");
		// 后加两个字段
		String nowsalary = request.getParameter("nowsalary");
		String frompart = request.getParameter("frompart");
		
		if(!username_token.equals(username)) {
			return new ResultMap().fail("401").message("没有权限修改他人信息");		
		}
		try {
			int count = this.housekeeperMapper.updateEmployerInfo(servicestate,username,name,headimageurl,headvideourl, idcard,education,address1,address2,address3,sex,role_id,brief,salary,workdate,marry,isdrive,describes, nativeplace,nowsalary,frompart);
			if(count == 1 ) {
				return new ResultMap().success().message("修改成功" );		
			}else {
				return new ResultMap().fail("435").message("修改失败");		
			}
		} catch (Exception e) {
			return new ResultMap().fail("436").message("修改失败");		
		}
	}
	
	
	@RequestMapping(value = "admin/housekeeper/editHousekeeperInfo", method = RequestMethod.POST)
	public ResultMap admineditHousekeeperInfo(HttpServletRequest request) {
		if(request.getParameter("username")== null) {
			return new ResultMap().fail("400").message("缺少参数username");		
		}
		String username = request.getParameter("username");
		String name = request.getParameter("name");
		String headimageurl = request.getParameter("headimageurl");
		String headvideourl = request.getParameter("headvideourl");
		String idcard = request.getParameter("idcard"); 
		String education = request.getParameter("education"); 
		String address1 = request.getParameter("address1");
		String address2 = request.getParameter("address2");
		String address3 = request.getParameter("address3");
		String sex = request.getParameter("sex");
		String role_id = request.getParameter("role_id");
		String brief = request.getParameter("brief");
		String salary = request.getParameter("salary");
		String workdate = request.getParameter("workdate");
		String marry = request.getParameter("marry");
		String isdrive = request.getParameter("isdrive");
		String describes = request.getParameter("describes");
		String nativeplace = request.getParameter("nativeplace");
		String servicestate = request.getParameter("servicestate");
		// 后加两个字段
		String nowsalary = request.getParameter("nowsalary");
		String frompart = request.getParameter("frompart");
		try {
			int count = this.housekeeperMapper.updateEmployerInfo(servicestate,username,name,headimageurl, headvideourl,idcard,education,address1,address2,address3,sex,role_id,brief,salary,workdate,marry,isdrive, describes,nativeplace,nowsalary,frompart);
			if(count == 1 ) {
				return new ResultMap().success().message("修改成功" );		
			}else {
				return new ResultMap().fail("435").message("修改失败");		
			}
		} catch (Exception e) {
			return new ResultMap().fail("436").message("修改失败");		
		}
	}
	
	@RequestMapping(value = "addhousekeeper", method = RequestMethod.POST)
	public ResultMap addhousekeeper(@Valid HouseKeeper params , HttpServletRequest request) {
		String username = request.getParameter("username");
		if(username == null ) {
			return new ResultMap().fail("400").message("请添加手机号码").data("");
		}
		User user = userMapper.loginHousekeeper(username) ; 
		if(user != null) {
			return new ResultMap().fail("434").message("改账号已经被注册！").data("");
		}
		
		Md5PasswordEncoder md5= new Md5PasswordEncoder();
    	md5.setEncodeHashAsBase64(false);
        String password= md5.encodePassword("123456", request.getParameter("username") ); 
        params.setPassword(password);
//		String name = request.getParameter("name");
//		String headimageurl = request.getParameter("headimageurl");
//		String idcard = request.getParameter("idcard"); 
//		String education = request.getParameter("education"); 
//		String address1 = request.getParameter("address1");
//		String address2 = request.getParameter("address2");
//		String address3 = request.getParameter("address3");
//		String sex = request.getParameter("sex");
//		String salary = request.getParameter("salary");
		try {
			int count = this.housekeeperMapper.addhousekeeper(params);
			int hkid = params.getHkid() ;
			if(count == 1 ) {
				return new ResultMap().success().message("插入成功").data(hkid);		
			}else {
				return new ResultMap().fail("435").message("插入失败");		
			}
		} catch (Exception e) {
			return new ResultMap().fail("436").message("插入失败");		
		}
	}
	
	@RequestMapping(value = "deletehousekeeper", method = RequestMethod.POST)
	public ResultMap deletehousekeeper(HttpServletRequest request) {
		String username = request.getParameter("username");
		if(username == null ) {
			return new ResultMap().fail("400").message("请添加手机号码");
		}
		User user = userMapper.loginHousekeeper(username) ; 
		if(user == null) {
			return new ResultMap().fail("434").message("改账号还未被注册！");
		}
		try {
			int count = this.housekeeperMapper.deletehousekeeper(username);
			if(count == 1 ) {
				return new ResultMap().success().message("删除成功");		
			}else {
				return new ResultMap().fail("435").message("删除失败");		
			}
		} catch (Exception e) {
			return new ResultMap().fail("436").message("删除失败").data(e.toString() );		
		}
	}
	
	@RequestMapping(value = "admin/showHousekeeper", method = RequestMethod.POST)
	public ResultMap showhousekeeper(HttpServletRequest request) {
		String username = request.getParameter("username");
		String state = request.getParameter("state");
		if(username == null || state==null ) {
			return new ResultMap().fail("400").message("缺少参数");
		}
		User user = userMapper.loginHousekeeper(username) ; 
		if(user == null) {
			return new ResultMap().fail("434").message("改账号还未被注册！");
		}
		try {
			int count = this.housekeeperMapper.showhousekeeper(username,state);
			if(count == 1 ) {
				return new ResultMap().success().message("修改成功");		
			}else {
				return new ResultMap().fail("435").message("修改失败");		
			}
		} catch (Exception e) {
			return new ResultMap().fail("436").message("修改失败").data(e.toString() );		
		}
	}
	
	@RequestMapping(value = "housekeeper/comment", method = RequestMethod.POST)
	public ResultMap housekeepercomment(@Valid EmployerComment params, BindingResult bindingResult ) {
		// 检查用户输入的参数是否正确
        if (bindingResult.hasErrors()){
            return new ResultMap().fail("400").message("缺少参数或者参数格式不对");
        } 
        List<EmployerComment> list = this.housekeeperMapper.housekeepercomment(params.getHkid() , params.getEmid()) ;
		return new ResultMap().success().data(list);		
	}
	
	@RequestMapping(value = "housekeeper/certificatelist", method = RequestMethod.POST)
	public ResultMap certificatelist(@Valid HousekeeperCertificate params, BindingResult bindingResult ) {
		// 检查用户输入的参数是否正确
        if (bindingResult.hasErrors()){
            return new ResultMap().fail("400").message("缺少参数或者参数格式不对");
        } 
        List<HousekeeperCertificate> list = this.housekeeperMapper.certificatelist(params.getHkid()) ;
		return new ResultMap().success().data(list);		
	}
	
	@RequestMapping(value = "admin/addCertificates", method = RequestMethod.POST)
	public ResultMap addCertificates(HttpServletRequest request) {
		if(request.getParameter("certificatelist")  == null || "".equals(request.getParameter("certificatelist").toString().trim())) {
			return new ResultMap().fail("400").message("certificatelist缺少参数或者参数格式不对");
		}
		String certificatelist = request.getParameter("certificatelist") ; 
		System.out.println(certificatelist);
		List<HousekeeperCertificate> list = JSON.parseArray(JSON.parse(certificatelist).toString(), HousekeeperCertificate.class ) ;
		System.out.println(list.toString());
		try {
			for(HousekeeperCertificate entity : list) {
				this.housekeeperMapper.deleteCertificate(entity.getHkid() , entity.getCid()) ;
				int count = this.housekeeperMapper.addCertificate(entity.getHkid() , entity.getCid() , entity.getPicture() ) ;
		        if(count != 1) {
		        	return new ResultMap().fail("435").message("插入失败");	
		        }
			 }
	        
		} catch (Exception e) {
			return new ResultMap().fail("436").message("插入失败").data(e);		
		}
		 return new ResultMap().success().message("插入成功");		 
		
	}
	
	
}
