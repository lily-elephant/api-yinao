package yinao.qualityLife.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jayway.jsonpath.internal.filter.ValueNode.UndefinedNode;

import yinao.qualityLife.dao.adminUserMapper;
import yinao.qualityLife.model.ResultMap;
import yinao.qualityLife.model.TokenDetail;
import yinao.qualityLife.model.domain.AdminUser;
import yinao.qualityLife.model.domain.CourseCatagoryCount;
import yinao.qualityLife.model.domain.Department;
import yinao.qualityLife.model.domain.Permission;
import yinao.qualityLife.model.domain.Role;
import yinao.qualityLife.utils.TokenUtils;

@RestController
public class adminUserController {
	private final adminUserMapper adminUsermapper;
	private final TokenUtils tokenUtils;
	private static int authResult;
	public adminUserController(adminUserMapper adminUsermapper,TokenUtils tokenUtils) {
		super();
		this.adminUsermapper = adminUsermapper;
		this.tokenUtils=tokenUtils;
	}
	//用户列表
	@RequestMapping(value="/admin/userlist",method=RequestMethod.POST)
	public ResultMap userList(HttpServletRequest request){
		if(request.getParameter("pageindex")==null || request.getParameter("pagecount")==null) {
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		int currPage = Integer.parseInt(request.getParameter("pageindex"));//1 2 
		int pageSize = Integer.parseInt(request.getParameter("pagecount"));//10
		int offset=(currPage-1)*pageSize;
		String phone=request.getParameter("phone");
		String name=request.getParameter("name");
		String department=request.getParameter("department");
		List<CourseCatagoryCount> userCounts=this.adminUsermapper.getUserCount(phone,name,department);
		List<AdminUser> adminUsers=this.adminUsermapper.getUserList(phone,name,department,offset,pageSize);
		if(adminUsers.size() != 0) {
			return new ResultMap().success().message("success").count(userCounts.get(0).getCount()).data(adminUsers);
		}else {
			return new ResultMap().success().message("没有更多数据").data("");
		}
	}
	//添加用户
	@RequestMapping(value="/admin/adduser",method=RequestMethod.POST)
	public ResultMap addUser(@Valid AdminUser adminUser,BindingResult bindingResult,HttpServletRequest request ){
		if(bindingResult.hasErrors()){
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		if(request.getParameter("password")==null) {
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		Md5PasswordEncoder md5= new Md5PasswordEncoder();
    	md5.setEncodeHashAsBase64(false);
        String password= md5.encodePassword(request.getParameter("password"),request.getParameter("phone"));
        adminUser.setPassword(password);
		List<CourseCatagoryCount> userCount=this.adminUsermapper.getPhoneCount(adminUser.getPhone());
		if(userCount.get(0).getCount()==0) {
			try {
				int result=this.adminUsermapper.addUser(adminUser);
				if(result==1) {
					int insertResult=this.adminUsermapper.addUserRole(adminUser.getRoleid(),adminUser.getUserid());
					if(insertResult==1) {
						 return new ResultMap().success().message("添加成功");
					}else {
						 return new ResultMap().fail("1").message("添加失败");
					}
				}else {
					 return new ResultMap().fail("1").message("添加失败");
				}
			} catch (Exception e) {
				 return new ResultMap().fail("1").message("添加失败");
			}
		}else {
			 return new ResultMap().fail("1").message("已添加");
		}
	}
	//编辑用户
	@RequestMapping(value="admin/updateuser",method=RequestMethod.POST)
	public ResultMap updateUser(HttpServletRequest request) {
		if(request.getParameter("userid")==null) {
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		List<AdminUser> adminUsers=this.adminUsermapper.getSingleUser(request.getParameter("userid"));
		if(adminUsers.size()!=0) {
			return new ResultMap().success().message("success").data(adminUsers);
		}else {
			return new ResultMap().success().message("没有更多数据").data("");
		}
	}
	//保存编辑的用户信息
	@RequestMapping(value="/admin/saveupdateuser",method=RequestMethod.POST)
	public ResultMap saveUpdateUser(@Valid AdminUser adminUser,BindingResult bindingResult,HttpServletRequest request ){
		if(bindingResult.hasErrors()){
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		if(request.getParameter("userid")==null) {
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		try {
			int result=this.adminUsermapper.saveUpdateUser(adminUser);
			if(result==1) {
				int insertResult=this.adminUsermapper.saveUserRole(adminUser.getRoleid(),adminUser.getUserid());
				if(insertResult==1) {
					 return new ResultMap().success().message("编辑成功");
				}else {
					 return new ResultMap().fail("1").message("编辑失败");
				}
			}else {
				 return new ResultMap().fail("1").message("编辑失败");
			}
		} catch (Exception e) {
			 return new ResultMap().fail("1").message("编辑失败");
		}	
	}
	//删除用户
	@RequestMapping(value="/admin/deleteuser",method=RequestMethod.POST)
	public ResultMap deleteUser(HttpServletRequest request) {
		if(request.getParameter("userid")==null) {
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		try {
			int result=this.adminUsermapper.deleteUser(request.getParameter("userid"));
			if(result==1) {
				 return new ResultMap().success().message("删除成功");
			}else {
				return new ResultMap().fail("1").message("删除失败");
			}
			
		} catch (Exception e) {
			return new ResultMap().fail("1").message("删除失败");
		}
	}
	//部门列表
	@RequestMapping(value="/admin/departmentlist",method=RequestMethod.POST)
	public ResultMap departmentList(HttpServletRequest request) {
		if(request.getParameter("pageindex")==null || request.getParameter("pagecount")==null) {
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		int currPage = Integer.parseInt(request.getParameter("pageindex"));//1 2 
		int pageSize = Integer.parseInt(request.getParameter("pagecount"));//10
		int offset=(currPage-1)*pageSize;
		List<CourseCatagoryCount> catagoryCounts=this.adminUsermapper.getDepartmentCount();
		List<Department> departments=this.adminUsermapper.departmentList(offset,pageSize);
		if(departments.size()!=0) {
			return new ResultMap().success().message("success").count(catagoryCounts.get(0).getCount()).data(departments);
		}else {
			return new ResultMap().success().message("没有更多数据").data("");
		}
	}
	//添加部门
	@RequestMapping(value="/admin/adddepartment",method=RequestMethod.POST)
	public ResultMap addDepartment(@Valid Department department,BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		List<CourseCatagoryCount> departmentCounts=this.adminUsermapper.getDepartmentCounts(department);
		if(departmentCounts.get(0).getCount()==0) {
			try {
				int result=this.adminUsermapper.addDepartment(department);
				if(result==1) {
					 return new ResultMap().success().message("添加成功");
				}else {
					 return new ResultMap().fail("1").message("添加失败");
				}
			} catch (Exception e) {
				 return new ResultMap().fail("1").message("添加失败");
			}
		}else {
			return new ResultMap().fail("1").message("已添加");
		}
		
		
	}
	//获取部门详情
	@RequestMapping(value="/admin/updatepartment",method=RequestMethod.POST)
	public ResultMap updatePartment(HttpServletRequest request) {
		if(request.getParameter("id")==null) {
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		List<Department> departments=this.adminUsermapper.getSingleDepartment(request.getParameter("id"));
		if(departments.size()!=0) {
			return new ResultMap().success().message("success").data(departments);
		}else {
			return new ResultMap().success().message("没有更多数据").data("");
		}
	}
	//保存部门编辑的信息
	@RequestMapping(value="/admin/saveupdatepartment",method=RequestMethod.POST)
	public ResultMap saveUpdateDepartment(@Valid Department department,BindingResult bindingResult,HttpServletRequest request) {
		if(request.getParameter("id")==null) {
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		if(bindingResult.hasErrors()){
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		
		try {
			int  result=this.adminUsermapper.saveUpdateDepartment(department);
			if(result==1) {
				 return new ResultMap().success().message("编辑成功");
			}else {
				 return new ResultMap().fail("1").message("编辑失败");
			}
		} catch (Exception e) {
			 return new ResultMap().fail("1").message("编辑失败");
		}
	}
	//删除部门
	@RequestMapping(value="/admin/deleteparament",method=RequestMethod.POST)
	public ResultMap deleteParament(HttpServletRequest request) {
		if(request.getParameter("id")==null) {
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		List<CourseCatagoryCount> departmetnUserCounts=this.adminUsermapper.departmentUserCount(request.getParameter("id"));
		System.out.println(departmetnUserCounts.get(0).getCount());
		if(departmetnUserCounts.get(0).getCount()==0) {
			try {
				int result=this.adminUsermapper.deletePartment(request.getParameter("id"));
				if(result==1) {
					 return new ResultMap().success().message("删除成功");
				}else {
					 return new ResultMap().fail("1").message("删除失败");
				}
			} catch (Exception e) {
				 return new ResultMap().fail("1").message("删除失败");
			}
			
		}else {
			return new ResultMap().fail("201").message("暂不能删除").data("");
		}
	}
	//角色列表
	@RequestMapping(value="/admin/rolelist",method=RequestMethod.POST)
	public ResultMap roleList(HttpServletRequest request) {
		if(request.getParameter("pageindex")==null || request.getParameter("pagecount")==null) {
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		int currPage = Integer.parseInt(request.getParameter("pageindex"));//1 2 
		int pageSize = Integer.parseInt(request.getParameter("pagecount"));//10
		int offset=(currPage-1)*pageSize;
		List<CourseCatagoryCount> roleCounts=this.adminUsermapper.getRoleCounts();
		List<Role> roles=this.adminUsermapper.roleList(offset,pageSize);
		if(roles.size()!=0) {
			return new ResultMap().success().message("success").count(roleCounts.get(0).getCount()).data(roles);
		}else {
			return new ResultMap().success().message("没有更多数据").data("");
		}
	
	}
	//新增角色
	@RequestMapping(value="/admin/addrole",method=RequestMethod.POST)
	public ResultMap addRole(@Valid Role role,BindingResult bindingResult,HttpServletRequest request){
		if(bindingResult.hasErrors()) {
			return new ResultMap().fail("400").message("缺少参数").data(""); 
		}
		try {
			int result=this.adminUsermapper.addRole(role);
			if(result==1) {
				 return new ResultMap().success().message("添加成功");
			}else {
				 return new ResultMap().fail("1").message("添加失败");
			}	
		} catch (Exception e) {
			return new ResultMap().fail("1").message("添加失败");
		}
	}
	//角色详情
	@RequestMapping(value="/admin/updaterole",method=RequestMethod.POST)
	public ResultMap updateRole(HttpServletRequest request) {
		if(request.getParameter("roleid")==null) {
			return new ResultMap().fail("400").message("缺少参数").data(""); 
		}
		List<Role> singleRoles=this.adminUsermapper.getSingleRole(request.getParameter("roleid"));
		if(singleRoles.size()!=0) {
			return new ResultMap().success().message("success").data(singleRoles);
		}else {
			return new ResultMap().success().message("没有更多数据").data("");
		}
	}
	//保存编辑的角色
	@RequestMapping(value="/admin/saveupdaterole",method=RequestMethod.POST)
	public ResultMap saveUpdateRole(@Valid Role role,BindingResult bindingResult,HttpServletRequest request) {
		if(request.getParameter("roleid")==null) {
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		if(bindingResult.hasErrors()){
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		try {
			int result=this.adminUsermapper.saveUpdateRole(role);
			if(result==1) {
				 return new ResultMap().success().message("编辑成功");
			}else {
				 return new ResultMap().fail("1").message("编辑失败");	
			}
		} catch (Exception e) {
			 return new ResultMap().fail("1").message("编辑失败");
		}	
	}
	//删除角色
	@RequestMapping(value="/admin/deleterole",method=RequestMethod.POST)
	public ResultMap deleteRole(HttpServletRequest request) {
		if(request.getParameter("roleid")==null) {
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		List<CourseCatagoryCount> roleUserCounts=this.adminUsermapper.roleUserCount(request.getParameter("roleid"));
		if(roleUserCounts.get(0).getCount()==0) {
			try {
				int result=this.adminUsermapper.deleteRole(request.getParameter("roleid"));
				if(result==1) {
					 return new ResultMap().success().message("删除成功");
				}else {
					 return new ResultMap().fail("1").message("删除失败");	
				}
			} catch (Exception e) {
				return new ResultMap().fail("1").message("删除失败");
			}
		}else {
			return new ResultMap().fail("201").message("暂不能删除").data("");
		}
	}
	//角色权限列表
	@RequestMapping(value="/admin/roleauth",method=RequestMethod.POST)
	public ResultMap roleAuth(HttpServletRequest request) {
		if(request.getParameter("roleid")==null) {
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		List<Permission> parentAuths=this.adminUsermapper.getParentAuths();
		for(Permission parentAuth:parentAuths) {
			String pid=parentAuth.getPermissionid();
			List<Permission> childAuths=this.adminUsermapper.getChildAuths(pid);
			List<Permission> roleAuths=this.adminUsermapper.getRoleAuths(request.getParameter("roleid"),pid);
			parentAuth.setChildren(childAuths);
			ArrayList<String> authArray=new ArrayList<String>();
			for(Permission value : roleAuths) {
				authArray.add(value.getPermissionid());
			}
			parentAuth.setRoleauth(authArray);
		}
		return new ResultMap().success().message("success").data(parentAuths);
	}
	//修改角色权限
	@RequestMapping(value="/admin/saveroleauth",method=RequestMethod.POST)
	public ResultMap saveRoleAuth(HttpServletRequest request) {
		if(request.getParameter("roleid")==null || request.getParameter("auths")==null) {
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		String[] authArray= {};
		String auths=request.getParameter("auths");
		//authArray=auths.substring(1, auths.length()-1).split(",");
		authArray=auths.split(",");
		List<CourseCatagoryCount> roleAuthCount=this.adminUsermapper.getRoleAuthCount(request.getParameter("roleid"));
		try {
			if(roleAuthCount.get(0).getCount()!=0) {
				int result=this.adminUsermapper.deleteRoleAuth(request.getParameter("roleid"));
				if(result!=0) {
					for(String  value:authArray) {
						 authResult=this.adminUsermapper.addRoleAuth(request.getParameter("roleid"),Integer.parseInt(value));
					}
					if(authResult!=0) {
						 return new ResultMap().success().message("修改成功");
					}else {
						return new ResultMap().fail("1").message("修改失败");
					}	
				}else {
					return new ResultMap().fail("1").message("修改失败");
				}
			}else {
				for(String  value:authArray) {
					 authResult=this.adminUsermapper.addRoleAuth(request.getParameter("roleid"),Integer.parseInt(value));
					
				}
				if(authResult!=0) {
					 return new ResultMap().success().message("修改成功");
				}else {
					return new ResultMap().fail("1").message("修改失败");
				}
			}	
		} catch (Exception e) {
			return new ResultMap().fail("1").message("修改失败");
		}
		
	}
	//部门下拉框
		@RequestMapping(value="/admin/selectdepartment",method=RequestMethod.POST)
		public ResultMap selectDepartment(HttpServletRequest request) {
			List<Department> departments=this.adminUsermapper.selectDepartment();
			if(departments.size()!=0) {
				return new ResultMap().success().message("success").data(departments);
			}else {
				return new ResultMap().success().message("没有更多数据").data("");
			}
		}
		//角色下拉框
		@RequestMapping(value="/admin/selectrole",method=RequestMethod.POST)
		public ResultMap selectRole(HttpServletRequest request) {
			List<Role> roles=this.adminUsermapper.selectRole();
			if(roles.size()!=0) {
				return new ResultMap().success().message("success").data(roles);
			}else {
				return new ResultMap().success().message("没有更多数据").data("");
			}
		}
		//用户登录
		
		@RequestMapping(value="/admin/login",method=RequestMethod.POST)
		public  ResultMap login(HttpServletRequest request) {
			if(request.getParameter("phone")==null || request.getParameter("password")==null) {
				return new ResultMap().fail("400").message("缺少参数").data("");
			}
			String phone=request.getParameter("phone");
			Md5PasswordEncoder md5= new Md5PasswordEncoder();
	    	md5.setEncodeHashAsBase64(false);
	        String password= md5.encodePassword(request.getParameter("password"),request.getParameter("password"));
			List<CourseCatagoryCount> phoneCount=this.adminUsermapper.getLoginPhoneCount(phone,password);
			if(phoneCount.get(0).getCount()==1) {
				List<AdminUser> adminUsers=this.adminUsermapper.getAdminUser(phone,password);
				String token=tokenUtils.generateToken(adminUsers.get(0).getPhone(),"Admin");
				adminUsers.get(0).setToken(token);
				return new ResultMap().success().message("success").data(adminUsers);
			}else {
				return new ResultMap().fail("1").message("账号或密码错误");
			}
			
		}
		//获取用户权限
		@RequestMapping(value="/admin/getuserauth",method=RequestMethod.POST)
		public ResultMap getUserAuth(HttpServletRequest request) {
			if(request.getParameter("userid")==null) {
				return new ResultMap().fail("400").message("缺少参数").data("");
			}
			List<Permission> permissionsParents=this.adminUsermapper.getUserAuth(request.getParameter("userid"));
			List<Permission> permissionsChild=this.adminUsermapper.getUserAuthChild(request.getParameter("userid"));
			if(permissionsParents.size()!=0) {
				for(Permission value : permissionsParents) {
					ArrayList<Permission> userAuths=new ArrayList<Permission>();
					if(value.getLevel().equals("1")) {
						for(Permission value1 : permissionsChild) {
							if(value1.getPid().equals(value.getPermissionid())) {
								userAuths.add(value1);
							}
						}
					}
					value.setUserauth(userAuths);
				}
				return new ResultMap().success().message("success").data(permissionsParents);
			}else {
				return new ResultMap().success().message("没有更多数据").data("");
			}
		}
		
		//获取用户权限url列表
		@RequestMapping(value="/admin/getuserpath",method=RequestMethod.POST)
		public ResultMap getUserPath(HttpServletRequest request) {
			if(request.getParameter("userid")==null) {
				return new ResultMap().fail("400").message("缺少参数").data("");
			}
			List<Permission> permissions=this.adminUsermapper.getUserPath(request.getParameter("userid"));
			ArrayList<String > userAuthPaths=new ArrayList<String>();
			for(Permission value : permissions) {
				if(value.getUrl() !=null && !value.getUrl().equals("")) {
					userAuthPaths.add(value.getUrl());
				}
			}
			return new ResultMap().success().message("success").data(userAuthPaths);
		}
		
		//修改密码
		@RequestMapping(value="/admin/updatepwd",method=RequestMethod.POST)
		public ResultMap updatePwd(HttpServletRequest request) {
			if(request.getParameter("phone")==null || request.getParameter("password")==null || request.getParameter("oldpassword")==null ) {
				return new ResultMap().fail("400").message("缺少参数").data("");
			}
			Md5PasswordEncoder md5= new Md5PasswordEncoder();
	    	md5.setEncodeHashAsBase64(false);
	    	String oldPassWord=md5.encodePassword(request.getParameter("oldpassword"),request.getParameter("phone"));
			List<AdminUser> oldpwds=this.adminUsermapper.getOldPwd(request.getParameter("phone"));
			if(oldpwds.get(0).getPassword().equals(oldPassWord)) {
				String password= md5.encodePassword(request.getParameter("password"),request.getParameter("phone"));
		        int results=this.adminUsermapper.updatePwd(password,request.getParameter("phone"));
		        if(results==1) {
		        	 return new ResultMap().success().message("修改成功");
				}else {
					return new ResultMap().fail("1").message("修改失败");
		        	
		        }
			}else {
				return new ResultMap().fail("1").message("原密码错误");
			}
			
	        
		}
		
		//重置密码
		@RequestMapping(value="/admin/resetpwd",method=RequestMethod.POST)
		public ResultMap resetPwd(HttpServletRequest request) {
			if(request.getParameter("phone")==null || request.getParameter("password")==null) {
				return new ResultMap().fail("400").message("缺少参数").data("");
			}
			Md5PasswordEncoder md5= new Md5PasswordEncoder();
	    	md5.setEncodeHashAsBase64(false);
			String password= md5.encodePassword(request.getParameter("password"),request.getParameter("phone"));
	        int results=this.adminUsermapper.updatePwd(password,request.getParameter("phone"));
	        if(results==1) {
	        	 return new ResultMap().success().message("重置成功");
			}else {
				return new ResultMap().fail("1").message("重置失败");
	        	
	        }
		}
	
}
