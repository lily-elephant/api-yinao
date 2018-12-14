package yinao.qualityLife.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import yinao.qualityLife.model.domain.AdminUser;
import yinao.qualityLife.model.domain.CourseCatagoryCount;
import yinao.qualityLife.model.domain.Department;
import yinao.qualityLife.model.domain.Permission;
import yinao.qualityLife.model.domain.Role;

@Mapper
@Component
public interface adminUserMapper {
	//用户列表
	public  List<AdminUser> getUserList(@Param("phone") String phone,@Param("name") String name,@Param("department") String department,@Param("offset") int offset,@Param("pageSize") int pageSize);
	//用户总数
	public List<CourseCatagoryCount> getUserCount(@Param("phone") String phone,@Param("name") String name,@Param("department") String department);
	//是否已添加用户
	public List<CourseCatagoryCount> getPhoneCount(@Param("phone") String phone);
	//添加用户
	public int addUser(AdminUser adminUser);
	public int addUserRole(@Param("roleid") String roleid,@Param("userid") int userid);
	//编辑用户
	public List<AdminUser> getSingleUser(@Param("userid") String userid);
	//保存用户编辑信息
	public int saveUpdateUser(AdminUser adminUser);
	public int saveUserRole(@Param("roleid") String roleid,@Param("userid") int userid);
	//删除用户
	public int deleteUser(@Param("userid") String userid);
	//部门列表
	public List<Department> departmentList(@Param("offset") int offset,@Param("pageSize") int pageSize);
	//部门记录总数
	public List<CourseCatagoryCount> getDepartmentCount();
	//添加部门信息
	public List<CourseCatagoryCount> getDepartmentCounts(Department department);
	public int addDepartment(Department department);
	//编辑部门信息
	public List<Department> getSingleDepartment(@Param("id") String id);
	//保存编辑的部门信息
	public int saveUpdateDepartment(Department department);
	//部门是否有用户存在
	public List<CourseCatagoryCount> departmentUserCount(@Param("id") String id);
	//删除部门
	public  int deletePartment(@Param("id") String id);
	//角色列表
	public List<Role> roleList(@Param("offset") int offset,@Param("pageSize") int pageSize);
	//角色总数
	public List<CourseCatagoryCount> getRoleCounts();
	//添加角色
	public int addRole(Role role);
	//获取角色详情
	public List<Role> getSingleRole(@Param("roleid") String roleid);
	//保存角色编辑信息
	public int saveUpdateRole(Role role);
	//角色下的用户
	public List<CourseCatagoryCount> roleUserCount(@Param("roleid") String roleid);
	//删除角色
	public int deleteRole(@Param("roleid") String roleid);
	//获取权限列表
	public List<Permission> getParentAuths();
	public List<Permission> getChildAuths(@Param("pid") String pid);
	public List<Permission> getRoleAuths(@Param("roleid") String roleid,@Param("pid") String pid);
	//获取角色拥有的权限
	public List<CourseCatagoryCount> getRoleAuthCount(@Param("roleid") String rolied);
	//删除角色原有的权限
	public int deleteRoleAuth(@Param("roleid") String roleid);
	//新增角色权限
	public int addRoleAuth(@Param("roleid") String roleid,@Param("value") int  value);
	//部门下拉列表
	public List<Department> selectDepartment();
	//角色下拉列表
	public List<Role> selectRole();
	//后台登录验证
	public List<CourseCatagoryCount> getLoginPhoneCount(@Param("phone") String phone,@Param("password") String password);
	//获取登录信息
	public List<AdminUser> getAdminUser(@Param("phone") String phone,@Param("password") String password);
	//获取用户一级权限
	public List<Permission> getUserAuth(@Param("userid") String userid);
	//获取用户二级权限
	public List<Permission> getUserAuthChild(@Param("userid") String userid);
	//获取用户权限id
	public List<Permission> getUserPath(@Param("userid") String userid);
	//获取原密码
	public List<AdminUser> getOldPwd(@Param("phone") String phone);
	//修改密码
	public int updatePwd(@Param("password") String password,@Param("phone") String phone);
	
	
	
}
