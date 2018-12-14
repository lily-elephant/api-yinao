package yinao.qualityLife.model.domain;

public class ServiceRecord {
	
	private String hkid ; 
	private String hkname ; 
	private String hkphone ; 
	private String hkheadimg ; 
	
	private String emid ; 
	private String emname ; 
	private String emphone ; 
	private String emheadimg ; 
	
	private String starttime ; 
	private String endtime ; 
	private String create_time ; 
	
	private String salary ;
	
	public ServiceRecord() {}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getHkid() {
		return hkid;
	}

	public void setHkid(String hkid) {
		this.hkid = hkid;
	}

	public String getHkname() {
		return hkname;
	}

	public void setHkname(String hkname) {
		this.hkname = hkname;
	}

	public String getHkphone() {
		return hkphone;
	}

	public void setHkphone(String hkphone) {
		this.hkphone = hkphone;
	}

	public String getHkheadimg() {
		return hkheadimg;
	}

	public void setHkheadimg(String hkheadimg) {
		this.hkheadimg = hkheadimg;
	}

	public String getEmid() {
		return emid;
	}

	public void setEmid(String emid) {
		this.emid = emid;
	}

	public String getEmname() {
		return emname;
	}

	public void setEmname(String emname) {
		this.emname = emname;
	}

	public String getEmphone() {
		return emphone;
	}

	public void setEmphone(String emphone) {
		this.emphone = emphone;
	}

	public String getEmheadimg() {
		return emheadimg;
	}

	public void setEmheadimg(String emheadimg) {
		this.emheadimg = emheadimg;
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

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	
	

}
