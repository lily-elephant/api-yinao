package yinao.qualityLife.model.domain;

import javax.validation.constraints.NotNull;

public class Courses {
	
	private String catagoryname ;
	
	private String cid;
	@NotNull
	private String name;
	@NotNull
	private String money;
	@NotNull
	private String brief;
	@NotNull
	private String vid;
	@NotNull
	private String aid;
	private String pictureid;
	@NotNull
	private String ismust;
	@NotNull
	private String sort;
	private String videoname;
	private String articlename;
	private String picturename;
	private int  islearn;
	public String getVideoname() {
		return videoname;
	}

	public void setVideoname(String videoname) {
		this.videoname = videoname;
	}

	public String getArticlename() {
		return articlename;
	}

	public void setArticlename(String articlename) {
		this.articlename = articlename;
	}
	

	public String getPictureid() {
		return pictureid;
	}

	public void setPictureid(String pictureid) {
		this.pictureid = pictureid;
	}

	public String getPicturename() {
		return picturename;
	}

	public void setPicturename(String picturename) {
		this.picturename = picturename;
	}

	public String getOrdercount() {
		return ordercount;
	}
	
	public String getCatagoryname() {
		return catagoryname;
	}

	public void setCatagoryname(String catagoryname) {
		this.catagoryname = catagoryname;
	}

	public void setOrdercount(String ordercount) {
		this.ordercount = ordercount;
	}

	private String article;
	private String  video;
	private String  viewcount;
	private String  ordercount;
	public String getViewcount() {
		return viewcount;
	}
	public void setViewcount(String viewcount) {
		this.viewcount = viewcount;
	}
	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getCcid() {
		return ccid;
	}

	public void setCcid(String ccid) {
		this.ccid = ccid;
	}

	@NotNull
	private String ccid;
	private int isbuy;
	private String create_time;
	public String getCoursepicture() {
		return coursepicture;
	}

	public void setCoursepicture(String coursepicture) {
		this.coursepicture = coursepicture;
	}

	private String update_time;
	private String create_at;
	@NotNull
	private String coursepicture;
	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getCreate_at() {
		return create_at;
	}

	public void setCreate_at(String create_at) {
		this.create_at = create_at;
	}

	public String getUpdate_at() {
		return update_at;
	}

	public void setUpdate_at(String update_at) {
		this.update_at = update_at;
	}

	private String update_at;
	
	public Courses() {
	
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
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

	public String getVid() {
		return vid;
	}

	public void setVid(String vid) {
		this.vid = vid;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getIsmust() {
		return ismust;
	}

	public void setIsmust(String ismust) {
		this.ismust = ismust;
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
	
	public int getIsbuy() {
		return isbuy;
	}

	public void setIsbuy(int isbuy) {
		this.isbuy = isbuy;
	}

	public int getIslearn() {
		return islearn;
	}

	public void setIslearn(int islearn) {
		this.islearn = islearn;
	}

	
	
	
}
