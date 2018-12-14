package yinao.qualityLife.model.domain;

public class WeChat {
	private String emid;
	private String transactionid;
	private String businesstype;
	private String osid;
	private String hkid;
	private String usertype;

	public WeChat() {}

	public WeChat(String emid , String transactionid , String businesstype , String osid , String hkid,String usertype) {
		this.emid = emid ; 
		this.transactionid = transactionid ; 
		this.businesstype = businesstype ; 
		this.osid = osid ; 
		this.hkid = hkid ; 
		this.usertype=usertype;
	}
	
	public String getEmid() {
		return emid;
	}

	public void setEmid(String emid) {
		this.emid = emid;
	}

	public String getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}

	public String getBusinesstype() {
		return businesstype;
	}

	public void setBusinesstype(String businesstype) {
		this.businesstype = businesstype;
	}

	public String getOsid() {
		return osid;
	}

	public void setOsid(String osid) {
		this.osid = osid;
	}

	public String getHkid() {
		return hkid;
	}

	public void setHkid(String hkid) {
		this.hkid = hkid;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

}
