package yinao.qualityLife.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import yinao.qualityLife.dao.courseCatagoryMapper;
import yinao.qualityLife.model.ResultMap;
import yinao.qualityLife.model.domain.Courses;
import yinao.qualityLife.model.domain.GetHkid;
import yinao.qualityLife.model.domain.HouseKeeper;
import yinao.qualityLife.model.domain.MyCourse;
import yinao.qualityLife.model.domain.CourseCatagory;
import yinao.qualityLife.model.domain.CourseCatagoryCount;
import yinao.qualityLife.utils.TokenUtils;


@RestController
public class CourseControllers {
	
	@Value("${token.header}")
    private String tokenHeader;
	private final courseCatagoryMapper courseCatagoryMapper;
	private final TokenUtils tokenUtils;
	
	public CourseControllers(courseCatagoryMapper courseCatagoryMapper, TokenUtils tokenUtils) {
		this.courseCatagoryMapper = courseCatagoryMapper;
		this.tokenUtils=tokenUtils;
	}
	//课程模块列表
	@RequestMapping(value="/course/coursecatagorylist",method=RequestMethod.POST)
	public ResultMap courseCatagoryList(HttpServletRequest request) {
		// userMapper.registerLoginEmployer(requestLoginUser01.getUsername(),password ) ;
		if(request.getParameter("pageindex")==null || request.getParameter("pagecount")==null) {
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		int currPage = Integer.parseInt(request.getParameter("pageindex"));//1 2 
		int pageSize = Integer.parseInt(request.getParameter("pagecount"));//10
		int offset=(currPage-1)*pageSize;
		List<CourseCatagory> courseCatagories=this.courseCatagoryMapper.getCourseCatagoryList(offset,pageSize);
		List<CourseCatagoryCount> courseCatagoryCounts=this.courseCatagoryMapper.getCourseCatagoryCount();
		// 将 ServletRequest 转换为 HttpServletRequest 才能拿到请求头中的 token
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        // 尝试获取请求头的 token
        String token = httpRequest.getHeader(this.tokenHeader);
        if(!token.isEmpty()) {
        	String username = this.tokenUtils.getUsernameFromToken(token);
        	if(username == null ) {
        		return new ResultMap().fail("401").message("token异常");
        	}
            List<GetHkid> getHkids=this.courseCatagoryMapper.getHkid(username);
        	for(CourseCatagory courseCatagory : courseCatagories) {
        		List<CourseCatagoryCount> orderCourses=this.courseCatagoryMapper.getOrderCount(getHkids.get(0).getHkid(),courseCatagory.getCcid());
	        	List<CourseCatagoryCount> courseCounts=this.courseCatagoryMapper.getCatagoryCourseCount(courseCatagory.getCcid());
//	        	if(orderCourses.get(0).getCount()==courseCounts.get(0).getCount()) {
//	        		courseCatagory.setIsbuy("1");
//	        		courseCatagory.setIscanbuy("0");
//	        	}else{
//	        		courseCatagory.setIsbuy("0");
//	        		if(orderCourses.get(0).getCount()==0) {
//	        			courseCatagory.setIscanbuy("1");
//	        		}else {
//	        			courseCatagory.setIscanbuy("0");
//	        		}
//	        		
//	        	}
	        	if(orderCourses.get(0).getCount() == 0 && courseCounts.get(0).getCount() != 0) {
	        		courseCatagory.setIscanbuy("1");
	        	}else {
	        		courseCatagory.setIscanbuy("0");
	        	}
        	}
        	
        }else {
        	for(CourseCatagory courseCatagory : courseCatagories) {
        		//courseCatagory.setIsbuy("0");
        		courseCatagory.setIscanbuy("1");
        	}
        }
		if(courseCatagories.size() != 0) {
			return new ResultMap().success().message("success").count(courseCatagoryCounts.get(0).getCount()).data(courseCatagories );		
		}else {
			return new ResultMap().success().message("没有更多数据");
		}	
	
	}
	//课程列表
	@RequestMapping(value="/course/courselist",method=RequestMethod.POST)
	public ResultMap courseList(HttpServletRequest request) {
		if(request.getParameter("pageindex")==null || request.getParameter("pagecount")==null) {
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		int currPage = Integer.parseInt(request.getParameter("pageindex"));//1 2 
		int pageSize = Integer.parseInt(request.getParameter("pagecount"));//10
		int offset=(currPage-1)*pageSize;
		List<Courses> courses=this.courseCatagoryMapper.getCourseList(request.getParameter("ccid"),offset,pageSize);
		List<CourseCatagoryCount> courseCounts=this.courseCatagoryMapper.getCourseCount(request.getParameter("ccid"));
		// 将 ServletRequest 转换为 HttpServletRequest 才能拿到请求头中的 token
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        // 尝试获取请求头的 token
        String authToken = httpRequest.getHeader(this.tokenHeader);
        if(authToken!=null) {
        	 String username = this.tokenUtils.getUsernameFromToken(authToken);
             List<GetHkid> getHkids=this.courseCatagoryMapper.getHkid(username);
            	for(Courses course: courses) {
               	 String cid=course.getCid();
               	 if(getHkids.size()!=0) {
               		List<CourseCatagoryCount> orderCount=this.courseCatagoryMapper.getOrderCourseCount(cid,getHkids.get(0).getHkid());
               		List<CourseCatagoryCount> orderLearnCourseCount=this.courseCatagoryMapper.getOrderLearnCourseCount(cid,getHkids.get(0).getHkid());
              		 if(orderCount.get(0).getCount()==0) {
                  		 course.setIsbuy(1);
                  	 }else{
                  		 course.setIsbuy(0);
                  	 }
              		if(orderLearnCourseCount.get(0).getCount()==0) {
                 		 course.setIslearn(0);
                 	 }else{
                 		 course.setIslearn(1);
                 	 }
               	 }else {
               		course.setIsbuy(1); 
               		course.setIslearn(0);
               	 }
              }           
        }else {
        	for(Courses course: courses) {
        		course.setIsbuy(1);
        		course.setIslearn(0);
        	}
        } 
        List<CourseCatagory> catagories=this.courseCatagoryMapper.getCourseCatagoryDetail(request.getParameter("ccid"));
        List<CourseCatagoryCount> catagoryTestCounts=this.courseCatagoryMapper.getExamCount(request.getParameter("ccid"));
        if(catagoryTestCounts.get(0).getCount()==0) {
        	catagories.get(0).setIstest("0");
        }else {
        	catagories.get(0).setIstest("1");
        }
       
		if(courses.size() != 0) {
			return new ResultMap().success().message("success").count(courseCounts.get(0).getCount()).ortherdata(catagories).data(courses);		
		}else {
			return new ResultMap().success().message("没有更多数据").ortherdata(catagories).ortherdata(catagories);
		}
	}

	
	//添加课程模块
	@RequestMapping(value="/course/addcoursecatagory",method=RequestMethod.POST)
	public ResultMap addCourseCatagory(HttpServletRequest request) {
		String description=request.getParameter("description");
		String ismust=request.getParameter("ismust");
		String sort=request.getParameter("sort");
		String name=request.getParameter("name");
		String picture=request.getParameter("picture");
		String discount=request.getParameter("discount");
		String aid=request.getParameter("aid");
		if(description==null || ismust==null || sort==null || name==null || picture==null || aid==null) {
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		try {
			int result=this.courseCatagoryMapper.addCourseCatagory(name,sort,description,ismust,request.getParameter("create_at"),picture,discount,aid);
			if(result==1) {
				return new ResultMap().success().message("添加成功");
			}else {
				return new ResultMap().fail("1").message("添加失败");
			}
		} catch (Exception e) {
			return new ResultMap().fail("1").message("添加失败");
		}
	}
	//课程模块的详情
	@RequestMapping(value="/course/coursecatagorydetail",method=RequestMethod.POST)
	public ResultMap courseCatagoryDetail(HttpServletRequest request) {
		if(request.getParameter("ccid")==null) {
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		List<CourseCatagory> catagories=this.courseCatagoryMapper.getCourseCatagoryDetail(request.getParameter("ccid"));
		if(catagories.size()!=0) {
			return new ResultMap().success().message("success").data(catagories);
		}else {
			return new ResultMap().success().message("没有更多数据");
		}
	}
	//编辑课程模块
	@RequestMapping(value="/course/editcoursecatagory",method=RequestMethod.POST)
	public ResultMap editCourseCatagory(HttpServletRequest request) {
		String description=request.getParameter("description");
		String ismust=request.getParameter("ismust");
		String sort=request.getParameter("sort");
		String name=request.getParameter("name");
		String ccid=request.getParameter("ccid");
		String picture=request.getParameter("picture");
		String discount=request.getParameter("discount");
		String aid=request.getParameter("aid");
		if(description==null || ismust==null || sort==null || name==null || ccid==null || picture==null || aid==null) {
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		try {
			int result=this.courseCatagoryMapper.editCourseCatagory(ccid,name,sort,description,ismust,request.getParameter("update_at"),picture,discount,aid);
			if(result==1) {
				return new ResultMap().success().message("编辑成功");
			}else {
				return new ResultMap().fail("1").message("编辑失败");
			}
		} catch (Exception e) {
			return new ResultMap().fail("1").message("编辑失败");
		}
	
	}
	//删除课程模块
	@RequestMapping(value="/course/deletecoursecatagory",method=RequestMethod.POST)
	public ResultMap deleteCourseCatagory(HttpServletRequest request) {
		String ccid=request.getParameter("ccid");
		if(ccid==null) {
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		try {
			int result=this.courseCatagoryMapper.deleteCourseCatagory(ccid,request.getParameter("update_at"));
			if(result==1) {
				return new ResultMap().success().message("删除成功");
			}else {
				return new ResultMap().fail("1").message("删除失败");
			}
		} catch (Exception e) {
			return new ResultMap().fail("1").message("删除失败");
		}
		
	}
	//添加课程
	@RequestMapping(value="/course/addcourse",method=RequestMethod.POST)
	public ResultMap addCourse(@Valid Courses course , BindingResult bindingResult) {
		 if (bindingResult.hasErrors()){
	            return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
	        }
		 try {
				int result=this.courseCatagoryMapper.addCourse(course);
				if(result==1) {
					return new ResultMap().success().message("添加成功");
				}else {
					return new ResultMap().fail("1").message("添加失败");
				}
			} catch (Exception e) {
				return new ResultMap().fail("1").message("添加失败");
			}
		
	}
	//编辑课程
	@RequestMapping(value="/course/editcourse",method=RequestMethod.POST)
	public ResultMap editCourse(@Valid Courses course ,BindingResult bindingResult,HttpServletRequest request) {
		 if (bindingResult.hasErrors()){
	            return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
	        }
		 if(request.getParameter("cid")==null){
			 return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
		 }
		 try {
				int result=this.courseCatagoryMapper.editCourse(course);
				if(result==1) {
					return new ResultMap().success().message("编辑成功");
				}else {
					return new ResultMap().fail("1").message("编辑失败");
				}
			} catch (Exception e) {
				return new ResultMap().fail("1").message("编辑失败");
			}
	}
	//删除课程
	@RequestMapping(value="/course/deletecourse",method=RequestMethod.POST)
	public ResultMap deleteCourse(HttpServletRequest request) {
		if(request.getParameter("cid")==null) {
			 return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
		}
		try {
			int result=this.courseCatagoryMapper.deleteCourse(request.getParameter("cid"),request.getParameter("created_at"));
			if(result==1) {
				return new ResultMap().success().message("删除成功");
			}else {
				return new ResultMap().fail("1").message("删除失败");
			}
		} catch (Exception e) {
			return new ResultMap().fail("1").message("删除失败");
		}
	}
	
	//我的课程列表
	@RequestMapping(value="course/mycourselist",method=RequestMethod.POST)
	public ResultMap myCourseList(@Valid MyCourse myCourse,BindingResult bindingResult,HttpServletRequest request) {
		if(bindingResult.hasErrors()) {
			 return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
        }
		if(request.getParameter("pageindex")==null || request.getParameter("pagecount")==null) {
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		 // 将 ServletRequest 转换为 HttpServletRequest 才能拿到请求头中的 token
        //HttpServletRequest httpRequest = (HttpServletRequest) request;
        // 尝试获取请求头的 token
        String token = request.getHeader(this.tokenHeader);
        // 尝试拿 token 中的 username
        // 若是没有 token 或者拿 username 时出现异常，那么 username 为 null
        String username = this.tokenUtils.getUsernameFromToken(token);
        List<GetHkid> getHkids=this.courseCatagoryMapper.getHkid(username);
		int currPage = Integer.parseInt(request.getParameter("pageindex"));//1 2 
		int pageSize = Integer.parseInt(request.getParameter("pagecount"));//10
		int offset=(currPage-1)*pageSize;
		System.out.println(getHkids.get(0).getHkid());
		List<CourseCatagoryCount> catagoryCounts=this.courseCatagoryMapper.getMyCourseCount(request.getParameter("ccid"),getHkids.get(0).getHkid());
		List<MyCourse> myCourses=this.courseCatagoryMapper.getMyCourseList(request.getParameter("ccid"),getHkids.get(0).getHkid(),offset,pageSize);
		if(myCourses.size() != 0) {
			return new ResultMap().success().message("success").count(catagoryCounts.get(0).getCount()).data(myCourses );		
		}else {
			return new ResultMap().success().message("没有更多数据");
		}
	}
	

	//课程模块考试内容
		@RequestMapping(value="course/gettestcontent",method=RequestMethod.POST)
		public ResultMap getTestContent(HttpServletRequest request) {
			if(request.getParameter("ccid")==null) {
				return new ResultMap().fail("400").message("缺少参数").data("");
			}
			List<CourseCatagoryCount>  multipleCounts=this.courseCatagoryMapper.getMultipleCount(request.getParameter("ccid"));
			List<CourseCatagoryCount>  singleCounts=this.courseCatagoryMapper.getSingleCount(request.getParameter("ccid"));
			Map<String, Integer> map=new HashMap<String,Integer>();
			int count=multipleCounts.get(0).getCount()+singleCounts.get(0).getCount();
			map.put("count", count);
			map.put("single", singleCounts.get(0).getCount());
			map.put("multiple", multipleCounts.get(0).getCount());
			return new ResultMap().success().message("success").data(map);
		}


	
	//考试列表
	@RequestMapping(value="course/gettestlist",method=RequestMethod.POST)
	public ResultMap getTestList(HttpServletRequest request) {
		 	String token = request.getHeader(this.tokenHeader);
	        // 尝试拿 token 中的 username
	        // 若是没有 token 或者拿 username 时出现异常，那么 username 为 null
	        String username = this.tokenUtils.getUsernameFromToken(token);
	        ArrayList<String> ccidArray=new ArrayList<String>();
	        List<GetHkid> getHkids=this.courseCatagoryMapper.getHkid(username);
	        List<CourseCatagory> catagories=this.courseCatagoryMapper.gettestCourseCatagory(getHkids.get(0).getHkid());
	        for(CourseCatagory catagory : catagories) {
	        	List<CourseCatagoryCount> orderCourses=this.courseCatagoryMapper.getOrderLearnCount(getHkids.get(0).getHkid(),catagory.getCcid());
	        	List<CourseCatagoryCount> courseCounts=this.courseCatagoryMapper.getCatagoryCourseCount(catagory.getCcid());
	        	if(orderCourses.get(0).getCount()==courseCounts.get(0).getCount()) {
	        		ccidArray.add(catagory.getCcid());
	        	}
	        }
	        List<CourseCatagory> testcatagories01=this.courseCatagoryMapper.gettestCatagory(ccidArray);
	        List<CourseCatagory> testcatagories = new ArrayList<CourseCatagory>() ; 
	        for(CourseCatagory entity : testcatagories01) {
	        	String ccid = entity.getCcid() ;
	        	List<CourseCatagoryCount> list = this.courseCatagoryMapper.getExamCount(ccid);
	        	if(list.get(0).getCount() != 0) {
	        		testcatagories.add(entity) ;
	        	}
	        }
	        return new ResultMap().success().message("success").data(testcatagories);
	     }
	//课程模块下拉
	@RequestMapping(value="/course/selectcoursecatagory",method=RequestMethod.POST)
	public ResultMap selectCourseCatagory(HttpServletRequest request){
		List<CourseCatagory> courseCatagories=this.courseCatagoryMapper.selectCourseCatagory();
		if(courseCatagories.size() != 0) {
			return new ResultMap().success().message("success").data(courseCatagories );		
		}else {
			return new ResultMap().success().message("没有更多数据");
		}	
	
	}

	//后台课程列表
	@RequestMapping(value="/course/admincourselist",method=RequestMethod.POST)
	public ResultMap adminCourseList(HttpServletRequest request) {
		if(request.getParameter("pageindex")==null || request.getParameter("pagecount")==null) {
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		String  ccid=request.getParameter("ccid");
		String  name=request.getParameter("name");
		int currPage = Integer.parseInt(request.getParameter("pageindex"));//1 2 
		int pageSize = Integer.parseInt(request.getParameter("pagecount"));//10
		int offset=(currPage-1)*pageSize;
		List<Courses> courses=this.courseCatagoryMapper.getAdminCourseList(ccid,name,offset,pageSize);
		List<CourseCatagoryCount> courseCounts=this.courseCatagoryMapper.getAdminCourseCount(ccid,name);
		if(courses.size() != 0) {
			for(Courses value : courses) {
				List<CourseCatagoryCount> orderCounts=this.courseCatagoryMapper.getcourseOrderCount(value.getCid());
				value.setOrdercount(orderCounts.get(0).getCount()+"");
			}
			return new ResultMap().success().message("success").count(courseCounts.get(0).getCount()).data(courses );		
		}else {
			return new ResultMap().success().message("没有更多数据");
		}
	}
	
	//匹配家政人员信息列表
	@RequestMapping(value="/housekeeper/matchhousekeeperlist",method=RequestMethod.POST)
	public ResultMap matchHouseKeeperList(HttpServletRequest request) {
		if(request.getParameter("matchcount")==null || request.getParameter("pageindex")==null || request.getParameter("pagecount")==null) {
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		HttpServletRequest httpRequest = (HttpServletRequest) request;
        String token = httpRequest.getHeader(this.tokenHeader);
        String username=this.tokenUtils.getUsernameFromToken(token);
        int currPage = Integer.parseInt(request.getParameter("pageindex"));//1 2 
		int pageSize = Integer.parseInt(request.getParameter("pagecount"));//10
		int offset=(currPage-1)*pageSize;
		List<HouseKeeper> houseKeepers;
		List<CourseCatagoryCount> houseKeeperCount;
		if(request.getParameter("matchcount").equals("0") || request.getParameter("matchcount")==null) {
			houseKeepers=this.courseCatagoryMapper.getHouseKeeperList(offset,pageSize);
			houseKeeperCount=this.courseCatagoryMapper.getHouseKeeperListCount();
		}else {
			 houseKeepers=this.courseCatagoryMapper.getHouseKeeper(username,offset,pageSize,request.getParameter("matchcount"));
		     houseKeeperCount=this.courseCatagoryMapper.getHouseKeeperCount(username,request.getParameter("matchcount"));
		}
        for(HouseKeeper value : houseKeepers) {
        	List<CourseCatagoryCount> getLikeCount=this.courseCatagoryMapper.getLikeCount(value.getHkid()+"");
        	if(getLikeCount.get(0).getCount()!=0) {
        		value.setLikeCount(Integer.toString(getLikeCount.get(0).getCount()));
        	}else {
        		value.setLikeCount("0");
        	}
        	List<CourseCatagoryCount> isLike=this.courseCatagoryMapper.getIsLike(value.getHkid()+"",username);
        	if(isLike.get(0).getCount()!=0) {
        		value.setIsLike("1");
        	}else {
        		value.setIsLike("0");
        	}
        	
        }
        if(houseKeepers.size()!=0) {
        	return new ResultMap().success().message("success").count(houseKeeperCount.get(0).getCount()).data(houseKeepers );
        }else {
        	return new ResultMap().success().message("没有更多数据");
        }
        
	}
	
		//搜索课程模块
		@RequestMapping(value="/course/serachcoursecatagorylist",method=RequestMethod.POST)
		public ResultMap searchCourseCatagoryList(HttpServletRequest request) {
			// userMapper.registerLoginEmployer(requestLoginUser01.getUsername(),password ) ;
			if(request.getParameter("pageindex")==null || request.getParameter("pagecount")==null || request.getParameter("name")==null) {
				return new ResultMap().fail("400").message("缺少参数").data("");
			}
			int currPage = Integer.parseInt(request.getParameter("pageindex"));//1 2 
			int pageSize = Integer.parseInt(request.getParameter("pagecount"));//10
			int offset=(currPage-1)*pageSize;
			List<CourseCatagory> courseCatagories=this.courseCatagoryMapper.serachCourseCatagoryList(offset,pageSize,request.getParameter("name"));
			List<CourseCatagoryCount> courseCatagoryCounts=this.courseCatagoryMapper.seachCourseCatagoryCount(request.getParameter("name"));
			// 将 ServletRequest 转换为 HttpServletRequest 才能拿到请求头中的 token
	        HttpServletRequest httpRequest = (HttpServletRequest) request;
	        // 尝试获取请求头的 token
	        String token = httpRequest.getHeader(this.tokenHeader);
	        if(!token.isEmpty()) {
	        	String username = this.tokenUtils.getUsernameFromToken(token);
	        	if(username == null ) {
	        		return new ResultMap().fail("401").message("token异常");
	        	}
	            List<GetHkid> getHkids=this.courseCatagoryMapper.getHkid(username);
	        	for(CourseCatagory courseCatagory : courseCatagories) {
	        		List<CourseCatagoryCount> orderCourses=this.courseCatagoryMapper.getOrderCount(getHkids.get(0).getHkid(),courseCatagory.getCcid());
		        	List<CourseCatagoryCount> courseCounts=this.courseCatagoryMapper.getCatagoryCourseCount(courseCatagory.getCcid());
		        	if(orderCourses.get(0).getCount() == 0 && courseCounts.get(0).getCount() != 0) {
		        		courseCatagory.setIscanbuy("1");
		        	}else {
		        		courseCatagory.setIscanbuy("0");
		        	}
		    	}
		    	
		    }else {
			    	for(CourseCatagory courseCatagory : courseCatagories){
			    		courseCatagory.setIscanbuy("1");
			    	}
		        }
			if(courseCatagories.size() != 0) {
				return new ResultMap().success().message("success").count(courseCatagoryCounts.get(0).getCount()).data(courseCatagories );		
			}else {
				return new ResultMap().success().message("没有更多数据");
			}	
		}

		
		//课程详情页
		@RequestMapping(value="/course/getcoursedetail",method=RequestMethod.POST)
		public ResultMap getCourseDetail(HttpServletRequest request) {
			if(request.getParameter("cid")==null) {
				return new ResultMap().fail("400").message("缺少参数").data("");
			}
			int addViewCount=this.courseCatagoryMapper.addViewCount(request.getParameter("cid"));
			List<Courses> courseDetail=this.courseCatagoryMapper.getCourseDetail(request.getParameter("cid"));
			// 将 ServletRequest 转换为 HttpServletRequest 才能拿到请求头中的 token
	        HttpServletRequest httpRequest = (HttpServletRequest) request;
	        // 尝试获取请求头的 token
	        String token = httpRequest.getHeader(this.tokenHeader);
	        if(!token.isEmpty()) {
	        	String username = this.tokenUtils.getUsernameFromToken(token);
	        	if(username == null ) {
	        		return new ResultMap().fail("401").message("token异常");
	        	}
	            List<GetHkid> getHkids=this.courseCatagoryMapper.getHkid(username);
	            List<CourseCatagoryCount> orderCount=this.courseCatagoryMapper.getOrderCourseCount(request.getParameter("cid"),getHkids.get(0).getHkid());       
	            for(Courses value : courseDetail) {
	            	if(orderCount.get(0).getCount()==0) {
	            		 value.setIsbuy(1);
	            	 }else{
	            		 value.setIsbuy(0);
	            	 }
		    	}
	            List<CourseCatagoryCount> courseLearn=this.courseCatagoryMapper.getCourseLearn(request.getParameter("cid"),getHkids.get(0).getHkid());
	           if(orderCount.get(0).getCount()!=0) {
	        	   if(courseLearn.get(0).getCount()!=1) {
	            	  this.courseCatagoryMapper.changeLearn(request.getParameter("cid"),getHkids.get(0).getHkid());
	            	  
	               }
	           }else {
	        	   if(courseDetail.get(0).getMoney().equals("0.00")) {
	        		  this.courseCatagoryMapper.addCourseLearn(request.getParameter("cid"),getHkids.get(0).getHkid());	 
	        	   	} 
           }
	    }else {
		    	for(Courses value : courseDetail) {
		    		value.setIsbuy(1);
		    	}
	        }
			if(courseDetail.size() !=0) {
				return new ResultMap().success().message("success").data(courseDetail);
			}else {
				return new ResultMap().success().message("没有更多数据");
			}
		}
		

	
	
	
	
	
	
	
	
	
	
}
