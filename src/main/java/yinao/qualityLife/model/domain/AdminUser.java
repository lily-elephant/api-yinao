package yinao.qualityLife.model.domain;

import javax.validation.constraints.NotNull;

public class AdminUser {
	private int userid;
	@NotNull
	private String name;
	@NotNull
	private String phone;
	private String department;
	@NotNull
	private String departmentid;
	private String position;
	private String role;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	private String token;
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	@NotNull
	private String roleid;
	private String entrytime;
	private String  sex;
	
	private String  password;
	
	public String getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public AdminUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getEntrytime() {
		return entrytime;
	}
	public void setEntrytime(String entrytime) {
		this.entrytime = entrytime;
	}
	

	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Override
	public String toString() {
		return "AdminUser[userid=" + userid +
				", name=" + name +
				", password=" + password +
				", phone=" + phone +
				", department=" + department +
				", departmentid=" + departmentid +
				", position=" + position +
				", role=" + role +
				", token=" + token +
				", roleid=" + roleid +
				", sex=" + sex +
				", entrytime=" + entrytime +"]";
	}
}
