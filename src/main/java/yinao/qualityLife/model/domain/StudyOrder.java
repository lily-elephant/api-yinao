package yinao.qualityLife.model.domain;

import javax.validation.constraints.NotNull;

public class StudyOrder {
	private String ocid ; 
	private String hkid ; 
	private String cid ; 
	private String create_time;
	private String update_time ;
	private String state ;
	private String isbuy ; 
	private String name ;
	private String ccname ;
	private String brief ;
	private String money ;
	private String coursepicture ;
	private String hkname ;
	
	
	public String getHkname() {
		return hkname;
	}

	public void setHkname(String hkname) {
		this.hkname = hkname;
	}
	public String getCcname() {
		return ccname;
	}

	public void setCcname(String ccname) {
		this.ccname = ccname;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public  StudyOrder() {}

	public String getOcid() {
		return ocid;
	}

	public void setOcid(String ocid) {
		this.ocid = ocid;
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

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIsbuy() {
		return isbuy;
	}

	public void setIsbuy(String isbuy) {
		this.isbuy = isbuy;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getCoursepicture() {
		return coursepicture;
	}

	public void setCoursepicture(String coursepicture) {
		this.coursepicture = coursepicture;
	}
	
	

}
