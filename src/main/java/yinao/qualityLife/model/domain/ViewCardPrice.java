package yinao.qualityLife.model.domain;


public class ViewCardPrice {
	private String primaryid ;
	private String ccid ; 
	private String cardprice ; 
	private String cardcount ; 
	private String cardname ; 

	public ViewCardPrice() {}
	public String getPrimaryid() {
		return primaryid;
	}

	public void setPrimaryid(String primaryid) {
		this.primaryid = primaryid;
	}
	public String getCcid() {
		return ccid;
	}

	public void setCcid(String ccid) {
		this.ccid = ccid;
	}

	public String getCardprice() {
		return cardprice;
	}

	public void setCardprice(String cardprice) {
		this.cardprice = cardprice;
	}

	public String getCardcount() {
		return cardcount;
	}

	public void setCardcount(String cardcount) {
		this.cardcount = cardcount;
	}

	public String getCardname() {
		return cardname;
	}

	public void setCardname(String cardname) {
		this.cardname = cardname;
	}
}
