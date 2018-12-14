package yinao.qualityLife.model.domain;

import javax.validation.constraints.NotNull;

public class InterviewOrder {
	
	@NotNull
	private String interviewid ;
	
	public InterviewOrder() {}

	public String getInterviewid() {
		return interviewid;
	}

	public void setInterviewid(String interviewid) {
		this.interviewid = interviewid;
	}
	
	

}
