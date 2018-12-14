package yinao.qualityLife.model.domain;

import javax.validation.constraints.NotNull;

public class HousekeeperCertificate {
	
	private String hkcid;
	@NotNull
	private String hkid ;
	private String cid ;
	private String picture ;
	private String state ;
	private String create_time ;
	private String name ;
	
	public HousekeeperCertificate() {}

	public String getHkcid() {
		return hkcid;
	}

	public void setHkcid(String hkcid) {
		this.hkcid = hkcid;
	}

	public String getHkid() {
		return hkid;
	}

	public void setHkid(String hkid) {
		this.hkid = hkid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "HousekeeperCertificate [hkcid=" + hkcid + ", hkid=" + hkid + ", cid=" + cid + ", picture=" + picture
				+ ", state=" + state + ", create_time=" + create_time + ", name=" + name + "]";
	}
	
	
	
	

}
