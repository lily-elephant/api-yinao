package yinao.qualityLife.model.domain;

public class UploadVideos {
	private String path;
	private Long size;
	private int sort;
	private String duration;
	private String name;
	
	public UploadVideos() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String  duration) {
		this.duration = duration;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	@Override 
	public String toString() {
		return "UploadVideos [size=" + size +
				", name=" + name +
				", sort=" + sort +
				", duration=" + duration +
				",  path=" + path +"]";
	}
	

}
