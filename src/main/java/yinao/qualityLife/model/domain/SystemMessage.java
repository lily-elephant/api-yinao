package yinao.qualityLife.model.domain;

import javax.validation.constraints.NotNull;

public class SystemMessage {
  private String smid;
  @NotNull
  private String title;
  @NotNull
  private String content;
  @NotNull
  private String clienttype;
  private String sendtime;
  @NotNull
  private String state;
	public SystemMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getSmid() {
		return smid;
	}
	public void setSmid(String smid) {
		this.smid = smid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSendtime() {
		return sendtime;
	}
	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getClienttype() {
		return clienttype;
	}
	public void setClienttype(String clienttype) {
		this.clienttype = clienttype;
	}
	
  
  
}
