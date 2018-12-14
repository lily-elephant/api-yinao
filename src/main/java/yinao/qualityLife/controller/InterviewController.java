package yinao.qualityLife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import yinao.qualityLife.dao.InterviewMapper;
import yinao.qualityLife.dao.UserMapper;
import yinao.qualityLife.model.ResultMap;
import yinao.qualityLife.model.domain.Emid;
import yinao.qualityLife.model.domain.Hkid;
import yinao.qualityLife.model.domain.HouseKeeper;
import yinao.qualityLife.model.domain.Interview;
import yinao.qualityLife.model.domain.InterviewList;
import yinao.qualityLife.model.domain.InterviewOrder;
import yinao.qualityLife.model.domain.User;
import yinao.qualityLife.utils.PageUtils;
import yinao.qualityLife.utils.TokenUtils;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class InterviewController {

    private final TokenUtils tokenUtils;
    private final InterviewMapper interviewMapper;
    private final UserMapper userMapper ; 

    @Value("${token.header}")
    private String tokenHeader;

    @Autowired
    public InterviewController(TokenUtils tokenUtils , InterviewMapper interviewMapper , UserMapper userMapper) {
        this.tokenUtils = tokenUtils ;
        this.interviewMapper = interviewMapper;
        this.userMapper = userMapper ; 
    }

    @RequestMapping(value = "order/addinterview",method = RequestMethod.POST)
    public ResultMap addinterview(@Valid Interview params, BindingResult bindingResult,HttpServletRequest request ){
    	String token = request.getHeader(tokenHeader) ;
    	String username = tokenUtils.getUsernameFromToken(token) ; 
    	Emid emid = this.userMapper.getEmidByUsername(username);
    	params.setEmid(emid.getEmid());

        // 检查用户输入的参数是否正确
        if (bindingResult.hasErrors()){
            return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
        }
        try {
            int count = this.interviewMapper.addinterview(params.getEmid(),params.getHkid(),params.getInterviewtime(),params.getPlace(), params.getManager() , params.getResult(),params.getRemark()) ;
            if(count == 1 ) {
                return new ResultMap().success().message("插入成功");
            }else {
                return new ResultMap().fail("435").message("插入失败");
            }
        } catch (Exception e) {
            return new ResultMap().fail("436").message("插入失败").data(e);
        }
    }
    
    @RequestMapping(value = "admin/addinterview",method = RequestMethod.POST)
    public ResultMap adminaddinterview(@Valid Interview params, BindingResult bindingResult,HttpServletRequest request ){
        // 检查用户输入的参数是否正确
        if (bindingResult.hasErrors()){
            return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
        }
        if(request.getParameter("emid") == null) {
        	return new ResultMap().fail("400").message("缺少参数emid");
        }
        try {
            int count = this.interviewMapper.addinterview(params.getEmid(),params.getHkid(),params.getInterviewtime(),params.getPlace(), params.getManager() , params.getResult(),params.getRemark()) ;
            if(count == 1 ) {
                return new ResultMap().success().message("插入成功");
            }else {
                return new ResultMap().fail("435").message("插入失败");
            }
        } catch (Exception e) {
            return new ResultMap().fail("436").message("插入失败").data(e);
        }
    }

    @RequestMapping(value = "editinterview",method = RequestMethod.POST)
    public ResultMap editinterview(@Valid Interview params, BindingResult bindingResult ){

        // 检查用户输入的参数是否正确
        if (bindingResult.hasErrors()){
            return new ResultMap().fail("400").message("缺少参数或者参数格式不对");
        }
        //检查参数种是否存在interviewid
        if (params.getInterviewid() == null){
            return new ResultMap().fail("400").message("缺少interviewid") ;
        }
        try {
            int count = this.interviewMapper.editinterview(params.getInterviewid() ,params.getEmid(),params.getHkid(),params.getInterviewtime(),params.getPlace(), params.getManager() , params.getResult(),params.getRemark()) ;
            if(count == 1 ) {
                return new ResultMap().success().message("修改成功");
            }else {
                return new ResultMap().fail("435").message("修改失败");
            }
        } catch (Exception e) {
            return new ResultMap().fail("436").message("修改失败").data(e);
        }
    }
    
    @RequestMapping(value = "order/cancelInterview",method = RequestMethod.POST)
    public ResultMap cancelInterview(@Valid InterviewOrder params, BindingResult bindingResult,HttpServletRequest request ){

        // 检查用户输入的参数是否正确
        if (bindingResult.hasErrors()){
            return new ResultMap().fail("400").message("缺少参数或者参数格式不对");
        }
        
        String token = request.getHeader(tokenHeader) ;
    	String username = tokenUtils.getUsernameFromToken(token) ; 
    	Emid emid = this.userMapper.getEmidByUsername(username);
        
        try {
            int count = this.interviewMapper.cancelInterview(params.getInterviewid() ,emid.getEmid()) ;
            if(count == 1 ) {
                return new ResultMap().success().message("修改成功");
            }else {
                return new ResultMap().fail("435").message("修改失败");
            }
        } catch (Exception e) {
            return new ResultMap().fail("436").message("修改失败").data(e);
        }
    }
    
    @RequestMapping(value = "order/cancelInterviewTwo",method = RequestMethod.POST)
    public ResultMap cancelInterviewTwo(@Valid InterviewOrder params, BindingResult bindingResult,HttpServletRequest request ){

        // 检查用户输入的参数是否正确
        if (bindingResult.hasErrors()){
            return new ResultMap().fail("400").message("缺少参数或者参数格式不对");
        }
        
        String token = request.getHeader(tokenHeader) ;
    	String username = tokenUtils.getUsernameFromToken(token) ; 
    	Hkid hkid = this.userMapper.getHkidByUsername(username);
        
        try {
            int count = this.interviewMapper.cancelInterviewTwo(params.getInterviewid() ,hkid.getHkid()) ;
            if(count == 1 ) {
                return new ResultMap().success().message("修改成功");
            }else {
                return new ResultMap().fail("435").message("修改失败");
            }
        } catch (Exception e) {
            return new ResultMap().fail("436").message("修改失败").data(e);
        }
    }
    
    @RequestMapping(value = "admin/cancelInterview",method = RequestMethod.POST)
    public ResultMap admincancelInterview(@Valid InterviewOrder params, BindingResult bindingResult,HttpServletRequest request ){

        // 检查用户输入的参数是否正确
        if (bindingResult.hasErrors()){
            return new ResultMap().fail("400").message("缺少参数或者参数格式不对");
        }
        
        if(request.getParameter("result") == null ) {
        	return new ResultMap().fail("400").message("缺少参数result");
        }
        
        if(!"雇主放弃".equals(request.getParameter("result"))  &&  !"家政人员放弃".equals(request.getParameter("result")) ) {
        	return new ResultMap().fail("400").message("参数result格式异常");
        }
        String result = request.getParameter("result") ; 
        try {
            int count = this.interviewMapper.admincancelInterview(params.getInterviewid() , result ) ;
            if(count == 1 ) {
                return new ResultMap().success().message("修改成功");
            }else {
                return new ResultMap().fail("435").message("修改失败");
            }
        } catch (Exception e) {
            return new ResultMap().fail("436").message("修改失败").data(e);
        }
    }
    
    @RequestMapping(value = "order/sureInterview",method = RequestMethod.POST)
    public ResultMap sureInterview(@Valid InterviewOrder params, BindingResult bindingResult,HttpServletRequest request ){

        // 检查用户输入的参数是否正确
        if (bindingResult.hasErrors()){
            return new ResultMap().fail("400").message("缺少参数或者参数格式不对");
        }
        
        String token = request.getHeader(tokenHeader) ;
    	String username = tokenUtils.getUsernameFromToken(token) ; 
    	Emid emid = this.userMapper.getEmidByUsername(username);
        
        try {
            int count = this.interviewMapper.sureInterview(params.getInterviewid() ,emid.getEmid()) ;
            if(count == 1 ) {
                return new ResultMap().success().message("修改成功");
            }else {
                return new ResultMap().fail("435").message("修改失败");
            }
        } catch (Exception e) {
            return new ResultMap().fail("436").message("修改失败").data(e);
        }
    }
    
    @RequestMapping(value = "admin/changeInterviewState",method = RequestMethod.POST)
    public ResultMap changeInterviewState(@Valid InterviewOrder params, BindingResult bindingResult,HttpServletRequest request ){

        // 检查用户输入的参数是否正确
        if (bindingResult.hasErrors()){
            return new ResultMap().fail("400").message("缺少参数或者参数格式不对");
        }
        
        try {
            int count = this.interviewMapper.changeInterviewState(params.getInterviewid() ) ;
            if(count == 1 ) {
                return new ResultMap().success().message("修改成功");
            }else {
                return new ResultMap().fail("435").message("修改失败");
            }
        } catch (Exception e) {
            return new ResultMap().fail("436").message("修改失败").data(e);
        }
    }


    @RequestMapping(value = "deleteinterview", method = RequestMethod.POST)
    public ResultMap deleteinterview(HttpServletRequest request) {
        String interviewid = request.getParameter("interviewid");
        if(interviewid == null ) {
            return new ResultMap().fail("400").message("请添加interviewid");
        }
        try {
            int count = this.interviewMapper.deleteinterview(interviewid);
            if(count == 1 ) {
                return new ResultMap().success().message("删除成功");
            }else {
                return new ResultMap().fail("435").message("删除失败");
            }
        } catch (Exception e) {
            return new ResultMap().fail("436").message("删除失败").data(e.toString() );
        }
    }
    
    
    @RequestMapping(value = "order/interviewlist", method = RequestMethod.POST)
    public ResultMap interviewlist(@Valid InterviewList params, BindingResult bindingResult , HttpServletRequest request) {
    	if(request.getParameter("pageindex") == null || request.getParameter("pageindex") == "") {
    		return new ResultMap().fail("400").message("缺少参数pageindex");
    	}
    	if(request.getParameter("pagecount") == null || request.getParameter("pagecount") == "") {
    		return new ResultMap().fail("400").message("缺少参数pagecount");
    	}
    	String token = request.getHeader(tokenHeader) ;
    	String username = tokenUtils.getUsernameFromToken(token) ; 
    	String usertype = tokenUtils.getUserTypeFromToken(token) ;
    	List<InterviewList> lists ; 
    	if("EMPLOYER".equals(usertype)) {
    		lists = this.interviewMapper.interviewlist(username, params.getHkphone(), params.getEmname(), params.getHkname()) ;
    	}else {
    		lists = this.interviewMapper.interviewlist(params.getEmphone(), username, params.getEmname(), params.getHkname()) ;
    	}
    	int pageindex = Integer.parseInt(request.getParameter("pageindex"));
		int pagecount = Integer.parseInt(request.getParameter("pagecount"));
    	
    	
    	List<InterviewList> list  = PageUtils.getList(lists , pageindex , pagecount ) ; 
		if(list != null) {
			return new ResultMap().success().message("success").count(lists.size()).data(list);
		}else {
			return new ResultMap().success().message("没有更多数据");
		}	
    }
    
    @RequestMapping(value = "admin/interviewlist", method = RequestMethod.POST)
    public ResultMap adminInterviewlist(@Valid InterviewList params, BindingResult bindingResult , HttpServletRequest request) {
    	if(request.getParameter("pageindex") == null || request.getParameter("pageindex") == "") {
    		return new ResultMap().fail("400").message("缺少参数pageindex");
    	}
    	if(request.getParameter("pagecount") == null || request.getParameter("pagecount") == "") {
    		return new ResultMap().fail("400").message("缺少参数pagecount");
    	}
    	String username = "" ; 
    	if(request.getParameter("username") != null) {
    		username = request.getParameter("username") ;
    	}
    	int pageindex = Integer.parseInt(request.getParameter("pageindex"));
		int pagecount = Integer.parseInt(request.getParameter("pagecount"));
		
		
    	List<InterviewList> lists = this.interviewMapper.interviewlist(username, params.getHkphone(), params.getEmname(), params.getHkname()) ;
    	
    	List<InterviewList> list  = PageUtils.getList(lists , pageindex , pagecount ) ; 
		if(list != null) {
			return new ResultMap().success().message("success").count(lists.size()).data(list);
		}else {
			return new ResultMap().success().message("没有更多数据");
		}	
    }
    
    
    @RequestMapping(value = "admin/detailsinterview", method = RequestMethod.POST)
    public ResultMap detailsinterview(@Valid InterviewList params, BindingResult bindingResult , HttpServletRequest request) {
    	
    	if(request.getParameter("interviewid") == null) {
    		return new ResultMap().fail("400").message("缺少参数interviewid");
    	}
    	
    	List<InterviewList> list = this.interviewMapper.detailsinterview(request.getParameter("interviewid")) ;
    	
    	
		if(list.size() != 0) {
			return new ResultMap().success().message("success").data(list);
		}else {
			return new ResultMap().success().message("没有更多数据");
		}	
    }
    
    
//    @RequestMapping(value = "admin/order/addservice",method = RequestMethod.POST)
//    public ResultMap admninaddservice(@Valid Interview params, BindingResult bindingResult,HttpServletRequest request ){
//    	
//    	if(request.getParameter("emid") == null ) {
//    		return new ResultMap().fail("400").message("缺少参数emid");
//    	}
//    	if(request.getParameter("manager") == null ) {
//    		return new ResultMap().fail("400").message("缺少参数manager");
//    	}
//        // 检查用户输入的参数是否正确
//        if (bindingResult.hasErrors()){
//        	return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
//        }
//        try {
//            int count = this.interviewMapper.addinterview(params.getEmid(),params.getHkid(),params.getInterviewtime(),params.getPlace(), params.getManager() , params.getResult(),params.getRemark()) ;
//            if(count == 1 ) {
//                return new ResultMap().success().message("插入成功");
//            }else {
//                return new ResultMap().fail("435").message("插入失败");
//            }
//        } catch (Exception e) {
//            return new ResultMap().fail("436").message("插入失败").data(e);
//        }
//    }




}
