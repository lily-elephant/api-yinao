package yinao.qualityLife.model.domain;

public class AuthData {
	
	private String auth_time ; 
	private String identity_auth ; 
	private String laolai_auth ; 
	private String phonenumber_auth ; 
	
	public AuthData() {}

	public String getAuth_time() {
		return auth_time;
	}

	public void setAuth_time(String auth_time) {
		this.auth_time = auth_time;
	}

	public String getIdentity_auth() {
		return identity_auth;
	}

	public void setIdentity_auth(String identity_auth) {
		this.identity_auth = identity_auth;
	}

	public String getLaolai_auth() {
		return laolai_auth;
	}

	public void setLaolai_auth(String laolai_auth) {
		this.laolai_auth = laolai_auth;
	}

	public String getPhonenumber_auth() {
		return phonenumber_auth;
	}

	public void setPhonenumber_auth(String phonenumber_auth) {
		this.phonenumber_auth = phonenumber_auth;
	}
	
	

}
