package yinao.qualityLife.model.domain;


public class ViewCard {
	private String viewid ;
	private String ccid ; 
	private String emid ; 
	private String totalcount ; 
	private String remaincount ; 
	private String buytime ; 
	private String wxno ; 
	
	public ViewCard() {}
	public String getViewid() {
		return viewid;
	}

	public void setViewid(String viewid) {
		this.viewid = viewid;
	}
	public String getCcid() {
		return ccid;
	}

	public void setCcid(String ccid) {
		this.ccid = ccid;
	}

	public String getEmid() {
		return emid;
	}

	public void setEmid(String emid) {
		this.emid = emid;
	}

	public String getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(String totalcount) {
		this.totalcount = totalcount;
	}

	public String getRemaincount() {
		return remaincount;
	}

	public void setRemaincount(String remaincount) {
		this.remaincount = remaincount;
	}

	public String getBuytime() {
		return buytime;
	}

	public void setBuytime(String buytime) {
		this.buytime = buytime;
	}

	public String getWxno() {
		return wxno;
	}

	public void setWxno(String wxno) {
		this.wxno = wxno;
	}
}
