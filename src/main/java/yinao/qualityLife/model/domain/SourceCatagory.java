package yinao.qualityLife.model.domain;

import java.util.ArrayList;
import javax.validation.constraints.NotNull;
public class SourceCatagory {
	
	private String scid;
	@NotNull
	private String name;
	@NotNull
	private String sourcetype;
	@NotNull
	private String sort;
	@NotNull
	private String parentid;
	@NotNull
	private String level;
	private String create_at;
	private String update_at;
	private String create_time;
	private String update_time;
	private  ArrayList<SourceCatagory> list;
	public ArrayList<SourceCatagory> getList() {
		return list;
	}

	public void setList(ArrayList<SourceCatagory> list) {
		this.list = list;
	}

	public String getScid() {
		return scid;
	}

	public void setScid(String scid) {
		this.scid = scid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSourcetype() {
		return sourcetype;
	}

	public void setSourcetype(String sourcetype) {
		this.sourcetype = sourcetype;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getCreate_at() {
		return create_at;
	}

	public void setCreate_at(String create_at) {
		this.create_at = create_at;
	}

	public String getUpdate_at() {
		return update_at;
	}

	public void setUpdate_at(String update_at) {
		this.update_at = update_at;
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

	public SourceCatagory() {
		
	}
	
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "sourceCatagory [scid=" + scid +
				", name=" + name +
				", name=" + name +
				", sourcetype=" + sourcetype +
				", parentid=" + parentid +
				",  sort=" + sort +
				",  list=" + list +
				",  level=" + level +
				",  create_at=" + create_at +
				",  update_at=" + update_at +
				",  create_time=" + create_time +
				", update_time=" + update_time +"]";
	}
	
	
	
	

}
