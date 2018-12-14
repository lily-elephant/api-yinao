package yinao.qualityLife.model.domain;

import javax.validation.constraints.NotNull;

public class Comment {
	
	private String cid ; 
	private String emid ; 
	@NotNull
	private String hkid ; 
	@NotNull
	private int score ; 
	@NotNull
	private String content ; 
	private String state ; 
	private String create_time ; 
	
	public Comment() {}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getEmid() {
		return emid;
	}

	public void setEmid(String emid) {
		this.emid = emid;
	}

	public String getHkid() {
		return hkid;
	}

	public void setHkid(String hkid) {
		this.hkid = hkid;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	@Override
	public String toString() {
		return "Comment [cid=" + cid + ", emid=" + emid + ", hkid=" + hkid + ", score=" + score + ", content=" + content
				+ ", state=" + state + ", create_time=" + create_time + "]";
	}
	
	
	
	

}
