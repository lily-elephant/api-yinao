package yinao.qualityLife.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import yinao.qualityLife.dao.HouseKeeperMapper;
import yinao.qualityLife.dao.employerMapper;
import yinao.qualityLife.model.ResultMap;
import yinao.qualityLife.model.domain.Employer;
import yinao.qualityLife.model.domain.EmployerHouseKeeper;
import yinao.qualityLife.model.domain.EmployerSign;
import yinao.qualityLife.model.domain.HouseKeeper;
import yinao.qualityLife.model.domain.ViewCard;
import yinao.qualityLife.model.domain.ViewCardPrice;
import yinao.qualityLife.utils.PageUtils;
import yinao.qualityLife.utils.TokenUtils;

@RestController
public class EmployerController {
		
	@Value("${token.header}")
    private String tokenHeader;
	
	private final employerMapper employerMapper;
	private final HouseKeeperMapper housekeeperMapper;
	private final TokenUtils tokenUtils;

	
	@Autowired
    public EmployerController(TokenUtils tokenUtils, employerMapper employerMapper,HouseKeeperMapper housekeeperMapper) {
        this.tokenUtils = tokenUtils ; 
        this.employerMapper = employerMapper;
        this.housekeeperMapper = housekeeperMapper;
    }
	
	
	@RequestMapping(value = "employerList", method = RequestMethod.GET)
	public ResultMap getEmployerInfo (HttpServletRequest request){
		String username = request.getParameter("username");
		String name = request.getParameter("name");
		String servicestate = request.getParameter("servicestate");
		int currPage = Integer.parseInt(request.getParameter("pageindex"));
		int pageSize = Integer.parseInt(request.getParameter("pagecount"));
		List<Employer> employer = this.employerMapper.getEmployerInfo(username,name,servicestate);
		List<Employer> list  = PageUtils.getList(employer , currPage , pageSize ) ; 
		if(list != null) {
			return new ResultMap().success().message("success").count(employer.size()).data(list );		
		}else {
			return new ResultMap().success().message("没有更多数据");
		}				
	}
	// 获取所有雇主联系家政人员的记录信息
	@RequestMapping(value = "employer/emhkcontractList", method = RequestMethod.POST)
	public ResultMap emhkcontractList (HttpServletRequest request){
		String emname = request.getParameter("emname");
		String hkname = request.getParameter("hkname");
		String emphone = request.getParameter("emphone");
		String hkphone = request.getParameter("hkphone");
		int pageindex=-1;
		int pagecount=-1;
		if(request.getParameter("pageindex")!=null) {
			pageindex = Integer.parseInt(request.getParameter("pageindex"));
		}
		if(request.getParameter("pagecount")!=null) {
			pagecount = Integer.parseInt(request.getParameter("pagecount"));
		}
		
		List<EmployerHouseKeeper> list=this.housekeeperMapper.emhouseKeeperContractList(emname,hkname,emphone,hkphone,pageindex,pagecount);
		
		return new ResultMap().success().data(list);
	}
	// 获取所有雇主签约家政人员的记录信息
	@RequestMapping(value = "employer/emhksignList", method = RequestMethod.POST)
	public ResultMap emhksignList (HttpServletRequest request){
		String emname = request.getParameter("emname");
		String hkname = request.getParameter("hkname");
		String emphone = request.getParameter("emphone");
		String hkphone = request.getParameter("hkphone");
		int pageindex=-1;
		int pagecount=-1;
		if(request.getParameter("pageindex")!=null) {
			pageindex = Integer.parseInt(request.getParameter("pageindex"));
		}
		if(request.getParameter("pagecount")!=null) {
			pagecount = Integer.parseInt(request.getParameter("pagecount"));
		}	
		List<EmployerHouseKeeper> list=this.housekeeperMapper.emhouseKeeperSignList(emname,hkname,emphone,hkphone,pageindex,pagecount);
		
		return new ResultMap().success().data(list);
	}
	// 获取所有雇主签约家政人员的记录信息
	@RequestMapping(value = "employer/emhksigndetail", method = RequestMethod.POST)
	public ResultMap emhksigndetail (HttpServletRequest request){
		String primaryid = request.getParameter("primaryid");
		EmployerHouseKeeper list=this.housekeeperMapper.emhouseKeeperSignDetail(primaryid);
		
		return new ResultMap().success().data(list);
	}
	// 更新雇主签约家政人员的记录信息
	@RequestMapping(value = "employer/updateemhksigndetail", method = RequestMethod.POST)
	public ResultMap updateemhksigndetail (@Valid EmployerHouseKeeper params, BindingResult bindingResult,HttpServletRequest request){		
		this.housekeeperMapper.updateemhouseKeeperSignDetail(params);
		return new ResultMap().success();
	}
	// 获取雇主联系信息，如果有数据，直接拨打电话
	@RequestMapping(value = "employer/contractList", method = RequestMethod.POST)
	public ResultMap employerContractList (HttpServletRequest request){
		String username = request.getParameter("username");
		String hkid = request.getParameter("hkid");
		int currPage=-1;
		int pageSize=-1;
		if(request.getParameter("currPage")!=null) {
			currPage = Integer.parseInt(request.getParameter("pageindex"));
		}
		if(request.getParameter("pageSize")!=null) {
			pageSize = Integer.parseInt(request.getParameter("pagecount"));
		}
		Employer employer=this.employerMapper.getEmployerByUserName(username);
		String emid=employer.getEmid();
		List<HouseKeeper> list=this.housekeeperMapper.houseKeeperContractList(emid, hkid,currPage,pageSize);
		
		return new ResultMap().success().data(list);
	}
	// 拨打电话，将拨打记录存入表中
	@RequestMapping(value = "employer/contract", method = RequestMethod.POST)
	public ResultMap employerContract (HttpServletRequest request){
		String username = request.getParameter("username");
		String hkid = request.getParameter("hkid");
		String ccid = request.getParameter("ccid");
		int currPage=-1;
		int pageSize=-1;
		if(request.getParameter("currPage")!=null) {
			currPage = Integer.parseInt(request.getParameter("currPage"));
		}
		if(request.getParameter("pageSize")!=null) {
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}
		Employer employer=this.employerMapper.getEmployerByUserName(username);
		String emid=employer.getEmid();
		List<HouseKeeper> list=this.housekeeperMapper.houseKeeperContractList(emid, hkid,currPage,pageSize);
		if(list.size()!=0) {
			this.employerMapper.updateContract(emid, hkid);
		}else {
			this.employerMapper.insertContract(emid, hkid);
		}
		// 将remaincount减一
		this.employerMapper.updateRemainCount(emid,ccid);
		return new ResultMap().success();
	}
	// 新增雇主签约申请
	@RequestMapping(value = "employer/sign", method = RequestMethod.POST)
	public ResultMap employerSign (HttpServletRequest request){
		String username = request.getParameter("username");
		String hkid = request.getParameter("hkid");
		Employer employer=this.employerMapper.getEmployerByUserName(username);
		String emid=employer.getEmid();
		this.employerMapper.insertSign(emid, hkid);
		
		return new ResultMap().success();
	}
	// 获取雇主签约列表
	@RequestMapping(value = "employer/signlist", method = RequestMethod.POST)
	public ResultMap employerSignList (HttpServletRequest request){
		String username = request.getParameter("username");
		String hkid = request.getParameter("hkid");
		Employer employer=this.employerMapper.getEmployerByUserName(username);
		String emid=employer.getEmid();
		List<EmployerSign> list=this.employerMapper.getSignList(emid, hkid);
		
		return new ResultMap().success().data(list);
	}
	// 获取雇主查询卡信息
	@RequestMapping(value = "employer/viewcard", method = RequestMethod.POST)
	public ResultMap employerViewCard (HttpServletRequest request){
		String username = request.getParameter("username");
		String ccid = request.getParameter("ccid");
		Employer employer=this.employerMapper.getEmployerByUserName(username);
		String emid=employer.getEmid();
		ViewCard viewcard=this.employerMapper.getViewCard(emid, ccid);
		
		return new ResultMap().success().data(viewcard);
	}
	// 获取查询卡价格次数信息
	@RequestMapping(value = "employer/viewcardpricebyccid", method = RequestMethod.POST)
	public ResultMap employerViewCardPriceByccid (HttpServletRequest request){
		String ccid = request.getParameter("ccid");
		List<ViewCardPrice> viewcardPrice=this.employerMapper.getViewCardPrice(ccid);
		
		return new ResultMap().success().data(viewcardPrice);
	}
	
	@RequestMapping(value = "employer/star", method = RequestMethod.POST)
	public ResultMap employerStar (HttpServletRequest request){
		String username = request.getParameter("username");
		String hkid = request.getParameter("hkid");
		String flag = request.getParameter("flag");//0,取消收藏  1,收藏
		Employer employer=this.employerMapper.getEmployerByUserName(username);
		String emid=employer.getEmid();
		
		if(flag.equals("0")) {
			this.employerMapper.deleteEmployerStar(emid, hkid);
		}
		else {
			this.employerMapper.insertEmployerStar(emid, hkid);
		}
		return new ResultMap().success();
	}
	@RequestMapping(value = "employer/starList", method = RequestMethod.POST)
	public ResultMap employerStarList (HttpServletRequest request){
		String username = request.getParameter("username");
		String hkid = request.getParameter("hkid");
		Employer employer=this.employerMapper.getEmployerByUserName(username);
		String emid=employer.getEmid();
		List<HouseKeeper> list=this.housekeeperMapper.houseKeeperStarList(emid, hkid);
		
		return new ResultMap().success().data(list);
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
