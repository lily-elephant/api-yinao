package yinao.qualityLife.model.domain;

public class LikeBean {
	
	private String lid;
	private String username ; 
	private String usertype ; 
	
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getLid() {
		return lid;
	}

	public void setLid(String lid) {
		this.lid = lid;
	}

	@Override
	public String toString() {
		return "LikeBean [lid=" + lid + ", username=" + username + ", usertype=" + usertype + "]";
	}
	
	
	

}
