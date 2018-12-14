package yinao.qualityLife.model.domain;

public class Employer {

    private String emid;
    private String name;
	private String username;
	private String password;
	private String headimageurl;
    private String idcard;
    private String education;
    private String address1;
    private String address2;
    private String address3;
    private String sex;
    private Integer state;   
    private String servicestate;
    private String creat_at;
    private String update_at;
    private String create_time;
    private String update_time;
    private String role_id;
    private String addresslabel;
    private String roomarea;
    private String courtarea;
    private String bedroom;
    private String restaurant;
    private String washroom;
    private String peoplecount;
    private String oldcount;
    private String childcount;
    private String describes;
    
	public String getAddresslabel() {
		return addresslabel;
	}


	public void setAddresslabel(String addresslabel) {
		this.addresslabel = addresslabel;
	}


	public String getRoomarea() {
		return roomarea;
	}


	public void setRoomarea(String roomarea) {
		this.roomarea = roomarea;
	}


	public String getCourtarea() {
		return courtarea;
	}


	public void setCourtarea(String courtarea) {
		this.courtarea = courtarea;
	}


	public String getBedroom() {
		return bedroom;
	}


	public void setBedroom(String bedroom) {
		this.bedroom = bedroom;
	}


	public String getRestaurant() {
		return restaurant;
	}


	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
	}


	public String getWashroom() {
		return washroom;
	}


	public void setWashroom(String washroom) {
		this.washroom = washroom;
	}


	public String getPeoplecount() {
		return peoplecount;
	}


	public void setPeoplecount(String peoplecount) {
		this.peoplecount = peoplecount;
	}


	public String getOldcount() {
		return oldcount;
	}


	public void setOldcount(String oldcount) {
		this.oldcount = oldcount;
	}


	public String getChildcount() {
		return childcount;
	}


	public void setChildcount(String childcount) {
		this.childcount = childcount;
	}



	public String getDescribes() {
		return describes;
	}


	public void setDescribes(String describes) {
		this.describes = describes;
	}


	public Employer() {}


	public String getServicestate() {
		return servicestate;
	}


	public void setServicestate(String servicestate) {
		this.servicestate = servicestate;
	}


	public String getEmid() {
		return emid;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getCreat_at() {
		return creat_at;
	}


	public void setCreat_at(String creat_at) {
		this.creat_at = creat_at;
	}


	public String getUpdate_at() {
		return update_at;
	}


	public void setUpdate_at(String update_at) {
		this.update_at = update_at;
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


	public String getRole_id() {
		return role_id;
	}


	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}


	public void setEmid(String emid) {
		this.emid = emid;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getIdcard() {
		return idcard;
	}


	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}


	public String getEducation() {
		return education;
	}


	public void setEducation(String education) {
		this.education = education;
	}


	public String getSex() {
		return sex;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}


	public String getAddress1() {
		return address1;
	}


	public void setAddress1(String address1) {
		this.address1 = address1;
	}


	public String getAddress2() {
		return address2;
	}


	public void setAddress2(String address2) {
		this.address2 = address2;
	}


	public String getAddress3() {
		return address3;
	}


	public void setAddress3(String address3) {
		this.address3 = address3;
	}


	public String getHeadimageurl() {
		return headimageurl;
	}

	public void setHeadimageurl(String headimageurl) {
		this.headimageurl = headimageurl;
	}

	public Integer getState() {
		return state;
	}


	public void setState(Integer state) {
		this.state = state;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "employer [emid=" + emid + ", name=" + name + ", username=" + username + ", password=" + password
				+ ", headimageurl=" + headimageurl + ", idcard=" + idcard + ", education=" + education + ", address1="
				+ address1 + ", address2=" + address2 + ", address3=" + address3 + ", sex=" + sex + ", state=" + state
				+ ", servicestate=" + servicestate + ", creat_at=" + creat_at + ", update_at=" + update_at
				+ ", create_time=" + create_time + ", update_time=" + update_time + ", role_id=" + role_id + "]";
	}
	
}
