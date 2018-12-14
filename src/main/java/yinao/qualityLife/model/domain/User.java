package yinao.qualityLife.model.domain;

import yinao.qualityLife.model.LoginDetail;
import yinao.qualityLife.model.TokenDetail;

public class User implements LoginDetail, TokenDetail {

	private String id ;
    private String username;
    private String name ;
    private String password;
    private String idcard;
    private String education;
    private String address1;
    private String address2;
    private String address3;
    private String sex;
    private String servicestate;
    private String roleid;
    private String auth;
    private Long create_time;
    private char state; 
    private String headimageurl ;
    private String brief ; 
    private String workdate ; 
    private String salary ; 
    private String result ;
    private String result2 ; 
    private String marry ; 
    private String isdrive ; 
    private String describes ; 
    private String nativeplace ; 
    private String addresslabel;
    private String roomarea;
    private String courtarea;
    private String bedroom;
    private String restaurant;
    private String washroom;
    private String peoplecount;
    private String oldcount;
    private String childcount;
    
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

	public String getMarry() {
		return marry;
	}

	public void setMarry(String marry) {
		this.marry = marry;
	}

	public String getIsdrive() {
		return isdrive;
	}

	public void setIsdrive(String isdrive) {
		this.isdrive = isdrive;
	}


	public String getDescribes() {
		return describes;
	}

	public void setDescribes(String describes) {
		this.describes = describes;
	}

	public String getNativeplace() {
		return nativeplace;
	}

	public void setNativeplace(String nativeplace) {
		this.nativeplace = nativeplace;
	}

	public String getResult2() {
		return result2;
	}

	public void setResult2(String result2) {
		this.result2 = result2;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getWorkdate() {
		return workdate;
	}

	public void setWorkdate(String workdate) {
		this.workdate = workdate;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public char getState() {
		return state;
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getServicestate() {
		return servicestate;
	}

	public void setServicestate(String servicestate) {
		this.servicestate = servicestate;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public void setState(char state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeadimageurl() {
		return headimageurl;
	}

	public void setHeadimageurl(String headimageurl) {
		this.headimageurl = headimageurl;
	}

	public User() {}
	
	public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public Long getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Long create_time) {
		this.create_time = create_time;
	}

//	public char getState() {
//		return state;
//	}
//
//	public void setState(char state) {
//		this.state = state;
//	}

	public User setEnable(char state) {
        this.state = state;
        return this;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean enable() {
//        if (this.state == '1'){
//            return true;
//        }
//        return false;
    	return true;
    }

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", auth=" + auth + ", create_time="
				+ create_time + ", state=" + state + "]";
	}

	@Override
	public String getUsertype() {
		// TODO Auto-generated method stub
		return null;
	}

   
}
