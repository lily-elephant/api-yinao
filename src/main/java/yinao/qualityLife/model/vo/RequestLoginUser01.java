package yinao.qualityLife.model.vo;

import javax.validation.constraints.NotNull;
public class RequestLoginUser01 {

    @NotNull
    private String username;
    
    private String name;
    
    @NotNull
    private String usertype;
    
    @NotNull
    private String hash;
    
    @NotNull
    private String tamp;
    
    @NotNull
    private String verifycode;
    

    public RequestLoginUser01() {
    }

    public String getUsername() {
        return username;
    }
    public String getName() {
        return name;
    }
    public String getUsertype() {
        return usertype;
    }
    public String getHash() {
        return hash;
    }
    public String getTamp() {
        return tamp;
    }
    public String getVerifycode() {
        return verifycode;
    }
    
    public RequestLoginUser01 setUsername(String username) {
        this.username = username;
        return this;
    }
    public void setName(String name) {
		this.name = name;
	}
    public RequestLoginUser01 setUsertype(String usertype) {
        this.usertype = usertype;
        return this;
    }
    public RequestLoginUser01 setHash(String hash) {
        this.hash = hash;
        return this;
    }
    public RequestLoginUser01 setTamp(String tamp) {
        this.tamp = tamp;
        return this;
    }
    public RequestLoginUser01 setVerifycode(String verifycode) {
        this.verifycode = verifycode;
        return this;
    }

    @Override
    public String toString() {
        return "RequestLoginUser01{" +
                "username='" + username + '\'' +
                 "name='" + name + '\'' +
                "usertype='" + usertype + '\'' +
                ", hash='" + hash + '\'' +
                 ", tamp='" + tamp + '\'' +
                  ", verifycode='" + verifycode + '\'' +
                '}';
    }
}
