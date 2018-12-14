package yinao.qualityLife.controller;

import java.util.List;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import yinao.qualityLife.dao.employerMapper;
import yinao.qualityLife.model.ResultMap;
import yinao.qualityLife.model.domain.Employer;
import yinao.qualityLife.utils.PageUtils;
import yinao.qualityLife.utils.TokenUtils;

@RestController
public class EmployerController {
		
	@Value("${token.header}")
    private String tokenHeader;
	
	private final employerMapper employerMapper;
	private final TokenUtils tokenUtils;

	
	@Autowired
    public EmployerController(TokenUtils tokenUtils, employerMapper employerMapper) {
        this.tokenUtils = tokenUtils ; 
        this.employerMapper = employerMapper;
    }
	
	
	@RequestMapping(value = "employerList", method = RequestMethod.GET)
	public ResultMap getEmployerInfo (HttpServletRequest request){
		String username = request.getParameter("username");
		String name = request.getParameter("name");
		String servicestate = request.getParameter("servicestate");
		int currPage = Integer.parseInt(request.getParameter("currPage"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		List<Employer> employer = this.employerMapper.getEmployerInfo(username,name,servicestate);
		List<Employer> list  = PageUtils.getList(employer , currPage , pageSize ) ; 
		if(list != null) {
			return new ResultMap().success().message("success").count(employer.size()).data(list );		
		}else {
			return new ResultMap().success().message("没有更多数据");
		}				
	}
	
	@RequestMapping(value = "editEmployerInfo", method = RequestMethod.POST)
	public ResultMap updateEmployerInfo(HttpServletRequest request) {
		String token = request.getHeader(tokenHeader) ;
		String username_token = tokenUtils.getUsernameFromToken(token) ; 
		String username = request.getParameter("username");
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		String idcard = request.getParameter("idcard"); 
		String education = request.getParameter("education"); 
		String address1 = request.getParameter("address1");
		String address2 = request.getParameter("address2");
		String address3 = request.getParameter("address3");
		String headimageurl = request.getParameter("headimageurl");
		String servicestate = request.getParameter("servicestate");
		
		String addresslabel = request.getParameter("addresslabel");
		String roomarea = request.getParameter("roomarea");
		String courtarea = request.getParameter("courtarea");
		String bedroom = request.getParameter("bedroom");
		String restaurant = request.getParameter("restaurant");
		String washroom = request.getParameter("washroom");
		String peoplecount = request.getParameter("peoplecount");
		String oldcount = request.getParameter("oldcount");
		String childcount = request.getParameter("childcount");
		String describes = request.getParameter("describes"); 
		if(!username_token.equals(username)) {
			return new ResultMap().fail("401").message("没有权限修改他人信息");		
		}
		
		
		try {
			int count = this.employerMapper.updateEmployerInfo(username,name,idcard,education,address1,address2,address3,headimageurl,servicestate, sex,addresslabel, roomarea ,courtarea ,bedroom, restaurant ,washroom ,peoplecount ,oldcount, childcount ,describes);
			if(count == 1 ) {
				return new ResultMap().success().message("修改成功");		
			}else {
				return new ResultMap().fail("435").message("修改失败");		
			}
		} catch (Exception e) {
			return new ResultMap().fail("436").message("修改失败");		
		}
	}
	
	
	@RequestMapping(value = "admin/deleteEmployer", method = RequestMethod.POST)
	public ResultMap deleteEmployer(HttpServletRequest request) {
		
		if(request.getParameter("username") == null || request.getParameter("state") == null) {
			return new ResultMap().fail("401").message("缺少参数");		
		}
		String username = request.getParameter("username") ;
		String state = request.getParameter("state") ;
		try {
			int count = this.employerMapper.deleteEmployer(username,state);
			if(count == 1 ) {
				return new ResultMap().success().message("修改成功");		
			}else {
				return new ResultMap().fail("435").message("修改失败");		
			}
		} catch (Exception e) {
			return new ResultMap().fail("436").message("修改失败");		
		}
	}
	
	
}
