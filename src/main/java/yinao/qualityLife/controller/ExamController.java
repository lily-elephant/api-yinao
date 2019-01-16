package yinao.qualityLife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import yinao.qualityLife.dao.ExamMapper;
import yinao.qualityLife.dao.InterviewMapper;
import yinao.qualityLife.dao.ServiceMapper;
import yinao.qualityLife.dao.UserMapper;
import yinao.qualityLife.dao.courseCatagoryMapper;
import yinao.qualityLife.model.ResultMap;
import yinao.qualityLife.model.domain.AnsewerCompare;
import yinao.qualityLife.model.domain.AnswerExam;
import yinao.qualityLife.model.domain.Ccid;
import yinao.qualityLife.model.domain.CourseCatagory;
import yinao.qualityLife.model.domain.CourseCatagoryCount;
import yinao.qualityLife.model.domain.Eid;
import yinao.qualityLife.model.domain.Emid;
import yinao.qualityLife.model.domain.Employer;
import yinao.qualityLife.model.domain.Exam;
import yinao.qualityLife.model.domain.ExamAdd;
import yinao.qualityLife.model.domain.ExamBen;
import yinao.qualityLife.model.domain.ExamOption;
import yinao.qualityLife.model.domain.ExamRecord;
import yinao.qualityLife.model.domain.ExamResult;
import yinao.qualityLife.model.domain.Hkid;
import yinao.qualityLife.model.domain.Interview;
import yinao.qualityLife.model.domain.Service;
import yinao.qualityLife.model.domain.User;
import yinao.qualityLife.utils.PageUtils;
import yinao.qualityLife.utils.TokenUtils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class ExamController{

    private final TokenUtils tokenUtils;
    private final ExamMapper examMapper;
    private final UserMapper userMapper ; 
    private final courseCatagoryMapper courseCatagoryMapper ;

    @Value("${token.header}")
    private String tokenHeader;

    @Autowired
    public ExamController(TokenUtils tokenUtils , ExamMapper examMapper  , UserMapper userMapper , courseCatagoryMapper courseCatagoryMapper) {
        this.tokenUtils = tokenUtils ;
        this.examMapper = examMapper;
        this.userMapper = userMapper ; 
        this.courseCatagoryMapper = courseCatagoryMapper ; 
    }
    //增加试题
    @RequestMapping(value = "exam/addexamcommon", method = RequestMethod.POST)
	public ResultMap addexamcommon(@Valid ExamAdd params, BindingResult bindingResult  ) {
		
    	// 检查用户输入的参数是否正确
        if (bindingResult.hasErrors()){
            return new ResultMap().fail("400").message("缺少参数或者参数格式不对");
        } 
        
        
        List<ExamOption> list = JSON.parseArray(JSON.parse(params.getOption()).toString(), ExamOption.class ) ;	
        
        
//        List<ExamOption> list = new ArrayList<>();
//        String option = params.getOption() ;
//        String[] ben = option.split(":") ;
//        for(int i=0 ; i<ben.length ;i++) {
//        	String[] ben01 =  ben[i].split(",") ;
//        	if(ben01.length != 3) {
//        		return new ResultMap().fail("401").message("option参数格式错误"); 
//        	}
//        	ExamOption obj = new ExamOption(ben01[0] , ben01[1] , ben01[2]) ;
//        	list.add(obj) ; 
//        }  
        //数据插入基础信息表
        try {
        	int count = this.examMapper.addexamcommon(params) ;
			if(count == 1 ) {
				String params_oid = "" ; 
				for(int i=0 ; i<list.size() ; i++) {
					list.get(i).setEid(params.getEid() + "");
					this.examMapper.addexamoption(list.get(i)) ;
					if("1".equals(list.get(i).getIsanswer())) {
						params_oid = params_oid + list.get(i).getOid() + "," ; 
					}
				}
				String benben = params_oid.substring(0, params_oid.length()-1 ) ;
				this.examMapper.addexamanswer(params.getEid(), benben ) ;
				//list 为option信息 
				return new ResultMap().success().message("插入成功");		
			}else {
				return new ResultMap().fail("435").message("插入失败");		
			}
		} catch (Exception e) {
			return new ResultMap().fail("436").message("插入失败").data(e);		
		}     
	}
    
    //编辑考试
    @RequestMapping(value = "exam/editexamcommon", method = RequestMethod.POST)
	public ResultMap editexamcommon(@Valid ExamAdd params, BindingResult bindingResult , HttpServletRequest request ) {
		
    	// 检查用户输入的参数是否正确
        if (bindingResult.hasErrors()){
            return new ResultMap().fail("400").message("缺少参数或者参数格式不对");
        } 
        List<ExamOption> list = JSON.parseArray(JSON.parse(params.getOption()).toString(), ExamOption.class ) ;	
//        List<ExamOption> list = new ArrayList<>();
//        String option = params.getOption() ;
//        String[] ben = option.split(":") ;
//        for(int i=0 ; i<ben.length ;i++) {
//        	String[] ben01 =  ben[i].split(",") ;
//        	if(ben01.length != 3) {
//        		return new ResultMap().fail("401").message("option参数格式错误"); 
//        	}
//        	ExamOption obj = new ExamOption(ben01[0] , ben01[1] , ben01[2]) ;
//        	list.add(obj) ; 
//        }  
        //数据修改基础信息表
        try {
        	int count = this.examMapper.editexamcommon(params);
			if(count == 1 ) {
				//外键关联 
				this.examMapper.deleteanswer(params.getEid()) ; 
				this.examMapper.deleteoption(params.getEid()) ; 
				String params_oid = "" ; 
				for(int i=0 ; i<list.size() ; i++) {
					list.get(i).setEid(params.getEid() + "");
					this.examMapper.addexamoption(list.get(i)) ;
					if("1".equals(list.get(i).getIsanswer())) {
						params_oid = params_oid + list.get(i).getOid() + "," ; 
						//this.examMapper.addexamanswer(list.get(i).getEid(), list.get(i).getOid()) ;
					}
				}
				String benben = params_oid.substring(0, params_oid.length()-1 ) ;
				this.examMapper.addexamanswer(params.getEid(), benben ) ;
				//list 为option信息 
				return new ResultMap().success().message("修改成功");		
			}else {
				return new ResultMap().fail("435").message("修改失败");		
			}
		} catch (Exception e) {
			return new ResultMap().fail("436").message("修改失败").data(e);		
		}        
        
	}
    //删除考试
    @RequestMapping(value = "exam/deleteexam", method = RequestMethod.POST)
	public ResultMap deleteexam(HttpServletRequest request ) {
    	// 检查用户输入的参数是否正确
        if (request.getParameter("eid") == null){
            return new ResultMap().fail("400").message("缺少参数eid");
        }        
        int eid = Integer.parseInt(request.getParameter("eid")) ;

        try {
        	this.examMapper.deleteanswer(eid) ; 
			this.examMapper.deleteoption(eid) ; 
        	int count = this.examMapper.deleteexam(eid);
			if(count == 1 ) {
				return new ResultMap().success().message("删除成功");		
			}else {
				return new ResultMap().fail("435").message("删除失败");		
			}
		} catch (Exception e) {
			return new ResultMap().fail("436").message("删除失败").data(e);		
		}   
       
        
	}
    //获取考试列表
    @RequestMapping(value = "exam/examlist",method = RequestMethod.POST)
    public ResultMap examlist(@Valid Exam params, BindingResult bindingResult , HttpServletRequest request ){  
    	if(request.getParameter("pageindex") == null ) {
    		return new ResultMap().fail("400").message("缺少参数pageindex");		
    	}
    	if(request.getParameter("pagecount") == null ) {
    		return new ResultMap().fail("400").message("缺少参数pagecount");		
    	}
    	String pageindex = request.getParameter("pageindex") ;
    	String pagecount = request.getParameter("pagecount") ;
        List<Exam> list = this.examMapper.examlist(params.getCcid() , params.getName() , params.getIsmust(), params.getEid()) ;
        for(int i = 0  ; i < list.size() ; i++ ) {
        	List<ExamOption> examOptions = this.examMapper.examOptionlist(list.get(i).getEid()) ; 
        	list.get(i).setOption(examOptions) ;
        	String ture_answer = "" ; 
        	List<ExamBen> list01 = this.examMapper.examAnswerben(list.get(i).getEid()) ; 
        	//System.out.println(list01.toString() ); 
        	if(list01.size() == 1) {
        		String params_oid = list01.get(0).getOid() + "" ;
        		//System.out.println(params_oid );
        		String[] arr = params_oid.split(",") ;
        		
        		for(int j=0 ; j< arr.length ; j++ ) {
        			
        			List<ExamOption> list02 = this.examMapper.getNameByOid(arr[j]) ; 
        			if(list02.size() == 1) {
        				ture_answer = ture_answer + list02.get(0).getName() +":" + list02.get(0).getContent() + ",";
        			}
        		}
        	}
        	String benben = ture_answer.substring(0 , ture_answer.length() -1 ) ;
        	list.get(i).setTure_answer(benben);
        }

        List<Exam> list01 = PageUtils.getList(list, Integer.parseInt(pageindex), Integer.parseInt(pagecount));
        return new ResultMap().success().count(list.size()).data(list01); 
 
    }
    
  //获取考试列表
    @RequestMapping(value = "exam/examlistaaa",method = RequestMethod.POST)
    public ResultMap examlistaaa(@Valid Exam params, BindingResult bindingResult , HttpServletRequest request ){  
    	List<Exam> labels=this.examMapper.getLabelList(params.getCcid());
    	ArrayList<Exam> list=new ArrayList<Exam>();
    	for(Exam value : labels) {
   		 List<Exam> examsSingel= this.examMapper.getexamlist(params.getCcid() , params.getName() , params.getIsmust(), params.getEid(),value.getLabel(),"SINGLE") ;
   		 List<Exam> examsMultiple= this.examMapper.getexamlist(params.getCcid() , params.getName() , params.getIsmust(), params.getEid(),value.getLabel(),"MULTIPLE") ;
   		 if(examsSingel.size()!=0) {
   			 list.add(examsSingel.get(0));
   		 }
   		 if(examsMultiple.size()!=0) {
   			 list.add(examsMultiple.get(0));
   		 }
   	}

        for(int i = 0  ; i < list.size() ; i++ ) {
        	List<ExamOption> examOptions = this.examMapper.examOptionlist(list.get(i).getEid()) ; 
        	list.get(i).setOption(examOptions) ;
        	String ture_answer = "" ; 
        	List<ExamOption> list01 = this.examMapper.examAnswer(list.get(i).getEid()) ; 
        	//System.out.println(list01.toString() );
        	if(list01.size() == 1) {
        		String params_oid = list01.get(0).getOid() + "" ;
        		
        		String[] arr = params_oid.split(",") ;
        		
        		for(int j=0 ; j< arr.length ; j++ ) {
        			
        			List<ExamOption> list02 = this.examMapper.getNameByOid(arr[j]) ; 
        			if(list02.size() == 1) {
        				ture_answer = ture_answer + list02.get(0).getName() +":" + list02.get(0).getContent() + ",";
        			}
        		}
        	}
        	String benben = ture_answer.substring(0 , ture_answer.length() -1 ) ;
        	list.get(i).setTure_answer(benben);
        }
        return new ResultMap().success().data(list); 
 
    }
    
  //获取入学考试列表
    @RequestMapping(value = "exam/baseExamlist",method = RequestMethod.POST)
    public ResultMap baseExamlist(@Valid Exam params, BindingResult bindingResult , HttpServletRequest request ){  
    	String ccid="0";
    	if(params.getCcid()!=null) {
    		ccid=params.getCcid();
    	}
    	List<Exam> list = this.examMapper.baseExamlist(ccid) ;
        for(int i = 0  ; i < list.size() ; i++ ) {
        	List<ExamOption> examOptions = this.examMapper.examOptionlist(list.get(i).getEid()) ; 
        	if(request.getHeader(tokenHeader)!=null && !"".equals(request.getHeader(tokenHeader)) && !request.getHeader(tokenHeader).isEmpty()) {
        		String token = request.getHeader(tokenHeader) ;
        		String username = tokenUtils.getUsernameFromToken(token) ; 
        		String usertype = tokenUtils.getUserTypeFromToken(token) ;
        		List<ExamBen> list01 ;
        		if("EMPLOYER".equals(usertype)) {
        			list01 = this.examMapper.getRecordByemid(list.get(i).getEid() , username) ;
        		}else {
        			list01 = this.examMapper.getRecordByhkid(list.get(i).getEid() , username) ;
        		}
        		if(list01.size() == 1) {
    				String selectedOid = list01.get(0).getOid() ; 
    				for(ExamOption entity : examOptions) {
    					if((entity.getOid()+"").equals(selectedOid)) {
    						entity.setIsSelected("1");
    					}else {
    						entity.setIsSelected("0");
    					}
    				}
    			}
        	}
        	//System.out.println(examOptions.toString() );
        	list.get(i).setOption(examOptions) ;
        	String ture_answer = "" ; 
        	List<ExamOption> list01 = this.examMapper.examAnswer(list.get(i).getEid()) ; 
        	//System.out.println(list01.toString() );
        	if(list01.size() == 1) {
        		String params_oid = list01.get(0).getOid() + "" ;
        		
        		String[] arr = params_oid.split(",") ;
        		
        		for(int j=0 ; j< arr.length ; j++ ) {
        			
        			List<ExamOption> list02 = this.examMapper.getNameByOid(arr[j]) ; 
        			if(list02.size() == 1) {
        				ture_answer = ture_answer + list02.get(0).getName() +":" + list02.get(0).getContent() + ",";
        			}
        		}
        	}
        	String benben = ture_answer.substring(0 , ture_answer.length() -1 ) ;
        	list.get(i).setTure_answer(benben);
        }
        return new ResultMap().success().data(list); 
 
    }
    
  //获取职位类别
    @RequestMapping(value = "exam/baseExamCatagory",method = RequestMethod.POST)
    public ResultMap baseExamCatagory(@Valid Exam params, BindingResult bindingResult , HttpServletRequest request ){  
    	List<Exam> list = this.examMapper.baseExamCatagory() ;
        return new ResultMap().success().data(list); 
    }
    //获取我需要的职位类别
    @RequestMapping(value = "exam/baseMyExamCatagory",method = RequestMethod.POST)
    public ResultMap baseMyExamCatagory(@Valid Employer params, BindingResult bindingResult , HttpServletRequest request ){  
    	List<Exam> list = this.examMapper.baseMyExamCatagory(params.getUsername()) ;
        return new ResultMap().success().data(list);
    }
    
    //增加考试结果
    @RequestMapping(value = "exam/addresult",method = RequestMethod.POST)
    public ResultMap addresult(@Valid ExamResult params, BindingResult bindingResult , HttpServletRequest request){

        // 检查用户输入的参数是否正确
        if (bindingResult.hasErrors()){
            return new ResultMap().fail("400").message("缺少参数或者参数格式不对");
        }         
        String token = request.getHeader(tokenHeader) ;
		String username = tokenUtils.getUsernameFromToken(token) ; 
		String usertype = tokenUtils.getUserTypeFromToken(token); 
		
        try {
        	int count = this.examMapper.addresult(params.getOid(), params.getEid(), usertype, username);
			if(count == 1 ) {
				return new ResultMap().success().message("插入成功");		
			}else {
				return new ResultMap().fail("435").message("插入失败");		
			}
		} catch (Exception e) {
			return new ResultMap().fail("436").message("插入失败").data(e);		
		}
 
    }
    //增加考试结果集
    @RequestMapping(value = "exam/addresults",method = RequestMethod.POST)
    public ResultMap addresults(HttpServletRequest request){
		String answer = request.getParameter("answer") ; 
		String token = request.getHeader(tokenHeader) ;
		String username = tokenUtils.getUsernameFromToken(token) ; 
		String usertype = tokenUtils.getUserTypeFromToken(token); 
		
		List<AnswerExam> list = JSON.parseArray(JSON.parse(answer).toString(), AnswerExam.class ) ;	
		 try {
	        for(int i=0 ; i< list.size() ; i++) {
	        	this.examMapper.deleteresultUser(list.get(i).getEid(), usertype, username);
	        	int count = this.examMapper.addresult(list.get(i).getOid(), list.get(i).getEid(), usertype, username);
	        	if(count != 1) {
	        		return new ResultMap().fail("435").message("插入失败");	
	        	}
	    	}
		} catch (Exception e) {
			return new ResultMap().fail("436").message("插入失败").data(e);		
		}
		 return new ResultMap().success().message("插入成功");		 
    }
    
    @RequestMapping(value = "exam/editresult", method = RequestMethod.POST)
	public ResultMap editresult(@Valid ExamResult params, BindingResult bindingResult  , HttpServletRequest request) {
		String token = request.getHeader(tokenHeader) ;
		String username = tokenUtils.getUsernameFromToken(token) ; 
		String usertype = tokenUtils.getUserTypeFromToken(token); 
		
		if(request.getParameter("id") == null ) {
			return new ResultMap().fail("401").message("缺少id");		
		}
		try {
			int count = this.examMapper.editresult(request.getParameter("id"), params.getOid(), params.getEid(), usertype, username) ;
			if(count == 1 ) {
				return new ResultMap().success().message("修改成功" );		
			}else {
				return new ResultMap().fail("435").message("修改失败");		
			}
		} catch (Exception e) {
			return new ResultMap().fail("436").message("修改失败").data(e);		
		}
	}
    
    @RequestMapping(value = "exam/deleteresult", method = RequestMethod.POST)
	public ResultMap deleteresult(@Valid ExamResult params, BindingResult bindingResult  , HttpServletRequest request) {
		String token = request.getHeader(tokenHeader) ;
		String usertype = tokenUtils.getUserTypeFromToken(token); 
		
		if(request.getParameter("id") == null ) {
			return new ResultMap().fail("401").message("缺少id");		
		}
		try {
			int count = this.examMapper.deleteresult(request.getParameter("id"),usertype) ;
			if(count == 1 ) {
				return new ResultMap().success().message("删除成功" );		
			}else {
				return new ResultMap().fail("435").message("删除失败");		
			}
		} catch (Exception e) {
			return new ResultMap().fail("436").message("删除失败").data(e);		
		}
	}
    
    // 获取我的考试记录 和答题 
    @RequestMapping(value = "exam/getEaxmRecord", method = RequestMethod.POST)
   	public ResultMap getEaxmRecord(HttpServletRequest request) {
    	String token = request.getHeader(tokenHeader) ;
		String usertype = tokenUtils.getUserTypeFromToken(token); 
		if(usertype.equals("EMPLOYER")) {
			return new ResultMap().fail("402").message("此接口为家政端专用接口");		
		}else {
			String username = tokenUtils.getUsernameFromToken(token) ; 
			Hkid entity = userMapper.getHkidByUsername(username) ;
			String ccid_parsms = request.getParameter("ccid") ;
			String hkid = entity.getHkid() ; 
			ArrayList<String> ccidArray=new ArrayList<String>();
			List<CourseCatagory> catagories=this.courseCatagoryMapper.gettestCourseCatagory(hkid);
	        for(CourseCatagory catagory : catagories) {
	        	List<CourseCatagoryCount> orderCourses=this.courseCatagoryMapper.getOrderCount(hkid,catagory.getCcid());
	        	List<CourseCatagoryCount> courseCounts=this.courseCatagoryMapper.getCatagoryCourseCount(catagory.getCcid());
	        	if(orderCourses.get(0).getCount()==courseCounts.get(0).getCount()) {
	        		ccidArray.add(catagory.getCcid());
	        	}
	        }
	        //所有可考试列表 ccid 1 和 2 
	        List<CourseCatagory> testcatagories=this.courseCatagoryMapper.gettestCatagory(ccidArray);
	        List<CourseCatagory> list01 = new ArrayList<CourseCatagory>() ; 
	        List<CourseCatagory> list02 = new ArrayList<CourseCatagory>() ; 
	        List<ExamRecord> list_record = new ArrayList<ExamRecord>();
	        for(CourseCatagory entity01 : testcatagories) {
	        	String ccid = entity01.getCcid() ;
	        	List<CourseCatagoryCount> list = this.examMapper.getUseCcidCount(ccid) ;
	        	if(list.get(0).getCount() != 0) {
	        		list01.add(entity01) ;
	        	}
	        }
	        if(ccid_parsms != null) {
	        	for(CourseCatagory entity02 : list01) {
		        	if(entity02.getCcid().equals(ccid_parsms)) {
		        		list02.add(entity02) ;
		        	}
		        }
	        	list01 = list02 ; 
	        }
	        //list01 所有可考试 的ccid
	        for(CourseCatagory entity02 : list01) {
	        	String ccid = entity02.getCcid() ;
	        	int score = 0 ; 
	        	String name = "" ;
	        	String time = "" ;
	        	//获取每个ccid下对应的eid
	        	List<Eid> list = this.examMapper.getEidByCcid(ccid) ;
	        	//System.out.println(list.toString() );
	        	if(list.size() != 0) {
	        		name = list.get(0).getName() ; 
	        	}
	        	boolean isHas = true ;
	        	for(Eid eidList : list) {
	        		//拿到eid比对答案表和用户答题表 答案是否一致  已知加分 
	        		String eid = eidList.getEid() ;
	        		// 2,3,4,5,6,7
	        		
	        		List<AnsewerCompare> answer_compare = this.examMapper.getAnsewerCompareList(eid , username);
	        		if(answer_compare.size() == 0) {
	        			isHas = false ; 
	        		}else {
	        			time = answer_compare.get(0).getCreate_time() ;
	        		}
	        		//System.out.println(isHas +"");
	        		if(answer_compare.size()!=0 && answer_compare.get(0).getTrue_oid().equals(answer_compare.get(0).getUser_oid())) {
	        			score += eidList.getScore() ;
	        			
	        		}
	        	}
	        	if(isHas) {
	        		ExamRecord examRecord = new ExamRecord(name , time , score) ;
		        	list_record.add(examRecord) ;
	        	}
	        	
	        }
	        /**
			 * 查出所有的ccid  然后遍历里面的cid 必学字段是不是都是已学  然后就是我可考试的列表 
			 * 然后通过可考试列表的ccid 遍历里面的eid  比对答题表里面是不是所有题目的答题信息都存在 都存在 
			 * 记录本次考试记录有效   然后对比答案表 计算得分 
			 */
	        return new ResultMap().success().data(list_record);	  
		}	
   	}
    
    // 后台管理系统  考试记录 和答题 
    @RequestMapping(value = "admin/exam/getEaxmRecord", method = RequestMethod.POST)
   	public ResultMap getadminEaxmRecord(HttpServletRequest request) {
//    	MultimediaInfo MultimediaInfo ;
    	String username = request.getParameter("username") ;
    	String usertype = request.getParameter("usertype") ;
    	if(username == null || usertype == null ) {
    		return new ResultMap().fail("400").message("缺少参数");		
    	}

		if(usertype.equals("EMPLOYER")) {
			return new ResultMap().fail("402").message("此接口为家政端专用接口");		
		}else {
			Hkid entity = userMapper.getHkidByUsername(username) ;
			if(entity == null ) {
				return new ResultMap().fail("401").message("username参数错误");		
			}
			String ccid_parsms = request.getParameter("ccid") ;
			String hkid = entity.getHkid() ; 
			ArrayList<String> ccidArray=new ArrayList<String>();
			//查找当前用户所购买的课程在哪个模块下    生成列表    
			List<CourseCatagory> catagories=this.courseCatagoryMapper.gettestCourseCatagory(hkid);
	        for(CourseCatagory catagory : catagories) {
	        	List<CourseCatagoryCount> orderCourses=this.courseCatagoryMapper.getOrderCount(hkid,catagory.getCcid());
	        	List<CourseCatagoryCount> courseCounts=this.courseCatagoryMapper.getCatagoryCourseCount(catagory.getCcid());
	        	if(orderCourses.get(0).getCount()==courseCounts.get(0).getCount()) {
	        		ccidArray.add(catagory.getCcid());
	        	}
	        }
	        //所有可考试  ccid  列表 
	        List<CourseCatagory> testcatagories=this.courseCatagoryMapper.gettestCatagory(ccidArray);
	        List<CourseCatagory> list01 = new ArrayList<CourseCatagory>() ; 
	        List<CourseCatagory> list02 = new ArrayList<CourseCatagory>() ; 
	        List<ExamRecord> list_record = new ArrayList<ExamRecord>();
	        for(CourseCatagory entity01 : testcatagories) {
	        	String ccid = entity01.getCcid() ;
	        	List<CourseCatagoryCount> list = this.examMapper.getUseCcidCount(ccid) ;
	        	if(list.get(0).getCount() != 0) {
	        		list01.add(entity01) ;
	        	}
	        }   
	        if(ccid_parsms != null) {
	        	for(CourseCatagory entity02 : list01) {
		        	if(entity02.getCcid().equals(ccid_parsms)) {
		        		list02.add(entity02) ;
		        	}
		        }
	        	list01 = list02 ; 
	        }
	        //list01 所有可考试 的ccid     
	        for(CourseCatagory entity02 : list01) {
	        	String ccid = entity02.getCcid() ;
	        	int score = 0 ; 
	        	String name = "" ;
	        	String time = "" ;
	        	//获取每个ccid下对应的eid
	        	List<Eid> list = this.examMapper.getEidByCcid(ccid) ;
	        	if(list.size() != 0) {
	        		name = list.get(0).getName() ; 
	        	}
	        	boolean isHas = true ;
	        	for(Eid eidList : list) {
	        		//拿到eid比对答案表和用户答题表 答案是否一致  已知加分 
	        		String eid = eidList.getEid() ;
	        		List<AnsewerCompare> answer_compare = this.examMapper.getAnsewerCompareList(eid , username);
	        		if(answer_compare.size() == 0) {
	        			isHas = false ; 
	        		}
	        		if(answer_compare.size()!=0 && answer_compare.get(0).getTrue_oid().equals(answer_compare.get(0).getUser_oid())) {
	        			score += eidList.getScore() ;
	        			time = answer_compare.get(0).getCreate_time() ;
	        		}
	        	}
	        	if(isHas) {
	        		ExamRecord examRecord = new ExamRecord(name , time , score) ;
		        	list_record.add(examRecord) ;
	        	}
	        }
	        /**
			 * 查出所有的ccid  然后遍历里面的cid 必学字段是不是都是已学  然后就是我可考试的列表 
			 * 然后通过可考试列表的ccid 遍历里面的eid  比对答题表里面是不是所有题目的答题信息都存在 都存在 
			 * 记录本次考试记录有效   然后对比答案表 计算得分 
			 */
	        return new ResultMap().success().data(list_record);	  
		}	
   	}
}
