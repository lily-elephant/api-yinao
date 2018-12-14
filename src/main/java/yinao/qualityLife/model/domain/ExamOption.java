package yinao.qualityLife.model.domain;

public class ExamOption {
	
	private int oid ; 
	private String eid ;
	private String name ; 
	private String content ; 
	private String isanswer ; 
	
	private String isSelected ; 	

	public String getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(String isSelected) {
		this.isSelected = isSelected;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public String getIsanswer() {
		return isanswer;
	}

	public void setIsanswer(String isanswer) {
		this.isanswer = isanswer;
	}

	public ExamOption() {}
	
	public ExamOption(String name , String content , String isanswer ) {
		super() ; 
		this.name = name ; 
		this.content = content ; 
		this.isanswer = isanswer ; 
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "ExamOption [oid=" + oid + ", eid=" + eid + ", name=" + name + ", content=" + content + ", isanswer="
				+ isanswer + ", isSelected=" + isSelected + "]";
	}


	
	

	
}
