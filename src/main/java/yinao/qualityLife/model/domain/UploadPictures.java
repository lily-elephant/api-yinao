package yinao.qualityLife.model.domain;

public class UploadPictures {
	private Long size;
	private int height;
	private int width;
	private int sort;
	private String path;
	private String name;
	
	
	public UploadPictures(Long size , int height , int width , int sort , String path , String name) {
		super();
		this.size = size ;
		this.height = height ; 
		this.width = width ; 
		this.sort = sort ; 
		this.path = path ; 
		this.name = name ; 
	}
	
	public UploadPictures() {
		
	}
	
	

	public Long getSize() {
		return size;
	}



	public void setSize(Long size) {
		this.size = size;
	}



	public int getHeight() {
		return height;
	}



	public void setHeight(int height) {
		this.height = height;
	}



	public int getWidth() {
		return width;
	}



	public void setWidth(int width) {
		this.width = width;
	}



	public String getPath() {
		return path;
	}



	public void setPath(String path) {
		this.path = path;
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
		return "MyCourse [size=" + size +
				", height=" + height +
				", width=" + width +
				", name=" + name +
				", sort=" + sort +
				",  path=" + path +"]";
	}
	

}
