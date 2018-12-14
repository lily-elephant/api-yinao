package yinao.qualityLife.model.domain;

import javax.validation.constraints.NotNull;
public class SourceVideo {
	private String vid;
	@NotNull
	private String scid;
	@NotNull
	private String name;
	@NotNull
	private String path;
	@NotNull
	private String duration;
	@NotNull
	private String size;
	@NotNull
	private String sort;
	private String catagoryname;
	public String getCatagoryname() {
		return catagoryname;
	}
	public void setCatagoryname(String catagoryname) {
		this.catagoryname = catagoryname;
	}

	private String create_at;
	private String create_time;
	private String update_at;
	private String update_time;
	public SourceVideo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getVid() {
		return vid;
	}
	public void setVid(String vid) {
		this.vid = vid;
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
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getCreate_at() {
		return create_at;
	}
	public void setCreate_at(String create_at) {
		this.create_at = create_at;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_at() {
		return update_at;
	}
	public void setUpdate_at(String update_at) {
		this.update_at = update_at;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	
	@Override
	public String toString() {
		return "sourcePicture [scid=" + scid +
				", vid=" + vid +
				", name=" + name +
				", catagoryname=" + catagoryname +
				", path=" + path +
				",  sort=" + sort +
				",  size=" + size +
				",  duration=" + duration +
				",  create_at=" + create_at +
				",  update_at=" + update_at +
				",  create_time=" + create_time +
				", update_time=" + update_time +"]";
	}
	
}
