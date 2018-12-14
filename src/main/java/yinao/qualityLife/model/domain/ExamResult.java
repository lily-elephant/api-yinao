package yinao.qualityLife.model.domain;

import javax.validation.constraints.NotNull;

public class ExamResult {
	
	@NotNull
	private String oid ; 
	@NotNull
	private String eid ; 
	
	private String username ; 
	private String create_time ; 
	private String update_time ; 
	private String state ; 
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public ExamResult() {}


	
}
