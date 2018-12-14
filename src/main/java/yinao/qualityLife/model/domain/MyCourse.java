package yinao.qualityLife.model.domain;

import javax.validation.constraints.NotNull;

public class MyCourse {
	@NotNull
	private String  ccid;

	private String  hkid;
	private String  name;
	private String  money;
	private String  brief;
	private String  video;
	private String  article;
	private String  ismust;
	public String getCoursepicture() {
		return coursepicture;
	}

	public void setCoursepicture(String coursepicture) {
		this.coursepicture = coursepicture;
	}

	private String  isbuy;
	private String  coursepicture;
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

	private String  cid;
	private String  create_time;
	private String  update_time;
	
	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public MyCourse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCcid() {
		return ccid;
	}

	public void setCcid(String ccid) {
		this.ccid = ccid;
	}

	public String getHkid() {
		return hkid;
	}

	public void setHkid(String hkid) {
		this.hkid = hkid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public String getIsmust() {
		return ismust;
	}

	public void setIsmust(String ismust) {
		this.ismust = ismust;
	}

	public String getIsbuy() {
		return isbuy;
	}

	public void setIsbuy(String isbuy) {
		this.isbuy = isbuy;
	}
	
	@Override 
	public String toString() {
		return "MyCourse [cid=" + cid +
				", ccid=" + ccid +
				", name=" + name +
				", hkid=" + hkid +
				", money=" + money +
				",  brief=" + brief +
				",  article=" + article +
				",  video=" + video +
				",  coursepicture=" + coursepicture +
				",  isbuy=" + isbuy +
				", create_time=" + create_time +
				", update_time=" + update_time +
				",  ismust=" + ismust +"]";
	}
	
	
	
	
	
}
