package yinao.qualityLife.model.domain;

import java.util.ArrayList;
import java.util.List;

public class Permission {

	private String permissionid;
	private String permissionname;
	private String level;
	private String url;	
	private String classes;
	private String pid;
	private String index;
	private  List<Permission> children;
	private ArrayList<String > roleauth;
	private ArrayList<Permission> userauth;
	
	
	public ArrayList<String> getRoleauth() {
		return roleauth;
	}
	public void setRoleauth(ArrayList<String> roleauth) {
		this.roleauth = roleauth;
	}
	public List<Permission> getChildren() {
		return children;
	}
	public void setChildren(List<Permission> children) {
		this.children = children;
	}
	public Permission() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getPermissionid() {
		return permissionid;
	}
	public void setPermissionid(String permissionid) {
		this.permissionid = permissionid;
	}
	public String getPermissionname() {
		return permissionname;
	}
	public void setPermissionname(String permissionname) {
		this.permissionname = permissionname;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes = classes;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	public ArrayList<Permission> getUserauth() {
		return userauth;
	}
	public void setUserauth(ArrayList<Permission> userauth) {
		this.userauth = userauth;
	}
	
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	@Override
	public String toString() {
		return "Role[permissionid=" + permissionid +
				", permissionname=" + permissionname +
				", level=" + level +
				", children=" + children +
				", roleauth=" + roleauth +
				", url=" + url +
				", classes=" + classes +
				", pid=" + pid +"]";
	}
	
}
