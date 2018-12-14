package yinao.qualityLife.model.domain;

public class OfflineTrainingRecord {
	
	private String name ;
	private String orid ; 
	private String otid ; 
	private String hkid ; 
	private String score ;
	private String state ; 
	private String create_time ; 
	private String update_time; 
	
	public OfflineTrainingRecord() {}

	public String getOrid() {
		return orid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOrid(String orid) {
		this.orid = orid;
	}

	public String getOtid() {
		return otid;
	}

	public void setOtid(String otid) {
		this.otid = otid;
	}

	public String getHkid() {
		return hkid;
	}

	public void setHkid(String hkid) {
		this.hkid = hkid;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
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

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	
	

}
