package yinao.qualityLife.model.domain;

import java.util.List;

public class Exam {
	
	private String eid ; 
	private String ccid ; 	
	private String name ; 
	private String ccname ;
	private String examtype ; 
	private String score ; 
	private String create_time ; 
	private String update_time ; 
	private String sort ; 
	private String ismust ; 
	private String oid ; 
	private String ture_answer ;
	private List<ExamOption> option ;
	private String label ; 
	
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getTure_answer() {
		return ture_answer;
	}

	public void setTure_answer(String ture_answer) {
		this.ture_answer = ture_answer;
	}

	public String getCcname() {
		return ccname;
	}

	public void setCcname(String ccname) {
		this.ccname = ccname;
	}
	
	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getIsmust() {
		return ismust;
	}

	public void setIsmust(String ismust) {
		this.ismust = ismust;
	}

	public Exam() {}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
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

	public String getExamtype() {
		return examtype;
	}

	public void setExamtype(String examtype) {
		this.examtype = examtype;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
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

	public List<ExamOption> getOption() {
		return option;
	}

	public void setOption(List<ExamOption> option) {
		this.option = option;
	}
	
	

	
	
	
	

}
