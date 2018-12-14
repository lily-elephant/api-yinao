package yinao.qualityLife.model.domain;

public class Eid {
	
	private String eid ; 
	private int score ;
	private String name ; 
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Eid() {}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "Eid [eid=" + eid + "]";
	}
	
	





}
