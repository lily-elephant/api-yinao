package yinao.qualityLife.model.domain;

import javax.validation.constraints.NotNull;

public class Banner {
	private String bannerid;
	@NotNull
	private String pid;
	@NotNull
	private String aid;
	@NotNull
	private String name;
	@NotNull
	private String product;
	@NotNull
	private String sort;
	private String picture;
	private String article;
	private String state;
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	private String create_at;
	private String create_time;
	private String update_at;
	private String update_time;
	
	public Banner() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getBannerid() {
		return bannerid;
	}

	public void setBannerid(String bannerid) {
		this.bannerid = bannerid;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

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

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUpdate_at() {
		return update_at;
	}

	public void setUpdate_at(String update_at) {
		this.update_at = update_at;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	
	@Override
	public String toString() {
		return "banner [aid=" + aid +
				", pid=" + pid +
				", name=" + name +
				", product=" + product +
				", sort=" + sort +
				", picture=" + picture +
				", state=" + state +
				", article=" + article +
				",  create_at=" + create_at +
				",  update_at=" + update_at +
				",  create_time=" + create_time +
				", update_time=" + update_time +"]";
	}
	
	
	

}
