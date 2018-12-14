package yinao.qualityLife.model.domain;

public class OfflineTraining {
	
	private String otid ;
	private String name ; 
	private String des ; 
	private String money ;
	private String create_time ; 
	private String pic ; 
	private String state ; 
	private String payflag;
	private String address ;
	private String buycount ;
	
	public String getBuycount() {
		return buycount;
	}

	public void setBuycount(String buycount) {
		this.buycount = buycount;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPayflag() {
		return payflag;
	}

	public void setPayflag(String payflag) {
		this.payflag = payflag;
	}

	public OfflineTraining() {}

	public String getOtid() {
		return otid;
	}

	public void setOtid(String otid) {
		this.otid = otid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "OfflineTraining [otid=" + otid + ", name=" + name + ", des=" + des + ", money=" + money
				+ ", create_time=" + create_time + ", pic=" + pic + ", state=" + state + ", payflag=" + payflag + "]";
	}
	
	
	
	

}
