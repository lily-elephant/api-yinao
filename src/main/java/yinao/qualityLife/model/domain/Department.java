package yinao.qualityLife.model.domain;

import javax.validation.constraints.NotNull;

public class Department {
	private String id;
	@NotNull
	private String name;
	private String describes;
	public Department() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescribes() {
		return describes;
	}
	public void setDescribes(String describes) {
		this.describes = describes;
	}
	
	@Override
	public String toString() {
		return "Department[id=" + id +
				", name=" + name +
				", describes=" + describes+"]";
	}
	
}
