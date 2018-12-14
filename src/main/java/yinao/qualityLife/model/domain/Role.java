package yinao.qualityLife.model.domain;

import javax.validation.constraints.NotNull;

public class Role {
	@NotNull
	private String rolename;
	private String roleid;
	private String created_at;
	public Role() {
		
	}
	
	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	@Override
	public String toString() {
		return "Role[roleid=" + roleid +
				", rolename=" + rolename +
				", created_at=" + created_at +"]";
	}
	
	
}
