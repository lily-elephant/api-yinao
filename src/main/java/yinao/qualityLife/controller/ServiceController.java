package yinao.qualityLife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import yinao.qualityLife.dao.InterviewMapper;
import yinao.qualityLife.dao.ServiceMapper;
import yinao.qualityLife.model.ResultMap;
import yinao.qualityLife.model.domain.Interview;
import yinao.qualityLife.model.domain.Service;
import yinao.qualityLife.utils.TokenUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class ServiceController {

    private final TokenUtils tokenUtils;
    private final ServiceMapper serviceMapper;
    private final InterviewMapper interviewMapper ;

    @Value("${token.header}")
    private String tokenHeader;

    @Autowired
    public ServiceController(TokenUtils tokenUtils , ServiceMapper serviceMapper , InterviewMapper interviewMapper) {
        this.tokenUtils = tokenUtils ;
        this.serviceMapper = serviceMapper;
        this.interviewMapper = interviewMapper ; 
    }
    


    @RequestMapping(value = "addservice",method = RequestMethod.POST)
    public ResultMap addservice(@Valid Service params, BindingResult bindingResult ,HttpServletRequest request){

        // 检查用户输入的参数是否正确
        if (bindingResult.hasErrors()){
            return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
        }
      
//      //起始时间
//      String startTime = params.getStarttime() ;
//      //结束时间
//      String endtime =  params.getEndtime() ;
//      //总共月数
//      int monthCounts ; 
//      
//      int startTtime_04 = Integer.parseInt( startTime.substring(0 , 4) );
//      int startTtime_57 = Integer.parseInt( startTime.substring(5 , 7) ); 
//      int startTtime_810 = Integer.parseInt( startTime.substring(8 , 10) ); 
//      
//      int endtime_04 = Integer.parseInt( endtime.substring(0 , 4) ); 
//      int endtime_57 = Integer.parseInt( endtime.substring(5 , 7) ); 
//      
//      if(startTtime_04 == endtime_04) {
//      	monthCounts = endtime_57 - startTtime_57 ;
//      }else {
//      	monthCounts = (endtime_04 - startTtime_04) * 12  + endtime_57 - startTtime_57 ;
//      }   
//      System.out.println(monthCounts);
//      for(int i=0 ; i<monthCounts ; i++) {
//      	String aaa = "" ; 
//      	String bbb = "" ; 
//      	
//      	if((startTtime_57 + i) > 12 ) {
//      		aaa = (startTtime_04+1) + "-" + (startTtime_57 + i - 12) + "-" + startTtime_810 + "" ;
//      	}else {
//      		aaa = (startTtime_04) + "-" + (startTtime_57 + i) + "-" + startTtime_810 + "" ;
//      	}
//      	
//      	if((startTtime_57 + i + 1) > 12 ) {
//      		bbb = (startTtime_04+1) + "-" + (startTtime_57 + i + 1 - 12) + "-" + startTtime_810 + "" ;
//      	}else {
//      		bbb = (startTtime_04) + "-" + (startTtime_57 + i + 1) + "-" + startTtime_810 + "" ;
//      	}
//      	//System.out.println("第" + (i + 1) + "条数据：" + aaa + "==============" + bbb);
//      	
//      	try {
//      		Service entity = new Service() ; 
//      		entity.setEmid(params.getEmid());
//      		entity.setHkid(params.getHkid());
//      		entity.setStarttime(aaa);
//      		entity.setEndtime(bbb);
//      		entity.setSalary(params.getSalary());
//      		entity.setManagefee(params.getManagefee());
//      		//String benben = startTtime_57 + i > 12 ? startTtime_57 + i - 12 +"" : startTtime_57 + i + "" ;
//      		//entity.setRemark( benben + "月份工资");
//        	this.serviceMapper.addservice(entity) ;
//        	//System.out.println(entity.getOsid());
//        	int salary = Integer.parseInt(params.getSalary()) ; 
//        	int managefee = Integer.parseInt(params.getManagefee()) ;
//        	int money = (salary + managefee) * 100 ; 
//        	
//        	//System.out.println(money);
//        	
//        	this.serviceMapper.addBillData(entity.getOsid(), params.getEmid(), money) ;
//        	        	               	 
//        } catch (Exception serviceMapper) {
//            return new ResultMap().fail("436").message("插入失败");
//        }  
//      }
        
        
        try {
        	Service entity = new Service() ; 
        	entity.setEmid(params.getEmid());
        	entity.setHkid(params.getHkid());
        	entity.setStarttime(params.getStarttime());
        	entity.setEndtime(params.getEndtime());
        	entity.setSalary(params.getSalary());
        	entity.setManagefee(params.getManagefee());
        	this.serviceMapper.addservice(entity) ;
        	//int salary = Integer.parseInt(params.getSalary()) ; 
        	int managefee = Integer.parseInt(params.getManagefee()) ;
        	//int money = (salary + managefee) * 100 ; 
        	//System.out.println(money);
        	this.serviceMapper.addBillData(entity.getOsid(), params.getEmid(), managefee) ;
	    } catch (Exception serviceMapper) {
	    	return new ResultMap().fail("436").message("插入失败");
	    } 
      
      
      
      if(request.getParameter("interviewid") != null && !"".equals(request.getParameter("interviewid"))) {
    	  this.interviewMapper.admincancelInterview(request.getParameter("interviewid") , "已签约" ) ;

      }
      
	  this.serviceMapper.addServiceRecord(params.getEmid() , params.getHkid() , params.getStarttime() , params.getEndtime() , request.getParameter("interviewid"),params.getSalary() ) ;

      

      
      
      return new ResultMap().success().message("插入成功");
           
    }

    @RequestMapping(value = "editservice",method = RequestMethod.POST)
    public ResultMap editservice(@Valid Service params, BindingResult bindingResult ){

        // 检查用户输入的参数是否正确
        if (bindingResult.hasErrors()){
            return new ResultMap().fail("400").message("缺少参数或者参数格式不对");
        }
     
        try {
            int count = this.serviceMapper.editservice(params.getOsid(), params.getEmid(), params.getHkid(), params.getStarttime(), params.getEndtime(), params.getSalary(), params.getManagefee(), params.getRemark()) ;
            if(count == 1 ) {
                return new ResultMap().success().message("修改成功");
            }else {
                return new ResultMap().fail("435").message("修改失败");
            }
        } catch (Exception e) {
            return new ResultMap().fail("436").message("修改失败").data(e);
        }
    }

    @RequestMapping(value = "deleteservice", method = RequestMethod.POST)
    public ResultMap deleteservice(HttpServletRequest request) {
        String osid = request.getParameter("osid");
        if(osid == null ) {
            return new ResultMap().fail("400").message("请添加osid");
        }
        try {
            int count = this.serviceMapper.deleteservice(osid);
            if(count == 1 ) {
                return new ResultMap().success().message("删除成功");
            }else {
                return new ResultMap().fail("435").message("删除失败");
            }
        } catch (Exception e) {
            return new ResultMap().fail("436").message("删除失败").data(e.toString() );
        }
    }




}
