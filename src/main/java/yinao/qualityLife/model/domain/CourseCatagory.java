package yinao.qualityLife.model.domain;

import javax.validation.constraints.NotNull;

public class CourseCatagory {
	private String ccid;
	private String name;
	private String description;
	private String create_time;
	private String update_time;
	private String price;
	private String ismust;
	private String sort;
	private String discount;
	private String aid;
	private String isbuy;
	private String iscanbuy;
	public String getIstest() {
		return istest;
	}


	public String getDiscount() {
		return discount;
	}


	public void setDiscount(String discount) {
		this.discount = discount;
	}


	public void setIstest(String istest) {
		this.istest = istest;
	}

	private String istest;
	public String getIsmust() {
		return ismust;
	}


	public void setIsmust(String ismust) {
		this.ismust = ismust;
	}


	public int getCourseCount() {
		return courseCount;
	}


	public void setCourseCount(int courseCount) {
		this.courseCount = courseCount;
	}
	@NotNull
	private String picture;
	private String picturename;
	private String pictureid;
	private int courseCount;
	private String articlecontent;
	private String articlename;

	public String getArticlecontent() {
		return articlecontent;
	}


	public void setArticlecontent(String articlecontent) {
		this.articlecontent = articlecontent;
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


	public String getPicture() {
		return picture;
	}


	public void setPicture(String picture) {
		this.picture = picture;
	}


	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = price;
	}


	public CourseCatagory() {
		
	}


	public String getCcid() {
		return ccid;
	}
	public void setCcid(String ccid) {
		this.ccid = ccid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public String getArticlename() {
		return articlename;
	}
	public void setArticlename(String articlename) {
		this.articlename = articlename;
	}


	public String getIsbuy() {
		return isbuy;
	}


	public void setIsbuy(String isbuy) {
		this.isbuy = isbuy;
	}


	public String getIscanbuy() {
		return iscanbuy;
	}


	public void setIscanbuy(String iscanbuy) {
		this.iscanbuy = iscanbuy;
	}


	@Override
	public String toString() {
		return "courseCatagory [ccid=" + ccid + ", name=" + name+ ", sort=" + sort + ", istest=" + istest +", ismust=" + ismust + ", courseCount=" + courseCount +", description=" + description + ",  create_time=" + create_time + ", update_time=" + update_time +", description=" + description+"]";
	}
	
	

}
