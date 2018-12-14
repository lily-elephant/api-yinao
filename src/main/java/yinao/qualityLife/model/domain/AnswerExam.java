package yinao.qualityLife.model.domain;

public class AnswerExam {
	
	private String eid ; 
	private String oid ;
	
	public AnswerExam() {}
	
	public AnswerExam(String eid , String oid ) {
		super();
		this.eid = eid ;
		this.oid = oid ; 
	}
	
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}

	@Override
	public String toString() {
		return "AnswerExam [eid=" + eid + ", oid=" + oid + "]";
	} 
	
	
	
	
	
	

}
