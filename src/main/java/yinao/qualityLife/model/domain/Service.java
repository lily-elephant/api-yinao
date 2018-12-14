package yinao.qualityLife.model.domain;

import javax.validation.constraints.NotNull;

public class Service {

    private int osid ;
    @NotNull
    private String emid ;
    @NotNull
    private String hkid ;
    @NotNull
    private String starttime ;
    @NotNull
    private String endtime ;
    @NotNull
    private String salary ;
    @NotNull
    private String managefee ;
    private String state ;
    private String remark ;
    private String create_time ;
    private String update_time ;

    public Service(){}


    public int getOsid() {
		return osid;
	}


	public void setOsid(int osid) {
		this.osid = osid;
	}


	public String getEmid() {
        return emid; 
    }

    public void setEmid(String emid) {
        this.emid = emid;
    }

    public String getHkid() {
        return hkid;
    }

    public void setHkid(String hkid) {
        this.hkid = hkid;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getManagefee() {
        return managefee;
    }

    public void setManagefee(String managefee) {
        this.managefee = managefee;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
}
