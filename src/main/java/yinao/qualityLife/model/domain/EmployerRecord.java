package yinao.qualityLife.model.domain;

public class EmployerRecord {
	
	private String osid ; 
	private String emid ; 
	private String hkid ;
	private String starttime ; 
	private String endtime ; 
	private String salary ; 
	private String managefee ; 
	private String state ; 
	private String remark ; 
	private String create_time ; 
	private String hkname ; 
	private String emname ;
	private String cid ;
	private int score ;
	private String content ;
	private String commenttime ;
	private String headimageurl ;
	private String address1 ;
	private String emphone ; 
	private String hkphone ; 
	private String payflag ;
	
	public String getPayflag() {
		return payflag;
	}

	public void setPayflag(String payflag) {
		this.payflag = payflag;
	}

	public String getEmphone() {
		return emphone;
	}

	public void setEmphone(String emphone) {
		this.emphone = emphone;
	}

	public String getHkphone() {
		return hkphone;
	}

	public void setHkphone(String hkphone) {
		this.hkphone = hkphone;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getHkname() {
		return hkname;
	}

	public void setHkname(String hkname) {
		this.hkname = hkname;
	}

	public String getHeadimageurl() {
		return headimageurl;
	}

	public void setHeadimageurl(String headimageurl) {
		this.headimageurl = headimageurl;
	}

	public EmployerRecord() {}

	public String getOsid() {
		return osid;
	}

	public void setOsid(String osid) {
		this.osid = osid;
	}

	public String getEmid() {
		return emid;
	}

	public void setEmid(String emid) {
		this.emid = emid;
	}

	public String getHkid() {
		return hkid;
	}

	public void setHkid(String hkid) {
		this.hkid = hkid;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getManagefee() {
		return managefee;
	}

	public void setManagefee(String managefee) {
		this.managefee = managefee;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getEmname() {
		return emname;
	}

	public void setEmname(String emname) {
		this.emname = emname;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}



	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCommenttime() {
		return commenttime;
	}

	public void setCommenttime(String commenttime) {
		this.commenttime = commenttime;
	}
}
