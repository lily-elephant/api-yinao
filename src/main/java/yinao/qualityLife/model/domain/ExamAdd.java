package yinao.qualityLife.model.domain;

import java.util.List;

import javax.validation.constraints.NotNull;

public class ExamAdd {
	
	private int eid ; 
	@NotNull
	private String ccid ; 	
	@NotNull
	private String name ; 
	@NotNull
	private String examtype ; 
	@NotNull
	private String score ; 
	
	private String create_time ; 
	private String update_time ; 
	@NotNull
	private String sort ; 
	@NotNull
	private String option ; 
	@NotNull
	private String label ;
	
	
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public ExamAdd() {}

	public int getEid() {
		return eid;
	}
	
	public void setEid(int eid) {
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

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}
	
	
	
	
	
	

	
	
	
	

}
