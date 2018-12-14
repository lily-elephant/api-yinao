package yinao.qualityLife.model.domain;

import javax.validation.constraints.NotNull;

public class Course {
    private String ocid ;
    @NotNull
    private String hkid ;
    @NotNull
    private String cid ;
    @NotNull
    private String duration ;
    private String crate_time ;
    private String update_time ;
    private String state ;

    public Course(){}

    public String getOcid() {
        return ocid;
    }

    public void setOcid(String ocid) {
        this.ocid = ocid;
    }

    public String getHkid() {
        return hkid;
    }

    public void setHkid(String hkid) {
        this.hkid = hkid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCrate_time() {
        return crate_time;
    }

    public void setCrate_time(String crate_time) {
        this.crate_time = crate_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
