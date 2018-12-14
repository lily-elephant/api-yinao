package yinao.qualityLife.model.vo;

import javax.validation.constraints.NotNull;
public class RequestLoginUser02 {

    @NotNull
    private String username;
    
    @NotNull
    private String usertype;
    
    private String name;
    
    @NotNull
    private String password;
    
    @NotNull
    private String hash;
    
    @NotNull
    private String tamp;
    
    @NotNull
    private String verifycode;
    
    @NotNull
    private String registertype;
    

    public RequestLoginUser02() {
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
    public String getName() {
		return name;
	}
    public String getPassword() {
        return password;
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
    
    public RequestLoginUser02 setUsername(String username) {
        this.username = username;
        return this;
    }
    public void setName(String name) {
		this.name = name;
	}
    public RequestLoginUser02 setUsertype(String usertype) {
        this.usertype = usertype;
        return this;
    }
    public RequestLoginUser02 setPassword(String password) {
        this.password = password;
        return this;
    }
    public RequestLoginUser02 setHash(String hash) {
        this.hash = hash;
        return this;
    }
    public RequestLoginUser02 setTamp(String tamp) {
        this.tamp = tamp;
        return this;
    }
    public RequestLoginUser02 setVerifycode(String verifycode) {
        this.verifycode = verifycode;
        return this;
    }
    public RequestLoginUser02 setRegistertype(String registertype) {
        this.registertype = registertype;
        return this;
    }

    @Override
    public String toString() {
        return "RequestLoginUser02{" +
                "username='" + username + '\'' +
                 "name='" + name + '\'' +
                 "password='" + password + '\'' +
                ", hash='" + hash + '\'' +
                 ", tamp='" + tamp + '\'' +
                  ", verifycode='" + verifycode + '\'' +
                  ", registertype='" + registertype + '\'' +
                  ", usertype='" + usertype + '\'' +
                '}';
    }
}
