package yinao.qualityLife.model.vo;

import javax.validation.constraints.NotNull;

public class RequestLoginUser03 {
		@NotNull
	    private String username;
	    
	    @NotNull
	    private String usertype;
	   
	    @NotNull
	    private String registertype;
	    

	    public RequestLoginUser03() {
	    }

	    public String getUsername() {
	        return username;
	    }
	    public String getRegistertype() {
	        return registertype;
	    }
	    public String getUsertype() {
	        return usertype;
	    }
	   
	    public RequestLoginUser03 setUsername(String username) {
	        this.username = username;
	        return this;
	    }
	   
	    public RequestLoginUser03 setUsertype(String usertype) {
	        this.usertype = usertype;
	        return this;
	    }
	   
	    public RequestLoginUser03 setRegistertype(String registertype) {
	        this.registertype = registertype;
	        return this;
	    }

	    @Override
	    public String toString() {
	        return "RequestLoginUser03{" +
	                "username='" + username + '\'' +
	                ", usertype='" + usertype + '\'' +
	                  ", registertype='" + registertype + '\'' +
	                '}';
	    }
}
