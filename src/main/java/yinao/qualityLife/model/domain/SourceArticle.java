package yinao.qualityLife.model.domain;

import javax.validation.constraints.NotNull;

public class SourceArticle {
	private String aid;
	@NotNull
	private String name;
	@NotNull
	private String brief;
	@NotNull
	private String content;
	@NotNull
	private String sort;
	private String scid;
	private String create_at;
	private String update_at;
	private String create_time;
	private String update_time;
	
	public SourceArticle() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getScid() {
		return scid;
	}

	public void setScid(String scid) {
		this.scid = scid;
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
	
	@Override
	public String toString() {
		return "sourceArticle [aid=" + aid +
				", name=" + name +
				", brief=" + brief +
				", content=" + content +
				",  sort=" + sort +
				",  scid=" + scid +
				",  create_at=" + create_at +
				",  update_at=" + update_at +
				",  create_time=" + create_time +
				", update_time=" + update_time +"]";
	}
	
	
	
}
